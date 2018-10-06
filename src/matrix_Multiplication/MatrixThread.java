package matrix_Multiplication;

public class MatrixThread extends Thread{
	float[][] A;
	float[][] B;
	float[][] C;
	int m;
	int n;
	int p;
	
	int i = 0;
	int j = 0;
	int k;
	
	public MatrixThread(float[][] A, float[][] B, float[][] C, int m, int p) {
		this.A = A;
		this.B = B;
		this.C = C;
		this.m = m;
		this.p = p;
	}
	
	public void run() {

		//C[i][j] += A[i][k] * B[k][j];

		for(int i = this.i; i < m; i++) {
			for(int j = this.j; j < p; j++) {
				for(int k = this.k; k < n; k++) {
					C[i][j] += A[i][k] * B[k][j];		
				}
			}
		}
		

	}
	
	public void setBound(int k, int n) {

		this.k = k;
		this.n = n;
	}

}
