import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.*;
import java.security.*;
import javax.crypto.spec.*;
import javax.crypto.*;

public class AES {
	private static final int IV_LENGTH=16;

	/* A helper - to reuse the stream code below - if a small String is to be encrypted */
	public static byte[] encrypt(String plainText, String password) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(plainText.getBytes("UTF8"));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		processEncrypt(bis, bos, password);
		return bos.toByteArray();
	}


	public static byte[] decrypt(String cipherText, String password) throws Exception {
		byte[] cipherTextBytes = cipherText.getBytes();
		ByteArrayInputStream bis = new ByteArrayInputStream(cipherTextBytes);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		processDecrypt(bis, bos, password);		
		return bos.toByteArray();
	}


	public static void processEncrypt(InputStream in, OutputStream out, String password) throws Exception{

		SecureRandom r = new SecureRandom();
		byte[] iv = new byte[IV_LENGTH];
		r.nextBytes(iv);
		out.write(iv); //write IV as a prefix
		out.flush();
		//System.out.println(">>>>>>>>written"+Arrays.toString(iv));

		Cipher cipher = Cipher.getInstance("AES/CFB8/PKCS5Padding"); //"DES/ECB/PKCS5Padding";"AES/CBC/PKCS5Padding"
		SecretKeySpec keySpec = new SecretKeySpec(password.getBytes(), "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);    	

		out = new CipherOutputStream(out, cipher);
		byte[] buf = new byte[1024];
		int numRead = 0;
		while ((numRead = in.read(buf)) >= 0) {
			out.write(buf, 0, numRead);
		}
		out.close();
	}


	public static void processDecrypt(InputStream in, OutputStream out, String password) throws Exception{

		byte[] iv = new byte[IV_LENGTH];
		in.read(iv);
		//System.out.println(">>>>>>>>red"+Arrays.toString(iv));

		Cipher cipher = Cipher.getInstance("AES/CFB8/PKCS5Padding"); //"DES/ECB/PKCS5Padding";"AES/CBC/PKCS5Padding"
		SecretKeySpec keySpec = new SecretKeySpec(password.getBytes(), "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

		in = new CipherInputStream(in, cipher);
		byte[] buf = new byte[1024];
		int numRead = 0;
		while ((numRead = in.read(buf)) >= 0) {
			out.write(buf, 0, numRead);
		}
		out.close();
	}
	
	public static void processEncrypt(InputStream in, OutputStream out, byte[] password) throws Exception{

		SecureRandom r = new SecureRandom();
		byte[] iv = new byte[IV_LENGTH];
		r.nextBytes(iv);
		out.write(iv); //write IV as a prefix
		out.flush();
		//System.out.println(">>>>>>>>written"+Arrays.toString(iv));

		Cipher cipher = Cipher.getInstance("AES/CFB8/PKCS5Padding"); //"DES/ECB/PKCS5Padding";"AES/CBC/PKCS5Padding"
		SecretKeySpec keySpec = new SecretKeySpec(password, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);    	

		out = new CipherOutputStream(out, cipher);
		byte[] buf = new byte[1024];
		int numRead = 0;
		while ((numRead = in.read(buf)) >= 0) {
			out.write(buf, 0, numRead);
		}
		out.close();
	}

	public static void processDecrypt(InputStream in, OutputStream out, byte[] password) throws Exception{

		byte[] iv = new byte[IV_LENGTH];
		in.read(iv);
		//System.out.println(">>>>>>>>red"+Arrays.toString(iv));

		Cipher cipher = Cipher.getInstance("AES/CFB8/PKCS5Padding"); //"DES/ECB/PKCS5Padding";"AES/CBC/PKCS5Padding"
		SecretKeySpec keySpec = new SecretKeySpec(password, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

		in = new CipherInputStream(in, cipher);
		byte[] buf = new byte[1024];
		int numRead = 0;
		while ((numRead = in.read(buf)) >= 0) {
			out.write(buf, 0, numRead);
		}
		out.close();
	}

	public static void copy(int mode, String inputFile, String outputFile, String password) throws Exception {

		BufferedInputStream is = new BufferedInputStream(new FileInputStream(inputFile));
		BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(outputFile));
		if(mode==Cipher.ENCRYPT_MODE){
			processEncrypt(is, os, password);
		}
		else if(mode==Cipher.DECRYPT_MODE){
			processDecrypt(is, os, password);
		}
		is.close();
		os.close();
	}


	public static void main(String[] args){

		if(args.length<1){
			System.out.println("Pass at least one argument (filename)");		
			return;
		}
		try{
			//check files - just for safety
			String fileName=args[0];
			String tempFileName=fileName+".enc";
			String resultFileName=fileName+".dec";

			File file = new File(fileName);
			if(!file.exists()){
				System.out.println("No file "+fileName);
				return;
			}
			File file2 = new File(tempFileName);
			File file3 = new File(resultFileName);
			if(file2.exists() || file3.exists()){
				System.out.println("File for encrypted temp file or for the result decrypted file already exists. Please remove it or use a different file name");
				return;
			}

			copy(Cipher.ENCRYPT_MODE, fileName, tempFileName, "password12345678");
			copy(Cipher.DECRYPT_MODE, tempFileName, resultFileName, "password12345678");

			System.out.println("Success. Find encrypted and decripted files in current directory");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}	

}

