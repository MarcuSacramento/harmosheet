// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.tools;

import java.awt.event.KeyEvent;
import harmotab.renderer.LocationItem;
import harmotab.core.Score;
import java.awt.Container;
import harmotab.element.Tab;
import harmotab.element.HarmoTabElement;

public class HarmoTabTool extends NoteTool
{
    private static final long serialVersionUID = 1L;
    private HarmoTabElement m_harmoTabElement;
    private Tab m_tab;
    private TabTool m_tabController;
    
    public HarmoTabTool(final Container container, final Score score, final LocationItem item) {
        super(container, score, item);
        this.m_harmoTabElement = null;
        this.m_tab = null;
        this.m_tabController = null;
        this.m_harmoTabElement = (HarmoTabElement)item.getElement();
        this.m_tab = this.m_harmoTabElement.getTab();
        final LocationItem tabLocationItem = (LocationItem)item.clone();
        tabLocationItem.m_element = this.m_tab;
        this.m_tabController = new TabTool(container, score, tabLocationItem);
    }
    
    @Override
    public void keyTyped(final KeyEvent event) {
        super.keyTyped(event);
        if (!this.m_harmoTabElement.isRest()) {
            this.m_tabController.keyTyped(event);
        }
    }
}
