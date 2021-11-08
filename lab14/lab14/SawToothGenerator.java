package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int period) {
        this.period = period;
        state = 0;
    }

    public double next() {
        state = (state + 1);
        return normalize(state % period);
    }

    private double normalize(int v) {
        return 2.0 * v / (period * 1.0 - 1) - 1;
    }
}
