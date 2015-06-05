package de.cookieapp.util;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import de.cookieapp.database.impl.DataProviderImpl;

public class PictureLoader {
	
	public static final String DEFAULTPIC = "resources/default_pic.png";
	public static final String TROLLFACE = "resources/troll_face_small.png";
	public static final String RECIPEREPO = "resources/recipes/";
	private static Display display; 


	
	/**
	 * Loads the Image at the given path, transforms it into an Image
	 * 
	 * @param name
	 *            the full path of the image
	 * @return the Image, which was located at the given path
	 */
	public static Image loadImageFromDatabase(String name) {
		Image result = null;
		InputStream stream = DataProviderImpl.class.getClassLoader().getResourceAsStream(name);
		if (stream != null) {
			try {
				result = new Image(Display.getDefault(), stream);
			} finally {
				try {
					stream.close();
				} catch (IOException unexpected) {
					throw new RuntimeException("Failed to close image input stream", unexpected);
				}
			}
		}
		return result;
	}	
}
