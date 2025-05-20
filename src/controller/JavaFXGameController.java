package src.controller;

import javafx.application.Platform;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import src.model.Choice;
import src.model.Game;
import src.model.SaveManager;
import src.model.Section;
import src.view.JavaFXView;

import java.util.Optional;

public class JavaFXGameController {
    private Game game;
    private JavaFXView view;
    private SaveManager saveManager;
    private CombatController combatController;
    
    public JavaFXGameController(JavaFXView view) {
        this.view = view;
        this.game = new Game();
        this.saveManager = new SaveManager();
    }
    
    public void startNewGame() {
        // Configurer l'écran de jeu
        view.setupGameScreen();
        
        // Afficher la section de départ
        Section currentSection = game.getCurrentSection();
        view.displaySection(currentSection);
        view.updatePlayerStats(game.getPlayer());
    }
    
    public void handleChoice(Choice choice, int choiceIndex) {
        if (handleChoiceRequirements(choice)) {
            // Si c'est un combat, gérer le combat
            if (choice.isCombat()) {
                handleCombat(choice);
            }
            
            // Passer à la section suivante
            int nextSection = choice.getNextSection();
            game.setCurrentSection(nextSection);
            Section currentSection = game.getCurrentSection();
            
            // Vérifier si la section courante est null
            if (currentSection == null) {
                view.appendGameText("Erreur: Section non trouvée. Veuillez contacter le développeur.", Color.RED);
                return;
            }
            
            // Vérifier si c'est une section spéciale
            if (isSpecialSection(currentSection.getNumber())) {
                handleSpecialSection(currentSection.getNumber());
            }
            
            // Mettre à jour l'interface
            view.displaySection(currentSection);
            view.updatePlayerStats(game.getPlayer());
            
            // Vérifier si le jeu est terminé
            checkGameOver();
        }
    }
    
    private boolean handleChoiceRequirements(Choice choice) {
        if (choice.requiresGold() && game.getPlayer().getGold() < choice.getGoldRequired()) {
            view.appendGameText("Vous n'avez pas assez d'or !", Color.RED);
            return false;
        }
        
        if (choice.requiresMeal() && game.getPlayer().getMeals() <= 0) {
            view.appendGameText("Vous n'avez pas de repas !", Color.RED);
            return false;
        }
        
        if (choice.requiresPotion() && game.getPlayer().getPotions() <= 0) {
            view.appendGameText("Vous n'avez pas de potion !", Color.RED);
            return false;
        }
        
        if (choice.requiresSpecialItem() && game.getPlayer().getSpecialItems() <= 0) {
            view.appendGameText("Vous n'avez pas l'objet requis !", Color.RED);
            return false;
        }
        
        // Appliquer les effets du choix
        if (choice.givesGold()) {
            game.getPlayer().addGold(choice.getGoldGiven());
            view.appendGameText("Vous avez gagné " + choice.getGoldGiven() + " pièces d'or !", Color.GREEN);
        }
        
        if (choice.givesMeal()) {
            game.getPlayer().addMeal(choice.getMealsGiven());
            view.appendGameText("Vous avez gagné " + choice.getMealsGiven() + " repas !", Color.GREEN);
        }
        
        if (choice.givesPotion()) {
            game.getPlayer().addPotion(choice.getPotionsGiven());
            view.appendGameText("Vous avez gagné " + choice.getPotionsGiven() + " potion(s) !", Color.GREEN);
        }
        
        if (choice.givesSpecialItem()) {
            game.getPlayer().addSpecialItem(choice.getSpecialItemsGiven());
            view.appendGameText("Vous avez gagné un objet spécial !", Color.GREEN);
        }
        
        if (choice.costsGold()) {
            game.getPlayer().removeGold(choice.getGoldRequired());
            view.appendGameText("Vous avez dépensé " + choice.getGoldRequired() + " pièces d'or.", Color.LIGHTYELLOW);
        }
        
        if (choice.costsMeal()) {
            game.getPlayer().removeMeal(1);
            view.appendGameText("Vous avez consommé un repas.", Color.LIGHTYELLOW);
        }
        
        if (choice.costsPotion()) {
            game.getPlayer().removePotion(1);
            view.appendGameText("Vous avez utilisé une potion.", Color.LIGHTYELLOW);
        }
        
        if (choice.costsSpecialItem()) {
            game.getPlayer().removeSpecialItem(1);
            view.appendGameText("Vous avez utilisé un objet spécial.", Color.LIGHTYELLOW);
        }
        
        if (choice.heals()) {
            int healAmount = choice.getHealAmount();
            game.getPlayer().heal(healAmount);
            view.appendGameText("Vous avez récupéré " + healAmount + " points d'endurance !", Color.GREEN);
        }
        
        if (choice.damagesPlayer()) {
            int damageAmount = choice.getDamageAmount();
            game.getPlayer().takeDamage(damageAmount);
            view.appendGameText("Vous avez perdu " + damageAmount + " points d'endurance !", Color.RED);
            
            // Vérifier si le joueur est mort
            if (game.getPlayer().getEndurance() <= 0) {
                view.appendGameText("Vous êtes mort !", Color.RED);
                handleGameOver();
                return false;
            }
        }
        
        return true;
    }
    
