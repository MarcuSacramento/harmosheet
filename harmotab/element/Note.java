// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.core.Localizer;
import harmotab.core.HarmoTabObject;
import harmotab.core.undo.RestoreCommand;
import harmotab.core.Height;
import harmotab.core.Figure;

public class Note extends TrackElement
{
    public static final String FIGURE_ATTR = "figure";
    public static final String REST_ATTR = "rest";
    public static final String HEIGHT_ATTR = "height";
    public static final String TIED_ATTR = "tied";
    public static final String NOTE_ATTR = "note";
    public static final boolean DEFAULT_REST = false;
    public static final boolean DEFAULT_TIED = false;
    protected Figure m_figure;
    protected Height m_height;
    protected boolean m_isRest;
    protected boolean m_isTied;
    
    public Note() {
        super((byte)2);
        this.m_figure = null;
        this.m_height = null;
        this.m_isRest = false;
        this.m_isTied = false;
        this.setFigure(new Figure());
        this.setHeight(new Height());
        this.setRest(false);
        this.setTied(false);
    }
    
    public Note(final Height height) {
        super((byte)2);
        this.m_figure = null;
        this.m_height = null;
        this.m_isRest = false;
        this.m_isTied = false;
        this.setHeight(height);
        this.setFigure(new Figure());
        this.setRest(false);
        this.setTied(false);
    }
    
    public Note(final Figure figure) {
        super((byte)2);
        this.m_figure = null;
        this.m_height = null;
        this.m_isRest = false;
        this.m_isTied = false;
        this.setFigure(figure);
        this.setHeight(new Height());
        this.setRest(false);
        this.setTied(false);
    }
    
    public Note(final Height height, final Figure figure) {
        super((byte)2);
        this.m_figure = null;
        this.m_height = null;
        this.m_isRest = false;
        this.m_isTied = false;
        this.setFigure(figure);
        this.setHeight(height);
        this.setRest(false);
        this.setTied(false);
    }
    
    @Override
    public Object clone() {
        final Note n = (Note)super.clone();
        final Figure f = (Figure)this.m_figure.clone();
        n.m_figure = null;
        n.setFigure(f);
        final Height h = (Height)this.m_height.clone();
        n.m_height = null;
        n.setHeight(h);
        return n;
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new NoteRestoreCommand(this);
    }
    
    public Figure getFigure() {
        return this.m_figure;
    }
    
    public void setFigure(final byte figure) {
        this.setFigure(new Figure(figure));
    }
    
    public void setFigure(final Figure figure) {
        this.removeAttributeChangesObserver(this.m_figure, "figure");
        this.addAttributeChangesObserver(this.m_figure = figure, "figure");
        this.fireObjectChanged("figure");
    }
    
    public Height getHeight() {
        return this.m_height;
    }
    
    public void setHeight(final Height height) {
        this.removeAttributeChangesObserver(this.m_height, "height");
        this.addAttributeChangesObserver(this.m_height = height, "height");
        this.fireObjectChanged("height");
    }
    
    public boolean isRest() {
        return this.m_isRest;
    }
    
    public void setRest(final boolean isRest) {
        this.m_isRest = isRest;
        this.fireObjectChanged("rest");
    }
    
    public boolean isTied() {
        return this.m_isTied;
    }
    
    public void setTied(final boolean isTied) {
        this.m_isTied = isTied;
        this.fireObjectChanged("tied");
    }
    
    @Override
    public String getTrackElementLocalizedName() {
        return Localizer.get("N_NOTE");
    }
    
    @Override
    public float getDuration() {
        return this.m_figure.getDuration();
    }
    
    @Override
    public float getWidthUnit() {
        return this.m_figure.getWidth();
    }
    
    public boolean isHookable() {
        return this.m_figure.isHookable() && (!this.m_isRest || this.m_figure.isTriplet());
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = super.serialize(serializer);
        object.setElementAttribute("figure", this.m_figure);
        object.setElementAttribute("height", this.m_height);
        if (this.isRest()) {
            object.setAttribute("rest", new StringBuilder(String.valueOf(this.isRest())).toString());
        }
        if (this.m_figure.isDotted()) {
            object.setAttribute("dotted", new StringBuilder(String.valueOf(this.m_figure.isDotted())).toString());
        }
        if (this.isTied()) {
            object.setAttribute("tied", new StringBuilder(String.valueOf(this.isTied())).toString());
        }
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        this.setFigure(object.hasAttribute("figure") ? ((Figure)object.getElementAttribute("figure")) : new Figure());
        this.setHeight(object.hasAttribute("height") ? ((Height)object.getElementAttribute("height")) : new Height());
        this.setRest(object.hasAttribute("rest") && Boolean.parseBoolean(object.getAttribute("rest")));
        this.m_figure.setDotted(object.hasAttribute("dotted") && Boolean.parseBoolean(object.getAttribute("dotted")));
        this.setTied(object.hasAttribute("tied") && Boolean.parseBoolean(object.getAttribute("tied")));
    }
}
