// Server.java
public class Server {
    private final double availableTime;
    private final int queueLength;
    private final int qmax;
    private final int id;

    Server(double availableTime, int queueLength, int qmax, int id) {
        this.availableTime = availableTime;
        this.queueLength = queueLength;
        this.qmax = qmax;
        this.id = id;
    }

    public double getAvailableTime() {
        return this.availableTime;
    }

    public int getQueueLength() {
        return this.queueLength;
    }

    public int getId() {
        return this.id;
    }

    public boolean isAvailable(double time) {
        return this.availableTime <= time;
    }

    public boolean isNotFull() {
        return this.queueLength < this.qmax;
    }

    public Server queueStatus(int change) {
        if ((this.queueLength == 0) && (change < 0)) {
            return this;
        }
        return new Server(this.availableTime, this.queueLength + change, this.qmax, this.id);
    }

    public Server serve(Customer customer) {
        double newAvailableTime = Math.max(
            this.availableTime, 
            customer.getArrivalTime()
        ) + customer.getServiceTime();
        return new Server(newAvailableTime, this.queueLength, this.qmax, this.id);
    }
}