package gui.pannelli;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import gestioneCampionato.Campionato;
import gestioneCampionato.Giornata;
import gui.DynamicLabel;
import gui.bottoni.ConfirmButton;
import gui.bottoni.ServiceButton;
import gui.finestre.MenuPrincipale;

/** Pannello di inserimento dei risultati
 * <p>
 * Pannello che mette a disposizione dell'utente gli elementi grafici e gli strumenti per inserire i risultati degli incontri.
 */
public class InserimentoRisultati extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L; //Versione dell'oggetto
	
	/**Il calendario delle partite */
	private Campionato c; 
	/**La finestra in cui e' inserito il pannello */
	private MenuPrincipale mp; 
	/**La giornata di cui si stanno inserendo i risultati */	
	private Giornata g; 
	/**Pulsante di conferma aggiornamento dei risultati della giornata corrente */
	private ConfirmButton aggiorna; 
	/**Pulsante di conferma eliminazione dei risultati */
	private ConfirmButton elimina; 
	/**Pulsante di ritorno al menu */
	private ConfirmButton esci; 
	/**Pulsante di salto alla prossima giornata da aggiornare */
	private ConfirmButton prossima; 
	/**Pulsante di salto alla precedente giornata da aggiornare */
	private ConfirmButton precedente; 
	/**Pulsante che permette la visualizzazione dei risultati secondari */
	private ServiceButton secondario; 
	/**Pulsante per il salto alla giornata desiderata */
	private ConfirmButton visualizzaGiornata;
	/**Label del numero della giornata di cui si stanno modificando i risultati */
	private JLabel gg; 
	/**Nomi delle squadre di casa */
	private JLabel[] squadraInCasa; 
	/**Nomi delle squadre in trasferta */
	private JLabel[] squadraInTrasferta; 
	/**Loghi attualmente inseriti nel pannello */
	private JLabel[] loghi; 
	/**Combo box per l'inserimento dei risultati in casa */
	private JComboBox<String>[] risultatiCasa; 
	/**Combo box per l'inserimento dei risultati in trasferta */
	private JComboBox<String>[] risultatiTrasferta; 
	/**Combo box per l'inserimento dei risultati secondari (mete,punti) in casa */
	private JComboBox<String>[] risultatiSecondariCasa; 
	/**Combo box per l'inserimento dei risultati secondari (mete,punti) in trasferta */
	private JComboBox<String>[] risultatiSecondariTrasferta; 
	/**Combo box di scelta delle squadre (tramite nome) */
	private JComboBox<Integer> giornate; 
	/**Percorsi dei loghi delle squadre di casa nella giornata visualizzata */
	private String[] loghiCasa; 
	/**Percorsi dei loghi delle squadre in trasferta nella giornata visualizzata */
	private String[] loghiTrasferta; 
	/**Numero delle squadre partecipanti al torneo */
	private int numSquadre; 
	/**Lunghezza della finestra mp */
	private int w;
	/**Altezza della finestra mp */
	private int h; 
	/**Altezza degli elementi del pannello */
	private int altezzaElementi; 
	/**Indice di giornata di cui si stanno modificando i risultati */
	private int numeroGiornata; 
	/**Indica se sono mostrati i risultati */
	private boolean risultati; 
	/**Indica se la giornata di cui si stanno modificando i risultati e' andata */
	private boolean and; 
	/**Indica se sono attualmente inseriti nel pannello dei loghi */
	private boolean loghiInseriti; 
	
	/** Costruttore
	 * 
	 * @param mp La finestra in cui e' contenuto il pannello.
	 * @param c Il campionato in corso.
	 */
	public InserimentoRisultati(MenuPrincipale mp,Campionato c) {
		super();
		this.c = c;
		this.mp = mp;
		numSquadre = c.getNumSquadre();
		numeroGiornata = 0;
		risultati = true;
		
		setLayout(null); // Imposto il layout manager nullo per poter inserire manualmente la posizione di ogni oggetto 
		
		setBackground(Color.white); // Imposto il colore di sfondo del pannello
		
		/* Calcolo le dimensioni del pannello per il posizionamento degli oggetti al suo interno */
		w = mp.getWidth(); 
		h = mp.getHeight();
		
		/* Creo l'oggetto titolo */
		Titolo titolo = new Titolo("Inserimento risultati",getBackground(),new Color(4,66,140),false);
		titolo.setBounds(0,h/40,w,h/10);
		
		/* Creo un separatore da inserire tra il titolo e gli altri elementi */
		JSeparator o = new JSeparator(SwingConstants.HORIZONTAL);
		o.setBounds(w/10,h/8,w*8/10,5);
		
		/* Creo i label e i separatori per la stampa delle informazioni sulla giornata */
		JLabel risultati = new JLabel("RISULTATO");
		risultati.setFont(new Font("Trebuchet MS",Font.BOLD,16));
		risultati.setForeground(new Color(4,66,140));
		risultati.setHorizontalAlignment(SwingConstants.CENTER);
		risultati.setBounds(w*25/40,h*5/32,w/6,h/20);
		gg = new JLabel();
		gg.setFont(new Font("Trebuchet MS",Font.BOLD,16));
		gg.setForeground(new Color(4,66,140));
		gg.setHorizontalAlignment(SwingConstants.CENTER);
		gg.setBounds(w/20,h*5/32,w*34/80,h/20);
		JSeparator risSeparator = new JSeparator(SwingConstants.HORIZONTAL);
		risSeparator.setBounds(w*25/40,h*13/64,w/6,50);
		JSeparator gSeparator = new JSeparator(SwingConstants.HORIZONTAL);
		gSeparator.setBounds(w/20,h*13/64,w*34/80,50);
		
		/* Calcolo l'altezza degli elementi (nome squadre e risultati) */
		altezzaElementi = h*48/(32*(2*numSquadre-1));
		if(altezzaElementi > h/10) altezzaElementi = h/10;
		
		/* Creo il pulsante di ritorno al menu, di conferma dei risultati e di salto tra le giornate */
		prossima = new ConfirmButton(">");
		prossima.setBounds(w*14/20,h*67/80,w/20,h/20);
		prossima.addActionListener(this);
		precedente = new ConfirmButton("<");
		precedente.setBounds(w*13/20,h*67/80,w/20,h/20);
		precedente.addActionListener(this);
		precedente.setVisible(false);
		visualizzaGiornata = new ConfirmButton("Giornata");
		visualizzaGiornata.setBounds(w*33/40,h*13/16,w/10,h/20);
		visualizzaGiornata.addActionListener(this);
		esci = new ConfirmButton("Torna al menu");
		esci.setBounds(w/10,h*13/16,w/5,h/10);
		esci.addActionListener(this);
		aggiorna = new ConfirmButton("Aggiorna risultati");
		aggiorna.setBounds(w*2/5,h*13/16,w/5,h/20);
		aggiorna.addActionListener(this);
		elimina = new ConfirmButton("Elimina risultati");
		elimina.setBounds(w*2/5,h*69/80,w/5,h/20);
		elimina.addActionListener(this);
		
		/* Creo la combo box per la scelta della giornata di cui inserire i risultati */
		giornate = new JComboBox<Integer>();
		for(int i=0;i<(numSquadre-1)*2;i++) giornate.addItem(i+1);
		giornate.setBounds(w*17/20,h*69/80,w/20,h/20);
		
		/* Creo la struttura per la visualizzazione delle partite */
		numeroGiornata = 0;
		creaStrutturaPartite();
		creaStrutturaRisultati();
		stampaGiornata();
		
		/* Aggiungo gli elementi al pannello */
		add(titolo); add(o); //Titolo e separatore
		add(risultati); add(gg); //Etichetta risultato e giornata
		add(risSeparator); add(gSeparator); //Separatori
		add(aggiorna); add(elimina); add(esci); add(prossima); add(precedente); add(visualizzaGiornata); //Pulsanti funzione
		add(giornate); //Combo box giornate
	}
	
	/** Metodo che crea la struttura per l'inserimento delle partite */
	private void creaStrutturaPartite() {
		/* Definisco i vettori dei loghi delle squadre */
		loghiCasa = new String[numSquadre/2];
		loghiTrasferta = new String[numSquadre/2]; 
		loghi = new JLabel[numSquadre];
		loghiInseriti = false;
		
		int offset = 0;
		squadraInCasa = new JLabel[numSquadre/2]; //Nomi delle squadre di casa
		squadraInTrasferta = new JLabel[numSquadre/2]; //Nomi delle squadre in trasferta
		for(int i=0;i<numSquadre/2;i++) {
			DynamicLabel casa = new DynamicLabel(false);
			squadraInCasa[i] = casa;
			DynamicLabel trasferta = new DynamicLabel(false);
			squadraInTrasferta[i] = trasferta;
			JLabel separatore = new JLabel(" - ");
			
			/* Squadra in casa */
			casa.setBounds(w/20,(h*7/32)+offset,w/5,h/20);
			casa.setHorizontalAlignment(SwingConstants.RIGHT);
			/*Squadra in trasferta */
			trasferta.setBounds(w*11/40,(h*7/32)+offset,w/5,h/20);
			/* Separatore */
			separatore.setBounds(w/4,(h*7/32)+offset,w/40,h/20);
			separatore.setFont(new Font("Trebuchet MS",Font.BOLD,16));
			separatore.setHorizontalAlignment(SwingConstants.CENTER);
			
			add(casa); add(trasferta); add(separatore);
			
			offset += altezzaElementi*3/2;
		}
	}
	
	/** Metodo di creazione della struttura dei risultati
	 * <p>
	 * Inserisce nel pannello gli strumenti per poter inserire i risultati delle partite visualizzate.
	 */
	@SuppressWarnings("unchecked") //Elimino il warning per la mancata specifica del tipo di oggetti che popoleranno la combo box, dato che lo faro' in seguito.
	private void creaStrutturaRisultati() {
		int offset = 0;
		risultatiCasa = new JComboBox[numSquadre/2];
		risultatiTrasferta = new JComboBox[numSquadre/2];
		risultatiSecondariCasa = new JComboBox[numSquadre/2];
		risultatiSecondariTrasferta = new JComboBox[numSquadre/2];
		
		for(int i=0;i<numSquadre/2;i++) {
			JComboBox<String> inCasa = new JComboBox<String>();
			inCasa.setBounds(w*5/8,(h*7/32)+offset,w/16,h/20);
			risultatiCasa[i] = inCasa;
			JComboBox<String> inTrasferta = new JComboBox<String>();
			inTrasferta.setBounds(w*35/48,(h*7/32)+offset,w/16,h/20);
			risultatiTrasferta[i] = inTrasferta;
			JLabel separatore = new JLabel(" - ");
			separatore.setBounds(w*11/16,(h*7/32)+offset,w/24,h/20);
			separatore.setHorizontalAlignment(SwingConstants.CENTER);
			separatore.setFont(new Font("Trebuchet MS",Font.BOLD,16));
			
			/* Imposto il punteggio massimo a 100 nel caso di calcio e rugby e a 3 nel caso della pallavolo */
			int punteggioMassimo = 100;
			if(c.getSport()==2) punteggioMassimo = 4;
			
			for(int j=0;j<punteggioMassimo;j++) {
				inCasa.addItem(String.valueOf(j));
				inTrasferta.addItem(String.valueOf(j));
			}
			
			add(inCasa); add(inTrasferta); add(separatore);
			
			offset += altezzaElementi*3/2;
		}
		
		/* Creo la struttura per i risultati secondari di pallavolo e rugby */
		if(c.getSport()!=1) {
			offset = 0;
			for(int i=0;i<numSquadre/2;i++) {
				JComboBox<String> secondarioInCasa = new JComboBox<String>();
				secondarioInCasa.setVisible(false);
				secondarioInCasa.setBounds(w*5/8,(h*7/32)+offset,w/16,altezzaElementi);
				risultatiSecondariCasa[i] = secondarioInCasa;
				JComboBox<String> secondarioInTrasferta = new JComboBox<String>();
				secondarioInTrasferta.setVisible(false);
				secondarioInTrasferta.setBounds(w*35/48,(h*7/32)+offset,w/16,altezzaElementi);
				risultatiSecondariTrasferta[i] = secondarioInTrasferta;
				
				/* Imposto il punteggio massimo a 150 punti nel caso della pallavolo e 20 mete nel caso del rugby */
				int punteggioMassimo=0;
				if(c.getSport()==2) punteggioMassimo = 150;
				if(c.getSport()==3) punteggioMassimo = 20;
				
				for(int j=0;j<punteggioMassimo;j++) {
					secondarioInCasa.addItem(String.valueOf(j));
					secondarioInTrasferta.addItem(String.valueOf(j));
				}
				
				add(secondarioInCasa); add(secondarioInTrasferta);
				
				offset += altezzaElementi*3/2;
			}
			
			secondario = new ServiceButton("PUNTEGGI");
			secondario.setBounds(w*13/16,h*5/32,w/6,h/20);
			secondario.addActionListener(this);
			
			add(secondario);
		}
	}

	/** Metodo di stampa delle partite della giornata */
	protected void stampaGiornata() {
		if(numeroGiornata < ((numSquadre-1)*2)-1) prossima.setVisible(true);
		if(numeroGiornata>0) precedente.setVisible(true);
		
		/* Giornata d'andata */
		if(numeroGiornata<numSquadre-1) {
			g = c.getCalendario().getGiornata(numeroGiornata); 
			gg.setText("GIORNATA " + (numeroGiornata+1) + " ANDATA");
			and = true;
		}
		/* Giornata di ritorno */
		else {
			g = c.getCalendario().getGiornata(numeroGiornata-(numSquadre-1)); 
			gg.setText("GIORNATA " + (numeroGiornata-(numSquadre-1)+1) + " RITORNO");
			and = false;
		}
		
		/* Inserimento nomi delle squadre */
		for(int i=0;i<numSquadre/2;i++) {
			if(numeroGiornata<numSquadre-1) {
				squadraInCasa[i].setText(g.getCasaAtIndex(i).getNome());
				loghiCasa[i] = g.getCasaAtIndex(i).getLogo();
				squadraInTrasferta[i].setText(g.getTrasfertaAtIndex(i).getNome());
				loghiTrasferta[i] = g.getTrasfertaAtIndex(i).getLogo();
			}
			else {
				squadraInCasa[i].setText(g.getTrasfertaAtIndex(i).getNome());
				loghiCasa[i] = g.getTrasfertaAtIndex(i).getLogo();
				squadraInTrasferta[i].setText(g.getCasaAtIndex(i).getNome());
				loghiTrasferta[i] = g.getCasaAtIndex(i).getLogo();
			}
		}
		
		/* Se gia' presenti inserisce nei combo box i valori relativi ai risultati */
		if(and && g.isRisultatiAndata()) mostraRisultati(g);
		if(!and && g.isRisultatiRitorno()) mostraRisultati(g);
		if(!g.isRisultatiAndata() && !g.isRisultatiRitorno()) azzeraRisultati();
		
		stampaLoghi(); //inserisce i loghi delle squadre
		
		/* Funzioni di aggiornamento degli oggetti inseriti nel pannello */
		revalidate(); repaint();
	}
	
	/** Metodo utilizzato per riportare al default(0) tutti i combo box dei risultati */
	private void azzeraRisultati() {
		for(int i=0;i<numSquadre/2;i++) {
			risultatiCasa[i].setSelectedIndex(0);
			risultatiTrasferta[i].setSelectedIndex(0);
		}
	}
	
	/** Metodo di inserimento risultati 
	 * <p>
	 * Alla conferma dell'utente, recupera i valori nei combo box e, se sono consistenti, li riporta nel corretto vettore nella corretta 
	 * giornata.
	 */
	private void inserisciRisultati() {
		if(!controlloRisultati()) {
			/* Giornata di andata */
			if(and) {
				for(int i=0;i<numSquadre/2;i++) {
					g.setRisultatoCasaAndata(risultatiCasa[i].getSelectedIndex(),i);
					g.setRisultatoTrasfertaAndata(risultatiTrasferta[i].getSelectedIndex(),i);
				}
				g.setRisultatiAndata();
			}
			
			/* Giornata di ritorno */
			else {
				for(int i=0;i<numSquadre/2;i++) {
					g.setRisultatoCasaRitorno(risultatiCasa[i].getSelectedIndex(),i);
					g.setRisultatoTrasfertaRitorno(risultatiTrasferta[i].getSelectedIndex(),i);
				}
				g.setRisultatiRitorno();
			}
			
			/* Risultati secondari */
			if(c.getSport()!=1) {
				/* Giornata di andata */
				if(and) {
					for(int i=0;i<numSquadre/2;i++) {
						g.setRisultatoSecondarioCasaAndata(risultatiSecondariCasa[i].getSelectedIndex(),i);
						g.setRisultatoSecondarioTrasfertaAndata(risultatiSecondariTrasferta[i].getSelectedIndex(),i);
					}
				}
				
				/* Giornata di ritorno */
				else {
					for(int i=0;i<numSquadre/2;i++) {
						g.setRisultatoSecondarioCasaRitorno(risultatiSecondariCasa[i].getSelectedIndex(),i);
						g.setRisultatoSecondarioTrasfertaRitorno(risultatiSecondariTrasferta[i].getSelectedIndex(),i);
					}
				}
			}
			
			JOptionPane.showMessageDialog(this, "Risultati aggiornati correttamente.","",JOptionPane.INFORMATION_MESSAGE);
			mp.setCalcola();
			stampaGiornata();
		}
		
		/* Funzioni di aggiornamento degli oggetti inseriti nel pannello */
		revalidate(); repaint();
	}
	
	/** Metodo di controllo della correttezza dei risultati
	 * <p>
	 * Se nella pallavolo è presente si cerca di inserire un 3-3 oppure una partita con entrambe le squadre a meno di 3 punti (risultati non 
	 * validi) colora il risultato in questione di rosso e rifiuta l'inserimento dei risultati.
	 * 
	 * @return 'true' se e' stato trovato un errore nei risultati, altrimenti 'false'.
	 */
	private boolean controlloRisultati() {
		if(c.getSport()!=2) return false; //Se lo sport non e' la pallavolo non ci puo' essere errore
		boolean errore = false; //Indica se e' presente un errore
		for(int i=0;i<numSquadre/2;i++) {
			/* 3-3 non puo' accadere nella pallavolo */	
			if(risultatiCasa[i].getSelectedIndex() == 3 && risultatiTrasferta[i].getSelectedIndex() == 3) {
					risultatiCasa[i].setBorder(BorderFactory.createLineBorder(Color.red));
					risultatiTrasferta[i].setBorder(BorderFactory.createLineBorder(Color.red));
					errore = true;
			}
			
			/* Una delle due squadre deve aver fatto 3 punti */	
			if(risultatiCasa[i].getSelectedIndex() != 3 && risultatiTrasferta[i].getSelectedIndex() != 3) {
					risultatiCasa[i].setBorder(BorderFactory.createLineBorder(Color.red));
					risultatiTrasferta[i].setBorder(BorderFactory.createLineBorder(Color.red));
					errore = true;
			}
		}
		
		return errore;
	}
	
	/** Metodo di stampa dei risultati
	 * <p>
	 * Inserisce nei box i valori corrispondenti ai risultati nel caso in cui per la giornata corrente siano già presenti.
	 * 
	 * @param g La giornata che si sta visualizzando
	 */
	private void mostraRisultati(Giornata g) {
		if(and) for(int i=0;i<numSquadre/2;i++) {
				risultatiCasa[i].setSelectedIndex(g.getRisultatoCasaAndata(i));
				risultatiTrasferta[i].setSelectedIndex(g.getRisultatoTrasfertaAndata(i));
			}
		else for(int i=0;i<numSquadre/2;i++) {
				risultatiCasa[i].setSelectedIndex(g.getRisultatoTrasfertaRitorno(i));
				risultatiTrasferta[i].setSelectedIndex(g.getRisultatoTrasfertaAndata(i));
			}
		
		/* Funzioni di aggiornamento degli oggetti inseriti nel pannello */
		revalidate(); repaint();
	}
	
	/** Metodo di eliminazione dei risultati
	 * <p>
	 * Elimina tutti i risultati del calendario, ponendo a false i rispettivi valori booleani in tutte le giornate.
	 */
	private void eliminaRisultati() {
		Giornata g;
		for(int i=0;i<numSquadre-1;i++) {
			g = c.getCalendario().getGiornata(i);
			g.resetRisultatiAndata();
			g.resetRisultatiRitorno();
		}
		
		mp.setCalcola(); //Chiedo il ricalcolo della classifica
		
		/* Funzioni di aggiornamento degli oggetti inseriti nel pannello */
		revalidate(); repaint();
	}
	
	/** Metodo di stampa dei loghi
	 * <p>
	 * Inserisce di fianco ad ogni squadra il suo logo.
	 */
	private void stampaLoghi() {
		if(loghiInseriti) rimuoviLoghi(); //Prima di inserire i loghi delle squadre attualmente visualizzate, rimuovo quelli gia' presenti se ci sono
		
		int offset = 0, indiceLoghi = 0;
		
		ImageIcon logoCasa, logoTrasferta;
		JLabel logo1, logo2;
		Image logo;
		
		/* Ogni logo viene prima trasformato in Image per poterlo ridimensionare e poi inserito in un JLabel per poterlo inserire nel pannello */
		for(int i=0;i<numSquadre/2;i++,indiceLoghi++) {
			logoCasa = new ImageIcon(loghiCasa[i]);
			logoTrasferta = new ImageIcon(loghiTrasferta[i]);
			
			logo = logoCasa.getImage().getScaledInstance(h/20,h/20,0);
			logoCasa.setImage(logo);
			logo = logoTrasferta.getImage().getScaledInstance(h/20,h/20,0);
			logoTrasferta.setImage(logo);
			
			logo1 = new JLabel();
			logo1.setIcon(logoCasa);
			logo1.setBounds(w/10-altezzaElementi,(h*7/32)+offset,h/20,h/20);
			logo2 = new JLabel();
			logo2.setIcon(logoTrasferta);
			logo2.setBounds(w*17/40,(h*7/32)+offset,h/20,h/20);
			
			loghi[indiceLoghi++] = logo1;
			loghi[indiceLoghi] = logo2;
			
			add(logo1); add(logo2);
			
			offset += altezzaElementi*3/2;
		}
	
		if(!loghiInseriti) loghiInseriti = true; //Setto la presenza dei loghi per eliminarli al prossimo cambio di pagina
		
		/* Funzioni di aggiornamento degli oggetti inseriti nel pannello */
		revalidate(); repaint();
	}
	
	/** Metodo di rimozione dei loghi 
	 * <p>
	 * Rimuove tutti i loghi inseriti nel pannello per permettere l'inserimento di altri al cambio della pagina.
	 */
	private void rimuoviLoghi() { 
		for(int i=0;i<numSquadre;i++) {
			remove(loghi[i]); 
			
			/* Funzioni di aggiornamento degli oggetti inseriti nel pannello */
			revalidate(); repaint();
		}
	}
	
	/** Metodo di gestione degli eventi generati dai pulsanti del pannello */
	public void actionPerformed(ActionEvent e) {
		/* Click pulsante "Esci" */
		if(e.getSource().equals(esci)) mp.ritornaAlMenu(this);
		
		/* Click pulsante "Inserisci risultati" */
		if(e.getSource().equals(aggiorna)) {
			inserisciRisultati();
		}
		
		/* Click pulsante "Elimina risultati" */
		if(e.getSource().equals(elimina)) {
			eliminaRisultati();
			stampaGiornata();
			JOptionPane.showMessageDialog(this, "Risultati eliminati correttamente.","",JOptionPane.INFORMATION_MESSAGE);
		}
		
		/* Click pulsanti di salto tra le giornate */
		if(e.getSource().equals(prossima)) {
			if(numeroGiornata<((numSquadre-1)*2)-1) numeroGiornata++;
			if(numeroGiornata==((numSquadre-1)*2)-1) prossima.setVisible(false);
			stampaGiornata();
		}
		if(e.getSource().equals(precedente)){
			if(numeroGiornata>0) numeroGiornata--;
			if(numeroGiornata==0) precedente.setVisible(false);
			stampaGiornata();
		}
		
		/* Click salto ad una giornata precisa */
		if(e.getSource().equals(visualizzaGiornata)) {
			numeroGiornata = giornate.getSelectedIndex();
			stampaGiornata();
		}
		
		/* Click inserimento punteggio secondario */
		if(e.getSource().equals(secondario)) {
			if(risultati) {
				for(int i=0;i<numSquadre/2;i++) {
					risultatiCasa[i].setVisible(false);
					risultatiTrasferta[i].setVisible(false);
					risultatiSecondariCasa[i].setVisible(true);
					risultatiSecondariTrasferta[i].setVisible(true);
				}
				risultati = false;
				if(c.getSport()==2) secondario.setText("PUNTEGGI");
				if(c.getSport()==3) secondario.setText("METE");
			}
			else {
				for(int i=0;i<numSquadre/2;i++) {
					risultatiCasa[i].setVisible(true);
					risultatiTrasferta[i].setVisible(true);
					risultatiSecondariCasa[i].setVisible(false);
					risultatiSecondariTrasferta[i].setVisible(false);
				}
				risultati = true;
				secondario.setText("RISULTATI");
			}
		}
	}
}
