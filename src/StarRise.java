
/** @Author Ethan, ,Anthony */

import java.util.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

    public MovingSprite(int x, int y, String folderPath) {
        this.x = x;
        this.y = y;
        this.images = loadFrames(folderPath);
    }

    private BufferedImage[] loadFrames(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".png"));
        if (files == null || files.length == 0) {
            throw new RuntimeException("No PNG files found in the specified folder: " + folderPath);
        }
        BufferedImage[] loadedFrames = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++) {
                loadedFrames[i] = ImageIO.read(files[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return loadedFrames;
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
    private BufferedImage[] frames; // Array to hold the frames
    private int currentFrame = 0; // Current frame index
    private int x = 100, y = 100; // Sprite position

    public SpriteScreen() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        //Level Complete Sprite
        //MovingSprite animatedSprite = new MovingSprite(100, 100, "src/FinishMain");
        //frames = animatedSprite.getImages();
        
        //Idle Sprite
        MovingSprite animatedSprite2 = new MovingSprite(0,0, "src/IdleMain");
        frames = animatedSprite2.getImages();
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (frames != null && frames.length > 0) {
            g.drawImage(frames[currentFrame], x, y, null); // Draw current frame
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
        new Timer(100, e -> { // Update frame every 100ms
            screen.currentFrame = (screen.currentFrame + 1) % screen.frames.length;
            screen.repaint();
        }).start();
    }




}