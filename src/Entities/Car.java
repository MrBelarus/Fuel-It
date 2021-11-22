package Entities;

/**
 * Car class
 * Contains information about car
 *
 * @author V.U.Kurhei
 * @version 1.0
 */
public class Car {
    private String model;
    private float totalPassedDistance;
    private float factoryAverageFuelConsumption;
    private float userAverageFuelConsumption;
    private int factoryReleaseYear;

    public Car() {}

    public Car(String model,
               float totalPassedDistance,
               float averageFuelConsumption,
               int factoryReleaseYear) {
        this.model = model;
        this.totalPassedDistance = totalPassedDistance;
        this.factoryAverageFuelConsumption = averageFuelConsumption;
        this.factoryReleaseYear = factoryReleaseYear;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public float getTotalPassedDistance() {
        return totalPassedDistance;
    }

    public void setTotalPassedDistance(float totalPassedDistance) {
        if (totalPassedDistance < 0){
            totalPassedDistance = 0;
        }
        this.totalPassedDistance = totalPassedDistance;
    }

    public float getFactoryAverageFuelConsumption() {
        return factoryAverageFuelConsumption;
    }

    public void setFactoryAverageFuelConsumption(float factoryAverageFuelConsumption) {
        if (factoryAverageFuelConsumption < 0){
            factoryAverageFuelConsumption = 0;
        }
        this.factoryAverageFuelConsumption = factoryAverageFuelConsumption;
    }

    public int getFactoryReleaseYear() {
        return factoryReleaseYear;
    }

    public void setFactoryReleaseYear(int factoryReleaseYear) {
        this.factoryReleaseYear = factoryReleaseYear;
    }

    public float getUserAverageFuelConsumption() {
        return userAverageFuelConsumption;
    }

    public void setUserAverageFuelConsumption(float userAverageFuelConsumption) {
        this.userAverageFuelConsumption = userAverageFuelConsumption;
    }

    /**
     * Display all information about this car
     * @return String result
     */
    @Override
    public String toString() {
        return "Информация об автомобиле:\n" +
                "\nМодель:\n-" + getModel() + "\n" +
                "\nГод выпуска:\n-" + getFactoryReleaseYear() + " г.\n" +
                "\nПробег:\n-" + getTotalPassedDistance() + " км.\n" +
                "\nЗаводской средний расход топлива:\n-" +
                getFactoryAverageFuelConsumption() + " л/100км.\n" +
                "\nТекущий средний расход топлива:\n-" +
                getUserAverageFuelConsumption() + " л/100км.";
    }
}
