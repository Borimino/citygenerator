package dk.borimino.citygenerator;

import java.io.*;
import java.awt.image.*;
import java.nio.*;

import javax.imageio.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;

public class Texture 
{
	public int id;

	public int width;
	public int height;

	private Texture(int id, int width, int height)
	{
		this.id = id;
		this.width = width;
		this.height = height;
	}

	public static Texture loadTexture(String name)
	{
		BufferedImage bimg = null;

		try {
			bimg = ImageIO.read(Texture.class.getClassLoader().getResourceAsStream(name));
		} catch(IOException e) {
			e.printStackTrace();
			System.err.println("Unable to load Texture: " + name);
			Game.end();
		}

		int textureID = glGenTextures();

		int[] pixels = new int[bimg.getWidth() * bimg.getHeight() * 4];
		bimg.getRGB(0, 0, bimg.getWidth(), bimg.getHeight(), pixels, 0, bimg.getWidth());

		ByteBuffer buffer = BufferUtils.createByteBuffer(pixels.length);

		for (int y = 0; y < bimg.getHeight(); y++)
		{
			for (int x = 0; x < bimg.getWidth(); x++)
			{
				int pixel = pixels[y * bimg.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) ((pixel) & 0xFF));
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}

		buffer.flip();

		glBindTexture(GL_TEXTURE_2D, textureID);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, bimg.getWidth(), bimg.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		return new Texture(textureID, bimg.getWidth(), bimg.getHeight());
	}

	public static Texture loadWindow(int shade)
	{
		int textureID = glGenTextures();

		ByteBuffer buffer = WindowGenerator.generateImages().get(shade);
		buffer.flip();

		glBindTexture(GL_TEXTURE_2D, textureID);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_BASE_LEVEL, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, WindowGenerator.width, WindowGenerator.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		Util.checkGLError();

		return new Texture(textureID, WindowGenerator.width, WindowGenerator.height);
	}
}
