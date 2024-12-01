
/** @Author Ethan, ,Anthony */

import java.util.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

