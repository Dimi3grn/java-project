# Documentation Projet - La Tour de Cristal

## Informations générales

**Référence :** YNOV-PROJET-JAVA-2025  
**Auteurs :** Xerly, Romain, Dimitri  
**Date :** Mai 2025

## 1. Présentation

### 1.1 Introduction

Ce document présente la documentation technique du projet "La Tour de Cristal", un jeu interactif du type "livre dont vous êtes le héros" développé en Java avec une interface graphique JavaFX. Ce projet a été réalisé dans le cadre du TP Info B1 de YNOV.

L'objectif de ce projet est de concevoir et développer un jeu interactif où le joueur peut prendre des décisions qui influenceront le déroulement de l'histoire. Le joueur incarnera le Seigneur Kai, un héros du Sommerlund qui doit s'échapper de la Tour de Cristal et découvrir les secrets du sorcier Zahda.

### 1.2 Glossaire

#### 1.2.1 Abréviations et Terminologies

| Abréviation | Signification |
|-------------|---------------|
| UML | Unified Modelling Language |
| MVC | Model-View-Controller (Modèle-Vue-Contrôleur) |
| LDVELH | Livre Dont Vous Êtes Le Héros |
| JavaFX | Framework Java pour interfaces graphiques riches |

#### 1.2.2 Documents de référence

