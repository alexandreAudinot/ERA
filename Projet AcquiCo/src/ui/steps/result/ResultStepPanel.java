package ui.steps.result;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ResultStepPanel extends JPanel {
    private static final String[] sortMethods = {"Confidence", "Lift"};

    private JComboBox sortBy;
    private JTable resultTable;
    private ResultTableModel tableModel;

    public ResultStepPanel() {
        setLayout(new BorderLayout());

        JPanel settingsPanel = new JPanel(new FlowLayout());
        settingsPanel.add(new JLabel("Sort by: "));
        sortBy = new JComboBox<>(sortMethods);
        settingsPanel.add(sortBy);

        add(settingsPanel, BorderLayout.NORTH);

        resultTable = new JTable();
        resultTable.setAutoCreateRowSorter(true);
        add(new JScrollPane(resultTable), BorderLayout.CENTER);

        bindEvents();
    }

    private void bindEvents() {
        sortBy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.sortBy((String)sortBy.getSelectedItem());
                resizeColumnWidth(resultTable);
            }
        });
    }

    public void setModel(ArrayList<algorithm.Rule> rules) {
        tableModel = new ResultTableModel(rules);
        resultTable.setModel(tableModel);
        resizeColumnWidth(resultTable);
    }

    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 50;
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
}
