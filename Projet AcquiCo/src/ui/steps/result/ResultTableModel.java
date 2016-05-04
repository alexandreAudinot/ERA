package ui.steps.result;

import algorithm.Rule;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.ArrayList;

public class ResultTableModel extends AbstractTableModel {
    private ArrayList<algorithm.Rule> rules;
    public static String[] columnName = {"Confidence", "Premise", "Consequence"};
    public static final String SORT_CONF = "Confidence";
    public static final String SORT_LIFT = "Lift";
    private String currentSort = SORT_CONF;

    public ResultTableModel(ArrayList<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public String getColumnName(int column) {
        return columnName[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public int getRowCount() {
        return rules.size();
    }

    @Override
    public int getColumnCount() {
        return columnName.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0:
                return currentSort.equals(SORT_CONF) ? rules.get(rowIndex).getConf() : rules.get(rowIndex).getLift();
            case 1:
                return rules.get(rowIndex).getPremise();
            case 2:
            default:
                return rules.get(rowIndex).getConsequence();
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (rules == null || rules.size() == 0)
            return Object.class;

        return getValueAt(0, columnIndex).getClass();
    }

    public void sortBy(String sortType) {
        currentSort = sortType;
        if(sortType.equals(SORT_CONF)) {
            columnName[0] = SORT_CONF;
            Rule.sortByConfidence(rules);
        }
        else {
            columnName[0] = SORT_LIFT;
            Rule.sortByLift(rules);
        }
        fireTableStructureChanged();
        fireTableDataChanged();
    }
}
