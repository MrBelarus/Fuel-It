import Entities.Car;

import java.util.ArrayList;

/**
 * Cars manager
 * Can be used to calculate fuel and distance operations
 *
 * @author V.U.Kurhei
 * @version 1.0
 */
public class CarManager {
    public static ArrayList<Car> cars = new ArrayList<>();

    /**
     * Calculates distance that Car can pass with given liters
     * @param litersAmount total fuel in liters
     * @param car car that will be driven
     * @return amount of distance in km
     */
    public static float CalculateDistance(float litersAmount, Car car){
        if (car.getAverageFuelConsumption() == 0f){
            return Float.MAX_VALUE;
        }
        return litersAmount / car.getAverageFuelConsumption() * 100f;
    }

    /**
     * Calculates distance that Car can pass with given liters
     * @param distance target distance to pass in km
     * @param car car that will be driven
     * @return amount of fuel in liters to pass target distance
     */
    public static float CalculateFuelAmount(float distance, Car car){
        return car.getAverageFuelConsumption() * distance / 100f;
    }

    /**
     * Calculates average fuel consumption of a car
     * @param passedDistance distance in km
     * @param wastedLiters fuel amount
     * @param drivenCar used car
     * @return new average fuel consumption of used car
     */
    public static float CalculateAverageFuelConsumption(float passedDistance,
                                                        float wastedLiters,
                                                        Car drivenCar){
        float totalCarWastedFuel = drivenCar.getTotalPassedDistance() / 100f *
                drivenCar.getAverageFuelConsumption();
        float newAverageFuelConsumption = (totalCarWastedFuel + wastedLiters) /
                (drivenCar.getTotalPassedDistance() + passedDistance) * 100f;

        drivenCar.setAverageFuelConsumption(newAverageFuelConsumption);
        drivenCar.setTotalPassedDistance(drivenCar.getTotalPassedDistance() + passedDistance);

        return newAverageFuelConsumption;
    }

    /**
     * Calculates session fuel consumption
     * @param passedDistance distance in km
     * @param wastedLiters fuel amount
     * @return session fuel consumption of used car
     */
    public static float CalculateSessionAverageFuelConsumption(float passedDistance,
                                                               float wastedLiters){
        return wastedLiters / passedDistance * 100f;
    }
}
