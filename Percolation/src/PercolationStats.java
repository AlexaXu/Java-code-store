/**
 * Created by Better on 2017/7/24.
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats 
{
    private double[] openProb;
    private final int trails;
    private final double mean;
    private final double stddev;

    public PercolationStats(int n, int trials) 
    {
        if(n <= 0 || trials <= 0) 
        {
            throw new IllegalArgumentException();
        }

        this.trails = trials;
        openProb = new double[trials];

        for(int i = 0; i < trials; i++) 
        {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) 
            {
                int a = StdRandom.uniform(1, n + 1);
                int b = StdRandom.uniform(1, n + 1);

                while (percolation.isOpen(a, b)) 
                {
                    a = StdRandom.uniform(1, n + 1);
                    b = StdRandom.uniform(1, n + 1);
                }

                percolation.open(a,b);

            }
            openProb[i] = (double)percolation.numberOfOpenSites() / (n * n);
        }

        mean = StdStats.mean(openProb);
        stddev = StdStats.stddev(openProb);

    }
    public double mean() 
    {
        return mean;
    }
    public double stddev() 
    {
        return stddev;
    }
    public double confidenceLo() 
    {
        return mean - (1.96 * stddev) / Math.sqrt(trails);
    }
    public double confidenceHi() {
        return mean + (1.96 * stddev) / Math.sqrt(trails);
    }

    public static void main(String[] args) 
    {

        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, trails);

        StdOut.printf("%-24s", "mean");
        StdOut.printf("= %.16f\n", percolationStats.mean());
        StdOut.printf("%-24s", "stddev");
        StdOut.printf("= %.18f\n", percolationStats.stddev());
        StdOut.printf("%-24s", "95% confidence interval");
        StdOut.printf("= %.16f, %.16f\n", percolationStats.confidenceLo(),
                    percolationStats.confidenceHi());
    }
}
