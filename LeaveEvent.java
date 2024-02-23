// LeaveEvent.java
public class LeaveEvent implements Event {
    private final Customer customer;

    public LeaveEvent(Customer customer) {
        this.customer = customer;
    }

    @Override
    public double getEventTime() {
        return customer.getArrivalTime();
    }

    @Override
    public double getWaitTime() {
        return 0.0;
    }

    @Override
    public Customer getCustomer() {
        return this.customer;
    }

    @Override
    public int served() {
        return 0;
    }

    @Override 
    public int left() {
        return 1;
    }
    
    @Override
    public Pair<ImList<Event>, ImList<Server>> process(ImList<Server> servers) {
        return new Pair<>(new ImList<Event>(), servers);
    }

    @Override
    public String output() {
        return String.format("%.3f %d leaves\n", 
        customer.getArrivalTime(), customer.getId());
        
    }
}
