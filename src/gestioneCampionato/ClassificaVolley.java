package gestioneCampionato;

/** Oggetto classifica specializzato (pallavolo)
 * <p>
 * Implementa i metodi di calcolo delle statistiche e della classifica sulla base delle regole della pallavolo.<br>
 */
public class ClassificaVolley extends Classifica{
	/**Il campionato di cui si sta calcolando la classifica */
	private Campionato c; 
	/**Numero di vittorie */
	private int[] vittorie; 
	 /**Numero di sconfitte */
	private int[] sconfitte;
	/**Numero di set vinti */
	private int[] setVinti; 
	/**Numero di set persi */
	private int[] setPersi; 
	/**Numero di punti fatti */
	private int[] puntiFatti; 
	/**Numero di punti subiti */
	private int[] puntiSubiti; 
	/**Quoziente set (vinti/persi) */
	private double[] quozienteSet; 
	/**Quoziente punti (fatti/subiti) */
	private double[] quozientePunti; 
	
	/** Costruttore
	 * 
	 * @param c Il campionato di cui si vuole calcolare la classifica 
	 */
	public ClassificaVolley(Campionato c) {
		super(c.getNumSquadre());
		this.c = c;
		
		/* Creo tutti i vettori delle statistiche */
		vittorie = new int[numSquadre];
		sconfitte = new int[numSquadre];
		setVinti = new int[numSquadre];
		setPersi = new int[numSquadre];
		quozienteSet = new double[numSquadre];
		puntiFatti = new int[numSquadre];
		puntiSubiti = new int[numSquadre];
		quozientePunti = new double[numSquadre];
		
		/* Azzero tutti i vettori delle statistiche */
		azzeraStatistiche();
	}
	
	/** Metodo di reset delle statistiche
	 * <p>
	 * Riporta tutti i valori delle statistiche al default (0) per poterle ricalcolare.<br>
	 */
	private void azzeraStatistiche() {
		for(int i=0;i<numSquadre;i++) {
			punti[i] = vittorie[i] = sconfitte[i] = setVinti[i] = setPersi[i] = puntiFatti[i] = puntiSubiti[i] = 0;
			quozienteSet[i] = quozientePunti[i] = 0.0;
		}
	}
	
