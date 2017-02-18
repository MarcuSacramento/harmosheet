// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

public class HarmoTabObjectEvent
{
    public static final String UNDEFINED_PROPERTY = "undefined";
    private HarmoTabObject m_source;
    private String m_property;
    private HarmoTabObjectEvent m_parent;
    
    public HarmoTabObjectEvent(final HarmoTabObject source, final String property, final HarmoTabObjectEvent parent) {
        this.m_source = null;
        this.m_property = null;
        this.m_parent = null;
        this.m_source = source;
        this.m_property = property;
        this.m_parent = parent;
    }
    
    public HarmoTabObjectEvent(final HarmoTabObject source, final String property) {
        this(source, property, null);
    }
    
    public HarmoTabObjectEvent(final HarmoTabObject source) {
        this(source, "undefined");
    }
    
    public HarmoTabObject getSource() {
        return this.m_source;
    }
    
    public String getProperty() {
        return this.m_property;
    }
    
    public HarmoTabObjectEvent getParent() {
        return this.m_parent;
    }
    
    public boolean propertyIs(final String property) {
        return this.m_property.equals(property);
    }
    
    public boolean hierarchyContains(final String property) {
        for (HarmoTabObjectEvent event = this; event != null; event = event.getParent()) {
            if (event.propertyIs(property)) {
                return true;
            }
        }
        return false;
    }
    
    public HarmoTabObjectEvent getHierarchyEvent(final String property) {
        for (HarmoTabObjectEvent event = this; event != null; event = event.getParent()) {
            if (event.propertyIs(property)) {
                return event;
            }
        }
        return null;
    }
    
    public void printStackTrace() {
        HarmoTabObjectEvent event = this;
        String indent = "\t";
        System.out.println("Event " + event);
        while (event != null) {
            System.out.println(String.valueOf(indent) + "Source = " + event.getSource());
            System.out.println(String.valueOf(indent) + "Property = " + event.getProperty());
            System.out.println(String.valueOf(indent) + "Parent = " + event.getParent());
            indent = String.valueOf(indent) + "\t";
            event = event.getParent();
        }
    }
}
