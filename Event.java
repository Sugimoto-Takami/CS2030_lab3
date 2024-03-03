// Event.java
import java.util.Comparator;

public interface Event {
    Pair<ImList<Event>, ImList<Server>> process(ImList<Server> servers);

    double getTime();

    double getWaitTime();

    Customer getCustomer();

    int served();

    int left();

    String output();

    static Comparator<Event> getComparator() {
        return (Event e1, Event e2) -> {
            int timeComparison = Double.compare(e1.getTime(), e2.getTime());
            if (timeComparison != 0) {
                return timeComparison;
            }
            return Integer.compare(e1.getCustomer().getId(), e2.getCustomer().getId());
        };
    }
}

