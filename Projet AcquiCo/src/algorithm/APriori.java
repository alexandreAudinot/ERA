package algorithm;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class APriori {
	private List<Itemset> allTransactions;
	
	private final List<String> allItems;
	
	private final double MINSUP;
	
	public APriori(double minsup, List<Itemset> transactions) {
		this.MINSUP = minsup;
		this.allTransactions = transactions;
		
		this.allItems = this.deduceAllKnownItems();
		
	}
	
	private List<String> deduceAllKnownItems() {
		List<String> items = new LinkedList<String>();
		
		for (Itemset t : this.allTransactions) {
			for (String item : t.getItems()) {
				if (! items.contains(item)) {
					items.add(item);
				}
			}
		}
		
		return items;
	}
	
	
	/**
	 * First step of the a priori algorithm
	 * @return
	 */
	public List<Itemset> getFrequentItemsets() {
		List<Itemset> frequentItemsets = new LinkedList<Itemset>();
		
		//TODO
		
		return frequentItemsets;
	}
	
	/**
	 * Returns how many times all of the provided items appear together in the transactions.
	 */
	public int support(Collection<String> itemset)  {
		int res = 0;
		
		for (Itemset t : this.allTransactions) {
			if (t.getItems().containsAll(itemset)) {
				res++;
			}
		}
		
		return res;
	}


	public double getMinsup() {
		return MINSUP;
	}

	public List<String> getAllItems() {
		return allItems;
	}
}
