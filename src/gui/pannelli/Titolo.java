package gui.pannelli;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Pannello del titolo
 * <p>
 * Utilizzato per inserire il titolo alle finestre principali.
 */
@SuppressWarnings("serial")
public class Titolo extends JPanel{
	/**Stringa del titolo */
	private JLabel t; 

	/** Costruttore 
	 * 
	 * @param titolo Stringa del titolo
	 * @param bg Colore di sfondo del titolo
	 * @param fg Colore del testo del titolo
	 * @param isMainTitle Indica se e' un titolo principale
	 * @param isUpper Indica se e' necessario mostrarlo tutto in maiuscolo
	 */
	public Titolo(String titolo,Color bg,Color fg,Boolean isMainTitle,Boolean isUpper) {
		super();
		
		setBackground(bg); // Imposto il colore di sfondo 

		/* Imposto il titolo */
		if(isUpper) t = new JLabel(titolo.toUpperCase());
		else t = new JLabel(titolo);
		t.setForeground(fg);
		if(isMainTitle) t.setFont(new Font("Trebuchet MS",Font.BOLD,64));
		else t.setFont(new Font("Trebuchet MS",Font.BOLD,32));
		
		add(t,BorderLayout.CENTER); // Aggiungo il titolo al pannello
	}
	
	/** Costruttore  
	 * 
	 * @param titolo Testo del titolo
	 * @param bg Colore di sfondo
	 */
	public Titolo(String titolo,Color bg) { this(titolo,bg,Color.white,false,true); }
	
	/** Costruttore  
	 * 
	 * @param titolo Testo del titolo
	 * @param bg Colore di sfondo
	 * @param fg Colore del testo
	 * @param isMainTitle Indica se e' un titolo principale
	 */
	public Titolo(String titolo,Color bg,Color fg,Boolean isMainTitle) { this(titolo,bg,fg,isMainTitle,true); }
	
	/** Costruttore  
	 * 
	 * @param titolo Testo del titolo
	 * @param bg Colore di sfondo
	 * @param isMainTitle Indica se e' un titolo principale
	 * @param isUpper Indica se deve essere stampato tutto in maiuscolo
	 */
	public Titolo(String titolo,Color bg,Boolean isMainTitle,Boolean isUpper) { this(titolo,bg,Color.white,isMainTitle,isUpper); }
	
	/** Costruttore  
	 * 
	 * @param titolo Testo del titolo
	 * @param bg Colore di sfondo
	 * @param isMainTitle Indica se e' un titolo principale
	 */
	public Titolo(String titolo,Color bg,Boolean isMainTitle) { this(titolo,bg,Color.white,isMainTitle,true); }
}