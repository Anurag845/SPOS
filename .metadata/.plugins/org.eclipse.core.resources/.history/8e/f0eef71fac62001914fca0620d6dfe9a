import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Pass1 {
	
	private String sourcecode;
	private String[] srccode_Lines;
	
	private int mdtline_cnt = 0;
	private int no_of_Lines;
	
	private String[] intm_code;
	private int intmLines_cnt = 0;
	private for_vs_pos[] for_pos;
	int no_of_args = 0;
	
	public void read() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("source.txt"));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while(line != null) {
			sb.append(line);
			sb.append("\n");
			line = br.readLine();
		}
		sourcecode = sb.toString();
		srccode_Lines = sourcecode.split("\n");
		no_of_Lines = srccode_Lines.length;
		Macro.mdt = new MDT[no_of_Lines];
		Macro.mnt = new MNT[no_of_Lines];
		intm_code = new String[no_of_Lines];
		for_pos = new for_vs_pos[3];
	}
	
	
	public void pass1() {
		String flag = "no_macro";
		for(int i = 0; i < no_of_Lines; i++) {
			String each_Line[] = srccode_Lines[i].split(" ");
			if(each_Line[0].equals("MACRO")) {
				if(each_Line.length == 3) {
					//System.out.println("Parameters present");
					String args[] = each_Line[2].split(",");
					no_of_args = args.length;
					for_pos = new for_vs_pos[no_of_args];
					for_vs_pos fptuple;
					if(no_of_args == 1) {
						fptuple = new for_vs_pos(args[0],"#1");
						for_pos[0] = fptuple;
					}
					else if(no_of_args == 2) {
						fptuple = new for_vs_pos(args[0],"#1");
						for_pos[0] = fptuple;
						fptuple = new for_vs_pos(args[1],"#2");
						for_pos[1] = fptuple;
					}
					else if(no_of_args == 3) {
						fptuple = new for_vs_pos(args[0],"#1");
						for_pos[0] = fptuple;
						fptuple = new for_vs_pos(args[1],"#2");
						for_pos[1] = fptuple;
						fptuple = new for_vs_pos(args[2],"#3");
						for_pos[2] = fptuple;
					}
					flag = "macro";
					Macro.macro_cnt++;
					mdtline_cnt++;
					MNT mnt_tuple = new MNT(each_Line[1],no_of_args,mdtline_cnt);
					Macro.mnt[Macro.macro_cnt-1] = mnt_tuple;
					continue;
				}
				else {
					no_of_args = 0;
					flag = "macro";
					Macro.macro_cnt++;
					mdtline_cnt++;
					MNT mnt_tuple = new MNT(each_Line[1],no_of_args,mdtline_cnt);
					Macro.mnt[Macro.macro_cnt-1] = mnt_tuple;
					continue;					
				}
				
			}
			if(flag.equals("macro")) {
				for(int j = 0; j < no_of_args; j++) {
					srccode_Lines[i] = srccode_Lines[i].replaceAll(for_pos[j].formal,for_pos[j].positional);
					//System.out.println("Postional vs Formal" + for_pos[j].formal + "," + for_pos[j].formal);
				}
				MDT mdt_tuple = new MDT(mdtline_cnt,srccode_Lines[i]);
				Macro.mdt[mdtline_cnt-1] = mdt_tuple;
				if(each_Line[0].equals("MEND")) {
					flag = "no_macro";
				}
				else {
					mdtline_cnt++;
				}
			}
			else if(flag.equals("no_macro")) {
				intm_code[intmLines_cnt] = srccode_Lines[i];
				intmLines_cnt++;
			}
		}
	}
	
	public void display() throws IOException {
		System.out.println("Read code is as follows-");
		//System.out.println(sourcecode);
		System.out.println("After splitting at newlines-");
		for(int i = 0; i < srccode_Lines.length; i++) {
			//System.out.println(srccode_Lines[i]);
		}
		System.out.println("MNT is as follows- ");
		for(int i = 0; i < Macro.macro_cnt; i++) {
			System.out.println(Macro.mnt[i].macro_name + " " + Macro.mnt[i].mdt_line + " " + Macro.mnt[i].no_of_params);
		}
		System.out.println("MDT is as follows- ");
		for(int i = 0; i < mdtline_cnt; i++) {
			System.out.println(Macro.mdt[i].line_no + " " + Macro.mdt[i].instruction);
		}
		System.out.println("Intermediate code is- ");
		for(int i = 0; i < intmLines_cnt; i++) {
			System.out.println(intm_code[i]);
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("intermediate.txt"));
		for(int i = 0; i < intmLines_cnt; i++) {
			writer.write(intm_code[i]);
			writer.newLine();
		}
		writer.flush();
		writer.close();
	}

}