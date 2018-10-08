package gui.finestre;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

import gestioneCampionato.Campionato;
import gestioneCampionato.Squadra;
import gui.pannelli.InserimentoRisultati;
import gui.pannelli.MenuGestione;
import gui.pannelli.ModificaAttributiSquadra;
import gui.pannelli.ModificaSquadra;
import gui.pannelli.VisualizzaCalendario;
import gui.pannelli.VisualizzaClassifica;
import utilities.LimiteFileSystem;

/** Finestra principale del gestore
 * <p>
 * Contiene tutti gli elementi che permettono all'utente di gestire il campionato, quindi di:<br>
 * <ol>
 * <il>modificare una squadra partecipante nei suoi attributi principali (nome, sede, logo);
 * <il>inserire risultati di una giornata;
 * <il>visualizzare il calendario e i risultati;
 * <il>visualizzare la classifica. 
 * </ol>
 */
public class MenuPrincipale extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L; //Versione dell'oggetto
	
	/**Il campionato in corso */
	private Campionato c; 
	/**Pannello contente il menu principale di gestione */
	private MenuGestione mg; 
	/**Pannello che permette la scelta della squadra da modificare */
	private ModificaSquadra ms; 
	/**Pannello che permette la modifica degli attributi di una squadra */
	private ModificaAttributiSquadra mas; 
	/**Pannello di visualizzazione del calendario */
	private VisualizzaCalendario cal; 
	/**Pannello di inserimento dei risultati */
	private InserimentoRisultati ir; 
	/**Pannello di visualizzazione della classifica */
	private VisualizzaClassifica vc; 
	/**Indica se e' necessario ricalcolare la classifica */
	private boolean calcola; 
	/** Indica se è il primo salvataggio del campionato (per chieder il nome) */
	private boolean primoSalvataggio;
	
	/** Costruttore
	 * 
	 * @param c Il campionato in corso
	 * @param primoSalvataggio Indica se il campionato è appena stato creato (quindi da salvare) oppure caricato 
	*/
	public MenuPrincipale(Campionato c,boolean primoSalvataggio) {
		super(c.getNome()); // Imposto come titolo della finestra il nome del campionato
		this.c = c;
		this.primoSalvataggio = primoSalvataggio;
		calcola = true;
		
		/* Imposto il logo della finestra */
		setIconImage(new ImageIcon("./media/icon.png").getImage());
		
		/* Imposto la dimensione della finestra a 2/3 di quella totale dello schermo e la centro in esso */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = (int)screenSize.getWidth();
		int h = (int)screenSize.getHeight();
		setBounds(w/6,h/6,w*2/3,h*2/3);
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Quando si chiude questa finestra, viene chiuso anche il programma 
		setResizable(false); //La finestra non può essere ridimensionata
		
		salvaCampionato(); //Salvo le informazioni riguardanti campionato e calendario in un nuovo file
		
		/* Aggiungo il pannello principale, ovvero quello del menu di gestione */
		mg = new MenuGestione(this,c);
		add(mg);
	}
	
	/** Metodo di switch dei pannelli
	 * <p>
	 * Rimuove il pannello passato come parametro e reinserisce il menu di gestione.
	 * 
	 * @param p Pannello da rimuovere.
	 */
	public void ritornaAlMenu(JPanel p) {
		remove(p);
		add(mg);
		
		/* Funzioni di aggiornamento degli oggetti inseriti nella finestra */
		revalidate(); repaint();
	}
	
	/** Metodo di switch dei pannelli 
	 * <p>
	 * Rimuove il pannello di scelta della squadra e inserisce quello di modifica degli attributi della squadra scelta.
	 * 
	 * @param s La squadra che si vuole modificare.
	 */
	public void modificaSquadra(Squadra s) {
		remove(ms);
		mas = new ModificaAttributiSquadra(this,s);
		add(mas);
		
		/* Funzioni di aggiornamento degli oggetti inseriti nella finestra */
		revalidate(); repaint();
	}
	
	/** Metodo di switch dei pannelli
	 * <p>
	 * Rimuove il pannello di modifica di una squadra e reinserisce quello di scelta della squadra da modificare.
	 */
	public void ritornaASceltaSquadra() {
		remove(mas);
		ms = new ModificaSquadra(this,c);
		add(ms);
		
		/* Funzioni di aggiornamento degli oggetti inseriti nella finestra */
		revalidate(); repaint();
	}
	
	/** Metodo di risoluzione dei nomi doppi
	 * <p>
	 * Trova il primo nome disponibile, basandosi su quello di base, per salvare un nuovo file. 
	 * 
	 * @param nome Il nome base del file
	 */
	private void salvaCampionatoGenerico(String nome) {
		boolean nomeGenerico = true;
		if(nome.endsWith(")")) {
			File f = new File("./media/salvataggi/" + nome.substring(0,nome.length()-3));
			nome = nome.substring(0,nome.length()-3);
			if(!f.exists()) c.setNomeFile(nome);
			else nomeGenerico = false;
		}
		else nomeGenerico = false;
		
		if(!nomeGenerico){
			int n = 1;
			while(true) {
				File f = new File("./media/salvataggi/" + nome + "(" + n + ")");
				if(!f.exists()) break;
				n++;
			}
			c.setNomeFile(nome + "(" + n + ")");
		}
		
	}
	
	/** Metodo di salvataggio del campionato 
	 * <p>
	 * Serializza l'oggetto Campionato e lo salva nella cartella apposita.
	 */
	private void salvaCampionato() {
		try{
			UIManager.put("FileChooser.readOnly", Boolean.TRUE);
			File root = new File("./media/salvataggi"); //Cartella all'interno della quale cerco le squadre
			/* INCAPSULAMENTO: inserisco l'oggetto LimiteFileSystem nell'oggetto JFileChooser */
			FileSystemView fsv = new LimiteFileSystem(root); //Limito il file chooser a cercare solo in quella cartella
			JFileChooser fc = new JFileChooser(fsv); 
			fc.getActionMap().get("Go Up").setEnabled(false); //Impedisco al file chooser di spostarsi su altri livelli del file system 
			if(primoSalvataggio && fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				c.setNomeFile(fc.getSelectedFile().getName());
				primoSalvataggio = false;
				
				/* Controllo se il file esiste già */
				File f = fc.getSelectedFile();
				if(f.exists()) {
					int cd = JOptionPane.showConfirmDialog(this,"Esiste già un campionato con questo nome. Sovrascriverlo?","Salvataggio campionato",JOptionPane.YES_NO_OPTION);
					if(!(cd == JOptionPane.YES_OPTION)) salvaCampionatoGenerico(f.getName());
				}
			} 
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("./media/salvataggi/" + c.getNomeFile())); //Creo un file di salvataggio
			os.writeObject(c); //Serializzo il campionato sul file
			os.flush(); 
			os.close(); //Chiudo lo stream
		}
		catch(IOException e) { 
			JOptionPane.showMessageDialog(this, "Errore nel salvataggio del campionato."); //Messaggio di errore
		}
	}
	
	/** Metodo di set del calcolo della classifica e di salvataggio del campionato 
	 * <p>
	 * Chiama il metodo di salvataggio (serializzazione) del campionato e setta il flag di calcolo della classifica.
	 */
	public void setCalcola() { 
		salvaCampionato();
		calcola = true; 
	}
	
	/** Metodo di reset del calcolo della classifica
	 * <p>
	 * Resetta il flag di calcolo della classifica.
	 */
	public void unsetCalcola() { calcola = false; }

	/** Metodo di gestione degli eventi generati dai pulsanti del menu */
	@Override
	public void actionPerformed(ActionEvent e) {
		/* Modifica squadra */
		if(e.getActionCommand().equals("Modifica squadra")) {
			remove(mg);
			ms = new ModificaSquadra(this,c);
			add(ms);
		}
		
		/* Visualizzazione calendario e risultati */
		if(e.getActionCommand().equals("Calendario")) {
			remove(mg);
			cal = new VisualizzaCalendario(this,c);
			add(cal);
		}
		
		/* Inserimento risultati */
		if(e.getActionCommand().equals("Inserisci risultati")) {
			remove(mg);
			ir = new InserimentoRisultati(this,c);
			add(ir);
		}
		
		/* Visualizzazione classifica */
		if(e.getActionCommand().equals("Classifica")) {
			remove(mg);
			vc = new VisualizzaClassifica(this,c,calcola);
			add(vc);
		}
		
		/* Ritorno al menu iniziale */
		if(e.getActionCommand().equals("Ritorna al menu iniziale")) {
			int confirm = JOptionPane.showConfirmDialog(this,"Sei sicuro di voler uscire da questa competizione?","",JOptionPane.YES_NO_OPTION);
			if(confirm == JOptionPane.YES_OPTION) {
				MenuIniziale fp = new MenuIniziale("myLeague");
				setVisible(false); //Chiudo questa finestra
				fp.setVisible(true); //Mostro il menu iniziale
			}
		}
		
		/* Funzioni di aggiornamento degli oggetti inseriti nella finestra */
		revalidate(); repaint();
	}
}
