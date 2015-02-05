package JAM;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

/**
 * Implementazione dell'interfaccia ADSL, fornisce emtodi per inserire, cercare
 * o rimovere una MessageBox e i metodi per eseguire il bind dell'ADSL lo start
 * e l'unbind
 *
 * @author Andrea Bragagnolo
 */
public class ADSLImpl extends UnicastRemoteObject implements ADSL {

	private final ArrayList<RemoteMessageBox> messageBoxes;
	private int port;
	private String name;

	/**
	 * Costruttore di ADSL che istanzia una coda di RemoteMessageBox
	 *
	 * @throws RemoteException
	 */
	public ADSLImpl() throws RemoteException {
		messageBoxes = new ArrayList<RemoteMessageBox>();
	}

	/**
	 * Costruttore di ADSL con porta specifica
	 *
	 * @param port
	 *            porta su cui viene avviato lo rmiregistry
	 * @throws RemoteException
	 */
	public ADSLImpl(int port) throws RemoteException {
		this.port = port;
		messageBoxes = new ArrayList<RemoteMessageBox>();
		this.name = "ADSL";
	}

	/**
	 * Restituisce una lista di riferimenti ad oggetti (remoti) di tipo
	 * RemoteMessageBox i cui proprietari sono uguali a agentID
	 *
	 * @param agentID
	 *            AgentID identifica il proprietario dell'oggetto
	 *            RemoteMessageBox
	 * @return List una lista di oggetti remoti con proprietario agentID
	 * @throws RemoteException
	 */
	public synchronized List<RemoteMessageBox> getRemoteMessageBox(
			AgentID agentID) throws RemoteException {
		ArrayList<RemoteMessageBox> remoteBoxList = new ArrayList<RemoteMessageBox>();
		for (RemoteMessageBox box : messageBoxes) {
			if (agentID.equals(box.getOwner())) {
				remoteBoxList.add(box);
			}
		}
		return remoteBoxList;
	}

	/**
	 * Richiede l'inserimento di messageBox presso l'ADSL, se l'elemento e`
	 * gia presente non viene eettuata alcuna operazione e viene lanciata
	 * un'opportuna eccezione
	 *
	 * @param messageBox
	 *            RemoteMessageBox identifica la RemoteMessageBox da inserire
	 *            nell'ADSL
	 * @throws RemoteException
	 */
	public synchronized void insertRemoteMessageBox(RemoteMessageBox messageBox)
			throws RemoteException {
		for (RemoteMessageBox box : messageBoxes) {
			if (messageBox.getOwner().equals(box.getOwner())) {
				throw new JAMADSLException("Inserimento box - Box gia` presente");
			}
		}
		messageBoxes.add(messageBox);
	}

	/**
	 * Richiede la cancellazione dell'oggetto RemoteMessageBox presente presso
	 * l'ADSL di proprieta dell'agente agentID. Se l'elemento non e` presente
	 * non viene eettuata alcuna operazione e viene lanciata un'opportuna
	 * eccezione
	 *
	 * @param agentID
	 *            AgentID indentifica il proprietario dell'oggetto
	 *            RemoteMessageBox da cancellare
	 * @throws RemoteException
	 */
	public synchronized void removeRemoteMessageBox(AgentID agentID)
			throws RemoteException {
		boolean rimosso = false;
		for (int i = 0; i < messageBoxes.size(); i++) {
			if (agentID.equals(messageBoxes.get(i).getOwner())) {
				messageBoxes.remove(i);
				i--;
				rimosso = true;
			}
		}
		if (!rimosso) {
			throw new JAMADSLException("Rimozione box - Agent " + agentID + " non trovato");
		}
	}

	/**
	 * avvia un processo rmiregistry sulla porta port della macchina su cui
	 * l'applicativo e` eseguito
	 *
	 * @throws RemoteException
	 */
	public void startRMIRegistry() throws RemoteException {
		java.rmi.registry.LocateRegistry.createRegistry(port);
	}

	/**
	 * iscrive (rebind) l'oggetto ADSLImpl corrente sullo rmiregistry con nome
	 * ADSL
	 *
	 * @throws RemoteException
	 * @throws java.net.MalformedURLException
	 */
	public void startADSL() throws RemoteException, MalformedURLException {
		Naming.rebind("rmi://127.0.0.1:" + port + "/" + name, this);
	}

	/**
	 * cancella (unbind) l'oggetto ADSLImpl corrente dallo rmiregistry
	 *
	 * @throws java.rmi.RemoteException
	 * @throws java.net.MalformedURLException
	 * @throws NotBoundException
	 */
	public void stopADSL() throws RemoteException, MalformedURLException,
			NotBoundException {
		Naming.unbind("rmi://127.0.0.1:" + port + "/" + name);
	}
}
