import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Priority {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter the number of jobs:- ");
		int n = sc.nextInt();
		
		ArrayList<Job> jobs = new ArrayList<Job>(n);
		boolean Finished[] = new boolean[n];
		String Sequence[] = new String[n];
		
		for(int cnt = 0; cnt < n; cnt++) {
			System.out.print("Job name:- ");
			String name = sc.next();
			System.out.print("Job arrival time:- ");
			int arr_time = sc.nextInt();
			System.out.print("Job burst time:- ");
			int burst_time = sc.nextInt();
			System.out.print("Job priority:- ");
			int priority = sc.nextInt();
			System.out.println();
			jobs.add(new Job(name, arr_time,burst_time, 0 , priority));
			Finished[cnt] = false;
		}
		
		Collections.sort(jobs, new Comparator<Job>() {
			@Override
			public int compare(Job o1, Job o2) {
				return Integer.compare(o1.getArr_time(), o2.getArr_time());
			}
		});
		
		int p_min = Integer.MAX_VALUE;
		int time = 0;
		int pointer = 0, cnt = 0;
		boolean arrived = false;
		
		while(cnt < n) {
			if(arrived == false) {
				int i = 0;
				for(i = 0; i < n; i++) {
					if(jobs.get(i).getArr_time() > time) {
						pointer = i;
						break;
					}
				}
				if(i == n) {
					arrived = true;
					pointer = n;
				}
			}
			int k = 0, high = 0;
			for(k = 0; k < pointer; k++) {
				if(Finished[k] == false && jobs.get(k).getPriority() < p_min) {
					p_min = jobs.get(k).getPriority();
					high = k;
				}
			}
			p_min = Integer.MAX_VALUE;
			Finished[high] = true;
			time += jobs.get(high).getBurst_time();
			Sequence[cnt++] = jobs.get(high).getName();
		}
		
		for(int i = 0; i < n; i++) {
			System.out.println(Sequence[i]);
		}
		
		sc.close();
		
	}

}
