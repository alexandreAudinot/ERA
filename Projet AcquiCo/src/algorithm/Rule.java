package algorithm;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Rule {
	private Collection<String> premise;
	private String consequence;
	private double conf;
	private double lift;

	public Rule(Collection<String> p, String c, double conf, double lift){
		this.premise = p;
		this.consequence = c;
		this.conf = (double)Math.round(conf * 1000) / 1000;
		this.lift = (double)Math.round(lift * 1000) / 1000;
	}

	public Collection<String> getPremise() {
		return premise;
	}

	public void setPremise(Collection<String> premise) {
		this.premise = premise;
	}

	public String getConsequence() {
		return consequence;
	}

	public void setConsequence(String consequence) {
		this.consequence = consequence;
	}

	public double getConf() {
		return conf;
	}

	public void setConf(double conf) {
		this.conf = conf;
	}

	public double getLift() {
		return lift;
	}

	public void setLift(double lift) {
		this.lift = lift;
	}

	@Override
	public String toString() {
		String premiseStr = "[";
		int i = 0;
		for(String s : premise) {
			premiseStr += s + (i != premise.size() - 1 ? "," : "");
			i++;
		}

		return premiseStr + "] -> " + consequence;
	}

	public static void sortByConfidence(List<Rule> rules) {
		Collections.sort(rules, new Comparator<Rule>() {
			@Override
			public int compare(Rule o1, Rule o2) {
				return (int) (o2.getConf()*1000 - o1.getConf()*1000); //TODO: improve...
			}
		});
	}

	public static void sortByLift(List<Rule> rules) {
		Collections.sort(rules, new Comparator<Rule>() {
			@Override
			public int compare(Rule o1, Rule o2) {
				return (int) (o2.getLift()*1000 - o1.getLift()*1000); //TODO: improve...
			}
		});
	}
}
