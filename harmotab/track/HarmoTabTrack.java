// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.track;

import harmotab.harmonica.TabModelController;
import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.element.Bar;
import java.util.ArrayList;
import harmotab.element.TrackElement;
import java.util.Collection;
import harmotab.core.HarmoTabObjectListener;
import harmotab.element.HarmoTabElement;
import harmotab.element.Element;
import harmotab.core.HarmoTabObject;
import harmotab.core.undo.RestoreCommand;
import harmotab.core.Localizer;
import harmotab.track.layout.TrackLayout;
import harmotab.track.layout.HarmoTabTrackLayout;
import harmotab.core.Score;
import harmotab.harmonica.TabAutomationProcessor;
import harmotab.harmonica.Harmonica;
import harmotab.harmonica.TabModel;

public class HarmoTabTrack extends StaffTrack
{
    public static String HARMOTAB_TRACK_TYPESTR;
    public static String TAB_MODEL_ATTR;
    public static String HARMONICA_ATTR;
    protected TabModel m_tabModel;
    protected Harmonica m_harmonica;
    private TabAutomationProcessor m_tabAutomationProcessor;
    
    static {
        HarmoTabTrack.HARMOTAB_TRACK_TYPESTR = "harmoTabTrack";
        HarmoTabTrack.TAB_MODEL_ATTR = "tabModel";
        HarmoTabTrack.HARMONICA_ATTR = "harmonica";
    }
    
    protected HarmoTabTrack() {
        this.m_tabAutomationProcessor = null;
    }
    
    public HarmoTabTrack(final Score score) {
        super(score);
        this.m_tabAutomationProcessor = null;
        this.setTrackLayout(new HarmoTabTrackLayout(this));
        this.setTabModel(new TabModel());
        this.setHarmonica(new Harmonica());
        this.setName(Localizer.get("N_HARMOTAB_TRACK"));
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new HarmoTabTrackRestoreCommand(this);
    }
    
    public void setTabModel(final TabModel model) {
        if (model == null) {
            throw new NullPointerException();
        }
        this.m_tabModel = model;
        if (this.m_tabAutomationProcessor == null) {
            this.m_tabAutomationProcessor = new TabAutomationProcessor(this.m_tabModel);
        }
        else {
            this.m_tabAutomationProcessor.setTabModel(this.m_tabModel);
        }
        this.fireObjectChanged(HarmoTabTrack.TAB_MODEL_ATTR);
    }
    
    public TabModel getTabModel() {
        return this.m_tabModel;
    }
    
    public void setHarmonica(final Harmonica harmonica) {
        if (harmonica == null) {
            throw new NullPointerException();
        }
        this.removeAttributeChangesObserver(this.m_harmonica, HarmoTabTrack.HARMONICA_ATTR);
        this.addAttributeChangesObserver(this.m_harmonica = harmonica, HarmoTabTrack.HARMONICA_ATTR);
        this.fireObjectChanged(HarmoTabTrack.HARMONICA_ATTR);
    }
    
    public Harmonica getHarmonica() {
        return this.m_harmonica;
    }
    
    @Override
    public void add(final Element element) {
        if (element instanceof HarmoTabElement) {
            element.addObjectListener(this.m_tabAutomationProcessor);
            this.m_tabAutomationProcessor.doAutoTab((HarmoTabElement)element);
        }
        super.add(element);
    }
    
    @Override
    public void add(final int index, final Element element) {
        if (element instanceof HarmoTabElement) {
            element.addObjectListener(this.m_tabAutomationProcessor);
            this.m_tabAutomationProcessor.doAutoTab((HarmoTabElement)element);
        }
        super.add(index, element);
    }
    
    @Override
    public Collection<TrackElement> getAddableElements() {
        final ArrayList<TrackElement> list = new ArrayList<TrackElement>();
        list.add(new HarmoTabElement());
        list.add(new Bar());
        return list;
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = super.serialize(serializer);
        object.setAttribute("type", HarmoTabTrack.HARMOTAB_TRACK_TYPESTR);
        if (this.m_harmonica != null) {
            object.setElementAttribute(HarmoTabTrack.HARMONICA_ATTR, this.m_harmonica);
        }
        if (this.m_tabModel != null) {
            object.setElementAttribute(HarmoTabTrack.TAB_MODEL_ATTR, this.m_tabModel);
        }
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        if (object.hasAttribute(HarmoTabTrack.HARMONICA_ATTR)) {
            this.setHarmonica((Harmonica)object.getElementAttribute(HarmoTabTrack.HARMONICA_ATTR));
        }
        if (object.hasAttribute(HarmoTabTrack.TAB_MODEL_ATTR)) {
            this.setTabModel((TabModel)object.getElementAttribute(HarmoTabTrack.TAB_MODEL_ATTR));
        }
        super.deserialize(serializer, object);
        final TabModelController tabModelController = new TabModelController(this.m_tabModel);
        tabModelController.populateFromHarmoTabTrack(this);
    }
}
