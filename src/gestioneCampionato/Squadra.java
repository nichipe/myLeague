package gestioneCampionato;

import java.io.*;

/** Oggetto squadra
 * <p> 
 * Contiene informazioni riguardanti nome, sede e logo della squadra e i metodi per poter leggere e modificare ognuno di questi valori.<br>
 * E' un oggetto serializzabile per far si che possa essere salvato su file e eventualmente caricato in un secondo momento.<br>
 * Implementa l'interfaccia Comparable per permetterne l'ordinamento alfabetico dopo l'esecuzione del sorteggio.<br>
 */
public class Squadra implements Serializable,Comparable<Object>{
	private static final long serialVersionUID = 1L; //La versione dell'oggetto
	
	/**Nome della squadra */
	private String nome; 
	/**Sede della squadra */
	private String sede; 
	/**Percorso al file immagine del logo della squadra */
	private String logo; 
	
	/* POLIMORFISMO: un esempio di polimorfismo. L'oggetto squadra, se non vengono passati parametri al costruttore, setta nome e sede come
	 *               stringhe vuote e il logo come quello di default; altrimenti setta i valori passati nel costruttore. */
	/** Costruttore
	 * 
	 * @param nome Nome della squadra
	 * @param sede Sede della squadra
	 * @param logo Logo della squadra (percorso al file immagine)
	 */
	public Squadra(String nome,String sede,String logo) {
		setNome(nome);
		setSede(sede);
		setLogo(logo);
	}
	/** Costruttore alternativo */
	public Squadra() { this("","","./media/loghi/logoDefault.png"); } // Costruttore vuoto, utilizzato quando viene creato per la prima volta il campionato
	
	/** Metodo di ordinamento della squadra basato sul nome 
	 * <p>
	 * Richiamato dall'oggetto Campionato per riordinare le squadre all'interno del vettore.
	 */
	@Override
	public int compareTo(Object o) {
		Squadra s = (Squadra)o;
		return nome.compareTo(s.getNome());
	} 
	
	/** Setter del nome della squadra 
	 * 
	 * @param nome Nome da impostare
	 */
	public void setNome(String nome) { this.nome = nome; } 
	
	/** Setter della sede della squadra 
	 * 
	 * @param sede Sede da impostare
	 */
	public void setSede(String sede) { this.sede = sede; } 
	
	/** Setter del logo della squadra 
	 * 
	 * @param logo Logo da impostare
	 */
	public void setLogo(String logo) { this.logo = logo; }
	
	/** Getter del nome della squadra 
	 * 
	 * @return Nome della squadra
	 */
	public String getNome() { return nome; }
	
	/** Getter della sede della squadra 
	 * 
	 * @return Sede della squadra
	 */
	public String getSede() { return sede; }
	
	/** Getter del logo della squadra 
	 * 
	 * @return Logo della squadra (percorso al file immagine)
	 */
	public String getLogo() { return logo; }
}