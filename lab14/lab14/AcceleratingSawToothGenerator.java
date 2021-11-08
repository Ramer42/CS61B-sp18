package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(int period, double factor) {
        this.period = period;
        this.factor = factor;
        state = 0;
    }

    public double next() {
        state = (state + 1);
        if (state > period - 1) {
            period = (int)Math.floor(period * factor);
            state = 0;
        }
        return normalize(state % period);
    }

    private double normalize(int v) {
        return 2.0 * v / (period * 1.0 - 1) - 1;
    }
}
