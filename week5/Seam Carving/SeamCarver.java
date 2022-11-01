import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if(picture == null) {
            throw new IllegalArgumentException();
        }
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    private int gradient(Color a, Color b) {
        int red = a.getRed() - b.getRed();
        int green = a.getGreen() - b.getGreen();
        int blue = a.getBlue() - b.getBlue();
        return red * red + green * green + blue * blue;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
            throw new IllegalArgumentException();
        }
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
            return 1000;
        }
        return Math.sqrt(gradient(picture.get(x - 1, y), picture.get(x + 1, y))
                + gradient(picture.get(x, y - 1), picture.get(x, y + 1)));
    }

    private void exchange() {
        Picture ans = new Picture(height(), width());
        for (int j = 0; j < width(); ++j) {
            for (int i = 0; i < height(); ++i) {
                ans.setRGB(i, j, picture.getRGB(j, i));
            }
        }
        picture = ans;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int width = width();
        int height = height();
        double[][] weight = new double[width][height];
        int[][] trace = new int[width][height];
        double minWeight = Double.POSITIVE_INFINITY;
        int index = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double energy = energy(i, j);
                if (i == 0) {
                    weight[i][j] = energy;
                } else {
                    weight[i][j] = Double.POSITIVE_INFINITY;
                    for (int t = -1; t <= 1; t++) {
                        if (j + t >= 0 && j + t < height) {
                            if (weight[i][j] > weight[i - 1][j + t] + energy) {
                                weight[i][j] = weight[i - 1][j + t] + energy;
                                trace[i][j] = t;
                            }
                        }
                    }
                    if (i == width - 1) {
                        if (minWeight > weight[i][j]) {
                            minWeight = weight[i][j];
                            index = j;
                        }
                    }
                }
            }
        }
        int[] arr = new int[width];
        for (int i = width - 1; i >= 0; i--) {
            arr[i] = index;
            index += trace[i][index];
        }
        return arr;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        exchange();
        int[] arr = findHorizontalSeam();
        exchange();
        return arr;
    }

    private void checkRange(int[] seam) {
        boolean check = true;
        for (int i = 1; i < seam.length; i++) {
            if (Math.abs(seam[i] - seam[i - 1]) > 1) {
                check = false;
            }
        }
        if (!check) {
            throw new IllegalArgumentException();
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException();
        }
        if (seam.length != width()) {
            throw new IllegalArgumentException();
        }
        checkRange(seam);
        if (height() <= 1) {
            throw new IllegalArgumentException();
        }
        Picture newPicture = new Picture(width(), height() - 1);
        for (int i = 0; i < width(); i++) {
            int tmp = 0;
            for (int j = 0; j < height(); j++) {
                if (j == seam[i]) {
                    tmp = -1;
                } else {
                    newPicture.set(i, j + tmp, picture.get(i, j));
                }
            }
        }
        picture = newPicture;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        exchange();
        removeHorizontalSeam(seam);
        exchange();
    }
}