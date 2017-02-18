// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.tools;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import harmotab.core.Localizer;
import harmotab.core.undo.UndoManager;
import javax.swing.SwingUtilities;
import java.awt.Component;
import harmotab.renderer.LocationItem;
import harmotab.core.Score;
import java.awt.Container;
import javax.swing.JTextField;
import harmotab.element.TextElement;
import java.awt.event.ActionListener;

public class TextTool extends Tool implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private TextElement m_textElement;
    private JTextField m_textField;
    
    public TextTool(final Container container, final Score score, final LocationItem item) {
        super(container, score, item);
        this.m_textElement = null;
        this.m_textField = null;
        this.m_textElement = (TextElement)item.getElement();
        this.m_textField = new JTextField();
        this.m_container.add(this.m_textField);
        this.m_textField.setBounds(this.m_locationItem.getX1(), this.m_locationItem.getY1(), this.m_locationItem.getWidth(), this.m_locationItem.getHeight());
        this.m_textField.setText(this.m_textElement.getText());
        this.m_textField.setFont(this.m_textElement.getFont());
        final String align = this.m_textElement.getAlignment();
        if (align.equals("left")) {
            this.m_textField.setHorizontalAlignment(2);
        }
        else if (align.equals("center")) {
            this.m_textField.setHorizontalAlignment(0);
        }
        else if (align.equals("right")) {
            this.m_textField.setHorizontalAlignment(4);
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TextTool.this.m_textField.setSelectionStart(0);
                TextTool.this.m_textField.setSelectionEnd(TextTool.this.m_textField.getText().length());
                TextTool.this.m_textField.requestFocus();
            }
        });
    }
    
    @Override
    public void setVisible(final boolean visible) {
        super.setVisible(visible);
        if (!visible) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    TextTool.this.m_container.remove(TextTool.this.m_textField);
                    if (!TextTool.this.m_textField.getText().equals(TextTool.this.m_textElement.getText())) {
                        UndoManager.getInstance().addUndoCommand(TextTool.this.m_textElement.createRestoreCommand(), Localizer.get("N_SCORE_PROPERTES"));
                        TextTool.this.m_textElement.setText(TextTool.this.m_textField.getText());
                    }
                }
            });
        }
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == this.m_textField) {
            this.setVisible(false);
        }
    }
    
    @Override
    public void keyTyped(final KeyEvent event) {
    }
}
