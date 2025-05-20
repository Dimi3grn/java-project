package src.model;

/**
 * Classe représentant un ennemi dans la Tour de Cristal
 */
public class Enemy {
    private String name;
    private String description;
    private int combatSkill;
    private int endurance;
    private boolean isBoss;
    private String specialAttack;
    
    public Enemy(String name, String description, int combatSkill, int endurance) {
        this.name = name;
        this.description = description;
        this.combatSkill = combatSkill;
        this.endurance = endurance;
        this.isBoss = false;
        this.specialAttack = null;
    }
    
    public Enemy(String name, String description, int combatSkill, int endurance, boolean isBoss, String specialAttack) {
        this.name = name;
        this.description = description;
        this.combatSkill = combatSkill;
        this.endurance = endurance;
        this.isBoss = isBoss;
        this.specialAttack = specialAttack;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCombatSkill() {
        return combatSkill;
    }

    public int getEndurance() {
        return endurance;
    }
    
    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }
    
    public boolean isBoss() {
        return isBoss;
    }
    
    public String getSpecialAttack() {
        return specialAttack;
    }
    
    public boolean hasSpecialAttack() {
        return specialAttack != null && !specialAttack.isEmpty();
    }
    
    public void takeDamage(int damage) {
        endurance -= damage;
        if (endurance < 0) {
            endurance = 0;
        }
    }
    
    public boolean isDefeated() {
        return endurance <= 0;
    }
    
    /**
     * Crée des ennemis spécifiques à l'univers de la Tour de Cristal
     */
    public static class EnemyFactory {
        public static Enemy createGuard() {
            return new Enemy(
                "Garde de la Tour", 
                "Un soldat en armure légère, armé d'une épée courte et d'un bouclier aux armoiries de Zahda.", 
                7, 
                6
            );
        }
        
        public static Enemy createEliteGuard() {
            return new Enemy(
                "Garde d'élite", 
                "Un guerrier expérimenté portant une armure de plates et maniant une hallebarde avec aisance.", 
                9, 
                8
            );
        }
        
        public static Enemy createZombie() {
            return new Enemy(
                "Serviteur Mort-Vivant", 
                "Une créature autrefois humaine, maintenant animée par la magie noire de Zahda. Sa chair décomposée dégage une odeur pestilentielle.", 
                6, 
                10,
                false,
                "Étreinte putride"
            );
        }
        
        public static Enemy createShadowHound() {
            return new Enemy(
                "Molosse des Ombres", 
                "Un chien de garde monstrueux dont le pelage semble absorber la lumière. Ses yeux luisent d'une flamme rouge malsaine.", 
                8, 
                7,
                false,
                "Morsure ténébreuse"
            );
        }
        
        public static Enemy createCrystalGolem() {
            return new Enemy(
                "Golem de Cristal", 
                "Une imposante créature humanoïde formée de cristaux étincelants. Sa structure reflète et déforme la lumière de façon hypnotique.", 
                10, 
                12,
                true,
                "Frappe cristalline"
            );
        }
        
        public static Enemy createZahda() {
            return new Enemy(
                "Zahda le Mage Noir", 
                "Le maître de la Tour de Cristal. Vêtu d'une longue robe noire ornée de symboles mystiques argentés, " +
                "son visage émacié est encadré par une longue chevelure blanche. Ses yeux brillent d'une lueur surnaturelle violette.", 
                12, 
                16,
                true,
                "Éclair d'énergie noire"
            );
        }
    }
} 