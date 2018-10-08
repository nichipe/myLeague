package gui.pannelli;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import gestioneCampionato.Campionato;
import gui.finestre.MenuIniziale;
import gui.DynamicLabel;
import gui.bottoni.ConfirmButton;

/** Pannello di creazione nuovo campionato
 * <p>
 * Mette a disposizione dell'utente tutti gli elementi per inizializzare un nuovo campionato.<br>
 * Permette dunque di scegliere:<br>
 * <li>il numero delle squadre partecipanti (obbligatoriamente pari) tra un minimo di 2 e un massimo di 20;
 * <li>su quale sport sara' disputato il campionato (calcio, pallavolo o rugby);
 * <li>il nome della competizione.
 */
@SuppressWarnings("serial")
public class CreazioneNuovoCampionato extends JPanel implements ActionListener{
	/**La finestra di cui fa parte il pannello */
	private MenuIniziale fp; 
	/**Scelta numero squadre partecipanti */
	private JComboBox<Integer> numSquadre; 
	/**Radio button di scelta della pallavolo */
	private JRadioButton volley; 
	/**Radio button di scelta del rugby */
	private JRadioButton rugby; 
	/**Campo di testo per il nome del campionato */
	private JTextField txt; 
	/**Pulsante di conferma dei dati */
	private ConfirmButton conferma; 
	
	/** Costruttore
	 * 
	 * @param fp Finestra in cui e' contenuto il pannello.
	 */
	public CreazioneNuovoCampionato(MenuIniziale fp) {
		super();
		this.fp = fp;
		
		setLayout(null); // Imposto il layout manager nullo per poter inserire manualmente la posizione di ogni oggetto 
		
		setBackground(Color.white); // Imposto il colore di sfondo del pannello
		
		/* Calcolo le dimensioni (in pixel) del pannello per il posizionamento degli oggetti al suo interno */
		int w = fp.getWidth(); 
		int h = fp.getHeight();
		
		/* Creo l'oggetto titolo */
		Titolo titolo = new Titolo("Crea nuovo campionato",getBackground(),new Color(4,66,140),false);
		titolo.setBounds(0,h/20,w,h/10);
		
		/* Creo un separatore da inserire tra il titolo e gli altri elementi */
		JSeparator o = new JSeparator(SwingConstants.HORIZONTAL);
		o.setBounds(w/10,h*3/20,w*8/10,5);
		
		/* Creo i label per le informazioni da acquisire (numero squadre, sport, nome campionato)*/
		DynamicLabel num = new DynamicLabel("Numero squadre",true);
		num.setBounds(w/10,h*9/40,w/5,h/20);
		DynamicLabel sport = new DynamicLabel("Sport",true);
		sport.setBounds(w/10,h*14/40,w/5,h/20);
		DynamicLabel nome = new DynamicLabel("Nome campionato",true);
		nome.setBounds(w/10,h*11/20,w/5,h/20);
		
		/* Creo il box per la scelta del numero di squadre */
		numSquadre = new JComboBox<Integer>();
		for(int i=2;i<=20;i+=2) numSquadre.addItem(i); //Max 20 squadre partecipanti
		numSquadre.setBounds(w*3/10,h*9/40,w/15,h/20);
		
		/* Creo i radio button per la scelta dello sport */
		JRadioButton calcio = new JRadioButton("Calcio");
		calcio.setSelected(true);
		calcio.setBounds(w/10,h*13/32,w/15,h/20);
		calcio.setBackground(Color.white);
		volley = new JRadioButton("Pallavolo"); //Globale per poter sapere se e' selezionato
		volley.setBounds(w/5,h*13/32,w/11,h/20);
		volley.setBackground(Color.white);
		rugby = new JRadioButton("Rugby"); //Globale per poter capire se e' selezionato
		rugby.setBounds(w/3,h*13/32,w/15,h/20);
		rugby.setBackground(Color.white);
		
		ButtonGroup g = new ButtonGroup();	//Button group che mi permette di legare logicamente tra loro i tre radio button
		g.add(calcio); g.add(volley); g.add(rugby);
		
		/* Creo lo spazio per inserire il nome del campionato */
		txt = new JTextField(21);
		txt.setBounds(w/10,h*3/5,w/3,h/20);
		
		/* Creo il bottone di conferma dei dati e quello di ritorno alla scelta del torneo */
		conferma = new ConfirmButton("Conferma");
		conferma.setBounds(w*3/5,h*3/4,w/5,h/10);
		conferma.addActionListener(this);
		ConfirmButton annulla = new ConfirmButton("Annulla");
		annulla.setBounds(w/5,h*3/4,w/5,h/10);
		annulla.addActionListener(this);
		
		/* Aggiungo gli elementi al pannello */
		add(titolo); add(o); //Titolo e separatore 
		add(num); add(sport); add(nome); //Etichette sezioni
		add(numSquadre); //Combo box numero squadre
		add(calcio); add(volley); add(rugby); //Radio button sport
		add(txt); //Campo di testo per nome campionato
		add(conferma); add(annulla); //éulsanti funzione
	}

	/** Metodo di gestione degli eventi generati dai pulsanti del pannello */
	@Override
	public void actionPerformed(ActionEvent e) {
		/* Click pulsante "Conferma" */
		if(e.getSource().equals(conferma)){
			String t = txt.getText(); //Nome campionato
			int n = (int)numSquadre.getSelectedItem(); //Numero squadre partecipanti
			
			/* s = 1(se lo sport scelto e' il calcio), 2(pallavolo), 3(rugby) */ 
			int s=1;
			if(volley.isSelected()) s = 2;
			else if(rugby.isSelected()) s = 3;
		 
			Campionato c = new Campionato(t,n,s); //Creo l'oggetto di tipo Campionato passandogli i valori ottenuti
			fp.visualizzaMenuInserimentoSquadre(c); //Mostro il pannello di inserimento squadre
		}
		
		/* Click pulsante "Annulla" */
		else fp.visualizzaMenuScelta(this); //Elimino questo pannello e mostro di nuovo il menu di scelta
	}	
}
