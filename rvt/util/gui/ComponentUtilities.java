// 
// Decompiled by Procyon v0.5.30
// 

package rvt.util.gui;

import java.awt.Frame;
import java.awt.Container;

public class ComponentUtilities
{
    public static Frame getParentFrame(Container comp) {
        if (comp == null) {
            return null;
        }
        while (comp.getParent() != null) {
            if (comp.getParent() instanceof Frame) {
                return (Frame)comp.getParent();
            }
            comp = comp.getParent();
        }
        return null;
    }
}
