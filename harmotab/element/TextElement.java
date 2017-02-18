// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.throwables.UnhandledCaseError;
import harmotab.core.undo.RestoreCommand;
import java.awt.Font;

public class TextElement extends Element
{
    public static final String TEXT_ATTR = "text";
    public static final String FONT_ATTR = "font";
    public static final String ALIGNMENT_ATTR = "alignment";
    public static final String FONT_FAMILY_ATTR = "fontFamily";
    public static final String FONT_SIZE_ATTR = "fontSize";
    public static final String FONT_STYLE_ATTR = "fontStyle";
    public static final String LEFT = "left";
    public static final String CENTER = "center";
    public static final String RIGHT = "right";
    public static final String DEFAULT_ALIGNMENT = "left";
    protected String m_text;
    protected Font m_font;
    protected String m_alignment;
    
    public TextElement() {
        super((byte)1);
        this.m_text = null;
        this.m_font = null;
        this.m_alignment = "left";
        this.setText(null);
        this.setFont(null);
        this.setAlignment("left");
    }
    
    public TextElement(final String text) {
        super((byte)1);
        this.m_text = null;
        this.m_font = null;
        this.m_alignment = "left";
        this.setText(text);
        this.setFont(null);
        this.setAlignment("left");
    }
    
    public TextElement(final String text, final Font font, final String alignment) {
        super((byte)1);
        this.m_text = null;
        this.m_font = null;
        this.m_alignment = "left";
        this.setText(text);
        this.setFont(font);
        this.setAlignment(alignment);
    }
    
    @Override
    public Object clone() {
        final TextElement text = (TextElement)super.clone();
        return text;
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new TextElementRestoreCommand(this);
    }
    
    public void setText(final String text) {
        this.m_text = text;
        this.fireObjectChanged("text");
    }
    
    public String getText() {
        return this.m_text;
    }
    
    public void setFont(final Font font) {
        if (font == null) {
            this.m_font = new Font("Sans-serif", 0, 12);
        }
        else {
            this.m_font = font;
        }
        this.fireObjectChanged("font");
    }
    
    public Font getFont() {
        return this.m_font;
    }
    
    public void setAlignment(final String alignment) {
        if (alignment.equals("left") || alignment.equals("center") || alignment.equals("right")) {
            this.m_alignment = alignment;
            this.fireObjectChanged("alignment");
            return;
        }
        throw new UnhandledCaseError("Invalid alignment !");
    }
    
    public String getAlignment() {
        return this.m_alignment;
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = super.serialize(serializer);
        object.setAttribute("text", this.getText());
        if (this.getFont() != null) {
            object.setAttribute("font", "");
            object.setAttribute("fontFamily", this.getFont().getFamily());
            object.setAttribute("fontSize", new StringBuilder(String.valueOf(this.getFont().getSize())).toString());
            object.setAttribute("fontStyle", new StringBuilder(String.valueOf(this.getFont().getStyle())).toString());
        }
        if (this.getAlignment() != "left") {
            object.setAttribute("alignment", this.getAlignment());
        }
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        this.setText(object.getAttribute("text"));
        if (object.hasAttribute("font")) {
            final Font font = new Font(object.getAttribute("font"), Integer.parseInt(object.getAttribute("fontStyle")), Integer.parseInt(object.getAttribute("fontSize")));
            this.setFont(font);
        }
        if (object.hasAttribute("alignment")) {
            this.setAlignment(object.getAttribute("alignment"));
        }
    }
}
