package JAM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

/**
 * interfaccia grafica per agenti di tipo JAMAgent. Fornisce pulsanti per
 * línizializzazione, lo start e la distruzione di un agente. Implementa
 * Observer per tener traccia delle attivita`degli agenti
 *
 * @author Andrea Bragagnolo
 */
public class JAMAgentMonitor extends JFrame implements Observer {

    private JAMAgent agent;
    private JButton init;
    private JButton start;
    private JButton destroy;
    private JButton exit;
    private JTextArea logArea;
    private JScrollPane scroll;
    private JPanel panelNorth;
    private JPanel panelCenter;
    private JPanel panelSouth;

    /**
     * il costruttore oltre a definire il layout dell'interfaccia assegna le
     * operazioni da eseguire ai vari bottoni
     *
     * @param jamAgent JAMAgent agente a cui appartiene l'interfaccia grafica
     * @param nome String nome dell'agente a cui appartiene l'interfaccia
     * @throws Exception
     */
    public JAMAgentMonitor(JAMAgent jamAgent, String nome) throws Exception {
        agent = jamAgent;
        agent.addObserver(this);
        init = new JButton("Init");
        start = new JButton("Start");
        destroy = new JButton("Destroy");
        exit = new JButton("Exit");
        logArea = new JTextArea(10, 30);
        scroll = new JScrollPane();
        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();

        //ActionListener
        ButtonsAction action = new ButtonsAction();
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    destroyJAMAgent();
                } catch (Exception ex) {
                    System.out.println("JAMMonitor exit:" + ex);
                }
                System.exit(0);
            }
        });
        init.addActionListener(action);
        start.addActionListener(action);
        destroy.addActionListener(action);

        //Costruttore
        scroll.getViewport().add(logArea);
        panelNorth.add(init);
        panelNorth.add(start);
        panelNorth.add(destroy);
        add(panelNorth, BorderLayout.NORTH);
        panelCenter.add(scroll);
        add(panelCenter, BorderLayout.CENTER);
        panelSouth.add(exit);
        add(panelSouth, BorderLayout.SOUTH);

        this.setTitle("JAMAgent Monitor " + nome);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * metodo utilizzato per caricare la MessageBox del JAMAgent
     *
     * @throws java.rmi.NotBoundException
     * @throws java.net.MalformedURLException
     * @throws java.rmi.RemoteException
     */
    public void initJAMAgent() throws NotBoundException, MalformedURLException, RemoteException {
        agent.init();
    }

    /**
     * metodo utilizzato per avviare il comportamento del JAMAgent
     */
    public void startJAMAgent() {
        agent.start();
    }

    /**
     * metodo utilizzato per rimuovere la MessageBox del JAMAgent
     *
     * @throws java.rmi.RemoteException
     */
    public void destroyJAMAgent() throws RemoteException {
        agent.destroy();
    }

    /**
     * metodo update di Observer che stampa sulla log area l'azione compiuta
     * dall'agente osservato
     *
     * @param ob Observable riferimento all'oggetto che ha richiamato il metodo,
     * di un oggetto Observable, notifyObserver().
     * @param extra_arg Object argomento passato da notifyObserver che specifica
     * l'azione avvenuta
     */
    public void update(Observable ob, Object extra_arg) {
        logArea.append((String) extra_arg + "\n");
    }

    /**
     * metodoto utilizzato per scrivere dei messaggi sulla log area
     *
     * @param string String messaggio da aggiungere alla log area
     */
    public void append(String string) {
        this.logArea.append(string);
    }

    /**
     *
     * la classe definisce le azioni eseguite dai diversi bottoni
     */
    private class ButtonsAction implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            try {
                if (event.getActionCommand().equals("Init")) {
                    initJAMAgent();
                }
                if (event.getActionCommand().equals("Start")) {
                    startJAMAgent();
                }
                if (event.getActionCommand().equals("Destroy")) {
                    destroyJAMAgent();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelCenter, ex, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
