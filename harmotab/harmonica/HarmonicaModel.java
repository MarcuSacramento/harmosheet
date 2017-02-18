// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.harmonica;

import harmotab.throwables.NotImplementedError;
import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import java.util.ArrayList;
import java.util.Collection;
import harmotab.element.Tab;
import harmotab.throwables.OutOfBoundsError;
import harmotab.core.undo.RestoreCommand;
import harmotab.core.Height;
import java.util.Vector;
import harmotab.core.HarmoTabObject;

public class HarmonicaModel extends HarmoTabObject
{
    public static final String HARMONICA_MODEL_TYPESTR = "harmonicaModel";
    public static final String NAME_ATTR = "name";
    public static final String NUMBER_OF_HOLES_ATTR = "numberOfHoles";
    public static final String CONTENT_ATTR = "content";
    public static final String HARMONICA_TYPE_ATTR = "harmonicaType";
    public static final int MIN_HOLE_VALUE = 1;
    public static final int MAX_HOLE_VALUE = 50;
    public static final int MIN_NUMBER_OF_HOLES = 4;
    public static final int MAX_NUMBER_OF_HOLES = 50;
    private static final String DEFAULT_NAME = "";
    private static final int DEFAULT_NUMBER_OF_HOLES = 10;
    private static final HarmonicaType DEFAULT_HARMONICA_TYPE;
    private static final byte NUMBER_OF_BREATH_PER_HOLE = 6;
    protected String m_name;
    protected int m_numberOfHoles;
    protected HarmonicaType m_harmonicaType;
    protected Vector<Vector<Height>> m_naturalModel;
    protected Vector<Vector<Height>> m_pushedModel;
    
    static {
        DEFAULT_HARMONICA_TYPE = HarmonicaType.DIATONIC;
    }
    
    public HarmonicaModel(final String name, final int numberOfHoles) {
        this.m_naturalModel = null;
        this.m_pushedModel = null;
        this.initModel();
        this.setName(name);
        this.setNumberOfHoles(numberOfHoles);
        this.setHarmonicaType(HarmonicaModel.DEFAULT_HARMONICA_TYPE);
    }
    
    public HarmonicaModel() {
        this("", 10);
    }
    
    private void initModel() {
        this.m_naturalModel = new Vector<Vector<Height>>(10);
        this.m_pushedModel = new Vector<Vector<Height>>(10);
    }
    
    public void resetModel() {
        this.initModel();
        this.setNumberOfHoles(10);
        this.setName("");
        this.fireObjectChanged("content");
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new HarmonicaModelRestoreCommand(this);
    }
    
    public String getName() {
        return this.m_name;
    }
    
    public void setName(final String name) {
        this.m_name = name;
        this.fireObjectChanged("name");
    }
    
    public int getNumberOfHoles() {
        return this.m_numberOfHoles;
    }
    
    public void setNumberOfHoles(final int numberOfHoles) throws OutOfBoundsError {
        if (numberOfHoles < 4 || numberOfHoles > 50) {
            throw new OutOfBoundsError("Invalid number of holes (" + numberOfHoles + ") !");
        }
        this.m_numberOfHoles = numberOfHoles;
        this.m_naturalModel.setSize(6);
        this.m_pushedModel.setSize(6);
        for (int i = this.m_naturalModel.size() - 1; i >= 0; --i) {
            if (this.m_naturalModel.elementAt(i) == null) {
                this.m_naturalModel.set(i, new Vector<Height>(10));
                this.m_pushedModel.set(i, new Vector<Height>(10));
            }
        }
        for (int i = 0; i < 6; ++i) {
            this.m_naturalModel.elementAt(i).setSize(numberOfHoles);
            this.m_pushedModel.elementAt(i).setSize(numberOfHoles);
        }
        this.fireObjectChanged("numberOfHoles");
    }
    
    public HarmonicaType getHarmonicaType() {
        return this.m_harmonicaType;
    }
    
    public void setHarmonicaType(final HarmonicaType type) {
        this.m_harmonicaType = type;
        this.fireObjectChanged("harmonicaType");
    }
    
    public Height getHeight(final Tab tab) {
        if (tab == null) {
            throw new NullPointerException();
        }
        final int hole = tab.getHole();
        if (hole == 0 || tab.getDirection() == 0) {
            throw new IllegalArgumentException("Undefined tab.");
        }
        if (hole < 1 || hole > this.m_numberOfHoles) {
            throw new IllegalArgumentException("Invalid hole number (" + tab.getHole() + ") !");
        }
        Height res = null;
        if (tab.isPushed()) {
            res = this.m_pushedModel.elementAt(getBreathIndex(tab)).elementAt(hole - 1);
        }
        else {
            res = this.m_naturalModel.elementAt(getBreathIndex(tab)).elementAt(hole - 1);
        }
        return (res != null) ? new Height(res) : null;
    }
    
