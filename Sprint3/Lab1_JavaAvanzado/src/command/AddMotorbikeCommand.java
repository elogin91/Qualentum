package command;

import dao.VehicleDao;
import model.Motorbike;

import java.util.Scanner;

public class AddMotorbikeCommand implements Command {

    private final VehicleDao vehicleDao;

    public AddMotorbikeCommand(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @Override
    public String description() {
        return "Añadir moto";
    }

    @Override
    public void run() {
        Motorbike motorbike = readMotorbike();

        if (motorbike != null) {
            System.out.println("Moto añadida: " + motorbike);
            vehicleDao.addMotorbike(motorbike);
        }
    }

    private Motorbike readMotorbike() {
        System.out.println("Introduzca por favor: Marca,modelo,año,cilindrada,si/no(tiene sidecar)");
        Motorbike motorbike = null;
        boolean forceExit = false;
        while (motorbike == null && !forceExit) {
            try {
                Scanner scan = new Scanner(System.in);
                String input = scan.nextLine();
                if (input.equalsIgnoreCase("salir")) {
                    forceExit = true;
                } else {
                    String[] newMotorbike = input.split(",");
                    if (newMotorbike.length != 5) {
                        throw new IllegalArgumentException();
                    }
                    Boolean hasSidecar = newMotorbike[4].equals("si");
                    motorbike = new Motorbike(newMotorbike[0], newMotorbike[1], Integer.parseInt(newMotorbike[2]), Integer.parseInt(newMotorbike[3]), hasSidecar);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Valores introducidos no válidos, introduzcalo de nuevo los valores separados por ',' o escriba salir.");
            }
        }
        return motorbike;
    }
}
