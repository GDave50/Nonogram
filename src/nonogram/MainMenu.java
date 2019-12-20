package nonogram;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

class MainMenu implements KeyListener, MouseListener, MouseMotionListener{
	
	private static final String[] OPTIONS =
			{ "Play", "Options", "How to Play", "Exit" };
	
	private static final Color BACKGROUND_COLOR = Color.WHITE;
	private static final Color FOREGROUND_COLOR = Color.BLACK;
	private static final Font FONT = new Font("Consolas", Font.BOLD, 30);
	
	private final int screenWidth, screenHeight;
	private Timer timer;
	private MainMenuButton[] menuButtons;
	private int selectedOption;
	
	MainMenu(JPanel display, int fps, Graphics g) {
		screenWidth = display.getWidth();
		screenHeight = display.getHeight();
		selectedOption = 0;
		
		timer = new Timer(1000 / fps, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				draw(g);
			}
		});
		
		makeMenuButtons(g);
		
		display.addKeyListener(this);
		display.addMouseListener(this);
		display.addMouseMotionListener(this);
	}
	
	private void makeMenuButtons(Graphics g) {
		FontMetrics fm = g.getFontMetrics(FONT);
		menuButtons = new MainMenuButton[OPTIONS.length];
		for (int i = 0; i < OPTIONS.length; i++)
			menuButtons[i] = new MainMenuButton(
					OPTIONS[i],
					screenWidth / 2 - fm.stringWidth(OPTIONS[i]) / 2,
					150 + i * (fm.getAscent() + 20),
					fm.stringWidth(OPTIONS[i]),
					fm.getAscent());
	}
	
	void run() {
		timer.start();
	}
	
	void interact() {
		switch (selectedOption) {
		case 0:
			Main.runGame();
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			System.exit(0);
			break;
		}
	}
	
	void draw(Graphics g) {
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, screenWidth, screenHeight);
		
		g.setFont(FONT);
		g.setColor(FOREGROUND_COLOR);
		for (int i = 0; i < OPTIONS.length; i++)
			menuButtons[i].draw(g, i == selectedOption);
	}
	
	@Override
	public void keyPressed(KeyEvent evt) {
		int keyCode = evt.getKeyCode();
		
		switch (keyCode) {
		case KeyEvent.VK_UP:
			if (selectedOption == 0)
				selectedOption = OPTIONS.length - 1;
			else
				selectedOption--;
			break;
		case KeyEvent.VK_DOWN:
			if (selectedOption == OPTIONS.length - 1)
				selectedOption = 0;
			else
				selectedOption++;
			break;
		case KeyEvent.VK_ENTER:
			interact();
			break;
		}
	}
	
	@Override
	public void mousePressed(MouseEvent evt) {
		interact();
	}
	
	@Override
	public void mouseMoved(MouseEvent evt) {
		for (int i = 0; i < OPTIONS.length; i++) {
			if (menuButtons[i].contains(evt.getPoint())) {
				selectedOption = i;
				break;
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent evt) {
		mouseMoved(evt);
	}
	
	@Override
	public void keyReleased(KeyEvent evt) {
	}
	@Override
	public void keyTyped(KeyEvent evt) {
	}
	@Override
	public void mouseClicked(MouseEvent evt) {
	}
	@Override
	public void mouseReleased(MouseEvent evt) {
	}
	@Override
	public void mouseEntered(MouseEvent evt) {
	}
	@Override
	public void mouseExited(MouseEvent evt) {
	}
}
