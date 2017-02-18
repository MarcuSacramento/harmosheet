// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.track.soundlayout;

import java.util.Iterator;
import harmotab.sound.SoundItem;
import java.util.ArrayList;

public class SoundItemGroup extends ArrayList<SoundItem>
{
    private static final long serialVersionUID = 1L;
    
    public void set(final SoundItem item) {
        this.clear();
        this.add(item);
    }
    
    public void extend(final float duration) {
        for (final SoundItem item : this) {
            item.extend(duration);
        }
    }
}
