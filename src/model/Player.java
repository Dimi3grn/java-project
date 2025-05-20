package src.model;

public class Player {
    private int endurance;
    private int combatSkill;
    private int gold;
    private int meals;
    private int potions;
    private int specialItems;

    public Player() {
        this.endurance = 20;
        this.combatSkill = 10;
        this.gold = 0;
        this.meals = 0;
        this.potions = 0;
        this.specialItems = 0;
    }

    // Getters et Setters
    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getCombatSkill() {
        return combatSkill;
    }

    public void setCombatSkill(int combatSkill) {
        this.combatSkill = combatSkill;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getMeals() {
        return meals;
    }

    public void setMeals(int meals) {
        this.meals = meals;
    }

    public int getPotions() {
        return potions;
    }

    public void setPotions(int potions) {
        this.potions = potions;
    }

    public int getSpecialItems() {
        return specialItems;
    }

    public void setSpecialItems(int specialItems) {
        this.specialItems = specialItems;
    }

    public void addEndurance(int amount) {
        this.endurance += amount;
    }

    public void addCombatSkill(int amount) {
        this.combatSkill += amount;
    }

    public void addGold(int amount) {
        this.gold += amount;
    }

    public void addMeal() {
        this.meals++;
    }

    public void addPotion() {
        this.potions++;
    }

    public void addSpecialItem() {
        this.specialItems++;
    }

    public boolean useMeal() {
        if (meals > 0) {
            meals--;
            endurance += 4;
            return true;
        }
        return false;
    }

    public boolean usePotion() {
        if (potions > 0) {
            potions--;
            endurance += 6;
            return true;
        }
        return false;
    }

    public boolean hasItem(String item) {
        switch (item.toLowerCase()) {
            case "meal":
                return meals > 0;
            case "potion":
                return potions > 0;
            case "special":
                return specialItems > 0;
            default:
                return false;
        }
    }
} 