package gui.pannelli;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import gestioneCampionato.Campionato;
import gui.finestre.MenuIniziale;
import gui.finestre.MenuPrincipale;
import gui.bottoni.MenuButton;

/** Pannello menu di scelta
 * <p>
 * Mette a disposizione dell'utente il menu per la scelta nuovo/carica campionato o per creare/eliminare una squadra.
 */
@SuppressWarnings("serial")
public class SceltaPartita extends JPanel{
	/**Finestra in cui e' contenuto il pannello */
	private MenuIniziale fp; 
	
	/** Costruttore
	 * 
	 * @param fp Finestra in cui e' contenuto il pannello.
	 * @param t Stringa contenente il titolo del pannello.
	 */
	public SceltaPartita(MenuIniziale fp,String t) {
		super();
		this.fp = fp;
		
		setLayout(null); // Imposto il layout manager nullo per poter inserire le coordinate di posizionamento di ogni oggetto
		
		setBackground(new Color(4,66,140)); // Imposto il colore di sfondo del pannello
		
		/* Calcolo le dimensioni (in pixel) del pannello per il posizionamento degli oggetti al suo interno */
		int w = fp.getWidth(); 
		int h = fp.getHeight(); 
		
		/* Creo l'oggetto titolo */
		Titolo titolo = new Titolo(t,getBackground(),true,false);
		titolo.setBounds(0,h/10,w,h/2);
		
		/* Creo i bottoni di scelta, ne imposto posizione e dimensione e vi aggiungo il listener (fp) */
		MenuButton creaNuovoCampionato = new MenuButton("Nuovo campionato");
		creaNuovoCampionato.setBounds(w/5,h/2,w/5,h/5);
		creaNuovoCampionato.addActionListener(fp);
		creaNuovoCampionato.setFont(new Font("Trebuchet MS",Font.BOLD,16));
		MenuButton caricaCampionato = new MenuButton("Carica campionato");
		caricaCampionato.setBounds(w*3/5,h/2,w/5,h/5);
		caricaCampionato.addActionListener(fp);
		caricaCampionato.setFont(new Font("Trebuchet MS",Font.BOLD,16));
		MenuButton creaElimina = new MenuButton("Gestione");
		creaElimina.setBounds(w*2/5,h*15/20,w/5,h/10);
		creaElimina.addActionListener(fp);
		creaElimina.setFont(new Font("Trebuchet MS",Font.BOLD,16));
		
		/* Aggiungo gli elementi al pannello */
		add(titolo); //Titolo
		add(creaNuovoCampionato); add(caricaCampionato); add(creaElimina); //Pulsanti modalita'
	}
	
	/** Metodo di inserimento della schermata di caricamento 
	 * <p>
	 * Mostra una schermata bianca con la barra di caricamento e alcune informazioni (scherzose) riguardo alle operazioni che sta svolgendo.<br>
	 * Al termine del caricamento chiude questa finestra e mostra quella del menu gestionale.<br>
	 * 
	 * @param c Il campionato che si sta creando
	 * @param salva Indica se il caricamento è legato ad un campionato giaà esistente o predefinito
	*/
	public void mostraCaricamento(Campionato c,boolean salva) {
		removeAll();
		setBackground(Color.white);
		
		/* Etichetta "CARICAMENTO" */
		JLabel caricamento = new JLabel("CARICAMENTO IN CORSO");
		caricamento.setForeground(new Color(4,66,140));
		caricamento.setFont(new Font("Trebuchet MS",Font.BOLD,24));
		caricamento.setBounds(getWidth()/4,getHeight()*3/10,getWidth()/2,getHeight()/20);
		caricamento.setHorizontalAlignment(SwingConstants.CENTER);
		JSeparator o = new JSeparator(SwingConstants.HORIZONTAL);
		o.setBounds(getWidth()/4,getHeight()*2/5,getWidth()/2,5);
		
		add(caricamento); add(o);
		
		/* Progress bar */
		JProgressBar pb = new JProgressBar(0,100);
		pb.setBounds(getWidth()/4,getHeight()*9/20,getWidth()/2,getHeight()/10);
		pb.setBorder(BorderFactory.createEtchedBorder(Color.black,Color.black));
		pb.setValue(0);
		pb.setForeground(Color.green);
		
		add(pb);
		
		/* Creo e avvio il thread che gestisce il progresso della barra di caricamento */
		Thread t = new Thread(new Runnable() {
			public void run() {
				Random r = new Random();
				for(int i=0;i<=100;i++) {
					pb.setValue(i);
					
					/* Ad ogni ciclo aggiorno la pagina per mostrare i progressi della barra */
					revalidate();
					repaint();
					
					try {Thread.sleep(r.nextInt(100));} catch(InterruptedException e) {}
				}
				
				/* Al termine del caricamento mostro il menu gestionale */
				MenuPrincipale mp = new MenuPrincipale(c,salva); //Apro la finestra di gestione del campionato
				fp.setVisible(false); //Chiudo la finestra corrente
				mp.setVisible(true); //Mostro il menu principale
			}
		});
		t.start();
	}
}
