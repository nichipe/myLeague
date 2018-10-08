package gestioneCampionato;

/** Oggetto classifica specializzato (rugby)
 * <p>
 * Implementa i metodi di calcolo delle statistiche e della classifica sulla base delle regole del rugby.<br>
 */
public class ClassificaRugby extends Classifica{
	/**Il campionato di cui si sta calcolando la classifica */
	private Campionato c; 
	/**Numero gol segnati da ogni squadra */
	private int[] puntiFatti; 
	/**Numero gol subiti da ogni squadra */
	private int[] puntiSubiti; 
	/**Numero vittorie di ogni squadra */
	private int[] vittorie; 
	/**Numero pareggi di ogni squadra */
	private int[] sconfitte; 
	/**Numero sconfitte di ogni squadra */
	private int[] pareggi; 
	/**Numero mete messe a segno da ogni squadra */
	private int[] mete; 
	
	/** Costruttore
	 * 
	 * @param c Il campionato di cui si vuole calcolare la classifica 
	 */
	public ClassificaRugby(Campionato c) {
		super(c.getNumSquadre());
		this.c = c;
		
		/* Creo tutti i vettori delle statistiche */
		puntiFatti = new int[numSquadre];
		puntiSubiti = new int[numSquadre];
		vittorie = new int[numSquadre];
		sconfitte = new int[numSquadre];
		pareggi = new int[numSquadre];
		mete = new int[numSquadre];
		
		/* Azzero tutti i vettori delle statistiche */
		azzeraStatistiche();
	}
	
	/** Metodo di reset delle statistiche
	 * <p>
	 * Riporta tutti i valori delle statistiche al default (0) per poterle ricalcolare.<br>
	 */
	private void azzeraStatistiche() {
		for(int i=0;i<numSquadre;i++) punti[i] = puntiFatti[i] = puntiSubiti[i] = vittorie[i] = sconfitte[i] = pareggi[i] = mete[i] = 0;
	}

