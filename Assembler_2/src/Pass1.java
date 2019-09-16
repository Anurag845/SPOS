import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Pass1 {
	
	String source = "";
	String sourcecode[];
	int len;
	Source_Tuple srclines[];
	int loc_cntr;
	int littab_ptr;
	int pooltab_ptr;
	OPTAB optab[] = new OPTAB[11];
	SYMTAB symtab[] = new SYMTAB[10];
	LITTAB littab[] = new LITTAB[3];
	Int_Code int_code[];
	int icode_ptr;
	int sym_cntr;
	
	public void initialize() {
		optab[0] = new OPTAB("STOP",0);
		//optab[0].mnemonic = "STOP";
		//optab[0].code = 0;
		optab[1] = new OPTAB("ADD",1);
		//optab[1].mnemonic = "ADD";
		//optab[1].code = 1;
		optab[2] = new OPTAB("SUB",2);
		//optab[2].mnemonic = "SUB";
		//optab[2].code = 2;
		optab[3] = new OPTAB("MUL",3);
		//optab[3].mnemonic = "MUL";
		//optab[3].code = 3;
		optab[4] = new OPTAB("MOVER",4);
		//optab[4].mnemonic = "MOVER";
		//optab[4].code = 4;
		optab[5] = new OPTAB("MOVEM",5);
		//optab[5].mnemonic = "MOVEM";
		//optab[5].code = 5;
		optab[6] = new OPTAB("COMP",6);
		//optab[6].mnemonic = "COMP";
		//optab[6].code = 6;
		optab[7] = new OPTAB("BC",7);
		//optab[7].mnemonic = "BC";
		//optab[7].code = 7;
		optab[8] = new OPTAB("DIV",8);
		//optab[8].mnemonic = "DIV";
		//optab[8].code = 8;
		optab[9] = new OPTAB("READ",9);
		//optab[9].mnemonic = "READ";
		//optab[9].code = 9;
		optab[10] = new OPTAB("PRINT",10);
		//optab[10].mnemonic = "PRINT";
		//optab[10].code = 10;
	}
	
	public void read() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("source.txt"));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while(line!=null) {
			sb.append(line);
			sb.append("\n");
			line = br.readLine();
		}
		source = sb.toString();
	}
	
	public void separate() {
		sourcecode = source.split("\n");
		len = sourcecode.length;
		srclines = new Source_Tuple[len];
		int_code = new Int_Code[len];
		for(int cnt = 0; cnt < len; cnt++) {
			String[] eachLine = sourcecode[cnt].split("\t");
			Source_Tuple t = null;
			if(eachLine.length == 1) {
				t = new Source_Tuple("",eachLine[0],"");
			}
			else if(eachLine.length == 2) {
				t = new Source_Tuple("",eachLine[0],eachLine[1]);
			}
			else if(eachLine.length == 3) {
				t = new Source_Tuple(eachLine[0],eachLine[1],eachLine[2]);
			}
			srclines[cnt] = t;
		}
	}
	
	public void pass1() {
		sym_cntr = 0;
		littab_ptr = 0;
		icode_ptr = 0;
		for(int cnt = 0; cnt < len; cnt++) {
			if(srclines[cnt].mnemonic.equals("START") || srclines[cnt].mnemonic.equals("ORIGIN")) {
				if(srclines[cnt].opcode.contains("+")) {
					String content[] = srclines[cnt].opcode.split("\\+");
					int k = 0;
					for(k = 0; k < sym_cntr; k++) {
						if(symtab[k].symbol.equals(content[0])) {
							break;
						}
					}
					loc_cntr = symtab[k].address + Integer.parseInt(content[1]);
				}
				else if(srclines[cnt].opcode.contains("-")) {
					String content[] = srclines[cnt].opcode.split("-");
					int k = 0;
					for(k = 0; k < sym_cntr; k++) {
						if(symtab[k].symbol.equals(content[0])) {
							break;
						}
					}
					loc_cntr = symtab[k].address - Integer.parseInt(content[1]);
				}
				else {
					loc_cntr = Integer.parseInt(srclines[cnt].opcode);
				}
				int code = 0;
				if(srclines[cnt].mnemonic.equals("START")) {
					code = 1;
				}
				else {
					code = 4;
				}
				String inst = "AD," + Integer.toString(code);
				String op = "C," + Integer.toString(loc_cntr);
				Int_Code ic = new Int_Code(inst,"",op);
				int_code[icode_ptr++] = ic;
				continue;
			}
			if(!srclines[cnt].symbol.equals("")) {
				String label = srclines[cnt].symbol;
				if(srclines[cnt].mnemonic.equals("EQU")) {
					int k = 0;
					for(k = 0; k < sym_cntr; k++) {
						if(symtab[k].symbol.equals(srclines[cnt].opcode)) {
							break;
						}
					}
					symtab[sym_cntr++] = new SYMTAB(label,symtab[k].address);
					continue;
				}
				else {
					symtab[sym_cntr++] = new SYMTAB(label,loc_cntr);
					loc_cntr++;
				}
			}
			if(srclines[cnt].mnemonic.equals("LTORG")) {
				littab[0].address = loc_cntr;
				loc_cntr++;
				littab[1].address = loc_cntr;
				loc_cntr++;
				continue;
			}
			if(srclines[cnt].mnemonic.equals("DS")) {
				String inst = "DL,01";
				int size = Integer.parseInt(srclines[cnt].opcode);
				loc_cntr += size;
				String op = "C," + Integer.toString(size);
				Int_Code ic = new Int_Code(inst,"",op);
				int_code[icode_ptr++] = ic;
			}
			else {
				String mn = srclines[cnt].mnemonic;
				//System.out.println("Mnemonic obtained is " + mn);
				int code = -1;
				for(int i = 0; i < 11; i++) {
					if(mn.equals(optab[i].mnemonic)) {
						code = optab[i].code;
						break;
					}
				}
				if(mn.equals("LAST")) {
					symtab[sym_cntr++] = new SYMTAB("LAST",loc_cntr);
					loc_cntr++;
					continue;
				}
				if(mn.equals("END")) {
					continue;
				}
				String inst = "IS," + Integer.toString(code);
				String operands[] = srclines[cnt].opcode.split(",");
				String op1 = "", op2 = "";
				if(operands[0].equals("AREG")) {
					op1 = "RG,1";
				}
				else if(operands[0].equals("BREG")) {
					op1 = "RG,2";
				}
				else if(operands[0].equals("CREG")) {
					op1 = "RG,3";
				}
				if(operands[1].startsWith("=")) {
					littab[littab_ptr] = new LITTAB(operands[1],0);
					op2 = "L," + Integer.toString(littab_ptr);
					littab_ptr++;
				}
				else {
					int k = 0;
					for(k = 0; k < sym_cntr; k++) {
						if(symtab[k].symbol.equals(operands[1])) {
							break;
						}
					}
					op2 = "S," + Integer.toString(k);
				}
				int_code[icode_ptr++] = new Int_Code(inst,op1,op2);
				loc_cntr++;
			}
		}
	}
	
	public void show() {
		//System.out.println("Source code as read is ");
		//System.out.println("Symbol\tMnemonic\tOpcode");
		for(int i = 0; i < icode_ptr; i++) {
			//System.out.println(srclines[i].symbol + "\t" + srclines[i].mnemonic + "\t" + srclines[i].opcode);
			System.out.println(int_code[i].inst + "\t" + int_code[i].op1 + "\t" + int_code[i].op2);
		}
		System.out.println();
		System.out.println();
		for(int i = 0; i < sym_cntr; i++) {
			System.out.println(symtab[i].symbol + " " + symtab[i].address);
		}
	}
	
	public static void main(String[] args) throws IOException {
		Pass1 obj = new Pass1();
		obj.initialize();
		obj.read();
		obj.separate();
		
		obj.pass1();
		obj.show();
	}

}
