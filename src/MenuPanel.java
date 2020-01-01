
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel implements KeyListener {

	// saves reference to the GameManager so we can dispose of this panel from within
	private GameManager gm;
	
	/** constructor */
	public MenuPanel(GameManager gm) {
		
		// saves GameManager reference locally
		this.gm = gm;
		
		// needed to make the keyboard inputs apply to this
		setFocusable(true);
		
		// adds this class' key listener to this class' JPanel
		addKeyListener(this);
		
		setBackground(Color.BLACK);	// sets the background color of the panel
	
	}
		
	@Override
	public void keyPressed(KeyEvent arg0) {
		int keyID = arg0.getKeyCode();	// saves the ID of the key pressed;
		switch (keyID) {
		case KeyEvent.VK_ENTER:
			gm.startGame();
			break;
		case KeyEvent.VK_ESCAPE:
			System.out.println("Quitting Game.");
			gm.quitGame();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
		
		
	}
	
	/**
	 * overriding paintComponent allows us to paint things to the screen */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);	// passes g to overridden super method
	
		// cast the Graphics object to a Graphics2D object to allow us
		// access to specific painting methods
		Graphics2D g2 = (Graphics2D) g;
		
		// set brush color
		g2.setColor(Color.WHITE);
		
		
		
		
		
		g2.drawString("Use arrow keys to turn and spacebar to fire.", 100, 60);
		g2.drawString("Press 'esc' to exit at anytime.", 100, 80);
		
		g2.drawString("No menu coded in yet, click enter to begin.", 100, 100);
	
	}
	
	
	
	
	
	
}
