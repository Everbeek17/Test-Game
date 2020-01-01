
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameManager extends JFrame implements Runnable {
	
	// JPanels
	private MenuPanel mp;
	private GamePanel gp;
	
	private Thread gameThread;
	
	private boolean running = false;
	
	public static final double desiredFPS = 60D;
	
	// dimensions for the JFrame window
	private final int windowWidth = 800, windowHeight = 600;	
	
	/** Constructor */
	public GameManager() {
		// initialization stuff for the frame
		setTitle("Test");		// sets the title of the Window (the JFrame)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// frees up memory when the window is closed
		setSize(windowWidth, windowHeight);		// sets size of window
		setResizable(false);					// allows/disallows resizing of window
		setLocationByPlatform(true);			// lets the OS handle the positioning of the window
		
		
		
		setVisible(true);
	}
	
	public boolean isGameRunning() { return running; }

	/** Creates instance of Menu Panel and adds to this Game Panel. */
	private void buildMenu() {
		// instantiates a menu JPanel
		mp = new MenuPanel(this);
		// adds the menu JPanel to this JFrame
		add(mp);
	}
	
	/** removes the menu panel and creates/adds game panel. */
	public void startGame() {
		// removes menu panel
		remove(mp);
		
		// creates and adds game panel
		gp = new GamePanel(this);
		add(gp);
		
		// validate() needed to switch JPanels for some reason.
		validate();
		gp.requestFocusInWindow();	// gives input control to gp
		//repaint();

		
		// creates and starts the Thread for the gameThread
		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	/** disposes of this JFrame and the gameThread, aka ends the game. **/
	public void quitGame() { 
		running = false;	// stops gameThread
		// waits for gameThread to end
		try {
			gameThread.join();
		} catch (InterruptedException e) {e.printStackTrace();}
		this.dispose();
	}
	
	public void gameOver() {
		running = false;
		
		
		
	}
	
	
	
	/**
	 * This method is called when the GameThread begins. Contains main game loop.
	 */
	@Override
	public void run() {
		long prev_frame_tick = System.nanoTime(), curr_frame_tick, timeElapsed,
				prev_physics_tick = prev_frame_tick;
		double ns = 1000000000 / desiredFPS;
		double delta = 0;
		
		
		
		// Main Game Loop
		while (running) {
		    curr_frame_tick = System.nanoTime();
		    timeElapsed = curr_frame_tick - prev_frame_tick;
		    delta += timeElapsed / ns;
		    prev_frame_tick = curr_frame_tick;
		    
		    
		    // updates game 60 times a second
		    if (delta >= 1) {
		    	// sends the number of nanoseconds since the last physics tick happened
		    	updateGame(curr_frame_tick - prev_physics_tick);
		    	prev_physics_tick = curr_frame_tick;
		    	
		    	delta--;
		    }
		    // paints screen as many times as we can
		    gp.repaint();
		    
		    
		    
		}
	}
	
	/** does required calculations/updates required variables based on how much time has passed.
	 * @param timeElapsed time since last update in nanoseconds  */
	private void updateGame(double timeElapsed) {
		
		
		gp.updateGame(timeElapsed);

		
		
	}
	
	
	
	
	
	/** main class to instantiate an instance of this GameManager which
	 *  will also create the JFrame that everyone will appear on. */
	public static void main(String [] args) {
		
		GameManager gm = new GameManager();
		
		gm.buildMenu();
		
		
		
	}


	



}
