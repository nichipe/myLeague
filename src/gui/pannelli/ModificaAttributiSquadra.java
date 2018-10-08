package gui.pannelli;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import gestioneCampionato.Squadra;
import gui.bottoni.ConfirmButton;
import gui.bottoni.ServiceButton;
import gui.finestre.MenuPrincipale;

/** Pannello di modifica della singola squadra
 * <p>
 * Contiene gli strumenti necessari a modificare gli attributi principali (nome, sede, logo) di una squadra.
 */
@SuppressWarnings("serial")
public class ModificaAttributiSquadra extends JPanel implements ActionListener{
	/**La finestra di cui fa parte il pannello */
	private MenuPrincipale mp; 
	/**La squadra che si sta modificando */
	private Squadra s; 
	/**Pulsante di annulamento modifiche e ritorno alla scelta della squadra */
	private ConfirmButton annulla; 
	/**Pulsante di ritorno al menu principale */
	private ConfirmButton menu; 
	/**Pulsante per aprire il file system */
	private ServiceButton sfoglia; 
	/**Pulsante di conferma dei cambiamenti */
	private ConfirmButton conferma; 
	/**Campo di testo per l'inserimento del nuovo nome */
	private JTextField nuovoNome;
	/**Campo di testo per l'inserimento della nuova sede */
	private JTextField nuovaSede; 
	/**Etichetta con il nome attuale della squadra */
	private JLabel vecchioNome; 
	/**Etichetta con la sede attuale della squadra */
	private JLabel vecchiaSede; 
	/**Logo attuale della squadra */
	private JLabel vecchioLogo; 
	/**Nuovo logo della squadra dopo le modifiche */
	private JLabel nuovoLogo; 
	/**Percorso del nuovo logo inserito */
	private String percorsoLogo; 
	/**Lunghezza (in pixel) della finestra */
	private int w; 
	/**Larghezza (in pixel) della finestra */
	private int h; 
	
	/** Costruttore
	 * 
	 * @param mp La finestra di cui fa parte il pannello.
	 * @param s La squadre che si sta modificando.
	 */
	public ModificaAttributiSquadra(MenuPrincipale mp,Squadra s) {
		super();
		this.mp = mp;
		this.s = s;
		
		setLayout(null); // Imposto il layout manager nullo per poter inserire manualmente la posizione di ogni oggetto 
		
		setBackground(Color.white); // Imposto il colore di sfondo del pannello
		
		/* Ottengo le dimensioni (in pixel) del pannello per posizionare gli elementi al suo interno */
		w = mp.getWidth();
		h = mp.getHeight();
		
		/* Creo l'oggetto titolo */
		Titolo titolo = new Titolo("Modifica attributi squadra",getBackground(),new Color(4,66,140),false);
		titolo.setBounds(0,h/20,w,h/10);
		
		/* Creo un separatore da inserire tra il titolo e gli altri elementi */
		JSeparator o = new JSeparator(SwingConstants.HORIZONTAL);
		o.setBounds(w/10,h*3/20,w*8/10,5);
		
		/* Creo i label per i campi da modificare (nome, sede, logo) */
		JLabel nome = new JLabel("NOME");
		nome.setFont(new Font("Trebuchet MS",Font.BOLD,20));
		nome.setBounds(w/10,h*3/20,w/5,h/10);

		JLabel sede = new JLabel("SEDE");
		sede.setFont(new Font("Trebuchet MS",Font.BOLD,20));
		sede.setBounds(w/10,h*9/32,w/5,h/10);
		
		JLabel logo = new JLabel("LOGO");
		logo.setFont(new Font("Trebuchet MS",Font.BOLD,20));
		logo.setBounds(w/10,h*9/20,w/5,h/10);
		
		/* Creo i label con i dati attuali */
		vecchioNome = new JLabel(s.getNome());
		vecchioNome.setFont(new Font("Trebuchet MS",Font.PLAIN,18));
		vecchioNome.setBounds(w/10,h*17/80,w/5,h/20);
		vecchiaSede = new JLabel(s.getSede());
		vecchiaSede.setFont(new Font("Trebuchet MS",Font.PLAIN,18));
		vecchiaSede.setBounds(w/10,h*28/80,w/5,h/20);
		
		/* Creo i campi di testo per inserire il nome e la sede */
		nuovoNome = new JTextField(21);
		nuovoNome.setBounds(w*3/10,h*18/80,w/5,h/20);
		nuovaSede = new JTextField(15);
		nuovaSede.setBounds(w*3/10,h*29/80,w/5,h/20);
		
		/* Inserisco il logo attuale della squadra e il campo dove inserire quello nuovo */
		ImageIcon oldLogo = new ImageIcon(s.getLogo());
		Image scaled = oldLogo.getImage().getScaledInstance(w/10,w/10,0);
		oldLogo.setImage(scaled);
		vecchioLogo = new JLabel();
		vecchioLogo.setBounds(w/10,h*21/40,w/10,w/10);
		vecchioLogo.setIcon(oldLogo);
		nuovoLogo = new JLabel();
		nuovoLogo.setBounds(w/2,h*21/40,w/10,w/10);
		
		/* Creo il pulsante di conferma dei dati, quello di ritorno alla scelta del torneo e quello di ritorno al menu principale e quello per inserire il logo */
		conferma = new ConfirmButton("Conferma modifiche");
		conferma.setBounds(w*7/10,h*3/4,w/5,h/10);
		conferma.addActionListener(this);
		annulla = new ConfirmButton("Annulla");
		annulla.setBounds(w/10,h*3/4,w/5,h/10);
		annulla.addActionListener(this);
		menu = new ConfirmButton("Torna al menu");
		menu.setBounds(w*2/5,h*3/4,w/5,h/10);
		menu.addActionListener(this);
		sfoglia = new ServiceButton("Sfoglia");
		sfoglia.setBounds(w*3/10,h*21/40,w/10,h/20);
		sfoglia.addActionListener(this);
		
		/* Aggiungo gli elementi al pannello */
		add(titolo); add(o); //Titolo e separatore
		add(nome); add(sede); add(logo); //Etichette intestazione sezioni
		add(vecchioNome); add(nuovoNome); add(vecchiaSede); add(nuovaSede); add(vecchioLogo); add(nuovoLogo); //Etichette nome, sede e logo
		add(conferma); add(annulla); add(menu); add(sfoglia); //Pulsanti funzione
	}