	/** Metodo di calcolo delle statistiche (volley)
	 * <p>
	 * Vengono calcolati numero di vittorie e sconfitte, set vinti e persi, punti fatti e subiti e punti partita, cosi' assegnati:<br>
	 * <li>+3 per la vittoria
	 * <li>0 per la sconfitta
	 * <p>
	 * In caso di partita al <i>tie break</i> (3-2) i punti sono così assegnati:<br>
	 * <li>+2 alla vincente
	 * <li>+1 alla perdente
	 */
	@Override
	protected void calcolaStatistiche() {
		azzeraStatistiche(); //Porto le statistiche al default prima di ricalcolarle
		
		for(int i=0;i<numSquadre-1;i++) {
			Giornata g = c.getCalendario().getGiornata(i); //Giornata i-esima
			
			/* RISULTATI ANDATA */
			if(g.isRisultatiAndata()) {
				for(int j=0;j<numSquadre/2;j++) {
					/* Vittoria squadra di casa */
					if(g.getRisultatoCasaAndata(j)>g.getRisultatoTrasfertaAndata(j)) {
						vittorie[c.getIndexAtName(g.getCasaAtIndex(j).getNome())]++;
						sconfitte[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())]++;
						/* Vittoria al tie break */
						if(g.getRisultatoCasaAndata(j)-g.getRisultatoTrasfertaAndata(j) == 1) {
							punti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += 2;
							punti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += 1;
						}
						else punti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += 3;
					}
					
					/* Vittoria squadra in trasferta */
					else {
						vittorie[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())]++;
						sconfitte[c.getIndexAtName(g.getCasaAtIndex(j).getNome())]++;
						/* Vittoria al tie break */
						if(g.getRisultatoTrasfertaAndata(j)-g.getRisultatoCasaAndata(j) == 1) {
							punti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += 2;
							punti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += 1;
						}
						else punti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += 3;
					}
					
					/* Aggiornamento punti */
					puntiFatti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += g.getRisultatoSecondarioCasaAndata(j);
					puntiFatti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += g.getRisultatoSecondarioTrasfertaAndata(j);
					puntiSubiti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += g.getRisultatoSecondarioTrasfertaAndata(j);
					puntiSubiti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += g.getRisultatoSecondarioCasaAndata(j);
					
					/* Aggiornamento set */
					setVinti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += g.getRisultatoCasaAndata(j);
					setVinti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += g.getRisultatoTrasfertaAndata(j);
					setPersi[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += g.getRisultatoTrasfertaAndata(j);
					setPersi[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += g.getRisultatoCasaAndata(j);
				}
			}
			
			/* RISULTATI RITORNO */
			if(g.isRisultatiRitorno()) {
				for(int j=0;j<numSquadre/2;j++) {
					/* Vittoria squadra di casa */
					if(g.getRisultatoCasaRitorno(j)>g.getRisultatoTrasfertaRitorno(j)) {
						vittorie[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())]++;
						sconfitte[c.getIndexAtName(g.getCasaAtIndex(j).getNome())]++;
						/* Vittoria al tie break */
						if(g.getRisultatoCasaRitorno(j)-g.getRisultatoTrasfertaRitorno(j) == 1) {
							punti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += 2;
							punti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += 1;
						}
						else punti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += 3;
					}
					
					/* Vittoria squadra in trasferta */
					else {
						vittorie[c.getIndexAtName(g.getCasaAtIndex(j).getNome())]++;
						sconfitte[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())]++;
						/* Vittoria al tie break */
						if(g.getRisultatoTrasfertaRitorno(j)-g.getRisultatoCasaRitorno(j) == 1) {
							punti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += 2;
							punti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += 1;
						}
						else punti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += 3;
					}
					
					/* Aggiornamento punti */
					puntiFatti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += g.getRisultatoSecondarioCasaAndata(j);
					puntiFatti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += g.getRisultatoSecondarioTrasfertaAndata(j);
					puntiSubiti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += g.getRisultatoSecondarioTrasfertaAndata(j);
					puntiSubiti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += g.getRisultatoSecondarioCasaAndata(j);
					
					/* Aggiornamento set */
					setVinti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += g.getRisultatoCasaRitorno(j);
					setVinti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += g.getRisultatoTrasfertaRitorno(j);
					setPersi[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += g.getRisultatoTrasfertaRitorno(j);
					setPersi[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += g.getRisultatoCasaRitorno(j);
				}
			}
		}
		
		/* Calcolo quozienti */
		for(int i=0;i<numSquadre;i++) {
			if(setPersi[i]!=0) quozienteSet[i] = (double)setVinti[i]/setPersi[i];
			else quozienteSet[i] = setVinti[i];
			
			if(puntiSubiti[i]!=0) quozientePunti[i] = (double)puntiFatti[i]/puntiSubiti[i];
			else quozientePunti[i] = puntiFatti[i];
		}
	}

	/** Metodo di calcolo della classifica (volley)
	 * <p>
	 * L'ordine delle squadre è calcolato basandosi sui seguenti criteri:<br>
	 * <ol>
	 * <li>maggior numero di punti;
	 * <li>maggior numero di vittorie;
	 * <li>miglior quoziente set (vinti/persi);
	 * <li>miglior quoziente punti (fatti/subiti);
	 * <li>ordine alfabetico.
	 * </ol>
	 */
	@Override
	public void calcolaClassifica() {
		calcolaStatistiche(); //Calcolo le statistiche su cui fare i confronti per estrarre la classifica
		
		for(int i=1;i<numSquadre;i++) {
			int k = i;
			int j = i-1;
			
			for(;j>=0;j--) {
				if(punti[posizione[j]]<punti[posizione[k]] || (punti[posizione[j]]<=punti[posizione[k]] && controllo(j))) {
					int tmp = posizione[k];
					posizione[k] = posizione[j];
					posizione[j] = tmp;
					k = j;
				}
				else break;
			}
		}
		
	}
	
