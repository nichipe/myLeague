package gui.pannelli;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import gestioneCampionato.Giornata;
import gui.DynamicLabel;

/** Panello che mostra una singola partita all'interno del calendario */
@SuppressWarnings("serial")
public class PannelloPartita extends JPanel{
	private DynamicLabel squadraCasa;
	private DynamicLabel squadraTrasferta;
	private DynamicLabel risultatoCasa;
	private DynamicLabel risultatoTrasferta;
	private ImageIcon logoCasa;
	private ImageIcon logoTrasferta;
	
	/** Costruttore
	 * 
	 * @param g La giornata che si deve mostrare
	 * @param partita La partita della giornata che si deve mostrare
	 * @param andata Indica se è una partita di andata o ritorno
	 * @param w Larghezza finestra
	 * @param h Altezza finestra
	 */
	public PannelloPartita(Giornata g,int partita,boolean andata,int w,int h) {
		super();
		
		setLayout(null);
		setBackground(Color.white); 
		
		DynamicLabel separatore = new DynamicLabel(" - ",true); //Per separare squadre e risultati
		separatore.setHorizontalAlignment(SwingConstants.CENTER);
		separatore.setVerticalAlignment(SwingConstants.CENTER);
		separatore.setBounds(w*19/40,0,w/20,h*3/4);
		
		squadraCasa = new DynamicLabel(false);
		squadraCasa.setBounds(w/10,0,w*3/10,h*3/4);
		squadraCasa.setHorizontalAlignment(SwingConstants.RIGHT);
		squadraCasa.setVerticalAlignment(SwingConstants.CENTER);
		squadraTrasferta = new DynamicLabel(false);
		squadraTrasferta.setBounds(w*12/20,0,w*3/10,h*3/4);
		squadraTrasferta.setHorizontalAlignment(SwingConstants.LEFT);
		squadraTrasferta.setVerticalAlignment(SwingConstants.CENTER);
		risultatoCasa = new DynamicLabel(true);
		risultatoCasa.setBounds(w*2/5,0,w/20,h*3/4);
		risultatoCasa.setHorizontalAlignment(SwingConstants.RIGHT);
		risultatoCasa.setVerticalAlignment(SwingConstants.CENTER);
		risultatoTrasferta = new DynamicLabel(true);
		risultatoTrasferta.setBounds(w*11/20,0,w/20,h*3/4);
		risultatoTrasferta.setHorizontalAlignment(SwingConstants.LEFT);
		risultatoTrasferta.setVerticalAlignment(SwingConstants.CENTER);
		
		if(andata) {
			squadraCasa.setText(g.getCasaAtIndex(partita).getNome());
			squadraTrasferta.setText(g.getTrasfertaAtIndex(partita).getNome());
			logoCasa = new ImageIcon(g.getCasaAtIndex(partita).getLogo());
			logoTrasferta = new ImageIcon(g.getTrasfertaAtIndex(partita).getLogo());
		}
		else {
			squadraCasa.setText(g.getTrasfertaAtIndex(partita).getNome());
			squadraTrasferta.setText(g.getCasaAtIndex(partita).getNome());
			logoCasa = new ImageIcon(g.getTrasfertaAtIndex(partita).getLogo());
			logoTrasferta = new ImageIcon(g.getCasaAtIndex(partita).getLogo());
		} 
		
		/* Inserisco i loghi */
		Image logo = logoCasa.getImage().getScaledInstance(h,h,0);
		logoCasa.setImage(logo);
		logo = logoTrasferta.getImage().getScaledInstance(h,h,0);
		logoTrasferta.setImage(logo);
		JLabel casaLogo = new JLabel(logoCasa);
		casaLogo.setBounds(w/40,0,h,h);
		JLabel trasfertaLogo = new JLabel(logoTrasferta);
		trasfertaLogo.setBounds(w*37/40,0,h,h);
		
		/* Inserisco, se ci sono, i risultati */
		if(andata && g.isRisultatiAndata()) {
			risultatoCasa.setText(""+g.getRisultatoCasaAndata(partita));
			risultatoTrasferta.setText(""+g.getRisultatoTrasfertaAndata(partita)); 
		}
		else if(g.isRisultatiRitorno()) {
			risultatoCasa.setText(""+g.getRisultatoCasaRitorno(partita));
			risultatoTrasferta.setText(""+g.getRisultatoTrasfertaRitorno(partita));	
		}
		
		add(casaLogo); add(squadraCasa); add(risultatoCasa); add(separatore); add(risultatoTrasferta); add(squadraTrasferta); add(trasfertaLogo);
	}
	
