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
	
	int lowRow;
	int highRow;
	int lowCol;
	int highCol;
	
	public MatrixThread(float[][] A, float[][] B, float[][] C, int m, int n, int p) {
		this.A = A;
		this.B = B;
		this.C = C;
		this.m = m;
		this.n = n;
		this.p = p;
	}
	
	public void run() {

		//C[i][j] += A[i][k] * B[k][j];

//		for(int i = this.i; i < m; i++) {
//			for(int j = this.j; j < p; j++) {
//				for(int k = this.k; k < n; k++) {
//					C[k][n] += A[i][j] * B[j][k];
//				}
//			}
//		}
		
		for(int i = this.m; i < n; i++) {
			for(int j = this.p; j < n; j++) {
				for(int x = lowRow; x < highRow; m++)
					for(int y = lowCol; y < highCol;p++)
						C[x][y] = A[i][j] * B[j][i];	
			}
		}

	}
	
	public void setBound(int lowRow, int highRow, int lowCol, int highCol) {

		this.lowRow = lowRow;
		this.highRow = highRow;
		this.lowCol = lowCol;
		this.highCol = highCol;
	}

}
