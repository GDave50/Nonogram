package nonogram.game;

import java.awt.event.KeyEvent;

public class Selection {
	
	private final int imageWidth, imageHeight;
	private int x, y;
	
	Selection(int imageWidth, int imageHeight) {
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}
	
	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_UP:
			if (y > 0)
				y--;
			else
				y = imageHeight - 1;
			break;
		case KeyEvent.VK_DOWN:
			if (y < imageHeight - 1)
				y++;
			else
				y = 0;
			break;
		case KeyEvent.VK_LEFT:
			if (x > 0)
				x--;
			else
				x = imageWidth - 1;
			break;
		case KeyEvent.VK_RIGHT:
			if (x < imageWidth - 1)
				x++;
			else
				x = 0;
			break;
		}
	}
	
	public void mouseMoved(int mouseX, int mouseY, int pixelSize) {
		if (mouseX < 0 || mouseY < 0)
			return;
		
		x = mouseX / pixelSize;
		y = mouseY / pixelSize;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
