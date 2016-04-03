package algorithm;

import java.util.LinkedList;
import java.util.List;

public class Itemset {
	List<String> items;

	public Itemset() {
		items = new LinkedList<String>();
	}
	
	public void addItem(String item) {
		this.items.add(item);
	}
	
	
	public List<String> getItems() {
		return items;
	}

}
