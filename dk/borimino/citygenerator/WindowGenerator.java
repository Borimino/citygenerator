package dk.borimino.citygenerator;

import java.util.*;
import java.awt.image.*;
import java.awt.*;

public class WindowGenerator 
{
	public static final int width = 100;
	public static final int height = 50;

	public static ArrayList<BufferedImage> generateImages()
	{
		ArrayList<BufferedImage> res = new ArrayList<BufferedImage>();

		for (int i = 0; i < 255; i++) 
		{
			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			for (int x = 0; x < width; x++) 
			{
				for (int y = 0; y < height; y++) 
				{
					img.setRGB(x, y, (new Color(i, i, i).getRGB()));
					res.add(img);
				}
			}
		}

		return res;
	}
}
