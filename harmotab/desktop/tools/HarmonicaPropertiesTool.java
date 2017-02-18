// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.tools;

import java.awt.event.KeyEvent;
import java.awt.Component;
import harmotab.desktop.actions.UserAction;
import harmotab.desktop.actions.ShowHarmonicaPropertiesAction;
import harmotab.renderer.LocationItem;
import harmotab.core.Score;
import java.awt.Container;

public class HarmonicaPropertiesTool extends Tool
{
    private static final long serialVersionUID = 1L;
    
    public HarmonicaPropertiesTool(final Container container, final Score score, final LocationItem item) {
        super(container, score, item);
        final ToolButton harmoPropertiesButton = new ToolButton(new ShowHarmonicaPropertiesAction());
        harmoPropertiesButton.setWide(true);
        this.add(harmoPropertiesButton);
    }
    
    @Override
    public void keyTyped(final KeyEvent event) {
    }
}