	/** Metodo di calcolo delle statistiche (rugby)
	 * <p>
	 * Vengono calcolati numero di vittorie, pareggi e sconfitte, punti segnati e subiti, mete e punti di classifica, cosi' assegnati:<br>
	 * <li>+4 per la vittoria
	 * <li>+2 per il pareggio
	 * <li>0 per la sconfitta
	 * <p> 
	 * Inoltre vengono assegnati punti bonus per:<br>
	 * <li>METE: la squadra che segna 4 o piu' mete guadagna un punto a prescindere dal risultato
	 * <li>DIFESA: la squadra che perde con uno scarto di 7 o meno punti guadagna un punto
	 */
	@Override
	protected void calcolaStatistiche() {
		azzeraStatistiche(); //Porto le statistiche a 0 prima di ricalcolarle
		
		for(int i=0;i<numSquadre-1;i++) {
			Giornata g = c.getCalendario().getGiornata(i); //Giornata i-esima
			
			/* RISULTATI ANDATA */
			if(g.isRisultatiAndata()) {
				for(int j=0;j<numSquadre/2;j++) {
					/* Vittoria squadra di casa */
					if(g.getRisultatoCasaAndata(j)>g.getRisultatoTrasfertaAndata(j)) {
						punti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += 4;
						vittorie[c.getIndexAtName(g.getCasaAtIndex(j).getNome())]++;
						sconfitte[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())]++;
						if((g.getRisultatoCasaAndata(j)-g.getRisultatoTrasfertaAndata(j)) <= 7) punti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())]++; //Bonus difesa
					}
					
					/* Vittoria squadra in trasferta */
					else if(g.getRisultatoCasaAndata(j)<g.getRisultatoTrasfertaAndata(j)) {
						punti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += 4;
						vittorie[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())]++;
						sconfitte[c.getIndexAtName(g.getCasaAtIndex(j).getNome())]++;
						if((g.getRisultatoTrasfertaAndata(j)-g.getRisultatoCasaAndata(j)) <= 7) punti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())]++; //Bonus difesa
					}
					
					/* Pareggio */
					else {
						punti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += 2;
						punti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += 2;
						pareggi[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())]++;
						pareggi[c.getIndexAtName(g.getCasaAtIndex(j).getNome())]++;
					}
					
					/* Bonus mete */
					if(g.getRisultatoSecondarioCasaAndata(j) >= 4) punti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())]++;
					if(g.getRisultatoSecondarioTrasfertaAndata(j) >= 4) punti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())]++;
					
					/* Aggiornamento mete */
					mete[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += g.getRisultatoSecondarioCasaAndata(j);
					mete[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += g.getRisultatoSecondarioTrasfertaAndata(j);
					
					/* Aggiornamento punti */
					puntiFatti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += g.getRisultatoCasaAndata(j);
					puntiFatti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += g.getRisultatoTrasfertaAndata(j);
					puntiSubiti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += g.getRisultatoTrasfertaAndata(j);
					puntiSubiti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += g.getRisultatoCasaAndata(j);
				}
			}
			
			/* RISULTATI RITORNO */
			if(g.isRisultatiRitorno()) {
				for(int j=0;j<numSquadre/2;j++) {
					/* Vittoria squadra di casa */
					if(g.getRisultatoCasaRitorno(j)>g.getRisultatoTrasfertaRitorno(j)) {
						punti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += 4;
						vittorie[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())]++;
						sconfitte[c.getIndexAtName(g.getCasaAtIndex(j).getNome())]++;
						if((g.getRisultatoCasaRitorno(j)-g.getRisultatoTrasfertaRitorno(j)) <= 7) punti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())]++; //Bonus difesa
					}
					
					/* Vittoria squadra in trasferta */
					else if(g.getRisultatoCasaRitorno(j)<g.getRisultatoTrasfertaRitorno(j)) {
						punti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += 4;
						vittorie[c.getIndexAtName(g.getCasaAtIndex(j).getNome())]++;
						sconfitte[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())]++;
						if((g.getRisultatoTrasfertaRitorno(j)-g.getRisultatoCasaRitorno(j)) <= 7) punti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())]++; //Bonus difesa
					}
					
					/* Pareggio */
					else {
						punti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += 2;
						punti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += 2;
						pareggi[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())]++;
						pareggi[c.getIndexAtName(g.getCasaAtIndex(j).getNome())]++;
					}
					
					/* Bonus mete */
					if(g.getRisultatoSecondarioTrasfertaAndata(j) >= 4) punti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())]++;
					if(g.getRisultatoSecondarioCasaAndata(j) >= 4) punti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())]++;
					
					/* Aggiornamento mete */
					mete[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += g.getRisultatoSecondarioCasaAndata(j);
					mete[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += g.getRisultatoSecondarioTrasfertaAndata(j);
					
					/* Aggiornamento punti */
					puntiFatti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += g.getRisultatoTrasfertaRitorno(j);
					puntiFatti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += g.getRisultatoCasaRitorno(j);
					puntiSubiti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += g.getRisultatoCasaRitorno(j);
					puntiSubiti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += g.getRisultatoTrasfertaRitorno(j);
				}
			}
		}
	}
	
	/** Metodo di calcolo della classifica (rugby)
	 * <p>
	 * L'ordine delle squadre è calcolato basandosi sui seguenti criteri:<br>
	 * <ol>
	 * <li>maggior numero di punti;
	 * <li>maggior differenza punti (differenza tra punti segnati e subiti);
	 * <li>maggior numero di punti segnati;
	 * <li>minor numero di punti subiti;
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
		int diffPunti1 = puntiFatti[posizione[j]] - puntiSubiti[posizione[j]];
		int diffPunti2 = puntiFatti[posizione[j+1]] - puntiSubiti[posizione[j+1]];
		
		/* Controllo differenza punti */
		if(diffPunti1>diffPunti2) return false;
		if(diffPunti1<diffPunti2) return true;
		
		/* Controllo punti fatti */
		if(puntiFatti[posizione[j]]>puntiFatti[posizione[j+1]]) return false;
		if(puntiFatti[posizione[j]]<puntiFatti[posizione[j+1]]) return true;
		
		/* Controllo punti subiti */
		if(puntiSubiti[posizione[j]]<puntiSubiti[posizione[j+1]]) return false;
		if(puntiSubiti[posizione[j]]>puntiSubiti[posizione[j+1]]) return true;
		
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
	
	/** Getter del numero di pareggi della squadra alla i-esima posizione in classifica 
	 * 
	 * @param i Indice della squadra
	 * @return Numero pareggi della squadra all'indice i
	 */
	public int getPareggiAtIndex(int i) { return pareggi[posizione[i]]; }
	
	/** Getter del numero di gol fatti della squadra alla i-esima posizione in classifica 
	 * 
	 * @param i Indice della squadra
	 * @return Numero gol fatti dalla squadra all'indice i
	 */
	public int getPuntiFattiAtIndex(int i) { return puntiFatti[posizione[i]]; }
	
	/** Getter del numero di gol subiti della squadra alla i-esima posizione in classifica 
	 * 
	 * @param i Indice della squadra
	 * @return Numero gol subiti dalla squadra all'indice i
	 */
	public int getPuntiSubitiAtIndex(int i) { return puntiSubiti[posizione[i]]; }

	/** Getter del numero di mete messe a segno della squadra alla i-esima posizione in classifica 
	 * 
	 * @param i Indice della squadra
	 * @return Numero mete messe a segno dalla squadra all'indice i
	 */
	public int getMeteAtIndex(int i) { return mete[posizione[i]]; }
}

