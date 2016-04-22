package parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileParser {

	public TransactionList readFile(String fileName)
	{
		TransactionList transactionList = new TransactionList();
		Charset charset = Charset.forName("US-ASCII");
		Path file = Paths.get(fileName);
		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
		    String line = reader.readLine();
		    String[] titles = line.split("\t");
		    while ((line = reader.readLine()) != null) {
		    	String[] elements = line.split("\t");
		    	Transaction transaction = new Transaction();
		    	for(int i=0; i<elements.length; i++) {
		    		String elem = elements[i];
		    		String title = titles[i];
		    		transaction.add(title + "=" + elem);
		    	}
		    	transactionList.add(transaction);
		    }
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
		
		return transactionList;
	}
	
	private Transaction extractSet(String line)
	{
		Transaction item = new Transaction();
		String[] elements = line.split("\t");
		
		for(String s : elements) {
			item.add(s);
		}

		return item;
	}
	
}
