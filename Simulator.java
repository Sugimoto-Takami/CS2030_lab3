// Simulator.java

import java.util.function.Supplier;

public class Simulator {
    private final int numOfServers;
    private final int qmax;
    private final ImList<Double> arrivalTimes;
    private final Supplier<Double> serviceTime;

    public Simulator(
        int numOfServers,
        int qmax,
        ImList<Double> arrivalTimes,
        Supplier<Double> serviceTime) {
        this.numOfServers = numOfServers;
        this.qmax = qmax;
        this.arrivalTimes = arrivalTimes;
        this.serviceTime = serviceTime;
    }

    private ImList<Server> initializeServers(int numOfServers) {
        ImList<Server> tempServers = new ImList<>();
        for (int i = 0; i < numOfServers; i++) {
            tempServers = tempServers.add(
                new Server(0.0, 0, qmax, i + 1)
                );
        }
        return tempServers;
    }

    public String simulate() {
        ImList<Server> servers = this.initializeServers(this.numOfServers);
        PQ<Event> pq = new PQ<Event>(Event.getComparator());
        int numServed = 0;
        int numLeft = 0; 
        double timeWait = 0.0;

        // initialize eventQueue
        for (int i = 0; i < arrivalTimes.size(); i++) {
            double arrivalTime = arrivalTimes.get(i);
            Customer customer = new Customer(arrivalTime, serviceTime, i + 1);
            ArriveEvent arriveEvent = new ArriveEvent(customer);
            pq = pq.add(arriveEvent);
        }

        String output = "";

        while (!pq.isEmpty()) {
            Pair<Event, PQ<Event>> pair = pq.poll();
            Event event = pair.first();
            pq = pair.second();

            Pair<ImList<Event>, ImList<Server>> newEvents = event.process(servers);

            for (Event newEvent : newEvents.first()) {
                pq = pq.add(newEvent);
                timeWait += newEvent.getWaitTime();
                numServed += newEvent.served();
                numLeft += newEvent.left();
                // if (newEvent instanceof ServeEvent) {
                //     numServed++;
                // } else if (newEvent instanceof LeaveEvent) {
                //     numLeft++;
                // }
            }

            ImList<Server> updatedServers = newEvents.second();
            servers = updatedServers;

            output += event.output();  // THIS IS!
        }
        double averageWaitTime =  timeWait / numServed;
        output += String.format("[%.3f %d %d]", averageWaitTime, numServed, numLeft);
        return output;
    }
}