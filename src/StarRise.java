
/** @Author Ethan, ,Anthony */

import java.util.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.Timer;

public class StarRise implements KeyListener{
	
	private int[] position; 
	
	public StarRise() {
		position = new int[2];
		position[0] = 0; //x position
		position[1] = 0; //y position
	}

	@Override
	public void keyTyped(KeyEvent e) {
		int keycode = e.getKeyCode();
		switch (keycode) {
			case KeyEvent.VK_RIGHT: //right arrow
				moveRight();
			case KeyEvent.VK_LEFT:
				moveLeft();
			case KeyEvent.VK_UP:
				jump();
		
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		switch (keycode) {
			case KeyEvent.VK_RIGHT: //right arrow
				moveRight();
			case KeyEvent.VK_LEFT:
				moveLeft();
			case KeyEvent.VK_UP:
				jump();
		
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keycode = e.getKeyCode();
		switch (keycode) {
			case KeyEvent.VK_RIGHT: //right arrow
				moveRight();
			case KeyEvent.VK_LEFT:
				moveLeft();
			case KeyEvent.VK_UP:
				jump();
		
		}
		
	}
	
	public boolean moveLeft() {
		position[0] -= 1;
		return true; // add functionality to see if space is occupied
	}
	
	public boolean moveRight() {
		position[0] += 1;
		return true; // add functionality to see if space is occupied
	}
	
	public boolean jump() {
		position[1] += 3;
		return true; // add functionality to see if space is occupied
	}
	


}

// Sprite class to represent a single sprite
class Sprite{
    private int x, y;
    private BufferedImage image;

    public Sprite(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public BufferedImage getImage() { return image; }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
}

class MovingSprite {
    private int x, y;
    private BufferedImage[] images;


    public MovingSprite(int x, int y, BufferedImage[] image) {
        this.x = x;
        this.y = y;
        this.images = images;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public BufferedImage[] getImages() { return images; }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
}

class SpriteScreen extends JPanel {

    private List<Sprite> sprites = new ArrayList<>();
  
    
    public SpriteScreen() {
        // Set up the JPanel
        setPreferredSize(new Dimension(800, 600)); // Set screen size
        setBackground(Color.BLACK);
        //sprites.add (new Sprite (xpos, y pos, createSpriteMethod(Parameters)))
        sprites.add(new Sprite(100, 100, createDummySprite(Color.RED)));
        sprites.add(new Sprite(300, 200, createDummySprite(Color.GREEN)));
    }

    // Method to create a dummy sprite (a simple rectangle image)
    private BufferedImage createDummySprite(Color color) {
        BufferedImage sprite = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = sprite.createGraphics();
        g.setColor(color);
        g.fillRect(0, 0, 50, 50);
        g.dispose();
        return sprite;
    }
    
    /*private BufferedImage createPlayerSprite() {
    	BufferedImage sprite = new BufferedImage()
    	return sprite;
    }*/

    // Add a sprite to the list
    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Sprite sprite : sprites) {
            g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), null);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sprite Screen Example");
        SpriteScreen screen = new SpriteScreen();

        frame.add(screen);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Example animation
        new Timer(16, e -> {
            screen.sprites.get(0).move(1, 0); // Move the first sprite to the right
            screen.repaint();
        }).start(); 
    
}



}