import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Pass2 {
	
	private String intermediatecode;
	private String[] intcode_Lines;
	private int no_of_Lines = 0;
	private act_vs_pos[] act_pos;
	String outputcode = "";
	
	public void read() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("intermediate.txt"));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while(line != null) {
			sb.append(line);
			sb.append("\n");
			line = br.readLine();
		}
		intermediatecode = sb.toString();
		intcode_Lines = intermediatecode.split("\n");
		no_of_Lines = intcode_Lines.length;
	}
	
	public void pass2() {
		for(int i = 0; i < no_of_Lines; i++) {
			String parameters = "no";
			String eachLine[] = intcode_Lines[i].split(" ");
			String firstword = eachLine[0];
			//System.out.println("First word of eachline is " + firstword);
			int mntcnt = 0;
			int no_of_params = 0;
			int mdtline = 0;
			for(mntcnt = 0; mntcnt < Macro.macro_cnt; mntcnt++) {
				if(firstword.equals(Macro.mnt[mntcnt].macro_name)) {
					no_of_params = Macro.mnt[mntcnt].no_of_params;
					mdtline = Macro.mnt[mntcnt].mdt_line;
					//System.out.println("MDTLINE IS " + mdtline);
					act_pos = new act_vs_pos[no_of_params];
					if(no_of_params == 0) {
						parameters = "no";
						break;
					}
					if(eachLine.length == 2) {
						parameters = "yes";
						String[] params = eachLine[1].split(",");
						if(no_of_params == params.length) {
							int k = 0;
							act_vs_pos actpos_tuple;
							if(no_of_params == 1) {
								actpos_tuple = new act_vs_pos(params[0],"#1");
								act_pos[k] = actpos_tuple;
								k += 1;
								break;
							}
							else if(no_of_params == 2) {
								actpos_tuple = new act_vs_pos(params[0],"#1");
								act_pos[k] = actpos_tuple;
								k += 1;
								actpos_tuple = new act_vs_pos(params[1],"#2");
								act_pos[k] = actpos_tuple;
								k += 1;
								break;
							}
							else if(no_of_params == 3) {
								actpos_tuple = new act_vs_pos(params[0],"#1");
								act_pos[k] = actpos_tuple;
								k += 1;
								actpos_tuple = new act_vs_pos(params[1],"#2");
								act_pos[k] = actpos_tuple;
								k += 1;
								actpos_tuple = new act_vs_pos(params[2],"#3");
								act_pos[k] = actpos_tuple;
								k += 1;
								break;
							}
						}
					}
				}
			}
			if(mntcnt == Macro.macro_cnt) {
				//Not a macro def.
				outputcode += intcode_Lines[i] + "\n";
			}
			else if(parameters.equals("yes")){
				for(int j = mdtline-1; !Macro.mdt[j].instruction.equals("MEND"); j++) {
					String mdef = Macro.mdt[j].instruction;
					for(int ap = 0; ap < no_of_params; ap++) {
						mdef = mdef.replaceAll(act_pos[ap].positional,act_pos[ap].actual);
					}
					outputcode += mdef + "\n";
				}
				outputcode += "MEND" + "\n";
			}
			else if(parameters.equals("no")) {
				for(int j = mdtline-1; !Macro.mdt[j].instruction.equals("MEND"); j++) {
					String mdef = Macro.mdt[j].instruction;
					outputcode += mdef + "\n";
				}
				outputcode += "MEND" + "\n";
			}
		}
	}
	
	
	public void display() {
		
		for(int i = 0; i < no_of_Lines; i++) {
			//System.out.println(intcode_Lines[i]);
		}
		System.out.println("Output File is\n" + outputcode);
	}
	
}