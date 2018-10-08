package gestioneCampionato;

/** Oggetto classifica specializzato (calcio)
 * <p>
 * Implementa i metodi di calcolo delle statistiche e della classifica sulla base delle regole del calcio.<br>
 */
public class ClassificaCalcio extends Classifica{
	/**Il campionato di cui si sta calcolando la classifica */
	private Campionato c; 
	/**Numero gol segnati da ogni squadra */
	private int[] golFatti; 
	/**Numero gol subiti da ogni squadra */
	private int[] golSubiti; 
	/**Numero vittorie di ogni squadra */
	private int[] vittorie; 
	/**Numero pareggi di ogni squadra */
	private int[] sconfitte; 
	/**Numero sconfitte di ogni squadra */
	private int[] pareggi; 
 	
	/** Costruttore
	 * 
	 * @param c Il campionato di cui si vuole calcolare la classifica 
	 */
	public ClassificaCalcio(Campionato c) {
		super(c.getNumSquadre());
		this.c = c;
		
		/* Creo tutti i vettori delle statistiche */
		golFatti = new int[numSquadre];
		golSubiti = new int[numSquadre];
		vittorie = new int[numSquadre];
		sconfitte = new int[numSquadre];
		pareggi = new int[numSquadre];
		
		/* Azzero tutti i vettori delle statistiche */
		azzeraStatistiche();
	}
	
	/** Metodo di reset delle statistiche
	 * <p> 
	 * Riporta tutti i valori delle statistiche al default (0) per poterle ricalcolare.<br>
	 */
	private void azzeraStatistiche() {
		for(int i=0;i<numSquadre;i++) punti[i] = golFatti[i] = golSubiti[i] = vittorie[i] = sconfitte[i] = pareggi[i] = 0;
	}

	/** Metodo di calcolo delle statistiche (calcio)
	 * <p>
	 * Vengono calcolati numero di vittorie, pareggi e sconfitte, gol segnati e subiti e punti, cosi' assegnati:<br>
	 * <li>+3 per la vittoria
	 * <li>+1 per il pareggio
	 * <li>0 per la sconfitta
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
						punti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += 3;
						vittorie[c.getIndexAtName(g.getCasaAtIndex(j).getNome())]++;
						sconfitte[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())]++;
					}
					
					/* Vittoria squadra in trasferta */
					else if(g.getRisultatoCasaAndata(j)<g.getRisultatoTrasfertaAndata(j)) {
						punti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += 3;
						vittorie[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())]++;
						sconfitte[c.getIndexAtName(g.getCasaAtIndex(j).getNome())]++;
					}
					
					/* Pareggio */
					else {
						punti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += 1;
						punti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += 1;
						pareggi[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())]++;
						pareggi[c.getIndexAtName(g.getCasaAtIndex(j).getNome())]++;
					}
					
					/* Aggiornamento gol */
					golFatti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += g.getRisultatoCasaAndata(j);
					golFatti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += g.getRisultatoTrasfertaAndata(j);
					golSubiti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += g.getRisultatoTrasfertaAndata(j);
					golSubiti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += g.getRisultatoCasaAndata(j);
				}
			}
			
			/* RISULTATI RITORNO */
			if(g.isRisultatiRitorno()) {
				for(int j=0;j<numSquadre/2;j++) {
					/* Vittoria squadra di casa */
					if(g.getRisultatoCasaRitorno(j)>g.getRisultatoTrasfertaRitorno(j)) {
						punti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += 3;
						vittorie[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())]++;
						sconfitte[c.getIndexAtName(g.getCasaAtIndex(j).getNome())]++;
					}
					
					/* Vittoria squadra in trasferta */
					else if(g.getRisultatoCasaRitorno(j)<g.getRisultatoTrasfertaRitorno(j)) {
						punti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += 3;
						vittorie[c.getIndexAtName(g.getCasaAtIndex(j).getNome())]++;
						sconfitte[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())]++;
					}
					
					/* Pareggio */
					else {
						punti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += 1;
						punti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += 1;
						pareggi[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())]++;
						pareggi[c.getIndexAtName(g.getCasaAtIndex(j).getNome())]++;
					}
					
					/* Aggiornamento gol */
					golFatti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += g.getRisultatoTrasfertaRitorno(j);
					golFatti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += g.getRisultatoCasaRitorno(j);
					golSubiti[c.getIndexAtName(g.getCasaAtIndex(j).getNome())] += g.getRisultatoCasaRitorno(j);
					golSubiti[c.getIndexAtName(g.getTrasfertaAtIndex(j).getNome())] += g.getRisultatoTrasfertaRitorno(j);
				}
			}
		}
	}
	
	/** Metodo di calcolo della classifica (calcio)
	 * <p>
	 * L'ordine delle squadre è calcolato basandosi sui seguenti criteri:<br>
	 * <ol>
	 * <li>maggior numero di punti;
	 * <li>maggior differenza reti (differenza tra gol segnati e subiti);
	 * <li>maggior numero di gol segnati;
	 * <li>minor numero di gol subiti;
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
		int diffReti1 = golFatti[posizione[j]] - golSubiti[posizione[j]];
		int diffReti2 = golFatti[posizione[j+1]] - golSubiti[posizione[j+1]];
		
		/* Controllo differenza reti */
		if(diffReti1>diffReti2) return false;
		if(diffReti1<diffReti2) return true;
		
		/* Controllo gol fatti */
		if(golFatti[posizione[j]]>golFatti[posizione[j+1]]) return false;
		if(golFatti[posizione[j]]<golFatti[posizione[j+1]]) return true;
		
		/* Controllo gol subiti */
		if(golSubiti[posizione[j]]<golSubiti[posizione[j+1]]) return false;
		if(golSubiti[posizione[j]]>golSubiti[posizione[j+1]]) return true;
		
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
	public int getGolFattiAtIndex(int i) { return golFatti[posizione[i]]; }
	
	/** Getter del numero di gol subiti della squadra alla i-esima posizione in classifica 
	 * 
	 * @param i Indice della squadra
	 * @return Numero gol subiti dalla squadra all'indice i
	 */
	public int getGolSubitiAtIndex(int i) { return golSubiti[posizione[i]]; }
}


















