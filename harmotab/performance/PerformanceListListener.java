// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.performance;

public interface PerformanceListListener
{
    void onPerformanceListChanged(final PerformancesList p0);
    
    void onDefaultPerformanceChanged(final PerformancesList p0);
    
    void onPerformanceListItemChanged(final PerformancesList p0, final Performance p1);
}
