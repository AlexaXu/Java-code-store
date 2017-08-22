import java.util.Scanner;

/**
 * Created by Better on 2017/7/25.
 */

public class SocialNetWork {
    private int n;
    private int[] netWork;
    private int[] size;
    private int connectedNum = 0;

    public SocialNetWork(int n) {
        this.n = n;

        netWork = new int[n];
        size = new int[n];
        for(int i = 0; i < n; i++) {
            netWork[i] = i;
            size[i] = 1;
        }
    }

    public int root(int p) {
        while(p != netWork[p]) {
            netWork[p] = netWork[netWork[p]];
            p = netWork[p];
        }

        return netWork[p];
    }


    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);

        if(i == j) return;

        if(size[p] < size[q]) {
            netWork[i] = j;
            size[q] += size[p];

            if (connectedNum < size[q]) {
                connectedNum = size[q];
            }
        }else {
            netWork[j] = i;
            size[p] += size[q];

            if (connectedNum < size[p]) {
                connectedNum = size[p];
            }
        }
    }

    private boolean allConnected() {
        if(connectedNum == n) return true;
        else return false;
    }

    public static void main(String [] args) {
        Scanner StdIn = new Scanner(System.in);

        int n = StdIn.nextInt();

        SocialNetWork socialNetWork = new SocialNetWork(n);

        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            String time = StdIn.readString();

            socialNetWork.union(p, q);

            if (socialNetWork.allConnected()) {
                StdOut.print("All members are connected at: " + time);
                break;
            }
        }
    }
}
