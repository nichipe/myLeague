package main;

import gui.finestre.MenuIniziale;

/** Classe principale
 * <p>
 * Contiene il metodo main, da cui inizia l'esecuzione del programma.
 */
public class Main {
	/* Metodo main */
	public static void main(String[] args) {
		MenuIniziale f = new MenuIniziale("myLeague"); // creo un oggetto di tipo FinestraPrincipale
		f.setVisible(true); // rendo visibile l'oggetto
	}
}
