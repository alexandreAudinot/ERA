package ui.steps.load;

import parser.Transaction;
import parser.TransactionList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoadStepPanel extends JPanel {
    private JLabel stateLabel;
    private JButton loadButton;
    private final JFileChooser fileChooser;
    private OnFileChosen onFileChosenListener;
    private OnGoToConfiguration onGoToConfiguration;
    private ArrayList<JCheckBox> keep;
    private JButton btnGo;
    private TransactionList transactions;
    private JPanel panelCenter;

    public LoadStepPanel() {
        keep = new ArrayList<>();
        fileChooser = new JFileChooser(".");
        fileChooser.setMultiSelectionEnabled(false);

        panelCenter = new JPanel();
        panelCenter.setLayout(new GridLayout(2, 1));

        stateLabel = new JLabel("Pas de fichier chargé pour le moment");
        panelCenter.add(stateLabel);

        loadButton = new JButton("Charger");
        panelCenter.add(loadButton);

        btnGo = new JButton("Go");

        setLayout(new BorderLayout());
        add(new JScrollPane(panelCenter), BorderLayout.CENTER);

        bindEvents();
    }

    private void bindEvents() {
        loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(fileChooser.showOpenDialog(LoadStepPanel.this) == JFileChooser.APPROVE_OPTION && onFileChosenListener != null) {
	                stateLabel.setText("Fichier chargé: " + fileChooser.getSelectedFile().getName());
                    transactions = onFileChosenListener.fileChosen(fileChooser.getSelectedFile());

                    //add checkbox
                    panelCenter.setLayout(new GridLayout(3 + transactions.getAttributesName().size(), 1));
                    for(String attributeName : transactions.getAttributesName()) {
                        JCheckBox cb = new JCheckBox(attributeName);
                        cb.setSelected(true);
                        keep.add(cb);
                        panelCenter.add(cb);
                    }
                    panelCenter.add(btnGo);
	            }
			}
		});

        btnGo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int diff = 0;
                for(int i = keep.size() - 1; i>=0; i--) {
                    if(!keep.get(i).isSelected()) {
                        for(Transaction t : transactions) {
                            t.getValues().remove(i);
                        }

                        transactions.getAttributesName().remove(i);
                        transactions.getIsNumericAttributes().remove(i);
                    }
                }
                onGoToConfiguration.goToConfiguration();
            }
        });
    }

    public void setOnFileChosenListener(OnFileChosen onFileChosenListener) {
        this.onFileChosenListener = onFileChosenListener;
    }

    public void setOnGoToConfiguration(OnGoToConfiguration onGoToConfiguration) {
        this.onGoToConfiguration = onGoToConfiguration;
    }
}
