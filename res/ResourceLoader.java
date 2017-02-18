// 
// Decompiled by Procyon v0.5.30
// 

package res;

import java.net.URL;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.HashMap;
import java.awt.Image;
import java.util.Map;

public class ResourceLoader
{
    private static ResourceLoader m_instance;
    private static Map<String, Image> m_cache;
    
    static {
        ResourceLoader.m_instance = null;
        ResourceLoader.m_cache = null;
    }
    
    private ResourceLoader() {
        ResourceLoader.m_cache = new HashMap<String, Image>();
    }
    
    public static synchronized ResourceLoader getInstance() {
        if (ResourceLoader.m_instance == null) {
            ResourceLoader.m_instance = new ResourceLoader();
        }
        return ResourceLoader.m_instance;
    }
    
    public Image loadImage(final String path) {
        Image image = null;
        if (ResourceLoader.m_cache.containsKey(path)) {
            return ResourceLoader.m_cache.get(path);
        }
        try {
            final URL url = this.getClass().getResource(path);
            if (url == null) {
                throw new IOException();
            }
            image = ImageIO.read(url);
        }
        catch (IOException e) {
            System.err.println("Cannot read file " + path + ".");
            e.printStackTrace();
        }
        if (image != null) {
            ResourceLoader.m_cache.put(path, image);
        }
        return image;
    }
}
