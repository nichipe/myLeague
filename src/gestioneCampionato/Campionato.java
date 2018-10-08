package gestioneCampionato;

import java.io.Serializable;
import java.util.Collections;
import java.util.Vector;

/** Oggetto campionato
 *  <p>
 * 	Contiene informazioni riguardanti nome della competizione, numero delle squadre partecipanti e sport su cui si disputa.<br>
 * 	Memorizza le squadre partecipanti e fornisce metodi per accedere ad esse sia tramite nome che tramite indice.<br>
 * 	Fornisce inoltre metodi per il controllo e la correzione di dati inconsistenti o mancanti e per il salvataggio delle squadre su file.<br>
 */
public class Campionato implements Serializable{
	private static final long serialVersionUID = 1L; //Versione dell'oggetto
	
	/**Il calendario degli incontri del campionato */
	private Calendario c; 
	/**La classifica del campionato */
	private transient Classifica cl; 
	/**Nome del campionato */
	private String nome;
	/**Il nome del file su cui è salvato il campionato */
	private String nomeFile;
	/**Vettore contenente le squadre partecipanti */
	private Vector<Squadra> squadre; 
	/**Numero squadre partecipanti */
	private int numSquadre; 
	/**Codice identificativo dello sport: 1 = calcio, 2 = pallavolo, 3 = rugby */
	private int sport; 
	
	
	
	/** Costruttore
	 * 
	 * @param nome Nome della competizione
	 * @param numSquadre Numero di squadre partecipanti
	 * @param sport Sport su cui si disputa la competizione
	 */
	public Campionato(String nome,int numSquadre,int sport) {
		this.nome = nome;
		this.numSquadre = numSquadre;
		this.sport = sport;
		c = new Calendario(numSquadre);
		
		/* Creo e popolo il vettore delle squadre */
		squadre = new Vector<Squadra>(numSquadre);
		for(int i=0;i<numSquadre;i++) squadre.addElement(new Squadra());
		
		/* Creo la classifica */
		creaClassifica();
	}
	
	/** Metodo di creazione dell'oggetto classifica 
	 * <p>
	 * Istanzia l'oggetto classifica con quello specializzato per il campionato che si è creato.<br>
	 */
	public void creaClassifica() {
		if(sport==1) cl = new ClassificaCalcio(this);
		if(sport==2) cl = new ClassificaVolley(this);
		if(sport==3) cl = new ClassificaRugby(this);
	}
	
	/** Metodo di controllo dei dati forniti
	 * <p>
	 * Controlla il corretto inserimento dei dati e, eventualmente, aggiusta i valori.<br> 
	 */
	public void controlloDati() {
		if(nome.equals("")) nome = "Campionato"; //Nome di default del campionato
		for(int i=0;i<numSquadre;i++) {
			Squadra s = squadre.elementAt(i);
			if(s.getNome().equals("")) s.setNome("SenzaNome_" + i); //Nome di default squadra 
			if(s.getSede().equals("")) s.setSede("SenzaSede"); //Sede di default
		}
	
		c.sorteggio(squadre); //Dopo aver risolto gli eventuali problemi, sorteggio il calendario
		Collections.sort(squadre); //Riordino le squadre in ordine alfabetico
	}
	
	/** Getter del numero di squadre partecipanti al campionato
	 * 
	 * @return Numero di squadre partecipanti
	 */
	public int getNumSquadre() { return numSquadre; }
	
	/** Getter del nome del campionato
	 * 
	 * @return Nome campionato
	 */
	public String getNome() { return nome; }
	
	/** Getter della squadra tramite indice 
	 * 
	 * @param i Indice della squadra richiesta
	 * @return Oggetto squadra 
	 */
	public Squadra getSquadraAtIndex(int i) { return squadre.elementAt(i); }
	
	/** Getter della squadra tramite nome 
	 * 
	 * @param nome Nome della squadra richiesta
	 * @return Oggetto squadra 
	 */
	public Squadra getSquadraAtName(String nome) { 
		for(int i=0;i<numSquadre;i++) if(squadre.elementAt(i).getNome().equals(nome)) return squadre.elementAt(i); 
		return null;
	}
	
	/** Getter dell'indice della squadra tramite il nome
	 * 
	 * @param nome Nome squadra richiesta
	 * @return Indice della squadra
	 */
	public int getIndexAtName(String nome) {
		for(int i=0;i<numSquadre;i++) if(squadre.elementAt(i).getNome().equals(nome)) return i; 
		return -1;
	}
	
	/** Getter del calendario 
	 * 
	 * @return Oggetto calendario
	 */
	public Calendario getCalendario() { return c; }
	
	/** Getter della classifica
	 * 
	 * @return Oggetto classifica
	 */
	public Classifica getClassifica() { return cl; }
	
	/** Getter dello sport
	 * 
	 * @return Codice dello sport
	 */
	public int getSport() { return sport; }
	
	/** Setter del nome del file
	 * 
	 * @param nomeFile Il nome del file su cui è salvato il campionato
	 */
	public void setNomeFile(String nomeFile) { this.nomeFile = nomeFile; }
	
	/** Getter del nome del file
	 * 
	 * @return Il nome del file su cui è salvato il campionato
	 */ 
	public String getNomeFile() { return nomeFile; }
}
