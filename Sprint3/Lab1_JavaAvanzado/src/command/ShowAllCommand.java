package command;

import dao.VehicleDao;
import model.Vehicle;

import java.util.List;

public class ShowAllCommand implements Command {
    private final VehicleDao vehicleDao;

    public ShowAllCommand(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @Override
    public String description() {
        return "Mostrar todos";
    }

    @Override
    public void run() {
        List<Vehicle> vehicleList = vehicleDao.getVehicles();
        if (vehicleList.isEmpty()) {
            System.out.println("Todavía no se ha introducido ningún vehículo");
        }
        for (Vehicle vehicle : vehicleList) {
            System.out.println(vehicle);
        }
    }
}
