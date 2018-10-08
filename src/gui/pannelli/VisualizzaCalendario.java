package gui.pannelli;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import gestioneCampionato.Campionato;
import gestioneCampionato.Giornata;
import gui.DynamicLabel;
import gui.bottoni.ConfirmButton;
import gui.bottoni.ServiceButton;
import gui.finestre.MenuPrincipale;

/** Pannello di visualizzazione del calendario
 * <p>
 * Mette a disposizione gli elementi grafici per visualizzare il calendario delle partite.<br>
 * L'utente puo' scegliere tra la visualizzazione del calendario generale (tutte le partite), quindi scegliere quale giornata visualizzare, 
 * oppure scegliere di visualizzare il calendario relativo ad una singola squadra.<br>
 * L'utente può inoltre stampare la visualizzazione corrente del campionato.<br>
 */
@SuppressWarnings("serial")
public class VisualizzaCalendario extends JPanel implements ActionListener{
	/**La finestra in cui e' contenuto il pannello */
	private MenuPrincipale mp; 
	/**Il campionato di cui si mostra il calendario */
	private Campionato c;
	/**Pulsante di ritorno al menu principale */
	private ConfirmButton indietro; 
	/**Pulsante di scelta visualizzazione di tutte giornate */
	private ServiceButton calendarioGiornate; 
	/**Pulsante di scelta visualizzazione giornata specifica */
	private ServiceButton calendarioGiornata; 
	/**Pulsante di scelta visualizzazione squadra specifica */
	private ServiceButton calendarioSquadra; 
	/**Combo box di scelta delle squadre (tramite nome) */
	private JComboBox<String> squadre; 
	/**Combo box di scelta delle squadre (tramite nome) */
	private JComboBox<Integer> giornate; 
	/** Scroll pane dove vengono inserite le giornate del campionato */
	private JScrollPane sp;
	/**Pannello che mostra il nome della squadra di cui si visualizza il risultato */
	private JPanel nomeSquadra;
	/**Pannello che mostra il numero della giornata di cui si visualizza il risultato */
	private JPanel numeroGiornata;
	/**Numero delle squadre partecipanti al campionato */
	private int numSquadre; 
	/**Lunghezza della finestra MenuPrincipale */
	private int w; 
	/**Altezza della finestra MenuPrincipale */
	private int h; 
	
