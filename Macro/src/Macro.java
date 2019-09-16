import java.io.IOException;

public class Macro {
	
	static MDT[] mdt;
	static MNT[] mnt;
	static int macro_cnt = 0;
	static int mdtline_cnt = 0;
	
	public static void main(String[] args) throws IOException {
		
		Pass1 p1 = new Pass1();
		p1.read();
		p1.pass1();
		p1.display();
		
		Pass2 p2 = new Pass2();
		p2.read();
		p2.pass2();
		p2.display();
	}

}