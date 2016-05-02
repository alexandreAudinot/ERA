package algorithm;

import java.util.Collection;

public class Rule {
	private Collection<String> premise;
	private String consequence;
	
	public Rule(Collection<String> p, String c){
	this.premise=p;
	this.consequence=c;
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


}