    private void handleCombat(Choice choice) {
        // Créer une fenêtre modale pour le combat
        Stage combatStage = new Stage();
        combatStage.initModality(Modality.APPLICATION_MODAL);
        combatStage.initOwner(view.getStage());
        combatStage.setTitle("Combat");
        
        BorderPane combatRoot = new BorderPane();
        combatRoot.setBackground(new Background(new BackgroundFill(Color.rgb(30, 30, 50), CornerRadii.EMPTY, Insets.EMPTY)));
        combatRoot.setPadding(new Insets(20));
        
        // En-tête du combat
        Text combatTitle = new Text("COMBAT ⚔");
        combatTitle.setFill(Color.LIGHTBLUE);
        combatTitle.setFont(Font.font("Georgia", FontWeight.BOLD, 24));
        
        // Combattant
        Text enemyName = new Text(choice.getEnemyName());
        enemyName.setFill(Color.WHITE);
        enemyName.setFont(Font.font("Georgia", FontWeight.BOLD, 18));
        
        // Stats de l'ennemi
        GridPane enemyStats = new GridPane();
        enemyStats.setHgap(10);
        enemyStats.setVgap(5);
        enemyStats.setPadding(new Insets(10));
        
        Label combatLabel = new Label("Habileté au combat:");
        combatLabel.setTextFill(Color.WHITE);
        enemyStats.add(combatLabel, 0, 0);
        
        Label combatValueLabel = new Label(String.valueOf(choice.getEnemyCombatSkill()));
        combatValueLabel.setTextFill(Color.YELLOW);
        enemyStats.add(combatValueLabel, 1, 0);
        
        Label enduranceLabel = new Label("Endurance:");
        enduranceLabel.setTextFill(Color.WHITE);
        enemyStats.add(enduranceLabel, 0, 1);
        
        Label enduranceValueLabel = new Label(String.valueOf(choice.getEnemyEndurance()));
        enduranceValueLabel.setTextFill(Color.YELLOW);
        enemyStats.add(enduranceValueLabel, 1, 1);
        
        // Zone de combat
        TextArea combatLog = new TextArea();
        combatLog.setEditable(false);
        combatLog.setWrapText(true);
        combatLog.setPrefHeight(200);
        combatLog.setStyle("-fx-control-inner-background: #202040; -fx-text-fill: white;");
        
        // Barres de vie
        ProgressBar playerHealthBar = new ProgressBar(1.0);
        playerHealthBar.setPrefWidth(400);
        playerHealthBar.setStyle("-fx-accent: green;");
        
        ProgressBar enemyHealthBar = new ProgressBar(1.0);
        enemyHealthBar.setPrefWidth(400);
        enemyHealthBar.setStyle("-fx-accent: red;");
        
        Label playerHealthLabel = new Label("Vous: " + game.getPlayer().getEndurance() + "/" + game.getPlayer().getMaxEndurance());
        playerHealthLabel.setTextFill(Color.LIGHTGREEN);
        
        Label enemyHealthLabel = new Label(choice.getEnemyName() + ": " + choice.getEnemyEndurance() + "/" + choice.getEnemyEndurance());
        enemyHealthLabel.setTextFill(Color.LIGHTCORAL);
        
        // Button de combat
        Button fightButton = new Button("Combattre");
        fightButton.setPrefWidth(150);
        fightButton.setStyle("-fx-background-color: #600000; -fx-text-fill: white; -fx-border-color: #800000; -fx-border-width: 2px;");
        
        Button fleeButton = new Button("Fuir");
        fleeButton.setPrefWidth(150);
        fleeButton.setStyle("-fx-background-color: #303030; -fx-text-fill: white; -fx-border-color: #505050; -fx-border-width: 2px;");
        fleeButton.setDisable(!choice.canFlee());
        
        HBox buttonBox = new HBox(20, fightButton, fleeButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        
        // Combat
        int[] playerEndurance = { game.getPlayer().getEndurance() };
        int[] enemyEndurance = { choice.getEnemyEndurance() };
        int playerMaxEndurance = game.getPlayer().getMaxEndurance();
        int enemyMaxEndurance = choice.getEnemyEndurance();
        int playerCombatSkill = game.getPlayer().getCombatSkill();
        int enemyCombatSkill = choice.getEnemyCombatSkill();
        
        // Log initial
        combatLog.appendText("Vous affrontez " + choice.getEnemyName() + "!\n");
        combatLog.appendText("Votre habileté au combat: " + playerCombatSkill + "\n");
        combatLog.appendText("Habileté au combat de l'ennemi: " + enemyCombatSkill + "\n\n");
        
        fightButton.setOnAction(e -> {
            // Jet de dés du joueur
            int playerRoll = (int) (Math.random() * 10) + 1;
            int playerRatingSum = playerRoll + playerCombatSkill;
            
            // Jet de dés de l'ennemi
            int enemyRoll = (int) (Math.random() * 10) + 1;
            int enemyRatingSum = enemyRoll + enemyCombatSkill;
            
            combatLog.appendText("Votre jet: " + playerRoll + " + " + playerCombatSkill + " = " + playerRatingSum + "\n");
            combatLog.appendText("Jet de l'ennemi: " + enemyRoll + " + " + enemyCombatSkill + " = " + enemyRatingSum + "\n");
            
            if (playerRatingSum > enemyRatingSum) {
                enemyEndurance[0] -= 2;
                combatLog.appendText("Vous infligez 2 points de dégâts à l'ennemi!\n\n");
            } else if (enemyRatingSum > playerRatingSum) {
                playerEndurance[0] -= 2;
                combatLog.appendText("L'ennemi vous inflige 2 points de dégâts!\n\n");
            } else {
                combatLog.appendText("Égalité! Aucun dégât infligé.\n\n");
            }
            
            // Mise à jour des barres de santé
            playerHealthBar.setProgress((double) playerEndurance[0] / playerMaxEndurance);
            enemyHealthBar.setProgress((double) enemyEndurance[0] / enemyMaxEndurance);
            
            playerHealthLabel.setText("Vous: " + playerEndurance[0] + "/" + playerMaxEndurance);
            enemyHealthLabel.setText(choice.getEnemyName() + ": " + enemyEndurance[0] + "/" + enemyMaxEndurance);
            
            // Changer la couleur des barres de santé en fonction du niveau
            if ((double) playerEndurance[0] / playerMaxEndurance < 0.3) {
                playerHealthBar.setStyle("-fx-accent: darkred;");
            } else if ((double) playerEndurance[0] / playerMaxEndurance < 0.6) {
                playerHealthBar.setStyle("-fx-accent: orange;");
            }
            
            if ((double) enemyEndurance[0] / enemyMaxEndurance < 0.3) {
                enemyHealthBar.setStyle("-fx-accent: darkred;");
            } else if ((double) enemyEndurance[0] / enemyMaxEndurance < 0.6) {
                enemyHealthBar.setStyle("-fx-accent: orange;");
            }
            
            // Vérifier la fin du combat
            if (enemyEndurance[0] <= 0) {
                boolean isBoss = choice.getEnemyName() != null && (
                    choice.getEnemyName().equals("Zahda") || 
                    choice.getEnemyName().contains("Chef") || 
                    choice.getEnemyName().contains("Maître") || 
                    choice.getEnemyName().contains("Gardien") ||
                    choice.getEnemyCombatSkill() >= 10 ||  // Tout ennemi puissant est considéré comme un boss
                    choice.getEnemyEndurance() >= 15       // Tout ennemi résistant est considéré comme un boss
                );
                
                combatLog.appendText("Victoire! Vous avez vaincu " + choice.getEnemyName() + "!");
                game.getPlayer().setEndurance(playerEndurance[0]);
                
                // Fermer la fenêtre de combat
                combatStage.close();
                
                // Afficher une notification visuelle pour la victoire contre un boss
                if (isBoss) {
                    Platform.runLater(() -> {
                        Stage victoryStage = new Stage();
                        victoryStage.initModality(Modality.APPLICATION_MODAL);
                        victoryStage.initOwner(view.getStage());
                        victoryStage.setTitle("Victoire!");
                        
                        BorderPane victoryPane = new BorderPane();
                        victoryPane.setBackground(new Background(new BackgroundFill(Color.rgb(20, 20, 40), CornerRadii.EMPTY, Insets.EMPTY)));
                        victoryPane.setPadding(new Insets(20));
                        
                        VBox content = new VBox(15);
                        content.setAlignment(Pos.CENTER);
                        
                        // Titre animé
                        Text victoryText = new Text("BOSS VAINCU !");
                        victoryText.setFont(Font.font("Georgia", FontWeight.BOLD, 32));
                        victoryText.setFill(Color.GOLD);
                        
                        // Animation du texte
                        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), victoryText);
                        fadeTransition.setFromValue(0.3);
                        fadeTransition.setToValue(1.0);
                        fadeTransition.setCycleCount(4);
                        fadeTransition.setAutoReverse(true);
                        
                        // Description de la victoire
                        Text descriptionText = new Text("Vous avez triomphé de " + choice.getEnemyName() + " !");
                        descriptionText.setFont(Font.font("Georgia", 18));
                        descriptionText.setFill(Color.WHITE);
                        
                        // Statistiques
                        Text statsText = new Text(
                            "Votre endurance restante: " + playerEndurance[0] + "/" + playerMaxEndurance + "\n" +
                            "Habileté au combat: " + playerCombatSkill
                        );
                        statsText.setFont(Font.font("Georgia", 14));
                        statsText.setFill(Color.LIGHTBLUE);
                        
                        // Bouton pour continuer
                        Button continueButton = new Button("Continuer l'aventure");
                        continueButton.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
                        continueButton.setPrefWidth(200);
                        continueButton.setStyle(
                            "-fx-background-color: #3c3c64; " +
                            "-fx-text-fill: white; " +
                            "-fx-border-color: gold; " +
                            "-fx-border-width: 2px; " +
                            "-fx-border-radius: 5px;"
                        );
                        continueButton.setOnAction(evt -> victoryStage.close());
                        
                        content.getChildren().addAll(
                            victoryText, 
                            new Separator(), 
                            descriptionText, 
                            statsText, 
                            continueButton
                        );
                        
                        victoryPane.setCenter(content);
                        
                        // Démarrer l'animation
                        fadeTransition.play();
                        
                        Scene victoryScene = new Scene(victoryPane, 400, 300);
                        victoryStage.setScene(victoryScene);
                        victoryStage.showAndWait();
                    });
                }
            } else if (playerEndurance[0] <= 0) {
                combatLog.appendText("Défaite! Vous avez été vaincu par " + choice.getEnemyName() + "!");
                game.getPlayer().setEndurance(0);
                combatStage.close();
                
                // Gestion de la défaite
                Platform.runLater(() -> {
                    handleGameOver();
                });
            }
        });
        
