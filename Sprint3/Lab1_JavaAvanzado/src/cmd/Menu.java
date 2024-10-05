package cmd;

import command.Command;

import java.util.Scanner;

public class Menu {
    private static final String MESSAGE = "Seleccione la opción a ejecutar:";
    private final Command[] commands;

    public Menu(Command... commands) {
        this.commands = commands;
    }

    public void runMenu() {
        int optionSelected = Integer.MIN_VALUE;
        while (optionSelected != exitOption()) {
            printMenu();
            optionSelected = readOption();
            if (optionSelected != exitOption()) {
                optionRun(optionSelected);
            }
        }
    }

    int exitOption() {
        return commands.length + 1;
    }

    void printMenu() {
        System.out.println(MESSAGE);

        for (int i = 0; i < commands.length; i++) {
            Command option = commands[i];
            System.out.println("Opción " + (i + 1) + ": " + option.description());
        }

        System.out.println("Opción " + exitOption() + ": Salir");
    }

    //Leer opción metida por el usuario
    int readOption() {
        Integer option = null;

        while (option == null) {
            try {
                Scanner scan = new Scanner(System.in);
                String input = scan.nextLine();
                int optionSelected = Integer.parseInt(input);
                if (!isValid(optionSelected)) {
                    throw new IllegalArgumentException();
                }
                option = optionSelected;
            } catch (IllegalArgumentException e) {
                System.out.print("Valor no válido, introduzcalo de nuevo: ");
            }
        }
        return option;
    }

    boolean isValid(int option) {
        return option > 0 && option <= exitOption();
    }

    //Lanzar acción
    void optionRun(int optionSelected) {
        commands[optionSelected - 1].run();
    }
}
