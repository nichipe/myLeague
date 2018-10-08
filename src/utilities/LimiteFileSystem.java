package utilities;

import java.io.File;
import java.io.IOException;
import javax.swing.filechooser.FileSystemView;

/** Oggetto di modifica del file chooser
 * <p>
 * Limita la visualizzazione del file system solo ad una directory e a tutte le sue sottodirectory.<br>
 * Utilizzato per evitare che l'utente cerchi di aprire file non validi.<br>
 */
public class LimiteFileSystem extends FileSystemView {
	/** Cartella in cui si posiziona inizialmente il file chooser */
	private File dir;
	/** Array delle cartelle in cui è possibile cercare all'interno del file chooser */
	private File[] dirs;

	/** Costruttore
	 * 
	 * @param dir Cartella in cui il file chooser si posiziona
	 */
	public LimiteFileSystem(File dir) {
		try { this.dir = dir.getCanonicalFile(); } //Cerca forma standard del percorso del file
		catch(IOException e) {}
		
		dirs = new File[1];
		dirs[0] = dir;
	}
	
	/** Metodo che permette di creare una nuova cartella attraverso il file chooser */
	@Override
	public File createNewFolder(File dir)
	{
		File folder = new File(dir, "New Folder");
		folder.mkdir();
		return folder;
	}

	/** Ritorna la cartella di default */
	@Override
	public File getDefaultDirectory() { return dir; }

	/** Ritorna la cartella home (in questo caso coincide con quella di default */
	@Override
	public File getHomeDirectory() { return dir; }

	/** Ritorna l'array delle cartelle in cui è possibile cercare il file */
	@Override
	public File[] getRoots() { return dirs; }
}
