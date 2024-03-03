import java.util.Comparator;

public class CustomerCompare implements Comparator<Customer> {
    public int compare(Customer c1, Customer c2) {
        int timeComparison = Double.compare(c1.getArrivalTime(), c2.getArrivalTime());
        return timeComparison;
    }
}
