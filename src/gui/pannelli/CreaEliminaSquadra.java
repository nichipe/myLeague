package gui.pannelli;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import gestioneCampionato.Squadra;
import gui.DynamicLabel;
import gui.bottoni.ConfirmButton;
import gui.bottoni.ServiceButton;
import gui.finestre.MenuIniziale;
import utilities.LimiteFileSystem;

/** Pannello di gestione delle squadre e dei campionati salvati
 * <p>
 * Mette a disposizione gli strumenti per creare o eliminare una squadra e per eliminare un campionato.<br>
 * L'eliminazione di una squadra non sara' effettiva all'interno di un campionato iniziato prima della stessa.<br>
 */
@SuppressWarnings("serial")
public class CreaEliminaSquadra extends JPanel implements ActionListener{
	/** La finestra in cui e' inserito il pannello */
	private MenuIniziale fp; 
	/** Pulsante per aprire il file system */
	private ServiceButton sfoglia; 
	/** Pulsante di conferma creazione squadra */
	private ConfirmButton crea; 
	/** Pulsante di ritorno al menu principale */
	private ConfirmButton esci; 
	/** Pulsante di conferma modifica squadra */
	private ConfirmButton conferma; 
	/** Pulsante di annullamento modifiche */
	private ConfirmButton annulla; 
	/** Pulsante per eliminare una squadra */
	private ConfirmButton modificaSquadra; 
	/** Pulsante per eliminare una squadra */
	private ConfirmButton eliminaSquadra; 
	/** Pulsante per eliminare il campionato */
	private ConfirmButton eliminaCampionato; 
	/** Campo di testo per inserire il nome della squadra da creare */
	private JTextField inserimentoNome; 
	/** Campo di testo per inserire la sede della squadra da creare */
	private JTextField inserimentoSede; 
	/** JLabel utilizzato per mostrare il logo */
	private JLabel mostraLogo; 
	/** Percorso del logo selezionato */
	private String logo;
	/** Nome del file su cui e' salvata la squadra che si sta modificando */
	private String nome;
	/** Lunghezza (in pixel) della finestra */
	private int w; 
	/** Altezza (in pixel) della finestra */
	private int h; 
	
