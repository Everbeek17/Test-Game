import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Enemy {

	private double x, y; // coordinates
	private double heading;	// direction in degrees
	private int speed; // movement in pixels per second
	private int radius;	// the size of the enemy (in pixels)
	
	private int currentHealth, maxHealth;	
	
	
	
	
	public int getHealth() { return currentHealth; }
	
	public Enemy(double startingX, double startingY, int startingSpeed, double startingHeading,
			int startingRadius, int startingHealth) {
		x = startingX;
		y = startingY;
		speed = startingSpeed;
		heading = startingHeading;
		radius = startingRadius;
		maxHealth = startingHealth;
		currentHealth = maxHealth;	// starts Enemy at full health
	}
	
	public double getX() { return x; }
	public double getY() { return y; }
	public int getRadius() { return radius; }
	
	
	/**
	 * Calculates how much money player receives for killing this enemy
	 * @return the amount of money received for this kill
	 */
	public float getRewardValue() {
		return maxHealth/10f;
	}
	
	// draws the enemy as circle with color relative to its health
	public void drawToGraphic(Graphics2D g2) {
		if (maxHealth <= 100) {
			if (currentHealth <= 50)
				g2.setColor(new Color((int) (255*currentHealth/50), 255, 0));
			else
				g2.setColor(new Color(255, (int) (255*2 - 255*currentHealth/50),  0));
		} else {
			g2.setColor(Color.BLACK);
		}
		g2.fill(new Ellipse2D.Double(x-radius, y-radius, radius*2, radius*2));
	}
	
	public void increment(double timeElapsed) {
		// how many pixels to move this second
		double pixelsThisIncrement = speed*timeElapsed/1000000000;
		x += pixelsThisIncrement*Math.cos(Math.toRadians(heading));
		y -= pixelsThisIncrement*Math.sin(Math.toRadians(heading));
	}
	
	/**
	 * Tests if the given bullet is making contact with this Enemy.
	 * @param b	the bullet to be tested with
	 * @return true if the bullet is making contact with this Enemy
	 */
	public boolean hasBulletHitMe(Bullet b) {
		// if the distance between the two is less than the sum of the radii then a collision has occurred.
		if (Math.sqrt((x-b.getX()) * (x-b.getX()) + (y-b.getY())*(y-b.getY())) < (radius+b.getRadius()))
			return true;
		return false;
	}
	
	/** Damages player by the given damage amount.
	 * @param damage	the amount to take away from Enemy's health
	 */
	public void damage(int damage) { currentHealth -= damage; }
	
	
}
