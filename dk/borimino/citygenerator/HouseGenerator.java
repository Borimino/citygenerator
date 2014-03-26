package dk.borimino.citygenerator;

import java.nio.*;
import org.lwjgl.*;
import java.util.*;

public class HouseGenerator 
{
	static public final float heightS = 2f;
	static public final float widthS = 1f;
	static public final float depthS = 1f;

	static public final float tHeight = 2f+0.5f;
	static public final float tWidth = 1f+0.5f;
	static public final float tDepth = 1f+0.5f;

	public static HashMap<FloatBuffer, Integer> generateHouses(int number)
	{
		HashMap<FloatBuffer, Integer> res = new HashMap<FloatBuffer, Integer>();
		
		for (int i = 0; i < number; i++) 
		{
			float scalar = 1f;
			//float heightS = 2f;
			//float widthS = 1f;
			//float depthS = 1f;

			float height = (float) (Math.random()*0.5 + 0.5);
			float width = (float) (Math.random()*0.5 + 0.5);
			float depth = (float) (Math.random()*0.5 + 0.5) * -1;

			//float height = 1f;
			//float width = 1f;
			//float depth = 1f;

			height = height * scalar * heightS;
			width = width * scalar * widthS;
			depth = depth * scalar * depthS;

			FloatBuffer res2 = BufferUtils.createFloatBuffer(3*4*6);
			int vecCount = 4*6;
			res2.put(new float[]
			{
				//Front
				0, 0, 0,
				width, 0, 0,
				width, height, 0,
				0, height, 0,

				//Right
				width, 0, 0,
				width, 0, depth,
				width, height, depth,
				width, height, 0,

				//Back
				width, 0, depth,
				0, 0, depth,
				0, height, depth,
				width, height, depth,

				//Left
				0, 0, depth,
				0, 0, 0,
				0, height, 0,
				0, height, depth,

				//Top
				0, height, 0,
				width, height, 0,
				width, height, depth,
				0, height, depth,

				//Buttom
				0, 0, 0,
				width, 0, 0,
				width, 0, depth,
				0, 0, depth
			});
			res2.rewind();
			
			res.put(res2, vecCount);
		}

		return res;
	}
}
