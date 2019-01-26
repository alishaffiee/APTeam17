package View;

import Control.CommandController;

import java.util.Scanner;

public class InputReader {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CommandController commandController = CommandController.commandController;

        while(true) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            String command = parts[0];
            switch (command) {
                case "buy": {
                    commandController.buyAnimal(parts[1]);
                    break;
                }
                case "pickup" : {
                    commandController.pickup(Integer.valueOf(parts[1]), Integer.valueOf(parts[2]));
                    break;
                }
                case "cage" : {
                    commandController.cage(Integer.valueOf(parts[1]), Integer.valueOf(parts[2]));
                    break;
                }
                case "well" : {
                    commandController.well();
                    break;
                }
                case "start" : {
                    commandController.startWorkshop(parts[1]);
                    break;
                }
                case "upgrade" : {
                    commandController.upgrade(parts[1]);
                    break;
                }
                case "load_workshop" : {
                    commandController.loadWorkshop(parts[1]);
                    break;
                }
                case "add_workshop" : {
                    commandController.addWorkshop(parts[1]);
                    break;
                }
                case "run" : {
                    commandController.run(parts[1]);
                    break;
                }
                case "save_game" : {
                    commandController.saveGame(parts[1]);
                    break;
                }
                case "load_game" : {
                    commandController.loadGame(parts[1]);
                    break;
                }
                case "print" : {
                    commandController.print(parts[1]);
                    break;
                }
                case "turn" : {
                    commandController.nextTurn(Integer.valueOf(parts[1]));
                    break;
                }
                default: {
                    System.out.println("Invalid command.");
                }
            }
        }
    }
}
