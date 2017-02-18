// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer.awtrenderers;

import res.ResourceLoader;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

public class AwtRenderersResources
{
    public static final int BREATH_WIDTH = 15;
    public static final int BREATH_HEIGHT = 20;
    public static final int BEND_WIDTH = 20;
    public static Image m_keyCImage;
    public static Image m_sharpImage;
    public static Image m_flatImage;
    public static Image m_naturalImage;
    public static Image m_digitsImage;
    public static Image m_notesImage;
    public static Image m_breathImage;
    public static Image m_emptyAreaImage;
    public static Image m_tempoImage;
    public static Image m_warningImage;
    public static Font m_defaultFont;
    public static Color m_defaultForeground;
    public static Font m_barNumberFont;
    public static Color m_barNumberColor;
    public static Font m_lyricsFont;
    public static Color m_lyricsForeground;
    public static Font m_harmonicaNameFont;
    public static Font m_harmonicaKeyFont;
    public static Color m_harmonicaForeground;
    
    static {
        final ResourceLoader loader = ResourceLoader.getInstance();
        AwtRenderersResources.m_keyCImage = loader.loadImage("/res/elements/key-c.png");
        AwtRenderersResources.m_sharpImage = loader.loadImage("/res/elements/sharp.png");
        AwtRenderersResources.m_flatImage = loader.loadImage("/res/elements/flat.png");
        AwtRenderersResources.m_naturalImage = loader.loadImage("/res/elements/natural.png");
        AwtRenderersResources.m_digitsImage = loader.loadImage("/res/elements/digits.png");
        AwtRenderersResources.m_notesImage = loader.loadImage("/res/elements/notes.png");
        AwtRenderersResources.m_breathImage = loader.loadImage("/res/elements/breath.png");
        AwtRenderersResources.m_emptyAreaImage = loader.loadImage("/res/elements/empty-area.png");
        AwtRenderersResources.m_warningImage = loader.loadImage("/res/elements/warning.png");
        AwtRenderersResources.m_tempoImage = loader.loadImage("/res/controllers/quarter.png");
        AwtRenderersResources.m_defaultFont = new Font("Sans-serif", 0, 12);
        AwtRenderersResources.m_defaultForeground = Color.BLACK;
        AwtRenderersResources.m_barNumberFont = new Font("Sans-serif", 0, 10);
        AwtRenderersResources.m_barNumberColor = Color.GRAY;
        AwtRenderersResources.m_lyricsFont = new Font("Sans-serif", 2, 11);
        AwtRenderersResources.m_lyricsForeground = Color.BLACK;
        AwtRenderersResources.m_harmonicaNameFont = new Font("Sans-serif", 0, 10);
        AwtRenderersResources.m_harmonicaKeyFont = new Font("Sans-serif", 1, 15);
        AwtRenderersResources.m_harmonicaForeground = Color.BLACK;
    }
}
