import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import javax.crypto.Cipher;

//import org.rosuda.JRI.Rengine;

import java.security.GeneralSecurityException;


public class Source {
	public static String _encryptAlg = "AES-256";
	public static String _hashAlg = "MD5";
	static final MngrFiles mgrFile = new MngrFiles();
	static final MngrScript mgrScript = new MngrScript();
	
	public static Vector<String> fileList = new Vector<String>();
	public static Vector<Double> ProbVector = new Vector<Double>();
	public static Vector<String> MAC = new Vector<String>();
	
	/*
	 * MAIN()
	 */
	public static void main(String[] args) throws  Exception {
		// TODO Auto-generated method stub

		/*for (int i = 0; i < 1; i++) {
			
			ProbGen(200, i+1, ProbVector);
		
			System.out.println("Size = " + ProbVector.size());
			Prob_Handle prh = new Prob_Handle(ProbVector);
			double mean = prh.getMean();
			double sd = prh.getSD();
			double a = mean - sd*prh.getZ(0.025);
			double b = mean + sd*prh.getZ(0.025);
			
			System.out.println("mean = " + mean);
			System.out.println("sd = " + sd);
			System.out.println("a = " + a);
			System.out.println("b = " + b);
			
			Double sum = (double) 0;
			for (int j = 0; j < ProbVector.size(); j++) {
				sum = sum + ProbVector.get(j);
				System.out.println("Prob = " + ProbVector.get(j));
			}

			System.out.println("Sum = " + sum);
			System.out.println("Size = " + ProbVector.size());
		}*/
		
		//ProbGen(300, 1, ProbVector);
		
		/*-----------------------------------------------------------------------------------------*/
		// @ProbVector is a list of probability
		/*HMAC Test
		 * To excute HMAC set input key: String set input data: String
		 * initialize Hmac with _hashAlg and key update data
		 */
		//HMACTest();
		/*-----------------------------------------------------------------------------------------*/
		// for each file in encrypt, compute HMAC and save it into csv file
		/*mgrFile.getFileList(MngrFiles.folderInput, fileList);
		System.out.println(fileList.size());
		for(int i = 0; i<fileList.size(); i++)
		{
			System.out.println(fileList.get(i));
		}
		Encrypt("trongcauhcmus123");
		Primary();
		mgrFile.writeResultCsv("Result");
		/*compute [a, b]*/
		

		
	} 
	/*Compute HMAC for file encrypted
	 * @numBlock : number of block
	 * @blockSize: number file on each block
	 * @fileList : list of file in folderEncrypt
	 * */
	public static void computeHMACfileList(int numBlock, int blockSize, Vector<String> fileList) throws GeneralSecurityException, IOException{
		
		int count = 1;
		Vector<String> strProbVector = new Vector<String>();
		for(int i = 0; i < numBlock; i++){
			//Generate ProbVector with number is blocksize
			
			ProbGen(blockSize, i + 1, ProbVector);
			MngrFiles.writeProbVectorResult(ProbVector, i + 1, "Result\\");
			ProbVector.clear();
			int temp = i + 1;
			MngrFiles.readKeyFileWithString("Result\\" + temp + "_ProbVectorResult.csv", strProbVector);
			
			//compute MAC for each block
			for(int j = 0; j < blockSize; j++){
				String tmp = computeHMACFile(MngrFiles.folderInput + fileList.get(i * blockSize + j), strProbVector.get(j));
				System.out.println("prob" + j + ": " + strProbVector.get(j));
				MainGUI._txtMACArea.append("Hmac file " + count + ": " + tmp + "\n");
				MAC.add(tmp);
				count++;
			}
			MngrFiles.writeMACresult(MAC, i + 1, "Result\\");
			
			MAC.clear();
			ProbVector.clear();
		}
		
		//compute for remain
		{
			int finalBlockSize = fileList.size() - numBlock*blockSize;
			System.out.println(finalBlockSize);
			if (finalBlockSize == 0)
				return;
			
			//Generate ProbVector with number is finalBlocksize
			ProbGen(finalBlockSize, numBlock + 1, ProbVector);
			
			//compute MAC for final block
			for(int j = 0; j < finalBlockSize; j++){
				String tmp = computeHMACFile(MngrFiles.folderInput + fileList.get(numBlock * blockSize + j), ProbVector.get(j).toString());
				System.out.println("End prob" + j + ": " + ProbVector.get(j).toString());
				MainGUI._txtMACArea.append("Hmac file " + count + ": " + tmp + "\n");
				MAC.add(tmp);
				count++;
			}
			MngrFiles.writeMACresult(MAC, numBlock+1, "Result\\");
			MngrFiles.writeProbVectorResult(ProbVector, numBlock + 1, "Result\\");
			MAC.clear();
			ProbVector.clear();
		}
		MngrScript.deleteTempFiles();
		
	}
	
