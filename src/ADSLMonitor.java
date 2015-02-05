package JAM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Interfaccia grafica per l'ADSL che fornisce un'area di log e i pulsanti per
 * eseguire il rebind del registro, lo start e l'unbind
 *
 * @author Andrea Bragagnolo
 */
public class ADSLMonitor extends JFrame {

    private final JButton registry;
    private final JButton start;
    private final JButton shutdown;
    private final JButton exit;
    private final JLabel label;
    private final JTextField portField;
    private final JTextArea logArea;
    private final JScrollPane scroll;
    private final JPanel panelNorth;
    private final JPanel panelCenter;
    private final JPanel panelSouth;

    /**
     * costruttore del monitor che oltre a definire il layout della finestra
     * agginge le azioni ai bottoni
     */
    public ADSLMonitor() {
        registry = new JButton("Start registry");
        start = new JButton("Start up");
        shutdown = new JButton("Shutdown");
        exit = new JButton("Exit");
        label = new JLabel("Port:");
        portField = new JTextField(12);
        logArea = new JTextArea(10, 30);
        scroll = new JScrollPane();
        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();

        //ActionListener
        ButtonsAction action = new ButtonsAction();
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        registry.addActionListener(action);
        start.addActionListener(action);
        shutdown.addActionListener(action);

        //Costruttore
        scroll.getViewport().add(logArea);
        panelNorth.add(label);
        panelNorth.add(portField);
        panelNorth.add(registry);
        add(panelNorth, BorderLayout.NORTH);
        panelCenter.add(scroll);
        add(panelCenter, BorderLayout.CENTER);
        panelSouth.add(start);
        panelSouth.add(shutdown);
        panelSouth.add(exit);
        add(panelSouth, BorderLayout.SOUTH);

        this.setTitle("ADSL Monitor");
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * la classe definisce le azioni eseguite dai diversi bottoni
     */
    private class ButtonsAction implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            if ("".equals(portField.getText())) {
                JOptionPane.showMessageDialog(panelCenter, "E` necessario inserire una porta", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    int port = Integer.parseInt(portField.getText());
                    ADSLImpl adsl = new ADSLImpl(port);
                    if (event.getActionCommand().equals("Start registry")) {
                        adsl.startRMIRegistry();
                        logArea.append("Started RMI Registry su porta: " + port + "\n");
                    }
                    if (event.getActionCommand().equals("Start up")) {
                        adsl.startADSL();
                        logArea.append("Piattaforma avviata (rebind)\n");
                    }
                    if (event.getActionCommand().equals("Shutdown")) {
                        adsl.stopADSL();
                        logArea.append("Piattaforma arrestata (unbind)\n");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panelCenter, ex, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
