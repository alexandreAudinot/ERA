package ui.steps.config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigStepPanel extends JPanel {
    private OnStartAlgo onStartAlgo;
    private JButton btnGo;
    private JFormattedTextField txtMinsup;

    public ConfigStepPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        JPanel minsunPanel = new JPanel();
        minsunPanel.setLayout(new FlowLayout());
        minsunPanel.add(new JLabel("Minsup: "));

        txtMinsup = new JFormattedTextField(new Double(0.3));
        minsunPanel.add(txtMinsup);

        panel.add(minsunPanel);

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
                    onStartAlgo.startAlgo((Double) txtMinsup.getValue());
            }
        });
    }

    public void setOnStartAlgo(OnStartAlgo onStartAlgo) {
        this.onStartAlgo = onStartAlgo;
    }
}
