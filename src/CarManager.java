import Entities.Car;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Cars manager
 * Can be used to calculate fuel and distance operations
 *
 * @author V.U.Kurhei
 * @version 1.0
 */
public class CarManager {
    public static ArrayList<Car> cars = new ArrayList<>();

    private final static String CARS_INFO_FILE_PATH = "cars_info.xml";

    /**
     * Calculates distance that Car can pass with given liters
     * @param litersAmount total fuel in liters
     * @param car car that will be driven
     * @return amount of distance in km with user average fuel consumption
     */
    public static float calculateUserDistance(float litersAmount, Car car){
        if (car.getFactoryAverageFuelConsumption() == 0f){
            return Float.MAX_VALUE;
        }
        return litersAmount / car.getUserAverageFuelConsumption() * 100f;
    }

    /**
     * Calculates distance that Car can pass with given liters
     * @param litersAmount total fuel in liters
     * @param car car that will be driven
     * @return amount of distance in km with factory average fuel consumption
     */
    public static float calculateFactoryDistance(float litersAmount, Car car){
        if (car.getFactoryAverageFuelConsumption() == 0f){
            return Float.MAX_VALUE;
        }
        return litersAmount / car.getFactoryAverageFuelConsumption() * 100f;
    }

    /**
     * Calculates distance that Car can pass with given liters
     * @param distance target distance to pass in km
     * @param car car that will be driven
     * @return amount of fuel in liters to pass target distance
     * with car's factory average fuel consumption
     */
    public static float calculateFactoryFuelAmount(float distance, Car car){
        return car.getFactoryAverageFuelConsumption() * distance / 100f;
    }

    /**
     * Calculates distance that Car can pass with given liters
     * @param distance target distance to pass in km
     * @param car car that will be driven
     * @return amount of fuel in liters to pass target distance
     * with car's user average fuel consumption
     */
    public static float calculateUserFuelAmount(float distance, Car car){
        return car.getUserAverageFuelConsumption() * distance / 100f;
    }

    /**
     * Calculates user's average fuel consumption of a car
     * @param passedDistance distance in km
     * @param wastedLiters fuel amount
     * @param drivenCar used car
     * @return new user average fuel consumption of used car
     */
    public static float calculateUserAverageFuelConsumption(float passedDistance,
                                                            float wastedLiters,
                                                            Car drivenCar){
        float userAverageFuelConsumption = (drivenCar.getUserTotalFuelWasted() + wastedLiters) /
                (drivenCar.getUserTotalPassedDistance() + passedDistance) * 100f;

        drivenCar.setUserAverageFuelConsumption(userAverageFuelConsumption);

        return userAverageFuelConsumption;
    }

    /**
     * Calculates session fuel consumption
     * @param passedDistance distance in km
     * @param wastedLiters fuel amount
     * @return session fuel consumption of used car
     */
    public static float calculateSessionAverageFuelConsumption(float passedDistance,
                                                               float wastedLiters){
        return wastedLiters / passedDistance * 100f;
    }

    /**
     * Calls FileManager and saves all vehicles to file
     */
    public static void saveCarsToFile(){
        FileManager.SaveCarsToXML(CARS_INFO_FILE_PATH, cars.toArray(new Car[cars.size()]));
    }

    /**
     * Calls FileManager and adds all vehicles from file
     */
    public static void loadCarsFromFile(){
        if (!FileManager.isFileExists(CARS_INFO_FILE_PATH)){
            return;
        }

        Car[] carsFromFile = FileManager.LoadCarsFromXML(CARS_INFO_FILE_PATH);

        if (carsFromFile == null)
            return;

        Collections.addAll(cars, carsFromFile);
    }
}
