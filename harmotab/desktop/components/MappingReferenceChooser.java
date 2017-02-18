// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import harmotab.core.HarmoTabObjectEvent;
import javax.swing.event.ChangeEvent;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import harmotab.core.HarmoTabObjectListener;
import javax.swing.event.ChangeListener;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import harmotab.core.Height;
import harmotab.element.Tab;
import java.util.ArrayList;
import javax.swing.JComboBox;
import harmotab.harmonica.Harmonica;
import javax.swing.JPanel;

public class MappingReferenceChooser extends JPanel
{
    private static final long serialVersionUID = 1L;
    private Harmonica m_harmonica;
    private HeightChooser m_heightChooser;
    private JComboBox m_possibilitiesCombo;
    private ArrayList<Tab> m_tabs;
    
    public MappingReferenceChooser(final Harmonica harmonica) {
        this.m_harmonica = null;
        this.m_heightChooser = null;
        this.m_possibilitiesCombo = null;
        this.m_tabs = null;
        this.m_harmonica = harmonica;
        this.m_tabs = new ArrayList<Tab>();
        this.m_heightChooser = new HeightChooser(new Height());
        this.m_possibilitiesCombo = new JComboBox();
        this.setLayout(new BorderLayout(10, 10));
        this.add(this.m_heightChooser, "West");
        this.add(this.m_possibilitiesCombo, "Center");
        final ChangesObserver listener = new ChangesObserver((ChangesObserver)null);
        this.m_heightChooser.addChangeListener(listener);
        this.m_harmonica.addObjectListener(listener);
        this.setOpaque(false);
    }
    
    public Tab getSelectedTab() {
        final int index = this.m_possibilitiesCombo.getSelectedIndex();
        if (index != -1) {
            return this.m_tabs.get(index);
        }
        return null;
    }
    
    public Height getSelectedHeight() {
        return this.m_heightChooser.getSelectedHeight();
    }
    
    private void updateReferencePossibilities() {
        final DefaultComboBoxModel model = (DefaultComboBoxModel)this.m_possibilitiesCombo.getModel();
        model.removeAllElements();
        this.m_tabs.clear();
        for (final Tab tab : this.m_harmonica.getTabPossibilities(this.m_heightChooser.getSelectedHeight())) {
            model.addElement(String.valueOf(tab.getHole()) + " - " + tab.getBreathName());
            this.m_tabs.add(tab);
        }
        final int index = model.getSize();
        if (index > 0) {
            this.m_possibilitiesCombo.setSelectedIndex(index / 2);
        }
    }
    
    private class ChangesObserver implements ChangeListener, HarmoTabObjectListener
    {
        @Override
        public void stateChanged(final ChangeEvent event) {
            MappingReferenceChooser.this.updateReferencePossibilities();
        }
        
        @Override
        public void onObjectChanged(final HarmoTabObjectEvent event) {
            MappingReferenceChooser.this.updateReferencePossibilities();
        }
    }
}
