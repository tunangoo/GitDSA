import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] pointss) {
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
            for (int j = i + 1; j < n; j++) {
                for (int u = j + 1; u < n; u++) {
                    for (int v = u + 1; v < n; v++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[u])
                                && points[i].slopeTo(points[j]) == points[i].slopeTo(points[v])) {
                            LineSegment tmp = new LineSegment(points[i], points[v]);
                            if (!segmentsList.contains(tmp)) {
                                segmentsList.add(tmp);
                            }
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
        Point[] a = new Point[5];
        a[0] = new Point(0, 1);
        a[1] = new Point(0, 3);
        a[2] = new Point(0, 2);
        a[3] = new Point(0, 4);
        a[4] = new Point(1, 4);
        BruteCollinearPoints aaa = new BruteCollinearPoints(a);
        System.out.println(aaa.numberOfSegments());
        LineSegment[] tmp = aaa.segments();
        System.out.println(aaa.numberOfSegments());
        System.out.println(aaa.numberOfSegments());
        */
    }
}