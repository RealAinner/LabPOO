@echo off
set "JDK=C:\Program Files\Java\jdk-21\bin"
cd /d "%~dp0"
"%JDK%\java" --module-path "lib" --add-modules javafx.controls,javafx.fxml -Djava.library.path="lib" -jar GymPOS.jar
pause
