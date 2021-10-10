package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int side_length;
    private int numberOfSites;
    private WeightedQuickUnionUF quickUnion;
    private WeightedQuickUnionUF quickUnionNew;  // create a quickUnion Without Bottom Site
    private boolean[] isOpenList;
    private int numberOfOpenSites_;
    public Percolation(int N) throws java.lang.IllegalArgumentException{                 // create N-by-N grid, with all sites initially blocked
        if (N < 1){
            throw new java.lang.IllegalArgumentException("Error:The side length must be at least 1");
        }
        side_length = N;
        numberOfSites = N * N + 2;
        isOpenList = new boolean[numberOfSites - 2];  // default values of boolean arrays created by 'new boolean[]' method are null
        numberOfOpenSites_ = 0;
        quickUnion = new WeightedQuickUnionUF(numberOfSites);
        quickUnionNew = new WeightedQuickUnionUF(numberOfSites - 1);  // Create a new quickUnion without bottom virtual site to solve backwash
    }

    private int map2Dto1D(int row, int col){
        return side_length * row + col;
    }

    public void open(int row, int col) throws java.lang.IndexOutOfBoundsException{       // open the site (row, col) if it is not open already
        if (row > side_length - 1 || col > side_length - 1 || row < 0 || col < 0){
            throw new java.lang.IndexOutOfBoundsException("Error:The index is out of bounds:0-" + (side_length - 1));
        }
        int index = map2Dto1D(row, col);
        if (isOpen(row, col)) { return; } else {
            isOpenList[index] = true;
            numberOfOpenSites_ += 1;
        }
        if (row - 1 == -1) {
            quickUnion.union(index, numberOfSites - 2);
            quickUnionNew.union(index, numberOfSites - 1 - 1); // In the new quickUnion, use last element to represent top virtual site
        } else if (isOpen(row - 1, col)) {
            quickUnion.union(index, map2Dto1D(row - 1, col));
            quickUnionNew.union(index, map2Dto1D(row - 1, col));
        }
        if (row + 1 == side_length) {
            quickUnion.union(index, numberOfSites - 1);
        } else if (isOpen(row + 1, col)) {
            quickUnion.union(index, map2Dto1D(row + 1, col));
            quickUnionNew.union(index, map2Dto1D(row + 1, col));
        }
        if (col - 1 >= 0 && isOpen(row, col - 1)) {
            quickUnion.union(index, map2Dto1D(row, col - 1));
            quickUnionNew.union(index, map2Dto1D(row, col - 1));
        }
        if (col + 1 <= side_length - 1 && isOpen(row, col + 1)) {
            quickUnion.union(index, map2Dto1D(row, col + 1));
            quickUnionNew.union(index, map2Dto1D(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) throws java.lang.IndexOutOfBoundsException{  // is the site (row, col) open?
        if (row > side_length - 1 || col > side_length - 1 || row < 0 || col < 0){
            throw new java.lang.IndexOutOfBoundsException("Error:The index is out of bounds:0-" + (side_length - 1));
        }
        return isOpenList[map2Dto1D(row, col)];
    }

    public boolean isFull(int row, int col) throws java.lang.IndexOutOfBoundsException{    // is the site (row, col) full?
        if (row > side_length - 1 || col > side_length - 1 || row < 0 || col < 0){
            throw new java.lang.IndexOutOfBoundsException("Error:The index is out of bounds:0-" + (side_length - 1));
        }
        return quickUnionNew.connected(map2Dto1D(row, col), numberOfSites - 1 - 1);  // Use new quickUnion to check isFull
    }

    public int numberOfOpenSites() {  // number of open sites
        return numberOfOpenSites_;
    }
    public boolean percolates() {   // does the system percolate?
        return quickUnion.connected(numberOfSites - 1, numberOfSites - 2);
    }

    public static void main(String[] args) {   // use for unit testing (not required)
        Percolation p1 = new Percolation(5);
//        p1.open(3,4);
//        p1.open(2,4);
//        p1.open(2,2);
//        p1.open(2,3);
//        p1.open(0,2);
//        p1.open(1,2);
//        p1.open(4,4);
//        p1.open(4,0);
//        p1.open(3,0);
//        p1.open(6,2);
        System.out.println(p1.isFull(2,2));
        System.out.println(p1.isFull(4,4));
        System.out.println(p1.percolates());
        System.out.println(p1.isFull(3,0));
    }
}
//package hw2;
//
//import edu.princeton.cs.algs4.WeightedQuickUnionUF;
//
//public class Percolation {
//    private int side_length;
//    private int numberOfSites;
//    private WeightedQuickUnionUF quickUnion;
//    private boolean[] isOpenList;
//    private int numberOfOpenSites_;
//    public Percolation(int N) throws java.lang.IllegalArgumentException{                 // create N-by-N grid, with all sites initially blocked
//        if (N < 1){
//            throw new java.lang.IllegalArgumentException("Error:The side length must be at least 1");
//        }
//        side_length = N;
//        numberOfSites = N * N + 2;
//        isOpenList = new boolean[numberOfSites - 2];  // default values of boolean arrays created by 'new boolean[]' method are null
//        numberOfOpenSites_ = 0;
//        quickUnion = new WeightedQuickUnionUF(numberOfSites);
//    }
//
//    private int map2Dto1D(int row, int col){
//        return side_length * row + col;
//    }
//
//    public void open(int row, int col) throws java.lang.IndexOutOfBoundsException{       // open the site (row, col) if it is not open already
//        if (row > side_length - 1 || col > side_length - 1 || row < 0 || col < 0){
//            throw new java.lang.IndexOutOfBoundsException("Error:The index is out of bounds:0-" + (side_length - 1));
//        }
//        int index = map2Dto1D(row, col);
//        if (isOpen(row, col)) { return; } else {
//            isOpenList[index] = true;
//            numberOfOpenSites_ += 1;
//        }
//        if (row - 1 == -1) {    //
//            quickUnion.union(index, numberOfSites - 2);  // Use the numberOfSites - 2(second-to-last) element of quickUnion to represent top virtual site
//        } else if (isOpen(row - 1, col)) {
//            quickUnion.union(index, map2Dto1D(row - 1, col));
//        }
//        if (row + 1 == side_length) {
//            quickUnion.union(index, numberOfSites - 1);  // Use the numberOfSites - 1(last) element of quickUnion to represent bottom virtual site
//        } else if (isOpen(row + 1, col)) {
//            quickUnion.union(index, map2Dto1D(row + 1, col));
//        }
//        if (col - 1 >= 0 && isOpen(row, col - 1)) {
//            quickUnion.union(index, map2Dto1D(row, col - 1));
//        }
//        if (col + 1 <= side_length - 1 && isOpen(row, col + 1)) {
//            quickUnion.union(index, map2Dto1D(row, col + 1));
//        }
//    }
//
//    public boolean isOpen(int row, int col) throws java.lang.IndexOutOfBoundsException{  // is the site (row, col) open?
//        if (row > side_length - 1 || col > side_length - 1 || row < 0 || col < 0){
//            throw new java.lang.IndexOutOfBoundsException("Error:The index is out of bounds:0-" + (side_length - 1));
//        }
//        return isOpenList[map2Dto1D(row, col)];
//    }
//
//    public boolean isFull(int row, int col) throws java.lang.IndexOutOfBoundsException{    // is the site (row, col) full?
//        if (row > side_length - 1 || col > side_length - 1 || row < 0 || col < 0){
//            throw new java.lang.IndexOutOfBoundsException("Error:The index is out of bounds:0-" + (side_length - 1));
//        }
//        return quickUnion.connected(map2Dto1D(row, col), numberOfSites - 2);
//    }
//
//    public int numberOfOpenSites() {  // number of open sites
//        return numberOfOpenSites_;
//    }
//    public boolean percolates() {   // does the system percolate?
//        return quickUnion.connected(numberOfSites - 1, numberOfSites - 2);
//    }
//
//    public static void main(String[] args) {   // use for unit testing (not required)
//        PercolationOriginal p1 = new PercolationOriginal(5);
//        p1.open(3,4);
//        p1.open(2,4);
//        p1.open(2,2);
//        p1.open(2,3);
//        p1.open(0,2);
//        p1.open(1,2);
//        p1.open(6,2);
//        System.out.println(p1.isFull(2,2));
//        System.out.println(p1.isFull(4,4));
//        System.out.println(p1.percolates());
//
//    }
//}
