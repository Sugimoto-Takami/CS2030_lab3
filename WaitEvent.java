// WaitEvent.java

public class WaitEvent implements Event {
    private final double time;
    private final Customer customer;
    private final Server server;

    public WaitEvent(double time, Customer customer, Server server) {
        this.time = time;
        this.customer = customer;
        this.server = server;
    }

    // should change
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

        ImList<Event> newEvents = new ImList<Event>();
        Server newServer;
        
        newServer = this.server.addCustomer(customer);
        servers = servers.set(newServer.getId() - 1, newServer);

        double serveTime;
        ServeEvent serveEvent;

        // some customers have already waited
        // only server is updated
        if (this.server.getQueueLength() != 0) {
            return new Pair<>(new ImList<Event>(), servers);

        } else {
            serveTime = server.getAvailableTime();
            serveEvent = new ServeEvent(serveTime, this.customer, newServer, true);
        }
        newEvents = newEvents.add(serveEvent);
        return new Pair<>(newEvents, servers);
    }

    @Override
    public String output() {
        return String.format("%.3f %d waits at %d\n", 
        this.time, customer.getId(), server.getId());
    }
    
}
