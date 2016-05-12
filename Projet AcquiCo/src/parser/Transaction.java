package parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Transaction {
    public ArrayList<String> set;

    public Transaction() {
        this.set = new ArrayList<String>();
    }

    public void add(String s) {
        set.add(s);
    }

    public ArrayList<String> getValues() {
        return set;
    }
    
    public String toString(){
    	String res = "[";
    	for(String s : set){
    		res += s + ", ";
    	}
    	res += "]";
    	return res;
    }
}
