// 
// Decompiled by Procyon v0.5.30
// 

package ie.debug;

import harmotab.harmonica.TabModel;
import harmotab.core.Height;
import harmotab.element.Tab;

public class TabModelLoadTest
{
    private Tab[] m_tabs;
    private Height[] m_heights;
    
    public static void main(final String[] args) {
        new TabModelLoadTest();
    }
    
    public TabModelLoadTest() {
        this.m_tabs = null;
        this.m_heights = null;
        this.m_tabs = new Tab[100];
        this.m_heights = new Height[100];
        for (int i = 0; i < 100; ++i) {
            this.m_tabs[i] = new Tab(i % 10 + 1, (byte)(i % 2), (byte)(i % 2));
            this.m_heights[i] = new Height(36 + i % 48);
        }
        final long start = System.currentTimeMillis();
        this.test();
        System.out.println(System.currentTimeMillis() - start);
    }
    
    public void test() {
        final TabModel model = new TabModel();
        for (int i = 0; i < 100000; ++i) {
            model.getHeight(this.m_tabs[i % 100]);
        }
        for (int i = 0; i < 100000; ++i) {
            model.setTab(this.m_heights[i % 100], this.m_tabs[i % 100]);
        }
        for (int i = 0; i < 1000000; ++i) {
            model.getHeight(this.m_tabs[i % 100]);
        }
        for (int i = 0; i < 1000000; ++i) {
            model.getTab(this.m_heights[i % 100]);
        }
    }
}
