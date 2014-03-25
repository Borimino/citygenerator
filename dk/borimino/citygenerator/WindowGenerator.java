package dk.borimino.citygenerator;

import java.util.*;
import java.awt.image.*;
import java.awt.*;

import java.nio.*;

import org.lwjgl.*;

public class WindowGenerator 
{
	public static final int width = 200;
	public static final int height = 100;

	static public final int border = 20;
	static public final int round = 00;

	public static ArrayList<ByteBuffer> generateImages()
	{
		ArrayList<ByteBuffer> res = new ArrayList<ByteBuffer>();

		for (int i = 0; i < 255; i++) 
		{
			ByteBuffer buffer = BufferUtils.createByteBuffer(width*height*4);

			for (int x = 0; x < height; x++) 
			{
				for (int y = 0; y < width; y++) 
				{
					if( (x+y) <= round || (x+(width-y)) <= round || ((height-x)+y) <= round || ((height-x)+(width-y)) <= round || x <= border || y <= border || height-1-x <= border || width-1-y <= border)
					{
						buffer.put((byte) (0 & 0xFF));
						buffer.put((byte) (0 & 0xFF));
						buffer.put((byte) (0 & 0xFF));
						buffer.put((byte) (254 & 0xFF));
						continue;
					}
					buffer.put((byte) (i & 0xFF));
					buffer.put((byte) (i & 0xFF));
					buffer.put((byte) (i & 0xFF));
					buffer.put((byte) (254 & 0xFF));
				}
			}
			res.add(buffer);
		}

		return res;
	}
}