	/** Costruttore 
	 * 
	 * @param fp La finestra in cui e' inserito il pannello
	 */
	public CreaEliminaSquadra(MenuIniziale fp) {
		super();
		this.fp = fp;
		nome = null;
		
		setLayout(null); // Imposto il layout manager nullo per poter inserire manualmente la posizione di ogni oggetto 
		
		setBackground(Color.white); // Imposto il colore di sfondo del pannello
		
		/* Calcolo le dimensioni (in pixel) del pannello per il posizionamento degli oggetti al suo interno */
		w = fp.getWidth(); 
		h = fp.getHeight();
		
		/* Creo l'oggetto titolo */
		Titolo titolo = new Titolo("Gestione squadre",getBackground(),new Color(4,66,140),false);
		titolo.setBounds(0,h/20,w,h/10);
		
		/* Creo un separatore da inserire tra il titolo e gli altri elementi */
		JSeparator o = new JSeparator(SwingConstants.HORIZONTAL);
		o.setBounds(w/10,h*3/20,w*8/10,5);
		
		/* Creo i label per i campi da creare (nome, sede, logo) */
		DynamicLabel nome = new DynamicLabel("NOME",true);
		nome.setBounds(w/10,h*7/40,w/5,h/20);
		DynamicLabel sede = new DynamicLabel("SEDE",true);
		sede.setBounds(w/10,h*5/16,w/5,h/20);
		DynamicLabel llogo = new DynamicLabel("LOGO",true);
		llogo.setBounds(w/10,h*19/40,w/5,h/20);
		
		/* Creo i campi di testo per inserire il nome e la sede */
		inserimentoNome = new JTextField(21);
		inserimentoNome.setBounds(w/10,h*18/80,w/5,h/20);
		inserimentoSede = new JTextField(15);
		inserimentoSede.setBounds(w/10,h*29/80,w/5,h/20);
		
		/* Creo il JLabel in cui inseriro' il logo */
		mostraLogo = new JLabel();
		mostraLogo.setBounds(w/10,h*21/40,w/10,w/10);
		
		/* Creo il tasto "sfoglia" per l'inserimento del logo */
		sfoglia = new ServiceButton("Sfoglia");
		sfoglia.setBounds(w/10,h*21/40,w/10,h/20);
		sfoglia.addActionListener(this);
		
		/* Creo il bottone di conferma dei dati e quello di ritorno alla scelta del torneo */
		crea = new ConfirmButton("Crea squadra");
		crea.setBounds(w/10,h*3/4,w/5,h/10);
		crea.addActionListener(this);
		esci = new ConfirmButton("Esci");
		esci.setBounds(w*2/5,h*3/4,w/5,h/10);
		esci.addActionListener(this);
		modificaSquadra = new ConfirmButton("Modifica squadra");
		modificaSquadra.setBounds(w*2/5,h*5/8,w/5,h/10);
		modificaSquadra.addActionListener(this);
		eliminaSquadra = new ConfirmButton("Elimina squadra");
		eliminaSquadra.setBounds(w*7/10,h*3/4,w/5,h/10);
		eliminaSquadra.addActionListener(this);
		eliminaCampionato = new ConfirmButton("Elimina campionato");
		eliminaCampionato.setBounds(w*7/10,h*5/8,w/5,h/10);
		eliminaCampionato.addActionListener(this);
		annulla = new ConfirmButton("Annulla");
		annulla.setBounds(w*2/5,h*5/8,w/10,h/10);
		annulla.addActionListener(this);
		annulla.setVisible(false);
		conferma = new ConfirmButton("Conferma");
		conferma.setBounds(w/2,h*5/8,w/10,h/10);
		conferma.addActionListener(this);
		conferma.setVisible(false);
		
		/* Setto il logo di default */
		logo = "./media/loghi/logoDefault.png";
		
		/* Aggiungo gli elementi al pannello */
		add(titolo); add(o); //Titolo e separatore
		add(nome); add(sede); add(llogo); //Etichette sezioni
		add(inserimentoNome); add(inserimentoSede); add(mostraLogo);  //Campi di testo per il nome e logo
		add(sfoglia); //Pulsante di esplorazione file system
		add(crea); add(esci); add(annulla); add(conferma); add(modificaSquadra); add(eliminaSquadra); add(eliminaCampionato); //Pulsanti funzione
	}

	/** Metodo di controllo
	 * <p>
	 * Controlla che i valori inseriti siano consistenti e, se non lo sono, evidenzia il campo errato e blocca la creazione della squadra.
	 * 
	 * @return 'true' se i valori sono validi, altrimenti 'false'
	 */
	private boolean controllo() {
		boolean valido = true; //Indica se i valori sono corretti
		inserimentoNome.setBorder(BorderFactory.createLineBorder(Color.black));
		inserimentoSede.setBorder(BorderFactory.createLineBorder(Color.black));
		
		if(inserimentoNome.getText().equals("")) {
			inserimentoNome.setBorder(BorderFactory.createLineBorder(Color.red));
			valido = false;
		}
		if(inserimentoSede.getText().equals("")) {
			inserimentoSede.setBorder(BorderFactory.createLineBorder(Color.red));
			valido = false;
		}
		return valido;
	}
	
