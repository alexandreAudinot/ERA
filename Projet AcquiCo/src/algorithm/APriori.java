package algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import parser.Transaction;
import parser.TransactionList;

public class APriori {
	private TransactionList allTransactions;

	private final Set<String> allItems;

	private final double MINSUP;
	
	private final double MINCONF;

	/**
	 * 
	 * @param minsup ex : 2/9, not 2
	 * @param transactions
	 */
	public APriori(double minsup, double minconf, TransactionList transactions) {
		this.MINSUP = minsup;
		this.MINCONF = minconf;
		this.allTransactions = transactions;

		this.allItems = this.deduceAllKnownItems();

	}

	private Set<String> deduceAllKnownItems() {
		Set<String> items = new HashSet<String>();

		for (Transaction t : this.allTransactions) {
			for (String i : t.getSet()) {
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

		for (Transaction t : this.allTransactions) {
			if (t.getSet().containsAll(itemset)) {
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
	
	

	/**
	 * Second step of the a priori algorithm
	 * @return 
	 */
	public Set<Rule> generateRules(){
		Set<Set<String>> frequents = getFrequentItemsets();
		Set<Rule> ruleList = new HashSet<Rule>();

		//For each kitemset frequent
		for (Set<String> f : frequents) {
			ArrayList<String> kitemset = new ArrayList<>(f.size());
			kitemset.addAll(f);

			//If this is a frequent k-itemset with k > 1 (at least 2 item)
			if (f.size() >= 2){
				int m = 0;

				//for each element, isolate it and create the rule
				while (m<f.size()){
					String to = kitemset.get(m);
					ArrayList<String> from = ((ArrayList<String>) kitemset.clone());
					from.remove(m);

					double conf = getConf(from, to);
					double lift = getLift(from, to);
					if(conf >= MINCONF)
						ruleList.add(new Rule(from, to, conf, lift));

					m++;
				}
			}
		}
		
		return ruleList;
	}

	private double getConf(Collection<String> from, String to) {
		double nbFrom = 0, nbAll = 0;

		ArrayList<String> all = new ArrayList<>(from.size() + 1);
		all.addAll(from);
		all.add(to);

		for (Transaction t : this.allTransactions) {
			if(t.getSet().containsAll(from))
				nbFrom++;

			if(t.getSet().containsAll(all))
				nbAll++;
		}

		return nbAll / nbFrom;
	}

	private double getLift(Collection<String> from, String to) {
		double nbFrom = 0, nbAll = 0, nbTo = 0;

		ArrayList<String> all = new ArrayList<>(from.size() + 1);
		all.addAll(from);
		all.add(to);

		for (Transaction t : this.allTransactions) {
			if(t.getSet().containsAll(from))
				nbFrom++;

			if(t.getSet().contains(to))
				nbTo++;

			if(t.getSet().containsAll(all))
				nbAll++;
		}

		return allTransactions.size() * nbAll / (nbFrom * nbTo);
	}
}
