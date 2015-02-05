package JAM;

/**
 * estensione di CategoryAgentID che crea un agente definito da nome e categoria
 *
 * @author Andrea Bragagnolo
 */
public class PersonalAgentID extends CategoryAgentID {

    /**
     * costruttore vuoto di PersonalAgentID che richiama quello di
     * CategoryAgentID
     */
    public PersonalAgentID() {
        super();
    }

    /**
     * costruttore con parametri
     *
     * @param name nome dell'agente da costruire
     * @param cat categoria dell'agente da costruire
     */
    public PersonalAgentID(String name, String cat) {
        super(cat);
        this.name = name;
    }

    /**
     * metodo equals che controlla se due agenti sono uguali. Ritorna false se
     * l'agente passato come parametro e` nullo altrimenti prova a castare
     * l'agent parametro in PersonalAgentID. Se riesce confronta nome e
     * categoria, nel caso il cast lanci un'eccezione viene richiamato il metodo
     * del CategoryAgentID
     *
     * @param agent agente da equiparare
     * @return true se sono uguali, false altrimenti
     */
    @Override
    public boolean equals(AgentID agent) {
        if (agent == null) {
            return false;
        }

        try {
            PersonalAgentID a = (PersonalAgentID) agent;
            if (a.name.equals(this.name) && a.category.equals(this.category)) {
                return true;
            }
        } catch (ClassCastException e) {
            return super.equals(agent);
        }
        return false;
    }
}
