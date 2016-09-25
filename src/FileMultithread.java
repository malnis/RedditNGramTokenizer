import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;

public class FileMultithread implements Runnable {
	private File file;
	private Thread t;
	private final  static String EN_SENT = "/home/hellovn/ngrams";
	
	public FileMultithread(File aFile){
		file = aFile;
	}
	public void run() {										
		Scanner sc;
		try {
			//loop through Reddit Json file
			sc = new Scanner(file, "UTF-8");
			Pattern p = Pattern.compile(
				    "\"body\":\"(.*?)\",\""
				);
				File output = new File(file.getPath() + "-comments");
				
				InputStream modelIn = new FileInputStream(EN_SENT + "/en-sent.bin");
			    SentenceModel model = new SentenceModel(modelIn);
				SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
				
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
					while (sc.findWithinHorizon(p, 0) != null)
					{
					  MatchResult m = sc.match();
					  
						String body = m.group(1).toLowerCase();
						String sentences[] = sentenceDetector.sentDetect(body);
						for (String record: sentences){
							writer.write(record + System.getProperty("line.separator"));
						}
						
					}
				    
				}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Thread finished:" + file.getName());
	}
	public void start ()
	   {
	      System.out.println("Starting " +  file.getName() );
	      if (t == null)
	      {
	         t = new Thread (this, file.getName());
	         t.start ();
	      }
	   }
}
