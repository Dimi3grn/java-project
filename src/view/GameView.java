package src.view;

import src.model.Player;
import src.model.Section;
import src.model.Choice;

public class GameView {
    
    public void displayWelcome() {
        // Afficher la bannière au démarrage
        ConsoleUtils.displayBanner();
        System.out.println(ConsoleUtils.formatSection("Vous êtes le Seigneur Kai, Loup Solitaire, légendaire héros du Sommerlund."));
        System.out.println(ConsoleUtils.formatSection("Capturé lors d'une mission secrète, vous vous réveillez dans les profondeurs de la Tour de Cristal."));
        System.out.println(ConsoleUtils.formatSection("Votre mission: vous échapper, découvrir les plans du maléfique sorcier Zahda et sauver le royaume."));
        System.out.println();
        System.out.println(ConsoleUtils.YELLOW + ConsoleUtils.BOLD + "Que l'aventure commence..." + ConsoleUtils.RESET);
        System.out.println();
    }
    
    public void displaySection(Section section) {
        if (section == null) {
            System.out.println("\n" + ConsoleUtils.formatError("Fin de l'aventure !"));
            return;
        }
        
        ConsoleUtils.printSeparator();
        ConsoleUtils.printChapterHeader(section.getNumber(), "La Tour de Cristal");
        System.out.println(ConsoleUtils.formatSection(section.getText()));
        System.out.println("\n" + ConsoleUtils.BOLD + ConsoleUtils.WHITE + "Que souhaitez-vous faire ?" + ConsoleUtils.RESET);
        
        int choiceIndex = 1;
        for (Choice choice : section.getChoices()) {
            System.out.println(ConsoleUtils.formatChoice(choiceIndex, choice.getText()));
            choiceIndex++;
        }
    }
    
    public void displayPlayerStatus(Player player) {
        ConsoleUtils.printSeparator();
        
        // Afficher les informations du personnage dans une boîte
        ConsoleUtils.printStatsBox(player.getEndurance(), player.getCombatSkill(), player.getGold());
        
        // Afficher l'inventaire dans une boîte séparée
        ConsoleUtils.printInventoryBox(player.getMeals(), player.getPotions(), player.getSpecialItems());
    }
    
    public void displayChoicePrompt(int maxChoice) {
        System.out.print("\n" + ConsoleUtils.YELLOW + ConsoleUtils.BOLD + "Votre choix (1-" + maxChoice + ") : " + ConsoleUtils.RESET);
    }
    
    public void displayInvalidChoice() {
        System.out.println(ConsoleUtils.formatError("Choix invalide !"));
    }
    
    public void displayMissingRequirement(String requirement) {
        System.out.println(ConsoleUtils.formatError("Vous n'avez pas " + requirement + " !"));
    }
    
    public void displayGameOver() {
        ConsoleUtils.printSeparator();
        ConsoleUtils.printTitle("FIN DE L'AVENTURE");
        System.out.println(ConsoleUtils.formatSection("La Tour de Cristal demeure un mystère insondable..."));
        System.out.println(ConsoleUtils.formatSection("Votre quête s'achève ici, mais les légendes des Seigneurs Kai perdureront."));
        System.out.println();
        System.out.println(ConsoleUtils.RED + ConsoleUtils.BOLD + "GAME OVER" + ConsoleUtils.RESET);
    }
    
    public void displayVictory() {
        ConsoleUtils.printSeparator();
        ConsoleUtils.printTitle("VICTOIRE");
        System.out.println(ConsoleUtils.formatSection("Les plans maléfiques de Zahda sont anéantis!"));
        System.out.println(ConsoleUtils.formatSection("Vous avez triomphé des périls de la Tour de Cristal et sauvé le royaume du Sommerlund."));
        System.out.println(ConsoleUtils.formatSection("Votre nom sera chanté dans les légendes pour les siècles à venir."));
        System.out.println();
        System.out.println(ConsoleUtils.YELLOW + ConsoleUtils.BOLD + "Vous êtes victorieux!" + ConsoleUtils.RESET);
        System.out.println();
        System.out.println(ConsoleUtils.formatSection("Merci d'avoir joué à La Tour de Cristal!"));
    }
    
    // Affichage du combat
    public void displayCombatStart() {
        ConsoleUtils.printSeparator();
        ConsoleUtils.printTitle("COMBAT " + ConsoleUtils.SWORD);
    }
    
    public void displayCombatStatus(int playerRoll, int enemyRoll, int playerEndurance, int enemyEndurance) {
        System.out.println("\nVotre jet : " + ConsoleUtils.YELLOW + playerRoll + ConsoleUtils.RESET);
        System.out.println("Jet de l'ennemi : " + ConsoleUtils.RED + enemyRoll + ConsoleUtils.RESET);
        
        if (playerRoll > enemyRoll) {
            System.out.println(ConsoleUtils.formatSuccess("Vous infligez 2 points de dégâts !"));
        } else if (enemyRoll > playerRoll) {
            System.out.println(ConsoleUtils.formatError("Vous subissez 2 points de dégâts !"));
        } else {
            System.out.println(ConsoleUtils.formatWarning("Égalité ! Aucun dégât n'est infligé."));
        }
    }
    
    public void displayCombatEnd(boolean victory) {
        if (victory) {
            System.out.println("\n" + ConsoleUtils.formatSuccess("Vous avez vaincu votre ennemi !"));
        } else {
            System.out.println("\n" + ConsoleUtils.formatError("Vous avez été vaincu !"));
        }
    }

    public void displayEnemyStats(int enemyCombatSkill, int enemyEndurance) {
        System.out.println(ConsoleUtils.WHITE + ConsoleUtils.BOLD + "Votre adversaire:" + ConsoleUtils.RESET);
        System.out.println("┌────────────────────────────────┐");
        System.out.println("│ " + ConsoleUtils.formatStatus("Habileté au combat", enemyCombatSkill) + "     │");
        System.out.println("│ " + ConsoleUtils.formatStatus("Endurance", enemyEndurance) + "            │");
        System.out.println("└────────────────────────────────┘");
    }
    
    public void displaySpecialEvent(String title, String description) {
        ConsoleUtils.printSeparator();
        System.out.println(ConsoleUtils.PURPLE + ConsoleUtils.BOLD + title + ConsoleUtils.RESET);
        System.out.println();
        System.out.println(ConsoleUtils.formatSection(description));
        System.out.println();
        System.out.println(ConsoleUtils.YELLOW + "Appuyez sur Entrée pour continuer..." + ConsoleUtils.RESET);
    }
} 