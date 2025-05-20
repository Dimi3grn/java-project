package src.model;

import java.io.*;

public class SaveManager {
    private static final String SAVE_DIRECTORY = "saves";
    
    public SaveManager() {
        // Créer le dossier de sauvegarde s'il n'existe pas
        File directory = new File(SAVE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    
    public boolean saveGame(Game game, String saveName) {
        try {
            // Créer un objet GameState pour stocker les données à sauvegarder
            GameState gameState = new GameState(
                game.getPlayer().getEndurance(),
                game.getPlayer().getCombatSkill(),
                game.getPlayer().getGold(),
                game.getPlayer().getMeals(),
                game.getPlayer().getPotions(),
                game.getPlayer().getSpecialItems(),
                game.getCurrentSection().getNumber()
            );
            
            // Sérialiser l'objet
            FileOutputStream fileOut = new FileOutputStream(SAVE_DIRECTORY + "/" + saveName + ".save");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(gameState);
            objectOut.close();
            fileOut.close();
            
            return true;
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde: " + e.getMessage());
            return false;
        }
    }
    
    public boolean loadGame(Game game, String saveName) {
        try {
            // Désérialiser l'objet
            FileInputStream fileIn = new FileInputStream(SAVE_DIRECTORY + "/" + saveName + ".save");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            GameState gameState = (GameState) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            
            // Mettre à jour l'état du jeu
            Player player = game.getPlayer();
            player.setEndurance(gameState.getEndurance());
            player.setCombatSkill(gameState.getCombatSkill());
            player.setGold(gameState.getGold());
            player.setMeals(gameState.getMeals());
            player.setPotions(gameState.getPotions());
            player.setSpecialItems(gameState.getSpecialItems());
            
            game.setCurrentSection(gameState.getSectionNumber());
            
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement: " + e.getMessage());
            return false;
        }
    }
    
    public String[] getSaveFiles() {
        File directory = new File(SAVE_DIRECTORY);
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".save"));
        
        if (files == null) {
            return new String[0];
        }
        
        String[] saveNames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getName();
            saveNames[i] = fileName.substring(0, fileName.length() - 5); // Enlever l'extension .save
        }
        
        return saveNames;
    }
    
    // Classe interne pour stocker les données de sauvegarde
    private static class GameState implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private final int endurance;
        private final int combatSkill;
        private final int gold;
        private final int meals;
        private final int potions;
        private final int specialItems;
        private final int sectionNumber;
        
        public GameState(int endurance, int combatSkill, int gold, int meals, int potions, int specialItems, int sectionNumber) {
            this.endurance = endurance;
            this.combatSkill = combatSkill;
            this.gold = gold;
            this.meals = meals;
            this.potions = potions;
            this.specialItems = specialItems;
            this.sectionNumber = sectionNumber;
        }
        
        public int getEndurance() {
            return endurance;
        }
        
        public int getCombatSkill() {
            return combatSkill;
        }
        
        public int getGold() {
            return gold;
        }
        
        public int getMeals() {
            return meals;
        }
        
        public int getPotions() {
            return potions;
        }
        
        public int getSpecialItems() {
            return specialItems;
        }
        
        public int getSectionNumber() {
            return sectionNumber;
        }
    }
} 