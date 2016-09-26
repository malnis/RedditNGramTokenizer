import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.shingle.ShingleFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.AttributeFactory;

import gnu.trove.map.hash.THashMap;

public class NGramThread implements Runnable {
	private File file;
	private Thread t;

	public NGramThread(File aFile) {
		file = aFile;
	}

	@Override
	public void run() {
		//Map<String, Integer> mapGram = new THashMap<>();
		try {
			// Class.forName ("org.netezza.Driver");
			// Connection ct =
			// DriverManager.getConnection("jdbc:netezza://",
			// "","");
			//
			// Statement sm = ct.createStatement();
			//
			// Integer month =
			// Integer.valueOf(file.getName().substring(file.getName().indexOf("-")
			// + 1,file.getName().indexOf("-")+3));
			// System.out.println("Month:" + month);
			//
			//map variables
			Map<String,Integer> mapGram1 = new THashMap<>();
			Map<String,Integer> mapGram2 = new THashMap<>();
			Map<String,Integer> mapGram3 = new THashMap<>();
			Map<String,Integer> mapGram4 = new THashMap<>();
			Map<String,Integer> mapGram5 = new THashMap<>();
			
			StringReader sreader = null;
			StandardTokenizer source = new StandardTokenizer(AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY);
			TokenStream tokenStream = new StandardFilter(source);
			ShingleFilter sf = new ShingleFilter(tokenStream, 2, 5);
			CharTermAttribute charTermAttribute = sf.addAttribute(CharTermAttribute.class);
												
			BufferedReader in = new BufferedReader(new FileReader(file));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				sreader = new StringReader(inputLine);

				//
				source.setReader(sreader);

				sf.reset();
				while (sf.incrementToken()) {
					String word = charTermAttribute.toString().toLowerCase();
					if (word.length() < 150) {																			
							String[] arrWords = word.split(" ");
							if (arrWords.length == 1){
								Integer value = mapGram1.putIfAbsent(word, 1);
								if (value != null){
									mapGram1.put(word, value + 1);
								}
							}
							if (arrWords.length == 2){
								Integer value = mapGram2.putIfAbsent(word, 1);
								if (value != null){
									mapGram2.put(word, value + 1);
								}
							}
							if (arrWords.length == 3){
								Integer value = mapGram3.putIfAbsent(word, 1);
								if (value != null){
									mapGram3.put(word, value + 1);
								}
							}
							if (arrWords.length == 4){
								Integer value = mapGram4.putIfAbsent(word, 1);
								if (value != null){
									mapGram4.put(word, value + 1);
								}
							}
							if (arrWords.length == 5){
								Integer value = mapGram5.putIfAbsent(word, 1);
								if (value != null){
									mapGram5.put(word, value + 1);
								}
							}
						}
					}
				tokenStream.close();

			}
			in.close();
			// write results
			try (BufferedWriter writer1 = new BufferedWriter(new FileWriter(new File(file.getPath() + "-grams1")));
					BufferedWriter writer2 = new BufferedWriter(new FileWriter(new File(file.getPath() + "-grams2")));
					BufferedWriter writer3 = new BufferedWriter(new FileWriter(new File(file.getPath() + "-grams3")));
					BufferedWriter writer4 = new BufferedWriter(new FileWriter(new File(file.getPath() + "-grams4")));
					BufferedWriter writer5 = new BufferedWriter(new FileWriter(new File(file.getPath() + "-grams5")))) {
				for (Map.Entry<String, Integer> entry : mapGram1.entrySet()) {					
					writer1.write(entry.getKey() + "," + entry.getValue() + System.getProperty("line.separator"));				
				}

				for (Map.Entry<String, Integer> entry : mapGram2.entrySet()) {					
					writer2.write(entry.getKey() + "," + entry.getValue() + System.getProperty("line.separator"));				
				}

				for (Map.Entry<String, Integer> entry : mapGram3.entrySet()) {					
					writer3.write(entry.getKey() + "," + entry.getValue() + System.getProperty("line.separator"));				
				}

				for (Map.Entry<String, Integer> entry : mapGram4.entrySet()) {					
					writer4.write(entry.getKey() + "," + entry.getValue() + System.getProperty("line.separator"));				
				}

				for (Map.Entry<String, Integer> entry : mapGram5.entrySet()) {					
					writer5.write(entry.getKey() + "," + entry.getValue() + System.getProperty("line.separator"));				
				}
			}
			System.out.println("File end:" + file.getName());
		} catch (

		FileNotFoundException e)

		{
			e.printStackTrace();
		} catch (

		IOException e)

		{
			e.printStackTrace();
		}
	}

	public void start() {
		System.out.println("Starting " + file.getName());
		if (t == null) {
			t = new Thread(this, file.getName());
			t.start();
		}
	}
}
