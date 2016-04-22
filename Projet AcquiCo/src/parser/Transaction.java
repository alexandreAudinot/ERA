package parser;

import java.util.HashSet;
import java.util.Set;

public class Transaction {
    private Set<String> set;

    public Transaction() {
        this.set = new HashSet<String>();
    }

    public void add(String s) {
        set.add(s);
    }

    public Set<String> getSet() {
        return set;
    }
}
