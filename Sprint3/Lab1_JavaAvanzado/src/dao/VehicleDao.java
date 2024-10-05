package dao;

import model.Car;
import model.Motorbike;
import model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleDao {
    private final List<Vehicle> vehicles = new ArrayList<>();

    public void addCar(Car car) {
        vehicles.add(car);
    }

    public void addMotorbike(Motorbike motorbike) {
        vehicles.add(motorbike);
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<Vehicle> getVehiclesByType(Class<? extends Vehicle> type) {
        return vehicles.stream().filter(type::isInstance).toList();
    }

    public List<Vehicle> getVehiclesByBrand(String brand) {
        return vehicles.stream().filter(vehicle ->
                vehicle.getBrand().equalsIgnoreCase(brand)
        ).toList();
    }
}
