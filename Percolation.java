import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int size;
    private final WeightedQuickUnionUF alg;
    private boolean[] sites;
    private int countOpenSites;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Invalid n size grid");
        size = n;
        alg = new WeightedQuickUnionUF((n * n) + 2);
        sites = new boolean[(n * n) + 1];
        for (int i = 1; i <= n; i++) {
            alg.union(0, i);
            alg.union((n * n) + 1, (n * n) + 1 - i);
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        // test class
    }

    public void open(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size)
            throw new IllegalArgumentException("Invalid paramenter");
        if (sites[getIndex(row, col)]) return;
        if (col + 1 <= size && isOpen(row, col + 1)) {
            alg.union(getIndex(row, col), getIndex(row, col + 1));
        }
        if (col - 1 > 0 && isOpen(row, col - 1)) {
            alg.union(getIndex(row, col), getIndex(row, col - 1));
        }
        if (row + 1 <= size && isOpen(row + 1, col)) {
            alg.union(getIndex(row, col), getIndex(row + 1, col));
        }
        if (row - 1 > 0 && isOpen(row - 1, col)) {
            alg.union(getIndex(row, col), getIndex(row - 1, col));
        }
        countOpenSites++;
        sites[getIndex(row, col)] = true;
    }

    public boolean percolates() {
        return alg.connected(0, (size * size) + 1);
    }

    public boolean isFull(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size)
            throw new IllegalArgumentException("Invalid paramenter");
        return alg.connected(0, getIndex(row, col)) && isOpen(row, col);
    }

    private int getIndex(int row, int col) {
        return (row - 1) * size + col;
    }

    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size)
            throw new IllegalArgumentException("Invalid paramenter");
        return sites[getIndex(row, col)];
    }

    public int numberOfOpenSites() {
        return countOpenSites;
    }
}