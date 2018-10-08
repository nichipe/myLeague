package gui.pannelli;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import gestioneCampionato.Campionato;
import gui.bottoni.ManageButton;
import gui.finestre.MenuPrincipale;

/** Pannello menu principale
 * <p>
 * Contiene tutti i bottoni che permettono di scegliere l'operazione desiderata tra:<br>
 * <li>Modifica squadra
 * <li>Modifica/Inserimento/Cancellazione risultati
 * <li>Visualizzazione calendario
 * <li>Visualizzazione classifica
 * Nell'angolo superiore destro è mostrato il logo dello sport su cui si disputa il campionato.
 */
@SuppressWarnings("serial")
public class MenuGestione extends JPanel{
	
	/** Costruttore
	 * 
	 * @param mp La finestra in cui e' inserito il pannello.
	 * @param c Il campionato in corso.
	 */
	public MenuGestione(MenuPrincipale mp,Campionato c) {
		super();
		
		setLayout(null); // Imposto il layout manager nullo per poter inserire manualmente la posizione di ogni oggetto 
		setBackground(Color.white); // Imposto il colore di sfondo del pannello
		
		/* Calcolo le dimensioni (in pixel) del pannello per il posizionamento degli oggetti al suo interno */
		int w = mp.getWidth(); 
		int h = mp.getHeight();
		
		/* Creo l'oggetto titolo */
		Titolo titolo = new Titolo(c.getNome(),getBackground(),new Color(4,66,140),true);
		titolo.setBounds(0,h/20,w,h/5);
		
		/* Creo un separatore da inserire tra il titolo e gli altri elementi e altri due per separare le tre sezioni */
		JSeparator o = new JSeparator(SwingConstants.HORIZONTAL);
		o.setBounds(w/10,h/4,w*8/10,5);
		JSeparator v1 = new JSeparator(SwingConstants.VERTICAL);
		v1.setBounds(w/3,h*5/16,5,h*9/16);
		JSeparator v2 = new JSeparator(SwingConstants.VERTICAL);
		v2.setBounds(w*2/3,h*5/16,5,h*9/16);
		
		/* Inserisco i label di intestazione delle tre sezioni */
		JLabel gestione = new JLabel("GESTISCI");
		gestione.setForeground(Color.black);
		gestione.setHorizontalAlignment(SwingConstants.CENTER);
		gestione.setFont(new Font("Trebuchet MS",Font.BOLD,20));
		gestione.setBounds(w/30,h/4,w*4/15,h/10);
		JLabel visualizza = new JLabel("VISUALIZZA");
		visualizza.setForeground(Color.black);
		visualizza.setHorizontalAlignment(SwingConstants.CENTER);
		visualizza.setFont(new Font("Trebuchet MS",Font.BOLD,20));
		visualizza.setBounds(w*11/30,h/4,w*4/15,h/10);
		
		/* Inserisco l'immagine identificativa dello sport */
		ImageIcon imgSport = null;
		Image img = null;
		if(c.getSport()==1) imgSport = new ImageIcon("./media/calcio.png");
		if(c.getSport()==2) imgSport = new ImageIcon("./media/volley.png");
		if(c.getSport()==3) imgSport = new ImageIcon("./media/rugby.png");
		img = imgSport.getImage().getScaledInstance(w*4/15,h/4,0);
		imgSport.setImage(img);
		JLabel sport = new JLabel();
		sport.setIcon(imgSport);
		sport.setBounds(w*7/10,h*13/40,w*4/15,h/4);
		
		/* Creo i bottoni per le varie funzionalita' */
		ManageButton modificaSquadra = new ManageButton("Modifica squadra");
		modificaSquadra.setBounds(w/30,h*2/5,w*4/15,h*3/20);
		modificaSquadra.addActionListener(mp);
		
		ManageButton risultati = new ManageButton("Inserisci risultati");
		risultati.setBounds(w/30,h*13/20,w*4/15,h*3/20);
		risultati.addActionListener(mp);
		
		ManageButton visualizzaCalendario = new ManageButton("Calendario");
		visualizzaCalendario.setBounds(w*11/30,h*2/5,w*4/15,h*3/20);
		visualizzaCalendario.addActionListener(mp);
		
		ManageButton visualizzaClassifica = new ManageButton("Classifica");
		visualizzaClassifica.setBounds(w*11/30,h*13/20,w*4/15,h*3/20);
		visualizzaClassifica.addActionListener(mp);
		
		ManageButton menuPrincipale = new ManageButton("Ritorna al menu iniziale");
		menuPrincipale.setBounds(w*7/10,h*13/20,w*4/15,h*3/20);
		menuPrincipale.addActionListener(mp);
		
		/* Aggiungo gli elementi al pannello */
		add(titolo); add(o); add(v1); add(v2); //Titolo e separatori 
		add(sport); //Logo dello sport 
		add(gestione); add(visualizza); //Etichette sezioni
		add(modificaSquadra); add(risultati); add(visualizzaCalendario); add(visualizzaClassifica); add(menuPrincipale); //Pulsanti modalita'
	}
}







