package nonogram.game;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import nonogram.pixelImage.PixelImage;

class GameState {
	
	final PixelImage pixelImage;
	final Selection selection;
	
	boolean spacePressed, xPressed, xHeld;
	
	GameState(BufferedImage image) {
		pixelImage = new PixelImage(image);
		selection = new Selection(image.getWidth(), image.getHeight());
	}
	
	void keyPressed(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_SPACE:
			spacePressed = true;
			break;
		case KeyEvent.VK_X:
			if (xPressed)
				xHeld = true;
			else
				xPressed = true;
			break;
		}
	}
	
	void keyReleased(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_SPACE:
			spacePressed = false;
			break;
		case KeyEvent.VK_X:
			xPressed = false;
			xHeld = false;
			break;
		}
	}
}
