package nonogram.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import nonogram.Main;

public class GameDisplay {
	
	public static final BufferedImage CROSS_IMAGE = Main.loadImage("C:/Users/Gage/Documents/Nonogram/cross.png");
	public static final BufferedImage RED_CROSS_IMAGE = Main.loadImage("C:/Users/Gage/Documents/Nonogram/redCross.png");
	
	public static final Font FONT = new Font("Consolas", Font.PLAIN, 20);
	public static final Font BOLD_FONT = new Font("Consolas", Font.BOLD, 22);
	private static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
	public static final Color FOREGROUND_COLOR = new Color(0, 0, 0);
	public static final Color ACCENT_COLOR = new Color(230, 190, 0);
	public static final int OUTER_BUFFER = 100;
	
	private final int screenWidth, screenHeight;
	final int pixelSize;
	private final BufferedImage innerImage;
	private final Graphics innerGraphics;
	
	private final GameState gs;
	
	GameDisplay(JPanel screen, BufferedImage image, GameState gs) {
		screenWidth = screen.getWidth();
		screenHeight = screen.getHeight();
		
		innerImage = new BufferedImage(screenWidth - OUTER_BUFFER,
				screenHeight - OUTER_BUFFER, BufferedImage.TYPE_INT_RGB);
		innerGraphics = innerImage.getGraphics();
		
		pixelSize = innerImage.getWidth() / image.getWidth();
		
		this.gs = gs;
	}
	
	void draw(Graphics g) {
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, screenWidth, screenHeight);
		
		drawInner();
		
		int imageX = screenWidth - innerImage.getWidth();
		int imageY = screenHeight - innerImage.getHeight();
		g.drawImage(innerImage, imageX, imageY, null);
		
		g.setColor(FOREGROUND_COLOR);
		GameDisplay.drawThickRectangle(g, imageX, imageY, innerImage.getWidth(), innerImage.getHeight(), 2);
		
		gs.pixelImage.layout.draw(g, OUTER_BUFFER, pixelSize);
		
		// draw guide lines for selection
		g.setColor(FOREGROUND_COLOR);
		GameDisplay.drawThickVerticalLine(g, OUTER_BUFFER + gs.selection.getX() * pixelSize, 0,
				OUTER_BUFFER, 1);
		GameDisplay.drawThickVerticalLine(g, OUTER_BUFFER + (gs.selection.getX() + 1) * pixelSize, 0,
				OUTER_BUFFER, 1);
		GameDisplay.drawThickHorizontalLine(g, 0, OUTER_BUFFER + gs.selection.getY() * pixelSize,
				OUTER_BUFFER, 1);
		GameDisplay.drawThickHorizontalLine(g, 0, OUTER_BUFFER + (gs.selection.getY() + 1) * pixelSize,
				OUTER_BUFFER, 1);
		
		// draw thick box around selected pixel
		GameDisplay.drawThickRectangle(g,
				OUTER_BUFFER + gs.selection.getX() * pixelSize - 3,
				OUTER_BUFFER + gs.selection.getY() * pixelSize - 3,
				pixelSize + 7,
				pixelSize + 7, 3);
	}
	
	private void drawInner() {
		innerGraphics.setColor(BACKGROUND_COLOR);
		innerGraphics.fillRect(0, 0, innerImage.getWidth(), innerImage.getHeight());
		
		gs.pixelImage.draw(innerGraphics, pixelSize);
		
		innerGraphics.setColor(FOREGROUND_COLOR);
		for (int x = 1; x < gs.pixelImage.getWidth(); x++) {
			if (x % 5 == 0)
				GameDisplay.drawThickVerticalLine(innerGraphics, x * pixelSize, 0, innerImage.getHeight(), 3);
			else
				innerGraphics.drawLine(x * pixelSize, 0, x * pixelSize, innerImage.getHeight());
		}
		
		for (int y = 1; y < gs.pixelImage.getHeight(); y++) {
			if (y % 5 == 0)
				GameDisplay.drawThickHorizontalLine(innerGraphics, 0, y * pixelSize, innerImage.getWidth(), 3);
			else
				innerGraphics.drawLine(0, y * pixelSize, innerImage.getWidth(), y * pixelSize);
		}
	}
	
	static void drawThickRectangle(Graphics g, int x, int y, int width, int height, int thickness) {
		for (int i = 0; i < thickness; i++)
			g.drawRect(x - i, y - i, width + i*2 - 1, height + i*2 - 1);
	}
	
	static void drawThickHorizontalLine(Graphics g, int x, int y, int length, int thickness) {
		g.drawLine(x, y, x + length, y);
		for (int i = 0; i < thickness / 2; i++)
			g.drawLine(x, y - i - 1, x + length, y - i - 1);
		for (int i = 0; i < thickness / 2; i++)
			g.drawLine(x, y + i + 1, x + length, y + i + 1);
	}
	
	static void drawThickVerticalLine(Graphics g, int x, int y, int length, int thickness) {
		g.drawLine(x, y, x, y + length);
		for (int i = 0; i < thickness / 2; i++)
			g.drawLine(x - i - 1, y, x - i - 1, y + length);
		for (int i = 0; i < thickness / 2; i++)
			g.drawLine(x + i + 1, y, x + i + 1, y + length);
	}
}
