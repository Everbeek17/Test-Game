import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements KeyListener {

	
	// saves reference to Game Manager so we can do some top-end tasks
	private GameManager gm;
	
	private Player player;
	private HUD hud;
	
	private Random randomizer = new Random();
	
	// array list of all the enemies
	private ArrayList<Enemy> Enemies = new ArrayList<Enemy>();
	
	// array list of all money drops
	private ArrayList<MoneyDrop> MoneyDrops = new ArrayList<MoneyDrop>();
	
	/** Constructor */
	public GamePanel(GameManager gm) {
		this.gm = gm;	// saves reference
		
		// needed to make the keyboard inputs apply to this
		setFocusable(true);
		
		// adds this class' key listener to this class' JPanel
		addKeyListener(this);
		
		//setBackground(Color.WHITE);	// sets the background color of the panel
		
		// instantiates a player and the HUD
		player = new Player();
		hud = new HUD(player);
		
		
		
		
	}
	
	
	/** does required calculations/updates required variables based on how much time has passed.
	 * @param timeElapsed time since last update in nanoseconds  */
	public void updateGame(double timeElapsed) {
		
		// increments player
		player.increment(timeElapsed);
		
		// increments all enemies
		for (int i = 0; i < Enemies.size(); i++) {
			Enemy currentEnemy = Enemies.get(i);
			currentEnemy.increment(timeElapsed);
			// checks if enemy has hit player
			if (player.hasEnemyHitMe(currentEnemy)) {
				// removes enemy
				Enemies.remove(i--);
				// take away one life then check if still alive
				if (!player.decrementLivesAndCheckIfStillAlive()) {
					gm.gameOver();	// ends game
				}
			}
		}
		
		// increments all MoneyDrops
		for (int i = 0; i < MoneyDrops.size(); i++) {
			MoneyDrop currentMoneyDrop = MoneyDrops.get(i);
			currentMoneyDrop.increment(timeElapsed);
			if (!currentMoneyDrop.stillAlive())
				MoneyDrops.remove(i--);	// deletes money drop if it's done
		}
		
		// increments all non-expired bullets
		for (int i = 0; i < player.Bullets.size(); i++) {
			Bullet currBullet = player.Bullets.get(i);
			currBullet.increment(timeElapsed);
			// if currBullet is now out of bounds
			// checks every frame if every bullet is within the playArea
			if (!player.isBulletWithinPlayArea(currBullet))
				player.Bullets.remove(i--);	// decrements i after removal to not miss any subsequent bullets as size() decrements after a removal
			
			
			// check this bullet against all enemies
			for (int j = 0; j < Enemies.size(); j++) {
				Enemy currEnemy = Enemies.get(j);
				
				// if current bullet has hit current enemy
				if (currEnemy.hasBulletHitMe(currBullet)) {
					currEnemy.damage(currBullet.getStrength());	// damages the enemy by the strength of the bullet
					if (currEnemy.getHealth() <= 0) {
						// creates a visual Money Drop on enemy death
						Enemy dyingEnemy = Enemies.get(j);	// saves the enemy that is currently dying
						MoneyDrops.add(new MoneyDrop(dyingEnemy.getX(), dyingEnemy.getY(), dyingEnemy.getRewardValue()));
						player.increaseBank(dyingEnemy.getRewardValue());	// gives player money for kill
						Enemies.remove(j--);	// removes current enemy now that they're dead
					}
					player.Bullets.remove(i--);	// removes current bullet	
				}
			}
		}
		
		// spawns an enemy with a probability of _1_ per second
		double enemyProbability = 1.0;
		if (enemyProbability*randomizer.nextDouble() < timeElapsed/1000000000) {
			double spawnLocationDegrees = randomizer.nextDouble()*360;
			player.spawnEnemy(Enemies, spawnLocationDegrees);
		}
	}
	
	
	
	/** overriding paintComponent allows us to paint things to the screen */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);	// passes g to overridden super method
		// cast the Graphics object to a Graphics2D object to allow us
		// access to specific painting methods
		Graphics2D g2 = (Graphics2D) g;
		if (gm.isGameRunning()) {
			hud.drawToGraphics(g2);		// draws HUD
			player.drawToGraphic(g2);	// draws Player and bullets
			// draws all enemies
			for (int i = 0; i < Enemies.size(); i++)
				Enemies.get(i).drawToGraphic(g2);
			// draws all Money Drops
			for (int i = 0; i < MoneyDrops.size(); i++)
				MoneyDrops.get(i).drawToGraphics(g2);
		} else { // else we have reached a game over.
			g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
			g2.drawString("Game Over. Thanks for playing!", 220, 300);
			g2.drawString("You made " + String.format("$%.2f.", player.getBank()), 275, 350);
		}
	}
	
	
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		int keyID = arg0.getKeyCode();	// saves the ID of the key pressed;
		switch (keyID) {
		case KeyEvent.VK_ENTER:
			System.out.println("Enter pressed while in-game.");
			break;
		case KeyEvent.VK_ESCAPE:
			System.out.println("Quitting Game.");
			gm.quitGame();
			break;
		case KeyEvent.VK_LEFT:
			player.setDirection(Player.DIR.LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			player.setDirection(Player.DIR.RIGHT);
			break;
		// shoot a bullet if space is pressed
		case KeyEvent.VK_SPACE:
			player.spawnBullet();
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int keyID = arg0.getKeyCode();	// saves the ID of the key pressed;
		switch (keyID) {
		// stops moving in current direction if that directional key was released
		case KeyEvent.VK_LEFT:
			if (player.getDirection() == Player.DIR.LEFT) player.setDirection(Player.DIR.NULL);
			break;
		case KeyEvent.VK_RIGHT:
			if (player.getDirection() == Player.DIR.RIGHT) player.setDirection(Player.DIR.NULL);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
}
