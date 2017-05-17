import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

public class MngrFiles {
	public static String folderInput = "src\\DataSet\\";
	public static String folderOutput = "src\\Encrypted\\";
	static File _fileResult = null;
	static FileOutputStream _fos = null;
	public static int _countBlock = 0;
	
	public MngrFiles(){
		
	}
	
	public int getFileList(String folderData, Vector<String> fileList){
		
		//get file from folder
		File f = new File(folderData);
		File[] listOfFiles = f.listFiles();
		int count = 0;
		
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				count++;
				if (count % MainGUI._numFilesOfBlock == 0) {
					_countBlock++;
				}
				fileList.add(listOfFiles[i].getName());
			}
			else if (listOfFiles[i].isDirectory()) {
				//System.out.println("Directory " + listOfFiles[i].getName());
				this.getFileList(listOfFiles[i].getPath(), fileList);
			}
			
		}
		return fileList.size();
	}
	
	public void writeResultCsv(String filename) throws IOException {
		String buffer = null;
		byte[] bytes = null;
		int numfile = 0, index = 0;		

		for(int i = 0; i < _countBlock; i++){
			int temp = i + 1;
			_fileResult = new File(temp + "_" + filename + ".csv");
			_fos		= new FileOutputStream(_fileResult, true);
			
			if (!_fileResult.exists()) {
				_fileResult.createNewFile();
			}
			
			buffer = "Block " + ++i + "\nFile name, MAC, Probvector\n";
			bytes = buffer.getBytes();
			_fos.write(bytes);
			_fos.flush();
			
			for(int j = 0; j < MainGUI._numFilesOfBlock; j++) {
				buffer = Source.fileList.get(index) + ", " + Source.MAC.get(index) + ", " + Source.ProbVector.get(index) +"\n";
				bytes = buffer.getBytes();
				_fos.write(bytes);
				_fos.flush();
				index++;
			}
			_fos.close();
		}
		
		numfile = Source.fileList.size() - (_countBlock * MainGUI._numFilesOfBlock);
		if (numfile > 0) {
			int temp = _countBlock + 1;
			_fileResult = new File(temp + "_" + filename + ".csv");
			_fos		= new FileOutputStream(_fileResult, true);
			buffer = "Block " + ++_countBlock + "\nFile name, MAC, Probvector\n";
			bytes = buffer.getBytes();
			_fos.write(bytes);
			_fos.flush();
			
			for (int i = 0; i < numfile; i++) {
				buffer = Source.fileList.get(index) + ", " + Source.MAC.get(index) + ", " + Source.ProbVector.get(index) +"\n";
				bytes = buffer.getBytes();
				_fos.write(bytes);
				_fos.flush();
				index++;
			}

			_fos.close();
		}
	}
	
	public static void writeMACresult(Vector<String> result, int blockID, String folderResult) throws IOException{
		_fileResult = new File(folderResult + blockID + "_MACResult" + ".csv");
		_fos = new FileOutputStream(_fileResult, false);
		String buffer = null;
		//byte[] bytes = null;
		
		if (!_fileResult.exists()) {
			_fileResult.createNewFile();
		}
		
		//write blockID and blockSize First
		buffer = Integer.toString(blockID) + '\n' + Integer.toString(result.size()) + '\n';
		_fos.write(buffer.getBytes());
		
		for(int i = 0; i<result.size(); i++){
			buffer = result.get(i)+ '\n';
			_fos.write(buffer.getBytes());
			_fos.flush();
		}
		_fos.close();
	}
	
	public static void writeProbVectorResult(Vector<Double> result, int blockID, String folderResult) throws IOException{
		_fileResult = new File(folderResult + blockID + "_ProbVectorResult" + ".csv");
		_fos = new FileOutputStream(_fileResult, false);
		String buffer = null;
		//byte[] bytes = null;
		
		if (!_fileResult.exists()) {
			_fileResult.createNewFile();
		}
		
		//write blockID and blockSize First
		buffer = Integer.toString(blockID) + '\n' + Integer.toString(result.size()) + '\n';
		_fos.write(buffer.getBytes());
		
		for(int i = 0; i<result.size(); i++){
			buffer = result.get(i).toString() + '\n';
			_fos.write(buffer.getBytes());
			_fos.flush();
		}
		_fos.close();
	}
}
