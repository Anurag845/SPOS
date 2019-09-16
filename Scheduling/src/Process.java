
public class Process {
	
	private String name;
	private int arr_time;
	private int wait_time;
	private int burst_time;
	private int rem_btime;
	private int c_time;
	private int ta_time;
	
	public Process(String name, int arr_time, int wait_time, int burst_time, int rem_btime) {
		super();
		this.name = name;
		this.arr_time = arr_time;
		this.wait_time = wait_time;
		this.burst_time = burst_time;
		this.rem_btime = rem_btime;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getArr_time() {
		return arr_time;
	}
	public void setArr_time(int arr_time) {
		this.arr_time = arr_time;
	}
	public int getWait_time() {
		return wait_time;
	}
	public void setWait_time(int wait_time) {
		this.wait_time = wait_time;
	}
	public int getBurst_time() {
		return burst_time;
	}
	public void setBurst_time(int burst_time) {
		this.burst_time = burst_time;
	}
	public int getRem_btime() {
		return rem_btime;
	}
	public void setRem_btime(int rem_btime) {
		this.rem_btime = rem_btime;
	}
	public int getC_time() {
		return c_time;
	}
	public void setC_time(int c_time) {
		this.c_time = c_time;
	}
	public int getTa_time() {
		return ta_time;
	}
	public void setTa_time(int ta_time) {
		this.ta_time = ta_time;
	}
}


/*import java.util.Comparator;

public class Process {
String name;
int BT,WT,AT,CT,TAT,remBT,priority;
boolean flag;
public Process(String name,int burst,int AT)
{
	this.name=name;
	BT=burst;
	this.AT=AT;
	WT=CT=TAT=0;
	remBT=BT;
	priority=0;
}
public Process(String name,int burst,int AT,int PR)
{
	this.name=name;
	BT=burst;
	this.AT=AT;
	WT=CT=TAT=0;
	remBT=BT;
	priority=PR;
	flag=false;
}
public void display()
{
	System.out.println(name+"\t"+BT+"\t"+AT+"\t"+CT+"\t"+TAT+"\t"+WT+"\t"+priority);
}
}

//Class for sorting Processes
class SortByArrival implements Comparator<Process>
{

	@Override
	public int compare(Process p1, Process p2) {
		
		return p1.AT-p2.AT;
	}
	
}

class SortByPriority implements Comparator<Process>
{

	@Override
	public int compare(Process o1, Process o2) {
		
		return o1.priority-o2.priority;
	}
	
}*/
