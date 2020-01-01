import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;


public class Bullet {
	
	private double x, y;
	private double heading, speed;
	private int bulletRadius;
	private int strength;
	
	
	public Bullet(double startX, double startY, double heading, double speed,
			int bulletRadius, int bulletStrength) {
		x = startX;
		y = startY;
		this.heading = heading;
		this.speed = speed;	// speed given in pixels per second
		this.bulletRadius = bulletRadius;	// pixels in radius
		strength = bulletStrength;
	}
	
	public int getStrength() { return strength; }
	
	/** Turns bullet strength into an impact value and returns that.
	 * The impact value being how much damage to do to other things.
	 * Currently returnable impacts are between 10 and 100.
	 * @return
	 */
	
	// draws the bullet as a circle with color defined by strength
	public void drawToGraphic(Graphics2D g2) {
		if (strength <= 100)
			g2.setColor(new Color((int) (42-42*strength/100), (int) (245-82*strength/100), (int) (152+95*strength/100)));
		else
			g2.setColor(Color.MAGENTA);
		g2.fill(new Ellipse2D.Double(x-bulletRadius, y-bulletRadius, bulletRadius*2, bulletRadius*2));
	}
	
	
	public void increment(double timeElapsed) {
		// how many pixels to move this second
		double pixelsThisIncrement = speed*timeElapsed/1000000000;
		x += pixelsThisIncrement*Math.cos(Math.toRadians(heading));
		y -= pixelsThisIncrement*Math.sin(Math.toRadians(heading));
	}
	
	public double getX() { return x; }
	public double getY() { return y; }
	public int getRadius() { return bulletRadius; }
}
