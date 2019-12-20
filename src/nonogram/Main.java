package nonogram;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import nonogram.game.Game;
import nonogram.game.GameDisplay;

public class Main {
	
	private static final int DISPLAY_FPS = 25;
	private static final String DISPLAY_TITLE = "Nonogram";
	private static final Dimension SCREEN_RESOLUTION = Toolkit.getDefaultToolkit().getScreenSize();
	static MenuState menuState = MenuState.MAIN_MENU;
	private static MainMenu mainMenu;
	static Display display;
	private static Game game;
	
	public static void main(String[] args) {
		display = new Display(600, 400, DISPLAY_TITLE);
		
		display.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				int keyCode = evt.getKeyCode();
				
				if (keyCode == KeyEvent.VK_ESCAPE)
					System.exit(0);
			}
		});
		
		mainMenu = new MainMenu(display, DISPLAY_FPS, display.getScreenGraphics());
		mainMenu.run();
		
		display.run(DISPLAY_FPS);
	}
	
	static void runGame() {
		runGame("C:/Users/Gage/Documents/Nonogram/flower.png");
	}
	
	static void runGame(String imagePath) {
		BufferedImage image = loadImage(imagePath);
		
		int pixelSize = 50;
		int screenWidth, screenHeight;
		
		do {
			screenWidth = GameDisplay.OUTER_BUFFER + pixelSize * image.getWidth();
			screenHeight = GameDisplay.OUTER_BUFFER + pixelSize * image.getHeight();
			
			pixelSize -= 5;
		} while (screenWidth > SCREEN_RESOLUTION.width - GameDisplay.OUTER_BUFFER || 
				screenHeight > SCREEN_RESOLUTION.height - GameDisplay.OUTER_BUFFER);
		
		display.resizeWindow(screenWidth, screenHeight);
		display.removeKeyListener(mainMenu);
		display.removeMouseListener(mainMenu);
		display.removeMouseMotionListener(mainMenu);
		
		game = new Game(display, image);
		game.run(DISPLAY_FPS, display.getScreenGraphics());
	}
	
	public static BufferedImage loadImage(String path) {
		File imageFile = new File(path);
		
		if (! imageFile.exists()) {
			System.err.println("Image file does not exist at path '" + path + "'");
			System.exit(1);
		}
		
		try {
			return ImageIO.read(imageFile);
		} catch (IOException ex) {
			System.err.println("Could not read image from path '" + path + "'");
			System.exit(1);
		}
		
		return null;
	}
}