	/** Metodo di risoluzione del punteggio
	 * <p>
	 * A parita' di punteggio calcola, seguendo la gerarchia, se le squadre vanno scambiate o meno.<br>
	 * 
	 * @param j Indice della squadra che ha pari punti con la sua successiva
	 * @return 'true' se le squadre vanno scambiate, altrimenti 'false'
	 */
	@Override
	protected boolean controllo(int j) {
		/* Controllo vittore */
		if(vittorie[posizione[j]]>vittorie[posizione[j+1]]) return false;
		if(vittorie[posizione[j]]<vittorie[posizione[j+1]]) return true;
		
		/* Controllo quoziente set */
		if(quozienteSet[posizione[j]]>quozienteSet[posizione[j+1]]) return false;
		if(quozienteSet[posizione[j]]<quozienteSet[posizione[j+1]]) return true;
		
		/* Controllo quoziente punti */
		if(quozientePunti[posizione[j]]>quozientePunti[posizione[j+1]]) return false;
		if(quozientePunti[posizione[j]]<quozientePunti[posizione[j+1]]) return true;
		
		return false;
	}
	
	/** Getter del nome della squadra alla i-esima posizione in classifica 
	 * 
	 * @param i Indice della squadra
	 * @return Nome della squadra all'indice i
	 */
	public String getNomeAtIndex(int i) { return c.getSquadraAtIndex(posizione[i]).getNome(); }
	
	/** Getter dei punti della squadra alla i-esima posizione in classifica 
	 * 
	 * @param i Indice della squadra
	 * @return Posizione della squadra all'indice i
	 */
	public int getPuntiAtIndex(int i) { return punti[posizione[i]]; }
	
	/** Getter del numero di vittorie della squadra alla i-esima posizione in classifica 
	 * 
	 * @param i Indice della squadra
	 * @return Numero vittorie della squadra all'indice i
	 */
	public int getVittorieAtIndex(int i) { return vittorie[posizione[i]]; }
	
	/** Getter del numero di sconfitte della squadra alla i-esima posizione in classifica 
	 * 
	 * @param i Indice della squadra
	 * @return Numero sconfitte della squadra all'indice i
	 */
	public int getSconfitteAtIndex(int i) { return sconfitte[posizione[i]]; }
	
	/** Getter del numero di set vinti della squadra alla i-esima posizione in classifica 
	 * 
	 * @param i Indice della squadra
	 * @return Numero set vinti dalla squadra all'indice i
	 */
	public int getSetVintiAtIndex(int i) { return setVinti[posizione[i]]; }
	
	/** Getter del numero di set persi della squadra alla i-esima posizione in classifica 
	 * 
	 * @param i Indice della squadra
	 * @return Numero set persi dalla squadra all'indice i
	 */
	public int getSetPersiAtIndex(int i) { return setPersi[posizione[i]]; }
	
	/** Getter del quoziente set della squadra alla i-esima posizione in classifica 
	 * 
	 * @param i Indice della squadra
	 * @return Quoziente set dalla squadra all'indice i
	 */
	public double getQuozienteSetAtIndex(int i) { return quozienteSet[posizione[i]]; }
	
	/** Getter del numero di punti fatti della squadra alla i-esima posizione in classifica 
	 * 
	 * @param i Indice della squadra
	 * @return Numero punti fatti dalla squadra all'indice i
	 */
	public int getPuntiFattiAtIndex(int i) { return puntiFatti[posizione[i]]; }
	
	/** Getter del numero di punti subiti della squadra alla i-esima posizione in classifica 
	 * 
	 * @param i Indice della squadra
	 * @return Numero punti subiti dalla squadra all'indice i
	 */
	public int getPuntiSubitiAtIndex(int i) { return puntiSubiti[posizione[i]]; }
	
	/** Getter del quoziente punti della squadra alla i-esima posizione in classifica 
	 * 
	 * @param i Indice della squadra
	 * @return Quoziente punti dalla squadra all'indice i
	 */
	public double getQuozientePuntiAtIndex(int i) { return quozientePunti[posizione[i]]; }
}
