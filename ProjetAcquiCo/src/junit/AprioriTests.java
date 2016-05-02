package junit;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import parser.Transaction;
import parser.TransactionList;
import algorithm.APriori;

public class AprioriTests {
	
	private APriori apriori;
	
	private final double MINSUP = 2./9;
	private final double MINCONF = 2./9;
	
	@Before
	public void setUp() throws Exception {
		apriori = new APriori(MINSUP,  MINCONF, getExampleTransactions());
	}

	@Test
	public void testGetItems() {
		Set<String> itemsResult = apriori.getAllItems(); 
		Set<String> itemsExpected = new HashSet<String>();
		
		for (int i = 1 ; i <= 5 ; i++) {
			itemsExpected.add("i" + i);
		}
		
		assertTrue(itemsResult.containsAll(itemsExpected));
		assertTrue(itemsExpected.containsAll(itemsResult));
	}
	
	@Test
	public void testStep1() {
		
		Set<Set<String>> frequentItemsets = apriori.getFrequentItemsets();
		
		System.out.println("All frequent sets :");
		for (Set<String> set : frequentItemsets) {
			System.out.println(set);
		}
		
		assertTrue(frequentItemsets.size() == 13);
	}
	
	private TransactionList getExampleTransactions() {
		TransactionList res = new TransactionList();
		
		Transaction i;
		i = new Transaction();
		i.add("i1");
		i.add("i2");
		i.add("i5");
		res.add(i);
		
		i = new Transaction();
		i.add("i2");
		i.add("i4");
		res.add(i);
		
		i = new Transaction();
		i.add("i2");
		i.add("i3");
		res.add(i);
		
		i = new Transaction();
		i.add("i1");
		i.add("i2");
		i.add("i4");
		res.add(i);
		
		i = new Transaction();
		i.add("i1");
		i.add("i3");
		res.add(i);
		
		i = new Transaction();
		i.add("i2");
		i.add("i3");
		res.add(i);
		
		i = new Transaction();
		i.add("i1");
		i.add("i3");
		res.add(i);
		
		i = new Transaction();
		i.add("i1");
		i.add("i2");
		i.add("i3");
		i.add("i5");
		res.add(i);
		
		i = new Transaction();
		i.add("i1");
		i.add("i2");
		i.add("i3");
		res.add(i);
		
		
		return res;
	}

}
