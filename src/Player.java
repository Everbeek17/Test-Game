import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class Player {
	
	public enum DIR { LEFT, RIGHT, NULL };
	
	
	// the direction the player is moving;
	private DIR facing = DIR.NULL;
	private double heading;
	private int playerRadius;
	private int playAreaRadius;
	
	private final double playerX = 400, playerY = 300;
	private int turningSpeed;	// degrees of rotation per second
	
	private boolean hasDottedLine = false;
	
	private int shooterWidth, shooterLength;
	private int shooterStrength;	// strength of the bullets created by this shooter
	private int bulletSpeed;	// pixels per second
	private int bulletRadius;	// pixels in radius
	
	
	private float bank;
	private int lives = 3;	// player starts with 3 lives
	
	
	// creates a dashed stroke
	private final static float dash1[] = {5.0f};
	private final static BasicStroke dashedStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
            10.0f, dash1, 0.0f);
	
	// ArrayList to store all the bullets fired before they've expired
	public ArrayList<Bullet> Bullets = new ArrayList<Bullet>();
	
	
	/** Constructor.
	 * Sets default starting values */
	public Player( ) {
		
		heading = 225.0;
		playerRadius = 25;
		playAreaRadius = 250;
		shooterWidth = 40;	// in degrees
		shooterLength = (int) (0.5*playerRadius);
		turningSpeed = 180;
		bulletSpeed = 75;
		bulletRadius = 3;
		bank = 0.0f;
		shooterStrength = 5;
		
		
		// for now we'll start players off with the dotted line
		hasDottedLine = true;
		
	}
	
	
	public void setDirection(DIR dir) {	facing = dir; }
	public DIR getDirection() { return facing; }
	
	public int getPlayAreaRadius() { return playAreaRadius; }
	
	public double getBank() { return bank; }
	public void increaseBank(float value) { bank += value; }
	
	public int getLives() { return lives; }
	public boolean decrementLivesAndCheckIfStillAlive() { return --lives > 0;	}
	
	public boolean isBulletWithinPlayArea(Bullet b) {
		if (Math.sqrt(Math.pow(Math.abs(playerX-b.getX()), 2) +
				Math.pow(Math.abs(playerY-b.getY()), 2)) +
				b.getRadius() > playAreaRadius)
			return false;
		return true;
	}
	
	
	public boolean hasEnemyHitMe(Enemy e) {
		// if the distance between the two is less than the sum of the radii then a collision has occurred.
		if (Math.sqrt((playerX-e.getX()) * (playerX-e.getX()) + (playerY-e.getY())*(playerY-e.getY())) < (playerRadius+e.getRadius()))
			return true;
		return false;
	}
	
	
	public void spawnBullet() {
		// spawns the bullet at the tip of the shooter and in the direction the shooter is pointing
		Bullets.add(new Bullet(
				playerX + (playerRadius+shooterLength+bulletRadius)*Math.cos(Math.toRadians(heading)),
				playerY - (playerRadius+shooterLength+bulletRadius)*Math.sin(Math.toRadians(heading)),
				heading, bulletSpeed, bulletRadius, shooterStrength));
	}
	
	public void spawnEnemy (ArrayList<Enemy> Enemies, double spawnLocationDegrees) {
		
		int enemyRadius = 20;
		int enemyHealth = 5;	
		int enemySpeed = 15;
		
		
		
		Enemies.add(new Enemy(playerX + (playAreaRadius - enemyRadius)*Math.cos(Math.toRadians(spawnLocationDegrees)),
				playerY - (playAreaRadius - enemyRadius)*Math.sin(Math.toRadians(spawnLocationDegrees)),
				enemySpeed, (spawnLocationDegrees + 180) % 360, enemyRadius, enemyHealth));
		
		
		
	}
	
	
	public void increment(double timeElapsed) {
		// converts nanoseconds passed to seconds passed and multiplies by degrees of rotation
		// per second to get the degrees moved in the passed time interval
		if (facing != DIR.NULL) { 
			double headingChange = turningSpeed*timeElapsed/1000000000;
			// if turning right then subtract headingChange instead of adding it.
			if (facing == DIR.RIGHT) headingChange = -headingChange;
			heading = (heading + headingChange + 360) % 360;
		}
	}
	
	
	
	
	
	
	
	
	
	/** Draws Player to given Graphics2D object.
	 * @param g2 the Graphics2D object to be drawn to */
	public void drawToGraphic(Graphics2D g2) {
		// draws white play area
		g2.setColor(Color.WHITE);
		g2.fill(new Ellipse2D.Double(playerX-playAreaRadius, playerY-playAreaRadius,
				playAreaRadius*2, playAreaRadius*2));
		
		
		// draws dotted line that shot will follow, provided player has that unlocked
		if (hasDottedLine) {
			g2.setColor(Color.BLACK);
			g2.setStroke(dashedStroke);
			g2.draw(new Line2D.Double(playerX, playerY, 
					playerX + playAreaRadius*Math.cos(Math.toRadians(heading)),
					playerY - playAreaRadius*Math.sin(Math.toRadians(heading))));
			g2.setStroke(new BasicStroke());	// resets stroke back to default
		}
		
		// saves and computes locations of triangle vertices
		double [] shooterCoords = new double[6];
		// leftX, leftY
		shooterCoords[0] = playerX + playerRadius*Math.cos(Math.toRadians(heading-shooterWidth/2));
		shooterCoords[1] = playerY - playerRadius*Math.sin(Math.toRadians(heading-shooterWidth/2));
		// middleX, middleY
		shooterCoords[2] = playerX + (playerRadius+shooterLength)*Math.cos(Math.toRadians(heading));
		shooterCoords[3] = playerY - (playerRadius+shooterLength)*Math.sin(Math.toRadians(heading));
		// rightX, rightY
		shooterCoords[4] = playerX + playerRadius*Math.cos(Math.toRadians(heading+shooterWidth/2));
		shooterCoords[5] = playerY - playerRadius*Math.sin(Math.toRadians(heading+shooterWidth/2));
		
		// draws player's shooter
		g2.setColor(Color.DARK_GRAY);
		Path2D shooterPath = new Path2D.Double();
		shooterPath.moveTo(shooterCoords[0], shooterCoords[1]);
		for(int i = 2; i < shooterCoords.length; i++)
			shooterPath.lineTo(shooterCoords[i], shooterCoords[++i]);
		shooterPath.closePath();
		g2.fill(shooterPath);
		
		// draws player circle
		g2.setColor(Color.BLUE);
		g2.fill(new Ellipse2D.Double(playerX-playerRadius, playerY-playerRadius,
				playerRadius*2, playerRadius*2));
		
		// draws every bullet that currently exists
		for (int i = 0; i < Bullets.size(); i++) Bullets.get(i).drawToGraphic(g2);
		
		
		
		
	}
}
