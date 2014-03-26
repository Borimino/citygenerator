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
import static org.lwjgl.util.glu.GLU.*;

public class test extends Game
{
	int vboVertexID;
	int[] vboVertexIDs;
	int vboTexID;
	int vertCount = 0;
	int[] vertCounts;
	Texture texture;

	float globalX = 0;
	float globalY = 0;

	public static final int globalWidth2 = 16;
	public static final int globalHeight2 = 16;

	public void init()
	{
		glMatrixMode(GL_PROJECTION);
		glOrtho(-4, 4, -4, 4, 20, -40);
		//gluPerspective(70f, 800f/600f, 1, 1000);

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glViewport(0, 0, Display.getWidth(), Display.getHeight());

		glEnable(GL_DEPTH_TEST);

		glEnable(GL_TEXTURE_2D);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		texture = Texture.loadWindow(100);
		System.out.println("Loaded window with " + 10);
		

		Util.checkGLError();
		
		HashMap<FloatBuffer, Integer> houses = HouseGenerator.generateHouses(globalWidth2*globalHeight2);
		//FloatBuffer vertices = (FloatBuffer) houses.keySet().toArray()[0];
		//vertCount = houses.get(vertices);

		FloatBuffer[] verticeses = houses.keySet().toArray(new FloatBuffer[houses.keySet().size()]);
		vertCounts = new int[verticeses.length];
		for (int i = 0; i < verticeses.length; i++) 
		{
			vertCounts[i] = houses.get(verticeses[i]);
		}

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

		//vboVertexID = glGenBuffers();
		//glBindBuffer(GL_ARRAY_BUFFER, vboVertexID);
		//glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		//glBindBuffer(GL_ARRAY_BUFFER, 0);

		vboTexID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboTexID);
		glBufferData(GL_ARRAY_BUFFER, verticesTex, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		vboVertexIDs = new int[verticeses.length];
		for (int i = 0; i < verticeses.length; i++) 
		{
			vboVertexIDs[i] = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vboVertexIDs[i]);
			glBufferData(GL_ARRAY_BUFFER, verticeses[i], GL_STATIC_DRAW);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
		}


		glRotatef(-20, 1, 0, 0);
		glRotatef(-45, 0, 1, 0);
		//glRotatef(00, 0, 1, 0);
		//glRotatef(-20, 1, 0, 0);
		//glRotatef(20, 0, 0, 1);
		//glRotatef(-00, 1, 0, 1);
		//glTranslatef(1*HouseGenerator.widthS*vboVertexIDs.length*0.5f, 0f, 0f);
		//glTranslatef(0, -2, 0);
		glTranslatef(-1*HouseGenerator.tWidth*globalWidth2*1f, 0f, -1*HouseGenerator.tDepth*globalHeight2*1f);
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

		glTranslatef(1*HouseGenerator.tWidth*globalWidth2*0.5f, 0f, 1*HouseGenerator.tDepth*globalHeight2*0.5f);
		glRotatef(-1, 0, 1, 0);
		glTranslatef(-1*HouseGenerator.tWidth*globalWidth2*0.5f, 0f, -1*HouseGenerator.tDepth*globalHeight2*0.5f);

		System.out.println("X " + globalX + " Y " + globalY);
		
		//System.out.println(globalWidth2);
		

		//glTranslatef(-1*HouseGenerator.tWidth*globalWidth2, 0, -1*HouseGenerator.tDepth*globalHeight2);
		//globalX += -1*HouseGenerator.tWidth*globalWidth2;
		//globalY += -1*HouseGenerator.tDepth*globalHeight2;

		//System.out.println(vboVertexIDs.length);
		

		for (int i = 0; i < vboVertexIDs.length; i++) 
		{
			glTranslatef(HouseGenerator.tWidth, 0, 0);
			//glTranslatef(0, 0, HouseGenerator.depthS);
			//glTranslatef(HouseGenerator.widthS, 0, HouseGenerator.depthS);
			globalX += HouseGenerator.tWidth;

			int vboVertexID = vboVertexIDs[i];
			int vertCount = vertCounts[i];
			
			glBindBuffer(GL_ARRAY_BUFFER, vboVertexID);
			glVertexPointer(3, GL_FLOAT, 0, 0);

			glBindBuffer(GL_ARRAY_BUFFER, vboTexID);
			glTexCoordPointer(3, GL_FLOAT, 0, 0);

			glEnableClientState(GL_VERTEX_ARRAY);
			glEnableClientState(GL_TEXTURE_COORD_ARRAY);

			glDrawArrays(GL_QUADS, 0, vertCount);

			glDisableClientState(GL_VERTEX_ARRAY);
			glDisableClientState(GL_TEXTURE_COORD_ARRAY);
			if((i+1) % globalWidth2 == 0)
			{
				//System.out.println("X " + globalX + " Y " + globalY);

				glTranslatef(-1*HouseGenerator.tWidth*(globalWidth2), 0, HouseGenerator.tDepth);
				globalX += -1*HouseGenerator.tWidth*(globalWidth2);
				globalY += HouseGenerator.tDepth;
				//System.out.println(i);
				
				//System.out.println("X " + globalX + " Y " + globalY);
				
			}

			//System.out.println("X " + globalX + " Y " + globalY);
		}

		glTranslatef(0, 0, -1*HouseGenerator.tDepth*(globalHeight2));
		globalY += -1*HouseGenerator.tDepth*(globalHeight2);

		Util.checkGLError();
	}

	public void dispose()
	{
		glDeleteBuffers(vboVertexID);
		for (int i = 0; i < vboVertexIDs.length; i++) 
		{
			glDeleteBuffers(vboVertexIDs[i]);
		}
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