        fleeButton.setOnAction(e -> {
            // Pénalité de fuite
            playerEndurance[0] -= 2;
            game.getPlayer().setEndurance(playerEndurance[0]);
            
            if (playerEndurance[0] <= 0) {
                game.getPlayer().setEndurance(0);
                Platform.runLater(() -> {
                    handleGameOver();
                });
            }
            
            combatStage.close();
        });
        
        // Layout de santé
        VBox playerHealthBox = new VBox(5, playerHealthLabel, playerHealthBar);
        VBox enemyHealthBox = new VBox(5, enemyHealthLabel, enemyHealthBar);
        VBox healthBarsBox = new VBox(10, playerHealthBox, enemyHealthBox);
        healthBarsBox.setPadding(new Insets(10));
        
        // Organisation du layout
        VBox headerBox = new VBox(10, combatTitle, enemyName, enemyStats);
        headerBox.setAlignment(Pos.CENTER);
        
        VBox contentBox = new VBox(15, headerBox, healthBarsBox, combatLog, buttonBox);
        contentBox.setPadding(new Insets(20));
        
        combatRoot.setCenter(contentBox);
        
        Scene combatScene = new Scene(combatRoot, 600, 700);
        combatStage.setScene(combatScene);
        combatStage.showAndWait();
    }
    
    public void handleGameEnd() {
        // Gérer la fin du jeu, victoire ou défaite
        if (game.getCurrentSection().getNumber() == 99) {
            // Victoire
            view.clearGameText();
            view.appendGameText("VICTOIRE", Color.GOLD);
            view.appendGameText("Les plans maléfiques de Zahda sont anéantis!\n\n" +
                    "Vous avez triomphé des périls de la Tour de Cristal et sauvé le royaume du Sommerlund.\n\n" +
                    "Votre nom sera chanté dans les légendes pour les siècles à venir.\n\n" +
                    "Merci d'avoir joué à La Tour de Cristal!");
        } else {
            // Défaite (autre section finale)
            handleGameOver();
        }
    }
    
    public void handleGameOver() {
        view.clearGameText();
        view.appendGameText("FIN DE L'AVENTURE", Color.RED);
        view.appendGameText("La Tour de Cristal demeure un mystère insondable...\n\n" +
                "Votre quête s'achève ici, mais les légendes des Seigneurs Kai perdureront.\n\n" +
                "GAME OVER");
        
        // Désactiver les choix
        view.displayChoices(null);
    }
    
    private boolean isSpecialSection(int sectionNumber) {
        // Les sections qui nécessitent un traitement spécial
        return sectionNumber == 42 || sectionNumber == 88 || sectionNumber == 66;
    }
    
    private void handleSpecialSection(int sectionNumber) {
        switch (sectionNumber) {
            case 42:
                // Section spéciale : Découverte d'un trésor
                view.appendGameText("Vous avez découvert un trésor caché!", Color.GOLD);
                game.getPlayer().addGold(10);
                game.getPlayer().addSpecialItem(1);
                break;
            case 88:
                // Section spéciale : Rencontre mystérieuse
                view.appendGameText("Une présence mystérieuse vous observe dans l'ombre...", Color.PURPLE);
                break;
            case 66:
                // Section spéciale : Piège
                view.appendGameText("Un piège se déclenche!", Color.RED);
                game.getPlayer().takeDamage(2);
                if (game.getPlayer().getEndurance() <= 0) {
                    handleGameOver();
                }
                break;
        }
        
        view.updatePlayerStats(game.getPlayer());
    }
    
    private void checkGameOver() {
        if (game.getPlayer().getEndurance() <= 0) {
            handleGameOver();
        }
    }
    
    public void saveGame() {
        // Créer une fenêtre modale pour sauvegarder
        Stage saveStage = new Stage();
        saveStage.initModality(Modality.APPLICATION_MODAL);
        saveStage.initOwner(view.getStage());
        saveStage.setTitle("Sauvegarder la partie");
        
        VBox saveBox = new VBox(15);
        saveBox.setPadding(new Insets(20));
        saveBox.setAlignment(Pos.CENTER);
        saveBox.setBackground(new Background(new BackgroundFill(Color.rgb(30, 30, 50), CornerRadii.EMPTY, Insets.EMPTY)));
        
        Text saveTitle = new Text("SAUVEGARDER LA PARTIE");
        saveTitle.setFill(Color.LIGHTBLUE);
        saveTitle.setFont(Font.font("Georgia", FontWeight.BOLD, 24));
        
        Label promptLabel = new Label("Entrez un nom pour votre sauvegarde:");
        promptLabel.setTextFill(Color.WHITE);
        promptLabel.setFont(Font.font("Georgia", 14));
        
        TextField saveNameField = new TextField();
        saveNameField.setPromptText("Nom de la sauvegarde");
        saveNameField.setPrefWidth(300);
        saveNameField.setStyle(
            "-fx-background-color: #202040; " +
            "-fx-text-fill: white; " + 
            "-fx-border-color: #8a8aff; " +
            "-fx-prompt-text-fill: lightgray; " +
            "-fx-highlight-fill: #8a8aff; " +
            "-fx-highlight-text-fill: white;"
        );
        
        Button saveButton = createMenuButton("Sauvegarder");
        Button cancelButton = createMenuButton("Annuler");
        
        HBox buttonBox = new HBox(15, saveButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        
        // Message d'erreur ou de succès
        Label messageLabel = new Label("");
        messageLabel.setTextFill(Color.WHITE);
        messageLabel.setFont(Font.font("Georgia", 14));
        messageLabel.setVisible(false);
        
        saveButton.setOnAction(e -> {
            String saveName = saveNameField.getText().trim();
            if (saveName.isEmpty()) {
                messageLabel.setText("Veuillez entrer un nom de sauvegarde.");
                messageLabel.setTextFill(Color.RED);
                messageLabel.setVisible(true);
                return;
            }
            
            boolean success = saveManager.saveGame(game, saveName);
            if (success) {
                messageLabel.setText("Partie sauvegardée avec succès!");
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setVisible(true);
                // Ajouter un délai avant de fermer la fenêtre
                new Thread(() -> {
                    try {
                        Thread.sleep(1500);
                        Platform.runLater(() -> {
                            saveStage.close();
                            view.appendGameText("Partie sauvegardée avec succès!", Color.GREEN);
                        });
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }).start();
            } else {
                messageLabel.setText("Erreur lors de la sauvegarde.");
                messageLabel.setTextFill(Color.RED);
                messageLabel.setVisible(true);
            }
        });
        
        cancelButton.setOnAction(e -> saveStage.close());
        
        saveBox.getChildren().addAll(saveTitle, promptLabel, saveNameField, messageLabel, buttonBox);
        
        Scene saveScene = new Scene(saveBox, 400, 300);
        saveStage.setScene(saveScene);
        saveStage.showAndWait();
    }
    
    public void showGameMenu() {
        // Créer une fenêtre modale pour le menu en jeu
        Stage menuStage = new Stage();
        menuStage.initModality(Modality.APPLICATION_MODAL);
        menuStage.initOwner(view.getStage());
        menuStage.setTitle("Menu du jeu");
        
        VBox menuBox = new VBox(15);
        menuBox.setPadding(new Insets(20));
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setBackground(new Background(new BackgroundFill(Color.rgb(30, 30, 50), CornerRadii.EMPTY, Insets.EMPTY)));
        
        Text menuTitle = new Text("MENU DU JEU");
        menuTitle.setFill(Color.LIGHTBLUE);
        menuTitle.setFont(Font.font("Georgia", FontWeight.BOLD, 24));
        
        Button saveButton = createMenuButton("Sauvegarder la partie");
        Button loadButton = createMenuButton("Charger une partie");
        Button returnButton = createMenuButton("Retourner au jeu");
        
        saveButton.setOnAction(e -> {
            menuStage.close();
            saveGame();
        });
        
        loadButton.setOnAction(e -> {
            menuStage.close();
            showLoadMenu();
        });
        
        returnButton.setOnAction(e -> menuStage.close());
        
        menuBox.getChildren().addAll(menuTitle, saveButton, loadButton, returnButton);
        
        Scene menuScene = new Scene(menuBox, 400, 300);
        menuStage.setScene(menuScene);
        menuStage.showAndWait();
    }
    
    public void showLoadMenu() {
        // Récupérer la liste des sauvegardes
        String[] saveFiles = saveManager.getSaveFiles();
        
        if (saveFiles.length == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Charger une partie");
            alert.setHeaderText(null);
            alert.setContentText("Aucune sauvegarde trouvée.");
            alert.showAndWait();
            return;
        }
        
        // Créer une fenêtre modale pour charger
        Stage loadStage = new Stage();
        loadStage.initModality(Modality.APPLICATION_MODAL);
        loadStage.initOwner(view.getStage());
        loadStage.setTitle("Charger une partie");
        
        VBox loadBox = new VBox(15);
        loadBox.setPadding(new Insets(20));
        loadBox.setAlignment(Pos.CENTER);
        loadBox.setBackground(new Background(new BackgroundFill(Color.rgb(30, 30, 50), CornerRadii.EMPTY, Insets.EMPTY)));
        
        Text loadTitle = new Text("CHARGER UNE PARTIE");
        loadTitle.setFill(Color.LIGHTBLUE);
        loadTitle.setFont(Font.font("Georgia", FontWeight.BOLD, 24));
        
        ListView<String> saveList = new ListView<>();
        saveList.getItems().addAll(saveFiles);
        saveList.setPrefHeight(200);
        saveList.setStyle(
            "-fx-control-inner-background: #202040; " + 
            "-fx-text-fill: white; " +
            "-fx-background-color: #202040; " +
            "-fx-border-color: #8a8aff;"
        );
        
        // Style des cellules de la liste
        saveList.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    setTextFill(Color.WHITE);
                    setStyle("-fx-background-color: #202040;");
                }
            }
        });
        
        // Ajouter un message d'information
        Label infoLabel = new Label("Sélectionnez une sauvegarde et cliquez sur 'Charger'");
        infoLabel.setTextFill(Color.YELLOW);
        infoLabel.setFont(Font.font("Georgia", 14));
        
        Button loadButton = createMenuButton("Charger");
        Button cancelButton = createMenuButton("Annuler");
        
        HBox buttonBox = new HBox(15, loadButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        
        // Message d'erreur
        Label errorLabel = new Label("");
        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(Font.font("Georgia", 14));
        errorLabel.setVisible(false);
        
        loadButton.setOnAction(e -> {
            String selectedSave = saveList.getSelectionModel().getSelectedItem();
            if (selectedSave != null) {
                boolean success = saveManager.loadGame(game, selectedSave);
                if (success) {
                    errorLabel.setText("Chargement réussi!");
                    errorLabel.setTextFill(Color.GREEN);
                    errorLabel.setVisible(true);
                    
                    // Ajouter un délai avant de fermer la fenêtre
                    new Thread(() -> {
                        try {
                            Thread.sleep(1000);
                            Platform.runLater(() -> {
                                loadStage.close();
                                view.setupGameScreen();
                                view.displaySection(game.getCurrentSection());
                                view.updatePlayerStats(game.getPlayer());
                                view.appendGameText("Partie chargée avec succès!", Color.GREEN);
                            });
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }).start();
                } else {
                    errorLabel.setText("Erreur lors du chargement de la partie.");
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setVisible(true);
                }
            } else {
                errorLabel.setText("Veuillez sélectionner une sauvegarde.");
                errorLabel.setVisible(true);
            }
        });
        
        cancelButton.setOnAction(e -> loadStage.close());
        
        loadBox.getChildren().addAll(loadTitle, infoLabel, saveList, errorLabel, buttonBox);
        
        Scene loadScene = new Scene(loadBox, 400, 350);
        loadStage.setScene(loadScene);
        loadStage.showAndWait();
    }
    
    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        button.setPrefWidth(180);
        button.setPrefHeight(40);
        button.setTextFill(Color.WHITE);
        
        button.setStyle(
            "-fx-background-color: #3c3c64; " +
            "-fx-border-color: #8a8aff; " + 
            "-fx-border-width: 2px; " +
            "-fx-border-radius: 5px;"
        );
        
        button.setOnMouseEntered(e -> 
            button.setStyle(
                "-fx-background-color: #5050a0; " +
                "-fx-border-color: #8a8aff; " + 
                "-fx-border-width: 2px; " +
                "-fx-border-radius: 5px;"
            )
        );
        
        button.setOnMouseExited(e -> 
            button.setStyle(
                "-fx-background-color: #3c3c64; " +
                "-fx-border-color: #8a8aff; " + 
                "-fx-border-width: 2px; " +
                "-fx-border-radius: 5px;"
            )
        );
        
        return button;
    }
} 