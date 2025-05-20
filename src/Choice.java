package src;

public class Choice {
    private String text;
    private int nextSection;
    private boolean requiresCombat;
    private int enemyCombatSkill;
    private int enemyEndurance;
    private boolean requiresItem;
    private String requiredItem;
    private boolean requiresGold;
    private int requiredGold;

    public Choice(String text, int nextSection) {
        this.text = text;
        this.nextSection = nextSection;
        this.requiresCombat = false;
        this.requiresItem = false;
        this.requiresGold = false;
    }

    public void setCombat(int enemyCombatSkill, int enemyEndurance) {
        this.requiresCombat = true;
        this.enemyCombatSkill = enemyCombatSkill;
        this.enemyEndurance = enemyEndurance;
    }

    public void setRequiredItem(String item) {
        this.requiresItem = true;
        this.requiredItem = item;
    }

    public void setRequiredGold(int amount) {
        this.requiresGold = true;
        this.requiredGold = amount;
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

    public int getEnemyCombatSkill() {
        return enemyCombatSkill;
    }

    public int getEnemyEndurance() {
        return enemyEndurance;
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
} 