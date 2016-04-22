package parser;

import java.util.ArrayList;

public class TransactionList extends ArrayList<Transaction> {
    private String titles;

    public void setTitles(String titles) {
        this.titles = titles;
    }
}
