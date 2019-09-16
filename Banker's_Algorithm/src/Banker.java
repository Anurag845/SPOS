import java.util.Scanner;

public class Banker {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int m = 3, n =5;
		String Process[] = {"P0","P1","P2","P3","P4"};
		
		int Available[] = {3,3,2};
		
		boolean Finished[] = new boolean[n];
		for(int cnt = 0 ; cnt < n; cnt++) {
			Finished[cnt] = false;
		}
		
		int Max[][] = {{7,5,3},{3,2,2},{9,0,2},{2,2,2},{4,3,3}};
		int Allocation[][] = {{0,1,0},{2,0,0},{3,0,2},{2,1,1},{0,0,2}};
		
		int Need[][] = new int[n][m];
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				Need[i][j] = Max[i][j] - Allocation[i][j];
			}
		}
		
		int Work[] = Available;
		String Sequence[] = new String[n];
		
		int cnt = 0;
		while(cnt < n) {
			for(int i = 0; i < n; i++) {
				if(Finished[i] == false) {
					int j = 0;
					for(j = 0; j < m; j++) {
						if(Need[i][j] > Work[j]) {
							break;
						}
					}
					if(j == m) {
						Finished[i] = true;
						Sequence[cnt++] = Process[i];
						for(int k = 0; k < m; k++) {
							Work[k] += Allocation[i][k];
						}
					}
				}
			}
		}
		
		for(int k = 0; k < n; k++) {
			System.out.println(Sequence[k]);
		}
		
	}
	
}
