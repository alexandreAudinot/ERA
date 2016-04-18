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
		TransactionList itemSet = new TransactionList();
		Charset charset = Charset.forName("US-ASCII");
		Path file = Paths.get(fileName);
		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
		    String line = reader.readLine();
		    itemSet.setTitles(line);
		    while ((line = reader.readLine()) != null) {
		    	Transaction item = extractSet(line);
		        itemSet.add(item);
		    }
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
		
		return itemSet;
	}
	
	private Transaction extractSet(String line)
	{
        System.out.println(line);
        
		Transaction item = new Transaction();
		String[] elements = line.split("\t");
		
		for(String s : elements) {
			item.add(s);
		}

		return item;
	}
	
	public static void main(String[] args)
	{
		FileParser parser = new FileParser();
		parser.readFile("tickets_de_caisse.txt");
	}
}
