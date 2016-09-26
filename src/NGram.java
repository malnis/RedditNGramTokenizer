import java.io.File;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;


public class NGram {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		String file = args[0];
		
		List<File> lsFiles = (List<File>) FileUtils.listFiles(new File(file), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		for (File f: lsFiles){
			if (f.getName().contains("comments")){
				System.out.println("File:" + f.getName());
				NGramThread R1 = new NGramThread(f);
			    R1.start();

			}
		}
		
	}
}
