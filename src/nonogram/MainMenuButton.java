package nonogram;

import java.awt.Graphics;
import java.awt.Rectangle;

class MainMenuButton extends Rectangle {
	
	private final String text;
	private final String selectedText;
	
	MainMenuButton(String text, int x, int y, int w, int h) {
		super (x, y, w, h);
		this.text = text;
		this.selectedText = "--> " + text;
	}
	
	void draw(Graphics g, boolean selected) {
		if (selected)
			g.drawString(selectedText, x - g.getFontMetrics().stringWidth("--> "), y + height);
		else
			g.drawString(text, x, y + height);
	}
}
