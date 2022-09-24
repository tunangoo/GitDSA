import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private WeightedQuickUnionUF uf;
    private boolean[] sttSite;
    private boolean[] isConnectedBottomRow;
    private int countOpenSite = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        uf = new WeightedQuickUnionUF(n * n + 1);
        sttSite = new boolean[n * n + 1];
        sttSite[n * n] = true;
        isConnectedBottomRow = new boolean[n * n + 1];
        for (int i = n * (n - 1); i < n * n; i++) isConnectedBottomRow[i] = true;
    }

    private int convert(int x, int y) {
        return (x - 1) * n + (y - 1);
    }

    private void connect(int u, int v) {
        if (sttSite[u] && sttSite[v]) {
            if (uf.find(u) != uf.find(v)) {
                boolean tt = false;
                if(isConnectedBottomRow[uf.find(u)] || isConnectedBottomRow[uf.find(v)]) {
                    tt = true;
                }
                uf.union(u, v);
                isConnectedBottomRow[uf.find(u)] = tt;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
        int tmp = convert(row, col);
        if (sttSite[tmp]) {
            return;
        }
        countOpenSite++;
        sttSite[tmp] = true;
        if (row == 1) {
            connect(n * n, tmp);
        }
        if (row > 1) {
            connect(tmp, convert(row - 1, col));
        }
        if (row < n) {
            connect(tmp, convert(row + 1, col));
        }
        if (col > 1) {
            connect(tmp, convert(row, col - 1));
        }
        if (col < n) {
            connect(tmp, convert(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
        return sttSite[convert(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
        if (uf.find(n * n) == uf.find(convert(row, col))) {
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpenSite;
    }

    // does the system percolate?
    public boolean percolates() {
        return isConnectedBottomRow[uf.find(n * n)];
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}

/*public class Percolation {
    private int n;
    private int[] uf;
    private boolean[] sttSite;
    private int countOpenSite = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Error: Invalid n");
        }
        this.n = n;
        uf = new int[n * n];
        for (int i = 0; i < n * n; i++) {
            uf[i] = i;
        }
        sttSite = new boolean[n * n];
    }

    private int convert(int x, int y) {
        return (x - 1) * n + (y - 1);
    }

    private int get(int i) {
        if (uf[i] == i) return i;
        return uf[i] = get(uf[i]);
    }

    private void noi(int u, int v) {
        int p = get(u);
        int q = get(v);
        if (p < q) {
            uf[q] = p;
        } else {
            uf[p] = q;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Error: Invalid row or col");
        }
        int tmp = convert(row, col);
        if (sttSite[tmp]) return;
        countOpenSite++;
        sttSite[tmp] = true;
        if (row > 1) {
            if (sttSite[convert(row - 1, col)]) {
                noi(tmp, convert(row - 1, col));
            }
        }
        if (row < n) {
            if (sttSite[convert(row + 1, col)]) {
                noi(tmp, convert(row + 1, col));
            }
        }
        if (col > 1) {
            if (sttSite[convert(row, col - 1)]) {
                noi(tmp, convert(row, col - 1));
            }
        }
        if (col < n) {
            if (sttSite[convert(row, col + 1)]) {
                noi(tmp, convert(row, col + 1));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Error: Invalid row or col");
        }
        return sttSite[convert(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Error: Invalid row or col");
        }
        int tmp = convert(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        if (get(tmp) < n) {
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpenSite;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 1; i <= n; i++) {
            if (isFull(n, i)) {
                return true;
            }
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}*/