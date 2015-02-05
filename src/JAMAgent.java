package JAM;

import java.net.MalformedURLException;
import java.rmi.*;
import java.util.*;

/**
 * definisce lo stato del JAM con il proprio PersonalAgentID la propria
 * MessageBox l'ip e la porta su cui verra` eseguita la lookup e una lista di
 * JAMBehaviour
 * 
 * @author Andrea Bragagnolo
 */
public abstract class JAMAgent extends Observable {

	private MessageBox myMessageBox;
	public PersonalAgentID myID;
	private ADSL adsl;
	private String name;
	private String ip;
	private int port;
	private List<JAMBehaviour> myBehaviours;

	/**
	 * costruttore di JAMAgent con indirizzo specificato
	 * 
	 * @param agentID
	 *            PersonalAgentID riferimento all' agente
	 * @param ip
	 *            String ip sul qualce connettersi
	 * @param name
	 *            String nome dell'ADSL
	 * @param port
	 *            int porta sulla quale connettersi
	 * @throws java.rmi.RemoteException
	 */
	public JAMAgent(PersonalAgentID agentID, String ip, String name, int port)
			throws RemoteException {
		myID = agentID;
		this.ip = ip;
		this.name = name;
		this.port = port;
		myMessageBox = new MessageBox(myID);
		this.myBehaviours = new ArrayList<JAMBehaviour>();
	}

	/**
	 * costruttore con inidirizzo non specificato che prende come valore di
	 * porta 1099 e di ip 127.0.0.1
	 * 
	 * @param agentID
	 *            PersonalAgentID riferimento all' agente
	 * @throws java.rmi.RemoteException
	 */
	public JAMAgent(PersonalAgentID agentID) throws RemoteException {
		myID = agentID;
		this.ip = "127.0.0.1";
		this.port = 1099;
		myMessageBox = new MessageBox(myID);
		this.myBehaviours = new ArrayList<JAMBehaviour>();
	}

	/**
	 * effettua la lookup presso lo rmiregistry all'indirizzo ip/port
	 * dell'oggetto ADSL di nome ADSL e memorizza tale riferimento remoto in
	 * adsl, quindi, se tutto e andato bene, iscrive presso l'ADSL l'oggetto di
	 * tipo MessageBox myMessageBox.
	 * 
	 * @throws java.rmi.NotBoundException
	 * @throws java.net.MalformedURLException
	 * @throws java.rmi.RemoteException
	 */
	public void init() throws NotBoundException, MalformedURLException,
			RemoteException {
		adsl = (ADSL) Naming.lookup("rmi://" + ip + ":" + port + "/ADSL");
		adsl.insertRemoteMessageBox(myMessageBox);
		setChanged();
		notifyObservers("--->INIT<---");
	}

	/**
	 * 
	 * effettua la rimozione della casella myMessageBox dall'ADSL
	 * 
	 * @throws java.rmi.RemoteException
	 */
	public void destroy() throws RemoteException {
		adsl.removeRemoteMessageBox(myMessageBox.getOwner());
		setChanged();
		notifyObservers("--->DESTROY<---");
		for (JAMBehaviour behaviour : myBehaviours) {
			if (behaviour.getThread().isAlive()) {
				behaviour.done();
			}
		}
	}

	/**
	 * controllo se sono presenti messaggi sulla MessageBox
	 * 
	 * @return boolean true se vi sono messaggi in myMessageBox, false
	 *         altrimenti
	 * @throws Exception
	 */
	public boolean check() throws Exception {
		return myMessageBox.isThereMessage();
	}

	/**
	 * controllo se sono presenti messaggi sulla MessageBox con parametri
	 * specificati
	 * 
	 * @param sender
	 *            AgentID sender del messaggio cercato
	 * @param perf
	 *            Performative performativa del messaggio cercato
	 * @return boolean true se vi sono messaggi in myMessageBox, false
	 *         altrimenti
	 * @throws JAMBehaviourInterruptedException 
	 * @throws Exception
	 */
	public boolean check(AgentID sender, Performative perf) throws JAMBehaviourInterruptedException {
		try {
			return myMessageBox.isThereMessage(sender, perf);
		} catch (InterruptedException e) {
			throw new JAMBehaviourInterruptedException();
		}
	}

