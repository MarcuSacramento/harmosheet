// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.harmonica;

import harmotab.core.HarmoTabObjectFactory;
import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.throwables.OutOfBoundsError;
import harmotab.throwables.BrokenImplementationError;
import harmotab.core.Effect;
import harmotab.core.Height;
import java.util.Iterator;
import harmotab.core.undo.RestoreCommand;
import harmotab.element.Tab;
import java.util.Vector;
import harmotab.core.HarmoTabObject;

public class TabModel extends HarmoTabObject
{
    public static final String TAB_MODEL_TYPESTR = "tabModel";
    public static final String MAPPING_ATTR = "mapping";
    protected Vector<Tab> m_model;
    
    public TabModel() {
        this.m_model = new Vector<Tab>(84);
        this.reset();
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new TabModelRestoreCommand(this);
    }
    
    public Object clone() {
        final TabModel model = (TabModel)super.clone();
        model.m_model = new Vector<Tab>(0);
        for (final Tab tab : this.m_model) {
            model.m_model.add((tab != null) ? ((Tab)tab.clone()) : null);
        }
        return model;
    }
    
    public Tab getTab(final Height height) {
        final Tab tab = this.m_model.elementAt(height.getSoundId());
        if (tab != null) {
            return new Tab(tab);
        }
        return null;
    }
    
    public void setTab(final Height height, final Tab tab) {
        if (tab != null && (tab.getHole() == 0 || tab.getDirection() == 0)) {
            return;
        }
        final Tab addTab = new Tab(tab);
        addTab.setEffect(new Effect());
        this.m_model.setElementAt(addTab, height.getSoundId());
        this.fireObjectChanged("mapping");
    }
    
    public Height getHeight(final Tab tab) {
        if (tab == null || tab.getHole() == 0 || tab.getDirection() == 0) {
            return null;
        }
        for (int size = this.m_model.size(), i = 0; i < size; ++i) {
            if (tab.equals(this.m_model.elementAt(i))) {
                try {
                    return new Height(i);
                }
                catch (OutOfBoundsError e) {
                    throw new BrokenImplementationError("Out of bounds note height (" + i + ") !");
                }
            }
        }
        return null;
    }
    
    public void deleteTab(final Height height) {
        this.m_model.setElementAt(null, height.getSoundId());
        this.fireObjectChanged("mapping");
    }
    
    public void reset() {
        this.m_model.clear();
        for (int i = 0; i <= 85; ++i) {
            this.m_model.add(i, null);
        }
        this.fireObjectChanged("mapping");
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = serializer.createSerializedObject("tabModel", this.hashCode());
        for (int i = 36; i < 84; ++i) {
            final Height height = new Height(i);
            final Tab tab = this.getTab(height);
            if (tab != null && tab.isDefined()) {
                final SerializedObject heightObject = height.serialize(serializer);
                heightObject.setElementAttribute("tab", tab);
                object.addChild(heightObject);
            }
        }
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        for (int numberOfChild = object.getChildsNumber(), i = 0; i < numberOfChild; ++i) {
            final SerializedObject child = object.getChild(i);
            if (child != null) {
                final HarmoTabObject childObject = HarmoTabObjectFactory.create(serializer, child);
                if (childObject instanceof Height) {
                    final Height height = (Height)childObject;
                    final Tab tab = (Tab)child.getElementAttribute("tab");
                    this.setTab(height, tab);
                }
            }
        }
    }
    
    public void print() {
        System.out.print(this + ":");
        for (final Tab tab : this.m_model) {
            System.out.print("-" + ((tab != null) ? tab.getHole() : " "));
        }
        System.out.println("");
    }
}
