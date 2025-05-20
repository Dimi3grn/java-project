package src.controller;

import src.model.Game;
import src.model.Choice;
import src.model.Section;
import src.model.SaveManager;
import src.view.GameView;
import src.view.ConsoleUtils;

import java.util.Scanner;

public class GameController {
    private Game game;
    private GameView view;
    private Scanner scanner;
    private CombatController combatController;
    private SaveManager saveManager;

    public GameController() {
        this.game = new Game();
        this.view = new GameView();
        this.scanner = new Scanner(System.in);
        this.combatController = new CombatController(view, scanner);
        this.saveManager = new SaveManager();
    }

    public void startGame() {
        view.displayWelcome();
        
        // Proposer de charger une partie
        displayMainMenu();
        
        while (!game.isGameOver()) {
            ConsoleUtils.printSeparator();
            Section currentSection = game.getCurrentSection();
            
            view.displaySection(currentSection);
            view.displayPlayerStatus(game.getPlayer());
            
            // Vérifier si c'est une section spéciale
            if (isSpecialSection(currentSection.getNumber())) {
                handleSpecialSection(currentSection.getNumber());
            }
            
            if (currentSection.getChoices().isEmpty()) {
                // C'est une section finale
                if (currentSection.getNumber() == 99) {
                    view.displayVictory();
                } else {
                    view.displayGameOver();
                }
                break;
            }
            
            // Afficher des options supplémentaires
            System.out.println(ConsoleUtils.CYAN + "\nOptions disponibles: " + ConsoleUtils.RESET);
            System.out.println(ConsoleUtils.formatChoice(0, "Menu (Sauvegarder/Charger)"));
            System.out.println("");
            
            view.displayChoicePrompt(currentSection.getChoices().size());
            int choiceIndex = getValidChoice(currentSection.getChoices().size());
            
            // Option spéciale pour le menu
            if (choiceIndex == 0) {
                displayGameMenu();
                continue;
            }
            
            Choice selectedChoice = currentSection.getChoices().get(choiceIndex - 1);
            
            if (handleChoice(selectedChoice)) {
                game.setCurrentSection(selectedChoice.getNextSection());
            }
        }
        
        if (game.getPlayer().getEndurance() <= 0) {
            view.displayGameOver();
        }
    }
    
    private void displayMainMenu() {
        boolean inMenu = true;
        
        while (inMenu) {
            ConsoleUtils.displayBanner();
            System.out.println(ConsoleUtils.BOLD + ConsoleUtils.WHITE + "Bienvenue dans La Tour de Cristal - Un livre dont vous êtes le héros" + ConsoleUtils.RESET);
            System.out.println();
            System.out.println(ConsoleUtils.formatChoice(1, "Nouvelle partie"));
            System.out.println(ConsoleUtils.formatChoice(2, "Charger une partie"));
            System.out.println(ConsoleUtils.formatChoice(3, "Quitter"));
            
            System.out.print("\n" + ConsoleUtils.YELLOW + ConsoleUtils.BOLD + "Votre choix : " + ConsoleUtils.RESET);
            int choice = getValidMenuChoice(3);
            
            switch (choice) {
                case 1:
                    inMenu = false;
                    break;
                case 2:
                    if (loadGameFromMenu()) {
                        inMenu = false;
                    }
                    break;
                case 3:
                    System.exit(0);
                    break;
            }
        }
    }
    
    private void displayGameMenu() {
        boolean inMenu = true;
        
        while (inMenu) {
            ConsoleUtils.printSeparator();
            ConsoleUtils.printTitle("MENU DU JEU");
            System.out.println(ConsoleUtils.formatChoice(1, "Sauvegarder la partie"));
            System.out.println(ConsoleUtils.formatChoice(2, "Charger une partie"));
            System.out.println(ConsoleUtils.formatChoice(3, "Retourner au jeu"));
            
            System.out.print("\n" + ConsoleUtils.YELLOW + ConsoleUtils.BOLD + "Votre choix : " + ConsoleUtils.RESET);
            int choice = getValidMenuChoice(3);
            
            switch (choice) {
                case 1:
                    saveGame();
                    break;
                case 2:
                    loadGame();
                    break;
                case 3:
                    inMenu = false;
                    break;
            }
        }
    }
    
