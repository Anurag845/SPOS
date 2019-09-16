import java.util.Scanner;

public class Optimal {
	
	public static void main(String args[]) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter number of pages:- ");
		int n = sc.nextInt();
		int pages[] = new int[n];
		System.out.println("Enter the pages:- ");
		
		for(int cnt = 0; cnt < n; cnt++) {
			pages[cnt] = sc.nextInt();
		}
		
		System.out.print("Enter cache capacity:- ");
		int capacity = sc.nextInt();
		int cache[] = new int[capacity];
		int fault_cnt = 0;
		int cache_size = 0;
		int next_occ[] = new int[capacity];
		
		for(int i = 0; i < n; i++) {
			if(cache_size < capacity) {
				int j = 0;
				for(j = 0; j < cache_size; j++) {
					if(cache[j] == pages[i]) {
						break;
					}
				}
				if(j == cache_size) {
					cache[cache_size++] = pages[i];
					fault_cnt++;
				}
			}
			else {
				int j = 0;
				for(j = 0; j < cache_size; j++) {
					if(cache[j] == pages[i]) {
						break;
					}
				}
				if(j == cache_size) {
					for(int k = 0; k < capacity; k++) {
						next_occ[k] = Integer.MAX_VALUE;
					}
					for(int k = 0; k < capacity; k++) {
						for(int c = i+1; c < n; c++) {
							if(cache[k] == pages[c]) {
								next_occ[k] = c;
							}
						}
					}
					int max_index = 0, max = next_occ[0];
					for(int k = 1; k < capacity; k++) {
						if(next_occ[k] > max) {
							max = next_occ[k];
							max_index = k;
						}
					}
					cache[max_index] = pages[i];
					fault_cnt++;
				}
			}
			System.out.println(cache[0] + " " + cache[1] + " " + cache[2]);
		}
		
		System.out.println("Number of page faults is " + fault_cnt);
		
	}

}
