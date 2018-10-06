package matrix_Multiplication;
import java.util.concurrent.Phaser;
import java.util.Random;
import java.util.Timer;

public class MatrixMultiplication {
	
	public static void main(String[] args) {
		int m = 400;
		int n = 400;
		int p = 400;
		
		float[][] A = new float[m][n];
		float[][] B = new float[n][p];
		float[][] C = new float[m][p];
		
		createRandomNumbers(A, m, n);
		createRandomNumbers(B, n, p);
		setZeroesTo2dArray(C);
		
		
		//fillWithOnes(A,m,n);
		//fillWithOnes(B,n,p);
		//setZeroesTo2dArray(C);
		
		matMult(A, B, C, m, n, p);
		setZeroesTo2dArray(C);
		
		matMult_2Threads(A, B, C, m, n, p);
		setZeroesTo2dArray(C);
		
		matMult_multipleThreads(A, B, C, m, n, p, 4);
		setZeroesTo2dArray(C);
		
		matMult_multipleThreads(A, B, C, m, n, p, 8);
		setZeroesTo2dArray(C);
		
		matMult_multipleThreads(A, B, C, m, n, p, 16);
		setZeroesTo2dArray(C);
		
		matMult_multipleThreads(A, B, C, m, n, p, 32);
		setZeroesTo2dArray(C);
		
	}
	
	/*
	 * @param A; Matrix of m x n
	 * @param B; Matrix of n x p
	 * @param C; Matrix of m x p; product of AB
	 */
	private static void matMult(float[][] A, float[][] B, float[][] C, int m, int n, int p) {
		long startTime = System.nanoTime();
		for(int i = 0; i < m; i++) {
			for(int j = 0; j < p; j++) {
				for(int k = 0; k < n; k++) {
					C[i][j] += (A[i][k] * B[k][j]);
				}
			}
		}
		long endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		
		System.out.println("Total time for " + m + " x " + p + " matrix: " + totalTime + " nanoseconds using 1 thread.");
		
	}
	private static void matMult_2Threads(float[][] A, float[][] B, float[][] C, int m, int n, int p) {
		long startTime = System.nanoTime();

		MatrixThread mThread1 = null;
		mThread1 = new MatrixThread(A, B, C, m, n, p);
		mThread1.setIJK(0, 0, n/2);
		mThread1.start();
		
		for(int i = 0; i < m; i++) {
			for(int j = 0; j < p; j++) {
				for(int k = 0; k < n/2; k++) {
					
					C[i][j] += A[i][k] * B[k][j];
					
				}
			}
		}
		while(mThread1.isAlive()) {
			try {
				mThread1.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		
		System.out.println("Total time for " + m + " x " + p + " matrix: " + totalTime + " nanoseconds  using 2 threads.");
	}
	
	private static void matMult_multipleThreads(float[][] A, float[][] B, float[][] C, int m, int n, int p, int threadAmount) {
		long startTime = System.nanoTime();

		MatrixThread[] mThreads = new MatrixThread[threadAmount];
		
		for(int i = 0; i < threadAmount; i++) {
			mThreads[i] = new MatrixThread(A, B, C, m, n, p);
			mThreads[i].setIJK(0, 0, n/threadAmount);
			mThreads[i].start();
		}
		
		for(int i = 0; i < m; i++) {
			for(int j = 0; j < p; j++) {
				for(int k = 0; k < n/threadAmount; k++) {
					C[i][j] += A[i][k] * B[k][j];
				}
			}
		}
		boolean allThreadsDead = false;
		while(!allThreadsDead) {
			try {
				for(int i = 0;i < threadAmount; i++) {
					mThreads[i].join();
					allThreadsDead = true;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		
		System.out.println("Total time for " + m + " x " + p + " matrix: " + totalTime + " nanoseconds using " + threadAmount + " threads.");
	}
	
	private static void setZeroesTo2dArray(float[][] arr) {
		for(int i = 0;i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++)
				arr[i][j] = 0;
		}
	}
	
	private static void createRandomNumbers(float[][] arr, int columns, int rows) {
		Random rand = new Random();
		
		for(int i = 0; i < columns; i++) {
			for(int j = 0; j < rows; j++) {
				arr[i][j] = rand.nextFloat();
			}
		}
	}
	
	private static void fillWithOnes(float[][] arr, int columns, int rows) {
		for(int i = 0; i < columns; i++) {
			for(int j = 0; j < rows; j++) {
				arr[i][j] = (float) 1.0;
			}
		}
	}
}
