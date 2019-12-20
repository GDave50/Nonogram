package nonogram.pixelImage.layout;

import java.awt.Point;

import nonogram.pixelImage.PixelImage;
import nonogram.pixelImage.PixelState;

class Segment {
	
	final int length;
	final boolean bold;
	private final Point[] coords;
	boolean completed;
	
	Segment(int length, boolean bold, Point[] coords) {
		this.length = length;
		this.bold = bold;
		this.coords = coords;
		completed = false;
	}
	
	void update(PixelImage pixelImage) {
		if (completed)
			return;
		
		if (coords == null) {
			completed = true;
			return;
		}
		
		for (int i = 0; i < coords.length; i++) {
			Point p = coords[i];
			
			if (pixelImage.getPixel(p.x, p.y).state != PixelState.SHOWN)
				return;
		}
		
		completed = true;
	}
}