	/** Costruttore
	 * <p>
	 * Utilizzato per mostrare la partita di una squadra specifica.
	 * 
	 * @param g La giornata che si deve mostrare
	 * @param andata Indica se è una partita di andata o ritorno
	 * @param squadra Il nome della squadra di cui si mostra il calendario
	 * @param w Larghezza finestra
	 * @param h Altezza finestra
	 */
	public PannelloPartita(Giornata g,boolean andata,String squadra,int w,int h) {
		super();
		
		setLayout(null);
		setBackground(Color.white); 
		
		DynamicLabel separatore = new DynamicLabel(" - ",true); //Per separare squadre e risultati
		separatore.setHorizontalAlignment(SwingConstants.CENTER);
		separatore.setVerticalAlignment(SwingConstants.CENTER);
		separatore.setBounds(w*19/40,0,w/20,h*3/4);
		
		risultatoCasa = new DynamicLabel(true);
		risultatoCasa.setBounds(w*2/5,0,w/20,h*3/4);
		risultatoCasa.setHorizontalAlignment(SwingConstants.RIGHT);
		risultatoCasa.setVerticalAlignment(SwingConstants.CENTER);
		risultatoTrasferta = new DynamicLabel(true);
		risultatoTrasferta.setBounds(w*11/20,0,w/20,h*3/4);
		risultatoTrasferta.setHorizontalAlignment(SwingConstants.LEFT);
		risultatoTrasferta.setVerticalAlignment(SwingConstants.CENTER);
		
		/* Trovo l'indice della partita desiderata */
		int i=0;
		boolean casa = true;
		while(true) {
			if(g.getCasaAtIndex(i).getNome().equals(squadra)) break;
			if(g.getTrasfertaAtIndex(i).getNome().equals(squadra)) {
				casa = false;
				break;
			}
			i++;
		}
		
		if(andata) {
			if(casa) {
				squadraCasa = new DynamicLabel(g.getCasaAtIndex(i).getNome(),true);
				squadraCasa.setForeground(Color.red);
				squadraTrasferta = new DynamicLabel(g.getTrasfertaAtIndex(i).getNome(),false);
			}
			else {
				squadraCasa = new DynamicLabel(g.getCasaAtIndex(i).getNome(),false);
				squadraTrasferta = new DynamicLabel(g.getTrasfertaAtIndex(i).getNome(),true);
				squadraTrasferta.setForeground(Color.red);
			}
			logoCasa = new ImageIcon(g.getCasaAtIndex(i).getLogo());
			logoTrasferta = new ImageIcon(g.getTrasfertaAtIndex(i).getLogo());
		}
		else {
			if(casa) {
				squadraCasa = new DynamicLabel(g.getTrasfertaAtIndex(i).getNome(),false);
				squadraTrasferta = new DynamicLabel(g.getCasaAtIndex(i).getNome(),true);
				squadraTrasferta.setForeground(Color.red);
			}
			else {
				squadraCasa = new DynamicLabel(g.getTrasfertaAtIndex(i).getNome(),true);
				squadraCasa.setForeground(Color.red);
				squadraTrasferta = new DynamicLabel(g.getCasaAtIndex(i).getNome(),false);
			}
			logoCasa = new ImageIcon(g.getTrasfertaAtIndex(i).getLogo());
			logoTrasferta = new ImageIcon(g.getCasaAtIndex(i).getLogo());
		}
		squadraCasa.setBounds(w/10,0,w*3/10,h*3/4);
		squadraCasa.setHorizontalAlignment(SwingConstants.RIGHT);
		squadraCasa.setVerticalAlignment(SwingConstants.CENTER);
		squadraTrasferta.setBounds(w*12/20,0,w*3/10,h*3/4);
		squadraTrasferta.setHorizontalAlignment(SwingConstants.LEFT);
		squadraTrasferta.setVerticalAlignment(SwingConstants.CENTER);
		
		/* Inserisco i loghi */
		Image logo = logoCasa.getImage().getScaledInstance(h,h,0);
		logoCasa.setImage(logo);
		logo = logoTrasferta.getImage().getScaledInstance(h,h,0);
		logoTrasferta.setImage(logo);
		JLabel casaLogo = new JLabel(logoCasa);
		casaLogo.setBounds(w/20,0,h,h);
		JLabel trasfertaLogo = new JLabel(logoTrasferta);
		trasfertaLogo.setBounds(w*9/10,0,h,h);
		
		/* Inserisco, se ci sono, i risultati */
		if(andata && g.isRisultatiAndata()) {
			risultatoCasa.setText(""+g.getRisultatoCasaAndata(i));
			risultatoTrasferta.setText(""+g.getRisultatoTrasfertaAndata(i)); 
		}
		else if(g.isRisultatiRitorno()) {
			risultatoCasa.setText(""+g.getRisultatoCasaRitorno(i));
			risultatoTrasferta.setText(""+g.getRisultatoTrasfertaRitorno(i));
		}	
		
		add(casaLogo); add(squadraCasa); add(risultatoCasa); add(separatore); add(risultatoTrasferta); add(squadraTrasferta); add(trasfertaLogo);
	}
}
