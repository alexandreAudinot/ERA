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

	public TransactionList readFile(String fileName)
	{
		ArrayList<String> attributesName = new ArrayList<String>();
		ArrayList<Boolean> isNumericAttributes = new ArrayList<Boolean>();
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
						boolean isNumeric = NumberUtils.isNumber(elem);
		    			isNumericAttributes.add(isNumeric);
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
		transactionList.setAttributesName(attributesName);
		transactionList.setIsNumericAttributes(isNumericAttributes);
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
		int j = tl.getAttributesName().indexOf(s);
		if ((j != -1) && (tl.getIsNumericAttributes().get(j) == true)){
			float minval = 999999;
			float maxval = -1;
			
			for(Transaction t : tl){
				float v = -1;
				String aa = t.getValues().get(j).toString();
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
					String bb = t.getValues().get(j).toString();
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
						t.getValues().remove(j);
						res.add(t);
					}
				}
			}
			boolean n = tl.getIsNumericAttributes().get(j);
			tl.getIsNumericAttributes().remove(j);
			tl.getIsNumericAttributes().add(false);
			tl.getAttributesName().remove(s);
			tl.getAttributesName().add(s);
		}
		res.setAttributesName(tl.getAttributesName());
		res.setIsNumericAttributes(tl.getIsNumericAttributes());
		return res;
	}
	
	public void allNumToBool(TransactionList tl, int[] nbNewAttrPerAttr){
		ArrayList<String> tmp = new ArrayList<String>();
		for(int i = 0; i < tl.getIsNumericAttributes().size(); i++){
			if(tl.getIsNumericAttributes().get(i))
				tmp.add(tl.getAttributesName().get(i));
		}
		for(int j = 0; j < tmp.size(); j++){
			tl = numericAttrToBool(tl, tmp.get(j), nbNewAttrPerAttr[j]);
		}
	}
	
}
