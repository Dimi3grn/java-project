package src.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.controller.GameController;
import src.controller.JavaFXGameController;
import src.model.Choice;
import src.model.Player;
import src.model.Section;
import javafx.scene.control.Separator;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class JavaFXView {
    private final Stage stage;
    private Scene scene;
    private BorderPane root;
    private TextFlow gameTextFlow;
    private VBox choicesBox;
    private ScrollPane scrollPane;
    private HBox statsBox;
    private JavaFXGameController controller;
    
    // Couleurs thématiques
    private final Color BG_COLOR = Color.rgb(20, 20, 40);
    private final Color TEXT_COLOR = Color.WHITESMOKE;
    private final Color TITLE_COLOR = Color.LIGHTBLUE;
    private final Color CHOICE_COLOR = Color.LIGHTGREEN;
    private final Color BUTTON_COLOR = Color.rgb(60, 60, 100);
    
    public JavaFXView(Stage stage) {
        this.stage = stage;
        stage.setTitle("La Tour de Cristal - Loup Solitaire");
        
        try {
            // Essayer de charger l'icône
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/crystal_tower_icon.png"))));
        } catch (Exception e) {
            // Continuer sans icône si elle n'est pas trouvée
            System.out.println("Icône non trouvée");
        }
        
        setupStage();
    }
    
    private void setupStage() {
        root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(BG_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        
        scene = new Scene(root, 1024, 768);
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }
    
    public void showMainMenu() {
        VBox menuBox = new VBox(20);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPadding(new Insets(50));
        
        // Titre du jeu
        Text titleText = new Text("LA TOUR DE CRISTAL");
        titleText.setFont(Font.font("Palatino", FontWeight.BOLD, 48));
        titleText.setFill(TITLE_COLOR);
        
        Text subtitleText = new Text("LOUP SOLITAIRE - LIVRE 17");
        subtitleText.setFont(Font.font("Palatino", FontWeight.BOLD, 24));
        subtitleText.setFill(TEXT_COLOR);
        
        // Boutons du menu
        Button newGameBtn = createMenuButton("Nouvelle partie");
        Button loadGameBtn = createMenuButton("Charger une partie");
        Button exitBtn = createMenuButton("Quitter");
        
        menuBox.getChildren().addAll(titleText, subtitleText, 
                new VBox(10), // Espacement
                newGameBtn, loadGameBtn, exitBtn);
        
        // Description du jeu
        TextFlow descriptionFlow = new TextFlow();
        descriptionFlow.setPadding(new Insets(30, 50, 30, 50));
        descriptionFlow.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        
        Text descriptionText = new Text("Vous êtes le Seigneur Kai, Loup Solitaire, légendaire héros du Sommerlund. "
                + "Capturé lors d'une mission secrète, vous vous réveillez dans les profondeurs de la Tour de Cristal. "
                + "Votre mission: vous échapper, découvrir les plans du maléfique sorcier Zahda et sauver le royaume.");
        descriptionText.setFill(TEXT_COLOR);
        descriptionText.setFont(Font.font("Georgia", 16));
        
        descriptionFlow.getChildren().add(descriptionText);
        
        // Événements des boutons
        newGameBtn.setOnAction(e -> {
            this.controller = new JavaFXGameController(this);
            this.controller.startNewGame();
        });
        
        loadGameBtn.setOnAction(e -> showLoadGameScreen());
        
        exitBtn.setOnAction(e -> Platform.exit());
        
        BorderPane menuRoot = new BorderPane();
        menuRoot.setBackground(new Background(new BackgroundFill(BG_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        menuRoot.setCenter(menuBox);
        menuRoot.setBottom(descriptionFlow);
        
        scene = new Scene(menuRoot, 1024, 768);
        stage.setScene(scene);
    }
    
    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Georgia", FontWeight.BOLD, 18));
        button.setPrefWidth(200);
        button.setPrefHeight(50);
        button.setStyle("-fx-background-color: #3c3c64; -fx-text-fill: white; -fx-border-color: #8a8aff; -fx-border-width: 2px;");
        
        button.setOnMouseEntered(e -> 
            button.setStyle("-fx-background-color: #5050a0; -fx-text-fill: white; -fx-border-color: #8a8aff; -fx-border-width: 2px;")
        );
        
        button.setOnMouseExited(e -> 
            button.setStyle("-fx-background-color: #3c3c64; -fx-text-fill: white; -fx-border-color: #8a8aff; -fx-border-width: 2px;")
        );
        
        return button;
    }
    
    public void setupGameScreen() {
        root = new BorderPane();
        
        // Ajouter un arrière-plan avec image pour toute l'interface
        try {
            // Changer le chemin d'accès: d'abord essayer depuis les ressources externes
            Image backgroundImage = null;
            try {
                // Essayer de charger depuis un fichier externe
                File file = new File("src/resources/background.jpg");
                if (file.exists()) {
                    backgroundImage = new Image(file.toURI().toString());
                }
            } catch (Exception e) {
                System.out.println("Impossible de charger l'image depuis le système de fichiers");
            }
            
            // Si ça n'a pas fonctionné, utiliser une couleur par défaut avec dégradé
            if (backgroundImage == null) {
                // Définir un dégradé comme arrière-plan par défaut
                LinearGradient gradient = new LinearGradient(0, 0, 0, 1, true,
                    CycleMethod.NO_CYCLE,
                    new Stop(0, Color.rgb(20, 20, 60)),
                    new Stop(1, Color.rgb(50, 10, 90))
                );
                root.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                // Utiliser l'image chargée
                BackgroundImage bgImage = new BackgroundImage(
                    backgroundImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
                );
                root.setBackground(new Background(bgImage));
            }
        } catch (Exception e) {
            // Fallback avec la couleur de fond en cas d'échec
            System.out.println("Erreur de chargement d'image: " + e.getMessage());
            root.setBackground(new Background(new BackgroundFill(BG_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        
        // Zone de texte du jeu avec scroll et fond semi-transparent
        gameTextFlow = new TextFlow();
        gameTextFlow.setPadding(new Insets(20));
        
        // Ajouter un fond semi-transparent au texte pour améliorer la lisibilité
        gameTextFlow.setBackground(new Background(new BackgroundFill(
            Color.rgb(20, 20, 40, 0.85), // Couleur avec transparence
            new CornerRadii(5),
            Insets.EMPTY
        )));
        
        scrollPane = new ScrollPane(gameTextFlow);
        scrollPane.setFitToWidth(true);
        
        // Rendre le fond du scroll transparent
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.getStyleClass().add("edge-to-edge");
        
        // Zone des choix avec fond semi-transparent
        choicesBox = new VBox(10);
        choicesBox.setPadding(new Insets(20));
        choicesBox.setAlignment(Pos.CENTER_LEFT);
        choicesBox.setBackground(new Background(new BackgroundFill(
            Color.rgb(20, 20, 40, 0.85), // Couleur avec transparence
            new CornerRadii(5),
            Insets.EMPTY
        )));
        
        // Panneau des statistiques avec fond semi-transparent
        statsBox = new HBox(20);
        statsBox.setPadding(new Insets(10, 20, 10, 20));
        statsBox.setBackground(new Background(new BackgroundFill(
            Color.rgb(40, 40, 60, 0.9),
            new CornerRadii(5),
            Insets.EMPTY
        )));
        
        // Menu du jeu avec fond semi-transparent - Avec bouton d'aide ajouté
        HBox menuBar = new HBox(10);
        menuBar.setPadding(new Insets(10));
        menuBar.setBackground(new Background(new BackgroundFill(
            Color.rgb(30, 30, 50, 0.9),
            new CornerRadii(0),
            Insets.EMPTY
        )));
        
        // Bouton d'aide à gauche
        Button helpBtn = new Button("?");
        helpBtn.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        helpBtn.setPrefWidth(30);
        helpBtn.setPrefHeight(30);
        helpBtn.setStyle(
            "-fx-background-color: #3c3c64; " +
            "-fx-text-fill: #8a8aff; " +
            "-fx-border-color: #8a8aff; " +
            "-fx-border-width: 1px; " +
            "-fx-border-radius: 15px; " +
            "-fx-background-radius: 15px;"
        );
        helpBtn.setOnMouseEntered(e -> 
            helpBtn.setStyle(
                "-fx-background-color: #5050a0; " +
                "-fx-text-fill: white; " +
                "-fx-border-color: #8a8aff; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 15px; " +
                "-fx-background-radius: 15px;"
            )
        );
        helpBtn.setOnMouseExited(e -> 
            helpBtn.setStyle(
                "-fx-background-color: #3c3c64; " +
                "-fx-text-fill: #8a8aff; " +
                "-fx-border-color: #8a8aff; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 15px; " +
                "-fx-background-radius: 15px;"
            )
        );
        helpBtn.setOnAction(e -> showReadmeHelp());
        
        // Boutons du menu principal à droite
        Button saveBtn = new Button("Sauvegarder");
        Button loadBtn = new Button("Charger");
        Button menuBtn = new Button("Menu principal");
        
        saveBtn.setOnAction(e -> controller.saveGame());
        loadBtn.setOnAction(e -> controller.showLoadMenu());
        menuBtn.setOnAction(e -> showMainMenu());
        
        styleButton(saveBtn);
        styleButton(loadBtn);
        styleButton(menuBtn);
        
        // Créer une barre divisée en deux parties: aide à gauche, menu à droite
        HBox rightButtons = new HBox(10, saveBtn, loadBtn, menuBtn);
        rightButtons.setAlignment(Pos.CENTER_RIGHT);
        
        // Utiliser tout l'espace disponible pour la séparation
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        menuBar.getChildren().addAll(helpBtn, spacer, rightButtons);
        
        // Ajouter de la marge entre les composants
        VBox gameContentBox = new VBox(15); // 15px de marge
        gameContentBox.setPadding(new Insets(20));
        gameContentBox.getChildren().addAll(scrollPane, choicesBox);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
        root.setTop(menuBar);
        root.setCenter(gameContentBox);
        root.setBottom(statsBox);
        
        scene = new Scene(root, 1024, 768);
        stage.setScene(scene);
    }
    
    private void styleButton(Button button) {
        button.setFont(Font.font("Georgia", 14));
        button.setTextFill(TEXT_COLOR);
        button.setStyle("-fx-background-color: #3c3c64; -fx-border-color: #8a8aff; -fx-border-width: 1px;");
        
        button.setOnMouseEntered(e -> 
            button.setStyle("-fx-background-color: #5050a0; -fx-border-color: #8a8aff; -fx-border-width: 1px;")
        );
        
        button.setOnMouseExited(e -> 
            button.setStyle("-fx-background-color: #3c3c64; -fx-border-color: #8a8aff; -fx-border-width: 1px;")
        );
    }
    
    public void displaySection(Section section) {
        if (section == null) return;
        
        gameTextFlow.getChildren().clear();
        
        // Titre de la section avec effet d'ombre pour une meilleure lisibilité
        Text titleText = new Text("Section " + section.getNumber() + "\n");
        titleText.setFont(Font.font("Georgia", FontWeight.BOLD, 24));
        titleText.setFill(TITLE_COLOR);
        
        // Ajouter un effet d'ombre pour améliorer la lisibilité sur l'image de fond
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(2.0);
        dropShadow.setOffsetX(1.0);
        dropShadow.setOffsetY(1.0);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.7));
        titleText.setEffect(dropShadow);
        
        // Texte de la section avec effet d'ombre
        Text sectionText = new Text(section.getText() + "\n\n");
        sectionText.setFont(Font.font("Georgia", 16));
        sectionText.setFill(TEXT_COLOR);
        sectionText.setEffect(dropShadow);
        
        gameTextFlow.getChildren().addAll(titleText, sectionText);
        
        // Afficher les choix
        displayChoices(section.getChoices());
        
        // Scroller au début de la section
        Platform.runLater(() -> scrollPane.setVvalue(0));
    }
    
    public void displayChoices(List<Choice> choices) {
        choicesBox.getChildren().clear();
        
        if (choices == null || choices.isEmpty()) {
            // C'est une section finale
            Button continueBtn = new Button("Continuer");
            styleChoiceButton(continueBtn);
            continueBtn.setOnAction(e -> controller.handleGameEnd());
            choicesBox.getChildren().add(continueBtn);
            return;
        }
        
        // Titre avec ombre pour améliorer la lisibilité
        Text choicePrompt = new Text("Que souhaitez-vous faire?");
        choicePrompt.setFont(Font.font("Georgia", FontWeight.BOLD, 18));
        choicePrompt.setFill(CHOICE_COLOR);
        
        // Ajouter un effet d'ombre
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(2.0);
        dropShadow.setOffsetX(1.0);
        dropShadow.setOffsetY(1.0);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.7));
        choicePrompt.setEffect(dropShadow);
        
        choicesBox.getChildren().add(choicePrompt);
        
        // Ajouter les choix de la section
        for (int i = 0; i < choices.size(); i++) {
            final int choiceIndex = i;
            Choice choice = choices.get(i);
            
            Button choiceBtn = new Button(choice.getText());
            styleChoiceButton(choiceBtn);
            
            choiceBtn.setOnAction(e -> controller.handleChoice(choice, choiceIndex));
            
            choicesBox.getChildren().add(choiceBtn);
        }
    }
    
    private void styleChoiceButton(Button button) {
        button.setFont(Font.font("Georgia", 16));
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setPadding(new Insets(10, 15, 10, 15));
        button.setTextFill(TEXT_COLOR);
        button.setWrapText(true);
        
        button.setStyle(
            "-fx-background-color: #3c3c64; " +
            "-fx-border-color: #8a8aff; " + 
            "-fx-border-width: 1px; " +
            "-fx-border-radius: 5px;"
        );
        
        button.setOnMouseEntered(e -> 
            button.setStyle(
                "-fx-background-color: #5050a0; " +
                "-fx-border-color: #8a8aff; " + 
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 5px;"
            )
        );
        
        button.setOnMouseExited(e -> 
            button.setStyle(
                "-fx-background-color: #3c3c64; " +
                "-fx-border-color: #8a8aff; " + 
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 5px;"
            )
        );
    }
    
    public void updatePlayerStats(Player player) {
        statsBox.getChildren().clear();
        
        // Statistiques de base
        VBox statsVBox = new VBox(5);
        statsVBox.setPadding(new Insets(5));
        statsVBox.setBackground(new Background(new BackgroundFill(Color.rgb(50, 50, 70), new CornerRadii(5), Insets.EMPTY)));
        
        Label statsTitle = new Label("ÉTAT DU PERSONNAGE");
        statsTitle.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        statsTitle.setTextFill(TEXT_COLOR);
        
        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(10);
        statsGrid.setVgap(5);
        
        // Ligne 1: Endurance
        statsGrid.add(new Label("Endurance:"), 0, 0);
        Label enduranceLabel = new Label(String.valueOf(player.getEndurance()));
        enduranceLabel.setTextFill(Color.YELLOW);
        statsGrid.add(enduranceLabel, 1, 0);
        
        // Ligne 2: Combat
        statsGrid.add(new Label("Combat:"), 0, 1);
        Label combatLabel = new Label(String.valueOf(player.getCombatSkill()));
        combatLabel.setTextFill(Color.YELLOW);
        statsGrid.add(combatLabel, 1, 1);
        
        // Ligne 3: Or
        statsGrid.add(new Label("Or:"), 0, 2);
        Label goldLabel = new Label(String.valueOf(player.getGold()));
        goldLabel.setTextFill(Color.YELLOW);
        statsGrid.add(goldLabel, 1, 2);
        
        // Appliquer un style aux labels
        for (var node : statsGrid.getChildren()) {
            if (node instanceof Label) {
                ((Label) node).setFont(Font.font("Georgia", 14));
                if (!((Label) node).getTextFill().equals(Color.YELLOW)) {
                    ((Label) node).setTextFill(TEXT_COLOR);
                }
            }
        }
        
        statsVBox.getChildren().addAll(statsTitle, statsGrid);
        
        // Inventaire
        VBox inventoryVBox = new VBox(5);
        inventoryVBox.setPadding(new Insets(5));
        inventoryVBox.setBackground(new Background(new BackgroundFill(Color.rgb(60, 60, 40), new CornerRadii(5), Insets.EMPTY)));
        
        Label inventoryTitle = new Label("INVENTAIRE");
        inventoryTitle.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        inventoryTitle.setTextFill(TEXT_COLOR);
        
        GridPane inventoryGrid = new GridPane();
        inventoryGrid.setHgap(10);
        inventoryGrid.setVgap(5);
        
        // Ligne 1: Potions
        inventoryGrid.add(new Label("Potions:"), 0, 0);
        Label potionsLabel = new Label(String.valueOf(player.getPotions()));
        setColorBasedOnValue(potionsLabel, player.getPotions());
        inventoryGrid.add(potionsLabel, 1, 0);
        
        // Ligne 2: Repas
        inventoryGrid.add(new Label("Repas:"), 0, 1);
        Label mealsLabel = new Label(String.valueOf(player.getMeals()));
        setColorBasedOnValue(mealsLabel, player.getMeals());
        inventoryGrid.add(mealsLabel, 1, 1);
        
        // Ligne 3: Objets spéciaux
        inventoryGrid.add(new Label("Objets:"), 0, 2);
        Label itemsLabel = new Label(String.valueOf(player.getSpecialItems()));
        setColorBasedOnValue(itemsLabel, player.getSpecialItems());
        inventoryGrid.add(itemsLabel, 1, 2);
        
        // Appliquer un style aux labels
        for (var node : inventoryGrid.getChildren()) {
            if (node instanceof Label && ((Label) node).getTextFill().equals(Color.BLACK)) {
                ((Label) node).setFont(Font.font("Georgia", 14));
                ((Label) node).setTextFill(TEXT_COLOR);
            }
        }
        
        inventoryVBox.getChildren().addAll(inventoryTitle, inventoryGrid);
        
        // Ajouter les panneaux à la barre de statistiques
        statsBox.getChildren().addAll(statsVBox, inventoryVBox);
    }
    
    private void setColorBasedOnValue(Label label, int value) {
        label.setFont(Font.font("Georgia", 14));
        if (value > 0) {
            label.setTextFill(Color.LIGHTGREEN);
        } else {
            label.setTextFill(Color.LIGHTCORAL);
        }
    }
    
    public void appendGameText(String text) {
        Text textNode = new Text(text + "\n\n");
        textNode.setFont(Font.font("Georgia", 16));
        textNode.setFill(TEXT_COLOR);
        
        gameTextFlow.getChildren().add(textNode);
        
        // Auto-scroll
        Platform.runLater(() -> scrollPane.setVvalue(1.0));
    }
    
    public void appendGameText(String text, Color color) {
        Text textNode = new Text(text + "\n\n");
        textNode.setFont(Font.font("Georgia", 16));
        textNode.setFill(color);
        
        gameTextFlow.getChildren().add(textNode);
        
        // Auto-scroll
        Platform.runLater(() -> scrollPane.setVvalue(1.0));
    }
    
    public void clearGameText() {
        gameTextFlow.getChildren().clear();
    }
    
    public void showLoadGameScreen() {
        // Implémenter la logique pour l'écran de chargement
        controller = new JavaFXGameController(this);
        controller.showLoadMenu();
    }
    
    // Méthode pour afficher un message d'erreur ou de succès
    public void showMessage(String message, boolean isError) {
        Color color = isError ? Color.RED : Color.GREEN;
        appendGameText(message, color);
    }
    
    public Stage getStage() {
        return stage;
    }
    
    /**
     * Affiche le contenu du fichier README.md dans une fenêtre d'aide avec formatage Markdown amélioré
     */
    private void showReadmeHelp() {
        // Créer une fenêtre modale
        Stage helpStage = new Stage();
        helpStage.initModality(Modality.APPLICATION_MODAL);
        helpStage.initOwner(stage);
        helpStage.setTitle("Documentation - La Tour de Cristal");
        
        // Créer le conteneur principal avec fond sombre
        BorderPane helpRoot = new BorderPane();
        helpRoot.setBackground(new Background(new BackgroundFill(
            Color.rgb(25, 25, 45),
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));
        helpRoot.setPadding(new Insets(15));
        
        // Titre de l'aide
        Text helpTitle = new Text("DOCUMENTATION DU JEU");
        helpTitle.setFont(Font.font("Georgia", FontWeight.BOLD, 24));
        helpTitle.setFill(TITLE_COLOR);
        
        // Créer une zone de texte pour afficher le README
        VBox readmeContainer = new VBox(10);
        readmeContainer.setPadding(new Insets(15));
        
        // Charger le contenu du README.md
        try {
            String readmeContent = new String(Files.readAllBytes(Paths.get("README.md")), StandardCharsets.UTF_8);
            
            // Parcourir ligne par ligne pour créer les composants
            String[] lines = readmeContent.split("\n");
            
            boolean inCodeBlock = false;
            StringBuilder codeBlockContent = new StringBuilder();
            
            boolean inTable = false;
            StringBuilder tableContent = new StringBuilder();
            
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                
                // Gestion des blocs de code
                if (line.startsWith("```")) {
                    if (inCodeBlock) {
                        // Fin du bloc de code
                        inCodeBlock = false;
                        TextArea codeArea = new TextArea(codeBlockContent.toString());
                        codeArea.setEditable(false);
                        codeArea.setWrapText(true);
                        codeArea.setStyle(
                            "-fx-background-color: #2d2d3d; " +
                            "-fx-text-fill: #a9b7c6; " +
                            "-fx-font-family: 'Courier New'; " +
                            "-fx-font-size: 14px; " +
                            "-fx-border-color: #444;"
                        );
                        codeArea.setPrefHeight(codeBlockContent.toString().split("\n").length * 20 + 20);
                        codeBlockContent.setLength(0);
                        readmeContainer.getChildren().add(codeArea);
                    } else {
                        // Début du bloc de code
                        inCodeBlock = true;
                    }
                    continue;
                }
                
                if (inCodeBlock) {
                    codeBlockContent.append(line).append("\n");
                    continue;
                }
                
                // Gestion des tableaux
                if (line.startsWith("|")) {
                    if (!inTable) {
                        inTable = true;
                        tableContent.setLength(0);
                    }
                    tableContent.append(line).append("\n");
                    
                    // Vérifier si c'est la fin du tableau
                    if (i == lines.length - 1 || !lines[i + 1].startsWith("|")) {
                        // Créer un tableau formaté
                        inTable = false;
                        TextArea tableArea = new TextArea(tableContent.toString());
                        tableArea.setEditable(false);
                        tableArea.setWrapText(true);
                        tableArea.setStyle(
                            "-fx-background-color: #2a2a40; " +
                            "-fx-text-fill: #e0e0e0; " +
                            "-fx-font-family: 'Courier New'; " +
                            "-fx-font-size: 14px; " +
                            "-fx-border-color: #555;"
                        );
                        tableArea.setPrefHeight(tableContent.toString().split("\n").length * 24 + 10);
                        readmeContainer.getChildren().add(tableArea);
                    }
                    continue;
                } else if (inTable) {
                    inTable = false;
                    // Créer un tableau formaté
                    TextArea tableArea = new TextArea(tableContent.toString());
                    tableArea.setEditable(false);
                    tableArea.setWrapText(true);
                    tableArea.setStyle(
                        "-fx-background-color: #2a2a40; " +
                        "-fx-text-fill: #e0e0e0; " +
                        "-fx-font-family: 'Courier New'; " +
                        "-fx-font-size: 14px; " +
                        "-fx-border-color: #555;"
                    );
                    tableArea.setPrefHeight(tableContent.toString().split("\n").length * 24 + 10);
                    readmeContainer.getChildren().add(tableArea);
                }
                
                // Titres H1
                if (line.startsWith("# ")) {
                    Label titleLabel = new Label(line.substring(2));
                    titleLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 26));
                    titleLabel.setTextFill(Color.LIGHTBLUE);
                    titleLabel.setPadding(new Insets(10, 0, 5, 0));
                    
                    // Ajouter une séparation horizontale sous le titre
                    Separator separator = new Separator();
                    separator.setStyle("-fx-background-color: #4a6da7;");
                    
                    VBox titleBox = new VBox(5, titleLabel, separator);
                    titleBox.setPadding(new Insets(10, 0, 5, 0));
                    readmeContainer.getChildren().add(titleBox);
                    continue;
                }
                
                // Titres H2
                if (line.startsWith("## ")) {
                    Label titleLabel = new Label(line.substring(3));
                    titleLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 22));
                    titleLabel.setTextFill(Color.LIGHTSKYBLUE);
                    titleLabel.setPadding(new Insets(8, 0, 4, 0));
                    readmeContainer.getChildren().add(titleLabel);
                    continue;
                }
                
                // Titres H3
                if (line.startsWith("### ")) {
                    Label titleLabel = new Label(line.substring(4));
                    titleLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 18));
                    titleLabel.setTextFill(Color.LIGHTSTEELBLUE);
                    titleLabel.setPadding(new Insets(6, 0, 3, 0));
                    readmeContainer.getChildren().add(titleLabel);
                    continue;
                }
                
                // Liste à puces
                if (line.startsWith("- ") || line.startsWith("* ")) {
                    HBox bulletBox = new HBox(10);
                    bulletBox.setAlignment(Pos.CENTER_LEFT);
                    
                    Label bulletLabel = new Label("•");
                    bulletLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                    bulletLabel.setTextFill(Color.LIGHTGRAY);
                    
                    Label textLabel = new Label(line.substring(2));
                    textLabel.setFont(Font.font("Georgia", 14));
                    textLabel.setTextFill(Color.WHITE);
                    textLabel.setWrapText(true);
                    
                    bulletBox.getChildren().addAll(bulletLabel, textLabel);
                    bulletBox.setPadding(new Insets(2, 0, 2, 20));
                    readmeContainer.getChildren().add(bulletBox);
                    continue;
                }
                
                // Ligne vide
                if (line.trim().isEmpty()) {
                    Pane spacer = new Pane();
                    spacer.setPrefHeight(10);
                    readmeContainer.getChildren().add(spacer);
                    continue;
                }
                
                // Texte normal
                Label textLabel = new Label(line);
                textLabel.setFont(Font.font("Georgia", 14));
                textLabel.setTextFill(Color.WHITE);
                textLabel.setWrapText(true);
                readmeContainer.getChildren().add(textLabel);
            }
            
        } catch (Exception e) {
            Label errorLabel = new Label("Impossible de charger le fichier README.md:\n" + e.getMessage());
            errorLabel.setFont(Font.font("Georgia", 14));
            errorLabel.setTextFill(Color.RED);
            readmeContainer.getChildren().add(errorLabel);
        }
        
        // Mettre le contenu dans un ScrollPane
        ScrollPane readmeScroll = new ScrollPane(readmeContainer);
        readmeScroll.setFitToWidth(true);
        readmeScroll.setPrefHeight(600);
        readmeScroll.setBackground(new Background(new BackgroundFill(
            Color.rgb(30, 30, 50),
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));
        readmeScroll.setStyle(
            "-fx-background: rgb(30, 30, 50); " +
            "-fx-background-color: rgb(30, 30, 50); " +
            "-fx-control-inner-background: rgb(30, 30, 50);"
        );
        
        // Bouton de fermeture
        Button closeButton = new Button("Fermer");
        closeButton.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        closeButton.setPrefWidth(120);
        closeButton.setPrefHeight(30);
        closeButton.setStyle(
            "-fx-background-color: #3c3c64; " +
            "-fx-text-fill: white; " +
            "-fx-border-color: #8a8aff; " +
            "-fx-border-width: 1px; " +
            "-fx-border-radius: 5px;"
        );
        closeButton.setOnAction(e -> helpStage.close());
        
        // Conteneur pour le titre
        HBox titleBox = new HBox(15, helpTitle);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(0, 0, 15, 0));
        
        // Conteneur pour le bouton en bas
        HBox bottomBox = new HBox(closeButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(15, 0, 0, 0));
        
        // Assembler l'interface
        VBox contentBox = new VBox(10);
        contentBox.getChildren().addAll(titleBox, readmeScroll, bottomBox);
        helpRoot.setCenter(contentBox);
        
        Scene helpScene = new Scene(helpRoot, 800, 700);
        helpStage.setScene(helpScene);
        helpStage.showAndWait();
    }
} 