// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer;

import java.awt.Graphics2D;

public abstract class ElementRendererBundle
{
    private boolean m_drawEditingHelpers;
    private boolean m_drawEditingWarnings;
    
    public ElementRendererBundle() {
        this.setMode(RenderingMode.VIEW_MODE);
    }
    
    public abstract void reset();
    
    public void setMode(final RenderingMode mode) {
        if (mode == RenderingMode.EDIT_MODE) {
            this.setDrawEditingHelpers(true);
            this.setDrawEditingWarnings(true);
        }
        else {
            if (mode != RenderingMode.VIEW_MODE) {
                throw new IllegalArgumentException("Unknown mode #" + mode);
            }
            this.setDrawEditingHelpers(false);
            this.setDrawEditingWarnings(false);
        }
    }
    
    public void setDrawEditingHelpers(final boolean draw) {
        this.m_drawEditingHelpers = draw;
    }
    
    public boolean getDrawEditingHelpers() {
        return this.m_drawEditingHelpers;
    }
    
    public void setDrawEditingWarnings(final boolean draw) {
        this.m_drawEditingWarnings = draw;
    }
    
    public boolean getDrawEditingWarnings() {
        return this.m_drawEditingWarnings;
    }
    
    public abstract void paintElement(final Graphics2D p0, final LocationItem p1);
}
