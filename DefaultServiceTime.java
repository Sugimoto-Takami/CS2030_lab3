// DefaultServiceTime.java
import java.util.function.Supplier;

class DefaultServiceTime implements Supplier<Double> {
    public Double get() {
        return  Math.random();
        //return 1.0;
    }
}
