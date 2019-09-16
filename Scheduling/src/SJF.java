import java.util.Scanner;

public class SJF {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter number of processes:- ");
		int n = sc.nextInt();
		
		Process process[] = new Process[n];
		
		for(int i = 0; i < n; i++) {
			System.out.print("Name of process " + i + " :- ");
			String name = sc.next();
			System.out.print("Arrival time of process " + name + " :- ");
			int arr_time  = sc.nextInt();
			System.out.print("Burst time of process " + name + " :- ");
			int burst_time = sc.nextInt();
			process[i] = new Process(name,arr_time,0,burst_time,burst_time);
		}
		
		String Sequence[] = new String[n];
		
		int pointer = 0;
		int time = 0;
		int cnt = 0;
		int min = Integer.MAX_VALUE;
		boolean flag = false;
		
		while(cnt < n) {
			for(int i = 0; i < n; i++) {
				if(process[i].getArr_time() <= time && process[i].getRem_btime() < min && process[i].getRem_btime() > 0) {
					min = process[i].getRem_btime();
					flag = true;
					pointer = i;
				}
			}
			if(flag == false) {
				time++;
				continue;
			}
			min--;
			process[pointer].setRem_btime(min);
			time++;
			if(min == 0) {
				min = Integer.MAX_VALUE;
				Sequence[cnt++] = process[pointer].getName();
				process[pointer].setC_time(time);
				process[pointer].setTa_time(time - process[pointer].getArr_time());
				process[pointer].setWait_time(process[pointer].getTa_time() - process[pointer].getBurst_time());
			}
		}
		
		for(int k = 0; k < n; k++) {
			System.out.println(Sequence[k]);
		}
		
	}
	
}