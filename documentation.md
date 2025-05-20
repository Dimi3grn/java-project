# 🏰 La Tour de Cristal

<div align="center">
  
**Version 1.0** | **Java JDK 17+** | **JavaFX 17+** | **Licence YNOV PROJET**

**Une aventure interactive inspirée de la célèbre série "Loup Solitaire" de Joe Dever**

*Développé par Xerly, Romain, et Dimitri - YNOV B1 2025*

[Installation](#-installation) • 
[Utilisation](#-guide-dutilisation) • 
[Documentation](#-documentation-technique) • 
[Dépannage](#-dépannage)

</div>

---

## 📋 Table des matières

- [Présentation du projet](#-présentation-du-projet)
- [Installation](#-installation)
  - [Prérequis](#prérequis)
  - [Étapes d'installation](#étapes-dinstallation)
  - [Configuration de l'IDE](#configuration-de-lide)
  - [Exécution](#exécution)
- [Guide d'utilisation](#-guide-dutilisation)
  - [Interface principale](#interface-principale)
  - [Déroulement du jeu](#déroulement-du-jeu)
  - [Combats](#combats)
  - [Sauvegarde et chargement](#sauvegarde-et-chargement)
- [Documentation technique](#-documentation-technique)
  - [Architecture du projet](#architecture-du-projet)
  - [Comment ajouter un chapitre](#comment-ajouter-un-chapitre-au-jeu)
  - [Gestion des effets spéciaux](#gestion-des-effets-spéciaux)
  - [Système de sauvegarde](#système-de-sauvegarde)
- [Dépannage](#-dépannage)
- [Crédits et licence](#-crédits-et-licence)
- [Perspectives d'évolution](#-perspectives-dévolution)

---

## 🌟 Présentation du projet

### 📖 Le concept

"La Tour de Cristal" est une adaptation numérique du concept des livres-jeux "Livre Dont Vous Êtes Le Héros" (LDVELH), spécifiquement inspirée du livre 17 de la série Loup Solitaire de Joe Dever.

Dans ce type de jeu interactif, le joueur lit des paragraphes de texte décrivant une situation, puis choisit parmi plusieurs options celle qui lui semble la plus pertinente. Chaque choix mène à une nouvelle section de l'histoire, créant ainsi un parcours unique.

### 🧙‍♂️ L'univers

Le joueur incarne le Seigneur Kai, un héros du Sommerlund qui doit s'échapper de la mystérieuse Tour de Cristal et découvrir les sombres secrets du sorcier Zahda avant qu'il ne soit trop tard.

### 💻 Caractéristiques techniques

Notre implémentation propose deux interfaces distinctes :
- Une interface console avec mise en forme ANSI pour les couleurs et styles
- Une interface graphique moderne développée avec JavaFX

---

## 🚀 Installation

### Prérequis

> ⚠️ **Important :** Vérifiez que votre système répond à ces exigences avant de commencer l'installation.

- JDK 17 ou supérieur (JDK 24 recommandé)
- JavaFX SDK 17 ou supérieur
- Espace disque : environ 50 Mo
- IDE compatible Java (IntelliJ IDEA recommandé)

### Étapes d'installation

#### 1. Téléchargement et configuration de JavaFX

<details>
<summary>Cliquez pour afficher les instructions détaillées</summary>

1. Téléchargez JavaFX SDK depuis [le site officiel](https://openjfx.io/)
2. Décompressez l'archive dans un répertoire de votre choix
3. Notez le chemin complet vers le dossier `lib` de JavaFX :
   - Windows : `C:\chemin\vers\javafx-sdk\lib`
   - macOS/Linux : `/chemin/vers/javafx-sdk/lib`

</details>

#### 2. Récupération du projet

<details>
<summary>Via Git (recommandé)</summary>

```bash
git clone https://github.com/votre-utilisateur/la-tour-de-cristal.git
cd la-tour-de-cristal
```

</details>

<details>
<summary>Via téléchargement direct</summary>

1. Téléchargez l'archive du projet depuis [la page de release](https://github.com/votre-utilisateur/la-tour-de-cristal/releases)
2. Extrayez le contenu dans le dossier de votre choix
3. Naviguez vers le dossier extrait

</details>

### Configuration de l'IDE

<table>
<tr>
<td width="50%">

#### IntelliJ IDEA

1. Ouvrez IntelliJ IDEA
2. Sélectionnez `File > Open` et choisissez le dossier du projet
3. Allez dans `File > Project Structure > Libraries`
4. Cliquez sur le bouton `+` et sélectionnez `Java`
5. Naviguez vers le dossier `lib` de votre installation JavaFX
6. Sélectionnez tous les fichiers JAR et cliquez sur `OK`
7. Configurez une Run Configuration :
   - Main class : `src.Main`
   - VM options : 
     ```
     --module-path "/chemin/vers/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml
     ```
   - Remplacez le chemin par celui noté précédemment

</td>
<td>

#### Eclipse

1. Ouvrez Eclipse
2. Sélectionnez `File > Import > General > Existing Projects into Workspace`
3. Sélectionnez le dossier du projet et cliquez sur `Finish`
4. Faites un clic droit sur le projet > `Build Path > Configure Build Path`
5. Dans l'onglet `Libraries`, cliquez sur `Add External JARs`
6. Naviguez vers le dossier `lib` de JavaFX et sélectionnez tous les JAR
7. Créez une nouvelle configuration de lancement :
   - Type : Java Application
   - Main class : `src.Main`
   - Arguments > VM arguments :
     ```
     --module-path "/chemin/vers/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml
     ```

</td>
</tr>
</table>

### Exécution

<details>
<summary>Via l'IDE</summary>

- Utilisez le bouton ▶️ `Run` dans votre IDE pour lancer l'application

</details>

<details>
<summary>Via le fichier batch (Windows)</summary>

1. Ouvrez le fichier `run.bat` dans un éditeur de texte
2. Modifiez la variable `PATH_TO_FX` pour qu'elle pointe vers votre installation JavaFX :
   ```batch
   set PATH_TO_FX=C:\chemin\vers\javafx-sdk\lib
   ```
3. Sauvegardez le fichier et double-cliquez dessus pour exécuter l'application

</details>

<details>
<summary>Via la ligne de commande</summary>

```bash
# Windows
java --module-path "C:\chemin\vers\javafx-sdk\lib" --add-modules javafx.controls,javafx.fxml -cp ./out/production/projet src.Main

# macOS / Linux
java --module-path "/chemin/vers/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml -cp ./out/production/projet src.Main
```

</details>

---

## 📱 Guide d'utilisation

### Interface principale

Au lancement, vous arrivez sur le menu principal avec trois options :
- **Nouvelle partie** : Commencez une nouvelle aventure
- **Charger une partie** : Reprenez une partie sauvegardée
- **Quitter** : Fermez l'application

### Déroulement du jeu

1. **Lecture du texte** : Le texte de la section courante est affiché, décrivant la situation
2. **Prise de décision** : Les choix disponibles sont présentés sous forme de boutons
3. **Avancement** : Cliquez sur un choix pour progresser dans l'histoire
4. **Visualisation des statistiques** : Vos statistiques sont toujours visibles en bas de l'écran
   - **Endurance** : Points de vie du personnage
   - **Combat** : Compétence au combat
   - **Or** : Monnaie pour les achats
   - **Inventaire** : Objets possédés (potions, repas, objets spéciaux)

### Combats

Les combats dans La Tour de Cristal suivent un système simple mais stratégique :

1. **Initiative** : Vous et votre adversaire lancez un dé virtuel à chaque tour
2. **Comparaison** : Votre jet (dé + habileté au combat) est comparé à celui de l'ennemi
3. **Résultat** : Le combattant avec le score le plus élevé inflige 2 points de dégâts
4. **Options tactiques** : Possibilité d'utiliser des potions ou de fuir (si permis)
5. **Fin du combat** : Le combat se termine quand l'un des combattants n'a plus d'endurance

### Sauvegarde et chargement

La Tour de Cristal permet de sauvegarder votre progression à tout moment :

1. **Sauvegarde** :
   - Cliquez sur "Sauvegarder" dans le menu en haut à droite
   - Entrez un nom pour votre sauvegarde
   - Confirmez pour créer le fichier de sauvegarde

2. **Chargement** :
   - Cliquez sur "Charger" dans le menu en haut à droite ou dans le menu principal
   - Sélectionnez la sauvegarde à charger dans la liste
   - Confirmez pour reprendre votre aventure là où vous l'aviez laissée

---

## 📘 Documentation technique

### Architecture du projet

Le projet est développé en Java, suivant une architecture Modèle-Vue-Contrôleur (MVC) pour séparer la logique métier, l'interface utilisateur et la gestion des interactions.

#### Principes MVC appliqués

L'architecture MVC appliquée dans "La Tour de Cristal" respecte les principes suivants:

1. **Séparation des responsabilités**: Chaque composant a un rôle spécifique
2. **Faible couplage**: Les composants peuvent évoluer indépendamment
3. **Haute cohésion**: Chaque classe a une responsabilité unique
4. **Réutilisabilité**: Les modèles sont indépendants de l'interface utilisateur

L'implémentation permet de supporter deux interfaces (console et graphique) tout en conservant la même logique de jeu.

#### Structure des packages

```
src/
├── Main.java               # Point d'entrée de l'application
├── controller/             # Contrôleurs pour la logique d'application
│   ├── CombatController.java
│   ├── GameController.java
│   ├── JavaFXGameController.java
├── model/                  # Classes du modèle de données
│   ├── Choice.java
│   ├── Enemy.java
│   ├── Game.java
│   ├── Player.java
│   ├── SaveManager.java
│   ├── Section.java
├── view/                   # Classes pour l'interface utilisateur
│   ├── ConsoleUtils.java
│   ├── GameView.java
│   ├── JavaFXView.java
└── resources/              # Ressources externes
    ├── css/
    │   └── style.css
    ├── images/
        └── crystal_tower_icon.png
```

#### Description des composants principaux

<table>
<tr>
<th>Composant</th>
<th>Classe</th>
<th>Description</th>
</tr>
<tr>
<td rowspan="5"><strong>Modèle</strong></td>
<td><code>Game</code></td>
<td>Classe centrale qui orchestre le jeu. Maintient la référence à la section courante et au joueur. Contient la méthode initializeSections() qui définit tout le contenu narratif.</td>
</tr>
<tr>
<td><code>Player</code></td>
<td>Représente le personnage du joueur avec ses attributs (endurance, habileté au combat) et son inventaire (or, repas, potions, objets spéciaux). Gère également les modifications d'état du personnage.</td>
</tr>
<tr>
<td><code>Section</code></td>
<td>Unité narrative fondamentale du jeu. Chaque section possède un numéro unique, un texte descriptif et une liste de choix possibles. Implémente le pattern Composite avec Choice.</td>
</tr>
<tr>
<td><code>Choice</code></td>
<td>Représente un choix possible dans une section. Peut avoir divers effets (combat, gain/perte d'or, objets, santé) et conditions (objet requis, or requis). Contient le lien vers la section suivante.</td>
</tr>
<tr>
<td><code>Enemy</code></td>
<td>Modélise un adversaire avec ses caractéristiques (nom, description, habileté au combat, endurance). Inclut une Factory pour créer différents types d'ennemis prédéfinis.</td>
</tr>
<tr>
<td rowspan="3"><strong>Vue</strong></td>
<td><code>JavaFXView</code></td>
<td>Interface graphique JavaFX. Gère l'affichage du texte, des choix, des statistiques et des menus. Communique avec le contrôleur pour transmettre les actions utilisateur.</td>
</tr>
<tr>
<td><code>GameView</code></td>
<td>Interface console textuelle. Utilise ANSI pour les couleurs et styles. Fournit des méthodes pour afficher les sections, les choix et les messages du jeu.</td>
</tr>
<tr>
<td><code>ConsoleUtils</code></td>
<td>Utilitaires pour l'affichage en console (couleurs, formatage, bordures, styles). Contient des constantes et méthodes pour uniformiser l'affichage textuel.</td>
</tr>
<tr>
<td rowspan="3"><strong>Contrôleur</strong></td>
<td><code>JavaFXGameController</code></td>
<td>Contrôleur spécifique à l'interface JavaFX. Gère les événements utilisateur et met à jour la vue et le modèle en conséquence. Implémente le pattern Observer.</td>
</tr>
<tr>
<td><code>GameController</code></td>
<td>Contrôleur pour l'interface console. Interprète les commandes textuelles et coordonne les interactions entre le modèle et la vue console.</td>
</tr>
<tr>
<td><code>CombatController</code></td>
<td>Gère la logique spécifique des combats. Calcule les résultats des jets de dés, met à jour l'état des combattants et détermine l'issue des affrontements.</td>
</tr>
</table>

#### Patterns de conception utilisés

Le projet implémente plusieurs patterns de conception pour une meilleure architecture:

1. **MVC (Modèle-Vue-Contrôleur)**: Architecture globale séparant données, présentation et logique applicative
2. **Factory Method**: Dans `Enemy.EnemyFactory` pour créer différents types d'ennemis prédéfinis
3. **Singleton**: Pour la classe `Game` qui représente l'instance unique du jeu en cours
4. **Composite**: Structure arborescente de `Section` et `Choice` pour construire l'histoire
5. **Strategy**: Pour les différentes interfaces utilisateur qui peuvent être interchangées
6. **Observer**: Pour la communication entre les contrôleurs et les vues
7. **Command**: Pour l'encapsulation des actions utilisateur (choix, combats)

### Comment ajouter un chapitre au jeu

Pour enrichir l'histoire de La Tour de Cristal, vous pouvez ajouter de nouvelles sections. Cette opération se fait en modifiant la méthode `initializeSections()` dans la classe `Game.java`.

#### Étape 1 : Comprendre la structure d'une section

Chaque section comprend trois éléments essentiels :
- Un **numéro unique** qui l'identifie
- Un **texte descriptif** qui raconte la situation
- Un ou plusieurs **choix** menant à d'autres sections

#### Règles de numérotation

Pour une meilleure organisation du code et éviter les conflits, suivez ces conventions:

- **1-99**: Sections principales du scénario originel
- **100-199**: Sections additionnelles pour de nouvelles zones de la Tour
- **200-299**: Sections pour quêtes secondaires 
- **300-399**: Sections liées à des personnages spécifiques
- **400-499**: Sections spéciales (fins alternatives, événements rares)
- **500-599**: Réservées pour les extensions futures

#### Bonnes pratiques pour l'écriture des sections

1. **Cohérence narrative**: Respectez le style et l'univers du jeu
2. **Paragraphes courts**: Privilégiez des descriptions concises mais évocatrices
3. **Choix équilibrés**: Offrez des alternatives variées avec des conséquences distinctes
4. **Vérification des liens**: Assurez-vous que tous les chemins mènent quelque part
5. **Évitez les impasses**: Une section ne doit jamais être sans issue (sauf fins spécifiques)
6. **Test des parcours**: Vérifiez vos nouvelles sections en les testant intégralement

#### Étape 2 : Créer une nouvelle section

Ouvrez le fichier `src/model/Game.java` et localisez la méthode `initializeSections()`. Ajoutez votre code à la fin de cette méthode, avant la dernière accolade.

```java
// Création d'une nouvelle section (numéro 120 par exemple)
Section section120 = new Section(120, 
    "La salle dans laquelle vous pénétrez est baignée d'une étrange lueur verdâtre. "
    + "Des cristaux phosphorescents sont incrustés dans les murs et le plafond, "
    + "illuminant l'espace d'une clarté surnaturelle. Au centre de la pièce, "
    + "un piédestal supporte une mystérieuse sphère d'obsidienne.");

// Ajout du premier choix (menant à la section 121)
Choice choice120_1 = new Choice("Examiner la sphère d'obsidienne", 121);
section120.addChoice(choice120_1);

// Ajout du second choix (menant à la section 122)
Choice choice120_2 = new Choice("Ignorer la sphère et explorer le reste de la salle", 122);
section120.addChoice(choice120_2);

// Ajout du troisième choix (menant à la section 123)
Choice choice120_3 = new Choice("Quitter immédiatement cette salle inquiétante", 123);
section120.addChoice(choice120_3);

// Enregistrement de la section dans la map des sections
sections.put(120, section120);
```

#### Étape 3 : Connecter votre section à l'histoire existante

Pour que votre section soit accessible, vous devez la connecter au reste de l'histoire. Identifiez une section existante où vous souhaitez ajouter un lien vers votre nouvelle section, puis modifiez ses choix.

```java
// Dans une section existante (par exemple la section 50)
// Ajoutez un nouveau choix qui mène à votre section
Choice choiceToNewSection = new Choice("Emprunter le couloir mystérieux", 120);
section50.addChoice(choiceToNewSection);
```

#### Étape 4 : Créer les sections de destination

Assurez-vous de créer toutes les sections référencées par vos choix (121, 122, 123 dans notre exemple).

```java
// Section 121 - Examiner la sphère
Section section121 = new Section(121,
    "En vous approchant de la sphère d'obsidienne, vous remarquez des inscriptions "
    + "gravées sur sa surface. Les symboles anciens luisent faiblement et semblent "
    + "réagir à votre présence. Une sensation étrange vous parcourt lorsque vous "
    + "tendez la main pour effleurer l'artefact.");

// Ajout des choix pour la section 121
Choice choice121_1 = new Choice("Toucher la sphère", 124);
Choice choice121_2 = new Choice("Essayer de déchiffrer les inscriptions", 125);
Choice choice121_3 = new Choice("Reculer prudemment", 122);
section121.addChoice(choice121_1);
section121.addChoice(choice121_2);
section121.addChoice(choice121_3);
sections.put(121, section121);

// Section 122 - Explorer la salle
Section section122 = new Section(122,
    "En explorant la salle, vous découvrez une étagère remplie de parchemins anciens "
    + "et de petits objets cristallins. L'un des murs présente une fresque élaborée "
    + "montrant une tour s'élevant au-dessus de nuages tourbillonnants, avec des "
    + "silhouettes encapuchonnées autour, les bras levés dans un geste rituel.");

// Ajout des choix pour la section 122
Choice choice122_1 = new Choice("Examiner les parchemins", 126);
Choice choice122_2 = new Choice("Étudier la fresque", 127);
Choice choice122_3 = new Choice("Retourner vers la sphère", 121);
section122.addChoice(choice122_1);
section122.addChoice(choice122_2);
section122.addChoice(choice122_3);
sections.put(122, section122);

// Section 123 - Quitter la salle
Section section123 = new Section(123,
    "Vous décidez de ne pas prendre de risques et quittez rapidement cette salle inquiétante. "
    + "Le couloir continue sur plusieurs mètres avant de bifurquer. À droite, vous percevez "
    + "un léger courant d'air frais, tandis qu'à gauche, une faible lueur orangée suggère "
    + "la présence de torches.");

// Ajout des choix pour la section 123
Choice choice123_1 = new Choice("Prendre à droite, vers l'air frais", 128);
Choice choice123_2 = new Choice("Prendre à gauche, vers la lueur", 129);
Choice choice123_3 = new Choice("Revenir sur vos pas", 120);
section123.addChoice(choice123_1);
section123.addChoice(choice123_2);
section123.addChoice(choice123_3);
sections.put(123, section123);
```

#### Étape 5 : Tester vos nouvelles sections

Après avoir ajouté vos sections, lancez le jeu et testez-les pour vous assurer que :
- Les transitions entre sections fonctionnent correctement
- Le texte s'affiche correctement
- Les choix mènent aux bonnes destinations
- L'histoire reste cohérente

### Gestion des effets spéciaux

Pour rendre votre histoire plus interactive, vous pouvez enrichir vos choix avec différents effets. Voici comment les implémenter :

#### Combats

Les combats sont un élément central de l'aventure. Vous pouvez créer des affrontements avec différents types d'ennemis.

```java
// Syntaxe : setCombat(nomEnnemi, habiletéCombat, endurance, possibilitéFuite)
Choice combatChoice = new Choice("Affronter le gardien des cristaux", 130);
combatChoice.setCombat("Gardien des Cristaux", 9, 12, false);
section120.addChoice(combatChoice);
```

#### Transactions et trésors

Gérez l'économie du jeu avec des coûts et des récompenses en or.

```java
// Choix qui nécessite de l'or (achat)
Choice buyChoice = new Choice("Acheter l'amulette au marchand (15 pièces d'or)", 140);
buyChoice.setRequiredGold(15);  // Coûte 15 pièces d'or

// Choix qui donne de l'or (trésor)
Choice treasureChoice = new Choice("Ouvrir le coffre secret", 141);
treasureChoice.setGivesGold(25);  // Donne 25 pièces d'or
```

#### Santé et dégâts

Ajoutez des risques et des opportunités de récupération.

```java
// Choix qui inflige des dégâts
Choice trapChoice = new Choice("Traverser le couloir piégé", 150);
trapChoice.setDamagesPlayer(4);  // Inflige 4 points de dégâts

// Choix qui soigne
Choice healChoice = new Choice("Boire à la fontaine magique", 151);
healChoice.setHeals(6);  // Restaure 6 points d'endurance
```

#### Objets et ressources

Gérez l'inventaire du joueur avec différents types d'objets.

```java
// Donner des objets
Choice findPotionChoice = new Choice("Fouiller l'armoire", 160);
findPotionChoice.setGivesPotion(2);  // Ajoute 2 potions

Choice findMealChoice = new Choice("Visiter les cuisines", 161);
findMealChoice.setGivesMeal(1);  // Ajoute 1 repas

Choice findArtifactChoice = new Choice("Examiner le socle", 162);
findArtifactChoice.setGivesSpecialItem(1);  // Ajoute 1 objet spécial

// Requérir des objets
Choice useKeyChoice = new Choice("Ouvrir la porte scellée", 170);
useKeyChoice.setRequiresSpecialItem();  // Nécessite un objet spécial

Choice eatChoice = new Choice("Prendre un repas pour reprendre des forces", 171);
eatChoice.setRequiresMeal();  // Nécessite un repas

Choice healChoice = new Choice("Utiliser une potion de soin", 172);
healChoice.setRequiresPotion();  // Nécessite une potion
```

### Système de sauvegarde

Le système de sauvegarde utilise la sérialisation Java pour stocker l'état du jeu. Les fichiers de sauvegarde sont stockés dans le dossier `saves/` à la racine du projet.

#### Structure d'une sauvegarde

Chaque sauvegarde contient les informations suivantes :
- Statistiques du joueur (endurance, habileté au combat)
- Inventaire (or, repas, potions, objets spéciaux)
- Numéro de la section actuelle

#### Fonctionnement interne

La classe `SaveManager` gère les opérations de sauvegarde et de chargement :

```java
// Sauvegarder une partie
saveManager.saveGame(game, "nom_sauvegarde");

// Charger une partie
saveManager.loadGame(game, "nom_sauvegarde");

// Lister les sauvegardes disponibles
String[] saveFiles = saveManager.getSaveFiles();
```

---

## 🔧 Dépannage

<div class="warning">

### Problèmes courants et solutions

<table>
<tr>
<th>Problème</th>
<th>Cause possible</th>
<th>Solution</th>
</tr>
<tr>
<td>

```
Error: JavaFX runtime components are missing
```

</td>
<td>JavaFX non trouvé dans le module-path</td>
<td>

1. Vérifiez que JavaFX SDK est correctement installé
2. Assurez-vous que le chemin dans les VM options est correct
3. Vérifiez que tous les JAR JavaFX sont référencés

</td>
</tr>
<tr>
<td>

```
ClassNotFoundException: src.Main
```

</td>
<td>Classe principale non trouvée</td>
<td>

1. Vérifiez que le projet est correctement compilé
2. Vérifiez que la structure des packages est intacte
3. Recompiler le projet entièrement

</td>
</tr>
<tr>
<td>Impossible de charger une sauvegarde</td>
<td>Fichier de sauvegarde corrompu ou incompatible</td>
<td>

1. Vérifiez que le fichier existe dans le dossier `saves/`
2. Assurez-vous que les permissions sont correctes
3. Si le problème persiste, commencez une nouvelle partie

</td>
</tr>
<tr>
<td>Écran noir au démarrage</td>
<td>Problème d'initialisation de JavaFX</td>
<td>

1. Vérifiez que toutes les dépendances sont installées
2. Consultez les logs pour plus d'informations
3. Essayez de lancer avec l'option `-verbose:gc`

</td>
</tr>
<tr>
<td>

```
java.lang.NullPointerException at src.model.Game.setCurrentSection
```

</td>
<td>Section référencée non existante</td>
<td>

1. Vérifiez que tous les numéros de section mentionnés dans les choix existent
2. Ajoutez les sections manquantes
3. Utilisez le débogueur pour identifier la section problématique

</td>
</tr>
</table>

</div>

### Logs et débogage

Pour obtenir plus d'informations en cas de problème, vous pouvez activer les logs détaillés :

```bash
java --module-path "/chemin/vers/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml -Djavafx.verbose=true -cp ./out/production/projet src.Main
```

### Réinitialisation des sauvegardes

Si les sauvegardes posent problème, vous pouvez les réinitialiser :

1. Fermez le jeu complètement
2. Supprimez le contenu du dossier `saves/`
3. Redémarrez l'application

### Contact et support

Pour toute question ou problème concernant le projet, veuillez contacter :
- 📧 support@tourdecristal.example.com
- 📝 Créez une issue sur le dépôt GitHub du projet

---

## 📝 Crédits et licence

### Crédits
- Le jeu est basé sur "La Tour de Cristal", livre 17 de la série Loup Solitaire écrite par Joe Dever
- Développé par l'équipe YNOV B1 : Xerly, Romain, Dimitri

### Licence
- Projet éducatif YNOV, tous droits réservés
- L'utilisation des personnages et concepts de Loup Solitaire est soumise aux droits d'auteur de Joe Dever

---

## 🔮 Perspectives d'évolution

Pour continuer à développer le jeu, voici quelques idées d'amélioration :

<table>
<tr>
<th>Fonctionnalité</th>
<th>Description</th>
<th>Difficulté</th>
</tr>
<tr>
<td>🎵 Ambiance sonore</td>
<td>Ajouter des effets sonores et une musique d'ambiance selon les sections</td>
<td>⭐⭐</td>
</tr>
<tr>
<td>📚 Extension de l'histoire</td>
<td>Ajouter plus de sections et d'embranchements pour enrichir l'aventure</td>
<td>⭐</td>
</tr>
<tr>
<td>🏹 Compétences Kai</td>
<td>Implémenter un système de disciplines Kai (sixième sens, camouflage, etc.)</td>
<td>⭐⭐⭐</td>
</tr>
<tr>
<td>🔨 Éditeur de scénarios</td>
<td>Créer une interface pour concevoir ses propres aventures</td>
<td>⭐⭐⭐⭐</td>
</tr>
<tr>
<td>👥 Mode multijoueur</td>
<td>Permettre à plusieurs joueurs de participer à la même aventure</td>
<td>⭐⭐⭐⭐⭐</td>
</tr>
<tr>
<td>📱 Applications mobiles</td>
<td>Porter le jeu sur Android et iOS via JavaFX Mobile</td>
<td>⭐⭐⭐</td>
</tr>
</table>

---

<div align="center">
  <p>
    <em>"Puisse la main de Kaï vous guider lors de votre quête."</em>
  </p>
  <p>
    Documentation v1.0 • Mai 2025 • YNOV B1
  </p>
</div>
