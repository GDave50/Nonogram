package nonogram.game;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import nonogram.pixelImage.Pixel;
import nonogram.pixelImage.PixelState;

public class Game implements KeyListener, MouseListener, MouseMotionListener {
	
	private final GameState gs;
	private final GameDisplay display;
	private boolean mouseInside;
	
	public Game (JPanel screen, BufferedImage image) {
		gs = new GameState(image);
		display = new GameDisplay(screen, image, gs);
		mouseInside = true; // TODO with this true, mouse click to start game clicks a tile too
		
		screen.addKeyListener(this);
		screen.addMouseListener(this);
		screen.addMouseMotionListener(this);
		
		gs.pixelImage.layout.update(gs.pixelImage);
	}
	
	public void run(int fps, Graphics g) {
		new Timer(1000 / fps, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				draw(g);
			}
		}).start();
	}
	
	@Override
	public void keyPressed(KeyEvent evt) {
		int keyCode = evt.getKeyCode();
		
		gs.keyPressed(keyCode);
		gs.selection.keyPressed(keyCode);
		
		switch (keyCode) {
		case KeyEvent.VK_SPACE:
			pressPixel();
			break;
		case KeyEvent.VK_X:
			crossPixel();
			break;
		
		case KeyEvent.VK_UP:
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT:
			if (gs.xPressed)
				crossPixel();
			else if (gs.spacePressed)
				pressPixel();
			break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent evt) {
		int keyCode = evt.getKeyCode();
		gs.keyReleased(keyCode);
	}
	
	@Override
	public void keyTyped(KeyEvent evt) {
	}
	
	private void pressPixel() {
		Pixel pixel = gs.pixelImage.getSelectedPixel(gs.selection);
		
		if (pixel.state == PixelState.EMPTY) {
			if (pixel.clear) {
				pixel.state = PixelState.RED_CROSS;
			} else {
				pixel.state = PixelState.SHOWN;
				gs.pixelImage.layout.update(gs.pixelImage);
			}
		}
	}
	
	private void crossPixel() {
		Pixel pixel = gs.pixelImage.getSelectedPixel(gs.selection);
		
		if (pixel.state == PixelState.EMPTY)
			pixel.state = PixelState.CROSS;
		else if (pixel.state == PixelState.CROSS && ! gs.xHeld)
			pixel.state = PixelState.EMPTY;
	}
	
	void draw(Graphics g) {
		display.draw(g);
	}
	
	@Override
	public void mousePressed(MouseEvent evt) {
		if (SwingUtilities.isLeftMouseButton(evt))
			pressPixel();
		else if (SwingUtilities.isRightMouseButton(evt))
			crossPixel();
	}
	
	@Override
	public void mouseDragged(MouseEvent evt) {
		if (! mouseInside)
			return;
		
		mouseMoved(evt);
		
		if (SwingUtilities.isLeftMouseButton(evt))
			pressPixel();
		else if (SwingUtilities.isRightMouseButton(evt))
			crossPixel();
	}
	
	@Override
	public void mouseMoved(MouseEvent evt) {
		Point mousePoint = evt.getPoint();
		int mouseX = mousePoint.x - GameDisplay.OUTER_BUFFER;
		int mouseY = mousePoint.y - GameDisplay.OUTER_BUFFER;
		
		gs.selection.mouseMoved(mouseX, mouseY, display.pixelSize);
		
		// TODO movement within one tile will repeat action
		if (gs.spacePressed)
			pressPixel();
		else if (gs.xPressed)
			crossPixel();
	}
	
	@Override
	public void mouseEntered(MouseEvent evt) {
		mouseInside = true;
	}
	
	@Override
	public void mouseExited(MouseEvent evt) {
		mouseInside = false;
	}
	
	@Override
	public void mouseClicked(MouseEvent evt) {
	}
	@Override
	public void mouseReleased(MouseEvent evt) {
	}
}
