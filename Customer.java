// Customer.java
import java.util.function.Supplier;

public class Customer {
    private final double arrivalTime;
    private final Supplier<Double> serviceTime;
    private final int id;

    public Customer(double arrivalTime, Supplier<Double> serviceTime, int id) {
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.id = id;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }
    
    public double getServiceTime() {
        return this.serviceTime.get();
    }

    public int getId() {
        return this.id;
    }
}