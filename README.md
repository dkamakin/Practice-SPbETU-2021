# Practice-SPbETU-2021

The program is designed to visualize the Prim algorithm.

Authors: [Kamakin Daniil](https://github.com/dkamakin), [Moskalenko Elizaveta](https://github.com/moskalenko9381), [Aukhadiev Alexander](https://github.com/Auhadiev)

![mainwindow](https://user-images.githubusercontent.com/54929583/125194621-c4b40c00-e25a-11eb-9506-4acdb6cf2ca6.png)

# Run on Windows

1. Download the repository
2. Open it with command line
3. Run the next command: 

GUI:
java --module-path javafx\windows\lib --add-modules javafx.controls,javafx.fxml -jar .\practice_spbetu.jar

CLI:
java --module-path javafx\windows\lib --add-modules javafx.controls,javafx.fxml -jar .\practice_spbetu.jar --cli


# Run on Linux

1. Download the repository
2. Open it with command line
3. Run the next command: 

GUI:
java --module-path javafx\linux\lib --add-modules javafx.controls,javafx.fxml -jar .\practice_spbetu.jar

CLI:
java --module-path javafx\linux\lib --add-modules javafx.controls,javafx.fxml -jar .\practice_spbetu.jar --cli

# Run on macOS

1. Download the repository
2. Open it with command line
3. Run the next command: 

GUI:
java --module-path javafx\macos\lib --add-modules javafx.controls,javafx.fxml -jar .\practice_spbetu.jar

CLI:
java --module-path javafx\macos\lib --add-modules javafx.controls,javafx.fxml -jar .\practice_spbetu.jar --cli

# FAQ

## How do I add a node?
Сlick on any available space
## How do I connect the nodes?
Choose the first one then the second one and type the weigth
## I've selected the wrong node to connect! How can I cancel the selection?
Press ESC
## I typed the wrong weight!
Click on the weight to change
## How can I move a node?
Click on the "Move" button, then select the node that you want to move, then click on any available space
## What are the available command-line arguments?
Only --gui and --cli
