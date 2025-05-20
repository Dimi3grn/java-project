@echo off
echo Lancement de La Tour de Cristal...
java --module-path "%PATH_TO_FX%" --add-modules javafx.controls,javafx.fxml -jar Game.jar
pause 