package command;

import dao.VehicleDao;
import model.Car;
import model.Motorbike;
import model.Vehicle;

import java.util.List;
import java.util.Scanner;

public class ShowByTypeCommand implements Command {
    private final VehicleDao vehicleDao;

    public ShowByTypeCommand(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @Override
    public String description() {
        return "Mostrar por tipo";
    }

    @Override
    public void run() {
        Class<? extends Vehicle> type = readType();
        List<Vehicle> vehicleList = vehicleDao.getVehiclesByType(type);
        if (vehicleList.isEmpty()) {
            System.out.println("No se ha encontrado ningún vehículo");
        }

        for (Vehicle vehicle : vehicleList) {
            System.out.println(vehicle);
        }
    }

    private Class<? extends Vehicle> readType() {
        System.out.println("Introduzca por favor el tipo: moto / coche");
        Class<? extends Vehicle> type = null;
        boolean forceExit = false;
        while (type == null && !forceExit) {
            try {
                Scanner scan = new Scanner(System.in);
                String input = scan.nextLine();
                if (input.equalsIgnoreCase("salir")) {
                    forceExit = true;
                } else {
                    if (input.equalsIgnoreCase("coche")) {
                        type = Car.class;
                    }
                    if (input.equalsIgnoreCase("moto")) {
                        type = Motorbike.class;
                    }
                    if (type == null) {
                        throw new IllegalArgumentException();
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Valores introducidos no válidos, introduzcalo de nuevo los valores separados por ',' o escriba salir.");
            }
        }
        return type;
    }
}
