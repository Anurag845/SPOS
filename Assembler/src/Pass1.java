import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Pass1 {
	
	private String sourcecode;
	private OPTAB[] instcodes;
	private SYMTAB[] symtable;
	private LITTAB[] littable;
	private Tuple[] tuples;
	private int loc_ptr;
	private int littab_ptr = 0;
	private int pooltab_ptr;
	private Assembler_Directives[] Asm_Dir;
	private int no_of_Lines;
	private int symtab_entries;
	private IC_tuple[] int_code;
	private int icnt = 0;
	private Target_Code[] target;
	private int target_Lines = 0;
	
	Pass1() {
		
		String inst,code;
		
		instcodes = new OPTAB[11];
		
		inst = "STOP";
		code = "00";
		instcodes[0] = new OPTAB(inst,code);
		
		inst = "ADD";
		code = "01";
		instcodes[1] = new OPTAB(inst,code);		
		
		inst = "SUB";
		code = "02";
		instcodes[2] = new OPTAB(inst,code);
		
		inst = "MUL";
		code = "03";
		instcodes[3] = new OPTAB(inst,code);
		
		inst = "MOVER";
		code = "04";
		instcodes[4] = new OPTAB(inst,code);
		
		inst = "MOVEM";
		code = "05";
		instcodes[5] = new OPTAB(inst,code);
		
		inst = "COMP";
		code = "06";
		instcodes[6] = new OPTAB(inst,code);
		
		inst = "BC";
		code = "07";
		instcodes[7] = new OPTAB(inst,code);
		
		inst = "DIV";
		code = "08";
		instcodes[8] = new OPTAB(inst,code);
		
		inst = "READ";
		code = "09";
		instcodes[9] = new OPTAB(inst,code);
		
		inst = "PRINT";
		code = "10";
		instcodes[10] = new OPTAB(inst,code);
		
		Asm_Dir = new Assembler_Directives[5];
		
		Asm_Dir[0] = new Assembler_Directives("START","01");
		Asm_Dir[1] = new Assembler_Directives("END","02");
		Asm_Dir[2] = new Assembler_Directives("EQU","03");
		Asm_Dir[3] = new Assembler_Directives("ORIGIN","04");
		Asm_Dir[4] = new Assembler_Directives("LTORG","05");
 	}
	
	public void read() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("sourcecode.txt"));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while(line!=null) {
			sb.append(line);
            sb.append("\n");
            line = br.readLine();
		}
		sourcecode = sb.toString();
	}
	
	public void process_Input() {
		loc_ptr = 0;
		pooltab_ptr = 1;
		String[] eachLine = sourcecode.split("\n");
		no_of_Lines = eachLine.length;
		System.out.println("Number of lines in source code " + no_of_Lines);
		tuples = new Tuple[no_of_Lines];
		symtable = new SYMTAB[no_of_Lines];
		target = new Target_Code[no_of_Lines];
		int_code = new IC_tuple[no_of_Lines];
		littable = new LITTAB[no_of_Lines];
		int srcline = 0;
		Tuple t = null;
		while(srcline < no_of_Lines) {
			String[] splittedLine = eachLine[srcline].split("\t");
			//System.out.println(splittedLine[1] + " " + splittedLine.length);
			if(splittedLine.length == 2) {
				if(splittedLine[1].equals("STOP")) {
					t = new Tuple("",splittedLine[1],"",0);
				}
				else {
					t = new Tuple("",splittedLine[0],splittedLine[1],0);
				}
			}
			else if(splittedLine.length == 3) {
				t = new Tuple(splittedLine[0],splittedLine[1],splittedLine[2],0);
			}
			tuples[srcline] = t;
			srcline++;
		}
		/*for(int i = 0; i < no_of_Lines; i++) {
			System.out.println(tuples[i].label + "\t" + tuples[i].inst + "\t" + tuples[i].opcode + "\t" + tuples[i].address);
		}*/
	}
	
	public void pass1() {
		IC_tuple icode;
		if(tuples[0].inst.equals("START")) {
			loc_ptr = Integer.parseInt(tuples[0].opcode);
			icode = new IC_tuple("AD,01","C,"+loc_ptr);
			int_code[icnt] = icode;
			icnt++;
		}
		String symbol = "";
		String mnemonic = "";
		
		int j = 0;
		for(int i = 1; i< tuples.length; i++) {
			
			if(!tuples[i].label.equals("")) {
				symbol = tuples[i].label;
				mnemonic = tuples[i].inst;
				if(mnemonic.equals("DC") || mnemonic.equals("DS")) {
					tuples[i].address = loc_ptr;
					symtable[j] = new SYMTAB(symbol,loc_ptr);
					j++;
					String op = tuples[i].opcode;
					if(op.startsWith("'") && op.endsWith("'")) {
						loc_ptr += 1;
						icode = new IC_tuple("DL,01","C,"+op);
					}
					else {
						loc_ptr += Integer.parseInt(tuples[i].opcode);
						icode = new IC_tuple("DL,02","C,"+op);
					}
					int_code[icnt] = icode;
					icnt++;
				}
				else if(belongstoOPT(mnemonic)) {
					tuples[i].address = loc_ptr;
					symtable[j] = new SYMTAB(symbol,loc_ptr);
					String code = null;
					for(int cnt = 0; cnt < 11; cnt++) {
						if(mnemonic.equals(instcodes[cnt].inst)) {
							code = instcodes[cnt].code;
							break;
						}
					}
					String operands[] = tuples[i].opcode.split(",");
					String reg = "";
					if(operands.length == 2) {
						reg = operands[0];
						if(reg.startsWith("A")) {
							reg = "(1)";
						}
						else if(reg.startsWith("B")) {
							reg = "(2)";
						}
						else if(reg.startsWith("C")) {
							reg = "(3)";
						}
						if(operands[1].startsWith("=") && operands[1].endsWith("'")) {
							LITTAB literal = new LITTAB("operands[1]","");
							littable[littab_ptr] = literal;
							littab_ptr++;
						}
					}
					
					icode = new IC_tuple("IS,"+code,tuples[i].opcode);
					int_code[icnt] = icode;
					icnt++;
					j++;
					loc_ptr += 1;
				}
				else if(belongstoAD(mnemonic)) {
					tuples[i].address = loc_ptr;
					String code = null;
					for(int cnt = 0; cnt < 5; cnt++) {
						if(mnemonic.equals(Asm_Dir[cnt].directive)) {
							code = Asm_Dir[cnt].code;
							break;
						}
					}
					String new_loc = tuples[i].opcode;
					String address[], lbl, inc, dec;
					if(new_loc.contains("+")) {
						address = new_loc.split("\\+");
						inc = address[1];
						lbl = address[0];
						//loc_ptr += Integer.parseInt(inc);
						for(int k = 0; k < j; k++) {
							if(symtable[k].symbol.equals(lbl)) {
								if(mnemonic.equals("EQU")) {
									int equ_add = symtable[k].address + Integer.parseInt(inc);
									symtable[j] = new SYMTAB(symbol,equ_add);
									j++;
								}
								else if(mnemonic.equals("ORIGIN")) {
									symtable[j] = new SYMTAB(symbol,loc_ptr);
									j++;
									loc_ptr = symtable[k].address + Integer.parseInt(inc);
								}
								break;
							}
						}
					}
					else if(new_loc.contains("-")) {
						address = new_loc.split("-");
						lbl = address[0];
						dec = address[1];
						for(int k = 0; k < j; k++) {
							if(symtable[k].symbol.equals(lbl)) {
								loc_ptr = symtable[k].address - Integer.parseInt(dec);
								break;
							}
						}
					}
					icode = new IC_tuple("AD,"+code,Integer.toString(loc_ptr));
					int_code[icnt] = icode;
					icnt++;
				}
			}
			else {
				mnemonic = tuples[i].inst;
				//System.out.println(mnemonic);
				if(belongstoOPT(mnemonic) || mnemonic.equals("STOP")) {
					tuples[i].address = loc_ptr;
					String code = null;
					for(int cnt = 0; cnt < 11; cnt++) {
						if(mnemonic.equals(instcodes[cnt].inst)) {
							code = instcodes[cnt].code;
							break;
						}
					}
					icode = new IC_tuple("IS,"+code,tuples[i].opcode);
					int_code[icnt] = icode;
					icnt++;
					loc_ptr += 1;
				}
				else if(mnemonic.equals("DS") || mnemonic.equals("DC")) {
					tuples[i].address = loc_ptr;
					loc_ptr += Integer.parseInt(tuples[i].opcode);
					String op = tuples[i].opcode;
					if(op.startsWith("'") && op.endsWith("'")) {
						loc_ptr += 1;
						icode = new IC_tuple("DL,01","C,"+op);
					}
					else {
						loc_ptr += Integer.parseInt(tuples[i].opcode);
						icode = new IC_tuple("DL,02","C,"+op);
					}
					int_code[icnt] = icode;
					icnt++;
				}
				else if(belongstoAD(mnemonic)) {
					if(mnemonic.equals("ORIGIN")) {
						//System.out.println("In origin");
						String code = null;
						for(int cnt = 0; cnt < 5; cnt++) {
							if(mnemonic.equals(Asm_Dir[cnt].directive)) {
								code = Asm_Dir[cnt].code;
								break;
							}
						}
						String location = tuples[i].opcode;
						String new_loc[];
						String lbl;
						if(location.contains("+")) {
							new_loc = location.split("\\+");
							lbl = new_loc[0];
							int inc = Integer.parseInt(new_loc[1]);
							for(int k = 0; k < j; k++) {
								if(lbl.equals(symtable[k].symbol)) {
									//System.out.println("Figuring loc_ptr");
									loc_ptr = symtable[k].address + inc;
									break;
								}
							}
						}
						else if(location.contains("-")) {
							new_loc = location.split("-");
							lbl = new_loc[0];
							int dec = Integer.parseInt(new_loc[1]);
							for(int k = 0; k < j; k++) {
								if(lbl.equals(symtable[k].symbol)) {
									loc_ptr = symtable[k].address - dec;
									break;
								}
							}
						}
						icode = new IC_tuple("AD,"+code,Integer.toString(loc_ptr));
						int_code[icnt] = icode;
						icnt++;
					}
				}
			}
		}
		symtab_entries = j;
	}
	
	public void display() {
		for(int i = 0; i < symtab_entries; i++) {
			//System.out.println(symtable[i].symbol + "\t" + symtable[i].address);
		}
		/*for(int i = 0; i< no_of_Lines; i++) {
			System.out.println(tuples[i].label + "\t" + tuples[i].inst + "\t" + tuples[i].opcode + "\t" + tuples[i].address);
		}*/
		for(int i = 0; i < icnt; i++) {
			System.out.println(int_code[i].inst_type + "\t" + int_code[i].opcode); 
		}
		
		System.out.println();
		System.out.println();
		
		for(int i = 0; i < littab_ptr; i++) {
			System.out.println(littable[i]);
		}
	}
	
	public boolean belongstoOPT(String mnemonic) {
		for(int i = 0; i < 11; i++) {
			if(mnemonic.equals(instcodes[i].inst)) {
				return true;
			}
		}
		return false;
	}
	
	public void pass2() {
		String[] code;
		String[] inst;
		String t_code, t_reg = null, t_add = null;
		Target_Code tuple;
		for(int i = 0; i < icnt; i++) {
			inst = int_code[i].inst_type.split(",");
			code = int_code[i].opcode.split(",");
			if(inst[0].equals("DL")) {
				t_code = "+00";
				t_reg = "0";
				t_add = code[1];
				tuple = new Target_Code(t_code,t_reg,t_add);
				target[target_Lines] = tuple;
				target_Lines++;
			}
			else if(inst[0].equals("IS")) {
				t_code = "+" + inst[1];
				if(code[0].equals("AREG")) {
					t_reg = "1";
					for(int k = 0; k < symtab_entries; k++) {
						if(code[1].equals(symtable[k].symbol)) {
							t_add = Integer.toString(symtable[k].address);
							break;
						}
					}
				}
				else if(code[0].equals("BREG")) {
					t_reg = "2";
					for(int k = 0; k < symtab_entries; k++) {
						if(code[1].equals(symtable[k].symbol)) {
							t_add = Integer.toString(symtable[k].address);
							break;
						}
					}
				}
				else if(code[0].equals("CREG")) {
					t_reg = "3";
					for(int k = 0; k < symtab_entries; k++) {
						if(code[1].equals(symtable[k].symbol)) {
							t_add = Integer.toString(symtable[k].address);
							break;
						}
					}
				}
				else {
					t_reg = "0";				
					for(int k = 0; k < symtab_entries; k++) {
						if(code[0].equals(symtable[k].symbol)) {
							t_add = Integer.toString(symtable[k].address);
							break;
						}
					}
				}
				tuple = new Target_Code(t_code,t_reg,t_add);
				target[target_Lines] = tuple;
				target_Lines++;
			}
		}
		
		for(int j = 0; j < target_Lines; j++) {
			System.out.println(target[j].code + "\t" + target[j].reg + "\t" + target[j].address);
		}
	}
	
	public boolean belongstoAD(String mnemonic) {
		for(int i = 0; i < 5; i++) {
			if(mnemonic.equals(Asm_Dir[i].directive)) {
				return true;
			}
		}		
		return false;
	}
	
	
	public static void main(String[] args) throws Exception{
		Pass1 p = new Pass1();
		p.read();
		p.process_Input();
		p.pass1();
		p.display();
		p.pass2();
	}

}