package src;

public class Choice {
    private String text;
    private int nextSection;
    private boolean requiresCombat;
    private int enemyCombatSkill;
    private int enemyEndurance;
    private String enemyName;
    private boolean canFlee;
    
    private boolean requiresItem;
    private String requiredItem;
    private boolean requiresGold;
    private int requiredGold;
    
    private boolean requiresMeal;
    private boolean requiresPotion;
    private boolean requiresSpecialItem;
    
    private boolean givesGold;
    private int goldGiven;
    private boolean givesMeal;
    private int mealsGiven;
    private boolean givesPotion;
    private int potionsGiven;
    private boolean givesSpecialItem;
    private int specialItemsGiven;
    
    private boolean heals;
    private int healAmount;
    private boolean damagesPlayer;
    private int damageAmount;

    public Choice(String text, int nextSection) {
        this.text = text;
        this.nextSection = nextSection;
        this.requiresCombat = false;
        this.requiresItem = false;
        this.requiresGold = false;
        this.requiresMeal = false;
        this.requiresPotion = false;
        this.requiresSpecialItem = false;
        this.givesGold = false;
        this.givesMeal = false;
        this.givesPotion = false;
        this.givesSpecialItem = false;
        this.heals = false;
        this.damagesPlayer = false;
        this.canFlee = false;
        this.enemyName = "Ennemi";
    }

    public void setCombat(int enemyCombatSkill, int enemyEndurance) {
        this.requiresCombat = true;
        this.enemyCombatSkill = enemyCombatSkill;
        this.enemyEndurance = enemyEndurance;
    }
    
    public void setCombat(String enemyName, int enemyCombatSkill, int enemyEndurance, boolean canFlee) {
        this.requiresCombat = true;
        this.enemyName = enemyName;
        this.enemyCombatSkill = enemyCombatSkill;
        this.enemyEndurance = enemyEndurance;
        this.canFlee = canFlee;
    }

    public void setRequiredItem(String item) {
        this.requiresItem = true;
        this.requiredItem = item;
    }

    public void setRequiredGold(int amount) {
        this.requiresGold = true;
        this.requiredGold = amount;
    }
    
    public void setRequiresMeal() {
        this.requiresMeal = true;
    }
    
    public void setRequiresPotion() {
        this.requiresPotion = true;
    }
    
    public void setRequiresSpecialItem() {
        this.requiresSpecialItem = true;
    }
    
    public void setGivesGold(int amount) {
        this.givesGold = true;
        this.goldGiven = amount;
    }
    
    public void setGivesMeal(int amount) {
        this.givesMeal = true;
        this.mealsGiven = amount;
    }
    
    public void setGivesPotion(int amount) {
        this.givesPotion = true;
        this.potionsGiven = amount;
    }
    
    public void setGivesSpecialItem(int amount) {
        this.givesSpecialItem = true;
        this.specialItemsGiven = amount;
    }
    
    public void setHeals(int amount) {
        this.heals = true;
        this.healAmount = amount;
    }
    
    public void setDamagesPlayer(int amount) {
        this.damagesPlayer = true;
        this.damageAmount = amount;
    }

    public String getText() {
        return text;
    }

    public int getNextSection() {
        return nextSection;
    }

    public boolean requiresCombat() {
        return requiresCombat;
    }
    
    public boolean isCombat() {
        return requiresCombat;
    }

    public int getEnemyCombatSkill() {
        return enemyCombatSkill;
    }

    public int getEnemyEndurance() {
        return enemyEndurance;
    }
    
    public String getEnemyName() {
        return enemyName;
    }
    
    public boolean canFlee() {
        return canFlee;
    }

    public boolean requiresItem() {
        return requiresItem;
    }

    public String getRequiredItem() {
        return requiredItem;
    }

    public boolean requiresGold() {
        return requiresGold;
    }

    public int getRequiredGold() {
        return requiredGold;
    }
    
    public boolean requiresMeal() {
        return requiresMeal;
    }
    
    public boolean requiresPotion() {
        return requiresPotion;
    }
    
    public boolean requiresSpecialItem() {
        return requiresSpecialItem;
    }
    
    public boolean givesGold() {
        return givesGold;
    }
    
    public int getGoldGiven() {
        return goldGiven;
    }
    
    public boolean givesMeal() {
        return givesMeal;
    }
    
    public int getMealsGiven() {
        return mealsGiven;
    }
    
    public boolean givesPotion() {
        return givesPotion;
    }
    
    public int getPotionsGiven() {
        return potionsGiven;
    }
    
    public boolean givesSpecialItem() {
        return givesSpecialItem;
    }
    
    public int getSpecialItemsGiven() {
        return specialItemsGiven;
    }
    
    public boolean heals() {
        return heals;
    }
    
    public int getHealAmount() {
        return healAmount;
    }
    
    public boolean damagesPlayer() {
        return damagesPlayer;
    }
    
    public int getDamageAmount() {
        return damageAmount;
    }
    
    public boolean costsGold() {
        return requiresGold;
    }
    
    public boolean costsMeal() {
        return requiresMeal;
    }
    
    public boolean costsPotion() {
        return requiresPotion;
    }
    
    public boolean costsSpecialItem() {
        return requiresSpecialItem;
    }
} 