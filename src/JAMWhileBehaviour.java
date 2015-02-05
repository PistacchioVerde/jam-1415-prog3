package JAM;

/**
 * classe astratta che estende JAMBehaviour ed implementa l'esecuzione del
 * comportamento in maniera "while" ovvero il metodo action viene ripetuto
 * fintanto che la variabile done non e` impostata a true. Il metodo dispose é
 * eseguito comunque (finally)
 * 
 * @author Andrea Bragagnolo
 */
public abstract class JAMWhileBehaviour extends JAMBehaviour {

	/**
	 * costruttore con agente di riferimento
	 * 
	 * @param jamAgent
	 *            JAMAgent proprietario del comportamento
	 */
	public JAMWhileBehaviour(JAMAgent jamAgent) {
		super(jamAgent);
	}

	/**
	 * implementazione del metodo run che esegue i metodi di setup, action e
	 * dispose. In caso di eccezzioni di tipo JAMBehaviourInterruptedException
	 * in setup o action viene invocato il metodo isDone() per controllare
	 * l'effettiva terminazione del procedimento. Nel caso il ritorno sia true
	 * viene terminata l'esecuzione, altrimenti stampata l'eccezione su console
	 */
	public void run() {
		try {
			setup();
			while (!isDone()) {
				action();
			}
		} catch (JAMBehaviourInterruptedException ex) {
			if (isDone()) {
				return;
			}
			System.out.println("JAMWhilebehaviour prima catch: " + ex);
		} finally {
			try {
				dispose();
			} catch (JAMBehaviourInterruptedException ex) {
				System.out.println("JAMWhileBehaviour finally: " + ex);
			}
		}
	}
}
