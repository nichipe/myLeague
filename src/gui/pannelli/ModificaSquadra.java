package gui.pannelli;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import gestioneCampionato.*;
import gui.DynamicLabel;
import gui.bottoni.ConfirmButton;
import gui.finestre.MenuPrincipale;

/** Pannello di scelta della squadra da modificare */
@SuppressWarnings("serial")
public class ModificaSquadra extends JPanel implements ActionListener{
	/**Il campionato in corso */
	private Campionato c; 
	/**La finestra in cui e' contenuto il pannello */
	private MenuPrincipale mp; 
	/**Gruppo a cui appartengono tutti i radio button, per sapere quale di essi e' selezionato */
	private ButtonGroup bg; 
	/**Pulsante di ritorno al menu principale */
	private ConfirmButton annulla; 
	
	/** Costruttore
	 * 
	 * @param mp La finestra in cui e' contenuto il pannello.
	 * @param c Il campionato in corso.
	 */
	public ModificaSquadra(MenuPrincipale mp,Campionato c) {
		super();
		this.mp = mp;
		this.c = c; 
		
		setLayout(null); // Imposto il layout manager nullo per poter inserire manualmente la posizione di ogni oggetto 
		
		setBackground(Color.white); // Imposto il colore di sfondo del pannello
		
		/* Calcolo le dimensioni (in pixel) del pannello per il posizionamento degli oggetti al suo interno */
		int w = mp.getWidth(); 
		int h = mp.getHeight();
		
		/* Creo l'oggetto titolo */
		Titolo titolo = new Titolo("Seleziona squadra da modificare",getBackground(),new Color(4,66,140),false);
		titolo.setBounds(0,h/20,w,h/10);
		
		/* Creo un separatore da inserire tra il titolo e gli altri elementi */
		JSeparator o = new JSeparator(SwingConstants.HORIZONTAL);
		o.setBounds(w/10,h*3/20,w*8/10,5);
		
		/* Creo i label per le colonne di dati delle squadre */
		//Semipagina sx
		JLabel nomeSquadra = new JLabel("NOME SQUADRA");
		nomeSquadra.setFont(new Font("Trebuchet MS",Font.BOLD,16));
		nomeSquadra.setBounds(w/20,h/5,w/5,h/20);
		add(nomeSquadra);
		
		JLabel sedeSquadra = new JLabel("SEDE");
		sedeSquadra.setFont(new Font("Trebuchet MS",Font.BOLD,16));
		sedeSquadra.setBounds(w*3/14,h/5,w/5,h/20);
		add(sedeSquadra);
		
		//Semipagina dx
		JLabel nomeSquadra2 = new JLabel("NOME SQUADRA");
		nomeSquadra2.setFont(new Font("Trebuchet MS",Font.BOLD,16));
		nomeSquadra2.setBounds(w*11/20,h/5,w/5,h/20);
		add(nomeSquadra2);
		
		JLabel sedeSquadra2 = new JLabel("SEDE");
		sedeSquadra2.setFont(new Font("Trebuchet MS",Font.BOLD,16));
		sedeSquadra2.setBounds(w*5/7,h/5,w/5,h/20);
		add(sedeSquadra2);
		
		/* Creo l'elenco delle squadre con radio button per scegliere quale modificare */
		int offset = 0, i, cordXCodice = w/40, cordXNome = w/20, cordXSede = w*3/14;
		JRadioButton squadra; //Radio button per selezionare la squadra da modificare
		DynamicLabel nome; //Label per mostrare il nome della squadra
		DynamicLabel sede; //Label per mostrare la sede della squadra
		bg = new ButtonGroup();
		for(i=0;i<c.getNumSquadre();i++) {
			squadra = new JRadioButton();
			squadra.setBounds(cordXCodice,(h/4)+offset,h/25,h/20);
			squadra.setBackground(Color.white);
			if(i==0) squadra.setSelected(true);
			bg.add(squadra);
			squadra.setActionCommand("S"+(char)(97+i));
		
			add(squadra);
			
			//Nome squadra
			nome = new DynamicLabel(c.getSquadraAtIndex(i).getNome(),false);
			nome.setBounds(cordXNome,(h/4)+offset,w/10,h/20);
			//Sede squadra
			sede = new DynamicLabel(c.getSquadraAtIndex(i).getSede(),false);
			sede.setBounds(cordXSede,(h/4)+offset,w/10,h/20);
			
			add(nome); add(sede);
			
			/* Distanza tra l'inizio di una riga e l'inizio della successiva */
			if(c.getNumSquadre()<=10) offset += h/10;
			else offset += h/c.getNumSquadre();	
			
			if(i == (c.getNumSquadre()/2)-1) {	//Quando arrivo a meta' squadre, proseguo nella semipagina di destra
				cordXCodice = w*21/40;
				cordXNome = w*11/20;
				cordXSede = w*5/7;
				offset = 0;
			}
		}
		
		/* Creo il bottone di conferma dei dati e quello di ritorno al menu di gestione */
		ConfirmButton modifica = new ConfirmButton("Modifica");
		modifica.setBounds(w*3/5,h*4/5,w/5,h/10);
		modifica.addActionListener(this);
		annulla = new ConfirmButton("Annulla");
		annulla.setBounds(w/5,h*4/5,w/5,h/10);
		annulla.addActionListener(this);
		
		/* Aggiungo gli elementi al pannello */
		add(titolo); add(o); //Titolo e separatore
		add(modifica); add(annulla); //Pulsanti funzione
	}

	/** Metodo di gestione degli eventi generati dai pulsanti del pannello */
	@Override
	public void actionPerformed(ActionEvent e) {
		/* Click pulsante "Annulla" */
		if(e.getSource().equals(annulla)) { mp.ritornaAlMenu(this); }
		
		/* Click pulsante "Modifica" */
		else {
			int index = bg.getSelection().getActionCommand().charAt(1)-97; //Indice della squadra da modificare
			mp.modificaSquadra(c.getSquadraAtIndex(index)); //Mostro il pannello di modifica della squadra
		}
	}
}



