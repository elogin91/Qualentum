package model;

import java.util.Objects;

public class Car extends Vehicle {
    private Integer numberOfDoors;
    private Boolean isConvertible;

    public Car(String brand, String model, Integer year, Integer numberOfDoors, Boolean isConvertible) {
        super(brand, model, year);
        this.numberOfDoors = numberOfDoors;
        this.isConvertible = isConvertible;
    }

    public Integer getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(Integer numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public Boolean getConvertible() {
        return isConvertible;
    }

    public void setConvertible(Boolean convertible) {
        isConvertible = convertible;
    }

    void putSeabelt() {
        // TODO: not implemented yet
    }

    void drift() {
        // TODO: not implemented yet
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Car car = (Car) o;
        return Objects.equals(getNumberOfDoors(), car.getNumberOfDoors()) && Objects.equals(isConvertible, car.isConvertible);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNumberOfDoors(), isConvertible);
    }

    @Override
    public String toString() {
        return "Car: " +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", numberOfDoors=" + numberOfDoors +
                ", isConvertible=" + isConvertible;
    }
}