	public static String computeHMACFile(String fileDirect, String keyPhrase) throws GeneralSecurityException, IOException{
		HMAC hmac = new HMAC(_hashAlg, keyPhrase);
		
		//startTime = System.nanoTime();
		byte[] sign = hmac.signFile(fileDirect);
		//endTime = System.nanoTime();
		//duration = (endTime - startTime) / 1000000.0;
		
        //convert the byte to hex format method 1
        String sb = new String();
        for (int i = 0; i < sign.length; i++) {
         sb = sb + (Integer.toString((sign[i] & 0xff) + 0x100, 16).substring(1));
        }
        //System.out.println(sb);
        
        return sb;
	}
	
	/*
	 * blockSize: số lượng file 1 block
	 * blockID: chi so block hien tai generate xs cho Block thứ num (tên file script <numBlock>_output.csv) blockID
	 * t nhớ là chuyển qua dùng Rjava rồi mà
	 * à quên, 
	 */
	public static void ProbGen(int blockSize, int blockID, Vector<Double> ProbVector) throws IOException{
		mgrScript.exeCreateRScriptFile(blockSize, blockID);
		
		ExecuteShellComand obj = new ExecuteShellComand();

		String[] command = {"source('src\\\\Distribution.R')"};

		obj.executeCommand(command);
		
		MngrScript.convertRToArray(blockID, blockSize, ProbVector);
	
	}
	
	/*
	 * copute HMAC for each file encrypted
	 */
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
	
	/*
	 * Mã hóa với tham số là tên file và key
	 * Giá trị trả về là true hoặc false
	 */
	public static boolean Encrypt(String name, String key, int blockID, int idFile) throws Exception{
		
		String strBlockID = new String(Integer.toString(blockID));
		String stridFile = new String(Integer.toString(idFile));

		int i = 0;
		int sizeBlockID = strBlockID.length();
		int sizeIdFile = stridFile.length();
		
		int sizeNumBlock = Integer.toString(MngrFiles._numBlock).length();
		int sizeNumFileOfBlock = Integer.toString(MainGUI._numFilesOfBlock).length();
		
		for (i = sizeBlockID; i < sizeNumBlock; i++) {
			strBlockID = "0" + strBlockID;
		}
		
		for (i = sizeIdFile; i < sizeNumFileOfBlock; i++) {
			stridFile = "0" + stridFile;
		}
		
		System.out.println("strBlockID: " + strBlockID + "\nstridFile" + stridFile);
		
		String fileName = MngrFiles.folderInput + name;
		String resultFileName = MngrFiles.folderOutput + strBlockID + "-" + stridFile + "-" + name + ".enc";
		
		File file = new File(fileName);
		if(!file.exists()){
			//System.out.println("No file "+fileName);
			return false;
		}
		File file2 = new File(resultFileName);
		if(file2.exists()){
			//System.out.println("File for encrypted temp file already exists. Please remove it or use a different file name");
			return false;
		}

		AES.copy(Cipher.ENCRYPT_MODE, fileName, resultFileName, key);
		
		//System.out.println("Success. Find encrypted files in current directory");
		return true;
	}
	