    private boolean saveGame() {
        ConsoleUtils.printSeparator();
        ConsoleUtils.printTitle("SAUVEGARDER LA PARTIE");
        System.out.print("\nEntrez un nom pour votre sauvegarde : ");
        String saveName = scanner.nextLine();
        
        if (saveName.trim().isEmpty()) {
            return false;
        }
        
        boolean success = saveManager.saveGame(game, saveName);
        
        if (success) {
            System.out.println(ConsoleUtils.formatSuccess("Partie sauvegardée avec succès !"));
        } else {
            System.out.println(ConsoleUtils.formatError("Erreur lors de la sauvegarde."));
        }
        
        System.out.println("\n" + ConsoleUtils.YELLOW + "Appuyez sur Entrée pour continuer..." + ConsoleUtils.RESET);
        scanner.nextLine();
        return success;
    }
    
    private boolean loadGame() {
        String[] saveFiles = saveManager.getSaveFiles();
        
        if (saveFiles.length == 0) {
            ConsoleUtils.printSeparator();
            System.out.println(ConsoleUtils.formatError("Aucune sauvegarde trouvée."));
            System.out.println("\n" + ConsoleUtils.YELLOW + "Appuyez sur Entrée pour continuer..." + ConsoleUtils.RESET);
            scanner.nextLine();
            return false;
        }
        
        ConsoleUtils.printSeparator();
        ConsoleUtils.printTitle("CHARGER UNE PARTIE");
        
        for (int i = 0; i < saveFiles.length; i++) {
            System.out.println(ConsoleUtils.formatChoice(i + 1, saveFiles[i]));
        }
        
        System.out.println(ConsoleUtils.formatChoice(saveFiles.length + 1, "Retour"));
        
        System.out.print("\n" + ConsoleUtils.YELLOW + ConsoleUtils.BOLD + "Votre choix : " + ConsoleUtils.RESET);
        int choice = getValidMenuChoice(saveFiles.length + 1);
        
        if (choice == saveFiles.length + 1) {
            return false;
        }
        
        boolean success = saveManager.loadGame(game, saveFiles[choice - 1]);
        
        if (success) {
            System.out.println(ConsoleUtils.formatSuccess("Partie chargée avec succès !"));
        } else {
            System.out.println(ConsoleUtils.formatError("Erreur lors du chargement."));
        }
        
        System.out.println("\n" + ConsoleUtils.YELLOW + "Appuyez sur Entrée pour continuer..." + ConsoleUtils.RESET);
        scanner.nextLine();
        return success;
    }
    
    private boolean loadGameFromMenu() {
        return loadGame();
    }
    
    private int getValidMenuChoice(int maxChoice) {
        int choice = -1;
        
        while (choice < 1 || choice > maxChoice) {
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consommer le retour à la ligne
                
                if (choice < 1 || choice > maxChoice) {
                    System.out.println(ConsoleUtils.formatError("Choix invalide !"));
                    System.out.print("\n" + ConsoleUtils.YELLOW + ConsoleUtils.BOLD + "Votre choix : " + ConsoleUtils.RESET);
                }
            } catch (Exception e) {
                scanner.nextLine(); // Nettoyer le scanner
                System.out.println(ConsoleUtils.formatError("Choix invalide !"));
                System.out.print("\n" + ConsoleUtils.YELLOW + ConsoleUtils.BOLD + "Votre choix : " + ConsoleUtils.RESET);
            }
        }
        
