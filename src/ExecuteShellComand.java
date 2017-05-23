import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.rosuda.JRI.*;

public class ExecuteShellComand {
	static Rengine r;
	void executeCommand(String[] command) {
		if (!Rengine.versionCheck()) {
			System.err.println("** Version mismatch - Java files don't match library version.");
			System.exit(1);
		}
		if(r == null)
			r = new Rengine(command, false, null);
		

		r.eval(command[0]);
		

		
		System.out.println("complete R script!");
	}
	
	String executeCommand01(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader =
                            new BufferedReader(new InputStreamReader(p.getInputStream()));

                        String line = "";
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}

}