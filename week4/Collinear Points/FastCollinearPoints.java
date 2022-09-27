import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] pointss) {
        if (pointss == null) {
            throw new IllegalArgumentException();
        }
        Point[] points = Arrays.copyOf(pointss, pointss.length);
        int n = pointss.length;
        if (n == 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < n; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = i + 1; j < n; j++) {
                if (points[i].equals(points[j])) {
                    throw new IllegalArgumentException();
                }
            }
        }
        List<LineSegment> segmentsList = new ArrayList<LineSegment>();
        Arrays.sort(points);
        for (int i = 0; i < n; i++) {
            if (i == 0 || points[i].compareTo(points[i - 1]) != 0) {
                Point[] points1 = Arrays.copyOf(points, points.length);
                Arrays.sort(points1, points[i].slopeOrder());
                int t = 0;
                for (int j = 1; j < points1.length - 1; j++) {
                    if (j >= t) {
                        t = j;
                        boolean isBeginning = true;
                        while (t < points1.length && points[i].slopeTo(points1[j]) == points[i].slopeTo(points1[t])) {
                            if (points[i].compareTo(points1[t]) > 0) isBeginning = false;
                            t++;
                        }
                        if (isBeginning && t - j >= 3) {
                            segmentsList.add(new LineSegment(points[i], points1[t - 1]));
                        }
                    }

                }
            }
        }
        segments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }

    public static void main(String[] args) {
        /*
        Point a[] = new Point[5];
        a[0] = new Point(1, 1);
        a[1] = new Point(3, 3);
        a[2] = new Point(0, 0);
        a[3] = new Point(4, 4);
        a[4] = new Point(2, 2);
        //Arrays.sort(a);
        Arrays.sort(a, a[0].slopeOrder());
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i].toString());
        }
        FastCollinearPoints b = new FastCollinearPoints(a);
        b.segments();
        for(int i = 0; i < b.segments.length; i++) {
            System.out.println(b.segments[i].toString());
        }
        */
    }
}