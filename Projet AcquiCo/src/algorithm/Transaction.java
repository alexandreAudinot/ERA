package algorithm;

import java.util.List;

public class Transaction {
	List<String> items;

	public Transaction() {
		
	}
	
	public void addItem(String item) {
		this.items.add(item);
	}
	
	
	public List<String> getItems() {
		return items;
	}

}
