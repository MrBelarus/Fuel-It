/**
 * Car class
 */
public class Car {
    private String model;
    private float totalPassedDistance;
    private float averageFuelConsumption;
    private int factoryReleaseYear;

    public Car() {}

    public Car(String model,
               float totalPassedDistance,
               float averageFuelConsumption,
               int factoryReleaseYear) {
        this.model = model;
        this.totalPassedDistance = totalPassedDistance;
        this.averageFuelConsumption = averageFuelConsumption;
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

    public float getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    public void setAverageFuelConsumption(float averageFuelConsumption) {
        if (averageFuelConsumption < 0){
            averageFuelConsumption = 0;
        }
        this.averageFuelConsumption = averageFuelConsumption;
    }

    public int getFactoryReleaseYear() {
        return factoryReleaseYear;
    }

    public void setFactoryReleaseYear(int factoryReleaseYear) {
        this.factoryReleaseYear = factoryReleaseYear;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", totalPassedDistance=" + totalPassedDistance +
                ", averageFuelConsumption=" + averageFuelConsumption +
                ", factoryReleaseYear=" + factoryReleaseYear +
                '}';
    }
}
