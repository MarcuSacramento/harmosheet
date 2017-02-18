// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop;

import harmotab.core.ScoreViewSelection;
import java.util.EventListener;

public interface SelectionListener extends EventListener
{
    void onSelectionChanged(final ScoreViewSelection p0);
}
