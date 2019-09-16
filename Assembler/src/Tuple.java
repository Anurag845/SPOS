
public class Tuple {

	String label;
	String inst;
	String opcode;
	int address;
	
	Tuple(String label, String inst, String opcode, int address) {
		this.label = label;
		this.inst = inst;
		this.opcode = opcode;
		this.address = address;
	}
}