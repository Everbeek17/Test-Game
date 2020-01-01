import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Class to create a HUD to show player statistics and the game screen around the player.
 * @author ridin
 *
 */
public class HUD {
	public static final Font comicSans = new Font("Comic Sans MS", Font.PLAIN, 18);
	public static final Font defaultFont = new Font("TimeRoman", Font.PLAIN, 18);
	
	
	// reference to player to get statistics from
	private Player player;
	
	private int count = 1;
	
	public HUD(Player p) {
		player = p;	// saves reference to player
		
		
		
		
		
	}
	
	
	// draws the entire HUD to the screen.
	public void drawToGraphics(Graphics2D g2) {
		
		
		g2.setColor(Color.BLACK);
		g2.setFont(comicSans);
		g2.drawString("Frames Rendered: " + count++, 25, 75);
	
		
		g2.drawString("Bank: $" + String.format("%.2f", player.getBank()), 640, 40);
		g2.drawString("Lives: " + player.getLives(), 100, 40);
		
		
		
		
		/*
	      Font trb = new Font("Comic Sans MS", Font.BOLD, 18);
	      Font tri = new Font("TimesRoman", Font.ITALIC, 18);
	      Font trbi = new Font("TimesRoman", Font.BOLD+Font.ITALIC, 18);
	      Font h = new Font("Helvetica", Font.PLAIN, 18);
	      Font c = new Font("Courier", Font.PLAIN, 18);
	      Font d = new Font("Dialog", Font.PLAIN, 18);      
	      Font z = new Font("ZapfDingbats", Font.PLAIN, 18);            

	      g2.setFont(tr);
	      g2.drawString("Hello World (times roman plain)",10,25);
	      g2.setFont(trb);
	      g2.drawString("Hello World (times roman bold)",10,50);
	      g2.setFont(tri);
	      g2.drawString("Hello World (times roman italic)",10,75);
	      g2.setFont(trbi);
	      g2.drawString("Hello World (times roman bold & italic)",10,100);
	      g2.setFont(h);
	      g2.drawString("Hello World (helvetica)",10,125);
	      g2.setFont(c);
	      g2.drawString("Hello World (courier)",10,150);
	      g2.setFont(d);
	      g2.drawString("Hello World (dialog)",10,175);
	      g2.setFont(z);
	      g2.drawString("Hello World (zapf dingbats)",10,200);
	      */
		// resets font back to default
		g2.setFont(defaultFont);
	}
	
}
