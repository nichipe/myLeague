package gui.bottoni;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JButton;

/** Pulsante di servizio
 * <p>
 * Pulsante semplice utilizzato per alcune scelte minori (ad esempio l'apertura del file chooser).
 */
public class ServiceButton extends JButton{
	private static final long serialVersionUID = 1L; //Versione dell'oggetto
	
	/** In alcuni casi i service button svolgono la loro funzione a coppie, questo e' il "bottone coppia" */
	ServiceButton sb; 
	
	/** Costruttore
	 * 
	 * @param titolo Etichetta del pulsante
	 */
	public ServiceButton(String titolo) {
		super(titolo);
	}
	
	/** Metodo di ritorno "bottone coppia"
	 * <p>
	 * Il metodo ritorna il bottone coppia, ovvero quello che si riferisce alla stessa squadra.
	 * 
	 * @return Il bottone coppia di questo 
	*/
	public ServiceButton getBottoneCoppia() { return sb; }
	
	/** Metodo di set del "bottone coppia"
	 * <p>
	 * Imposta il bottone coppia di questo.
	 * 
	 * @param sb L'oggetto da impostare come coppia.
	 */
	public void setBottoneCoppia(ServiceButton sb) { this.sb = sb; }
	
	/** Metodo di decorazione del bottone */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		setBackground(Color.white); // Imposto il colore di sfondo
		setForeground(Color.black); // Imposto il colore dell'etichetta
	}
}
