package gestioneCampionato;

import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

/** Oggetto calendario
 * <p>
 * Contiene l'ordine degli incontri tra le squadre.<br>
 * Fornisce i metodi per sorteggiare gli incontri e per restituire le partite relative ad una precisa giornata o ad una precisa squadra.<br>
 * Le squadre si incontrano due volte (andata e ritorno), in cui ognuna gioca una partita in casa e una in trasferta; ogni squadra affronta 
 * esattamente due volte tutte le altre.<br>
 */
public class Calendario implements Serializable{
	private static final long serialVersionUID = 1L; //Versione dell'oggetto
	
	/**Vettore delle giornate del calendario */
	private Vector<Giornata> g; 
	/**Numero delle squadre partecipanti */
	private int numSquadre; 
	
	/** Costruttore
	 * 
	 * @param numSquadre Numero delle squadre partecipanti
	 */
	public Calendario(int numSquadre) {
		this.numSquadre = numSquadre;
		
		/* Creo e popolo il vettore delle giornate */
		g = new Vector<Giornata>(numSquadre);
		for(int i=0;i<numSquadre-1;i++) g.addElement(new Giornata(numSquadre/2));
	}
	
	/** Metodo di sorteggio del campionato
	 * <p>
	 * Implementa l'<b>Algoritmo di Berger</b> per il calcolo del calendario.<br>
	 * Ogni squadra affronta due volte tutte le altre, una volta in casa e una in trasferta.<br>
	 * Ogni squadra gioca una partita in casa e una in trasferta alternativamente.<br>
	 * 
	 * @param squadre Vettore delle squadre partecipanti al torneo
	 */
	public void sorteggio(Vector<Squadra> squadre) {
		Random r = new Random(); //Oggetto usato per estrarre numeri casuali
		
		/* Se le squadre sono due non uso l'algoritmo di Berger, ma calcolo casualmente (casa/trasferta) la partita di andata */
		if(numSquadre == 2) {
			if(r.nextBoolean()) g.elementAt(0).addPartita(squadre.elementAt(0),squadre.elementAt(1));
			else g.elementAt(0).addPartita(squadre.elementAt(1),squadre.elementAt(0));
		}
		else { /* Se le squadre sono più di due implemento l'algoritmo di Berger */
			/* Creo due array di interi contenenti gli indici delle squadre di casa e trasferta */
			int[] casa = new int[numSquadre/2];
			int[] trasferta = new int[numSquadre/2];
		
			/* Array di booleani che, per ogni squadra, indica se deve essere o meno sorteggiata nel ciclo attuale */
			boolean[] indici = new boolean[numSquadre];
			for(int i=0;i<numSquadre;i++) indici[i] = true;
			
			/* Ad ogni ciclo scelgo casualmente un'indice (tra quelli ancora disponibili) per la prossima squadra di ogni vettore */
			for(int i=0;i<numSquadre/2;i++) {
				do{ casa[i] = r.nextInt(numSquadre); } while(!indici[casa[i]]);
				indici[casa[i]] = false;
				do{ trasferta[i] = r.nextInt(numSquadre); } while(!indici[trasferta[i]]);
				indici[trasferta[i]] = false;
			}
		
			for(int i=0;i<numSquadre-1;i++) {
				/* Genero le partite della giornate i-esima imponendo l'alternanza casa/trasferta */
				if(i%2 == 0) 
					for(int j=0;j<numSquadre/2;j++) g.elementAt(i).addPartita(squadre.elementAt(casa[j]),squadre.elementAt(trasferta[j]));
				else 
					for(int j=0;j<numSquadre/2;j++) g.elementAt(i).addPartita(squadre.elementAt(trasferta[j]),squadre.elementAt(casa[j]));
			
				/* Faccio lo shift dei due vettori (tramite le rispettive funzioni) per modificare le partite */
				int pivot = casa[0];
				int espulso = trasferta[numSquadre/2-1];
				trasferta = shiftD(trasferta,casa[1]);
				casa = shiftS(casa,espulso);
				casa[0] = pivot;
			}
		}
	}
	
	/** Metodo di shift a sinistra
	 * <p>
	 * Metodo di servizio della funzione sorteggio.<br>
	 * Shifta il vettore passato come parametro verso sinistra di una posizione, inserendo alla fine l'elemento e.<br>
	 * 
	 * @param vettore Vettore che viene shiftato
	 * @param e Elemento inserito nell'ultima posizione del vettore
	 * @return Il nuovo vettore
	 */
	private int[] shiftS(int[] vettore,int e) {
		int[] tmp = new int[numSquadre/2];
		
		for(int i=0;i<numSquadre/2-1;i++) tmp[i] = vettore[i+1];
		tmp[numSquadre/2-1] = e;
		
		return tmp;
	}
	
	/** Metodo di shift a destra
	 * <p>
	 * Metodo di servizio della funzione sorteggio.<br>
	 * Shifta il vettore passato come parametro verso destra di una posizione, inserendo all'inizio l'elemento passato come parametro.<br>
	 * 
	 * @param vettore Vettore che viene shiftato
	 * @param e Elemento inserito nella prima posizione del vettore
	 * @return Il nuovo vettore
	 */
	private int[] shiftD(int[] vettore,int e) {
		int[] tmp = new int[numSquadre/2];
		
		for(int i=1;i<numSquadre/2;i++) tmp[i] = vettore[i-1];
		tmp[0] = e;
		
		return tmp;
	}
	
	/** Metodo di recupero della giornata
	 * 
	 * @param index Indice della giornata richiesta
	 * @return Oggetto giornata all'indice index
	 */
	public Giornata getGiornata(int index) { return g.elementAt(index); }
}
