# La Tour de Cristal - Livre dont vous êtes le héros

Ce projet est une adaptation du livre "La Tour de Cristal" (Loup Solitaire n°17) en jeu interactif avec interface graphique.

## Prérequis

- Java JDK 11 ou supérieur
- JavaFX 13 ou supérieur

## Installation

1. Téléchargez JavaFX SDK depuis [https://gluonhq.com/products/javafx/](https://gluonhq.com/products/javafx/)
2. Extrayez l'archive dans un dossier de votre choix
3. Définissez la variable d'environnement `PATH_TO_FX` pour qu'elle pointe vers le dossier `lib` du SDK JavaFX:
   - Windows: `set PATH_TO_FX=C:\path\to\javafx-sdk\lib`
   - macOS/Linux: `export PATH_TO_FX=/path/to/javafx-sdk/lib`

## Compilation

Pour compiler le projet:

```
javac --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml -d out src/*.java src/model/*.java src/view/*.java src/controller/*.java
```

## Exécution

Exécutez le script `run.bat` (Windows) ou lancez manuellement:

```
java --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml -cp out src.Main
```

## Fonctionnalités

- Interface graphique moderne avec thème adapté à l'ambiance du jeu
- Navigation entre les sections du livre
- Système de combat
- Gestion de l'inventaire
- Sauvegarde et chargement de parties 