    public void setHeight(final Tab tab, final Height height) {
        if (tab == null || height == null) {
            throw new NullPointerException();
        }
        final int hole = tab.getHole();
        if (hole == 0 || tab.getDirection() == 0) {
            throw new IllegalArgumentException("Undefined tab.");
        }
        if (hole < 1 || hole > this.m_numberOfHoles) {
            throw new IllegalArgumentException("Invalid hole number (" + hole + ").");
        }
        if (tab.isPushed()) {
            this.m_pushedModel.elementAt(getBreathIndex(tab)).set(hole - 1, new Height(height));
        }
        else {
            this.m_naturalModel.elementAt(getBreathIndex(tab)).set(hole - 1, new Height(height));
        }
        this.fireObjectChanged("content");
    }
    
    public boolean isSet(final Tab tab) {
        if (tab == null) {
            throw new NullPointerException();
        }
        final int hole = tab.getHole();
        if (hole == 0 || tab.getDirection() == 0) {
            throw new IllegalArgumentException("Undefined tab.");
        }
        if (hole < 1 || hole > this.m_numberOfHoles) {
            throw new IllegalArgumentException("Invalid hole number (" + hole + ").");
        }
        if (tab.isPushed()) {
            return this.m_pushedModel.elementAt(getBreathIndex(tab)).get(hole - 1) != null;
        }
        return this.m_naturalModel.elementAt(getBreathIndex(tab)).get(hole - 1) != null;
    }
    
    public void unset(final Tab tab) {
        if (tab == null) {
            throw new NullPointerException();
        }
        final int hole = tab.getHole();
        if (hole == 0 || tab.getDirection() == 0) {
            throw new IllegalArgumentException("Undefined tab.");
        }
        if (hole < 1 || hole > this.m_numberOfHoles) {
            throw new IllegalArgumentException("Invalid hole number (" + hole + ").");
        }
        if (tab.isPushed()) {
            this.m_pushedModel.elementAt(getBreathIndex(tab)).set(hole - 1, null);
        }
        else {
            this.m_naturalModel.elementAt(getBreathIndex(tab)).set(hole - 1, null);
        }
        this.fireObjectChanged("content");
    }
    
    public Tab getTab(final Height height) {
        if (height == null) {
            throw new NullPointerException();
        }
        for (int holesNumber = this.getNumberOfHoles(), hole = 0; hole < holesNumber; ++hole) {
            for (byte j = 0; j < 6; ++j) {
                final Height naturalModelHeight = this.m_naturalModel.elementAt(j).get(hole);
                if (naturalModelHeight != null && naturalModelHeight.getSoundId() == height.getSoundId()) {
                    return createTab(hole + 1, j);
                }
                final Height pushedModelHeight = this.m_pushedModel.elementAt(j).get(hole);
                if (pushedModelHeight != null && pushedModelHeight.getSoundId() == height.getSoundId()) {
                    return createTab(hole + 1, j, true);
                }
            }
        }
        return null;
    }
    
    public Collection<Tab> getTabPossibilities(final Height height) {
        if (height == null) {
            throw new NullPointerException();
        }
        final ArrayList<Tab> list = new ArrayList<Tab>();
        for (int holesNumber = this.getNumberOfHoles(), hole = 0; hole < holesNumber; ++hole) {
            for (byte j = 0; j < 6; ++j) {
                final Height naturalModelHeight = this.m_naturalModel.elementAt(j).get(hole);
                if (naturalModelHeight != null && naturalModelHeight.getAlteredNoteId() == height.getAlteredNoteId()) {
                    list.add(createTab(hole + 1, j));
                }
                final Height pushedModelHeight = this.m_pushedModel.elementAt(j).get(hole);
                if (pushedModelHeight != null && pushedModelHeight.getAlteredNoteId() == height.getAlteredNoteId()) {
                    list.add(createTab(hole + 1, j));
                }
            }
        }
        return list;
    }
    
    private static int getBreathIndex(final Tab tab) {
        if (tab.getDirection() == 1) {
            switch (tab.getBend()) {
                case 0: {
                    return 2;
                }
                case 1: {
                    return 1;
                }
                case 2: {
                    return 0;
                }
            }
        }
        else if (tab.getDirection() == 2) {
            switch (tab.getBend()) {
                case 0: {
                    return 3;
                }
                case 1: {
                    return 4;
                }
                case 2: {
                    return 5;
                }
            }
        }
        throw new IllegalArgumentException("Direction not defined.");
    }
    
    public static Tab createTab(final int hole, final byte type) {
        switch (type) {
            case 0: {
                return new Tab(hole, (byte)1, (byte)2);
            }
            case 1: {
                return new Tab(hole, (byte)1, (byte)1);
            }
            case 2: {
                return new Tab(hole, (byte)1, (byte)0);
            }
            case 3: {
                return new Tab(hole, (byte)2, (byte)0);
            }
            case 4: {
                return new Tab(hole, (byte)2, (byte)1);
            }
            case 5: {
                return new Tab(hole, (byte)2, (byte)2);
            }
            default: {
                return null;
            }
        }
    }
    
    public static Tab createTab(final int hole, final byte type, final boolean pushed) {
        final Tab tab = createTab(hole, type);
        if (tab != null) {
            tab.setPushed(pushed);
        }
        return tab;
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        throw new NotImplementedError("HarmonicaModel serialization/deserialization not implemented.");
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        throw new NotImplementedError("HarmonicaModel serialization/deserialization not implemented.");
    }
}
