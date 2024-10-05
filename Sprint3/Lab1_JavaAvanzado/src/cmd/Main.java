package cmd;

import command.*;
import dao.VehicleDao;

public class Main {
    //Shortcut psvm
    public static void main(String[] args) {
        System.out.println("Bienvenido! a contiación se le muestra un menu con las diferentes fucnionalidadldes de nuestra super app");

        VehicleDao vehicleDao = new VehicleDao();

        Menu newMenu = new Menu(
                new MenuCommand(
                        "Añadir vehículos",
                        new AddCarCommand(vehicleDao),
                        new AddMotorbikeCommand(vehicleDao)
                ),
                new MenuCommand(
                        "Mostrar vehículos",
                        new ShowAllCommand(vehicleDao),
                        new ShowByTypeCommand(vehicleDao),
                        new ShowByBrandCommand(vehicleDao)
                )
        );
        newMenu.runMenu();
        System.out.println("Saliendo...");
    }
}
