package gui;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JLabel;

/** Label personalizzato
 * <p>
 * Funziona come un classico JLabel, ma ridimensiona in maniera automatica il font in base alle dimensioni dello stesso.
 */
@SuppressWarnings("serial")
public class DynamicLabel extends JLabel {
	/* Indica se il testo deve essere in grassetto */
	private boolean bold;
	
	/** Costruttore
	 * 
	 * @param bold Indica se il testo deve essere in grassetto
	 */
	public DynamicLabel(boolean bold) {
		this.bold = bold;
        this.addComponentListener(new FontHandler());
    }
    
	/** Costruttore
	 * 
	 * @param text Testo del label
	 * @param bold Indica se il testo deve essere in grassetto
	 */
    public DynamicLabel(String text,boolean bold){
        super(text);
        this.bold = bold;
        this.addComponentListener(new FontHandler());
    }
    
    protected void changeBold() {
    	if(bold) bold = false;
    	else bold = true;
    }
    
    /** Classe che funge da gestore di componente (in questo caso il label) per la gestione delle dimensioni del font */
    private class FontHandler extends ComponentAdapter{
    	/* Gestisce la dimensione del font */
    	@Override
        public void componentResized(ComponentEvent e) {
            Font font = getFont();
            FontMetrics metrics = getGraphics().getFontMetrics(font);
            int newSizeByWidth = 0;
            if(metrics.stringWidth(getText()) != 0) newSizeByWidth = font.getSize()*getWidth()/metrics.stringWidth(getText());
            else newSizeByWidth = font.getSize()*getWidth();
            int newSizeByHeight = font.getSize()*getHeight()/metrics.getHeight();
            if(bold) setFont(new Font("Trebuchet MS",Font.BOLD,java.lang.Math.min(newSizeByWidth, newSizeByHeight)));
            else setFont(new Font("Trebuchet MS",Font.PLAIN,java.lang.Math.min(newSizeByWidth, newSizeByHeight)));
        }
    }
}
