// WaitEvent.java
public class WaitEvent implements Event {
    private final Customer customer;
    private final Server server;

    public WaitEvent(Customer customer, Server server) {
        this.customer = customer;
        this.server = server;
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
        ServeEvent serveEvent = new ServeEvent(customer, server, true, this.getEventTime());
        ImList<Event> newEvents = new ImList<Event>().add(serveEvent);

        // here
        Server waitedServer = server.queueStatus(1);
        ImList<Server> updatedServers = servers.set(server.getId() - 1, waitedServer);
        return new Pair<>(newEvents, updatedServers);
    }

    @Override
    public String output() {
        return String.format("%.3f %d waits at %d\n", 
        customer.getArrivalTime(), customer.getId(), server.getId());
    }
    
}
