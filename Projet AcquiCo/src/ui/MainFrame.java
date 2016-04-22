package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import parser.FileParser;
import parser.TransactionList;
import ui.steps.config.ConfigStepPanel;
import ui.steps.load.LoadStepPanel;
import ui.steps.load.onFileChosen;
import ui.steps.result.ResultStepPanel;

import javax.swing.*;

public class MainFrame extends JFrame {

    //steps
    private LoadStepPanel loadStepPanel;
    private ConfigStepPanel configStepPanel;
    private ResultStepPanel resultStepPanel;

    //parser
    private FileParser parser = new FileParser();

    private JTabbedPane tabbedPane;
    private JMenuBar menuBar;
    private JMenuItem quit;
    private JMenuItem aPropos;

    public void start() {
        setLocationRelativeTo(null);
        setSize(500, 350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("INSA - Extracteur de règles d'association");

        initTabbedPane();
        initMenu();

        setVisible(true);
    }

    private void initTabbedPane() {
        tabbedPane = new JTabbedPane();

        loadStepPanel = new LoadStepPanel();
        configStepPanel = new ConfigStepPanel();
        resultStepPanel = new ResultStepPanel();

        tabbedPane.addTab("Source", loadStepPanel);
        tabbedPane.addTab("Configuration", configStepPanel);
        tabbedPane.addTab("Résultats", resultStepPanel);

        allowSteps(true, false, false);
        add(tabbedPane);
    }

    private void initMenu() {
        menuBar = new JMenuBar();

        //Fichier
        JMenu menuFichier = new JMenu("Fichier");
        menuBar.add(menuFichier);

        quit = new JMenuItem("Quitter");
        menuFichier.add(quit);

        //?
        JMenu menuLast = new JMenu("?");
        menuBar.add(menuLast);

        aPropos = new JMenuItem("A propos");
        menuLast.add(aPropos);

        setJMenuBar(menuBar);
        bindEvents();
    }

    private void bindEvents() {
        //Menu
        quit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.dispose();
			}
		});
        aPropos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainFrame.this, "...", "A propos", JOptionPane.INFORMATION_MESSAGE);
			}
		});

        //Load Panel
        loadStepPanel.setOnFileChosenListener(new onFileChosen() {
			@Override
			public void fileChosen(File selectedFile) {
				TransactionList transactions = parser.readFile(selectedFile.getAbsolutePath());
	            JOptionPane.showMessageDialog(MainFrame.this, "TransactionList.size:" + transactions.size());
	            allowSteps(true, true, false);
			}
		});

        //Config Panel

        //Result Panel
    }


    public void allowSteps(boolean loadStep, boolean configStep, boolean resulStep) {
        tabbedPane.setEnabledAt(0, loadStep);
        tabbedPane.setEnabledAt(1, configStep);
        tabbedPane.setEnabledAt(2, resulStep);
    }

    public void allowSteps(boolean loadStep, boolean configStep, boolean resulStep, int showStepIndex) {
        allowSteps(loadStep, configStep, resulStep);
        tabbedPane.setSelectedIndex(showStepIndex);
    }
}
