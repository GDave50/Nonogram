package nonogram.pixelImage.layout;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import nonogram.game.GameDisplay;
import nonogram.pixelImage.Pixel;
import nonogram.pixelImage.PixelImage;

public class PixelLayout {
	
	private static final Color ACTIVE_COLOR = GameDisplay.FOREGROUND_COLOR;
	private static final Color BOLD_COLOR = GameDisplay.ACCENT_COLOR;
	private static final Color INACTIVE_COLOR = new Color(210, 210, 210);
	
	private final Line[] layout;
	private final int imageWidth, imageHeight;
	
	public PixelLayout(PixelImage pixelImage) {
		imageWidth = pixelImage.getWidth();
		imageHeight = pixelImage.getHeight();
		
		layout = new Line[imageWidth + imageHeight];
		
		for (int y = 0; y < imageHeight; y++)
			layout[y] = new Line(true, y, processLine(pixelImage, y, true));
		
		for (int x = 0; x < imageWidth; x++)
			layout[imageHeight + x] = new Line(false, x, processLine(pixelImage, x, false));
	}
	
	public void update(PixelImage pixelImage) {
		for (int i = 0; i < layout.length; i++)
			layout[i].update(pixelImage);
	}
	
	public void draw(Graphics g, int buffer, int pixelSize) {
		FontMetrics fm = g.getFontMetrics(GameDisplay.FONT);
		
		for (int y = 0; y < imageHeight; y++) {
			Line line = layout[y];
			
			int drawX = buffer - 10;
			for (int i = line.size() - 1; i >= 0; i--) {
				Segment seg = line.get(i);
				
				if (seg.completed) {
					g.setFont(GameDisplay.FONT);
					g.setColor(INACTIVE_COLOR);
				} else if (seg.bold) {
					g.setFont(GameDisplay.BOLD_FONT);
					g.setColor(BOLD_COLOR);
				} else {
					g.setFont(GameDisplay.FONT);
					g.setColor(ACTIVE_COLOR);
				}
				
				String numString = seg.length + "";
				drawX -= fm.stringWidth(numString);
				g.drawString(numString,
						drawX,
						buffer + y * pixelSize + pixelSize / 2 + fm.getAscent() / 2);
				drawX -= fm.stringWidth(" ")/2;
			}
		}
		
		for (int x = 0; x < imageWidth; x++) {
			Line line = layout[imageHeight + x];
			
			for (int i = 0; i < line.size(); i++) {
				Segment seg = line.get(i);
				
				if (seg.completed) {
					g.setFont(GameDisplay.FONT);
					g.setColor(INACTIVE_COLOR);
				} else if (seg.bold) {
					g.setFont(GameDisplay.BOLD_FONT);
					g.setColor(BOLD_COLOR);
				} else {
					g.setFont(GameDisplay.FONT);
					g.setColor(ACTIVE_COLOR);
				}
				
				String numString = seg.length + "";
				g.drawString(numString,
						x * pixelSize + buffer + pixelSize / 2 - fm.stringWidth(numString) / 2,
						buffer - 10 - (line.size() - i - 1) * (fm.getAscent() + fm.stringWidth(" ")/2));
			}
		}
	}
	
	private ArrayList<Segment> processLine(PixelImage pixelImage, int xory, boolean horizontal) {
		ArrayList<Segment> segs = new ArrayList<>();
		
		boolean counting = false;
		int start = -1;
		ArrayList<Point> points = new ArrayList<>();
		
		for (int xory2 = 0; xory2 <
				(horizontal ? pixelImage.getWidth() : pixelImage.getHeight());
				xory2++) {
			
			Pixel pixel = horizontal ?
					pixelImage.getPixel(xory2, xory) : pixelImage.getPixel(xory, xory2);
			Point point = horizontal ? new Point(xory2, xory) : new Point(xory, xory2);
			
			if (! counting && ! pixel.clear) {
				counting = true;
				start = xory2;
				points.add(point);
			} else if (counting && pixel.clear) {
				int length = xory2 - start;
				boolean bold = length > (horizontal ? imageWidth : imageHeight) / 2;
				Point[] coords = new Point[points.size()];
				segs.add(new Segment(length, bold, points.toArray(coords)));
				
				counting = false;
				points.clear();
			} else if (counting && ! pixel.clear) {
				points.add(point);
			}
		}
		
		if (counting) {
			int length =
					(horizontal ? pixelImage.getWidth() : pixelImage.getHeight())
					- start;
			boolean bold = length > (horizontal ? imageWidth : imageHeight) / 2;
			Point[] coords = new Point[points.size()];
			segs.add(new Segment(length, bold, points.toArray(coords)));
		}
		
		if (segs.isEmpty())
			segs.add(new Segment(0, false, null));
		
		return segs;
	}
}
