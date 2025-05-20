package src.view;

public class ConsoleUtils {
    // Codes ANSI pour les couleurs
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    
    // Arrière-plans colorés
    public static final String BG_BLACK = "\u001B[40m";
    public static final String BG_RED = "\u001B[41m";
    public static final String BG_GREEN = "\u001B[42m";
    public static final String BG_YELLOW = "\u001B[43m";
    public static final String BG_BLUE = "\u001B[44m";
    public static final String BG_PURPLE = "\u001B[45m";
    public static final String BG_CYAN = "\u001B[46m";
    public static final String BG_WHITE = "\u001B[47m";
    
    // Styles de texte
    public static final String BOLD = "\u001B[1m";
    public static final String ITALIC = "\u001B[3m";
    public static final String UNDERLINE = "\u001B[4m";
    
    // Symboles et bordures spéciales
    private static final String[] CRYSTAL_BORDER = {
        "╭─────❖─────╮",
        "│           │",
        "╰─────❖─────╯"
    };
    
    // Symboles de décoration 
    public static final String CRYSTAL = "❖";
    public static final String SWORD = "⚔";
    public static final String SHIELD = "🛡";
    public static final String POTION = "⚗";
    public static final String BOOK = "📕";
    public static final String KEY = "🔑";
    public static final String SKULL = "💀";
    public static final String TOWER = "🏰";
    
    public static void clearScreen() {
        // Utilisé pour tenter de nettoyer l'écran, mais ne fonctionne pas toujours
        // On ajoute des sauts de ligne pour créer une séparation visuelle
        System.out.print("\n\n\n\n\n\n\n\n\n");
        System.out.println(BLUE + "═".repeat(80) + RESET);
        System.out.println();
    }
    
    public static void printTitle(String title) {
        String decoratedTitle = " " + TOWER + " " + title + " " + TOWER + " ";
        int length = decoratedTitle.length() - 6; // Compensation pour les caractères ANSI
        
        System.out.println();
        System.out.println(BLUE + "╭" + "═".repeat(length + 2) + "╮" + RESET);
        System.out.println(BLUE + "│" + RESET + BOLD + BG_BLUE + YELLOW + decoratedTitle + RESET + BLUE + "│" + RESET);
        System.out.println(BLUE + "╰" + "═".repeat(length + 2) + "╯" + RESET);
        System.out.println();
    }
    
    public static void printSeparator() {
        System.out.println();
        System.out.println(BLUE + "╭" + "─".repeat(30) + CRYSTAL + "─".repeat(30) + "╮" + RESET);
        System.out.println();
    }
    
    public static void printChapterHeader(int sectionNumber, String title) {
        System.out.println();
        System.out.println(PURPLE + "╔══════" + CRYSTAL + "══════╗" + RESET);
        System.out.println(PURPLE + "║ " + BOLD + "SECTION " + sectionNumber + RESET + PURPLE + " ║" + RESET);
        System.out.println(PURPLE + "╚══════" + CRYSTAL + "══════╝" + RESET);
        System.out.println(YELLOW + ITALIC + "« " + title + " »" + RESET);
        System.out.println();
    }
    
    public static void printStatsBox(int endurance, int combatSkill, int gold) {
        System.out.println(CYAN + "┌───────────────────────┐" + RESET);
        System.out.println(CYAN + "│" + BOLD + " ÉTAT DU PERSONNAGE    " + RESET + CYAN + "│" + RESET);
        System.out.println(CYAN + "├───────────┬───────────┤" + RESET);
        System.out.println(CYAN + "│" + RESET + " Endurance " + CYAN + "│" + RESET + " " + formatStatValue(endurance) + "      " + CYAN + "│" + RESET);
        System.out.println(CYAN + "│" + RESET + " Combat    " + CYAN + "│" + RESET + " " + formatStatValue(combatSkill) + "      " + CYAN + "│" + RESET);
        System.out.println(CYAN + "│" + RESET + " Or        " + CYAN + "│" + RESET + " " + formatStatValue(gold) + "      " + CYAN + "│" + RESET);
        System.out.println(CYAN + "└───────────┴───────────┘" + RESET);
    }
    
    private static String formatStatValue(int value) {
        return YELLOW + value + RESET;
    }
    
    public static String formatStatus(String label, int value) {
        return CYAN + BOLD + label + ": " + YELLOW + value + RESET;
    }
    
    public static String formatChoice(int index, String text) {
        return PURPLE + "  " + index + ". " + GREEN + text + RESET;
    }
    
    public static String formatSection(String text) {
        // Diviser le texte en paragraphes pour une meilleure lisibilité
        String[] paragraphs = text.split("\\. ");
        StringBuilder formattedText = new StringBuilder();
        
        for (int i = 0; i < paragraphs.length; i++) {
            if (i < paragraphs.length - 1) {
                formattedText.append(WHITE + paragraphs[i] + ". " + RESET);
            } else {
                formattedText.append(WHITE + paragraphs[i] + RESET);
            }
            
            if (i < paragraphs.length - 1) {
                formattedText.append("\n   ");
            }
        }
        
        return formattedText.toString();
    }
    
