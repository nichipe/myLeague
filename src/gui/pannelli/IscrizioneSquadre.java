package gui.pannelli;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;
import java.util.Vector;

import gestioneCampionato.Campionato;
import gestioneCampionato.Squadra;
import gui.bottoni.ConfirmButton;
import gui.bottoni.ServiceButton;
import gui.finestre.MenuIniziale;
import gui.finestre.MenuPrincipale;
import utilities.LimiteFileSystem;

/** Pannello di inserimento squadre
 * <p>
 * Pannello che mette a disposizione dell'utente gli strumenti per iscrivere le squadre al campionato.<br>
 * Per ogni squadra l'utente puo' scegliere se crearla da capo, inserendo tutti i dati o se caricarla da un file. 
 * Nel secondo caso l'utente puo' comunque decidere di modificare uno o piu' attributi della squadra prima di avviare la competizione.<br>
 * Le squadre partecipanti saranno comunque completamente modificabili anche dopo la creazione del torneo.<br>
 * Ogni squadra creata verra' salvata e sara' disponibile al caricamento dal campionato successivo.
 */
public class IscrizioneSquadre extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L; //Versione dell'oggetto
	
	/** La finestra in cui e' inserito il pannello */
	private MenuIniziale fp; 
	/** Il campionato che si sta creando */
	private Campionato c; 
	/** Vettore dei pulsanti per inserire i loghi */
	private ServiceButton[] loghi; 
	/** Pulsante per aprire il file chooser per scegliere un logo */
	private ServiceButton logoSquadra; 
	/** Pulsante di conferma dei dati */
	private ConfirmButton conferma; 
	/** Pulsante di ritorno alla schermata di creazione del campionato */
	private ConfirmButton annulla; 
	/** Campo di testo per inserire il nome della squadra */
	private JTextField nomeSquadra;
	/** Campo di testo per inserire la sede della squadra */
	private JTextField sedeSquadra; 
	/** Vettore dei campi di testo dei nomi di ogni squadra */
	private JTextField[] nomi; 
	/** Vettore dei campi di testo delle sedi di ogni squadra */
	private JTextField[] sedi; 
	/** Vettore dei percorsi dei loghi inseriti */
	private String[] percorsoLogo; 
	/** Array dei nomi delle squadre caricate fino ad ora */
	private Vector<String> nomiCaricati;
	/** Array delle sedi delle squadre caricate fino ad ora */
	private Vector<String> sediCaricate;
	/** La lunghezza in pixel della finestra */
	private int w; 
	/** L'altezza in pixel della finestra */
	private int h; 
	
	/** Costruttore
	 * 
	 * @param fp Finestra in cui e' contenuto il pannello.
	 * @param c Campionato che si sta creando.
	 */
	public IscrizioneSquadre(MenuIniziale fp,Campionato c){
		super();
		this.fp = fp;
		this.c = c;
		
		/* Inizializzo gli array delle squadre caricate */
		nomiCaricati = new Vector<String>();
		sediCaricate = new Vector<String>();
		
		/* Creo i vettori dove inserisco i JTexField per nomi e sedi delle squadre e i pulsanti per i loghi */
		nomi = new JTextField[c.getNumSquadre()];
		sedi = new JTextField[c.getNumSquadre()];
		loghi = new ServiceButton[c.getNumSquadre()];
		percorsoLogo = new String[c.getNumSquadre()];
		for(int i=0;i<c.getNumSquadre();i++) {
			nomi[i] = sedi[i] = null;
			loghi[i] = null;
			percorsoLogo[i] = null;
		}
		
		setLayout(null); // Imposto il layout manager nullo per poter inserire manualmente la posizione di ogni oggetto 
		
		setBackground(Color.white); // Imposto il colore di sfondo del pannello
		
		/* Calcolo le dimensioni (in pixel) del pannello per il posizionamento degli oggetti al suo interno */
		w = fp.getWidth(); 
		h = fp.getHeight();
		
		/* Creo l'oggetto titolo */
		Titolo titolo = new Titolo("Squadre partecipanti",getBackground(),new Color(4,66,140),false);
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
		
		/* Creo la struttura per la creazione delle squadre */
		int offset = 0, i, cordXCodice = w/40, cordXCrea = w/20, cordXCarica = w/5;
		ServiceButton creaSquadra; //L'utente vuole creare una nuova squadra
		ServiceButton caricaSquadra; //L'utente vuole caricare una squadra
		for(i=0;i<c.getNumSquadre();i++) {
			JLabel codice = new JLabel(String.valueOf(i+1)); //Ordine di inserimento della squadra
			codice.setBounds(cordXCodice,(h/4)+offset,h/20,h/20);
			codice.setFont(new Font("Trebuchet MS",Font.BOLD,14));
			add(codice);
			
			/* Pulsanti di creazione/caricamento della i-esima squadra */
			creaSquadra = new ServiceButton("Crea");
			creaSquadra.setBounds(cordXCrea,(h/4)+offset,w/10,h/20);
			creaSquadra.addActionListener(this);
			creaSquadra.setActionCommand("CR"+(char)(97+i)); //Per identificare quale squadra si vuole creare
			caricaSquadra = new ServiceButton("Carica");
			caricaSquadra.setBounds(cordXCarica,(h/4)+offset,w/10,h/20);
			caricaSquadra.addActionListener(this);
			caricaSquadra.setActionCommand("CA"+(char)(97+i)); //Per identificare quale squadra si vuole caricare
			
			/* Setto i bottoni coppia che mi saranno utili per eliminare i bottoni insieme */
			creaSquadra.setBottoneCoppia(caricaSquadra);
			caricaSquadra.setBottoneCoppia(creaSquadra);
			
			add(creaSquadra); add(caricaSquadra);
			
			/* Distanza tra l'inizio di una riga e l'inizio della successiva */
			if(c.getNumSquadre()<=10) offset += h/10;
			else offset += h/c.getNumSquadre();	
			
			if(i == (c.getNumSquadre()/2)-1) {	//Quando arrivo a meta' squadre, proseguo nella semipagina di destra
				cordXCodice = w*21/40;
				cordXCrea = w*11/20;
				cordXCarica = w*7/10;
				offset = 0;
			}
		}
		
		/* Creo il bottone di conferma dei dati e quello di ritorno alla creazione del campionato */
		conferma = new ConfirmButton("Conferma");
		conferma.setBounds(w*3/5,h*4/5,w/5,h/10);
		conferma.addActionListener(this);
		annulla = new ConfirmButton("Annulla");
		annulla.setBounds(w/5,h*4/5,w/5,h/10);
		annulla.addActionListener(this);
		
		/* Aggiungo gli elementi al pannello */
		add(titolo); add(o); //Titolo e separatore
		add(conferma); add(annulla); //Pulsanti funzione
	}

	/** Metodo di raccolta dati dal form
	 * <p>
	 *  Quando l'utente conferma l'inserimento dei dati, va a recuperarli dai due array di JTextField e li assegna alle squadre corrispondenti.
	 */
	private void raccoltaDati() {
		for(int i=0;i<c.getNumSquadre();i++) {
			Squadra s = c.getSquadraAtIndex(i);
			if(nomi[i] != null && !nomi[i].getText().equals("")) s.setNome(nomi[i].getText());
			if(sedi[i] != null && !sedi[i].getText().equals("")) s.setSede(sedi[i].getText());
			if(percorsoLogo[i] != null) s.setLogo(percorsoLogo[i]);
		}
	}
	
	/** Metodo di inserimento della schermata di caricamento 
	 * <p>
	 * Mostra una schermata bianca con la barra di caricamento e alcune informazioni (scherzose) riguardo alle operazioni che sta svolgendo.<br>
	 * Al termine del caricamento chiude questa finestra e mostra quella del menu gestionale.<br>
	 */
	private void mostraCaricamento() {
		removeAll();
		
		/* Etichetta "CARICAMENTO" e separatore */
		JLabel caricamento = new JLabel("CARICAMENTO IN CORSO");
		caricamento.setForeground(new Color(4,66,140));
		caricamento.setFont(new Font("Trebuchet MS",Font.BOLD,24));
		caricamento.setBounds(w/4,h*3/10,w/2,h/20);
		caricamento.setHorizontalAlignment(SwingConstants.CENTER);
		JSeparator o = new JSeparator(SwingConstants.HORIZONTAL);
		o.setBounds(w/4,h*2/5,w/2,5);
		
		add(caricamento); add(o);
		
		/* Progress bar */
		JProgressBar pb = new JProgressBar(0,100);
		pb.setBounds(w/4,h*9/20,w/2,h/10);
		pb.setBorder(BorderFactory.createEtchedBorder(Color.black,Color.black));
		pb.setValue(0);
		pb.setForeground(Color.green);
		add(pb);
		
		/* Info progressi */
		String[] infoProgressi = {"Sto sorteggiando il calendario","Sto falsificando l'eta' dei giocatori","Sto corrompendo gli arbitri","Sto montando le tribune negli stadi",
								  "Sto decidendo chi vincera' il campionato","Sto assumendo tifosi finti","Sto comprando online i palloni","Sto scegliendo i telecronisti",
								  "Sto forgiando il premio nell'oro","Sto istruendo i raccattapalle","Sto calibrando l'arbitro tecnologico","Sto disegnando le righe del campo"}; 
		JLabel progressi = new JLabel(infoProgressi[0]+"...");
		progressi.setFont(new Font("Trebuchet MS",Font.BOLD,16));
		progressi.setBounds(w/4,h*3/5,w/2,h/20);
		progressi.setHorizontalAlignment(SwingConstants.CENTER);
		
		add(progressi);
		
		/* Creo e avvio il thread che gestisce il progresso della barra di caricamento */
		Thread t = new Thread(new Runnable() {
			public void run() {
				Random r = new Random();
				for(int i=0,cont=0;i<=100;i+=r.nextInt(2)) {
					pb.setValue(i);
					if(cont>=4000) {
						progressi.setText(infoProgressi[r.nextInt(infoProgressi.length)]+"...");
						cont = 0;
					}
					
					revalidate();
					repaint();
					
					int wait = r.nextInt(100)+100;
					cont += wait;
					try { Thread.sleep(wait); } 
					catch(InterruptedException ex) {
						System.err.println("Thread interrotto prematuramente.");
						System.err.println(ex.getMessage());
					}
				}
				
				/* Al termine del caricamento mostro il menu gestionale */
				MenuPrincipale mp = new MenuPrincipale(c,true); //Apro la finestra di gestione del campionato
				fp.setVisible(false); //Chiudo la finestra corrente
				mp.setVisible(true); //Mostro il menu principale
			}
		});
		t.start();
	}	
	
	/** Metodo di gestione degli eventi generati dai pulsanti del pannello */
	@Override
	public void actionPerformed(ActionEvent e) {
		/* Click pulsante "Annulla" */
		if(e.getSource().equals(annulla)) fp.visualizzaMenuCreazioneCampionato(); //Ritorno alle impostazioni del campionato
		
		/* Click pulsante "Conferma" */
		if(e.getSource().equals(conferma)) { 
			raccoltaDati(); //Raccolgo i dati sulle squadre dai text field e li inserisco negli oggetti corrispondenti
			c.controlloDati(); //Controllo la consistenza dei dati e eventualmente li modifico
			mostraCaricamento(); //Mostro il caricamento prima di aprire la finestra di gestione
		}
		
		/* Caricamento logo da file */
		if(e.getActionCommand().startsWith("Sfoglia")) { 
			JFileChooser fc = new JFileChooser("./media/loghi"); 
	        fc.setFileFilter(new FileNameExtensionFilter("File JPG, JPEG e PNG", "jpg", "jpeg", "png")); //Imposto i possibili formati di file da inserire
			fc.setAcceptAllFileFilterUsed(false);
	        fc.getActionMap().get("New Folder").setEnabled(false); //Impedisco al file chooser di creare nuove cartelle
			if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				loghi[e.getActionCommand().charAt(7)-97].setBorder(BorderFactory.createLineBorder(Color.green)); //Indico che il logo e' stato scelto
				percorsoLogo[e.getActionCommand().charAt(7)-97] = fc.getSelectedFile().getPath();
			}
		}
		
		/* Inserimento squadre */
		if(e.getActionCommand().startsWith("CA") || e.getActionCommand().startsWith("CR")) {
			ServiceButton sb = (ServiceButton)e.getSource();
			/* sb (ovvero il bottone principale) e' sempre il primo a sinistra */
			if(e.getActionCommand().startsWith("CA")) sb = sb.getBottoneCoppia(); 
			int newH = sb.getBounds().y;
			int newW = sb.getBounds().x;
			
			/* Creo e inserisco le componenti */
			int index = e.getActionCommand().charAt(2)-97; //Indice della squadra che si sta creando e quindi della posizione nel vettore
			
			nomeSquadra = new JTextField();
			nomeSquadra.setBounds(newW,newH,w/7,h/20);
			nomi[index] = nomeSquadra;
			sedeSquadra = new JTextField();
			sedeSquadra.setBounds(newW+w*23/140,newH,w/7,h/20);
			sedi[index] = sedeSquadra;
			logoSquadra = new ServiceButton("Sfoglia");
			logoSquadra.setBorder(BorderFactory.createLineBorder(Color.red)); //Indico che il logo non e' ancora stato scelto
			logoSquadra.setBounds(newW+w*23/70,newH,w/10,h/20);
			logoSquadra.setActionCommand("Sfoglia" + (char)(97+index));
			logoSquadra.addActionListener(this);
			loghi[index] = logoSquadra;
		
			/* Squadra caricata da file */
			if(e.getActionCommand().startsWith("CA")) { 
				UIManager.put("FileChooser.readOnly", Boolean.TRUE);
				File root = new File("./media/squadre"); //Cartella all'interno della quale cerco le squadre
				/* INCAPSULAMENTO: inserisco l'oggetto LimiteFileSystem nell'oggetto JFileChooser */
				FileSystemView fsv = new LimiteFileSystem(root); //Limito il file chooser a cercare solo in quella cartella
				JFileChooser fc = new JFileChooser(fsv); 
				fc.getActionMap().get("Go Up").setEnabled(false); //Impedisco al file chooser di spostarsi su altri livelli del file system
				while(true) {
					if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						Squadra s = null;
						try {
							ObjectInputStream is = new ObjectInputStream(new FileInputStream(fc.getSelectedFile()));
							s = (Squadra)(is.readObject());
							if(nomiCaricati.contains(s.getNome()) && sediCaricate.contains(s.getSede())) {
								JOptionPane.showMessageDialog(this, "Errore. Squadra già inserita."); //Messaggio di errore
							}
							else {
								nomiCaricati.addElement(s.getNome());
								sediCaricate.add(s.getSede());
								nomi[index].setText(s.getNome());
								sedi[index].setText(s.getSede());
								percorsoLogo[index] = s.getLogo();
								loghi[index].setBorder(BorderFactory.createLineBorder(Color.green)); //Indico che il logo e' stato scelto
								/* Elimino i bottoni e aggiungo i campi di testo sulla riga della squadra corrispondente */
								ServiceButton sb2 = sb.getBottoneCoppia();
								sb.setVisible(false); sb2.setVisible(false);
								/* Aggiungo gli elementi sulla riga corrispondente alla squadra che si sta creando */
								add(nomeSquadra); add(sedeSquadra); add(logoSquadra);
								break;
							}
							is.close();
						}
						catch(IOException e1) {
							JOptionPane.showMessageDialog(this, "Errore nel caricamento della squadra."); //Messaggio di errore
						}
						catch(ClassNotFoundException e2) {
							JOptionPane.showMessageDialog(this, "Errore nel caricamento della squadra."); //Messaggio di errore
						}
					}
					else break;
				}
			}
			else {
				/* Elimino i bottoni e aggiungo i campi di testo sulla riga della squadra corrispondente */
				ServiceButton sb2 = sb.getBottoneCoppia();
				sb.setVisible(false); sb2.setVisible(false);
				/* Aggiungo gli elementi sulla riga corrispondente alla squadra che si sta creando */
				add(nomeSquadra); add(sedeSquadra); add(logoSquadra);
			}
			
			/* Funzioni di aggiornamento degli oggetti inseriti nella finestra */
			revalidate(); repaint();
		}
	}
}