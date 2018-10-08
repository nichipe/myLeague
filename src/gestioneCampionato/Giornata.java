package gestioneCampionato;

import java.io.Serializable;
import java.util.Vector;

/** Oggetto giornata
 * <p>
 * Tiene traccia di ogni partita tramite due array di squadre (casa e trasferta).<br>
 * Fornisce metodi per il recupero di una o tutte le partite di giornata e per l'inserimento del risultato.<br>
 * Permette di calcolare le partite a squadre invertite (per il ritorno).<br>
 */
public class Giornata implements Serializable{
	private static final long serialVersionUID = 1L; //Versione dell'oggetto
	
	/** Lista delle squadre in casa, ordinate per partita */
	private	Vector<Squadra> casa; 
	/** Lista delle squadre in trasferta, ordinate per partita */
	private Vector<Squadra> trasferta; 
	/** Valore che indica se sono stati inseriti i risultati di andata */
	private boolean isRisultatiAndata; 
	/** Valore che indica se sono stati inseriti i risultati di ritorno */
	private boolean isRisultatiRitorno; 
	/** Risultati delle squadre di casa delle partite di andata */
	private int[] risultatiCasaAndata; 
	/** Risultati delle squadre in trasferta delle partite di andata */
	private int[] risultatiTrasfertaAndata; 
	/** Risultati delle squadre di casa delle partite di ritorno */
	private int[] risultatiCasaRitorno; 
	/** Risultati secondari delle squadre in trasferta delle partite di ritorno */
	private int[] risultatiTrasfertaRitorno; 
	/** Risultati secondari delle squadre di casa delle partite di andata */
	private int[] risultatiSecondariCasaAndata; 
	/** Risultati secondari delle squadre in trasferta delle partite di andata */
	private int[] risultatiSecondariTrasfertaAndata; 
	/** Risultati secondari delle squadre di casa delle partite di ritorno */
	private int[] risultatiSecondariCasaRitorno; 
	/** Risultati secondari delle squadre in trasferta delle partite di ritorno */
	private int[] risultatiSecondariTrasfertaRitorno; 
	
	/** Costruttore
	 * 
	 * @param numPartite Numero delle giornate nel singolo girone (solo andata)
	 */
	public Giornata(int numPartite) {
		casa = new Vector<Squadra>(numPartite);
		trasferta = new Vector<Squadra>(numPartite);
		
		isRisultatiAndata = isRisultatiRitorno = false; //Inizialmente non sono presenti risultati
		
		/* Creo i vettori dei risultati 
		 * 
		 * I risultati "secondari" si riferiscono a punti totali (pallavolo) e mete (rugby)
		 */
		risultatiCasaAndata = new int[numPartite];
		risultatiTrasfertaAndata = new int[numPartite];
		risultatiCasaRitorno = new int[numPartite];
		risultatiTrasfertaRitorno = new int[numPartite];
		risultatiSecondariCasaAndata = new int[numPartite];
		risultatiSecondariTrasfertaAndata = new int[numPartite];
		risultatiSecondariCasaRitorno = new int[numPartite];
		risultatiSecondariTrasfertaRitorno = new int[numPartite];
	}
	
	/** Metodo di inserimento di una partita
	 * 
	 * @param c Indice della squadra di casa della partita da aggiungere
	 * @param t Indice della squadra in trasferta della partita da aggiungere
 	 */
	public void addPartita(Squadra c,Squadra t) {
		casa.addElement(c);
		trasferta.addElement(t);
	}
	
	/** Metodo di recupero della squadra in casa alla index-esima partita
	 * 
	 * @param index Indice della squadra
	 * @return Oggetto squadra
	 */
	public Squadra getCasaAtIndex(int index) { return casa.elementAt(index); }
	
	/** Metodo di recupero della squadra in trasferta alla index-esima partita
	 * 
	 * @param index Indice della squadra
	 * @return Oggetto squadra
	 */
	public Squadra getTrasfertaAtIndex(int index) { return trasferta.elementAt(index); }
	
	/** Ritorna un booleano che indica se i risultati di andata sono presenti 
	 * 
	 * @return 'true' se i risultati di andata sono inseriti, altrimenti 'false' 
	 */
	public boolean isRisultatiAndata() { return isRisultatiAndata; }
	
	/** Ritorna un booleano che indica se i risultati di ritorno sono presenti 
	 * 
	 * @return 'true' se i risultati di andata sono inseriti, altrimenti 'false'
	 */
	public boolean isRisultatiRitorno() { return isRisultatiRitorno; }
	
	/** Setta il booleano che controlla se sono stati inseriti i risultati dell'andata */
	public void setRisultatiAndata() { isRisultatiAndata = true; }
	
	/** Setta il booleano che controlla se sono stati inseriti i risultati del ritorno */
	public void setRisultatiRitorno() { isRisultatiRitorno = true; }
	
	/** Resetta il booleano che controlla se sono stati inseriti i risultati dell'andata */
	public void resetRisultatiAndata() { isRisultatiAndata = false; }
	
	/** Resetta il booleano che controlla se sono stati inseriti i risultati del ritorno */
	public void resetRisultatiRitorno() { isRisultatiRitorno = false; }
	
