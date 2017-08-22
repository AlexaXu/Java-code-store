/**
 * Created by Better on 2017/7/23.
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF connectSite;
    private final int n;
    private Boolean[] openSite;
    private int openNum = 0;


    public Percolation(int n)
    {
        if(n <= 0)
        {
            throw new IllegalArgumentException();
        }
        this.n = n;

        connectSite = new WeightedQuickUnionUF(n * n + 2);

        openSite = new Boolean[n * n];
        for (int i = 0; i < n * n; i++)
        {
            openSite[i] = false;
        }

        for(int i = 0; i < n; i++)
        {
            connectSite.union(i, n * n);
        }

        for(int i = n * n - 1; i > n * n - n - 1; i--)
        {
            connectSite.union(i, (n * n + 1));
        }
    }

    public void open(int row, int col)
    {
        if((row < 1) || (col < 1) || (row > n) || (col > n)) 
        {
            throw new IllegalArgumentException();
        }

        if(isOpen(row, col))
        {
            return;
        }

        int self = (row - 1) * n + col - 1;
        int up = (row - 2) * n + col - 1;
        int down = row * n + col - 1;
        int left = (row - 1) * n + col - 2;
        int right = (row - 1) * n + col;

        openSite[self] = true;
        openNum++;

        if(row == 1) 
        {
            connectSite.union(self, n * n);
        }
        if(row == n) 
        {
            connectSite.union(self, n * n + 1);
        }
        if(row != 1 && isOpen(row - 1, col))
        {
            connectSite.union(self, up);
        }
        if(row != n && isOpen(row + 1, col))
        {
            connectSite.union(self, down);
        }
        if(col != 1 && isOpen(row, col - 1))
        {
            connectSite.union(self, left);
        }
        if(col != n && isOpen(row, col + 1))
        {
            connectSite.union(self, right);
        }

    }

    public boolean isOpen(int row, int col)
    {
        if((row < 1) || (col < 1) || (row > n) || (col > n)) 
        {
            throw new IllegalArgumentException();
        }

        int self = (row - 1) * n + col - 1;

        return openSite[self];
    }

    public boolean isFull(int row, int col){
        if((row < 1) || (col < 1) || (row > n) || (col > n)) 
        {
            throw new IllegalArgumentException();
        }

        int self = (row - 1) * n + col - 1;

        return connectSite.connected(self, n * n);
    }

    public int numberOfOpenSites()
    {
        return openNum;
    }

    public boolean percolates()
    {
        return connectSite.connected(n * n, n * n + 1);
    }


    public static void main(String[] args)
    {
        Percolation per = new Percolation(5);
        per.open(2, 3);
        System.out.println(per.isOpen(2, 3));
    }
}
