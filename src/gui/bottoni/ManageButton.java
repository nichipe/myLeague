package gui.bottoni;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/** Pulsante di gestione
 * <p>
 * Utilizzato all'interno del menu principale per scegliere la funzionalita' desiderata. 
 */
public class ManageButton extends JButton{
	private static final long serialVersionUID = 1L; //Versione dell'oggetto
	
	/** Costruttore
	 * 
	 * @param titolo Etichetta del pulsante
	 */
	public ManageButton(String titolo) {
		super(titolo);
	}
	
	/** Metodo di decorazione del bottone */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		setBorder(BorderFactory.createMatteBorder(1,1,10,1,new Color(4,66,140))); // Creo il bordo 
		setBackground(Color.white); // Imposto il colore di sfondo
		setForeground(new Color(4,66,140)); // Imposto il colore dell'etichetta
	}
}
