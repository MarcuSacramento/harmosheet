// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.harmonica;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import java.util.Collection;
import harmotab.throwables.OutOfBoundsError;
import harmotab.element.Tab;
import harmotab.core.undo.RestoreCommand;
import harmotab.core.Height;
import harmotab.core.HarmoTabObject;

public class Harmonica extends HarmoTabObject
{
    public static final String HARMONICA_TYPESTR = "harmonica";
    public static final String NAME_ATTR = "name";
    public static final String MODEL_ATTR = "model";
    public static final String TUNNING_ATTR = "tunning";
    public static final String NUMBER_OF_HOLES_ATTR = "numberOfHoles";
    public static final String HARMONICA_TYPE_ATTR = "harmonicaType";
    protected String m_name;
    protected HarmonicaModel m_model;
    protected Height m_tunning;
    protected int m_tunningOffset;
    
    public Harmonica(final String name, final HarmonicaModel model, final Height tunning) {
        this.m_name = null;
        this.m_model = null;
        this.m_tunning = null;
        this.setName(name);
        this.setModel(model);
        this.setTunning(tunning);
    }
    
    public Harmonica(final String name) {
        this(name, new HarmonicaModel(), new Height());
    }
    
    public Harmonica(final HarmonicaModel model) {
        this(model.getName(), model, new Height());
    }
    
    public Harmonica() {
        this("");
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new HarmonicaRestoreCommand(this);
    }
    
    public HarmonicaModel getModel() {
        return this.m_model;
    }
    
    public void setModel(final HarmonicaModel model) {
        if (model == null) {
            throw new NullPointerException();
        }
        this.removeAttributeChangesObserver(this.m_model, "model");
        this.addAttributeChangesObserver(this.m_model = model, "model");
        this.fireObjectChanged("model");
    }
    
    public Height getTunning() {
        return this.m_tunning;
    }
    
    public void setTunning(final Height tunning) {
        if (tunning == null) {
            throw new NullPointerException();
        }
        this.removeAttributeChangesObserver(this.m_tunning, "tunning");
        this.m_tunning = tunning;
        this.m_tunningOffset = this.m_tunning.getAlteredNoteId();
        this.addAttributeChangesObserver(this.m_tunning, "tunning");
        this.fireObjectChanged("tunning");
    }
    
    public String getName() {
        return this.m_name;
    }
    
    public void setName(final String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        this.m_name = name;
        this.fireObjectChanged("name");
    }
    
    public Height getHeight(final Tab tab) {
        final Height height = this.m_model.getHeight(tab);
        if (height == null) {
            return null;
        }
        try {
            return new Height(height.getSoundId() + this.m_tunningOffset);
        }
        catch (OutOfBoundsError e) {
            return null;
        }
    }
    
    public void setHeight(final Tab tab, final Height h) {
        final Height height = new Height(h.getSoundId() - this.m_tunningOffset);
        this.m_model.setHeight(tab, height);
    }
    
    public Tab getTab(final Height h) {
        final Height height = new Height(h.getSoundId() - this.m_tunningOffset);
        return this.m_model.getTab(height);
    }
    
    public Collection<Tab> getTabPossibilities(final Height h) {
        final Height height = new Height(h.getSoundId() - this.m_tunningOffset);
        return this.m_model.getTabPossibilities(height);
    }
    
    public boolean isSet(final Tab tab) {
        return this.m_model.isSet(tab);
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = serializer.createSerializedObject("harmonica", this.hashCode());
        object.setAttribute("name", this.getName());
        object.setElementAttribute("tunning", this.m_tunning);
        object.setAttribute("numberOfHoles", String.valueOf(this.m_model.getNumberOfHoles()));
        object.setAttribute("harmonicaType", this.m_model.getHarmonicaType().toString());
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        this.setName(object.getAttribute("name"));
        this.setTunning((Height)object.getElementAttribute("tunning"));
        if (object.hasAttribute("numberOfHoles")) {
            this.m_model.setNumberOfHoles(Integer.parseInt(object.getAttribute("numberOfHoles")));
        }
        if (object.hasAttribute("harmonicaType")) {
            this.m_model.setHarmonicaType(HarmonicaType.parseHarmonicaType(object.getAttribute("harmonicaType")));
        }
    }
}
