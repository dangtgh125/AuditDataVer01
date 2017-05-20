import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MngrScript {
	private String _name = null;
	private FileOutputStream _out;
	public final int a = 90;
	private static String _folderTemp = "TempR\\";

	public MngrScript() {
		_name = "Distribution.R";
		_out  = null;
	}
	
	private static double randomMean(int numFile) {
		double random = numFile*0.2 + numFile * 0.65 * Math.random();
		return random;
	}
	
	private static double randomSd(int numFile) {
		double random = numFile*0.02 + numFile * 0.08 * Math.random();
		return random;
	}
	
	public void exeCreateRScriptFile(int numFile, int numBlock) throws IOException {
		double mean = randomMean(numFile);
		double sd = randomSd(numFile);
		System.out.println("mean = " + mean);
		System.out.println("sd = " + sd);
		String content = "# Create a sequence of numbers between 1 and " + numFile + " incrementing by 1."
						+ "\n\tx <- seq(1, " + numFile + ", by = 1)"
						+ "\n\t# Choose the mean as 2.5 and standard deviation as 0.5."
						+ "\n\ty <- dnorm(x, mean = " + mean + ", sd = " + sd + ")"
						+ "\n\tprint(y)"
						+ "\n\twrite.csv(y,\"Result\\\\" + _folderTemp + "\\" + numBlock + "_output.csv\", row.names = FALSE)";
		
		File f = new File("src\\" + _name);
		if (f.exists()) {
			if (f.delete()) {
				System.out.println("Delete success file");
			}
		}
		f.createNewFile();
		
		_out = new FileOutputStream(f);
		byte[] buffer = content.getBytes();
		_out.write(buffer);
		_out.flush();
		_out.close();
		
	}
	
	public static void deleteTempFiles() {
		//get file from folder
		File f = new File("Result\\" + _folderTemp);
		File[] listOfFiles = f.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				listOfFiles[i].delete();
			}
			else if (listOfFiles[i].isDirectory()) {
				//System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
		System.out.println("Delete temp file success");
	}
	
	
	public static void convertRToArray(String in, Vector<Double> out) {
		// String[] splitString = in.split("(\\w)*.(\\w)*e-(\\w)*");

		String pattern = "\\d+\\.\\d*e?-?\\d+";
		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);
		// Now create matcher object.
		Matcher m = r.matcher(in);

		while (m.find() == true) {
			out.addElement(Double.parseDouble(m.group(0)));
		}
		for (int i = 0; i < out.size(); i++) {
			//System.out.println(out.get(i));
		}
	}
	
	/*
	 * Mở từng file chứa kết quả prob của từng block và đọc từng dòng vào
	 */
	public static void convertRToArray(Vector<Double> out) throws IOException {
		FileReader fileName = null;
		BufferedReader reader = null;
		String buffer = null;
		
		for (int i = 0; i < MngrFiles._countBlock; i++) {
			int numBlock = i + 1;
			fileName = new FileReader("Result\\" + _folderTemp + numBlock + "_output.csv");
			reader = new BufferedReader(fileName);
			
			buffer = reader.readLine();
			while ((buffer = reader.readLine()) != null) {
				out.addElement(Double.parseDouble(buffer));
			}
			
			fileName.close();
			reader.close();
		}
	}
}
