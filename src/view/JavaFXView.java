package src.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import src.controller.GameController;
import src.controller.JavaFXGameController;
import src.model.Choice;
import src.model.Player;
import src.model.Section;

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
        root.setBackground(new Background(new BackgroundFill(BG_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        
        // Zone de texte du jeu avec scroll
        gameTextFlow = new TextFlow();
        gameTextFlow.setPadding(new Insets(20));
        
        scrollPane = new ScrollPane(gameTextFlow);
        scrollPane.setFitToWidth(true);
        scrollPane.setBackground(new Background(new BackgroundFill(BG_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        scrollPane.getStyleClass().add("edge-to-edge");
        
        // Zone des choix
        choicesBox = new VBox(10);
        choicesBox.setPadding(new Insets(20));
        choicesBox.setAlignment(Pos.CENTER_LEFT);
        
        // Panneau des statistiques
        statsBox = new HBox(20);
        statsBox.setPadding(new Insets(10, 20, 10, 20));
        statsBox.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 60), CornerRadii.EMPTY, Insets.EMPTY)));
        
        // Menu du jeu
        HBox menuBar = new HBox(10);
        menuBar.setPadding(new Insets(10));
        menuBar.setBackground(new Background(new BackgroundFill(Color.rgb(30, 30, 50), CornerRadii.EMPTY, Insets.EMPTY)));
        
        Button saveBtn = new Button("Sauvegarder");
        Button loadBtn = new Button("Charger");
        Button menuBtn = new Button("Menu principal");
        
        saveBtn.setOnAction(e -> controller.saveGame());
        loadBtn.setOnAction(e -> controller.showLoadMenu());
        menuBtn.setOnAction(e -> showMainMenu());
        
        styleButton(saveBtn);
        styleButton(loadBtn);
        styleButton(menuBtn);
        
        menuBar.getChildren().addAll(saveBtn, loadBtn, menuBtn);
        menuBar.setAlignment(Pos.CENTER_RIGHT);
        
        // Assemblage de l'interface
        VBox gameContentBox = new VBox();
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
        
        // Titre de la section
        Text titleText = new Text("Section " + section.getNumber() + "\n");
        titleText.setFont(Font.font("Georgia", FontWeight.BOLD, 24));
        titleText.setFill(TITLE_COLOR);
        
        // Texte de la section
        Text sectionText = new Text(section.getText() + "\n\n");
        sectionText.setFont(Font.font("Georgia", 16));
        sectionText.setFill(TEXT_COLOR);
        
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
        
        Text choicePrompt = new Text("Que souhaitez-vous faire?");
        choicePrompt.setFont(Font.font("Georgia", FontWeight.BOLD, 18));
        choicePrompt.setFill(CHOICE_COLOR);
        
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
} 