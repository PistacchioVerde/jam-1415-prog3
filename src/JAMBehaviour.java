package JAM;

/**
 * classe che definisce un behaviour. Fornisce i metodi per conoscere il thread
 * del behaviour, settare, invocare la sleep e interrompere il thread. Fornisce
 * anche le signature dei metodi start, setup e dispose.
 *
 * @author Andrea Bragagnolo
 */
public abstract class JAMBehaviour implements Runnable {

	private boolean done;
	private Thread myThread;
	public JAMAgent myAgent;

    /**
     * costruttore del behaviour che setta a false la variabile done e
     * inizializza myAgent tramite parametro
     *
     * @param jamAgent JAMAgent riferimento all'agente a cui appartiene il
     * behaviour
     */
    public JAMBehaviour(JAMAgent jamAgent) {
        myAgent = jamAgent;
        done = false;
    }

    /**
     *
     * @return Thread metodo usato per conoscere il thread del behaviour
     */
    public Thread getThread() {
        return myThread;
    }

    /**
     * metodo che intorrompe il thread e setta done a true
     */
    public void done() {
        done = true;
        myThread.interrupt();
    }

    /**
     *
     * @return boolean ritorna il valore di done
     */
    public boolean isDone() {
        return done;
    }

    /**
     * da alla variabile il valore di myThread
     *
     * @param myThread Thread
     */
    public void setMyThread(Thread myThread) {
        this.myThread = myThread;
    }

    /**
     * metodo usato per richiamare il metodo sleep() sul thread
     *
     * @param ms long millisecondi che inidicano quanto un thread deve andare in
     * sleep
     * @throws JAMBehaviourInterruptedException 
     * @throws InterruptedException
     */
    public void sleep(long ms) throws JAMBehaviourInterruptedException {
        try {
			myThread.sleep(ms);
		} catch (InterruptedException e) {
			throw new JAMBehaviourInterruptedException();
		}
    }

    public abstract void action() throws JAMBehaviourInterruptedException;

    public abstract void setup() throws JAMBehaviourInterruptedException;

    public abstract void dispose() throws JAMBehaviourInterruptedException;
}
