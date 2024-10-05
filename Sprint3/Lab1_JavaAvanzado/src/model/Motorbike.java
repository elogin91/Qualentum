package model;

import java.util.Objects;

public class Motorbike extends Vehicle {
    private Integer engineDisplacement;
    private Boolean hasSidecar;

    public Motorbike(String brand, String model, Integer year, Integer engineDisplacement, Boolean hasSidecar) {
        super(brand, model, year);
        this.engineDisplacement = engineDisplacement;
        this.hasSidecar = hasSidecar;
    }

    public Integer getEngineDisplacement() {
        return engineDisplacement;
    }

    public void setEngineDisplacement(Integer engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    public Boolean getHasSidecar() {
        return hasSidecar;
    }

    public void setHasSidecar(Boolean hasSidecar) {
        this.hasSidecar = hasSidecar;
    }

    void wheelie() {
        // TODO: not implemented yet
    }

    void putHelmet() {
        // TODO: not implemented yet
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Motorbike motorbike = (Motorbike) o;
        return Objects.equals(getEngineDisplacement(), motorbike.getEngineDisplacement()) && Objects.equals(getHasSidecar(), motorbike.getHasSidecar());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getEngineDisplacement(), getHasSidecar());
    }

    @Override
    public String toString() {
        return "Motorbike: " +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", engineDisplacement=" + engineDisplacement +
                ", hasSidecar=" + hasSidecar;
    }
}
