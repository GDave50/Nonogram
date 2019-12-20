package nonogram.pixelImage;

import java.awt.Color;

public class Pixel {
	
	public PixelState state;
	public Color color;
	public boolean clear;
	
	Pixel(int rgb) {
		state = PixelState.EMPTY;
		clear = (rgb >> 24) == 0;
		
		if (! clear)
			rgb |= (255 << 24);
		color = new Color(rgb, true);
	}
}