        return choice;
    }
    
    private int getValidChoice(int maxChoice) {
        int choice = -1;
        
        while (choice < 0 || choice > maxChoice) {
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consommer le retour à la ligne
                
                if (choice < 0 || choice > maxChoice) {
                    System.out.println(ConsoleUtils.formatError("Choix invalide !"));
                    view.displayChoicePrompt(maxChoice);
                }
            } catch (Exception e) {
                scanner.nextLine(); // Nettoyer le scanner
                System.out.println(ConsoleUtils.formatError("Choix invalide !"));
                view.displayChoicePrompt(maxChoice);
            }
        }
        
        return choice;
    }
    
    private boolean handleChoice(Choice choice) {
        // Vérifier si le choix nécessite un combat
        if (choice.requiresCombat()) {
            return handleCombat(choice);
        }
        
        // Vérifier si le choix nécessite un objet
        if (choice.requiresItem()) {
            if (!game.getPlayer().hasItem(choice.getRequiredItem())) {
                System.out.println(ConsoleUtils.formatError("Vous n'avez pas " + choice.getRequiredItem() + " !"));
                System.out.println("\n" + ConsoleUtils.YELLOW + "Appuyez sur Entrée pour continuer..." + ConsoleUtils.RESET);
                scanner.nextLine();
                return false;
            }
        }
        
        // Vérifier si le choix nécessite de l'or
        if (choice.requiresGold()) {
            if (game.getPlayer().getGold() < choice.getRequiredGold()) {
                System.out.println(ConsoleUtils.formatError("Vous n'avez pas " + choice.getRequiredGold() + " pièces d'or !"));
                System.out.println("\n" + ConsoleUtils.YELLOW + "Appuyez sur Entrée pour continuer..." + ConsoleUtils.RESET);
                scanner.nextLine();
                return false;
            }
            game.getPlayer().addGold(-choice.getRequiredGold());
        }
        
        return true;
    }
    
    private boolean handleCombat(Choice choice) {
        return combatController.executeCombat(game.getPlayer(), choice);
    }
    
    // Gestion des sections spéciales
    private boolean isSpecialSection(int sectionNumber) {
        // Sections qui déclenchent des événements spéciaux
        return sectionNumber == 19 || sectionNumber == 29 || sectionNumber == 31 || sectionNumber == 37 || sectionNumber == 45;
    }
    
    private void handleSpecialSection(int sectionNumber) {
        switch (sectionNumber) {
            case 19:
                view.displaySpecialEvent("VISION MYSTIQUE", 
                    "La pierre de cristal pulse entre vos mains, envoyant des ondes de magie pure à travers votre corps. " +
                    "Une vision s'impose à votre esprit avec une clarté saisissante : un homme vêtu de noir, Zahda, " +
                    "se tient devant un bassin de cristal au sommet de la tour. Il y verse un liquide scintillant " +
                    "tout en psalmodiant dans une langue ancienne. Un nuage sombre se forme au-dessus du bassin, " +
                    "tournoyant lentement avant de prendre la forme d'un portail béant. Au-delà de ce portail, " +
                    "vous apercevez des ombres menaçantes qui tentent de franchir le seuil entre les mondes.");
                System.out.println("\n" + ConsoleUtils.YELLOW + "Appuyez sur Entrée pour continuer..." + ConsoleUtils.RESET);
                scanner.nextLine();
                break;
                
            case 29:
                // Ajout d'un repas supplémentaire lors de la rencontre avec le cuisinier
                game.getPlayer().addMeal();
                view.displaySpecialEvent("REPAS COPIEUX", 
                    "Le cuisinier vous sert un ragoût délicieusement épicé dans un grand bol en bois. " +
                    "La viande est tendre et les légumes parfaitement cuits, nageant dans une sauce riche et savoureuse. " +
                    "C'est le meilleur repas que vous ayez eu depuis des semaines. Vous sentez vos forces revenir " +
                    "à mesure que vous videz le bol. Le cuisinier vous offre également un petit paquet enveloppé de feuilles " +
                    "pour la route. 'Cela pourrait vous être utile plus tard,' dit-il avec un clin d'œil complice.");
                System.out.println("\n" + ConsoleUtils.YELLOW + "Appuyez sur Entrée pour continuer..." + ConsoleUtils.RESET);
                scanner.nextLine();
                break;
                
            case 31: 
                // Le puits empoisonné - possibilité de perdre de la vie
                view.displaySpecialEvent("LE PUITS CENTRAL", 
                    "Vous vous approchez du puits central dont l'eau miroite d'étranges reflets bleutés. " +
                    "Une soif intense vous tenaille soudain. Vous vous rappelez vaguement l'avertissement gravé " +
                    "dans votre cellule concernant l'eau corrompue par Zahda.");
                System.out.println(ConsoleUtils.formatChoice(1, "Boire l'eau du puits"));
                System.out.println(ConsoleUtils.formatChoice(2, "Résister à la tentation"));
                
                System.out.print("\n" + ConsoleUtils.YELLOW + ConsoleUtils.BOLD + "Votre choix : " + ConsoleUtils.RESET);
                int choice = getValidMenuChoice(2);
                
                if (choice == 1) {
                    game.getPlayer().addEndurance(-3);
                    view.displaySpecialEvent("EAU EMPOISONNÉE", 
                        "Vous cédez à la soif et plongez vos mains dans l'eau pour en boire. " +
                        "Dès que le liquide touche vos lèvres, vous sentez un froid intense se répandre dans votre corps, " +
                        "accompagné d'une douleur sourde. L'eau est empoisonnée par la magie noire de Zahda ! " +
                        "Votre endurance diminue de 3 points.");
                } else {
                    game.getPlayer().addSpecialItem();
                    view.displaySpecialEvent("RÉSISTANCE MENTALE", 
                        "Faisant appel à votre discipline Kai de Résistance Mentale, vous résistez au charme " +
                        "qui vous poussait à boire. En observant attentivement le puits, vous remarquez un petit " +
                        "objet brillant au fond. Vous parvenez à le récupérer sans toucher l'eau. " +
                        "C'est un petit cristal enchanté qui pourrait vous être utile plus tard.");
                }
                
                System.out.println("\n" + ConsoleUtils.YELLOW + "Appuyez sur Entrée pour continuer..." + ConsoleUtils.RESET);
                scanner.nextLine();
                break;
                
            case 37:
                // Découverte du rituel
                view.displaySpecialEvent("RÉVÉLATION", 
                    "En explorant cette partie de la tour, vous découvrez un ancien manuscrit caché dans une niche murale. " +
                    "Les pages jaunies contiennent des schémas et des instructions détaillées pour un rituel complexe. " +
                    "Vous comprenez maintenant que Zahda tente d'ouvrir un portail vers le Royaume des Ténèbres pour " +
                    "invoquer d'anciennes entités maléfiques. Si vous ne parvenez pas à l'arrêter avant ce soir, " +
                    "le royaume du Sommerlund et peut-être le monde entier seront en grand danger.");
                System.out.println("\n" + ConsoleUtils.YELLOW + "Appuyez sur Entrée pour continuer..." + ConsoleUtils.RESET);
                scanner.nextLine();
                break;
                
            case 45:
                // Trésor
                game.getPlayer().addGold(25);
                game.getPlayer().addPotion();
                view.displaySpecialEvent("TRÉSOR DÉCOUVERT", 
                    "Au fond de cette salle se trouve un petit coffre orné de symboles mystiques. " +
                    "Vous parvenez à l'ouvrir sans déclencher de piège. À l'intérieur, vous trouvez " +
                    "25 pièces d'or et une potion de guérison de qualité supérieure. " +
                    "Ces trésors appartenaient sans doute à un précédent prisonnier qui n'a pas eu " +
                    "votre chance ou vos compétences pour s'échapper de la Tour de Cristal.");
                System.out.println("\n" + ConsoleUtils.YELLOW + "Appuyez sur Entrée pour continuer..." + ConsoleUtils.RESET);
                scanner.nextLine();
                break;
        }
    }
} 