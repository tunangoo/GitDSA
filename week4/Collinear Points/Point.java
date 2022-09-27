import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(x, y, that.x, that.y);
    }

    @Override
    public String toString() {
        return ("(" + x + ", " + y + ")");
    }

    public int compareTo(Point that) {
        if (y == that.y) {
            if (x < that.x) {
                return -1;
            } else if (x == that.x) {
                return 0;
            } else {
                return 1;
            }
        } else if (y < that.y) {
            return -1;
        } else {
            return 1;
        }
    }

    public double slopeTo(Point that) {
        if (x == that.x) {
            if (y == that.y) {
                return Double.NEGATIVE_INFINITY;
            } else {
                return Double.POSITIVE_INFINITY;
            }
        } else {
            if (y == that.y) {
                return 0.0;
            } else {
                return (double) (that.y - y) / (double) (that.x - x);
            }
        }
    }

    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point a, Point b) {
            double aSlope = slopeTo(a);
            double bSlope = slopeTo(b);
            if (aSlope < bSlope) {
                return -1;
            } else if (aSlope > bSlope) {
                return 1;
            } else {
                return 0;
            }
        }

    }

    public static void main(String[] args) {

    }
}