	/**
	 * richiede all'ADSL mediante il metodo getRemoteMessageBoxes gli oggetti di
	 * tipo RemoteMessageBox il cui proprietario e` specificato dal receiver
	 * del messaggio message, su tali oggetti invoca la writeMessage di message;
	 * 
	 * @param message
	 *            Message messaggio utilizzato per ricercare il proprietario ed
	 *            eseguire la writeMessage()
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void send(Message message) {
		try {
			List<RemoteMessageBox> rmtBox = adsl.getRemoteMessageBox(message
					.getReceiver());
			for (RemoteMessageBox box : rmtBox) {
				box.writeMessage(message);
				setChanged();
				notifyObservers("SEND message " + message.getPerformative()
						+ " to " + message.getReceiver());
			}
		} catch (RemoteException e) {
			System.out.println(e);
		}
	}

	/**
	 * legge della propria casella myMessageBox un messaggio mediante il metodo
	 * readMessage() con le caratteristiche specificate dai parametri
	 * 
	 * @return Message oggetto di tipo Message
	 * @throws JAMBehaviourInterruptedException
	 * @throws java.lang.Exception
	 */
	public Message receive() throws JAMBehaviourInterruptedException {
		Message msgOut;
		try {
			msgOut = myMessageBox.readMessage();
		} catch (InterruptedException e) {
			throw new JAMBehaviourInterruptedException();
		}
		setChanged();
		notifyObservers("RECEIVE message " + msgOut.getPerformative()
				+ " from " + msgOut.getSender());
		return msgOut;
	}

	/**
	 * legge della propria casella myMessageBox un messaggio mediante il metodo
	 * readMessage() con le caratteristiche specificate dai parametri
	 * 
	 * @param perf
	 *            Performative performativa del messaggio
	 * @return Message oggetto di tipo Message
	 * @throws Exception
	 */
	public Message receive(Performative perf)
			throws JAMBehaviourInterruptedException {
		Message msgOut;
		try {
			msgOut = myMessageBox.readMessage(perf);
		} catch (InterruptedException e) {
			throw new JAMBehaviourInterruptedException();
		}
		setChanged();
		notifyObservers("RECEIVE message " + msgOut.getPerformative()
				+ " from " + msgOut.getSender());
		return msgOut;
	}

	/**
	 * legge della propria casella myMessageBox un messaggio mediante il metodo
	 * readMessage() con le caratteristiche specificate dai parametri
	 * 
	 * @param sender
	 *            AgentID sender del messaggio
	 * @return Message oggetto di tipo Message
	 * @throws Exception
	 */
	public Message receive(AgentID sender)
			throws JAMBehaviourInterruptedException {
		Message msgOut;
		try {
			msgOut = myMessageBox.readMessage(sender);
		} catch (InterruptedException e) {
			throw new JAMBehaviourInterruptedException();
		}
		setChanged();
		notifyObservers("RECEIVE message " + msgOut.getPerformative()
				+ " from " + msgOut.getSender());
		return msgOut;
	}

	/**
	 * legge della propria casella myMessageBox un messaggio mediante il metodo
	 * readMessage() con le caratteristiche specificate dai parametri
	 * 
	 * @param sender
	 *            AgentID sender del messaggio
	 * @param perf
	 *            Performative performativa del messaggio
	 * @return Message oggetto di tipo Message
	 * @throws Exception
	 */
	public Message receive(AgentID sender, Performative perf)
			throws JAMBehaviourInterruptedException {
		Message msgOut;
		try {
			msgOut = myMessageBox.readMessage(sender, perf);
		} catch (InterruptedException e) {
			throw new JAMBehaviourInterruptedException();
		}
		setChanged();
		notifyObservers("RECEIVE message " + msgOut.getPerformative()
				+ " from " + msgOut.getSender());
		return msgOut;
	}

	/**
	 * aggiunge un oggetto di tipo JAMBehaviour alla lista myBehaviour
	 * 
	 * @param behaviour
	 *            JAMBehaviour behaviour da aggiungere alla lista
	 * @throws Exception
	 */
	public void addBehaviour(JAMBehaviour behaviour) throws Exception {
		myBehaviours.add(behaviour);
	}

	/**
	 * crea un oggetto di tipo thread per ogni comportamento di tipo
	 * JAMBehaviour non gia in esecuzione presente in myBehaviours e invoca su
	 * di esso il metodo start
	 */
	public void start() {
		setChanged();
		notifyObservers("--->START<---");
		for (JAMBehaviour behaviour : myBehaviours) {
			if (behaviour.getThread() == null) {
				Thread thread = new Thread(behaviour);
				behaviour.setMyThread(thread);
				behaviour.getThread().start();
				setChanged();
				notifyObservers("--->START" + myID + behaviour.getThread()
						+ "<---");
			} else if (!behaviour.getThread().isAlive()) {
				Thread thread = new Thread(behaviour);
				behaviour.setMyThread(thread);
				behaviour.getThread().start();
				setChanged();
				notifyObservers("--->START" + myID + behaviour.getThread()
						+ "<---");
			}
		}
	}

	/**
	 * @return PersonalAgentID ritorna il riferimento all'agente del JAMAgent
	 */
	public PersonalAgentID getMyID() {
		return myID;
	}
}
