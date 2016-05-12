package algorithm;

import java.beans.FeatureDescriptor;
import java.lang.annotation.ElementType;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import parser.Transaction;
import parser.TransactionList;

public class APriori {
	public enum EItemsetType {
		Frequent,
		Maximal,
		Closed
	};
	
	private TransactionList allTransactions;

	private final Set<String> allItems;

	private final double MINSUP;
	
	private final double MINCONF;
	
	private Set<Set<String>> frequents;

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
			for (String i : t.getValues()) {
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
			if (t.getValues().containsAll(itemset)) {
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
		EItemsetType type = allTransactions.getItemsetType();
		
		frequents = getFrequentItemsets();
		if(type == EItemsetType.Closed)	
			frequents = (Set<Set<String>>) getClosedFrequetItemsets();
		else if (type == EItemsetType.Maximal)
			frequents = (Set<Set<String>>) getMaximalFrequetItemsets();
		
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

	public Object getClosedFrequetItemsets(){
		ArrayList<ArrayList<String>> freq = new ArrayList<>();
		for(Set<String> s : frequents){
			freq.add(new ArrayList<String>(s));
		}		
		ArrayList<ArrayList<String>> res = new ArrayList<>();
		for(int i = 0; i < freq.size(); i++){
			for(int j = 0; j < freq.size(); j++){
				if(i!=j){
					if(freq.get(j).containsAll(freq.get(i))){
						if(support(freq.get(i)) > support(freq.get(j))){
							if(!res.contains(freq.get(i)))
							res.add(freq.get(i));
						}else{
							res.remove(freq.get(i));
						}
					}
				}
			}
		}
		
		HashSet<HashSet<String>> ret = new HashSet<HashSet<String>>();
		for(ArrayList<String> tmp : res) {
			ret.add(new HashSet<String>(tmp));
		}
		
		return ret;
	}
	
	public Object getMaximalFrequetItemsets(){
		ArrayList<ArrayList<String>> freq = new ArrayList<>();
		for(Set<String> s : frequents){
			freq.add(new ArrayList<String>(s));
		}		
		ArrayList<ArrayList<String>> res = new ArrayList<>();
		for(int i = 0; i < freq.size(); i++){
			for(int j = 0; j < freq.size(); j++){
				if(i!=j){
					if((freq.get(j).size() == freq.get(i).size()+1) && (!freq.get(j).containsAll(freq.get(i)))){
						if(!res.contains(freq.get(i)))
							res.add(freq.get(i));		
					}
				}
			}
		}
		
		HashSet<HashSet<String>> ret = new HashSet<HashSet<String>>();
		for(ArrayList<String> tmp : res) {
			ret.add(new HashSet<String>(tmp));
		}
		
		return ret;
	}
	
	private double getConf(Collection<String> from, String to) {
		double nbFrom = 0, nbAll = 0;

		ArrayList<String> all = new ArrayList<>(from.size() + 1);
		all.addAll(from);
		all.add(to);

		for (Transaction t : this.allTransactions) {
			if(t.getValues().containsAll(from))
				nbFrom++;

			if(t.getValues().containsAll(all))
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
			if(t.getValues().containsAll(from))
				nbFrom++;

			if(t.getValues().contains(to))
				nbTo++;

			if(t.getValues().containsAll(all))
				nbAll++;
		}

		return allTransactions.size() * nbAll / (nbFrom * nbTo);
	}
}
