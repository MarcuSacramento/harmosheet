// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.core.Localizer;
import harmotab.core.HarmoTabObject;
import harmotab.throwables.OutOfSpecificationError;
import harmotab.throwables.UnhandledCaseError;
import harmotab.throwables.OutOfBoundsError;
import harmotab.core.undo.RestoreCommand;
import harmotab.core.Effect;

public class Tab extends TrackElement
{
    public static final String HOLE_ATTR = "hole";
    public static final String DIRECTION_ATTR = "direction";
    public static final String BEND_ATTR = "bend";
    public static final String EFFECT_ATTR = "effect";
    public static final String PUSHED_ATTR = "pushed";
    public static final byte UNDEFINED = 0;
    public static final String UNDEFINED_STR = "";
    public static final byte BLOW = 1;
    public static final byte DRAW = 2;
    public static final String BLOW_STR = "blow";
    public static final String DRAW_STR = "draw";
    public static final byte NONE = 0;
    public static final byte HALF_BEND = 1;
    public static final byte FULL_BEND = 2;
    public static final String NONE_STR = "none";
    public static final String HALF_BEND_STR = "half";
    public static final String FULL_BEND_STR = "full";
    private static final byte DEFAULT_DIRECTION = 0;
    private static final byte DEFAULT_BEND = 0;
    private static final int DEFAULT_HOLE = 0;
    private static final boolean DEFAULT_PUSHED = false;
    private static final String FULL_OVERBLOW_STRING = "full overblow";
    private static final String HALF_OVERBLOW_STRING = "half overblow";
    private static final String BLOW_STRING = "blow";
    private static final String DRAW_STRING = "draw";
    private static final String HALF_BEND_STRING = "half bend";
    private static final String FULL_BEND_STRING = "full bend";
    protected int m_hole;
    protected byte m_direction;
    protected byte m_bend;
    protected boolean m_pushed;
    protected Effect m_effect;
    
    public Tab(final int hole, final byte direction, final byte bend, final boolean pushed) {
        super((byte)3);
        this.m_effect = null;
        this.setHole(hole);
        this.setDirection(direction);
        this.setBend(bend);
        this.setEffect(new Effect());
        this.setPushed(pushed);
    }
    
    public Tab(final int hole, final byte direction, final byte bend) {
        this(hole, direction, bend, false);
    }
    
    public Tab() {
        this(0, (byte)0, (byte)0);
    }
    
    public Tab(final int hole) {
        this(hole, (byte)0, (byte)0);
    }
    
    public Tab(final byte direction) {
        this(0, direction, (byte)0);
    }
    
    public Tab(final Tab tab) {
        super((byte)3);
        this.m_effect = null;
        this.set(tab);
    }
    
    public void set(final Tab tab) {
        this.setHole(tab.getHole());
        this.setDirection(tab.getDirection());
        this.setBend(tab.getBend());
        this.setEffect((Effect)tab.getEffect().clone());
        this.setPushed(tab.isPushed());
    }
    
    @Override
    public Object clone() {
        final Tab tab = (Tab)super.clone();
        tab.setEffect((Effect)this.getEffect().clone());
        return tab;
    }
    
    @Override
    public boolean equals(final Object object) {
        if (object == null) {
            return false;
        }
        if (!(object instanceof Tab)) {
            return false;
        }
        final Tab tab = (Tab)object;
        return this.m_hole == tab.m_hole && this.m_direction == tab.m_direction && this.m_bend == tab.m_bend && this.m_effect.getType() == tab.getEffect().getType() && this.m_pushed == tab.m_pushed;
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new TabRestoreCommand(this);
    }
    
    public int getHole() {
        return this.m_hole;
    }
    
    public void setHole(final int hole) throws OutOfBoundsError {
        if ((hole < 1 || hole > 50) && hole != 0) {
            throw new OutOfBoundsError("Invalid hole number (" + hole + ") !");
        }
        this.m_hole = hole;
        this.fireObjectChanged("hole");
    }
    
    public byte getDirection() {
        return this.m_direction;
    }
    
    public String getDirectionStr() {
        switch (this.m_direction) {
            case 2: {
                return "draw";
            }
            case 1: {
                return "blow";
            }
            case 0: {
                return "";
            }
            default: {
                throw new UnhandledCaseError("Unhandled direction '#" + this.m_direction + "'");
            }
        }
    }
    
    public void setDirection(final byte direction) {
        if (direction != 1 && direction != 2 && direction != 0) {
            throw new OutOfSpecificationError("Invalid direction '#" + direction + "'");
        }
        this.m_direction = direction;
        this.fireObjectChanged("direction");
    }
    
    public void setDirection(final String direction) {
        if (direction == null) {
            throw new NullPointerException();
        }
        if (direction.equals("draw")) {
            this.setDirection((byte)2);
        }
        else if (direction.equals("blow")) {
            this.setDirection((byte)1);
        }
        else {
            if (!direction.equals("")) {
                throw new IllegalArgumentException("Unknown direction '" + direction + "'");
            }
            this.setDirection((byte)0);
        }
    }
    
