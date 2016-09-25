import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class Process {
	public static void main(String[] args){
		//input in the location of the folder that includes many comment files.
		String file = args[0];
		
		List<File> lsFiles = (List<File>) FileUtils.listFiles(new File(file), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		for (File f: lsFiles){
			System.out.println("File:" + f.getName());
			FileMultithread R1 = new FileMultithread(f);
		    R1.start();
		}
	}
}
