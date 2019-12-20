package nonogram;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class Display extends JPanel {
	
	private final JFrame frame;
	private BufferedImage screenImage;
	private Graphics screenGraphics;
	
	Display(int width, int height, String title) {
		setPreferredSize(new Dimension(width, height));
		
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		frame.setContentPane(this);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		screenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		screenGraphics = screenImage.getGraphics();
	}
	
	void run(int fps) {
		requestFocus();
		
		new Timer(1000 / fps, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				repaint();
			}
		}).start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(screenImage, 0, 0, null);
	}
	
	void resizeWindow(int width, int height) {
		setPreferredSize(new Dimension(width, height));
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		screenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		screenGraphics = screenImage.getGraphics();
	}
	
	Graphics getScreenGraphics() {
		return screenGraphics;
	}
}