    public byte getBend() {
        return this.m_bend;
    }
    
    public String getBendStr() {
        switch (this.m_bend) {
            case 0: {
                return "none";
            }
            case 1: {
                return "half";
            }
            case 2: {
                return "full";
            }
            default: {
                throw new UnhandledCaseError("Unhandled ben '#" + this.m_bend + "'");
            }
        }
    }
    
    public boolean isBended() {
        return this.m_bend != 0;
    }
    
    public void setBend(final byte bend) throws OutOfSpecificationError {
        if (bend != 0 && bend != 1 && bend != 2) {
            throw new OutOfSpecificationError("Invalid bend !");
        }
        this.m_bend = bend;
        this.fireObjectChanged("bend");
    }
    
    public void setBend(final String bend) {
        if (bend == null) {
            throw new NullPointerException();
        }
        if (bend.equals("none")) {
            this.setBend((byte)0);
        }
        else if (bend.equals("half")) {
            this.setBend((byte)1);
        }
        else {
            if (!bend.equals("full")) {
                throw new IllegalArgumentException("Invalid bend '" + bend + "'");
            }
            this.setBend((byte)2);
        }
    }
    
    public Effect getEffect() {
        return this.m_effect;
    }
    
    public void setEffect(final Effect effect) {
        this.removeAttributeChangesObserver(this.m_effect, "effect");
        this.addAttributeChangesObserver(this.m_effect = effect, "effect");
        this.fireObjectChanged("effect");
    }
    
    public boolean isPushed() {
        return this.m_pushed;
    }
    
    public void setPushed(final boolean pushed) {
        this.m_pushed = pushed;
        this.fireObjectChanged("pushed");
    }
    
    public void print() {
        System.out.println(String.valueOf(this.getTrackElementLocalizedName()) + " " + this.getHole() + " " + this.getDirectionStr() + " " + this.getBend() + " " + this.getEffect().getEffectTypeStr() + " " + this.isPushed());
    }
    
    @Override
    public String getTrackElementLocalizedName() {
        return Localizer.get("N_TAB");
    }
    
    public void toggleDirection() {
        switch (this.getDirection()) {
            case 0: {
                this.setDirection((byte)1);
                break;
            }
            case 1: {
                this.setDirection((byte)2);
                break;
            }
            case 2: {
                this.setDirection((byte)0);
                break;
            }
        }
    }
    
    public String getBreathName() {
        Label_0113: {
            switch (this.getDirection()) {
                case 1: {
                    switch (this.getBend()) {
                        case 0: {
                            return "blow";
                        }
                        case 1: {
                            return "half overblow";
                        }
                        case 2: {
                            return "full overblow";
                        }
                        default: {
                            break Label_0113;
                        }
                    }
                    break;
                }
                case 2: {
                    switch (this.getBend()) {
                        case 0: {
                            return "draw";
                        }
                        case 1: {
                            return "half bend";
                        }
                        case 2: {
                            return "full bend";
                        }
                        default: {
                            break Label_0113;
                        }
                    }
                    break;
                }
            }
        }
        throw new IllegalArgumentException();
    }
    
    public void setBreath(final String breath) {
        if (breath.equals("full overblow")) {
            this.setDirection((byte)1);
            this.setBend((byte)2);
        }
        else if (breath.equals("half overblow")) {
            this.setDirection((byte)1);
            this.setBend((byte)1);
        }
        else if (breath.equals("blow")) {
            this.setDirection((byte)1);
            this.setBend((byte)0);
        }
        else if (breath.equals("draw")) {
            this.setDirection((byte)2);
            this.setBend((byte)0);
        }
        else if (breath.equals("half bend")) {
            this.setDirection((byte)2);
            this.setBend((byte)1);
        }
        else {
            if (!breath.equals("full bend")) {
                throw new IllegalArgumentException("Invalid breath '" + breath + "'");
            }
            this.setDirection((byte)2);
            this.setBend((byte)2);
        }
    }
    
    public boolean isEmpty() {
        return this.m_hole == 0 && this.m_direction == 0 && this.m_bend == 0;
    }
    
    public boolean isDefined() {
        return this.m_hole != 0 && this.m_direction != 0;
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = super.serialize(serializer);
        object.setAttribute("hole", new StringBuilder(String.valueOf(this.getHole())).toString());
        object.setAttribute("direction", this.getDirectionStr());
        if (this.getBend() != 0) {
            object.setAttribute("bend", this.getBendStr());
        }
        object.setAttribute("effect", this.m_effect.getEffectTypeStr());
        if (this.isPushed()) {
            object.setAttribute("pushed", String.valueOf(this.isPushed()));
        }
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        this.setHole(Integer.decode(object.getAttribute("hole")));
        this.setDirection(object.getAttribute("direction"));
        if (object.hasAttribute("bend")) {
            this.setBend(object.getAttribute("bend"));
        }
        else {
            this.setBend((byte)0);
        }
        this.setEffect(new Effect(object.getAttribute("effect")));
        this.setPushed(object.hasAttribute("pushed") && Boolean.parseBoolean(object.getAttribute("pushed")));
    }
}
