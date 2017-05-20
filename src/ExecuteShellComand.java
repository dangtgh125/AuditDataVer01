import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.rosuda.JRI.*;

public class ExecuteShellComand {

	void executeCommand(String[] command) {
		Rengine r = new Rengine(command, false, null);
		r.eval(command[0]);
		r.end();
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