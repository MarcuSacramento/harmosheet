// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import java.util.Iterator;
import harmotab.performance.Performance;
import harmotab.core.Localizer;
import harmotab.performance.PerformancesList;
import harmotab.performance.PerformanceListListener;
import javax.swing.JComboBox;

public class PerformancesChooser extends JComboBox implements PerformanceListListener
{
    private static final long serialVersionUID = 1L;
    protected PerformancesList m_perfs;
    
    public PerformancesChooser(final PerformancesList perfs, final int selected) {
        this.m_perfs = null;
        (this.m_perfs = perfs).addPerformanceListListener(this);
        this.updateList();
    }
    
    private void updateList() {
        this.removeAllItems();
        this.addItem(Localizer.get("N_SYNTHETIZER"));
        for (final Performance perf : this.m_perfs) {
            this.addItem(perf.getName());
        }
    }
    
    public void setSelectedSynthetizer() {
        this.setSelectedIndex(0);
    }
    
    public void setSelectedPerformanceIndex(final int selected) {
        this.setSelectedIndex(selected + 1);
    }
    
    public void setSelectedPerformance(final Performance performance) {
        this.setSelectedPerformanceIndex(this.m_perfs.indexOf(performance));
    }
    
    public Performance getSelectedPerformance() {
        final int index = this.getSelectedIndex() - 1;
        if (index >= 0) {
            return this.m_perfs.get(index);
        }
        return null;
    }
    
    @Override
    public void onPerformanceListChanged(final PerformancesList list) {
        this.updateList();
    }
    
    @Override
    public void onDefaultPerformanceChanged(final PerformancesList list) {
    }
    
    @Override
    public void onPerformanceListItemChanged(final PerformancesList list, final Performance perf) {
        this.updateList();
    }
}
