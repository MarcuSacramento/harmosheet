// 
// Decompiled by Procyon v0.5.30
// 

package rvt.util.gui;

import java.awt.Component;

public class RepaintWorker implements Runnable
{
    private Component m_component;
    
    public RepaintWorker(final Component comp) {
        this.m_component = null;
        this.m_component = comp;
    }
    
    @Override
    public void run() {
        this.m_component.repaint();
    }
}
