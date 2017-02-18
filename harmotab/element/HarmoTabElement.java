// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.core.HarmoTabObject;
import harmotab.core.undo.RestoreCommand;
import harmotab.core.Height;
import harmotab.core.Figure;

public class HarmoTabElement extends Note
{
    public static final String TAB_ATTR = "tab";
    protected Tab m_tab;
    
    public HarmoTabElement() {
        this.m_tab = null;
        this.m_type = 4;
        this.setTab(new Tab());
    }
    
    public HarmoTabElement(final Figure figure) {
        super(figure);
        this.m_tab = null;
        this.m_type = 4;
        this.setTab(new Tab());
    }
    
    public HarmoTabElement(final Height height, final Figure figure, final Tab tab) {
        super(height, figure);
        this.m_tab = null;
        this.m_type = 4;
        this.setTab(tab);
    }
    
    public HarmoTabElement(final Height height, final Figure figure) {
        super(height, figure);
        this.m_tab = null;
        this.m_type = 4;
        this.setTab(new Tab());
    }
    
    @Override
    public Object clone() {
        final HarmoTabElement e = (HarmoTabElement)super.clone();
        final Tab t = (Tab)this.m_tab.clone();
        e.setTab(t);
        return e;
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new HarmoTabElementRestoreCommand(this, super.createRestoreCommand());
    }
    
    public Tab getTab() {
        return this.m_tab;
    }
    
    public void setTab(final Tab tab) {
        if (tab == null) {
            throw new NullPointerException();
        }
        this.removeAttributeChangesObserver(this.m_tab, "tab");
        this.addAttributeChangesObserver(this.m_tab = tab, "tab");
        this.fireObjectChanged("tab");
    }
    
    @Override
    public void setRest(final boolean isRest) {
        if (isRest) {
            this.m_tab = new Tab();
        }
        super.setRest(isRest);
    }
    
    @Override
    public void setTied(final boolean tied) {
        if (tied) {
            this.m_tab = new Tab();
        }
        super.setTied(tied);
    }
    
    public boolean canHaveTab() {
        return !this.isRest() && !this.isTied();
    }
    
    @Override
    public boolean contains(final Element subElement) {
        return subElement == this.m_tab;
    }
    
    @Override
    public boolean delete(final Element subElement) {
        if (subElement == this.m_tab) {
            this.setTab(new Tab());
            return true;
        }
        return false;
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = super.serialize(serializer);
        if (this.getTab() != null) {
            object.setElementAttribute("tab", this.getTab());
        }
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        super.deserialize(serializer, object);
        this.setTab(object.hasAttribute("tab") ? ((Tab)object.getElementAttribute("tab")) : new Tab());
    }
}
