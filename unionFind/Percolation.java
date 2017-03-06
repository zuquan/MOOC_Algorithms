package unionFind;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.UF;

/**
 * @author Neil
 * @Date 2017-02-12 exercise of Union-Find Application in Algorithm 1 use UF class to model the board
 */
public class Percolation {

	private int N; // size of board
	private UF uf; // board
	private boolean open[]; // to record the status of open for each site

	private boolean isfull[]; // to record the status of full for each site
	private boolean ispercolate;

	// to record the status of connecting to buttom
	private boolean connectBottom[];

	/**
	 * create N-by-N grid, with all sites blocked
	 */
	public Percolation(int N) {
		if (N <= 0) {
			throw new IllegalArgumentException("Construction of Percolation, N<0");
		}
		this.N = N;
		uf = new UF(N * N + 2);
		open = new boolean[N * N + 1];
		connectBottom = new boolean[N * N + 1];
		// for (int i = 1; i < N * N; i++) {
		// isopen[i] = false;
		// }
		isfull = new boolean[N * N + 1];
		ispercolate = false;
	}

	/**
	 * open site (row i, column j) if it is not open already
	 */
	public void open(int i, int j) {
		validate(i, j);
		setOpen(i, j);

		// down case
		if (i == N) {
			setConnectButtom(i, j);
			// uf.union(locate(i, j), N * N + 1); // TBD, maybe some issue here.
		}

		// up case
		if (i == 1) {
			uf.union(locate(i, j), 0);
		}

		// check up
		if (i > 1 && isOpen(i - 1, j)) {
			uf.union(locate(i, j), locate((i - 1), j));
			if (!isConnectButtom(i, j) && isConnectButtom(i - 1, j)) setConnectButtom(i, j);
		}

		// check down
		if (i < N && isOpen(i + 1, j)) {
			uf.union(locate(i, j), locate((i + 1), j));
			if (!isConnectButtom(i, j) && isConnectButtom(i + 1, j)) setConnectButtom(i, j);
		}

		// check left
		if (j > 1 && isOpen(i, j - 1)) {
			uf.union(locate(i, j), locate(i, (j - 1)));
			if (!isConnectButtom(i, j) && isConnectButtom(i, j - 1)) setConnectButtom(i, j);
		}

		// check right
		if (j < N && isOpen(i, j + 1)) {
			uf.union(locate(i, j), locate(i, (j + 1)));
			if (!isConnectButtom(i, j) && isConnectButtom(i, j + 1)) setConnectButtom(i, j);
		}

		// check if percolate
		if (!ispercolate && isFull(i, j) && isConnectButtom(i, j)) ispercolate = true;

	}

	/**
	 * is site (row i, column j) open?
	 */
	public boolean isOpen(int i, int j) {
		validate(i, j);
		return open[locate(i, j)];
	}

	/**
	 * set row i, column j to open
	 * */
	private void setOpen(int i, int j) {
		open[locate(i, j)] = true;
	}

	/**
	 * does site (row i, column j) connect to bottom?
	 */
	private boolean isConnectButtom(int i, int j) {
		return connectBottom[locate(i, j)];
	}

	/**
	 * set row i, column j connect to bottom.
	 * */
	private void setConnectButtom(int i, int j) {
		this.connectBottom[locate(i, j)] = true;
	}

	/**
	 * is site (row i, column j) full? full means it connect to an open site in the first row by a chain of open sites.
	 */
	public boolean isFull(int i, int j) {
		return isOpen(i, j) && uf.connected(locate(i, j), 0);
	}

	/**
	 * does the system percolate?
	 */
	public boolean percolates() {
		return ispercolate;
//		return uf.connected(0, N * N + 1);
	}

	/**
	 * transform the axis (i,j) to array index
	 */
	private int locate(int i, int j) {
		return (i - 1) * N + j;
	}

	/**
	 * validate the input, override validate(i)
	 */
	private void validate(int i, int j) {
		validate(i);
		validate(j);
	}

	/**
	 * validate the input n
	 */
	private void validate(int n) {
		if (n > N || n <= 0) {
			throw new IndexOutOfBoundsException("invalid input with " + n + " while N=" + N);
		}
	}

	// test client (optional)
	public static void main(String[] args) {

		// validate checking
		// Percolation p = new Percolation(0);
		//
		// Percolation p1 = new Percolation(10);
		// p1.open(20, 2);
		// p1.open(10, 0);

	}

}
