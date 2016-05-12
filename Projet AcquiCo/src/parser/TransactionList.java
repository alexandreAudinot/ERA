package parser;

import java.util.ArrayList;

public class TransactionList extends ArrayList<Transaction> {
	private static final long serialVersionUID = -8878030961190729009L;

	private ArrayList<String> attributesName;
	private ArrayList<Boolean> isNumericAttributes;
	private int nbNumericAttribute;
	private ArrayList<Integer> numberOfRange = new ArrayList<>();

	public ArrayList<String> getAttributesName() {
		return attributesName;
	}

	public void setAttributesName(ArrayList<String> attributesName) {
		this.attributesName = attributesName;
	}

	public ArrayList<Boolean> getIsNumericAttributes() {
		return isNumericAttributes;
	}

	public void setIsNumericAttributes(ArrayList<Boolean> isNumericAttributes) {
		this.isNumericAttributes = isNumericAttributes;

		nbNumericAttribute = 0;
		for(Boolean b : isNumericAttributes)
			if(b) nbNumericAttribute++;
	}

	public ArrayList<Integer> getNumberOfRange() {
		return numberOfRange;
	}

	public void setNumberOfRange(ArrayList<Integer> numberOfRange) {
		this.numberOfRange = numberOfRange;
	}

	public int getNbNumericAttribute() {
		return nbNumericAttribute;
	}
}
