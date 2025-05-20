package src.model;

public class Player {
    private int endurance;
    private int maxEndurance;
    private int combatSkill;
    private int gold;
    private int meals;
    private int potions;
    private int specialItems;

    public Player() {
        this.endurance = 20;
        this.maxEndurance = 20;
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
        this.endurance = Math.min(endurance, maxEndurance);
    }
    
    public int getMaxEndurance() {
        return maxEndurance;
    }
    
    public void setMaxEndurance(int maxEndurance) {
        this.maxEndurance = maxEndurance;
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
        this.endurance = Math.min(this.endurance + amount, maxEndurance);
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
    
    public void addMeal(int amount) {
        this.meals += amount;
    }

    public void addPotion() {
        this.potions++;
    }
    
    public void addPotion(int amount) {
        this.potions += amount;
    }

    public void addSpecialItem() {
        this.specialItems++;
    }
    
    public void addSpecialItem(int amount) {
        this.specialItems += amount;
    }
    
    public void removeGold(int amount) {
        this.gold = Math.max(0, this.gold - amount);
    }
    
    public void removeMeal(int amount) {
        this.meals = Math.max(0, this.meals - amount);
    }
    
    public void removePotion(int amount) {
        this.potions = Math.max(0, this.potions - amount);
    }
    
    public void removeSpecialItem(int amount) {
        this.specialItems = Math.max(0, this.specialItems - amount);
    }
    
    public void takeDamage(int amount) {
        this.endurance = Math.max(0, this.endurance - amount);
    }
    
    public void heal(int amount) {
        this.endurance = Math.min(this.endurance + amount, maxEndurance);
    }

    public boolean useMeal() {
        if (meals > 0) {
            meals--;
            endurance += 4;
            if (endurance > maxEndurance) {
                endurance = maxEndurance;
            }
            return true;
        }
        return false;
    }

    public boolean usePotion() {
        if (potions > 0) {
            potions--;
            endurance += 6;
            if (endurance > maxEndurance) {
                endurance = maxEndurance;
            }
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
    
    public boolean hasGold(int amount) {
        return gold >= amount;
    }
} 