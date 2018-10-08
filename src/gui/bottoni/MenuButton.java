package gui.bottoni;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

/** Pulsante menu principale
 * <p>
 * Pulsante utilizzato nel menu di scelta iniziale.
 */
public class MenuButton extends JButton{
	private static final long serialVersionUID = 1L; //Versione dell'oggetto
	
	/** Costruttore
	 * 
	 * @param titolo Etichetta del pulsante
	 */
	public MenuButton(String titolo) {
		super(titolo);
	}
	
	/** Metodo di decorazione del bottone */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		/* Creo il bordo */
		Border b =  BorderFactory.createLineBorder(Color.white,3);
		b = BorderFactory.createCompoundBorder(b, BorderFactory.createEtchedBorder());
		setBorder(b);
		
		setBackground(Color.white); // Imposto il colore di sfondo	
		setForeground(new Color(4,66,140)); // Imposto il colore dell'etichetta
	}
}
