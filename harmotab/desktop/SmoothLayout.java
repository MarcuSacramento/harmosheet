// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop;

import java.awt.Dimension;
import java.awt.Container;
import java.awt.Component;
import java.awt.LayoutManager2;

public class SmoothLayout implements LayoutManager2
{
    private Component northComponent;
    private Component southComponent;
    private Component eastComponent;
    private Component westComponent;
    public static final String NORTH = "North";
    public static final String SOUTH = "South";
    public static final String EAST = "East";
    public static final String WEST = "West";
    public static String lastPositionning;
    
    static {
        SmoothLayout.lastPositionning = null;
    }
    
    public SmoothLayout() {
        this.northComponent = null;
        this.southComponent = null;
        this.eastComponent = null;
        this.westComponent = null;
    }
    
    @Override
    public void addLayoutComponent(final String string, final Component component) {
        if (string.equals("North")) {
            this.northComponent = component;
        }
        else if (string.equals("South")) {
            this.southComponent = component;
        }
        else if (string.equals("East")) {
            this.eastComponent = component;
        }
        else if (string.equals("West")) {
            this.westComponent = component;
        }
        SmoothLayout.lastPositionning = string;
    }
    
    @Override
    public void layoutContainer(final Container container) {
        if (this.northComponent != null) {
            final Dimension dim = this.northComponent.getPreferredSize();
            this.northComponent.setBounds(0, 0, container.getWidth(), dim.height);
        }
        else if (this.southComponent != null) {
            final Dimension dim = this.southComponent.getPreferredSize();
            this.southComponent.setBounds(0, container.getHeight() - dim.height, container.getWidth(), dim.height);
        }
        else if (this.westComponent != null) {
            final Dimension dim = this.westComponent.getPreferredSize();
            this.westComponent.setBounds(0, 0, dim.width, container.getHeight());
        }
        else if (this.eastComponent != null) {
            final Dimension dim = this.eastComponent.getPreferredSize();
            this.eastComponent.setBounds(container.getWidth() - dim.width, 0, dim.width, container.getHeight());
        }
    }
    
    @Override
    public Dimension minimumLayoutSize(final Container container) {
        return new Dimension(0, 0);
    }
    
    @Override
    public Dimension preferredLayoutSize(final Container container) {
        return new Dimension(0, 0);
    }
    
    @Override
    public void removeLayoutComponent(final Component component) {
        if (component == this.northComponent) {
            this.northComponent = null;
        }
        else if (component == this.westComponent) {
            this.westComponent = null;
        }
        else if (component == this.southComponent) {
            this.southComponent = null;
        }
        else if (component == this.eastComponent) {
            this.eastComponent = null;
        }
    }
    
    @Override
    public void addLayoutComponent(final Component component, final Object object) {
        if (object != null) {
            this.addLayoutComponent((String)object, component);
        }
    }
    
    @Override
    public float getLayoutAlignmentX(final Container container) {
        return 0.0f;
    }
    
    @Override
    public float getLayoutAlignmentY(final Container container) {
        return 0.0f;
    }
    
    @Override
    public void invalidateLayout(final Container container) {
    }
    
    @Override
    public Dimension maximumLayoutSize(final Container container) {
        return null;
    }
}
