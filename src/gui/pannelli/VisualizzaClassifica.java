package gui.pannelli;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import gestioneCampionato.Campionato;
import gestioneCampionato.Classifica;
import gestioneCampionato.ClassificaCalcio;
import gestioneCampionato.ClassificaRugby;
import gestioneCampionato.ClassificaVolley;
import gui.bottoni.ConfirmButton;
import gui.finestre.MenuPrincipale;
import utilities.CalcioTableModel;
import utilities.RugbyTableModel;
import utilities.VolleyTableModel;

/** Pannello di visualizzazione della classifica
 * <p>
 * Mostra la tabella contenente la classifica, calcolata (se necessario) sulla base del regolamento del campionato.
 */
@SuppressWarnings("serial")
public class VisualizzaClassifica extends JPanel implements ActionListener{
	/** La finestra in cui e' contenuto il pannello */
	private MenuPrincipale mp; 
	/** Pulsante di ritorno al menu */
	private ConfirmButton menu; 
	/** Pulsante di stampa della classifica */
	
	/** Costruttore 
	 * 
	 * @param mp La finestra in cui e' contenuto il pannello
	 * @param c Il campionato di cui si sta mostrando la classifica
	 * @param calcola Indica se e' necessario ricalcolare la classifica
	 */
	public VisualizzaClassifica(MenuPrincipale mp,Campionato c,boolean calcola) {
		super();
		this.mp = mp;
		
		setLayout(null); // Imposto il layout manager nullo per poter inserire manualmente la posizione di ogni oggetto 
		
		setBackground(Color.white); // Imposto il colore di sfondo del pannello
		
		/* Calcolo le dimensioni (in pixel) del pannello per il posizionamento degli oggetti al suo interno */
		int w = mp.getWidth(); 
		int h = mp.getHeight();
		
		/* Creo l'oggetto titolo */
		Titolo titolo = new Titolo("Classifica",getBackground(),new Color(4,66,140),false);
		titolo.setBounds(0,h/20,w,h/10);
		
		/* Creo un separatore da inserire tra il titolo e gli altri elementi */
		JSeparator o = new JSeparator(SwingConstants.HORIZONTAL);
		o.setBounds(w/10,h*3/20,w*4/5,5);
		
		/* Creo la tabella che visualizza la classifica, dopo averla calcolata */
		Classifica cl = c.getClassifica();
		if(calcola) {
			cl.calcolaClassifica();
			mp.unsetCalcola();
		}
		TableModel m = null;
		if(c.getSport()==1)  m = new CalcioTableModel((ClassificaCalcio)cl);
		if(c.getSport()==2)  m = new VolleyTableModel((ClassificaVolley)cl);
		if(c.getSport()==3)  m = new RugbyTableModel((ClassificaRugby)cl);
		JTable classifica = new JTable(m);
		
		/* Elimino la possibilita' di ridimensionare le colonne */
		classifica.getTableHeader().setReorderingAllowed(false);
		classifica.getTableHeader().setResizingAllowed(false);
		
		classifica.getTableHeader().setFont(new Font("Trebuchet MS",Font.BOLD,12)); //Imposto il font dei titoli delle colonne
		classifica.getColumnModel().getColumn(0).setPreferredWidth(w/40); //Richiedo che la prima colonna sia più stretta per la posizione
		classifica.getColumnModel().getColumn(1).setPreferredWidth(w/6); //Richiedo che la seconda colonna sia più larga per il nome della squadra
		classifica.setRowHeight(h/20); //Imposto l'altezza di ogni riga
		
		/* Imposto l'allineamento di tutti i valori centrale */
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		for(int i=0;i<m.getColumnCount();i++) classifica.getColumnModel().getColumn(i).setCellRenderer(dtcr);
		
		JScrollPane sp = new JScrollPane(classifica);
		sp.setBounds(w/10,h*7/40,w*4/5,h*23/40);
		
		/* Creo il pulsante di ritorno al menu principale */
		menu = new ConfirmButton("Torna al menu");
		menu.setBounds(w*2/5,h*25/32,w/5,h/10);
		menu.addActionListener(this);
		
		/* Aggiungo gli elementi al pannello */
		add(titolo); add(o); //Titolo e separatore
		add(sp); //Scroll pane contentente la tabella 
		add(menu); //Pulsanti 
	}

	/** Metodo di gestione degli eventi generati dai pulsanti del pannello */
	@Override
	public void actionPerformed(ActionEvent e) { mp.ritornaAlMenu(this); }
}
