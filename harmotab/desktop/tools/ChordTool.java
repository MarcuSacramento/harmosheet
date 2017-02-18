// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.tools;

import java.awt.event.KeyEvent;
import java.awt.Window;
import harmotab.desktop.setupdialog.ChordSetupDialog;
import harmotab.core.undo.UndoManager;
import java.awt.event.ActionEvent;
import java.awt.Component;
import harmotab.core.Localizer;
import harmotab.renderer.LocationItem;
import harmotab.core.Score;
import java.awt.Container;
import harmotab.desktop.components.FigureChooser;
import harmotab.element.Chord;
import java.awt.event.ActionListener;

public class ChordTool extends Tool implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private Chord m_chord;
    private ToolButton m_chordEditorButton;
    private FigureChooser m_figureChooser;
    
    public ChordTool(final Container container, final Score score, final LocationItem item) {
        super(container, score, item);
        this.m_chord = (Chord)item.getElement();
        (this.m_chordEditorButton = new ToolButton(Localizer.get("TT_CHORD_SELECTION"), (byte)16)).addActionListener(this);
        this.add(this.m_chordEditorButton);
        this.addSeparator();
        (this.m_figureChooser = new FigureChooser(this.m_chord.getFigure())).setSize(60, 30);
        this.m_figureChooser.addActionListener(this);
        this.add(this.m_figureChooser);
    }
    
    @Override
    public void actionPerformed(final ActionEvent event) {
        if (event.getSource() == this.m_chordEditorButton) {
            UndoManager.getInstance().addUndoCommand(this.m_chord.createRestoreCommand(), "TT_CHORD_SELECTION");
            new ChordSetupDialog(null, this.m_chord).setVisible(true);
        }
        else if (event.getSource() == this.m_figureChooser) {
            UndoManager.getInstance().addUndoCommand(this.m_chord.createRestoreCommand(), Localizer.get("N_FIGURE"));
            this.m_chord.setFigure(this.m_figureChooser.getSelectedFigure());
        }
    }
    
    @Override
    public void keyTyped(final KeyEvent event) {
    }
}