	/** Costruttore
	 * 
	 * @param mp La finestra in cui e' contenuto il pannello. 
	 * @param c Il campionato in corso.
	 */
	public VisualizzaCalendario(MenuPrincipale mp,Campionato c) {
		super();
		this.mp = mp;
		this.c = c;
		numSquadre = c.getNumSquadre();
		
		setLayout(null); // Imposto il layout manager nullo per poter inserire manualmente la posizione di ogni oggetto 
		
		setBackground(Color.white); // Imposto il colore di sfondo del pannello
		
		/* Calcolo le dimensioni (in pixel) del pannello per il posizionamento degli oggetti al suo interno */
		w = mp.getWidth(); 
		h = mp.getHeight();
		
		/* Creo l'oggetto titolo */
		Titolo titolo = new Titolo("Calendario e risultati",getBackground(),new Color(4,66,140),false);
		titolo.setBounds(0,h/40,w,h/10);
		
		/* Creo un separatore da inserire tra il titolo e gli altri elementi */
		JSeparator o = new JSeparator(SwingConstants.HORIZONTAL);
		o.setBounds(w/10,h/8,w*8/10,5);
		
		/* Label modalità di visualizzazione */
		JLabel visMod = new JLabel("VISUALIZZAZIONE"); 
		visMod.setFont(new Font("Trebuchet MS",Font.BOLD,16));
		visMod.setForeground(new Color(4,66,140));
		visMod.setHorizontalAlignment(SwingConstants.CENTER);
		visMod.setBounds(w*3/5,h*5/32,w*11/30,h/20);
		JSeparator visSep = new JSeparator(SwingConstants.HORIZONTAL);
		visSep.setBounds(w*3/5,h*7/32,w*11/30,50);
		
		/* Creo i tasti di scelta della modalità di visualizzazione */
		calendarioGiornate = new ServiceButton("Tutte le giornate");
		calendarioGiornate.setBounds(w*3/5,h/4,w*3/20,h/15);
		calendarioGiornate.addActionListener(this);
		calendarioGiornata = new ServiceButton("Giornata");
		calendarioGiornata.setBounds(w*3/5,h*3/8,w*3/20,h/15);
		calendarioGiornata.addActionListener(this);
		calendarioSquadra = new ServiceButton("Squadra");
		calendarioSquadra.setBounds(w*3/5,h/2,w*3/20,h/15);
		calendarioSquadra.addActionListener(this);
		
		/* Creo la combo box per la scelta della giornata di cui visualizzare il calendario */
		giornate = new JComboBox<Integer>();
		for(int i=0;i<(numSquadre-1)*2;i++) giornate.addItem(i+1);
		giornate.setBounds(w*4/5,h*3/8,w/20,h/15);
		
		/* Creo la combo box per la scelta della squadra di cui visualizzare il calendario */
		squadre = new JComboBox<String>();
		for(int i=0;i<numSquadre;i++) squadre.addItem(c.getSquadraAtIndex(i).getNome());
		squadre.setBounds(w*4/5,h/2,w/6,h/15);
		
		/* Creo il button di ritorno al menu */
		indietro = new ConfirmButton("Torna al menu");
		indietro.setBounds(w*3/5,h*13/16,w/5,h/15);
		indietro.addActionListener(this);
		
		numeroGiornata = new JPanel();
		nomeSquadra = new JPanel();
		
		/* Aggiungo gli elementi al pannello */
		add(titolo); add(o); //Titolo e separatore
		add(visMod); add(visSep); //Visualizzazione 
		add(indietro); add(calendarioGiornate); add(calendarioGiornata); add(calendarioSquadra); //Pulsanti modalità di visualizzazione
		add(giornate); add(squadre); //Combo box giornate e squadre 
		
		visualizzaGiornate();
	}
	
