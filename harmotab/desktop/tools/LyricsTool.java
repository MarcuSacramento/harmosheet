// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.tools;

import harmotab.core.Duration;
import javax.swing.event.ChangeEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import harmotab.core.Localizer;
import harmotab.core.undo.UndoManager;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;
import java.awt.Component;
import harmotab.renderer.LocationItem;
import harmotab.core.Score;
import java.awt.Container;
import harmotab.desktop.components.DurationChooser;
import javax.swing.JTextField;
import harmotab.element.Lyrics;
import java.awt.event.ActionListener;

public class LyricsTool extends Tool implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private Lyrics m_lyrics;
    private JTextField m_textField;
    private DurationChooser m_durationChooser;
    
    public LyricsTool(final Container container, final Score score, final LocationItem item) {
        super(container, score, item);
        this.m_lyrics = (Lyrics)item.getElement();
        this.add(this.m_durationChooser = new DurationChooser(this.m_lyrics.getDurationObject()));
        this.m_textField = new JTextField();
        this.m_container.add(this.m_textField);
        this.m_textField.setBounds(this.m_locationItem.getX1(), this.m_locationItem.getY1(), this.m_locationItem.getWidth(), this.m_locationItem.getHeight());
        this.m_textField.setText(this.m_lyrics.getText());
        this.m_textField.addActionListener(this);
        this.m_durationChooser.addChangeListener(new DurationChangesObserver());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LyricsTool.this.m_textField.setSelectionStart(0);
                LyricsTool.this.m_textField.setSelectionEnd(LyricsTool.this.m_textField.getText().length());
                LyricsTool.this.m_textField.requestFocus();
            }
        });
    }
    
    @Override
    public void setVisible(final boolean visible) {
        super.setVisible(visible);
        if (!visible) {
            this.m_container.remove(this.m_textField);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    LyricsTool.this.validateText();
                }
            });
        }
    }
    
    private void validateText() {
        if (!this.m_textField.getText().equals(this.m_lyrics.getText())) {
            UndoManager.getInstance().addUndoCommand(this.m_lyrics.createRestoreCommand(), Localizer.get("N_LYRICS"));
            this.m_lyrics.setText(this.m_textField.getText());
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
    
    private class DurationChangesObserver implements ChangeListener
    {
        @Override
        public void stateChanged(final ChangeEvent event) {
            if (event.getSource() == LyricsTool.this.m_durationChooser) {
                LyricsTool.this.m_lyrics.setDispachEvents(false, null);
                LyricsTool.this.validateText();
                LyricsTool.this.m_lyrics.setDispachEvents(true, null);
                UndoManager.getInstance().addUndoCommand(LyricsTool.this.m_lyrics.createRestoreCommand(), Localizer.get("N_DURATION"));
                LyricsTool.this.m_lyrics.setDurationObject(new Duration(LyricsTool.this.m_durationChooser.getDurationValue()));
            }
        }
    }
}
