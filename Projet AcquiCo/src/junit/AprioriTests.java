package junit;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import algorithm.APriori;
import algorithm.Itemset;

public class AprioriTests {
	
	private APriori apriori;
	
	private final double MINSUP = 2/9;
	
	

	@Before
	public void setUp() throws Exception {
		apriori = new APriori(MINSUP, getExampleTransactions());
	}

	@Test
	public void testGetItems() {
		List<String> itemsResult = apriori.getAllItems(); 
		List<String> itemsExpected = new LinkedList<String>();
		
		for (int i = 1 ; i <= 5 ; i++) {
			itemsExpected.add("i" + i);
		}
		
		assertTrue(itemsResult.containsAll(itemsExpected));
		assertTrue(itemsExpected.containsAll(itemsResult));
	}
	
	private List<Itemset> getExampleTransactions() {
		List<Itemset> res = new LinkedList<Itemset>();
		
		Itemset i;
		
		i = new Itemset();
		i.addItem("i1");
		i.addItem("i2");
		i.addItem("i5");
		res.add(i);
		

		i = new Itemset();
		i.addItem("i2");
		i.addItem("i4");
		res.add(i);
		

		i = new Itemset();
		i.addItem("i2");
		i.addItem("i3");
		res.add(i);
		

		i = new Itemset();
		i.addItem("i1");
		i.addItem("i2");
		i.addItem("i4");
		res.add(i);
		

		i = new Itemset();
		i.addItem("i1");
		i.addItem("i3");
		res.add(i);
		

		i = new Itemset();
		i.addItem("i2");
		i.addItem("i3");
		res.add(i);
		

		i = new Itemset();
		i.addItem("i1");
		i.addItem("i3");
		res.add(i);
		

		i = new Itemset();
		i.addItem("i1");
		i.addItem("i2");
		i.addItem("i3");
		i.addItem("i5");
		res.add(i);
		

		i = new Itemset();
		i.addItem("i1");
		i.addItem("i2");
		i.addItem("i3");
		res.add(i);
		
		
		return res;
	}

}
