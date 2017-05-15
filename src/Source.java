import java.io.*;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.swing.plaf.SliderUI;

//import org.rosuda.JRI.Rengine;

import java.security.GeneralSecurityException;


public class Source {
	public static String _encryptAlg = "AES-256";
	public static String _hashAlg = "MD5";
	static final MngrFiles mgrFile = new MngrFiles();
	private static MngrScript _mgrScript = new MngrScript();
	
	public static Vector<String> fileList = new Vector<String>();
	public static Vector<Double> ProbVector = new Vector<Double>();
	public static Vector<String> MAC = new Vector<String>();
	
	/*
	 * MAIN()
	 */
	public static void main(String[] args) throws  Exception {
		// TODO Auto-generated method stub

		_mgrScript.exeCreateRScriptFile(100);
		
		ExecuteShellComand obj = new ExecuteShellComand();

		String command = "Rscript src\\Distribution.R";

		String output = obj.executeCommand(command);

		//System.out.println(output);

		//Vector<Double> ProbVector = new Vector<Double>();
		convertRToArray(output, ProbVector);
		
		Prob_Handle prh = new Prob_Handle(ProbVector);
		double confidentValue = 0.95;
		double mean = prh.getMean();
		double sd = prh.getSD();
		double a = mean - sd*prh.getZ(0.025);
		double b = mean + sd*prh.getZ(0.025);
		
		System.out.println("mean = " + mean);
		System.out.println("sd = " + sd);
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		/*-----------------------------------------------------------------------------------------*/
		// @ProbVector is a list of probability
		/*HMAC Test
		 * To excute HMAC set input key: String set input data: String
		 * initialize Hmac with _hashAlg and key update data
		 */
		//HMACTest();
		/*-----------------------------------------------------------------------------------------*/
		// for each file in encrypt, compute HMAC and save it into csv file
		mgrFile.getFileList(MngrFiles.folderInput, fileList);
		System.out.println(fileList.size());
		for(int i = 0; i<fileList.size(); i++)
		{
			System.out.println(fileList.get(i));
		}
		Encrypt("trongcauhcmus123");
		Primary();
		mgrFile.writeResultCsv();
		/*compute [a, b]*/

		
	} 
	/*
	 * copute HMAC for each file encrypted
	 * */
	public static void Primary() throws GeneralSecurityException, IOException{
		
		for (int i = 0; i < fileList.size(); i++) {
			HMAC hmac = new HMAC(_hashAlg, ProbVector.get(i).toString().getBytes());
			
			//folder Input ở đây là folder chứa file Encrypt
			String tmpfile = MngrFiles.folderOutput + fileList.get(i) + ".enc";
			System.out.println(tmpfile);
			
			byte[] sign = hmac.signFile(tmpfile);
			
	        //convert the byte to hex format method 1
	        String sb = new String();
	        for (int j = 0; j < sign.length; j++) {
	         sb = sb + (Integer.toString((sign[j] & 0xff) + 0x100, 16).substring(1));
	        }
	        System.out.println(sb);
	        MAC.addElement(sb);
		}
	}
	
	public static void Encrypt(String key) throws Exception{

		for(int i = 0; i < fileList.size(); i++){
			String tmp = fileList.get(i);
			String fileName = MngrFiles.folderInput + tmp;
			String resultFileName = MngrFiles.folderOutput + tmp + ".enc";
			
			File file = new File(fileName);
			if(!file.exists()){
				//System.out.println("No file "+fileName);
				return;
			}
			File file2 = new File(resultFileName);
			if(file2.exists()){
				//System.out.println("File for encrypted temp file already exists. Please remove it or use a different file name");
				return;
			}

			AES.copy(Cipher.ENCRYPT_MODE, fileName, resultFileName, key);
			
			//System.out.println("Success. Find encrypted files in current directory");
		}
	}
	
	public static void Decrypt(String key) throws Exception {
		MainGUI._txtAreaDecrypt.setText("");
		for(int i = 0; i < fileList.size(); i++){
			String tmp = fileList.get(i);
			String fileName = MngrFiles.folderInput + tmp;
			String resultFileName = MngrFiles.folderOutput + tmp;
			MainGUI._txtAreaDecrypt.append(tmp + "...");
			
			// File encrypt has exception is .enc
			String temp[] = resultFileName.split("\\.");
			if(!temp[1].equals("enc")){
				resultFileName = temp[0] + "." + temp[1];
			}
			else
				resultFileName = temp[0];

			File file = new File(fileName);
			if(!file.exists()){
				MainGUI._txtAreaDecrypt.append("Fail\nNo file " + fileName + "\n");
				System.out.println("No file "+fileName);
				return;
			}
			File file2 = new File(resultFileName);
			if(file2.exists()){
				MainGUI._txtAreaDecrypt.append("Fail\nFile for encrypted temp file already exists. Please remove it or use a different file name\n");
				System.out.println("File for the result decrypted file already exists. Please remove it or use a different file name");
				return;
			}

			AES.copy(Cipher.DECRYPT_MODE, fileName, resultFileName, key);

			MainGUI._txtAreaDecrypt.append("Success\n");
			System.out.println("Success. Find decrypted files in current directory");
		}
	}
	
	public static void HMACTest() throws GeneralSecurityException, IOException {
		long startTime;
		double duration;
		long endTime;
		String fileDirect = "C:\\Users\\trong\\Google Drive\\WORK DOCUMENT\\Computer Security\\K12\\Topic02_ComputerNetworkOverview\\CCDA DESGN 640-864.rar";
		String keyPhrase = "this is a key";
		HMAC hmac = new HMAC(_hashAlg, keyPhrase);
		
		startTime = System.nanoTime();
		byte[] sign = hmac.signFile(fileDirect);
		endTime = System.nanoTime();
		duration = (endTime - startTime) / 1000000.0;
		
        //convert the byte to hex format method 1
        String sb = new String();
        for (int i = 0; i < sign.length; i++) {
         sb = sb + (Integer.toString((sign[i] & 0xff) + 0x100, 16).substring(1));
        }
        System.out.println(sb);
        System.out.println(duration);
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

}
