// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.io.score;

import java.io.IOException;
import java.util.Iterator;
import java.util.Collection;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Component;
import javax.swing.JOptionPane;
import harmotab.core.Localizer;
import harmotab.core.ImagesListView;
import java.io.File;
import harmotab.core.Score;

public class PngFileWriter extends ScoreWriter
{
    private int m_width;
    private int m_height;
    
    public PngFileWriter(final Score score, final String path, final int width, final int height) {
        super(score, path);
        this.m_width = width;
        this.m_height = height;
    }
    
    @Override
    protected void write(final Score score, final File file) throws IOException {
        final ImagesListView view = new ImagesListView(score, this.m_width, this.m_height);
        final Collection<BufferedImage> images = view.createImages();
        boolean exists = false;
        for (int i = 0; i < images.size(); ++i) {
            final File imageFile = this.getFile(file, i);
            if (imageFile.exists()) {
                exists = true;
            }
        }
        if (exists) {
            final int result = JOptionPane.showConfirmDialog(null, Localizer.get("M_OVERWRITE_EXISTING_PNG_QUESTION"), Localizer.get("ET_PNG_EXPORT"), 0);
            if (result != 0) {
                return;
            }
        }
        int index = 0;
        for (final BufferedImage img : images) {
            ImageIO.write(img, "PNG", this.getFile(file, index));
            ++index;
        }
    }
    
    private File getFile(final File reference, final int index) {
        String path = reference.getAbsoluteFile() + ((index > 0) ? ("_" + index) : "");
        if (!path.toLowerCase().endsWith(".png")) {
            path = String.valueOf(path) + ".png";
        }
        return new File(path);
    }
}