	/*
	 * Giải mã với tham số là tên file và key
	 * Giá trị trả về là true hoặc false
	 */
	public static boolean Decrypt(String name, String key) throws Exception {
		String fileName = MngrFiles.folderInput + name;
		String resultFileName = MngrFiles.folderOutput + name;
		
		// File encrypt has exception is .enc
		String temp[] = resultFileName.split("\\.");
		if(!temp[1].equals("enc")){
			resultFileName = temp[0] + "." + temp[1];
		}
		else
			resultFileName = temp[0];

		File file = new File(fileName);
		if(!file.exists()){
			System.out.println("No file "+fileName);
			return false;
		}
		File file2 = new File(resultFileName);
		if(file2.exists()){
			System.out.println("File for the result decrypted file already exists. Please remove it or use a different file name");
			return false;
		}

		AES.copy(Cipher.DECRYPT_MODE, fileName, resultFileName, key);

		System.out.println("Success. Find decrypted files in current directory");
		return true;
	}
	
	/*
	 * Input:
	 * 		+ DirectoryData: Đường dẫn tới thư mục chứa bộ dữ liệu cần kiểm định
	 * 		+ numOfFileVerify:	 Số lượng file kiểm tra
	 * Description: Hàm sẽ gọi hàm computeHMACFile để tính Hmac từng file trong khoảng startPoint và endPoint
	 * 				rồi lấy giá trị so sánh với MAC tương ứng nếu không khớp trả về false ngược lại khớp toàn bộ
	 * 				trả về true.
	 *  
	 */ 
	public static int Verify (String keyFile, String DirectoryData, int numOfFileVerify) throws GeneralSecurityException, IOException {
		
		int i = 0, j = 0;
		Vector<String> filelist = new Vector<String>();
		Vector<String> ProbVector = new Vector<String>();
		Vector<Integer>    listIndexRandom = new Vector<Integer>();
		mgrFile.getFileListVerify(DirectoryData, filelist);
		
		MngrFiles.readKeyFileWithString(keyFile, ProbVector);
		
		int temp = getRandom(filelist);
		listIndexRandom.add(temp);
		
		for (i = 1; i < numOfFileVerify; i++) {
			temp = getRandom(filelist);
			while (listIndexRandom.indexOf(temp) != -1) {
				temp++;
				if (temp >= filelist.size()) {
					temp = getRandom(filelist);
				}
			}
			listIndexRandom.add(temp);
		}
		
		Collections.sort(listIndexRandom);
		for (i = 0; i < numOfFileVerify; i++) {

			System.out.println(listIndexRandom.get(i));
		}
		MainGUI._txtAreaLogFileVerify.setText("");
		for (i = 0; i < numOfFileVerify; i++) {
			j = listIndexRandom.get(i);
			String tmp = computeHMACFile(DirectoryData + filelist.get(j), ProbVector.get(j));
			System.out.println("Prob: " + ProbVector.get(j));
			System.out.println("tmp: " + tmp);
			System.out.println("Mac: " + MAC.get(j));
			if (!tmp.equals(MAC.get(j))) {
				return j + 1;
			}
			MainGUI._txtAreaLogFileVerify.append("File " + (j + 1) + " verify success!\n");
		}
		MainGUI._txtAreaLogFileVerify.append("Done");
		listIndexRandom.clear();
		return 0;
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
	
	//convert blockID and idFile
	//VD: 100 file thì nếu idFile = 1 sau convert sẽ ra 001
	public static void processIdBlockAndFile(String blockID, String idFile) {
		int sizeBlockID = blockID.length();
		int sizeIdFile = idFile.length();
		int i = 0;
		
		int sizeNumBlock = Integer.toString(MngrFiles._numBlock).length();
		int sizeNumFileOfBlock = Integer.toString(MainGUI._numFilesOfBlock).length();
		
		for (i = sizeBlockID; i < sizeNumBlock; i++) {
			blockID = "0" + blockID;
		}
		
		for (i = sizeIdFile; i < sizeNumFileOfBlock; i++) {
			idFile = "0" + idFile;
		}
	}
	
	//Random trả về vị trí index bất kì trong mảng
	public static int getRandom(Vector<String> array) {
	    int rnd = new Random().nextInt(array.size());
	    return rnd;
	}
	
	// value là giá trị, places là số chỉ số thập phân sau dấu ,
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
