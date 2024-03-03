// DoneEvent.java
public class DoneEvent implements Event {
    private final double time;
    private final Customer customer;
    private final Server server;
    // private final double serveStartTime;

    public DoneEvent(double time, Customer customer, Server server) {
        this.time = time;
        this.customer = customer;
        this.server = server;
    }

    @Override 
    public double getTime() {
        return this.time;
    }

    @Override
    public double getWaitTime() {
        return 0.0;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public int served() {
        return 0;
    }

    @Override 
    public int left() {
        return 0;
    }

    @Override
    public Pair<ImList<Event>, ImList<Server>> process(ImList<Server> servers) {
        return new Pair<>(new ImList<Event>(), servers);
    }

    @Override
    public String output() {
        return String.format("%.3f %d done serving by %d\n", 
        this.time, customer.getId(), server.getId());
    }
}