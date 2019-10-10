package matrix_Multiplication;
import java.util.Random;
import java.util.Timer;

public class MatrixMultiplication {
	
	public static void main(String[] args) {
		int m = 320;
		int n = 320;
		int p = 320;
		
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
		
		fillWithOnes(A,m,n);
		fillWithOnes(B,n,p);
		matMult_multipleThreads(A, B, C, m, n, p, 2);
		setZeroesTo2dArray(C);
		
		fillWithOnes(A,m,n);
		fillWithOnes(B,n,p);
		matMult_multipleThreads(A, B, C, m, n, p, 4);
		setZeroesTo2dArray(C);
		
		fillWithOnes(A,m,n);
		fillWithOnes(B,n,p);
		matMult_multipleThreads(A, B, C, m, n, p, 8);
		setZeroesTo2dArray(C);
		
		fillWithOnes(A,m,n);
		fillWithOnes(B,n,p);
		matMult_multipleThreads(A, B, C, m, n, p, 16);
		setZeroesTo2dArray(C);
		
		fillWithOnes(A,m,n);
		fillWithOnes(B,n,p);
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
	
	private static void matMult_multipleThreads(float[][] A, float[][] B, float[][] C, int m, int n, int p, int threadAmount) {
		long startTime = System.nanoTime();

		MatrixThread[] mThreads = new MatrixThread[threadAmount];
		
		/*
		 * creates another (thread) total extra threads.
		 * setBound gives it the parts of array C that it should work on
		 */
		for(int i = 0; i < threadAmount; i++) {
			mThreads[i] = new MatrixThread(A, B, C, m, n,p);
			// mThreads[i].setBound((n/threadAmount + (i * (n/threadAmount))), ((2 * (n/threadAmount)) + (i * (n/threadAmount))));	//problem lies here, they're all doing the same thing
			mThreads[i].setBound(i *(threadAmount/m), (i *(threadAmount/m)) + (threadAmount/m),
								 i *(threadAmount/p), (i *(threadAmount/p)) + (threadAmount/p));
			mThreads[i].start();
		}
		
		try {
			for(int i = 0;i < threadAmount- 1; i++) {
				mThreads[i].join();

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
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
/* Example Output:
Total time for 320 x 320 matrix: 114153711 nanoseconds using 1 thread.
Total time for 320 x 320 matrix: 127858752 nanoseconds using 2 threads.
Total time for 320 x 320 matrix: 151084898 nanoseconds using 4 threads.
Total time for 320 x 320 matrix: 70959619 nanoseconds using 8 threads.
Total time for 320 x 320 matrix: 63806903 nanoseconds using 16 threads.
Total time for 320 x 320 matrix: 120966376 nanoseconds using 32 threads.
 */
