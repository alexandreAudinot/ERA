package algorithm;

import java.util.Collection;

public class Rule {
	private Collection<String> premise;
	private String consequence;
	private double conf;

	public Rule(Collection<String> p, String c, double conf){
		this.premise=p;
		this.consequence=c;
		this.conf = (double)Math.round(conf * 1000) / 1000;
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
}
