
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







class PlayerSprite {
    private int x, y;
    private BufferedImage[] images;

    public PlayerSprite(int x, int y) {
        this.x = x;
        this.y = y;
        images = loadFrames("src/IdleMain");
    }
    
    
    public void setY(int y) {
    	this.y = y;
    }
    public void setSpriteRight () {
    	images = loadFrames("src/RightMain");
    	
    }
    
    public void setSpriteLeft() {
    	images = loadFrames("src/LeftMain");
    }
    
    public void setSpriteJump() {
    	images = loadFrames ("src/JumpMain");
    }
    
    public void setSpriteStand() {
    	images = loadFrames("src/IdleMain");
    }
    //IMAGE SCALER
    public BufferedImage scaleImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        Graphics2D g2d = scaledImage.createGraphics();
        
        // Use rendering hints for better quality
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose(); // Clean up graphics resources
        
        return scaledImage;
    }
    
    //ARRAY OF SPRITE FRAMES PLAYER
    private BufferedImage[] loadFrames(String src) {
        File folder = new File(src);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".png"));
        if (files == null || files.length == 0) {
            throw new RuntimeException("No PNG files found in the specified folder: " + src);
        }
        BufferedImage[] loadedFrames = new BufferedImage[files.length];
        try {
            for (int i = 0; i < files.length; i++) {
                loadedFrames[i] = ImageIO.read(files[i]);
                loadedFrames[i] = scaleImage(loadedFrames[i], 50,50);
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
    
    private boolean isTouchingGround(Rectangle hitbox, List<Rectangle> obstacles) {
        for (Rectangle obstacle : obstacles) {
            if (hitbox.intersects(obstacle)) {
                return true; // Stop falling if touching the ground or an obstacle
            }
        }
        return false; // Continue falling if no collision
    }
    
    public boolean falling(List<Rectangle> obstacles, int height) {
        Rectangle hitbox = new Rectangle(this.x, this.y, 50, 50); // Create a hitbox for the player
        
        // Move the player down as long as it is not touching anything
        if (!isTouchingGround(hitbox, obstacles)) {
            this.y += height;
            return true;// Move player down by 1 pixel per frame
        }
        return false;
    }
}






class SpriteScreen extends JPanel implements KeyListener {
	
	
    private BufferedImage[] frames; // Array to hold the frames
    private int currentFrame = 0; // Current frame index
    private PlayerSprite player;
    private List<Rectangle> obstacles = new ArrayList<>();
    private Timer fallTimer;
    private BufferedImage backgroundImage;
    private BufferedImage rockImage;

    public SpriteScreen() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
       // setFocusable(true);
        // Initialize the player sprite
        player = new PlayerSprite(400, 550);
        frames = player.getImages();
        
        //ground
        obstacles.add(new Rectangle(0,600,800,50));
        
        //obstacle test
        
        obstacles.add(new Rectangle(0,480,150,25));
        obstacles.add(new Rectangle (220,440,150,25));
        obstacles.add(new Rectangle(340,480,150,25));
        obstacles.add(new Rectangle(400,420,150,25));
        obstacles.add(new Rectangle (320,360,150,25));
        obstacles.add(new Rectangle (200,300,150, 25));
        obstacles.add(new Rectangle (160,220,150,25));
        obstacles.add(new Rectangle (240,100 ,150,25));
        
        
        
        fall(5);
        int jumpSpeed = 3;
        loadBackgroundImage();
        loadRockImage();
        
    }
        
    private void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(new File("src/BackgroundOne/BackgroundOne-1.png")); // Load image
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadRockImage() {
        try {
            rockImage = ImageIO.read(new File("src/Rocks/RockOne/RockOne-1.png")); // Load your obstacle image
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void fall(int dir) {
        fallTimer = new Timer(16, e -> {
            player.falling(obstacles, dir); // Make the player fall
            repaint(); // Redraw the screen
        });
        fallTimer.start(); // Start the falling timer
    }
    
    public void jump(int dir) {
        Timer jumpTimer = new Timer(16, e -> {
            player.falling(obstacles, -dir); // Make the player fall
            repaint(); // Redraw the screen
        });
        
        jumpTimer.setRepeats(false);
        fallTimer.start(); // Start the falling timer
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Stretch image to fit the panel
        }
        
        if (player.getY() <= 0) {
        	try {
        		
				backgroundImage = ImageIO.read(new File("src/BackgroundTwo/BackgroundTwo-1.png"));
				rockImage = ImageIO.read(new File ("src/Tree/TreeTwo/TreeTwo-1.png"));
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
				player.setY(550);
				
				obstacles.set(1, new Rectangle(150,430,150,25));
				obstacles.set(2, new Rectangle(490,320,150,25));
				obstacles.set(3, new Rectangle(480,110,150,25));
				obstacles.set(4, new Rectangle(240,30,150,25));
				obstacles.set(5, new Rectangle(250,240,150,25));
				obstacles.set(6, new Rectangle(160,70,150,25));
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        
        // Draw the current frame of the player
        if (frames != null && frames.length > 0) {
            g.drawImage(frames[currentFrame], player.getX(), player.getY(), null); // Draw player sprite
            
            Graphics2D g2d = (Graphics2D) g;

            // Draw background image if applicable
            if (backgroundImage != null) {
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }

            // Set transparency (alpha value)
            float alpha = 0f; // 50% transparent
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

            // Draw a transparent rectangle
            g2d.setColor(Color.GREEN); // Rectangle color
            
            
            for (Rectangle obstacle : obstacles) {
                g.fillRect(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
            }
            
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            
            if (frames != null && frames.length > 0) {
            g2d.drawImage(frames[currentFrame], player.getX(), player.getY(), null);
        }
        }
        
        
        
        for (Rectangle obstacle : obstacles) {
            if (rockImage != null) {
                g.drawImage(rockImage, obstacle.x, obstacle.y, obstacle.width, obstacle.height, this); // Draw obstacle image
            }
        }
        

        
    }
    

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    
    @Override
    public void keyPressed(KeyEvent e) {
        // Move the player based on the key pressed
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: // Left arrow
            	player.setSpriteLeft();
                player.move(-10, 0);
                
                break;
            case KeyEvent.VK_RIGHT: // Right arrow
            	player.setSpriteRight();
                player.move(10, 0);
                
                break;
            case KeyEvent.VK_UP: // Up arrow
            	/*if (player.falling(obstacles, 5) == false) {
            		player.setSpriteJump();
            		jump(5);
            	}*/
            	player.setSpriteJump();
            	player.move(0, -150);
            	
                break;

        }
        frames = player.getImages();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    	switch (e.getKeyCode()) {
        case KeyEvent.VK_LEFT: // Left arrow
        	player.setSpriteStand();        
            break;
        case KeyEvent.VK_RIGHT: // Right arrow
        	player.setSpriteStand();

            break;
        case KeyEvent.VK_UP: // Up arrow
        	player.setSpriteStand();
            break;

    }
    frames = player.getImages();
    repaint();
    
    }
    
    
    

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sprite Screen Example");
        SpriteScreen screen = new SpriteScreen();

        frame.add(screen);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Add the key listener
        frame.addKeyListener(screen);

        // Animation timer
        new Timer(75, e -> { // Update frame every 100ms
            screen.currentFrame = (screen.currentFrame + 1) % screen.frames.length;
            screen.repaint();
        }).start();
    }



}