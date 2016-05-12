package ui.steps.config;

import parser.TransactionList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class ConfigStepPanel extends JPanel {
    private OnStartAlgo onStartAlgo;
    private JButton btnGo;
    private JFormattedTextField txtMinsup;
    private JFormattedTextField txtMinconf;
    private ArrayList<JFormattedTextField> numberOfRangeBind;
    JPanel panel;

    public ConfigStepPanel() {
        numberOfRangeBind = new ArrayList<>();

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JPanel minSupPanel = new JPanel();
        minSupPanel.setLayout(new FlowLayout());
        minSupPanel.add(new JLabel("Min support: "));

        txtMinsup = new JFormattedTextField(new Double(0.3));
        txtMinsup.setColumns(5);
        minSupPanel.add(txtMinsup);

        panel.add(minSupPanel);


        JPanel minConfPanel = new JPanel();
        minConfPanel.setLayout(new FlowLayout());
        minConfPanel.add(new JLabel("Min confidence: "));

        txtMinconf = new JFormattedTextField(new Double(0.2));
        txtMinconf.setColumns(5);
        minConfPanel.add(txtMinconf);

        panel.add(minConfPanel);

        btnGo = new JButton("Go");
        panel.add(btnGo);

        setLayout(new BorderLayout());
        add(new JScrollPane(panel), BorderLayout.CENTER);
        
        bindEvents();
    }

    private void bindEvents() {
        btnGo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(onStartAlgo != null) {
                    int[] numberOfRangePerAttribute = new int[numberOfRangeBind.size()];
                    for(int i = 0; i<numberOfRangeBind.size(); i++)
                        numberOfRangePerAttribute[i] = (int)numberOfRangeBind.get(i).getValue();

                    onStartAlgo.startAlgo((Double) txtMinsup.getValue(), (Double) txtMinconf.getValue(), numberOfRangePerAttribute);
                }
            }
        });
    }

    public void setTransactionList(TransactionList tl) {
        panel.setLayout(new GridLayout(4 + tl.getNbNumericAttribute(), 1));
        panel.remove(btnGo);
        for(int i = 0; i<tl.getIsNumericAttributes().size(); i++) {
            if(tl.getIsNumericAttributes().get(i)) {
                JPanel numInfo = new JPanel(new FlowLayout());
                numInfo.add(new JLabel("Number of range for [" + tl.getAttributesName().get(i) + "]: "));
                Integer in = 1;
                JFormattedTextField txt = new JFormattedTextField(in);
                numInfo.add(txt);
                txt.setColumns(3);
                numberOfRangeBind.add(txt);
                panel.add(numInfo);
            }
        }
        panel.add(btnGo);
    }

    public void setOnStartAlgo(OnStartAlgo onStartAlgo) {
        this.onStartAlgo = onStartAlgo;
    }
}
