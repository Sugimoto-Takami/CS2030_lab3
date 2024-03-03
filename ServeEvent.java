// ServeEvent.java
public class ServeEvent implements Event {
    private final double time;
    private final Customer customer;
    private final Server server;
    private final boolean waited;

    public ServeEvent(double time, Customer customer, Server server, boolean waited) {
        this.time = time;
        this.customer = customer;
        this.server = server;
        this.waited = waited;
    }

    @Override 
    public double getTime() {
        return this.time;
    }

    @Override
    public double getWaitTime() {
        if (!waited) {
            return 0.0;
        }
        return this.time - customer.getArrivalTime();
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
        ImList<Event> newEvents = new ImList<Event>();
        
        // UPDATE!!!
        Server server = servers.get(this.server.getId() - 1);
        Server servedServer = server.serve(this.customer);

        // add
        if (servedServer.getQueueLength() != 0) {
            ServeEvent rescheduledEvent = new ServeEvent(
                servedServer.getAvailableTime(),
                servedServer.nextCustomer(),
                servedServer,
                waited
            );
            newEvents = newEvents.add(rescheduledEvent);
        }

        double doneTime = this.time + servedServer.getServiceTimeFromServer();
        DoneEvent doneEvent = new DoneEvent(doneTime, customer, server);
        newEvents = newEvents.add(doneEvent);

        ImList<Server> updatedServers = servers.set(server.getId() - 1, servedServer);
        return new Pair<>(newEvents, updatedServers);
    }

    @Override
    public String output() {
        return String.format("%.3f %d serves by %d\n",
        this.time, customer.getId(), server.getId());
    }
}