| Identification | Description |
|----------------|-------------|
| [REF 1] | Document «MiniProjetYnovB1.pdf» mars 2025 |
| [REF 2] | Java Documentation - Oracle (https://docs.oracle.com/en/java/) |
| [REF 3] | JavaFX Documentation - Oracle (https://openjfx.io/javadoc/17/) |

#### 1.2.3 Glossaire métier

| Terminologie | Description |
|--------------|-------------|
| Section | Unité narrative du jeu représentant une situation ou un lieu spécifique. Chaque section contient du texte descriptif et des choix possibles pour le joueur. |
| Choix | Option disponible pour le joueur à la fin d'une section. Chaque choix mène à une nouvelle section. |
| Combat | Système de confrontation entre le joueur et un ennemi, basé sur des jets de dés et des statistiques. |
| Endurance | Attribut du personnage représentant sa santé/vitalité. Si elle atteint zéro, le jeu se termine. |
| Habileté au combat | Attribut du personnage représentant sa capacité au combat. Plus cette valeur est élevée, plus le joueur a de chances de gagner un combat. |
| Loup Solitaire | Nom du héros principal, un Seigneur Kai du royaume de Sommerlund. |
| Tour de Cristal | Lieu où se déroule l'aventure, dominé par le sorcier Zahda. |

## 2. Projet - Le Livre dont vous êtes le héros

### 2.1 Spécification fonctionnelle

#### 2.1.1 Présentation

Le projet "La Tour de Cristal" est une adaptation numérique du concept des livres-jeux "Livre Dont Vous Êtes Le Héros" (LDVELH), spécifiquement inspiré de la série Loup Solitaire de Joe Dever. Dans ce type de jeu, le joueur lit des paragraphes de texte décrivant une situation, puis choisit parmi plusieurs options celle qui lui semble la plus pertinente. Chaque choix mène à une nouvelle section de l'histoire, créant ainsi un parcours unique et interactif.

Notre implémentation propose deux interfaces distinctes :
- Une interface console avec mise en forme ANSI pour les couleurs et styles
- Une interface graphique moderne développée avec JavaFX

#### 2.1.2 Besoins fonctionnels

Le jeu doit permettre de :

1. **Naviguer entre les sections** : Passer d'une section à une autre en fonction des choix du joueur.
2. **Gérer un personnage** : Suivre les statistiques du personnage (endurance, habileté au combat, inventaire, etc.).
3. **Participer à des combats** : Système de combat basé sur les statistiques du joueur et de l'ennemi avec une part d'aléatoire.
4. **Gérer un inventaire** : Collecter, utiliser et gérer des objets (potions, repas, objets spéciaux, or).
5. **Sauvegarder/Charger une partie** : Permettre au joueur d'enregistrer sa progression et de la reprendre ultérieurement.
6. **Afficher une interface utilisateur** : Présenter clairement le texte, les choix et les informations du personnage, soit en console, soit via l'interface graphique JavaFX.
7. **Gérer des événements spéciaux** : Déclencher des événements particuliers à certains moments du jeu.
8. **Afficher des dialogues de combat** : Interface dédiée pour les séquences de combat avec visualisation des statistiques, jets de dés et barres de vie.

#### 2.1.3 Enchaînement

Le déroulement typique d'une partie est le suivant :

1. Le joueur démarre le jeu et accède au menu principal.
2. Le joueur choisit de commencer une nouvelle partie ou de charger une partie existante.
3. Le texte de la section courante est affiché, décrivant la situation.
4. Les choix disponibles sont présentés au joueur sous forme de boutons (JavaFX) ou d'options numérotées (console).
5. Le joueur sélectionne un choix.
6. Si le choix implique un combat, le système de combat est déclenché avec une interface dédiée.
7. Si le choix nécessite un objet, de l'or ou une autre condition spéciale, la vérification est effectuée.
8. Le jeu passe à la section suivante en fonction du choix et met à jour les statistiques du joueur.
9. À tout moment, le joueur peut accéder au menu pour sauvegarder sa progression ou charger une autre partie.
10. Ce cycle continue jusqu'à la fin de l'aventure (victoire ou défaite).

#### 2.1.4 Analyse Métier

Le jeu s'inspire fortement de la structure des livres-jeux traditionnels, notamment de la série "Loup Solitaire" de Joe Dever. Les concepts clés adaptés sont :

- **Sections numérotées** : Chaque passage de l'histoire est identifié par un numéro unique.
- **Système de combat** : Basé sur la comparaison des valeurs d'habileté au combat et des jets de dés.
- **Gestion de ressources** : Le joueur doit gérer son endurance, ses provisions, et ses objets.
- **Narration non-linéaire** : L'histoire peut se dérouler de différentes façons selon les choix du joueur.
- **Conditions spéciales** : Certains passages ne sont accessibles que si le joueur possède certains objets ou a accompli certaines actions.
- **Sections spéciales** : Moments clés de l'aventure déclenchant des événements particuliers.
- **Ennemis variés** : Différents types d'adversaires avec leurs propres statistiques et caractéristiques.

### 2.2 Spécification Technique

#### 2.2.1 Présentation

Le projet est développé en Java, utilisant une architecture Modèle-Vue-Contrôleur (MVC) pour séparer la logique métier, l'interface utilisateur et la gestion des interactions. Cette approche permet une meilleure organisation du code et facilite les modifications et les évolutions futures. Nous avons implémenté deux vues distinctes : une vue console avec des couleurs ANSI et une vue graphique en JavaFX.

#### 2.2.2 Choix technologiques

- **Langage** : Java (JDK 24)
- **Architecture** : Modèle-Vue-Contrôleur (MVC)
- **Interface utilisateur** : 
  - Console Java avec mise en forme ANSI pour les couleurs et styles
  - Interface graphique JavaFX pour une expérience moderne
- **Persistance des données** : Sérialisation Java pour les sauvegardes
- **Style graphique** : CSS personnalisé pour un thème sombre approprié à l'ambiance du jeu

#### 2.2.3 Architecture

Le projet est structuré selon le pattern MVC avec une séparation claire des responsabilités :

**Modèle (Model)** :
- Classes représentant les données et la logique métier : `Game`, `Player`, `Section`, `Choice`, `Enemy`, etc.
- Gestion de la sérialisation/désérialisation : `SaveManager`

**Vue (View)** :
- Affichage console : `GameView`, `ConsoleUtils`
- Affichage graphique : `JavaFXView`
- Ressources graphiques : CSS, images, polices personnalisées

**Contrôleur (Controller)** :
- Gestion du flux de jeu console : `GameController`
- Gestion du flux de jeu graphique : `JavaFXGameController`
- Gestion des combats : `CombatController`

Les principales classes du modèle sont :
- `Game` : Classe principale contenant la logique du jeu et l'état actuel
- `Player` : Représente le personnage du joueur avec ses attributs et inventaire
- `Section` : Représente une section du livre avec son texte et ses choix
- `Choice` : Représente un choix possible dans une section avec ses conditions et effets
- `Enemy` : Représente un ennemi que le joueur peut combattre, avec une factory pour créer différents types d'ennemis

#### 2.2.4 Problèmes Techniques

Plusieurs défis techniques ont été relevés durant le développement :

1. **Double interface utilisateur** : Maintenir à la fois une interface console et une interface graphique tout en partageant la même logique métier.

2. **Système de combat équilibré** : Création d'un système de combat avec une part d'aléatoire tout en restant équitable et intéressant, avec des interfaces dédiées pour la console et pour JavaFX.

3. **Interface graphique moderne** : Développement d'une interface JavaFX attrayante avec CSS personnalisé pour créer une ambiance appropriée au thème du jeu.

4. **Gestion des ressources graphiques** : Organisation des ressources externes (CSS, images) pour une utilisation correcte dans le projet JavaFX.

5. **Persistence des données** : Implémentation d'un système de sauvegarde et chargement fonctionnel dans les deux interfaces.

6. **Navigation fluide** : Assurer une transition fluide entre les sections et permettre au joueur de revenir facilement au menu principal.

7. **État du joueur** : Maintenir et afficher correctement l'état du joueur, en particulier pendant et après les combats.

## 3. ANNEXE

### 3.1 Répartition des tâches

| Membre | Rôle principal | Tâches réalisées |
|--------|----------------|------------------|
| **Dimitri** | Backend Developer | - Conception initiale<br>- Architecture MVC<br>- Implémentation modèle<br>- Logique de jeu<br>- Système de combat<br>- Sauvegarde/chargement<br>- Documentation technique |
| **Romain** | Frontend Developer | - Interface utilisateur console<br>- Interface utilisateur JavaFX<br>- Styles CSS<br>- Animations<br>- Mise en forme visuelle<br>- Écran de combat<br>- Menu principal<br>- Contrôleurs d'interface |
| **Xerly** | Support & QA | - Tests fonctionnels<br>- Rédaction sections narratives<br>- Équilibrage du jeu<br>- Conception des ennemis<br>- Création de l'histoire<br>- Blagues et support moral |

### 3.2 Structure du projet

```
src/
├── Main.java
├── Choice.java
├── GameManager.java
├── Player.java
├── Section.java
├── controller/
│   ├── CombatController.java
│   ├── GameController.java
│   ├── JavaFXGameController.java
├── model/
│   ├── Choice.java
│   ├── Enemy.java
│   ├── Game.java
│   ├── Player.java
│   ├── SaveManager.java
│   ├── Section.java
├── view/
│   ├── ConsoleUtils.java
│   ├── GameView.java
│   ├── JavaFXView.java
└── resources/
    ├── css/
    │   └── style.css
    ├── images/
        └── crystal_tower_icon.png
```

### 3.3 Fonctionnalités implémentées

- ✅ Double interface (console ANSI et graphique JavaFX)
- ✅ Système de navigation entre sections
- ✅ Gestion des statistiques du personnage
- ✅ Système de combat avec interface dédiée
- ✅ Inventaire complet (or, repas, potions, objets spéciaux)
- ✅ Sauvegarde et chargement de parties
- ✅ Événements spéciaux
- ✅ Menu principal avec options
- ✅ Conditions et effets sur les choix (dégâts, soins, gains d'objets, coûts)
- ✅ Ennemis variés via une factory
- ✅ Système de styles CSS pour l'interface graphique
- ✅ Gestion des fins multiples (victoire, défaite)

### 3.4 Points clés du développement

#### Interface JavaFX

L'implémentation de l'interface JavaFX a constitué une évolution majeure du projet. Elle offre une expérience utilisateur moderne avec :

- Un menu principal attractif
- Un affichage du texte avec mise en forme
- Des boutons stylisés pour les choix
- Un panneau de statistiques toujours visible
- Une interface de combat dédiée
- Des dialogues modaux pour les sauvegardes et chargements
- Un thème sombre cohérent avec l'ambiance du jeu

#### Système de combat amélioré

Le système de combat a été développé avec une interface graphique dédiée permettant :

- L'affichage des statistiques des combattants
- Des barres de vie visuelles
- Un journal de combat détaillé
- La possibilité d'utiliser des objets pendant le combat
- L'option de fuir certains combats
- Des animations pour les jets de dés

#### Gestion des choix enrichie

Les choix disponibles pour le joueur ont été enrichis avec de nombreuses conditions et effets :

- Combat avec différents types d'ennemis
- Exigence d'objets spécifiques
- Coûts en or ou ressources
- Gains d'objets, d'or ou de statistiques
- Effets sur l'endurance du joueur (dégâts, soins)
- Événements spéciaux déclenchés par certains choix

### 3.5 Conclusion et perspectives

Le projet "La Tour de Cristal" implémente avec succès un jeu du type "livre dont vous êtes le héros" en Java avec une double interface : console et graphique JavaFX. L'architecture MVC permet une séparation claire des responsabilités et a facilité l'implémentation des deux interfaces sans duplication excessive de code.

**Améliorations possibles :**
- Ajout d'effets sonores et musique d'ambiance
- Plus de sections et une histoire plus complexe avec des embranchements multiples
- Système de statistiques étendu avec plus d'attributs pour le personnage
- Système de combat plus élaboré avec compétences spéciales
- Sauvegarde en ligne avec classements des joueurs
- Mode multijoueur coopératif
- Applications mobiles (Android/iOS) via JavaFX Mobile
