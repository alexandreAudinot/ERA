package parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.lang.math.NumberUtils;

public class FileParser {
	
	public static ArrayList<String> attributesName = new ArrayList<String>();
	public static ArrayList<Boolean> isNumericAttributes = new ArrayList<Boolean>();

	public TransactionList readFile(String fileName)
	{
		TransactionList transactionList = new TransactionList();
		Charset charset = Charset.forName("US-ASCII");
		Path file = Paths.get(fileName);
		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
		    String line = reader.readLine();
		    String[] titles = line.split("\t");
		    boolean isFirstValuedLine = true;
		    while ((line = reader.readLine()) != null) {
		    	String[] elements = line.split("\t");
		    	Transaction transaction = new Transaction();
		    	for(int i=0; i<elements.length; i++) {
		    		String elem = elements[i];
		    		String title = titles[i];
		    		if(isFirstValuedLine){
		    			isNumericAttributes.add(NumberUtils.isNumber(elem));
		    			attributesName.add(title);
		    		}
		    		transaction.add(title + "=" + elem);
		    	}
		    	transactionList.add(transaction);
		    	isFirstValuedLine = false;
		    	
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
	
	// When an attribute is numeric, add nbAttr boolean attributes and remove the numeric one
	public TransactionList numericAttrToBool(TransactionList tl, String s, int nbAttr){
		TransactionList res = new TransactionList();
		int j = attributesName.indexOf(s);
		if ((j != -1) && (isNumericAttributes.get(j) == true)){
			float minval = 999999;
			float maxval = -1;
			
			for(Transaction t : tl){
				float v = -1;
				String aa = t.getSet().get(j).toString();
				String[] aaa = aa.split("=",2);
				String aaaa = aaa[1];
				if(NumberUtils.isNumber(aaaa)){
					v = Float.parseFloat(aaaa);
				}
				if(v > maxval)
					maxval = v;
				if(v < minval && v > 0)
					minval = v;
			}
			int interval = Math.round((maxval - minval) / nbAttr);
			for(int i = 0; i < tl.size(); i++){
				Transaction t = new Transaction();
				for(int k = 0; k < nbAttr - 1; k++){					
					t = tl.get(i);	
					String bb = t.getSet().get(j).toString();
					String[] bbb = bb.split("=",2);
					String title = bbb[0];
					String value = bbb[1];
					
					float numericValue = Float.parseFloat(value);
					int newval = (int) (minval + (k+1) * interval);
					String addedAttr = "" + title + "<" + newval + "=";
					if(numericValue < (minval + (k+1) * interval))
						addedAttr += "T";
					else{
						addedAttr += "F"; 
					}
					t.add(addedAttr);
					if(k == nbAttr - 2){
						t.getSet().remove(j);
						res.add(t);
					}
				}
			}
			boolean n = isNumericAttributes.get(j);
			isNumericAttributes.remove(j);
			isNumericAttributes.add(false);
			attributesName.remove(s);
			attributesName.add(s);			
		}
		return res;
	}
	
	public TransactionList allNumToBool(TransactionList tl, int[] nbNewAttrPerAttr){
		ArrayList<String> tmp = new ArrayList<String>();
		for(int i = 0; i < isNumericAttributes.size(); i++){
			if(isNumericAttributes.get(i))
				tmp.add(attributesName.get(i));
		}
		TransactionList res = new TransactionList();
		res = tl;
		for(int j = 0; j < tmp.size(); j++){
			res = numericAttrToBool(res, tmp.get(j), nbNewAttrPerAttr[j]);
		}
		return res;
	}
	
}
