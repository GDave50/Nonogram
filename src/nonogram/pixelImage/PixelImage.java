package nonogram.pixelImage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import nonogram.game.GameDisplay;
import nonogram.game.Selection;
import nonogram.pixelImage.layout.PixelLayout;

public class PixelImage {
	
	private final Pixel[][] pixels;
	public final PixelLayout layout;
	
	public PixelImage(BufferedImage image) {
		pixels = new Pixel[image.getWidth()][image.getHeight()];
		
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				pixels[x][y] = new Pixel(image.getRGB(x, y));
				pixels[x][y].state = PixelState.EMPTY;
			}
		}
		
		layout = new PixelLayout(this);
	}
	
	public void draw(Graphics g, int pixelSize) {
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				Pixel pixel = getPixel(x, y);
				
				switch (pixel.state) {
				case SHOWN:
					if (! pixel.clear) {
						g.setColor(pixel.color);
						g.fillRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
					}
					break;
				case CROSS:
					g.drawImage(GameDisplay.CROSS_IMAGE,
							x * pixelSize, y * pixelSize,
							pixelSize, pixelSize, null);
					break;
				case RED_CROSS:
					g.drawImage(GameDisplay.RED_CROSS_IMAGE,
							x * pixelSize, y * pixelSize,
							pixelSize, pixelSize, null);
					break;
				case EMPTY:
					g.setColor(GameDisplay.FOREGROUND_COLOR);
					g.drawRect(x * pixelSize,  y * pixelSize, pixelSize, pixelSize);
					break;
				}
			}
		}
	}
	
	public Pixel getPixel(int x, int y) {
		return pixels[x][y];
	}
	
	public Pixel getSelectedPixel(Selection selection) {
		return getPixel(selection.getX(), selection.getY());
	}
	
	public int getWidth() {
		return pixels.length;
	}
	
	public int getHeight() {
		return pixels[0].length;
	}
}
