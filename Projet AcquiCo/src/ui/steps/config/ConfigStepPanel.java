package ui.steps.config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigStepPanel extends JPanel {
    private OnStartAlgo onStartAlgo;
    private JButton btnGo;
    private JFormattedTextField txtMinsup;
    private JFormattedTextField txtMinconf;

    public ConfigStepPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JPanel minSupPanel = new JPanel();
        minSupPanel.setLayout(new FlowLayout());
        minSupPanel.add(new JLabel("Min support: "));

        txtMinsup = new JFormattedTextField(new Double(0.3));
        minSupPanel.add(txtMinsup);

        panel.add(minSupPanel);


        JPanel minConfPanel = new JPanel();
        minConfPanel.setLayout(new FlowLayout());
        minConfPanel.add(new JLabel("Min confidence: "));

        txtMinconf = new JFormattedTextField(new Double(0.2));
        minConfPanel.add(txtMinconf);

        panel.add(minConfPanel);

        btnGo = new JButton("Go");
        panel.add(btnGo);

        add(panel, BorderLayout.CENTER);
        
        bindEvents();
    }

    private void bindEvents() {
        btnGo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(onStartAlgo != null)
                    onStartAlgo.startAlgo((Double) txtMinsup.getValue(), (Double) txtMinconf.getValue());
            }
        });
    }

    public void setOnStartAlgo(OnStartAlgo onStartAlgo) {
        this.onStartAlgo = onStartAlgo;
    }
}