	/** Aggiorna i label e i text field dopo ogni modifica */
	private void aggiornaPagina() {
		vecchioNome.setText(s.getNome());
		vecchiaSede.setText(s.getSede());
		ImageIcon logo = new ImageIcon(percorsoLogo);
		Image scaled = logo.getImage().getScaledInstance(w/10,w/10,0);
		logo.setImage(scaled);
		vecchioLogo.setIcon(logo);
		nuovoNome.setText("");
		nuovaSede.setText("");
		nuovoLogo.setIcon(null);
	}
	
	/** Metodo di gestione degli eventi generati dai pulsanti del pannello */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(sfoglia)) {
			UIManager.put("FileChooser.readOnly", Boolean.TRUE);
			JFileChooser fc = new JFileChooser("./media/loghi"); 
	        fc.setFileFilter(new FileNameExtensionFilter("File JPG, JPEG e PNG", "jpg", "jpeg", "png")); //Imposto i possibili formati di file da inserire
			fc.setAcceptAllFileFilterUsed(false);
			if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				percorsoLogo = fc.getSelectedFile().getPath();
				/* Inserisco il nuovo logo della squadra */
				ImageIcon logo = new ImageIcon(percorsoLogo);
				Image scaled = logo.getImage().getScaledInstance(w/10,w/10,0);
				logo.setImage(scaled);
				nuovoLogo.setIcon(logo);
			}
		}
		
		/* Click pulsante "Annulla" */
		if(e.getSource().equals(annulla)) { mp.ritornaASceltaSquadra(); }
		
		/* Click pulsante "Torna al menu" */
		if(e.getSource().equals(menu)) { mp.ritornaAlMenu(this); }
		
		/* Click pulsante "Conferma modifiche" */
		if(e.getSource().equals(conferma)) {
			if(!nuovoNome.getText().equals("")) s.setNome(nuovoNome.getText());
			if(!nuovaSede.getText().equals("")) s.setSede(nuovaSede.getText());
			s.setLogo(percorsoLogo);
			
			/* Funzioni di aggiornamento degli oggetti inseriti nella finestra */
			aggiornaPagina(); revalidate(); repaint();
		}
	}
}






