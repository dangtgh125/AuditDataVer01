import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

public class MngrFiles {
	public static String folderInput = "src\\DataSet\\";
	public static String folderOutput = "src\\Encrypted\\";
	File _fileResult = null;
	FileOutputStream _fos = null;
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
	
	public void writeResultCsv() throws IOException {
		_fileResult = new File("Result.csv");
		_fos		= new FileOutputStream(_fileResult, true);
		String buffer = null;
		byte[] bytes = null;
		int numfile = 0, index = 0;
		
		if (!_fileResult.exists()) {
			_fileResult.createNewFile();
		}
		

		for(int i = 0; i < _countBlock; i++){
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
		}
		
		numfile = Source.fileList.size() - (_countBlock * MainGUI._numFilesOfBlock);
		if (numfile > 0) {
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
		}
		
		_fos.close();
	}
}
