import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;

public class SeamCarver {
    private Picture picture;
//    private double[][] energyMat;
    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    // current picture
    public Picture picture() { return this.picture; }

    // width of current picture
    public int width() { return this.picture.width(); }

    // height of current picture
    public int height() { return this.picture.height(); }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int xl, xr, yh, yl;
        if (x == 0) { xl = width() - 1; } else { xl = x - 1; }
        if (x == width() - 1) { xr = 0; } else { xr = x + 1; }
        if (y == 0) { yh = height() - 1; } else { yh = y - 1; }
        if (y == height() - 1) { yl = 0; } else { yl = y + 1; }
        int energyHor = pow(getR(xl, y) - getR(xr, y)) +
                pow(getG(xl, y) - getG(xr, y)) + pow(getB(xl, y) - getB(xr, y));
        int energyVer = pow(getR(x, yh) - getR(x, yl)) +
                pow(getG(x, yh) - getG(x, yl)) + pow(getB(x, yh) - getB(x, yl));
        return energyHor + energyVer;
    }

    private int getR(int x, int y) { return (this.picture.getRGB(x,y) >> 16) & 0xFF; }
    private int getG(int x, int y) { return (this.picture.getRGB(x,y) >> 8) & 0xFF; }
    private int getB(int x, int y) { return this.picture.getRGB(x,y) & 0xFF; }
    private int pow(int x) { return x * x; }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        Picture transpose = new Picture(height(), width());
        Picture pictureCopy = new Picture(width(), height());
//        double[][] energyMatTrans = new double[height()][width()];
//        double[][] energyMatCopy = new double[width()][height()];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                transpose.setRGB(j, i, this.picture.getRGB(i, j));
                pictureCopy.setRGB(i, j, this.picture.getRGB(i, j));
//                energyMatCopy[i][j] = energyMat[i][j];
//                energyMatTrans[j][i] = energyMat[i][j];
            }
        }
        this.picture = transpose;
//        this.energyMat = energyMatTrans;
        int[] seam = findVerticalSeam();
        this.picture = pictureCopy;
//        this.energyMat = energyMatCopy;
        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] energyMat = new double[width()][height()];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                energyMat[i][j] = energy(i, j);
            }
        }

        double[][] minCostMat = new double[width()][height()];
        int[][] prePixelMat = new int[width()][height()];
        for (int i = 0; i < width(); i++) {
            minCostMat[i][0] = energyMat[i][0];
        }
        for (int j = 1; j < height(); j++) {
            for (int i = 0; i < width(); i++) {
                double minCost = Double.MAX_VALUE;
                for (int n = -1; n < 2; n++) {
                    if (i + n < 0 || i + n > width() - 1)  { continue; }
                    if (minCostMat[i + n][j - 1] < minCost) {
                        minCost = minCostMat[i + n][j - 1];
                        minCostMat[i][j] = minCost + energyMat[i][j];
                        prePixelMat[i][j] = i + n;
                    }
                }
            }
        }

        double minCostTotal = Double.MAX_VALUE;
        int minEndIndex = 0;
        for (int i = 0; i < width(); i++) {
            if (minCostMat[i][height() - 1] < minCostTotal) {
                minCostTotal = minCostMat[i][height() - 1];
                minEndIndex = i;
            }
        }

        int[] seam = new int[height()];
        seam[height() - 1] = minEndIndex;
        for (int j = height() - 1; j > 0; j--) {
            minEndIndex = prePixelMat[minEndIndex][j];
            seam[j - 1] = minEndIndex;
        }
        return seam;
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        this.picture = SeamRemover.removeHorizontalSeam(this.picture, seam);
    }
    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        this.picture = SeamRemover.removeVerticalSeam(this.picture, seam);
    }
}
