package gestioneCampionato;

/** Oggetto classifica
 *  <p>
 *	Fornisce metodi per l'aggiornamento delle statistiche delle squadre e per il calcolo della classifica.<br>
 *	E' astratta, viene specializzata in modo diverso a seconda dello sport del campionato.<br>
 */
/* EREDITARIETA': in questo contesto si evidenzia l'utilizzo dell'ereditarietà, in quanto l'oggetto classifica viene specializzato da altre
 *                sottoclassi specifiche per il calcolo di ogni sport. Questi oggetti, estendo quello principale, ereditano tutte le
 *                variabili e i metodi con visibilità pubblica o protetta e devono implementare i metodi astratti definiti in questa classe.*/
public abstract class Classifica {
	/**Ordine degli indici delle squadre per classifica */
	protected int[] posizione; 
	/**Ordine dei punti delle squadre per classifica */
	protected int[] punti; 
	/**Numero delle squadre partecipanti */
	protected int numSquadre; 
	
	/** Costruttore
	 * 
	 * @param numSquadre Numero delle squadre partecipanti
	 */
	public Classifica(int numSquadre) {
		this.numSquadre = numSquadre;
		
		/* Creo e resetto i vettori della posizione e del punteggio delle squadre */
		posizione = new int[numSquadre];
		for(int i=0;i<numSquadre;i++) posizione[i] = i;
		punti = new int[numSquadre];
		for(int i=0;i<numSquadre;i++) punti[i] = 0;
	}
	
	/** Getter del numero delle squadre partecipanti 
	 * 
	 * @return Numero squadre partecipanti
	 */
	public int getNumSquadre() { return numSquadre; }
	
	/** Metodo di calcolo della classifica */
	public abstract void calcolaClassifica();
	
	/** Metodo di calcolo delle statistiche di ogni squadra */
	protected  abstract void calcolaStatistiche();
	
	/** Metodo di controllo: decide se la squadra alla posizione i deve essere scambiata con la successiva */
	protected  abstract boolean controllo(int i);
}
