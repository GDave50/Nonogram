package nonogram.pixelImage.layout;

import java.util.ArrayList;

import nonogram.pixelImage.Pixel;
import nonogram.pixelImage.PixelImage;
import nonogram.pixelImage.PixelState;

class Line {
	
	private final boolean horizontal;
	private final int coord;
	private final ArrayList<Segment> segs;
	private boolean completed;
	
	Line(boolean horizontal, int coord, ArrayList<Segment> segs) {
		this.horizontal = horizontal;
		this.coord = coord;
		this.segs = segs;
		completed = false;
	}
	
	void update(PixelImage pixelImage) {
		if (completed)
			return;
		
		for (int i = 0; i < segs.size(); i++)
			segs.get(i).update(pixelImage);
		
		for (int i = 0; i < segs.size(); i++)
			if (! segs.get(i).completed)
				return;
		
		completed = true;
		
		if (horizontal) {
			for (int x = 0; x < pixelImage.getWidth(); x++) {
				Pixel pixel = pixelImage.getPixel(x, coord);
				
				if (pixel.state == PixelState.EMPTY)
					pixel.state = PixelState.CROSS;
			}
		} else {
			for (int y = 0; y < pixelImage.getHeight(); y++) {
				Pixel pixel = pixelImage.getPixel(coord, y);
				
				if (pixel.state == PixelState.EMPTY)
					pixel.state = PixelState.CROSS;
			}
		}
	}
	
	int size() {
		return segs.size();
	}
	
	Segment get(int i) {
		return segs.get(i);
	}
}
