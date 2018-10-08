package gui.bottoni;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/** Pulsante di conferma
 * <p>
 * Utilizzato per confermare l'inserimento dei dati all'interno di un modulo.
 */
public class ConfirmButton extends JButton{
	private static final long serialVersionUID = 1L; //Versione dell'oggetto
	
	/** Costruttore
	 * 
	 * @param titolo Etichetta del pulsante
	 */
	public ConfirmButton(String titolo) {
		super(titolo);
	}
	
	/** Metodo di decorazione del bottone */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		setBorder(BorderFactory.createMatteBorder(1,5,1,1,Color.black)); // Creo il bordo 
		setBackground(Color.white); // Imposto il colore di sfondo
		setForeground(Color.black); // Imposto il colore dell'etichetta
	}
}

