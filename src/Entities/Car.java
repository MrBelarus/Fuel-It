package Entities;

import java.util.ArrayList;

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

    private int userTotalPassedDistance;
    private float userTotalFuelWasted;

    private float fuelTankCapacity;

    private ArrayList<String> operationsLog = new ArrayList<>();

    /**
     * Default constructor for creating a car instance
     */
    public Car() {}

    /**
     * Constructor with params for creating a car instance
     * @param model car model name
     * @param totalPassedDistance car mileage in km
     * @param averageFuelConsumption car factory average fuel consumption<br>
     *                               in liters per 100 kilometers
     * @param factoryReleaseYear car factory release year
     * @param fuelTankCapacity car fuel tank capacity in liters
     */
    public Car(String model,
               float totalPassedDistance,
               float averageFuelConsumption,
               int factoryReleaseYear,
               float fuelTankCapacity) {
        this.model = model;
        this.totalPassedDistance = totalPassedDistance;
        this.factoryAverageFuelConsumption = averageFuelConsumption;
        this.factoryReleaseYear = factoryReleaseYear;
        this.fuelTankCapacity = fuelTankCapacity;
    }

    /**
     * get car's model name
     */
    public String getModel() {
        return model;
    }

    /**
     * set car's model name
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * get car's mileage
     */
    public float getTotalPassedDistance() {
        return totalPassedDistance;
    }

    /**
     * set car's mileage
     */
    public void setTotalPassedDistance(float totalPassedDistance) {
        if (totalPassedDistance < 0){
            totalPassedDistance = 0;
        }
        this.totalPassedDistance = totalPassedDistance;
    }

    /**
     * get car's factory average fuel consumption
     */
    public float getFactoryAverageFuelConsumption() {
        return factoryAverageFuelConsumption;
    }

    /**
     * set car's factory average fuel consumption
     */
    public void setFactoryAverageFuelConsumption(float factoryAverageFuelConsumption) {
        if (factoryAverageFuelConsumption < 0){
            factoryAverageFuelConsumption = 0;
        }
        this.factoryAverageFuelConsumption = factoryAverageFuelConsumption;
    }

    /**
     * get car's factory release year
     */
    public int getFactoryReleaseYear() {
        return factoryReleaseYear;
    }

    /**
     * set car's factory release year
     */
    public void setFactoryReleaseYear(int factoryReleaseYear) {
        this.factoryReleaseYear = factoryReleaseYear;
    }

    /**
     * get car's user average fuel consumption
     */
    public float getUserAverageFuelConsumption() {
        return userAverageFuelConsumption;
    }

    /**
     * set car's user average fuel consumption
     */
    public void setUserAverageFuelConsumption(float userAverageFuelConsumption) {
        this.userAverageFuelConsumption = userAverageFuelConsumption;
    }

    /**
     * Get list of operations for this car
     * @return String list of operations
     */
    public ArrayList<String> getOperationsLog() {
        return operationsLog;
    }

    /**
     * Set list of operations for this car
     */
    public void setOperationsLog(ArrayList<String> operationsLog) {
        this.operationsLog = operationsLog;
    }

    /**
     * add operation log to this car
     */
    public void addOperationLog(String logInfo){
        operationsLog.add(logInfo);
    }

    /**
     * get car's fuel tank capacity
     */
    public float getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    /**
     * set car's fuel tank capacity
     */
    public void setFuelTankCapacity(float fuelTankCapacity) {
        this.fuelTankCapacity = fuelTankCapacity;
    }

    /**
     * get user total passed distance amount
     */
    public int getUserTotalPassedDistance() {
        return userTotalPassedDistance;
    }

    /**
     * set user total passed distance
     */
    public void setUserTotalPassedDistance(int userTotalPassedDistance) {
        this.userTotalPassedDistance = userTotalPassedDistance;
    }

    /**
     * get user total wasted fuel amount
     */
    public float getUserTotalFuelWasted() {
        return userTotalFuelWasted;
    }

    /**
     * set user total wasted fuel amount
     */
    public void setUserTotalFuelWasted(float userTotalFuelWasted) {
        this.userTotalFuelWasted = userTotalFuelWasted;
    }


    /**
     * Display all information about this car
     * @return String result
     */
    @Override
    public String toString() {
        return  String.format("Информация об автомобиле:\n" +
                "\nМодель:\n-" + getModel() + "\n" +
                "\nГод выпуска:\n-" + getFactoryReleaseYear() + " г.\n" +
                "\nПробег:\n-" + getTotalPassedDistance() + " км.\n" +
                "\nОбъем бензобака:\n-" +
                getFuelTankCapacity() + " л.\n" +
                "\nЗаводской средний расход топлива:\n-%.2f л/100км.\n" +
                "\nТекущий средний расход топлива:\n-%.2f л/100км.\n",
                getFactoryAverageFuelConsumption(),
                getUserAverageFuelConsumption());
    }
}
