// ArriveEvent.java

public class ArriveEvent implements Event {
    private final Customer customer;

    // ArriveEventに関しては, timeはcustomerから取ればよい.
    public ArriveEvent(Customer customer) {
        this.customer = customer;
    }

    @Override 
    public double getTime() {
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
        ServeEvent serveEvent;
        WaitEvent waitEvent;
        LeaveEvent leaveEvent;

        ImList<Event> newEvents = new ImList<>();

        boolean isServed = false;
        boolean isWait = false;

        for (int i = 0; i < servers.size(); i++) {
            Server server = servers.get(i);
            if (server.isAvailable(this.getTime())) {
                isServed = true;
                serveEvent = new ServeEvent(this.getTime(), this.customer, server, false);
                newEvents = newEvents.add(serveEvent);
                break;
            }
        }

        if (!isServed) {
            for (int i = 0; i < servers.size(); i++) {
                Server server = servers.get(i);
                if (server.isNotFull()) {
                    isWait = true;
                    // server = server.queueStatus(1);
                    waitEvent = new WaitEvent(this.getTime(), this.customer, server);
                    newEvents = newEvents.add(waitEvent);
                    break;
                }
            }
        }

        if (!(isServed || isWait)) {
            leaveEvent = new LeaveEvent(this.customer);
            newEvents = newEvents.add(leaveEvent);
        }

        return new Pair<>(newEvents, servers);
    }

    @Override
    public String output() {
        return String.format("%.3f %d arrives\n",
                this.getTime(), customer.getId());
    }
}