    public static String formatError(String text) {
        return BG_RED + WHITE + BOLD + " " + text + " " + RESET;
    }
    
    public static String formatSuccess(String text) {
        return BG_GREEN + BLACK + BOLD + " " + text + " " + RESET;
    }
    
    public static String formatWarning(String text) {
        return BG_YELLOW + BLACK + BOLD + " " + text + " " + RESET;
    }
    
    public static void displayBanner() {
        String[] banner = {
            "████████╗ ██████╗ ██╗   ██╗██████╗     ██████╗ ███████╗     ██████╗██████╗ ██╗███████╗████████╗ █████╗ ██╗     ",
            "╚══██╔══╝██╔═══██╗██║   ██║██╔══██╗    ██╔══██╗██╔════╝    ██╔════╝██╔══██╗██║██╔════╝╚══██╔══╝██╔══██╗██║     ",
            "   ██║   ██║   ██║██║   ██║██████╔╝    ██║  ██║█████╗      ██║     ██████╔╝██║███████╗   ██║   ███████║██║     ",
            "   ██║   ██║   ██║██║   ██║██╔══██╗    ██║  ██║██╔══╝      ██║     ██╔══██╗██║╚════██║   ██║   ██╔══██║██║     ",
            "   ██║   ╚██████╔╝╚██████╔╝██║  ██║    ██████╔╝███████╗    ╚██████╗██║  ██║██║███████║   ██║   ██║  ██║███████╗",
            "   ╚═╝    ╚═════╝  ╚═════╝ ╚═╝  ╚═╝    ╚═════╝ ╚══════╝     ╚═════╝╚═╝  ╚═╝╚═╝╚══════╝   ╚═╝   ╚═╝  ╚═╝╚══════╝",
            "                                                                                                                 "
        };
        
        // Afficher la bannière en bleu et jaune, façon cristal
        for (int i = 0; i < banner.length; i++) {
            if (i % 2 == 0) {
                System.out.println(BLUE + banner[i] + RESET);
            } else {
                System.out.println(CYAN + banner[i] + RESET);
            }
        }
        
        System.out.println();
        System.out.println(YELLOW + BOLD + "                            LOUP SOLITAIRE - LIVRE 17" + RESET);
        System.out.println();
        System.out.println(WHITE + "                    " + CRYSTAL + " Une aventure épique dans l'univers de Loup Solitaire " + CRYSTAL + RESET);
        System.out.println();
        System.out.println(BLUE + "═".repeat(97) + RESET);
        System.out.println();
    }
    
    public static void displayHealthBars(int playerEndurance, int maxPlayerEndurance, int enemyEndurance, int maxEnemyEndurance) {
        System.out.println(WHITE + BOLD + "ÉTAT DU COMBAT:" + RESET);
        
        // Barre de vie du joueur (maximum 20 caractères)
        int playerBarLength = Math.max(0, (int)((double)playerEndurance / maxPlayerEndurance * 20));
        String playerHealthBar = "█".repeat(playerBarLength) + "▒".repeat(20 - playerBarLength);
        
        // Barre de vie de l'ennemi (maximum 20 caractères)
        int enemyBarLength = Math.max(0, (int)((double)enemyEndurance / maxEnemyEndurance * 20));
        String enemyHealthBar = "█".repeat(enemyBarLength) + "▒".repeat(20 - enemyBarLength);
        
        System.out.println("┌──────────────────────────────────────┐");
        System.out.println("│ " + BOLD + "Vous:   " + RESET + GREEN + playerHealthBar + RESET + " " + YELLOW + playerEndurance + "/" + maxPlayerEndurance + RESET + " │");
        System.out.println("│ " + BOLD + "Ennemi: " + RESET + RED + enemyHealthBar + RESET + " " + YELLOW + enemyEndurance + "/" + maxEnemyEndurance + RESET + " │");
        System.out.println("└──────────────────────────────────────┘");
    }
    
    public static void printInventoryBox(int meals, int potions, int specialItems) {
        System.out.println(YELLOW + "┌───────────────────────┐" + RESET);
        System.out.println(YELLOW + "│" + BOLD + " INVENTAIRE            " + RESET + YELLOW + "│" + RESET);
        System.out.println(YELLOW + "├───────────────┬───────┤" + RESET);
        System.out.println(YELLOW + "│" + RESET + " " + POTION + " Potions    " + YELLOW + "│" + RESET + " " + formatInventoryCount(potions) + " " + YELLOW + "│" + RESET);
        System.out.println(YELLOW + "│" + RESET + " " + BOOK + " Repas      " + YELLOW + "│" + RESET + " " + formatInventoryCount(meals) + " " + YELLOW + "│" + RESET);
        System.out.println(YELLOW + "│" + RESET + " " + CRYSTAL + " Objets     " + YELLOW + "│" + RESET + " " + formatInventoryCount(specialItems) + " " + YELLOW + "│" + RESET);
        System.out.println(YELLOW + "└───────────────┴───────┘" + RESET);
    }
    
    private static String formatInventoryCount(int count) {
        if (count > 0) {
            return GREEN + count + RESET;
        } else {
            return RED + count + RESET;
        }
    }
} 