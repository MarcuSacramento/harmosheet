// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer;

import java.util.ConcurrentModificationException;
import java.awt.Point;
import java.awt.Graphics2D;
import harmotab.track.layout.TrackLayout;
import java.util.Iterator;
import harmotab.track.Track;
import harmotab.throwables.OutOfSpecificationError;
import harmotab.track.layout.HeaderLayout;
import harmotab.core.Score;

public class ScoreRenderer
{
    public static final int MIN_PAGE_WIDTH = 400;
    public static final int MIN_PAGE_HEIGHT = 300;
    private int m_pageWidth;
    private int m_pageHeight;
    private boolean m_multiline;
    private boolean m_headerDrawingEnabled;
    private Score m_score;
    private HeaderLayout m_headerLayout;
    private ElementRendererBundle m_elementRendererBundle;
    private int m_verticalMargin;
    private int m_horizontalMargin;
    private int m_interlineSpace;
    
    public ScoreRenderer(final Score score) {
        this.m_pageWidth = 600;
        this.m_pageHeight = 1000;
        this.m_multiline = true;
        this.m_headerDrawingEnabled = true;
        this.m_score = null;
        this.m_headerLayout = null;
        this.m_elementRendererBundle = null;
        this.m_verticalMargin = 50;
        this.m_horizontalMargin = 50;
        this.m_interlineSpace = 30;
        this.m_score = score;
        this.m_elementRendererBundle = new AwtEditorElementRendererBundle();
        this.m_headerLayout = new HeaderLayout(score);
    }
    
    public void setElementRenderer(final ElementRendererBundle renderer) {
        if (renderer == null) {
            throw new NullPointerException();
        }
        this.m_elementRendererBundle = renderer;
    }
    
    public ElementRendererBundle getElementRenderer() {
        return this.m_elementRendererBundle;
    }
    
    public int getPageWidth() {
        return this.m_pageWidth;
    }
    
    public int getPageHeight() {
        return this.m_pageHeight;
    }
    
    public void setPageSize(final int width, final int height) throws OutOfSpecificationError {
        if (width < 0 || height < 0) {
            throw new OutOfSpecificationError("Invalid page size (" + width + " x " + height + ") !");
        }
        this.m_pageWidth = Math.max(400, width);
        this.m_pageHeight = Math.max(300, height);
    }
    
    public boolean isMultiline() {
        return this.m_multiline;
    }
    
    public void setMultiline(final boolean multiline) {
        this.m_multiline = multiline;
    }
    
    public boolean isHeaderDrawingEnabled() {
        return this.m_headerDrawingEnabled;
    }
    
    public void setHeaderDrawingEnabled(final boolean enabled) {
        this.m_headerDrawingEnabled = enabled;
    }
    
    public int getLineHeight() {
        int value = 0;
        for (final Track track : this.m_score) {
            value += track.getTrackLayout().getTrackHeight();
        }
        value += this.m_interlineSpace;
        return value;
    }
    
    public int getHeaderHeight() {
        if (!this.isHeaderDrawingEnabled()) {
            return 0;
        }
        return this.m_headerLayout.getHeight();
    }
    
    public int getInterlineHeight() {
        return this.m_interlineSpace;
    }
    
    public int getLineOffset(final int line) {
        return this.getHeaderHeight() + (line - 1) * this.getLineHeight();
    }
    
    public void layout(final LocationList locations) {
        if (this.isHeaderDrawingEnabled()) {
            this.m_headerLayout.processHeaderPositionning(locations, this.m_pageWidth - this.m_verticalMargin * 2);
        }
        int round = 0;
        int tracksStillNotLayedOut = this.m_score.getTracksCount();
        final float scoreDuration = this.m_score.getDuration();
        while (tracksStillNotLayedOut > 0) {
            for (final Track track : this.m_score) {
                final TrackLayout layout = track.getTrackLayout();
                if (layout.getLayoutRound() == round) {
                    layout.processElementsPositionning(locations, this.m_pageWidth - this.m_verticalMargin * 2, scoreDuration);
                    --tracksStillNotLayedOut;
                }
            }
            ++round;
        }
        locations.addOffset(this.m_verticalMargin, this.m_horizontalMargin);
        final int lineHeight = this.getLineHeight();
        int trackOffset = this.getHeaderHeight();
        int trackId = 0;
        for (final Track track2 : this.m_score) {
            locations.addVerticalOffset(trackId, lineHeight, trackOffset);
            trackOffset += track2.getTrackLayout().getTrackHeight();
            ++trackId;
        }
    }
    
    public void paint(final Graphics2D g, final LocationList locations, final Point offset) {
        try {
            for (final LocationItem item : locations) {
                if (item.m_x2 >= 0 && item.m_x1 <= this.m_pageWidth && item.m_y2 >= 0 && item.m_y1 <= this.m_pageHeight) {
                    this.m_elementRendererBundle.paintElement(g, item);
                }
            }
        }
        catch (ConcurrentModificationException ex) {}
    }
}
