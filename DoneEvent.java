// DoneEvent.java
public class DoneEvent implements Event {
    private final Customer customer;
    private final Server server;
    private final double serveStartTime;

    public DoneEvent(Customer customer, Server server, double serveStartTime) {
        this.customer = customer;
        this.server = server;
        this.serveStartTime = serveStartTime;
    }

    @Override
    public double getEventTime() {
        return serveStartTime + new DefaultServiceTime().get();
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
        // update server's condition?
        return new Pair<>(new ImList<Event>(), servers);
    }

    @Override
    public String output() {
        return String.format("%.3f %d done serving by %d\n", 
        this.getEventTime(), customer.getId(), server.getId());
    }
}