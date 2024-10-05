package command;

import dao.VehicleDao;
import model.Vehicle;

import java.util.List;
import java.util.Scanner;

public class ShowByBrandCommand implements Command {
    private final VehicleDao vehicleDao;

    public ShowByBrandCommand(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @Override
    public String description() {
        return "Mostrar por marca";
    }

    @Override
    public void run() {
        String brand = readBrand();
        List<Vehicle> vehicleList = vehicleDao.getVehiclesByBrand(brand);
        if (vehicleList.isEmpty()) {
            System.out.println("No se ha encontrado ningún vehículo");
        }

        for (Vehicle vehicle : vehicleList) {
            System.out.println(vehicle);
        }
    }

    private String readBrand() {
        System.out.println("Introduzca por favor la marca");
        String brand = null;
        boolean forceExit = false;
        while (brand == null && !forceExit) {
            try {
                Scanner scan = new Scanner(System.in);
                String input = scan.nextLine();
                if (input.equalsIgnoreCase("salir")) {
                    forceExit = true;
                } else {
                    brand = input;
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Valores introducidos no válidos, introduzcalo de nuevo los valores separados por ',' o escriba salir.");
            }
        }
        return brand;
    }
}
