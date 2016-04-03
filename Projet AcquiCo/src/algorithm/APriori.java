package algorithm;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class APriori {
	private List<Set<String>> allTransactions;

	private final Set<String> allItems;

	private final double MINSUP;

	/**
	 * 
	 * @param minsup ex : 2/9, not 2
	 * @param transactions
	 */
	public APriori(double minsup, List<Set<String>> transactions) {
		this.MINSUP = minsup;
		this.allTransactions = transactions;

		this.allItems = this.deduceAllKnownItems();

	}

	private Set<String> deduceAllKnownItems() {
		Set<String> items = new HashSet<String>();

		for (Set<String> t : this.allTransactions) {
			for (String i : t) {
				if (! items.contains(i)) {
					items.add(i);
				}
			}
		}

		return items;
	}


	/**
	 * First step of the a priori algorithm (iterative algorithm)
	 * @return
	 */
	public Set<Set<String>> getFrequentItemsets() {
		Set<Set<String>> frequents = new HashSet<Set<String>>();

		Set<Set<String>> Lk = new HashSet<Set<String>>(); // Frequent k-itemsets

		for (String item : allItems) { // Generate frequent 1-itemsets
			Set<String> itemset = new HashSet<String>();
			itemset.add(item);
			if (isFrequent(itemset)) {
				Lk.add(itemset);
			}
		}
		frequents.addAll(Lk);

		while (! Lk.isEmpty()) {
			Set<Set<String>> Ck = new HashSet<Set<String>>(); // Candidate for frequent k-itemsets

			for (Set<String> i1 : Lk) {
				for (Set<String> i2 : Lk) {
					if (! i1.equals(i2)) {
						Set<String> union = new HashSet<String>(i1);
						union.addAll(i2);
						Ck.add(union);
					}
				}
			}
			Lk = new HashSet<Set<String>>();
			for (Set<String> i : Ck) {
				if (isFrequent(i)) {
					Lk.add(i);
				}
			}
			frequents.addAll(Lk);
			// "k++"
		}

		return frequents;
	}

	private boolean isFrequent(Collection<String> itemset) {
		return support(itemset) >= MINSUP;//TODO ask if should be > or >=
	}

	/**
	 * Returns how many times all of the provided items appear together in the transactions.
	 */
	public double support(Collection<String> itemset)  {
		int res = 0;

		for (Set<String> t : this.allTransactions) {
			if (t.containsAll(itemset)) {
				res++;
			}
		}
		return (res*1.0)/allTransactions.size();
	}


	public double getMinsup() {
		return MINSUP;
	}

	public Set<String> getAllItems() {
		return allItems;
	}
}
