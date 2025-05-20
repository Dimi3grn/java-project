package src.controller;

import src.model.Choice;
import src.model.Player;
import src.view.GameView;
import src.view.ConsoleUtils;

import java.util.Random;
import java.util.Scanner;

public class CombatController {
    private Random random;
    private GameView view;
    private Scanner scanner;
    
    public CombatController(GameView view, Scanner scanner) {
        this.random = new Random();
        this.view = view;
        this.scanner = scanner;
    }
    
    public boolean executeCombat(Player player, Choice choice) {
        int enemyCombatSkill = choice.getEnemyCombatSkill();
        int enemyEndurance = choice.getEnemyEndurance();
        int initialPlayerEndurance = player.getEndurance();
        
        ConsoleUtils.printSeparator();
        view.displayCombatStart();
        view.displayEnemyStats(enemyCombatSkill, enemyEndurance);
        
        boolean combatInProgress = true;
        boolean playerWins = false;
        int roundCount = 1;
        
        while (combatInProgress) {
            // Afficher l'état actuel du combat avec une barre de HP
            ConsoleUtils.printSeparator();
            System.out.println(ConsoleUtils.BLUE + ConsoleUtils.BOLD + "ROUND " + roundCount + ConsoleUtils.RESET);
            ConsoleUtils.displayHealthBars(player.getEndurance(), initialPlayerEndurance, enemyEndurance, choice.getEnemyEndurance());
            
            // Tour du joueur
            int playerRoll = random.nextInt(6) + 1 + player.getCombatSkill();
            int enemyRoll = random.nextInt(6) + 1 + enemyCombatSkill;
            
            view.displayCombatStatus(playerRoll, enemyRoll, player.getEndurance(), enemyEndurance);
            
            // Calcul des dégâts
            if (playerRoll > enemyRoll) {
                enemyEndurance -= 2;
                System.out.println(ConsoleUtils.formatSuccess("L'ennemi perd 2 points d'endurance !"));
            } else if (enemyRoll > playerRoll) {
                player.addEndurance(-2);
                System.out.println(ConsoleUtils.formatError("Vous perdez 2 points d'endurance !"));
            } else {
                System.out.println(ConsoleUtils.formatWarning("Égalité ! Aucun dégât n'est infligé."));
            }
            
            // Vérifier si le combat est terminé
            if (enemyEndurance <= 0) {
                combatInProgress = false;
                playerWins = true;
            } else if (player.getEndurance() <= 0) {
                combatInProgress = false;
                playerWins = false;
            } else {
                // Proposer des options pendant le combat
                System.out.println("\n" + ConsoleUtils.WHITE + ConsoleUtils.BOLD + "Options de combat:" + ConsoleUtils.RESET);
                System.out.println(ConsoleUtils.formatChoice(1, "Continuer le combat"));
                System.out.println(ConsoleUtils.formatChoice(2, "Utiliser une potion " + ConsoleUtils.POTION + " (" + player.getPotions() + " disponibles)"));
                if (player.getMeals() > 0) {
                    System.out.println(ConsoleUtils.formatChoice(3, "Manger un repas " + ConsoleUtils.BOOK + " (" + player.getMeals() + " disponibles)"));
                }
                
                System.out.print("\n" + ConsoleUtils.YELLOW + ConsoleUtils.BOLD + "Votre choix : " + ConsoleUtils.RESET);
                
                String input = scanner.nextLine().trim();
                System.out.println(); // Ajouter un espace pour plus de lisibilité
                
                if (input.equals("2") || input.equalsIgnoreCase("P")) {
                    if (player.usePotion()) {
                        System.out.println(ConsoleUtils.formatSuccess("Vous utilisez une potion et regagnez 6 points d'endurance."));
                    } else {
                        System.out.println(ConsoleUtils.formatError("Vous n'avez pas de potion !"));
                    }
                } else if (input.equals("3") || input.equalsIgnoreCase("M")) {
                    if (player.useMeal()) {
                        System.out.println(ConsoleUtils.formatSuccess("Vous mangez un repas et regagnez 4 points d'endurance."));
                    } else {
                        System.out.println(ConsoleUtils.formatError("Vous n'avez pas de repas !"));
                    }
                }
            }
            
            roundCount++;
            
            // Petite pause pour rendre le combat plus lisible
            if (combatInProgress) {
                try {
                    Thread.sleep(800); // Pause de 0.8 seconde
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        
        // Afficher l'état final du combat
        ConsoleUtils.printSeparator();
        ConsoleUtils.displayHealthBars(player.getEndurance(), initialPlayerEndurance, enemyEndurance, choice.getEnemyEndurance());
        
        if (playerWins) {
            System.out.println(ConsoleUtils.CYAN + "╔════════════════════════════════════╗" + ConsoleUtils.RESET);
            System.out.println(ConsoleUtils.CYAN + "║" + ConsoleUtils.GREEN + ConsoleUtils.BOLD + "        VICTOIRE AU COMBAT !       " + ConsoleUtils.RESET + ConsoleUtils.CYAN + "║" + ConsoleUtils.RESET);
            System.out.println(ConsoleUtils.CYAN + "╚════════════════════════════════════╝" + ConsoleUtils.RESET);
        } else {
            System.out.println(ConsoleUtils.RED + "╔════════════════════════════════════╗" + ConsoleUtils.RESET);
            System.out.println(ConsoleUtils.RED + "║" + ConsoleUtils.WHITE + ConsoleUtils.BOLD + "        DÉFAITE AU COMBAT...       " + ConsoleUtils.RESET + ConsoleUtils.RED + "║" + ConsoleUtils.RESET);
            System.out.println(ConsoleUtils.RED + "╚════════════════════════════════════╝" + ConsoleUtils.RESET);
        }
        
        view.displayCombatEnd(playerWins);
        
        System.out.println("\n" + ConsoleUtils.YELLOW + "Appuyez sur Entrée pour continuer..." + ConsoleUtils.RESET);
        scanner.nextLine();
        
        return playerWins;
    }
} 