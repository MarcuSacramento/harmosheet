// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer;

import harmotab.renderer.renderingelements.EmptyArea;
import java.awt.Graphics2D;

public class AwtPrintingElementRendererBundle extends AwtEditorElementRendererBundle
{
    public AwtPrintingElementRendererBundle() {
        this.setMode(RenderingMode.VIEW_MODE);
    }
    
    @Override
    protected void paintEmptyArea(final Graphics2D g, final EmptyArea area, final LocationItem l) {
    }
    
    @Override
    protected void paintWarning(final Graphics2D g, final LocationItem item) {
    }
}
