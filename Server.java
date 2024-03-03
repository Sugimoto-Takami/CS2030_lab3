// Server.java
public class Server {
    private final double time;
    private final double serviceTime;
    private final double availableTime;
    private final ImList<Customer> queueCustomer;
    private final int queueLength;
    private final int qmax;
    private final int id;

    Server(double time, 
        double serviceTime,
        double availableTime,
        ImList<Customer> queueCustomer,
        int queueLength,
        int qmax,
        int id) {
        this.time = time;
        this.serviceTime = serviceTime;
        this.availableTime = availableTime;
        this.queueCustomer = queueCustomer;
        this.queueLength = queueLength;
        this.qmax = qmax;
        this.id = id;
    }

    public double getTime() {
        return this.time;
    }

    public double getServiceTimeFromServer() {
        return this.serviceTime;
    }

    public double getAvailableTime() {
        return this.availableTime;
    }

    public ImList<Customer> getQueueCustomer() {
        return this.queueCustomer;
    }

    public int getQueueLength() {
        return this.queueLength;
    }

    public int getId() {
        return this.id;
    }
    
    public boolean isAvailable(double time) {
        return this.availableTime <= time;
        // return this.time + this.serviceTime <= time;
    }

    public boolean isNotFull() {
        return this.queueLength < this.qmax;
    }

    public Customer nextCustomer() {
        return this.queueCustomer.get(0);
    }

    public Server addCustomer(Customer customer) {
        ImList<Customer> newQueueCustomer = this.queueCustomer.add(customer);
        int newQueueLength = this.queueLength + 1;

        return new Server(this.time,
        this.serviceTime,
        this.availableTime,
        newQueueCustomer,
        newQueueLength,
        this.qmax,
        this.id);
    }

    public Server queueStatus(int change) {
        if ((this.queueLength == 0) && (change < 0)) {
            return this;
        }
        return new Server(this.time, 
        this.serviceTime,
        this.availableTime,
        this.queueCustomer,
        this.queueLength + change,
        this.qmax,
        this.id);
    }

    public Server serve(Customer customer) {
        double serviceTime  = customer.getServiceTime();

        double newAvailableTime = Math.max(
            this.availableTime,
            customer.getArrivalTime()
        ) + serviceTime;

        int newQueueLength = this.queueLength;
        ImList<Customer> newCustomerQueue = this.queueCustomer;

        if (this.queueLength != 0) {
            newCustomerQueue = this.queueCustomer.remove(0);
            newQueueLength--;
        }

        // IF it is implemented, NullPointerException occur
        // if (this.queueLength >= 2) {
        //     newCustomerQueue = this.queueCustomer.remove(0);
        // }

        return new Server(this.time,
        serviceTime,
        newAvailableTime,
        newCustomerQueue,
        newQueueLength,
        this.qmax,
        this.id);
    }
}