	/** Metodo di salvataggio dei loghi
	 *<p>
	 * Ogni volta che una squadra viene creata salva il suo logo in una cartella interna al programma.
	 * 
	 * @param s Squadra di cui si deve salvare il logo
	 */
	private void modificaLogo(Squadra s) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(new File(s.getLogo()));
			os = new FileOutputStream(new File("./media/loghi/" + s.getNome() + s.getSede() + ".jpg"));
			byte[] buffer = new byte[1024];
			int dim;
			while((dim = is.read(buffer)) > 0) os.write(buffer,0,dim);
			is.close(); os.close();
		} catch(IOException e) {
			JOptionPane.showMessageDialog(this, "Errore nel'apertura del file.","Errore",JOptionPane.INFORMATION_MESSAGE);
		}		
		
		s.setLogo("./media/loghi/" + s.getNome() + s.getSede() + ".jpg");
	}
	
	/** Metodo di salvataggio della squadra
	 * <p>
	 * Serializza l'oggetto squadra che gli viene passato in un file col formato "'nomesquadra'-'sedequadra'"
	 * 
	 * @param s La squadra creata
	 */
	private void salvaSquadra(Squadra s) {
		try{
			ObjectOutputStream os = null;
			if(nome != null) os = new ObjectOutputStream(new FileOutputStream("./media/squadre/" + nome));
			else os = new ObjectOutputStream(new FileOutputStream("./media/squadre/" + s.getNome() + "-" + s.getSede()));
			os.writeObject(s); os.flush(); os.close();
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(this, "Errore nel salvataggio del file.","Errore",JOptionPane.INFORMATION_MESSAGE);
		}
		nome = null;
	}
	
	/** Metodo di modifica del pannello per permettere all'utente di modificare una squadra 
	 * 
	 * @param s La squadra che si sta modificando
	 */
	private void modificaSquadra(Squadra s) {
		inserimentoNome.setText(s.getNome());
		inserimentoSede.setText(s.getSede());
		logo = s.getLogo();
		ImageIcon imgLogo = new ImageIcon(s.getLogo());
		Image scaled = imgLogo.getImage().getScaledInstance(w/10,w/10,0);
		imgLogo.setImage(scaled);
		mostraLogo.setIcon(imgLogo);
		sfoglia.setBounds(w*3/10,h*21/40,w/10,h/20);
		modificaSquadra.setVisible(false);
		annulla.setVisible(true);
		conferma.setVisible(true);
	}
	
	/** Metodo di gestione degli eventi generati dai pulsanti del pannello */
	@Override
	public void actionPerformed(ActionEvent e) {
		/* Click "crea", recupera le informazioni e salva la squadra */
		if(e.getSource().equals(crea) || e.getSource().equals(conferma)) {
			String nome = inserimentoNome.getText();
			String sede = inserimentoSede.getText();
			if(controllo()) {
				Squadra s = new Squadra(nome,sede,logo);
				modificaLogo(s);
				salvaSquadra(s);
				inserimentoNome.setBorder(BorderFactory.createLineBorder(Color.black));
				inserimentoNome.setText("");
				inserimentoSede.setBorder(BorderFactory.createLineBorder(Color.black));
				inserimentoSede.setText("");
				mostraLogo.setIcon(null);
				sfoglia.setBounds(w/10,h*21/40,w/10,h/20);
				JOptionPane.showMessageDialog(this, "Squadra creata/modificata correttamente.","",JOptionPane.INFORMATION_MESSAGE);
			}
			modificaSquadra.setVisible(true);
			annulla.setVisible(false);
			conferma.setVisible(false);
		}
		
		/* Click "annulla" */
		if(e.getSource().equals(annulla)) {
				inserimentoNome.setBorder(BorderFactory.createLineBorder(Color.black));
				inserimentoNome.setText("");
				inserimentoSede.setBorder(BorderFactory.createLineBorder(Color.black));
				inserimentoSede.setText("");
				mostraLogo.setIcon(null);
				sfoglia.setBounds(w/10,h*21/40,w/10,h/20);
				modificaSquadra.setVisible(true);
				annulla.setVisible(false);
				conferma.setVisible(false);
		}
		
		UIManager.put("FileChooser.readOnly", Boolean.TRUE);
		JFileChooser fc = null; //Oggetto che permette di esplorare il file system
		
		/* Click "modifica squadra" */
		if(e.getSource().equals(modificaSquadra)) {
			/* INCAPSULAMENTO: in questo contesto si evidenzia l'utilizzo dell'incapsulamento. All'oggetto JFileChooser viene passato
			 *                 un oggetto FileSystemView che indica al file chooser in che modo visualizzare il file system. */
			File root = new File("./media/squadre"); //Cartella all'interno della quale cerco le squadre
			FileSystemView fsv = new LimiteFileSystem(root); //Limito il file chooser a cercare solo in quella cartella
			fc = new JFileChooser(fsv); //fc.getActionMap().get("New Folder").setEnabled(false); //Impedisco al file chooser di creare nuove cartelle
			fc.getActionMap().get("Go Up").setEnabled(false); //Impedisco al file chooser di spostarsi su altri livelli del file system
			fc.setApproveButtonText("Apri");
			if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				Squadra s = null;
				try {
					ObjectInputStream is = new ObjectInputStream(new FileInputStream(fc.getSelectedFile()));
					s = (Squadra)(is.readObject());
					nome = fc.getSelectedFile().getName();
					modificaSquadra(s);
					is.close();
				}
				catch(IOException e1) {
					JOptionPane.showMessageDialog(this, "Errore nel caricamento della squadra."); //Messaggio di errore
				}
				catch(ClassNotFoundException e2) {
					JOptionPane.showMessageDialog(this, "Errore nel caricamento della squadra."); //Messaggio di errore
				}
			}
		}
		
		/* Click "elimina squadra", mostra all'utente l'elenco delle squadre presenti e chiede conferma quando l'utente sceglie quale eliminare */
		if(e.getSource().equals(eliminaSquadra)) {
			File root = new File("./media/squadre"); //Cartella all'interno della quale cerco le squadre
			FileSystemView fsv = new LimiteFileSystem(root); //Limito il file chooser a cercare solo in quella cartella
			fc = new JFileChooser(fsv); 
			fc.getActionMap().get("Go Up").setEnabled(false); //Impedisco al file chooser di spostarsi su altri livelli del file system
			fc.setApproveButtonText("Elimina");
			if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				int confirm = JOptionPane.showConfirmDialog(this,"Sei sicuro di voler eliminare questa squadra?","",JOptionPane.YES_NO_OPTION);
				if(confirm == JOptionPane.YES_OPTION) {
					try{ 
						java.nio.file.Files.delete(fc.getSelectedFile().toPath()); 
						JOptionPane.showMessageDialog(this, "Squadra eliminata correttamente."); //Messaggio di conferma all'utente
					}
					catch(IOException e1) {
						JOptionPane.showMessageDialog(this, "Errore nell'eliminazione del file.","Errore",JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		}
		
		/* Click "elimina campionato", mostra all'utente l'elenco dei campionati presenti e chiede conferma quando l'utente sceglie quale eliminare */
		if(e.getSource().equals(eliminaCampionato)) {
			File root = new File("./media/salvataggi"); //Cartella all'interno della quale cerco le squadre
			FileSystemView fsv = new LimiteFileSystem(root); //Limito il file chooser a cercare solo in quella cartella
			fc = new JFileChooser(fsv); 
			fc.setApproveButtonText("Elimina");
			if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				int confirm = JOptionPane.showConfirmDialog(this,"Sei sicuro di voler eliminare questo campionato?","",JOptionPane.YES_NO_OPTION);
				if(confirm == JOptionPane.YES_OPTION) {
					try{ 
						java.nio.file.Files.delete(fc.getSelectedFile().toPath()); 
						JOptionPane.showMessageDialog(this, "Campionato eliminato correttamente."); //Messaggio di conferma all'utente
					}
					catch(IOException e2) {
						System.err.println("Impossibile aprire il file."); 
						System.err.println(e2.getMessage());
					}
				}
			}			
		}
		
		/* Click "sfoglia", apre il file chooser per scegliere il logo della squadra */
		if(e.getSource().equals(sfoglia)) {
			fc = new JFileChooser("./media/loghi"); 
	        fc.setFileFilter(new FileNameExtensionFilter("File JPG, JPEG e PNG", "jpg", "jpeg", "png")); //Imposto i possibili formati di file da inserire
			fc.setAcceptAllFileFilterUsed(false);
			if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				logo = fc.getSelectedFile().getPath();
				sfoglia.setBounds(w*3/10,h*21/40,w/10,h/20);
				
				/* Inserisco il logo della squadra */
				ImageIcon imgLogo = new ImageIcon(logo);
				Image scaled = imgLogo.getImage().getScaledInstance(w/10,w/10,0);
				imgLogo.setImage(scaled);
				mostraLogo.setIcon(imgLogo);
			}
		}
		
		/* Click "esci", ritorna al menu */
		if(e.getSource().equals(esci)) fp.visualizzaMenuScelta(this);
	}
}
