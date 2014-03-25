package dk.borimino.citygenerator;

import java.util.*;
import java.awt.image.*;
import java.nio.*;

import org.lwjgl.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.input.*;
import static org.lwjgl.input.Keyboard.*;

public class test extends Game
{
	int vboVertexID;
	int vboTexID;
	int vertCount = 0;
	Texture texture;

	public void init()
	{
		glMatrixMode(GL_PROJECTION);
		glOrtho(-2, 2, -2, 2, 2, -2);

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glViewport(0, 0, Display.getWidth(), Display.getHeight());

		glEnable(GL_DEPTH_TEST);

		glEnable(GL_TEXTURE_2D);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		texture = Texture.loadWindow(10);
		System.out.println("Loaded window with " + 10);
		

		Util.checkGLError();
		
		HashMap<FloatBuffer, Integer> houses = HouseGenerator.generateHouses(1);
		FloatBuffer vertices = (FloatBuffer) houses.keySet().toArray()[0];
		vertCount = houses.get(vertices);

		FloatBuffer verticesTex = BufferUtils.createFloatBuffer(3*4*6);
		verticesTex.put(new float[]
		{
			0, 0, 0,
			5, 0, 0,
			5, 20, 0,
			0, 20, 0,

			0, 0, 0,
			5, 0, 0,
			5, 20, 0,
			0, 20, 0,

			0, 0, 0,
			5, 0, 0,
			5, 20, 0,
			0, 20, 0,

			0, 0, 0,
			5, 0, 0,
			5, 20, 0,
			0, 20, 0,

			0, 0, 0,
			0, 0, 0,
			0, 0, 0,
			0, 0, 0,

			0, 0, 0,
			0, 0, 0,
			0, 0, 0,
			0, 0, 0
		});
		verticesTex.rewind();

		vboVertexID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboVertexID);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		vboTexID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboTexID);
		glBufferData(GL_ARRAY_BUFFER, verticesTex, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);


		glRotatef(45, 0, 1, 0);
		glRotatef(-20, 1, 0, 1);
	}

	public void update(long elapsedTime)
	{
		if (isKeyDown(KEY_ESCAPE)) 
			end();
	}

	public void render()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


		glBindTexture(GL_TEXTURE_2D, texture.id);

		glBindBuffer(GL_ARRAY_BUFFER, vboVertexID);
		glVertexPointer(3, GL_FLOAT, 0, 0);

		glBindBuffer(GL_ARRAY_BUFFER, vboTexID);
		glTexCoordPointer(3, GL_FLOAT, 0, 0);

		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);

		glDrawArrays(GL_QUADS, 0, vertCount);

		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);

		Util.checkGLError();
	}

	public void dispose()
	{
		glDeleteBuffers(vboVertexID);
	}

	public static void main (String [] args)
	{
		//System.out.println("Printing");
		//ArrayList<BufferedImage> imgs = WindowGenerator.generateImages();
		//System.out.println(imgs.get(0));
		//System.out.println(WindowGenerator.generateImages());
		
		//System.out.println(HouseGenerator.generateHouse());

		new test();

		//test t = new test();
		//t.start();
	}
}
