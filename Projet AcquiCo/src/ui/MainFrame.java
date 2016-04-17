package ui;

import ui.steps.config.ConfigStepPanel;
import ui.steps.load.LoadStepPanel;
import ui.steps.result.ResultStepPanel;

import javax.swing.*;

public class MainFrame extends JFrame {

    public static void main(String[] args) {
        new MainFrame().start();
    }

    //steps
    private LoadStepPanel loadStepPanel;
    private ConfigStepPanel configStepPanel;
    private ResultStepPanel resultStepPanel;

    private JTabbedPane tabbedPane;
    private JMenuBar menuBar;
    private JMenuItem quit;
    private JMenuItem aPropos;

    private void start() {
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
        quit.addActionListener(e -> MainFrame.this.dispose());
        aPropos.addActionListener(e ->  JOptionPane.showMessageDialog(MainFrame.this, "...", "A propos", JOptionPane.INFORMATION_MESSAGE));

        //Load Panel
        loadStepPanel.setOnFileChosenListener(selectedFile ->  {
            JOptionPane.showMessageDialog(MainFrame.this, selectedFile.getAbsolutePath());
            allowSteps(true, true, false);
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
