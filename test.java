import dk.borimino.citygenerator.*;
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
	int vertCount = 0;

	public void init()
	{
		glMatrixMode(GL_PROJECTION);
		glOrtho(-1, 1, -1, 1, 1, -1);

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glViewport(0, 0, Display.getWidth(), Display.getHeight());

		glEnable(GL_DEPTH_TEST);

		
		HashMap<FloatBuffer, Integer> houses = HouseGenerator.generateHouses(1);
		FloatBuffer vertices = (FloatBuffer) houses.keySet().toArray()[0];
		vertCount = houses.get(vertices);

		vboVertexID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboVertexID);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);


		glRotatef(45, 0, 1, 0);
		glRotatef(10, 1, 0, 1);
	}

	public void update(long elapsedTime)
	{
		if (isKeyDown(KEY_ESCAPE)) 
			end();
	}

	public void render()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


		glEnableClientState(GL_VERTEX_ARRAY);

		glBindBuffer(GL_ARRAY_BUFFER, vboVertexID);
		glVertexPointer(3, GL_FLOAT, 0, 0);

		glDrawArrays(GL_QUADS, 0, vertCount);

		glDisableClientState(GL_VERTEX_ARRAY);
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
		
		System.out.println(HouseGenerator.generateHouse());

		new test();

		//test t = new test();
		//t.start();
	}
}
