package src.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Game {
    private Player player;
    private Map<Integer, Section> sections;
    private int currentSection;
    private Random random;

    public Game() {
        this.player = new Player();
        this.sections = new HashMap<>();
        this.currentSection = 1;
        this.random = new Random();
        initializeSections();
    }

    public Player getPlayer() {
        return player;
    }

    public Section getCurrentSection() {
        return sections.get(currentSection);
    }

    public void setCurrentSection(int sectionNumber) {
        this.currentSection = sectionNumber;
    }

    public boolean isGameOver() {
        return player.getEndurance() <= 0 || sections.get(currentSection) == null;
    }

    public boolean executeCombat(Choice choice) {
        int enemyCombatSkill = choice.getEnemyCombatSkill();
        int enemyEndurance = choice.getEnemyEndurance();
        
        while (enemyEndurance > 0 && player.getEndurance() > 0) {
            // Tour du joueur
            int playerRoll = random.nextInt(6) + 1 + player.getCombatSkill();
            int enemyRoll = random.nextInt(6) + 1 + enemyCombatSkill;
            
            if (playerRoll > enemyRoll) {
                enemyEndurance -= 2;
            } else if (enemyRoll > playerRoll) {
                player.addEndurance(-2);
            }
            
            if (player.getEndurance() <= 0) {
                return false;
            }
        }
        
        return true;
    }

    private void initializeSections() {
        // Section 1 - Début de l'aventure
        Section section1 = new Section(1, 
            "Vous vous réveillez dans une cellule sombre et humide de la Tour de Cristal. " +
            "La lumière filtre à travers une petite fenêtre grillagée. Vous êtes le Seigneur Kai, Loup Solitaire, " +
            "capturé lors d'une mission d'espionnage pour le roi du Sommerlund. " +
            "Vous devez vous échapper et découvrir les secrets de cette tour mystérieuse avant qu'il ne soit trop tard.");
        
        Choice choice1 = new Choice("Examiner la cellule", 2);
        Choice choice2 = new Choice("Méditer et utiliser vos pouvoirs de Seigneur Kai", 3);
        section1.addChoice(choice1);
        section1.addChoice(choice2);
        sections.put(1, section1);

        // Section 2 - Examiner la cellule
        Section section2 = new Section(2,
            "En examinant minutieusement votre cellule, vous remarquez une pierre descellée près du sol. " +
            "Derrière celle-ci se trouve une vieille clé rouillée, probablement oubliée par un prisonnier précédent. " +
            "Vous remarquez également une petite fissure dans le mur opposé.");
        Choice choice3 = new Choice("Prendre la clé", 4);
        Choice choice4 = new Choice("Examiner la fissure", 5);
        section2.addChoice(choice3);
        section2.addChoice(choice4);
        sections.put(2, section2);

        // Section 3 - Méditer et utiliser vos pouvoirs de Seigneur Kai
        Section section3 = new Section(3,
            "Vous vous asseyez en position de méditation et faites appel à vos pouvoirs Kai. " +
            "Grâce à votre sixième sens, vous détectez la présence d'un garde qui s'approche de votre cellule. " +
            "Il semble porter quelque chose d'important.");
        Choice choice5 = new Choice("Vous préparer à neutraliser le garde", 6);
        choice5.setCombat(7, 6);
        Choice choice6 = new Choice("Feindre l'inconscience", 7);
        section3.addChoice(choice5);
        section3.addChoice(choice6);
        sections.put(3, section3);

        // Section 4 - Prendre la clé
        Section section4 = new Section(4,
            "Vous récupérez la clé rouillée et la cachez dans votre tunique. Elle pourrait servir à ouvrir " +
            "la porte de votre cellule ou peut-être un autre verrou dans la tour. Vous entendez des pas dans le couloir.");
        Choice choice7 = new Choice("Utiliser la clé sur la porte de la cellule", 8);
        Choice choice8 = new Choice("Vous cacher et attendre", 9);
        section4.addChoice(choice7);
        section4.addChoice(choice8);
        sections.put(4, section4);

        // Section 5 - Examiner la fissure
        Section section5 = new Section(5,
            "La fissure est étroite mais profonde. En regardant à l'intérieur, vous apercevez une faible lueur bleutée. " +
            "Quelque chose semble briller de l'autre côté. En tendant votre bras, vous pourriez peut-être l'atteindre.");
        Choice choice9 = new Choice("Tenter d'atteindre l'objet lumineux", 10);
        Choice choice10 = new Choice("Retourner examiner le reste de la cellule", 2);
        section5.addChoice(choice9);
        section5.addChoice(choice10);
        sections.put(5, section5);

        // Section 6 - Combat avec le garde
        Section section6 = new Section(6,
            "Le garde ouvre la porte de votre cellule et vous bondissez sur lui, prenant avantage de l'effet de surprise. " +
            "Après un combat bref mais intense, vous parvenez à le maîtriser. Sur son corps, vous trouvez une potion de guérison.");
        player.addPotion();
        Choice choice11 = new Choice("Prendre son équipement", 11);
        Choice choice12 = new Choice("Quitter immédiatement la cellule", 12);
        section6.addChoice(choice11);
        section6.addChoice(choice12);
        sections.put(6, section6);

        // Section 7 - Feindre l'inconscience
        Section section7 = new Section(7,
            "Vous vous allongez sur la paillasse et feignez l'inconscience. Le garde entre avec précaution, portant un plateau " +
            "de nourriture. Il le dépose près de vous et s'apprête à repartir.");
        Choice choice13 = new Choice("Bondir sur le garde", 13);
        choice13.setCombat(6, 5);
        Choice choice14 = new Choice("Attendre son départ puis manger", 14);
        section7.addChoice(choice13);
        section7.addChoice(choice14);
        sections.put(7, section7);

        // Section 8 - Utiliser la clé
        Section section8 = new Section(8,
            "La clé rouillée s'insère dans la serrure mais tourne avec difficulté. Après quelques tentatives, " +
            "vous parvenez à déverrouiller la porte qui s'ouvre avec un grincement sinistre. Le couloir est sombre et désert.");
        Choice choice15 = new Choice("Aller à gauche", 15);
        Choice choice16 = new Choice("Aller à droite", 16);
        section8.addChoice(choice15);
        section8.addChoice(choice16);
        sections.put(8, section8);

        // Section 9 - Vous cacher et attendre
        Section section9 = new Section(9,
            "Vous vous cachez dans l'ombre et attendez. Un garde passe devant votre cellule sans s'arrêter. " +
            "Il porte une torche qui éclaire brièvement l'intérieur. Vous remarquez que la serrure de votre porte semble ancienne.");
        Choice choice17 = new Choice("Essayer la clé sur la porte", 8);
        Choice choice18 = new Choice("Examiner davantage la cellule", 17);
        section9.addChoice(choice17);
        section9.addChoice(choice18);
        sections.put(9, section9);

        // Section 10 - Atteindre l'objet lumineux
        Section section10 = new Section(10,
            "En tendant le bras au maximum dans la fissure, vos doigts effleurent un objet froid et lisse. " +
            "Avec patience, vous parvenez à le faire glisser jusqu'à vous. C'est une pierre de cristal bleutée, " +
            "un artefact magique qui brille d'une lumière intérieure.");
        Choice choice19 = new Choice("Garder la pierre de cristal", 18);
        Choice choice20 = new Choice("Examiner la pierre de plus près", 19);
        section10.addChoice(choice19);
        section10.addChoice(choice20);
        sections.put(10, section10);

        // Section 11 - Prendre l'équipement du garde
        Section section11 = new Section(11,
            "Vous dépouillez rapidement le garde. Vous récupérez une épée courte, une bourse contenant 15 couronnes d'or, " +
            "et une clé en fer qui semble plus récente que celle que vous avez trouvée.");
        player.addGold(15);
        player.addCombatSkill(2); // L'épée améliore vos capacités de combat
        Choice choice21 = new Choice("Quitter la cellule et aller à gauche", 15);
        Choice choice22 = new Choice("Quitter la cellule et aller à droite", 16);
        section11.addChoice(choice21);
        section11.addChoice(choice22);
        sections.put(11, section11);

        // Section 12 - Quitter immédiatement la cellule
        Section section12 = new Section(12,
            "Ne voulant pas perdre de temps, vous quittez votre cellule et vous retrouvez dans un couloir faiblement éclairé. " +
            "Des torches vacillantes projettent des ombres inquiétantes sur les murs de pierre.");
        Choice choice23 = new Choice("Aller à gauche", 15);
        Choice choice24 = new Choice("Aller à droite", 16);
        section12.addChoice(choice23);
        section12.addChoice(choice24);
        sections.put(12, section12);

        // Section 13 - Bondir sur le garde
        Section section13 = new Section(13,
            "Vous bondissez sur le garde qui, surpris, n'a pas le temps de se défendre efficacement. " +
            "Après un bref combat, vous le neutralisez. Sur lui, vous trouvez une clé en fer et un repas.");
        player.addMeal();
        Choice choice25 = new Choice("Quitter la cellule avec précaution", 12);
        Choice choice26 = new Choice("Fouiller davantage le garde", 20);
        section13.addChoice(choice25);
        section13.addChoice(choice26);
        sections.put(13, section13);

        // Section 14 - Attendre le départ du garde puis manger
        Section section14 = new Section(14,
            "Vous attendez que le garde quitte votre cellule. Il verrouille la porte derrière lui. " +
            "Le repas qu'il a apporté contient un morceau de pain, de la viande séchée et une petite gourde d'eau. " +
            "Ce n'est pas délicieux, mais cela vous redonne des forces.");
        player.addEndurance(2); // Le repas vous redonne un peu d'endurance
        Choice choice27 = new Choice("Examiner à nouveau la cellule", 2);
        Choice choice28 = new Choice("Vous reposer un moment", 21);
        section14.addChoice(choice27);
        section14.addChoice(choice28);
        sections.put(14, section14);

        // Section 15 - Aller à gauche
        Section section15 = new Section(15,
            "Le couloir s'élargit progressivement et débouche sur une salle de garde. Deux hommes en armure légère " +
            "sont attablés, jouant aux dés. Ils ne vous ont pas encore remarqué.");
        Choice choice29 = new Choice("Les attaquer par surprise", 22);
        choice29.setCombat(8, 8);
        Choice choice30 = new Choice("Tenter de passer discrètement", 23);
        section15.addChoice(choice29);
        section15.addChoice(choice30);
        sections.put(15, section15);

        // Section 16 - Aller à droite
        Section section16 = new Section(16,
            "Le couloir devient de plus en plus étroit et descend légèrement. L'air est chargé d'odeurs de cuisine. " +
            "Vous arrivez bientôt devant une porte entrouverte d'où s'échappent des effluves de nourriture.");
        Choice choice31 = new Choice("Entrer dans la cuisine", 24);
        Choice choice32 = new Choice("Continuer plus loin dans le couloir", 25);
        section16.addChoice(choice31);
        section16.addChoice(choice32);
        sections.put(16, section16);

        // Section 17 - Examiner davantage la cellule
        Section section17 = new Section(17,
            "En examinant plus attentivement votre cellule, vous remarquez des inscriptions gravées sur le mur, " +
            "près de votre paillasse. Ce sont des notes laissées par un prisonnier précédent: " +
            "'Méfiez-vous de l'eau du puits central. Le seigneur Zahda l'a corrompue avec sa magie noire.'");
        Choice choice33 = new Choice("Essayer la clé sur la porte", 8);
        Choice choice34 = new Choice("Examiner la fissure dans le mur", 5);
        section17.addChoice(choice33);
        section17.addChoice(choice34);
        sections.put(17, section17);

        // Section 18 - Garder la pierre de cristal
        Section section18 = new Section(18,
            "Vous glissez la pierre de cristal dans votre poche. Elle émet une douce chaleur et semble vibrer légèrement. " +
            "Vous sentez qu'elle possède un pouvoir particulier qui pourrait vous être utile.");
        player.addSpecialItem(); // La pierre compte comme un objet spécial
        Choice choice35 = new Choice("Examiner la porte de la cellule", 26);
        Choice choice36 = new Choice("Vous reposer un moment", 21);
        section18.addChoice(choice35);
        section18.addChoice(choice36);
        sections.put(18, section18);

        // Section 19 - Examiner la pierre de plus près
        Section section19 = new Section(19,
            "En examinant la pierre attentivement, vous remarquez qu'elle pulse au rythme de votre cœur. " +
            "Soudain, une vision vous submerge: vous voyez un homme en robe noire devant un bassin de cristal, " +
            "accomplissant un rituel obscur. C'est Zahda, le maître de la tour!");
        Choice choice37 = new Choice("Garder la pierre de cristal", 18);
        Choice choice38 = new Choice("Poser la pierre et essayer la clé sur la porte", 8);
        section19.addChoice(choice37);
        section19.addChoice(choice38);
        sections.put(19, section19);

        // Section 20 - Fouiller davantage le garde
        Section section20 = new Section(20,
            "En fouillant plus minutieusement le garde, vous trouvez un petit parchemin caché dans sa botte. " +
            "Il contient un plan sommaire des premiers niveaux de la tour. Vous voyez qu'un escalier montant se trouve " +
            "au bout du couloir de droite.");
        Choice choice39 = new Choice("Quitter la cellule et aller à gauche", 15);
        Choice choice40 = new Choice("Quitter la cellule et aller à droite", 16);
        section20.addChoice(choice39);
        section20.addChoice(choice40);
        sections.put(20, section20);

        // Section 21 - Se reposer
        Section section21 = new Section(21,
            "Vous décidez de vous reposer un moment pour récupérer vos forces. La Tour de Cristal est un lieu dangereux " +
            "et vous aurez besoin de toute votre énergie. Après une courte méditation Kai, vous vous sentez revigoré.");
        player.addEndurance(3); // La méditation Kai vous permet de récupérer de l'endurance
        Choice choice41 = new Choice("Examiner la porte de votre cellule", 26);
        Choice choice42 = new Choice("Examiner la fissure dans le mur", 5);
        section21.addChoice(choice41);
        section21.addChoice(choice42);
        sections.put(21, section21);

        // Section 22 - Attaquer les gardes
        Section section22 = new Section(22,
            "Vous bondissez sur les gardes qui, surpris par votre attaque soudaine, renversent la table de jeu. " +
            "Après un combat acharné, vous parvenez à les neutraliser. La voie est maintenant libre.");
        Choice choice43 = new Choice("Fouiller la salle de garde", 27);
        Choice choice44 = new Choice("Continuer vers la porte au fond", 28);
        section22.addChoice(choice43);
        section22.addChoice(choice44);
        sections.put(22, section22);

        // Section 23 - Passer discrètement
        Section section23 = new Section(23,
            "Faisant appel à vos talents de furtivité Kai, vous vous glissez silencieusement le long du mur, " +
            "restant dans les ombres. Les gardes, absorbés par leur jeu, ne remarquent rien. " +
            "Vous atteignez une porte au fond de la salle.");
        Choice choice45 = new Choice("Ouvrir la porte", 28);
        Choice choice46 = new Choice("Revenir sur vos pas et prendre l'autre couloir", 16);
        section23.addChoice(choice45);
        section23.addChoice(choice46);
        sections.put(23, section23);

        // Section 24 - Entrer dans la cuisine
        Section section24 = new Section(24,
            "Vous entrez dans une vaste cuisine où s'affaire un homme corpulent. Il sursaute en vous voyant mais, " +
            "étrangement, ne semble pas alarmé. 'Encore un prisonnier qui s'échappe ? Bah, je ne suis pas payé pour faire le garde. " +
            "Tu veux manger quelque chose ?'");
        Choice choice47 = new Choice("Accepter son offre", 29);
        Choice choice48 = new Choice("Lui demander la sortie la plus proche", 30);
        section24.addChoice(choice47);
        section24.addChoice(choice48);
        sections.put(24, section24);

        // Section 25 - Continuer dans le couloir
        Section section25 = new Section(25,
            "Le couloir continue de descendre en colimaçon. L'air devient plus frais et humide. " +
            "Vous entendez un bruit d'eau qui coule. Bientôt, vous arrivez dans une grande salle circulaire " +
            "dominée par un puits central.");
        Choice choice49 = new Choice("Examiner le puits", 31);
        Choice choice50 = new Choice("Prendre la porte à l'est", 32);
        Choice choice51 = new Choice("Prendre la porte à l'ouest", 33);
        section25.addChoice(choice49);
        section25.addChoice(choice50);
        section25.addChoice(choice51);
        sections.put(25, section25);

        // Section 26 - Examiner la porte de la cellule
        Section section26 = new Section(26,
            "Vous examinez attentivement la porte de votre cellule. La serrure est ancienne mais solide. " +
            "Si vous avez récupéré une clé, vous pourriez essayer de l'utiliser.");
        Choice choice52 = new Choice("Essayer la clé rouillée", 8);
        Choice choice53 = new Choice("Tenter de forcer la serrure", 34);
        section26.addChoice(choice52);
        section26.addChoice(choice53);
        sections.put(26, section26);

        // Section 27 - Fouiller la salle de garde
        Section section27 = new Section(27,
            "Vous fouillez rapidement la salle de garde. Dans un coffre non verrouillé, vous découvrez une cotte de mailles légère, " +
            "20 pièces d'or et une potion de guérison. Ces objets vous seront certainement utiles.");
        player.addPotion();
        player.addGold(20);
        player.addEndurance(2); // La cotte de mailles améliore votre protection
        Choice choice54 = new Choice("Continuer vers la porte au fond", 28);
        section27.addChoice(choice54);
        sections.put(27, section27);

        // Section 28 - La porte au fond
        Section section28 = new Section(28,
            "La porte donne sur un escalier qui monte en spirale. D'après la construction, " +
            "vous devez vous trouver dans l'une des tours secondaires. En montant, vous pourriez accéder " +
            "aux étages supérieurs, mais le danger pourrait être plus grand.");
        Choice choice55 = new Choice("Monter l'escalier", 35);
        Choice choice56 = new Choice("Redescendre et prendre l'autre couloir", 16);
        section28.addChoice(choice55);
        section28.addChoice(choice56);
        sections.put(28, section28);

        // Section 29 - Accepter l'offre du cuisinier
        Section section29 = new Section(29,
            "Le cuisinier vous sert un copieux ragoût de viande et de légumes. C'est la meilleure nourriture " +
            "que vous ayez mangée depuis votre capture. En mangeant, vous discutez avec lui et apprenez qu'un rituel " +
            "important doit avoir lieu ce soir au sommet de la tour.");
        player.addMeal();
        player.addEndurance(4); // Le repas vous redonne des forces immédiatement
        Choice choice57 = new Choice("Lui demander plus d'informations sur le rituel", 36);
        Choice choice58 = new Choice("Le remercier et continuer votre chemin", 37);
        section29.addChoice(choice57);
        section29.addChoice(choice58);
        sections.put(29, section29);

        // Section 30 - Demander la sortie
        Section section30 = new Section(30,
            "Le cuisinier rit. 'La sortie ? Il n'y en a qu'une, et elle est bien gardée, mon ami. " +
            "Si j'étais toi, je chercherais plutôt un moyen d'atteindre les étages supérieurs. " +
            "La bibliothèque au troisième niveau contient peut-être des informations utiles.'");
        Choice choice59 = new Choice("Accepter un repas avant de partir", 29);
        Choice choice60 = new Choice("Le remercier et continuer votre chemin", 37);
        section30.addChoice(choice59);
        section30.addChoice(choice60);
        sections.put(30, section30);

        // Section finale - Le sommet de la Tour de Cristal
        Section sectionFinale = new Section(99,
            "Après de nombreuses épreuves et dangers, vous atteignez enfin le sommet de la Tour de Cristal. " +
            "Devant vous se trouve Zahda, le Mage Noir, préparant son rituel maléfique. Grâce à vos pouvoirs Kai " +
            "et aux connaissances acquises durant votre ascension, vous parvenez à déjouer ses plans et à neutraliser " +
            "le sorcier. La Tour de Cristal est libérée de son influence néfaste, et vous avez accompli votre mission " +
            "pour le Sommerlund. Cette victoire sera chantée dans les annales des Seigneurs Kai !");
        sections.put(99, sectionFinale);
    }
} 