	/** Ritorna il risultato secondario della squadra di casa della index-esima partita di andata 
	 * 
	 * @param index Indice della squadra
	 * @return Punteggio della squadra di casa all'andata alla index-esima partita
	 */
	public int getRisultatoSecondarioCasaAndata(int index) { return risultatiSecondariCasaAndata[index]; }
	
	/** Ritorna il risultato secondario della squadra in trasferta della index-esima partita di andata 
	 * 
	 * @param index Indice della squadra
	 * @return Punteggio della squadra in trasferta all'andata alla index-esima partita
	 */
	public int getRisultatoSecondarioTrasfertaAndata(int index) { return risultatiSecondariTrasfertaAndata[index]; }
	
	/** Ritorna il risultato secondario della squadra di casa della index-esima partita di ritorno 
	 * 
	 * @param index Indice della squadra
	 * @return Punteggio della squadra di casa al ritorno alla index-esima partita
	 */
	public int getRisultatoSecondarioCasaRitorno(int index) { return risultatiSecondariCasaRitorno[index]; }
	
	/**Ritorna il risultato secondario della squadra in trasferta della index-esima partita di ritorno 
	 * 
	 * @param index Indice della squadra
	 * @return Punteggio della squadra in trasferta alla index-esima partita
	 */
	public int getRisultatoSecondarioTrasfertaRitorno(int index) { return risultatiSecondariTrasfertaRitorno[index]; }
	
	/** Setta il risultato secondario della squadra di casa della index-esima partita di andata 
	 * 
	 * @param ris Punteggio
	 * @param index Indice della partita
	 */
	public void setRisultatoSecondarioCasaAndata(int ris,int index) { risultatiSecondariCasaAndata[index] = ris; }
	
	/** Setta il risultato secondario della squadra in trasferta della index-esima partita di andata 
	 * 
	 * @param ris Punteggio
	 * @param index Indice della partita
	 */
	public void setRisultatoSecondarioTrasfertaAndata(int ris,int index) { risultatiSecondariTrasfertaAndata[index] = ris; }
	
	/** Setta il risultato secondario della squadra di casa della index-esima partita di ritorno 
	 * 
	 * @param ris Punteggio
	 * @param index Indice della partita
	 */
	public void setRisultatoSecondarioCasaRitorno(int ris,int index) { risultatiSecondariCasaRitorno[index] = ris; }
	
	/** Setta il risultato secondario della squadra in trasferta della index-esima partita di ritorno 
	 * 
	 * @param ris Punteggio
	 * @param index Indice della partita
	 */
	public void setRisultatoSecondarioTrasfertaRitorno(int ris,int index) { risultatiSecondariTrasfertaRitorno[index] = ris; }
	
	/** Ritorna il risultato della squadra di casa della index-esima partita di andata 
	 * 
	 * @param index Indice della squadra
	 * @return Punteggio della squadra di casa all'andata alla index-esima partita
	 */
	public int getRisultatoCasaAndata(int index) { return risultatiCasaAndata[index]; }
	
	/** Ritorna il risultato della squadra in trasferta della index-esima partita di andata 
	 * 
	 * @param index Indice della squadra
	 * @return Punteggio della squadra in trasferta all'andata alla index-esima partita
	 */
	public int getRisultatoTrasfertaAndata(int index) { return risultatiTrasfertaAndata[index]; }
	
	/** Ritorna il risultato della squadra di casa della index-esima partita di ritorno 
	 * 
	 * @param index Indice della squadra
	 * @return Punteggio della squadra di casa al ritorno alla index-esima partita
	 */
	public int getRisultatoCasaRitorno(int index) { return risultatiCasaRitorno[index]; }
	
	/** Ritorna il risultato della squadra in trasferta della index-esima partita di ritorno 
	 * 
	 * @param index Indice della squadra
	 * @return Punteggio della squadra in trasferta alla index-esima partita
	 */
	public int getRisultatoTrasfertaRitorno(int index) { return risultatiTrasfertaRitorno[index]; }
	
	/** Setta il risultato della squadra di casa della index-esima partita di andata 
	 * 
	 * @param ris Punteggio
	 * @param index Indice della partita
	 */
	public void setRisultatoCasaAndata(int ris,int index) { risultatiCasaAndata[index] = ris; }
	
	/** Setta il risultato della squadra in trasferta della index-esima partita di andata 
	 * 
	 * @param ris Punteggio
	 * @param index Indice della partita
	 */
	public void setRisultatoTrasfertaAndata(int ris,int index) { risultatiTrasfertaAndata[index] = ris; }
	
	/** Setta il risultato della squadra di casa della index-esima partita di ritorno 
	 * 
	 * @param ris Punteggio
	 * @param index Indice della partita
	 */
	public void setRisultatoCasaRitorno(int ris,int index) { risultatiCasaRitorno[index] = ris; }
	
	/** Setta il risultato della squadra in trasferta della index-esima partita di ritorno 
	 * 
	 * @param ris Punteggio
	 * @param index Indice della partita
	 */
	public void setRisultatoTrasfertaRitorno(int ris,int index) { risultatiTrasfertaRitorno[index] = ris; }
}
