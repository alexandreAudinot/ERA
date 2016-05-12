package parser;

import java.util.ArrayList;

import algorithm.APriori.EItemsetType;

public class TransactionList extends ArrayList<Transaction> {
	private static final long serialVersionUID = -8878030961190729009L;

	private ArrayList<String> attributesName;
	private ArrayList<Boolean> isNumericAttributes;
	private int nbNumericAttribute;
	private ArrayList<Integer> numberOfRange = new ArrayList<>();
	private EItemsetType itemsetType;

	public EItemsetType getItemsetType() {
		return itemsetType;
	}

	public void setItemsetType(EItemsetType itemsetType) {
		this.itemsetType = itemsetType;
	}

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
