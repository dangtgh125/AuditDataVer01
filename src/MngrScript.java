import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MngrScript {
	private String _name = null;
	private FileOutputStream _out;
	public static final int a = 0;

	public MngrScript() {
		_name = "Distribution.R";
		_out  = null;
	}
	
	private double randomMean(int numFile) {
		double random = Math.random() * numFile;
		return random;
	}
	
	private double randomSd(int numFile, double mean) {
		double random = Math.random() * numFile - (mean * 0.5);
		return random;
	}
	
	public void exeCreateRScriptFile(int numFile) throws IOException {
		double mean = randomMean(numFile);
		double sd = randomSd(numFile, mean);
		String content = "# Create a sequence of numbers between 1 and " + numFile + " incrementing by 1."
						+ "\nx <- seq(1, " + numFile + ", by = 1)"
						+ "\n# Choose the mean as 2.5 and standard deviation as 0.5."
						+ "\ny <- dnorm(x, mean = " + mean + ", sd = " + sd + ")"
						+ "\nprint(y)"
						+ "\nwrite.csv(y,\"output.csv\", row.names = FALSE)";
		
		File f = new File("src\\" + this._name);
		if (f.exists()) {
			if (f.delete()) {
				System.out.println("Delete success file");
			}
		}
		f.createNewFile();
		
		this._out = new FileOutputStream(f);
		byte[] buffer = content.getBytes();
		this._out.write(buffer);
		this._out.flush();
		this._out.close();
		
	}
}
