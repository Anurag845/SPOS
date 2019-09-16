import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class RoundRobin {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter number of processes:- ");
		int n = sc.nextInt();
		
		ArrayList<Process> process = new ArrayList<Process>(n);
		boolean Finished[] = new boolean[n];
		
		String Sequence[] = new String[n];
		
		for(int cnt = 0; cnt < n; cnt++) {
			System.out.print("Enter name of process " + cnt + " :- ");
			String name = sc.next();
			System.out.print("Enter arrival time of process " + name + " :- ");
			int arr_time = sc.nextInt();
			System.out.print("Enter burst time of process " + name + " :- ");
			int burst_time = sc.nextInt();
			process.add(new Process(name, arr_time, 0, burst_time, burst_time));
			Finished[cnt] = false;
		}
		
		Collections.sort(process, new Comparator<Process>() {
			@Override
			public int compare(Process o1, Process o2) {
				return Integer.compare(o1.getArr_time(), o2.getArr_time());
			}
		});
		
		int quantum = 2;
		
		int time = 0;
		int cnt = 0;
		
		while(cnt < n) {
			for(int i = 0; i < n; i++) {
				int arr_time = process.get(i).getArr_time();
				int remb_time = process.get(i).getRem_btime();
				if(remb_time > 0 && arr_time <= time && Finished[i] == false) {
					if(remb_time > quantum) {
						time += quantum;
						process.get(i).setRem_btime(remb_time-quantum);
					}
					else {
						time += remb_time;
						process.get(i).setRem_btime(0);
						Finished[i] = true;
						process.get(i).setC_time(time);
						process.get(i).setTa_time(time - arr_time);
						process.get(i).setWait_time(process.get(i).getTa_time() - process.get(i).getBurst_time());
						Sequence[cnt++] = process.get(i).getName(); 
					}
				}
			}
			
		}
		
		float avg_time = 0;
		for(int c = 0; c < n; c++) {
			avg_time += process.get(c).getWait_time();
			System.out.println(Sequence[c]);
		}
		
		avg_time /= n;
		System.out.println("Average waiting time is " + avg_time);
		
		sc.close();
	}

}
