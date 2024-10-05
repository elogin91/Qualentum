package command;

import dao.VehicleDao;
import model.Car;

import java.util.Scanner;

public class AddCarCommand implements Command {

    private final VehicleDao vehicleDao;

    public AddCarCommand(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @Override
    public String description() {
        return "Añadir coche";
    }

    @Override
    public void run() {
        Car car = readCar();

        if (car != null) {
            System.out.println("Coche añadido: " + car);
            vehicleDao.addCar(car);
        }
    }

    private Car readCar() {
        System.out.println("Introduzca por favor: Marca,modelo,año,número de puertas,si/no(convertible)");
        Car car = null;
        boolean forceExit = false;
        while (car == null && !forceExit) {
            try {
                Scanner scan = new Scanner(System.in);
                String input = scan.nextLine();
                if (input.equalsIgnoreCase("salir")) {
                    forceExit = true;
                } else {
                    String[] newCar = input.split(",");
                    if (newCar.length != 5) {
                        throw new IllegalArgumentException();
                    }
                    Boolean isConvertible = newCar[4].equals("si");
                    car = new Car(newCar[0], newCar[1], Integer.parseInt(newCar[2]), Integer.parseInt(newCar[3]), isConvertible);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Valores introducidos no válidos, introduzcalo de nuevo los valores separados por ',' o escriba salir.");
            }
        }
        return car;
    }
}
