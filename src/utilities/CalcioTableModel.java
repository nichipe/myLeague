package utilities;

import javax.swing.table.AbstractTableModel;

import gestioneCampionato.ClassificaCalcio;

/** Modello classifica (calcio)
 * <p>
 * Implementa tutti i metodi dell'AbstractTableModel per poter inserire correttamente i dati nella tabella che mostra la classifica.
 */
public class CalcioTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L; //Versione dell'oggetto
	
	/** La classifica che si vuole mostrare */
	private ClassificaCalcio c; 
	/** Titoli delle colonne della classifica */
	private String[] nomeCol = {"","NOME","PUNTI","VINTE","PAREGGIATE","PERSE","GOL FATTI","GOL SUBITI","DIFF. GOL"};
	
	/** Costruttore
	 * 
	 * @param c La classifica che si vuole mostrare
	 */
	public CalcioTableModel(ClassificaCalcio c) { this.c = c; }
	
	/** Ritorna il numero delle colonne, ovvero 8 */
	@Override
	public int getColumnCount() { return 9; }
	
	/** Ritorna il nome della colonna */
	@Override
	public String getColumnName(int col) { return nomeCol[col]; }

	/** Ritorna il numero delle righe, ovvero il numero delle squadre */
	@Override
	public int getRowCount() { return c.getNumSquadre(); }
	
	/** Imposta ogni cella non modificabile dall'utente */
	@Override
	public boolean isCellEditable(int row,int col) { return false; }

	/** Ritorna il valore da inserire in ogni cella */
	@Override
	public Object getValueAt(int row, int col) {
		switch(col) {
			case 0: return row+1;
			case 1: return c.getNomeAtIndex(row); //Nome squadra
			case 2: return c.getPuntiAtIndex(row); //Punti
			case 3: return c.getVittorieAtIndex(row); //Vittorie
			case 4: return c.getPareggiAtIndex(row); //Pareggi
			case 5: return c.getSconfitteAtIndex(row); //Sconfitte
			case 6: return c.getGolFattiAtIndex(row); //Gol fatti
			case 7: return c.getGolSubitiAtIndex(row); //Gol subiti
			case 8: return c.getGolFattiAtIndex(row)-c.getGolSubitiAtIndex(row); //Differenza reti
		}
		return null;
	}
}
