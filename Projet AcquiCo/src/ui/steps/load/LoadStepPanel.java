package ui.steps.load;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadStepPanel extends JPanel {
    private JLabel stateLabel;
    private JButton loadButton;
    private final JFileChooser fileChooser;
    private onFileChosen onFileChosenListener;

    public LoadStepPanel() {
        fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);

        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new GridLayout(2, 1));

        stateLabel = new JLabel("Pas de fichier chargé pour le moment");
        panelCenter.add(stateLabel);

        loadButton = new JButton("Charger");
        panelCenter.add(loadButton);

        add(panelCenter, BorderLayout.CENTER);

        bindEvents();
    }

    private void bindEvents() {
        loadButton.addActionListener(new ActionListener() {
			 
			@Override
			public void actionPerformed(ActionEvent e) {
				if(fileChooser.showOpenDialog(LoadStepPanel.this) == JFileChooser.APPROVE_OPTION && onFileChosenListener != null) {
	                stateLabel.setText("Fichier chargé: " + fileChooser.getSelectedFile().getName());
	                onFileChosenListener.fileChosen(fileChooser.getSelectedFile());
	            }
			}
		});
    }

    public void setOnFileChosenListener(onFileChosen onFileChosenListener) {
        this.onFileChosenListener = onFileChosenListener;
    }
}
