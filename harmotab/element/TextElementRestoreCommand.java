// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.core.undo.RestoreCommand;

class TextElementRestoreCommand extends TextElement implements RestoreCommand
{
    private TextElement m_saved;
    
    public TextElementRestoreCommand(final TextElement saved) {
        this.m_saved = saved;
        this.m_text = this.m_saved.m_text;
        this.m_font = this.m_saved.m_font;
        this.m_alignment = this.m_saved.m_alignment;
    }
    
    @Override
    public void execute() {
        if (this.m_text != this.m_saved.m_text) {
            this.m_saved.setText(this.m_text);
        }
        if (this.m_font != this.m_saved.m_font) {
            this.m_saved.setFont(this.m_font);
        }
        if (this.m_alignment != this.m_saved.m_alignment) {
            this.m_saved.setAlignment(this.m_alignment);
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new TextElementRestoreCommand(this.m_saved);
    }
}
