package gui.finestre;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

import gestioneCampionato.Campionato;
import gui.pannelli.CreaEliminaSquadra;
import gui.pannelli.CreazioneNuovoCampionato;
import gui.pannelli.IscrizioneSquadre;
import gui.pannelli.SceltaPartita;
import utilities.LimiteFileSystem;

/** Finestra principale
 * <p>
 * Si apre all'avvio del programma.<br>
 * Gestisce i pannelli che permettono all'utente di creare un nuovo campionato, di caricarne uno esistente da file e di creare/eliminare squadre.<br>
 */
public class MenuIniziale extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L; //Versione dell'oggetto
	
	/**Pannello che mette a disposizione la scelta nuovo/carica campionato e crea/elimina squadra */
	private SceltaPartita sp; 
	/**Pannello che permette la creazione di un nuovo campionato */
	private CreazioneNuovoCampionato nc; 
	/**Pannello che permette l'inserimento delle squadre partecipanti */
	private IscrizioneSquadre is; 
	/**Pannello che permette la creazione di una nuova squadra o l'eliminazione di una esistente */
	private CreaEliminaSquadra ce; 

	/** Costruttore
	 * 
	 * @param titolo Stringa contenente il titolo della finestra.
	 */
	public MenuIniziale(String titolo) {
		super(titolo);
		
		/* Imposto il logo della finestra */
		setIconImage(new ImageIcon("./media/icon.png").getImage());
		
		/* Imposto la dimensione della finestra a 2/3 di quella totale dello schermo e la centro in esso */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = (int)screenSize.getWidth();
		int h = (int)screenSize.getHeight();
		setBounds(w/6,h/6,w*2/3,h*2/3);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Quando si chiude questa finestra, viene chiuso anche il programma 
		setResizable(false); //La finestra non può essere ridimensionata
		
		/* Creo e aggiungo alla finesta il pannello del menu di scelta */
		sp = new SceltaPartita(this,titolo);
		add(sp);
	}

	/** Metodo di switch dei pannelli
	 * <p>
	 * Rimuove il pannello di creazione del campionato e inserisce quello di iscrizione delle squadre.
	 * 
	 * @param c Il campionato che si sta creando.
	 */
	public void visualizzaMenuInserimentoSquadre(Campionato c) {
		remove(nc);
		is = new IscrizioneSquadre(this,c);
		add(is);
		
		/* Funzioni di aggiornamento degli oggetti inseriti nella finestra */
		revalidate(); repaint();
	}
	
	/** Metodo di switch dei pannelli
	 * <p>
	 * Rimuove il pannello di iscrizione delle squadre e ritorna alla creazione del campionato.
	 */
	public void visualizzaMenuCreazioneCampionato() {
		remove(is);
		nc = new CreazioneNuovoCampionato(this);
		add(nc);
		
		/* Funzioni di aggiornamento degli oggetti inseriti nella finestra */
		revalidate(); repaint();
	}
	
	/** Metodo di switch dei pannelli
	 * <p>
	 * Rimuove il pannello passato come parametro e ritorna al menu di scelta (utilizzato per le opzioni "ritorna al menu").
	 * 
	 * @param p Il pannello che viene rimosso
	 */
	public void visualizzaMenuScelta(JPanel p) {
		remove(p);
		add(sp);
		
		/* Funzioni di aggiornamento degli oggetti inseriti nella finestra */
		revalidate(); repaint();
	}
		
	/** Metodo di caricamento del campionato
	 * <p>
	 * Mostra il file chooser che esplora la cartella root permettendo all'utente di scegliere quale campionato caricare.
	 *
	 * @param root Cartella in cui si posiziona il file chooser
	 * @param salva Indica se il caricamento è legato ad un campionato giaà esistente o predefinito
	 */
	private void caricamento(File root,boolean salva) {
		UIManager.put("FileChooser.readOnly", Boolean.TRUE);
		FileSystemView fsv = new LimiteFileSystem(root); //Limito il file chooser a cercare solo in quella cartella
		JFileChooser fc = new JFileChooser(fsv); 
		fc.getActionMap().get("Go Up").setEnabled(false); //Impedisco al file chooser di spostarsi su altri livelli del file system
		/* Mostro la finestra di dialogo e se l'utente conferma, procedo con il caricamento */
		if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			Campionato c = null;
			try {
				ObjectInputStream is = new ObjectInputStream(new FileInputStream(fc.getSelectedFile())); //Recupero il file salvato
				c = (Campionato)(is.readObject()); //Leggo il file e lo deserializzo in un oggetto Campionato
				is.close(); //Chiudo lo stream
			}
			catch(IOException e1) { 
				JOptionPane.showMessageDialog(this, "Errore nel caricamento."); //Messaggio di errore
			}
			catch(ClassNotFoundException e2) { 
				JOptionPane.showMessageDialog(this, "Errore nel caricamento."); //Messaggio di errore
			}
			
			c.creaClassifica(); //Creo l'oggetto classifica i base al campionato caricato
			
			sp.mostraCaricamento(c,salva); //Mostro la barra di caricamento prima di aprire la finestra di gestione
		}
	}
	
	/** Metodo di gestione degli eventi generati dai pulsanti del menu */
	@Override
	public void actionPerformed(ActionEvent e) {
		/* Click pulsante "Nuovo campionato" */
		if(e.getActionCommand().equals("Nuovo campionato")) {
			Object[] opzioni = {"Personalizzato","Predefinito"};
			int cd = JOptionPane.showOptionDialog(this,"Scegliere tipo campionato.","Nuovo campionato",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,opzioni,opzioni[0]);
			if(cd == 0) {
				remove(sp);
				nc = new CreazioneNuovoCampionato(this);
				add(nc);
			}
			else{
				File root = new File("./media/predefiniti"); //Cartella all'interno della quale cerco le squadre
				caricamento(root,true);
			}
		}
		
		/* Click pulsante "Carica campionato", carica un campionato gia' esistente da file */
		if(e.getActionCommand().equals("Carica campionato")) {
			File root = new File("./media/salvataggi"); //Cartella all'interno della quale cerco le squadre
			caricamento(root,false);
		}
		
		/* Click pulsante "Crea/Elimina", mostra pannello di creazione squadra e eliminazione squadra o campionato */
		if(e.getActionCommand().equals("Gestione")) {
			remove(sp);
			ce = new CreaEliminaSquadra(this);
			
			add(ce);
		}
		
		/* Funzioni di aggiornamento degli oggetti inseriti nella finestra */
		revalidate(); repaint();
	}
}
