import java.awt.Color;
import java.awt.Graphics2D;

public class MoneyDrop {

	private float visualTimeLeft = 1.25f;	// in seconds
	private float value;	// in $
	private float x, y;	// coordinates
	private static int speed = 4;	// pixels per second (how fast value sign floats up)
	
	private static final Color MoneyColor = new Color(34, 139, 34);
	
	
	public MoneyDrop (double x, double y, float value) {
		this.x = (float) x - 30;
		this.y = (float) y;
		
		this.value = value;
	}
	
	/**
	 * Does this Money Drop still have visual time left?
	 * @return true if visual time left is greater than 0
	 */
	public boolean stillAlive() { return visualTimeLeft > 0; }
	
	public void increment(double timeElapsed) {
		visualTimeLeft -= timeElapsed/1000000000;
		y -= speed*timeElapsed/1000000000;
	}
	
	
	public void drawToGraphics(Graphics2D g2) {
		
		g2.setColor(MoneyColor);
		g2.drawString(String.format("+$%.2f", value), x, y);
		
		
	}
	
	
}
