package src;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class GameManager {
    private Player player;
    private Map<Integer, Section> sections;
    private int currentSection;
    private Scanner scanner;
    private Random random;

    public GameManager() {
        this.player = new Player();
        this.sections = new HashMap<>();
        this.currentSection = 1;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        initializeSections();
    }

    private void initializeSections() {
        // Section 1 - Début de l'aventure
        Section section1 = new Section(1, 
            "Vous vous réveillez dans une cellule sombre et humide. La Tour de Cristal se dresse devant vous, " +
            "son sommet perdu dans les nuages. Vous devez vous échapper et découvrir les secrets de cette tour mystérieuse.");
        
        Choice choice1 = new Choice("Examiner la cellule", 2);
        Choice choice2 = new Choice("Tenter de briser la porte", 3);
        section1.addChoice(choice1);
        section1.addChoice(choice2);
        sections.put(1, section1);

        // Section 2 - Examiner la cellule
        Section section2 = new Section(2,
            "Vous trouvez une vieille clé rouillée sous la paille et une petite fente dans le mur.");
        Choice choice3 = new Choice("Prendre la clé", 4);
        Choice choice4 = new Choice("Examiner la fente", 5);
        section2.addChoice(choice3);
        section2.addChoice(choice4);
        sections.put(2, section2);

        // Section 3 - Tenter de briser la porte
        Section section3 = new Section(3,
            "La porte est solide et verrouillée. Un garde vous entend et arrive en courant !");
        Choice choice5 = new Choice("Combattre le garde", 6);
        choice5.setCombat(7, 6);
        section3.addChoice(choice5);
        sections.put(3, section3);

        // Section 4 - Prendre la clé
        Section section4 = new Section(4,
            "Vous prenez la clé rouillée. Elle pourrait peut-être ouvrir la porte de votre cellule.");
        Choice choice6 = new Choice("Utiliser la clé sur la porte", 7);
        Choice choice7 = new Choice("Examiner la fente", 5);
        section4.addChoice(choice6);
        section4.addChoice(choice7);
        sections.put(4, section4);

        // Section 5 - Examiner la fente
        Section section5 = new Section(5,
            "La fente est trop étroite pour passer, mais vous apercevez quelque chose qui brille de l'autre côté.");
        Choice choice8 = new Choice("Tenter d'atteindre l'objet", 8);
        Choice choice9 = new Choice("Retourner à la cellule", 2);
        section5.addChoice(choice8);
        section5.addChoice(choice9);
        sections.put(5, section5);

        // Section 6 - Combat avec le garde
        Section section6 = new Section(6,
            "Vous avez vaincu le garde ! Vous trouvez une potion de soin sur son corps.");
        Choice choice10 = new Choice("Prendre la potion", 9);
        Choice choice11 = new Choice("Fouiller le garde", 10);
        section6.addChoice(choice10);
        section6.addChoice(choice11);
        sections.put(6, section6);

        // Section 7 - Utiliser la clé
        Section section7 = new Section(7,
            "La clé fonctionne ! La porte s'ouvre avec un grincement. Vous êtes maintenant dans un couloir sombre.");
        Choice choice12 = new Choice("Aller à gauche", 11);
        Choice choice13 = new Choice("Aller à droite", 12);
        section7.addChoice(choice12);
        section7.addChoice(choice13);
        sections.put(7, section7);

        // Section 8 - Atteindre l'objet
        Section section8 = new Section(8,
            "Vous parvenez à attraper l'objet : c'est une petite pierre brillante qui semble avoir des propriétés magiques.");
        Choice choice14 = new Choice("Garder la pierre", 13);
        Choice choice15 = new Choice("La jeter", 14);
        section8.addChoice(choice14);
        section8.addChoice(choice15);
        sections.put(8, section8);

        // Section 9 - Prendre la potion
        Section section9 = new Section(9,
            "Vous prenez la potion. Elle pourrait vous être utile plus tard.");
        player.addPotion();
        Choice choice16 = new Choice("Continuer", 15);
        section9.addChoice(choice16);
        sections.put(9, section9);

        // Section 10 - Fouiller le garde
        Section section10 = new Section(10,
            "Vous trouvez une bourse contenant 10 pièces d'or et une clé en or.");
        player.addGold(10);
        Choice choice17 = new Choice("Prendre la clé en or", 16);
        Choice choice18 = new Choice("Continuer sans la clé", 15);
        section10.addChoice(choice17);
        section10.addChoice(choice18);
        sections.put(10, section10);

        // Section 11 - Aller à gauche
        Section section11 = new Section(11,
            "Le couloir s'élargit et vous mène à une salle de garde. Deux gardes sont en train de jouer aux dés.");
        Choice choice19 = new Choice("Les attaquer", 17);
        choice19.setCombat(8, 8);
        Choice choice20 = new Choice("Tenter de passer discrètement", 18);
        section11.addChoice(choice19);
        section11.addChoice(choice20);
        sections.put(11, section11);

        // Section 12 - Aller à droite
        Section section12 = new Section(12,
            "Le couloir se rétrécit et débouche sur une cuisine. Un cuisinier est occupé à préparer le repas.");
        Choice choice21 = new Choice("Parler au cuisinier", 19);
        Choice choice22 = new Choice("Fouiller la cuisine", 20);
        section12.addChoice(choice21);
        section12.addChoice(choice22);
        sections.put(12, section12);

        // Section 13 - Garder la pierre
        Section section13 = new Section(13,
            "La pierre brille d'une lueur étrange dans votre main. Vous sentez qu'elle a un pouvoir spécial.");
        player.addSpecialItem();
        Choice choice23 = new Choice("Continuer", 21);
        section13.addChoice(choice23);
        sections.put(13, section13);

        // Section 14 - Jeter la pierre
        Section section14 = new Section(14,
            "La pierre tombe et se brise en mille morceaux. Un éclair de lumière traverse la cellule.");
        Choice choice24 = new Choice("Continuer", 22);
        section14.addChoice(choice24);
        sections.put(14, section14);

        // Section 15 - Continuer après la potion
        Section section15 = new Section(15,
            "Vous avancez prudemment dans le couloir. Des bruits de pas résonnent au loin.");
        Choice choice25 = new Choice("Se cacher", 23);
        Choice choice26 = new Choice("Avancer rapidement", 24);
        section15.addChoice(choice25);
        section15.addChoice(choice26);
        sections.put(15, section15);

        // Section 16 - Prendre la clé en or
        Section section16 = new Section(16,
            "La clé en or brille d'un éclat particulier. Elle doit ouvrir quelque chose d'important.");
        player.addSpecialItem();
        Choice choice27 = new Choice("Continuer", 25);
        section16.addChoice(choice27);
        sections.put(16, section16);

        // Section 17 - Combat avec les gardes
        Section section17 = new Section(17,
            "Vous avez vaincu les deux gardes ! Leur table de jeu contient des pièces d'or.");
        player.addGold(15);
        Choice choice28 = new Choice("Prendre l'or", 26);
        Choice choice29 = new Choice("Continuer", 27);
        section17.addChoice(choice28);
        section17.addChoice(choice29);
        sections.put(17, section17);

        // Section 18 - Passer discrètement
        Section section18 = new Section(18,
            "Vous parvenez à vous faufiler derrière les gardes sans être remarqué.");
        Choice choice30 = new Choice("Continuer", 28);
        section18.addChoice(choice30);
        sections.put(18, section18);

        // Section 19 - Parler au cuisinier
        Section section19 = new Section(19,
            "Le cuisinier vous propose un repas chaud en échange d'une faveur.");
        Choice choice31 = new Choice("Accepter", 29);
        Choice choice32 = new Choice("Refuser", 30);
        section19.addChoice(choice31);
        section19.addChoice(choice32);
        sections.put(19, section19);

        // Section 20 - Fouiller la cuisine
        Section section20 = new Section(20,
            "Vous trouvez des provisions et une petite cachette derrière les étagères.");
        Choice choice33 = new Choice("Prendre des provisions", 31);
        Choice choice34 = new Choice("Examiner la cachette", 32);
        section20.addChoice(choice33);
        section20.addChoice(choice34);
        sections.put(20, section20);

        // Section 21 - Continuer avec la pierre
        Section section21 = new Section(21,
            "La pierre continue de briller, guidant votre chemin dans l'obscurité.");
        Choice choice35 = new Choice("Suivre la lumière", 33);
        Choice choice36 = new Choice("Explorer les environs", 34);
        section21.addChoice(choice35);
        section21.addChoice(choice36);
        sections.put(21, section21);

        // Section 22 - Continuer sans la pierre
        Section section22 = new Section(22,
            "L'obscurité est totale. Vous devez avancer à tâtons.");
        Choice choice37 = new Choice("Avancer prudemment", 35);
        Choice choice38 = new Choice("Appeler à l'aide", 36);
        section22.addChoice(choice37);
        section22.addChoice(choice38);
        sections.put(22, section22);

        // Section 23 - Se cacher
        Section section23 = new Section(23,
            "Vous vous cachez derrière un pilier. Des gardes passent sans vous voir.");
        Choice choice39 = new Choice("Attendre", 37);
        Choice choice40 = new Choice("Les suivre", 38);
        section23.addChoice(choice39);
        section23.addChoice(choice40);
        sections.put(23, section23);

        // Section 24 - Avancer rapidement
        Section section24 = new Section(24,
            "Vos pas résonnent dans le couloir. Un garde vous repère !");
        Choice choice41 = new Choice("Combattre", 39);
        choice41.setCombat(9, 7);
        Choice choice42 = new Choice("Fuir", 40);
        section24.addChoice(choice41);
        section24.addChoice(choice42);
        sections.put(24, section24);

        // Section 25 - Continuer avec la clé en or
        Section section25 = new Section(25,
            "La clé en or brille plus intensément. Vous approchez d'une porte dorée.");
        Choice choice43 = new Choice("Utiliser la clé", 41);
        Choice choice44 = new Choice("Examiner la porte", 42);
        section25.addChoice(choice43);
        section25.addChoice(choice44);
        sections.put(25, section25);

        // Section 26 - Prendre l'or des gardes
        Section section26 = new Section(26,
            "Vous empochez l'or des gardes. Un bruit de pas approche !");
        Choice choice45 = new Choice("Se cacher", 43);
        Choice choice46 = new Choice("Fuir", 44);
        section26.addChoice(choice45);
        section26.addChoice(choice46);
        sections.put(26, section26);

        // Section 27 - Continuer après le combat
        Section section27 = new Section(27,
            "Le couloir s'élargit et vous mène à une salle de trésor.");
        Choice choice47 = new Choice("Explorer la salle", 45);
        Choice choice48 = new Choice("Continuer", 46);
        section27.addChoice(choice47);
        section27.addChoice(choice48);
        sections.put(27, section27);

        // Section 28 - Continuer après être passé discrètement
        Section section28 = new Section(28,
            "Vous arrivez dans une salle de repos des gardes. Elle est vide pour le moment.");
        Choice choice49 = new Choice("Fouiller la salle", 47);
        Choice choice50 = new Choice("Continuer", 48);
        section28.addChoice(choice49);
        section28.addChoice(choice50);
        sections.put(28, section28);

        // Section 29 - Accepter la faveur du cuisinier
        Section section29 = new Section(29,
            "Le cuisinier vous demande de livrer un repas au garde de la tour supérieure.");
        Choice choice51 = new Choice("Accepter la mission", 49);
        Choice choice52 = new Choice("Refuser", 50);
        section29.addChoice(choice51);
        section29.addChoice(choice52);
        sections.put(29, section29);

        // Section 30 - Refuser la faveur du cuisinier
        Section section30 = new Section(30,
            "Le cuisinier vous regarde avec méfiance et vous chasse de la cuisine.");
        Choice choice53 = new Choice("Partir", 51);
        Choice choice54 = new Choice("Insister", 52);
        section30.addChoice(choice53);
        section30.addChoice(choice54);
        sections.put(30, section30);

        // Ajoutez d'autres sections ici...
    }

    public void start() {
        System.out.println("=== LA TOUR DE CRISTAL ===");
        System.out.println("Bienvenue dans cette aventure !");
        
        while (true) {
            Section current = sections.get(currentSection);
            if (current == null) {
                System.out.println("Fin de l'aventure !");
                break;
            }

            current.display();
            System.out.println("\nVotre état :");
            System.out.println("Endurance : " + player.getEndurance());
            System.out.println("Habilité au combat : " + player.getCombatSkill());
            System.out.println("Or : " + player.getGold());
            System.out.println("Repas : " + player.getMeals());
            System.out.println("Potions : " + player.getPotions());

            System.out.print("\nVotre choix (1-" + current.getChoices().size() + ") : ");
            int choice = scanner.nextInt();
            
            if (choice < 1 || choice > current.getChoices().size()) {
                System.out.println("Choix invalide !");
                continue;
            }

            Choice selectedChoice = current.getChoices().get(choice - 1);
            
            if (selectedChoice.requiresCombat()) {
                if (!handleCombat(selectedChoice)) {
                    System.out.println("Vous avez été vaincu !");
                    break;
                }
            }

            if (selectedChoice.requiresItem()) {
                if (!player.hasItem(selectedChoice.getRequiredItem())) {
                    System.out.println("Vous n'avez pas l'objet requis !");
                    continue;
                }
            }

            if (selectedChoice.requiresGold()) {
                if (player.getGold() < selectedChoice.getRequiredGold()) {
                    System.out.println("Vous n'avez pas assez d'or !");
                    continue;
                }
                player.addGold(-selectedChoice.getRequiredGold());
            }

            currentSection = selectedChoice.getNextSection();
        }
    }

    private boolean handleCombat(Choice choice) {
        int enemyCombatSkill = choice.getEnemyCombatSkill();
        int enemyEndurance = choice.getEnemyEndurance();
        
        System.out.println("\n=== COMBAT ===");
        System.out.println("Ennemi - Habilité au combat : " + enemyCombatSkill + ", Endurance : " + enemyEndurance);
        
        while (enemyEndurance > 0 && player.getEndurance() > 0) {
            // Tour du joueur
            int playerRoll = random.nextInt(6) + 1 + player.getCombatSkill();
            int enemyRoll = random.nextInt(6) + 1 + enemyCombatSkill;
            
            System.out.println("\nVotre jet : " + playerRoll);
            System.out.println("Jet de l'ennemi : " + enemyRoll);
            
            if (playerRoll > enemyRoll) {
                enemyEndurance -= 2;
                System.out.println("Vous infligez 2 points de dégâts !");
            } else if (enemyRoll > playerRoll) {
                player.addEndurance(-2);
                System.out.println("Vous subissez 2 points de dégâts !");
            } else {
                System.out.println("Égalité ! Aucun dégât n'est infligé.");
            }
            
            System.out.println("Votre endurance : " + player.getEndurance());
            System.out.println("Endurance de l'ennemi : " + enemyEndurance);
            
            if (player.getEndurance() <= 0) {
                return false;
            }
        }
        
        return true;
    }
} 