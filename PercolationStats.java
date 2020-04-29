import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int t;
    private final double mean;
    private final double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Invalid Argument");
        double [] data = new double[trials];
        t = trials;
        for (int i = 0; i < trials; i++) {
            Percolation test = new Percolation(n);
            while (!test.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                test.open(row, col);

            }
            data[i] = test.numberOfOpenSites() / (double) (n * n);
        }
        mean = StdStats.mean(data);
        stddev = StdStats.stddev(data);
    }

    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(20, 10000);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println(
                "95% confidence interval = [" + stats.confidenceLo() + " , " + stats.confidenceHi()
                        + "]");
    }


    public double mean() {
        return mean;
    }


    public double stddev() {
        return stddev;
    }


    public double confidenceLo() {
        return (mean - (CONFIDENCE_95 * stddev) / Math.sqrt(t));
    }

    public double confidenceHi() {
        return (mean + (CONFIDENCE_95 * stddev) / Math.sqrt(t));
    }

}