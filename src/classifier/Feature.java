package classifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Feature {
    private static Random random = new Random(0);

    private class Connection {
        int x;
        int y;
        boolean sign;
        Connection(int x, int y, boolean sign) {
            this.x = x;
            this.y = y;
            this.sign = sign;
        }
    }

    private double weight;

    private List<Connection> connections = new ArrayList<>();

    public Feature(int width, int height, int connectionCount) {
        for(int a = 0;a < connectionCount;a++) {
            connections.add(new Connection(random.nextInt(width), random.nextInt(height), random.nextBoolean()));
        }
        this.weight = (random.nextDouble()) - 1;
    }

    double evaluateFeature(boolean image[][]) {
        int sum = 0;
        for(Connection c : connections) {
            sum += image[c.x][c.y] ? 1 : 0;
        }
        return sum > 2 ? 1 : 0;
    }

    public double getWeight() {
        return weight;
    }

    public void updateWeight(double delta) {
        weight -= delta;
    }

}
