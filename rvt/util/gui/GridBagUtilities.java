// 
// Decompiled by Procyon v0.5.30
// 

package rvt.util.gui;

import java.awt.Insets;
import java.awt.GridBagConstraints;

public class GridBagUtilities
{
    private static GridBagConstraints m_constraints;
    
    static {
        GridBagUtilities.m_constraints = new GridBagConstraints();
    }
    
    public static GridBagConstraints getConstraints(final int gridx, final int gridy) {
        final GridBagConstraints c = createNewConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        return c;
    }
    
    public static GridBagConstraints getConstraints(final int gridx, final int gridy, final int gridwidth, final int gridheight) {
        final GridBagConstraints c = createNewConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        return c;
    }
    
    public static GridBagConstraints getConstraints(final int gridx, final int gridy, final double weightx, final double weighty) {
        final GridBagConstraints c = createNewConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        c.weightx = weightx;
        c.weighty = weighty;
        return c;
    }
    
    public static GridBagConstraints getConstraints(final int gridx, final int gridy, final int gridwidth, final int gridheight, final double weightx, final double weighty) {
        final GridBagConstraints c = createNewConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        c.weightx = weightx;
        c.weighty = weighty;
        return c;
    }
    
    public static void setDefaultPositionning(final int anchor, final int fill) {
        GridBagUtilities.m_constraints.anchor = anchor;
        GridBagUtilities.m_constraints.fill = fill;
    }
    
    public static void setDefaultPadding(final Insets insets, final int ipadx, final int ipady) {
        GridBagUtilities.m_constraints.insets = insets;
        GridBagUtilities.m_constraints.ipadx = ipadx;
        GridBagUtilities.m_constraints.ipady = ipady;
    }
    
    public static void setDefaultWeight(final double weightx, final double weighty) {
        GridBagUtilities.m_constraints.weightx = weightx;
        GridBagUtilities.m_constraints.weighty = weighty;
    }
    
    private static GridBagConstraints createNewConstraints() {
        return (GridBagConstraints)GridBagUtilities.m_constraints.clone();
    }
}
