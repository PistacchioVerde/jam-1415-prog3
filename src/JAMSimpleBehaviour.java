package JAM;

/**
 * classe astratta che estende JAMBehaviour ed implementa l'esecuzione del
 * comportamento in maniera "simple" ovvero esegue una volta sola il metodo run,
 * setup e dispose. Il metodo dispose é eseguito comunque (finally)
 *
 * @author Andrea Bragagnolo
 */
public abstract class JAMSimpleBehaviour extends JAMBehaviour {

    /**
     * costruttore con agente di riferimento
     *
     * @param jamAgent JAMAgent proprietario del comportamento
     */
    public JAMSimpleBehaviour(JAMAgent jamAgent) {
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
            action();
        } catch (JAMBehaviourInterruptedException ex) {
            if (isDone()) {
                return;
            }
            System.out.println(ex);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                dispose();
                done();
            } catch (JAMBehaviourInterruptedException ex) {
                System.out.println(ex);
            }
        }
    }
}
