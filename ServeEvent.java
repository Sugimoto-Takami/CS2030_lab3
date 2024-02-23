// ServeEvent.java
public class ServeEvent implements Event {
    private final Customer customer;
    private final Server server;
    private final boolean waited;
    private final double waitStartTime; // here

    public ServeEvent(Customer customer, Server server, boolean waited, double waitStartTime) {
        this.customer = customer;
        this.server = server;
        this.waited = waited;
        this.waitStartTime = waitStartTime;
    }

    @Override 
    public double getEventTime() {
        if (waited) {
            // For multiple waiting Customers
            double eventTime = server.getAvailableTime();
            int queueLength = server.getQueueLength();
            for (int i = queueLength; i > 0; i--) {
                eventTime += new DefaultServiceTime().get();
            }
            return eventTime;
        }
        return Math.max(server.getAvailableTime(), customer.getArrivalTime());
    }

    @Override
    public double getWaitTime() {
        if (!waited) {
            return 0.0;
        }
        return this.getEventTime() - this.waitStartTime;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public int served() {
        return 1;
    }

    @Override 
    public int left() {
        return 0;
    }

    @Override
    public Pair<ImList<Event>, ImList<Server>> process(ImList<Server> servers) {
        DoneEvent doneEvent = new DoneEvent(customer, server, this.getEventTime());
        ImList<Event> newEvents = new ImList<Event>().add(doneEvent);

        Server servedServer = server.serve(customer);
        servedServer = servedServer.queueStatus(-1);

        ImList<Server> updatedServers = servers.set(server.getId() - 1, servedServer);
        return new Pair<>(newEvents, updatedServers);
    }

    @Override
    public String output() {
        return String.format("%.3f %d serves by %d\n",
        this.getEventTime(), customer.getId(), server.getId());
    }
}