	/** Metodo di visualizzazione di tutte le giornate */
	private void visualizzaGiornate() {
		JPanel visuale = new JPanel();
		visuale.setLayout(new BoxLayout(visuale,BoxLayout.Y_AXIS));
		visuale.setBackground(Color.white);
		
		for(int i=0;i<(numSquadre-1)*2;i++) {
			/* Etichetta numero giornata */
			JPanel giornata = new JPanel();
			giornata.setPreferredSize(new Dimension(w*39/80,h/20));
			giornata.setBackground(new Color(4,66,140));
			DynamicLabel numero = new DynamicLabel("Giornata " + (i+1),true);
			numero.setForeground(Color.white);
			giornata.add(numero,BorderLayout.CENTER);
			visuale.add(giornata);
			
			/* Partite e risultati giornata */
			if(i<numSquadre-1) {
				Giornata g = c.getCalendario().getGiornata(i);
				for(int j=0;j<numSquadre/2;j++) {
					PannelloPartita pp = new PannelloPartita(g,j,true,w*39/80,h/20);
					pp.setPreferredSize(new Dimension(w*39/80,h/20));
					visuale.add(pp);
				}
			}
			else {
				Giornata g = c.getCalendario().getGiornata(i-(numSquadre-1));
				for(int j=0;j<numSquadre/2;j++) {
					PannelloPartita pp = new PannelloPartita(g,j,false,w*39/80,h/20);
					pp.setPreferredSize(new Dimension(w*39/80,h/20));
					visuale.add(pp);
				}
			}
		}
		
		sp = new JScrollPane(visuale,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setBounds(w/16,h*5/32,w/2,h*23/32);
		sp.setBackground(Color.white);
		sp.setBorder(null);
		add(sp);
	}
	
	/** Metodo di visualizzazione di una singola giornata 
	 * 
	 * @param n Numero dela giornata da visualizzare
	 */
	private void visualizzaGiornata(int n) {
		JPanel visuale = new JPanel();
		visuale.setLayout(new BoxLayout(visuale,BoxLayout.Y_AXIS));
		visuale.setBackground(Color.white);
		
		/* Etichetta numero giornata */
		numeroGiornata = new JPanel();
		numeroGiornata.setBounds(w/16,h*5/32,w/2,h/20);
		numeroGiornata.setBackground(new Color(4,66,140));
		DynamicLabel numero = new DynamicLabel("Giornata " + (n+1),true);
		numero.setForeground(Color.white);
		numeroGiornata.add(numero,BorderLayout.CENTER);
		add(numeroGiornata);
	
		/* Partite e risultati giornata */
		if(n<numSquadre-1) {
			Giornata g = c.getCalendario().getGiornata(n);
			for(int j=0;j<numSquadre/2;j++) {
				PannelloPartita pp = new PannelloPartita(g,j,true,w*39/80,h/20);
				pp.setPreferredSize(new Dimension(w*39/80,h/20));
				visuale.add(pp);
			}
		}
		else {
			Giornata g = c.getCalendario().getGiornata(n-(numSquadre-1));
			for(int j=0;j<numSquadre/2;j++) {
				PannelloPartita pp = new PannelloPartita(g,j,false,w*39/80,h/20);
				pp.setPreferredSize(new Dimension(w*39/80,h/20));
				visuale.add(pp);
			}
		}
				
		sp = new JScrollPane(visuale,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setBounds(w/16,h*7/32,w/2,h*21/32);
		sp.setBackground(Color.white);
		sp.setBorder(null);
		add(sp);
	}
	
	/** Metodo di visualizzazione del calendario di una singola squadra 
	 * 
	 * @param squadra Nome della squadra di cui di vuole visualizzare il campionato
	 */
	private void visualizzaSquadra(String squadra) {
		JPanel visuale = new JPanel();
		visuale.setLayout(new BoxLayout(visuale,BoxLayout.Y_AXIS));
		visuale.setBackground(Color.white);
		
		/* Etichetta squadra visualizzata */
		nomeSquadra = new JPanel();
		nomeSquadra.setBounds(w/16,h*5/32,w/2,h/20);
		nomeSquadra.setBackground(new Color(4,66,140));
		DynamicLabel nome = new DynamicLabel("Calendario di " + squadra.toUpperCase(),true);
		nome.setForeground(Color.white);
		nomeSquadra.add(nome,BorderLayout.CENTER);
		add(nomeSquadra);
		
		for(int i=0;i<(numSquadre-1)*2;i++) {
			Giornata g = null;
			PannelloPartita pp = null;
			/* Partite e risultati giornata */
			if(i<numSquadre-1) {
				g = c.getCalendario().getGiornata(i);
				pp = new PannelloPartita(g,true,squadra,w*39/80,h/20);
			}
			else {
				g = c.getCalendario().getGiornata(i-(numSquadre-1));
				pp = new PannelloPartita(g,false,squadra,w*39/80,h/20);
			}
					
			pp.setPreferredSize(new Dimension(w*39/80,h/20));
			visuale.add(pp);
		}
		
		sp = new JScrollPane(visuale,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setBounds(w/16,h*7/32,w/2,h*21/32);
		sp.setBackground(Color.white);
		sp.setBorder(null);
		add(sp);
	}
	
	/** Metodo di gestione degli eventi generati dai pulsanti del menu */
	@Override
	public void actionPerformed(ActionEvent e) {
		/* Click pulsante "Indietro" */
		if(e.getSource().equals(indietro)) mp.ritornaAlMenu(this);
		
		/* Click pulsante "Tutte le giornate" */
		if(e.getSource().equals(calendarioGiornate)) {
			remove(numeroGiornata);
			remove(nomeSquadra);
			remove(sp);
			visualizzaGiornate();
		}
		
		/* Click pulsante "Giornata" */
		if(e.getSource().equals(calendarioGiornata)) {
			remove(nomeSquadra);
			remove(sp);
			visualizzaGiornata(giornate.getSelectedIndex());
		}
		
		/* Click pulsante "Squadra" */
		if(e.getSource().equals(calendarioSquadra)) {
			remove(numeroGiornata);
			remove(sp);
			visualizzaSquadra((String)squadre.getSelectedItem());
		}
		
		revalidate();
		repaint();
	}
}