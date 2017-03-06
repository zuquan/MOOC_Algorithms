package unionFind;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private int T; // number of trail
	private double x[];

	/**
	 * perform T independent experiments on an N-by-N grid
	 */
	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0) {
			throw new IllegalArgumentException("Construction of PercolationStats,  n <= 0 or trials <= 0");
		}
		this.T = T;
		x = new double[T];

		for (int t = 0; t < T; t++) {
			Percolation perc = new Percolation(N);
			int count = 0;
			while (!perc.percolates()) {
				int i = StdRandom.uniform(1, N + 1);
				int j = StdRandom.uniform(1, N + 1);
				while (perc.isOpen(i, j)) {
					i = StdRandom.uniform(1, N + 1);
					j = StdRandom.uniform(1, N + 1);
				}
				perc.open(i, j);
				count++;
			}

			x[t] = (double) count / (N * N);
//			StdOut.println(count + ", " + x[t]);
		}
	}

	/**
	 * // sample mean of percolation threshold
	 */
	public double mean() {
		double sum = 0;
		for (int i = 0; i < T; i++) {
			sum += x[i];
		}

//		StdOut.println("mean: " + sum / T + " : " + StdStats.mean(x));
		return sum / T;
	}

	/**
	 * // sample standard deviation of percolation threshold
	 */
	public double stddev() {
		double mean = mean();
		double sum = 0.0;
		for (int i = 0; i < T; i++) {
			sum += (x[i] - mean) * (x[i] - mean);
		}

//		StdOut.println("std = " + Math.sqrt(sum / (T - 1)) + " : " + StdStats.stddev(x));
		return Math.sqrt(sum / (T - 1));
	}

	/**
	 * // low endpoint of 95% confidence interval
	 */
	public double confidenceLo() {
		return mean() - 1.96 * stddev() / Math.sqrt(T);
	}

	/**
	 * // high endpoint of 95% confidence interval
	 */
	public double confidenceHi() {
		return mean() + 1.96 * stddev() / Math.sqrt(T);
	}

	// test client (described below)
	public static void main(String[] args) {

		PercolationStats ps = new PercolationStats(2, 10000);
		StdOut.println("mean = " + ps.mean());
		StdOut.println("stddev = " + ps.stddev());
		StdOut.println("interval = " + ps.confidenceLo() + "," + ps.confidenceHi());
	}
}
