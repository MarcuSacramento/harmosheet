// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.tools;

import javax.swing.ImageIcon;
import harmotab.track.Track;
import java.util.ListIterator;
import harmotab.throwables.ObjectNotFoundError;
import harmotab.element.Element;
import java.awt.event.KeyEvent;
import harmotab.core.Height;
import harmotab.core.Figure;
import javax.swing.SwingUtilities;
import harmotab.core.Localizer;
import harmotab.core.undo.UndoManager;
import java.awt.event.ActionEvent;
import java.awt.Component;
import harmotab.desktop.actions.UserAction;
import harmotab.renderer.LocationItem;
import harmotab.core.Score;
import java.awt.Container;
import harmotab.element.Note;
import harmotab.desktop.components.AlterationChooser;
import harmotab.desktop.components.FigureChooser;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;

public class NoteTool extends Tool implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private JButton m_upButton;
    private JButton m_downButton;
    private JToggleButton m_restButton;
    private JToggleButton m_dottedButton;
    private JToggleButton m_tiedButton;
    private JToggleButton m_tripletButton;
    private FigureChooser m_figureChooser;
    private AlterationChooser m_alterationChooser;
    private Note m_note;
    
    public NoteTool(final Container container, final Score score, final LocationItem item) {
        super(container, score, item);
        this.m_upButton = null;
        this.m_downButton = null;
        this.m_restButton = null;
        this.m_dottedButton = null;
        this.m_tiedButton = null;
        this.m_tripletButton = null;
        this.m_figureChooser = null;
        this.m_alterationChooser = null;
        this.m_note = null;
        this.m_note = (Note)this.m_locationItem.getElement();
        this.m_upButton = new ToolButton(new MoveUpAction());
        this.m_downButton = new ToolButton(new MoveDownAction());
        (this.m_figureChooser = new FigureChooser(this.m_note.getFigure())).setSize(60, 30);
        this.m_alterationChooser = new AlterationChooser(this.m_note.getHeight().getAlteration());
        this.m_restButton = new ToolToggleButton(new ToggleRestStateAction());
        this.m_dottedButton = new ToolToggleButton(new ToggleDottedStateAction());
        this.m_tiedButton = new ToolToggleButton(new ToggleTiedStateAction());
        this.m_tripletButton = new ToolToggleButton(new ChangeTrippletStateAction());
        if (this.m_note.isRest()) {
            this.m_restButton.setSelected(true);
            this.m_figureChooser.setDisplayRests(true);
            this.m_alterationChooser.setEnabled(false);
            this.m_upButton.setEnabled(false);
            this.m_downButton.setEnabled(false);
            this.m_tiedButton.setEnabled(false);
        }
        else {
            this.m_alterationChooser.setSelectedAlteration(this.m_note.getHeight().getAlteration());
            this.m_restButton.setSelected(false);
        }
        if (this.m_note.isTied()) {
            this.m_tiedButton.setSelected(true);
            this.m_upButton.setEnabled(false);
            this.m_downButton.setEnabled(false);
            this.m_alterationChooser.setEnabled(false);
            this.m_restButton.setEnabled(false);
        }
        else {
            this.m_tiedButton.setSelected(false);
        }
        this.m_dottedButton.setSelected(this.m_note.getFigure().isDotted());
        this.m_tripletButton.setSelected(this.m_note.getFigure().isTriplet());
        this.add(this.m_upButton);
        this.add(this.m_downButton);
        this.addSeparator();
        this.add(this.m_figureChooser);
        this.add(this.m_alterationChooser);
        this.addSeparator();
        this.add(this.m_restButton);
        this.add(this.m_dottedButton);
        this.add(this.m_tiedButton);
        this.add(this.m_tripletButton);
        this.m_figureChooser.addActionListener(this);
        this.m_alterationChooser.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(final ActionEvent event) {
        UserAction action = null;
        if (event.getSource() == this.m_figureChooser) {
            final Boolean dotted = this.m_note.getFigure().isDotted();
            final Figure figure = this.m_figureChooser.getSelectedFigure();
            figure.setDotted(dotted);
            action = new ChangeFigureAction(figure);
        }
        else if (event.getSource() == this.m_alterationChooser) {
            final Height height = this.m_note.getHeight();
            UndoManager.getInstance().addUndoCommand(height.createRestoreCommand(), Localizer.get("N_ALTERATION"));
            height.setAlteration(this.m_alterationChooser.getSelectedAlteration());
            this.tieCheck();
        }
        if (action != null) {
            SwingUtilities.invokeLater(action);
        }
    }
    
    @Override
    public void keyTyped(final KeyEvent event) {
        UserAction action = null;
        switch (event.getKeyCode()) {
            case 38: {
                action = new MoveUpAction();
                break;
            }
            case 40: {
                action = new MoveDownAction();
                break;
            }
        }
        switch (event.getKeyChar()) {
            case '.': {
                final Figure figure = this.m_note.getFigure();
                UndoManager.getInstance().addUndoCommand(figure.createRestoreCommand(), Localizer.get("TT_DOTTED"));
                figure.setDotted(!this.m_note.getFigure().isDotted());
                break;
            }
            case '+': {
                try {
                    final Figure figure = this.m_note.getFigure();
                    UndoManager.getInstance().addUndoCommand(figure.createRestoreCommand(), Localizer.get("N_FIGURE"));
                    figure.setType((byte)(this.m_note.getFigure().getType() - 1));
                }
                catch (Throwable t) {}
                break;
            }
            case '-': {
                try {
                    final Figure figure = this.m_note.getFigure();
                    UndoManager.getInstance().addUndoCommand(figure.createRestoreCommand(), Localizer.get("N_FIGURE"));
                    figure.setType((byte)(this.m_note.getFigure().getType() + 1));
                }
                catch (Throwable t2) {}
                break;
            }
            case '#': {
                final Height height = this.m_note.getHeight();
                UndoManager.getInstance().addUndoCommand(height.createRestoreCommand(), Localizer.get("N_SHARP"));
                height.setAlteration((byte)1);
                this.tieCheck();
                break;
            }
            case 'b': {
                final Height height = this.m_note.getHeight();
                UndoManager.getInstance().addUndoCommand(height.createRestoreCommand(), Localizer.get("N_FLAT"));
                height.setAlteration((byte)2);
                this.tieCheck();
                break;
            }
            case 'n': {
                final Height height = this.m_note.getHeight();
                UndoManager.getInstance().addUndoCommand(height.createRestoreCommand(), Localizer.get("N_NATURAL"));
                height.setAlteration((byte)0);
                this.tieCheck();
                break;
            }
        }
        if (action != null) {
            SwingUtilities.invokeLater(action);
        }
    }
    
    private void tieCheck() {
        try {
            final ListIterator<Element> iterator = this.getTrack().listIterator(this.m_note);
            Element contiguousElement = iterator.next();
            boolean found = false;
            Note first = this.m_note;
            while (!found && iterator.hasPrevious()) {
                contiguousElement = iterator.previous();
                if (contiguousElement instanceof Note) {
                    first = (Note)contiguousElement;
                    if (first.isTied()) {
                        continue;
                    }
                    found = true;
                }
            }
            boolean prevIsRest = false;
            if (iterator.hasNext()) {
                do {
                    contiguousElement = iterator.next();
                    if (contiguousElement instanceof Note) {
                        final Note contiguousNote = (Note)contiguousElement;
                        if (!contiguousNote.isTied() && contiguousNote != first) {
                            break;
                        }
                        if (prevIsRest) {
                            UndoManager.getInstance().appendToLastUndoCommand(contiguousNote.createRestoreCommand());
                            contiguousNote.setTied(false);
                            break;
                        }
                        prevIsRest = contiguousNote.isRest();
                        if (contiguousNote.getHeight().getSoundId() == first.getHeight().getSoundId()) {
                            continue;
                        }
                        UndoManager.getInstance().appendToLastUndoCommand(contiguousNote.createRestoreCommand());
                        contiguousNote.setHeight(new Height(first.getHeight()));
                    }
                } while (iterator.hasNext());
            }
        }
        catch (ObjectNotFoundError e) {
            int index = this.m_locationItem.getElementIndex();
            final Track track = this.getTrack();
            Block_14: {
                while (index >= 0) {
                    if (index >= track.size()) {
                        break Block_14;
                    }
                    if (track.get(index) instanceof Note) {
                        final Note note = (Note)track.get(index);
                        if (note.isTied()) {
                            UndoManager.getInstance().appendToLastUndoCommand(note.createRestoreCommand());
                            note.setTied(false);
                        }
                        index = -2;
                    }
                    ++index;
                }
                return;
            }
        }
    }
    
    private class MoveUpAction extends UserAction
    {
        private static final long serialVersionUID = 1L;
        
        public MoveUpAction() {
            super(null, Localizer.get("TT_MOVE_UP"), ToolIcon.getIcon((byte)5));
        }
        
        @Override
        public void run() {
            final Height height = NoteTool.this.m_note.getHeight();
            UndoManager.getInstance().addUndoCommand(height.createRestoreCommand(), Localizer.get("N_NOTE"));
            height.moveUp();
            NoteTool.this.tieCheck();
        }
    }
    
    private class MoveDownAction extends UserAction
    {
        private static final long serialVersionUID = 1L;
        
        public MoveDownAction() {
            super(null, Localizer.get("TT_MOVE_DOWN"), ToolIcon.getIcon((byte)6));
        }
        
        @Override
        public void run() {
            final Height height = NoteTool.this.m_note.getHeight();
            UndoManager.getInstance().addUndoCommand(height.createRestoreCommand(), Localizer.get("N_NOTE"));
            height.moveDown();
            NoteTool.this.tieCheck();
        }
    }
    
    private class ChangeFigureAction extends UserAction
    {
        private static final long serialVersionUID = 1L;
        private Figure m_newFigure;
        
        public ChangeFigureAction(final Figure figure) {
            super(null, null);
            this.m_newFigure = null;
            this.m_newFigure = figure;
        }
        
        @Override
        public void run() {
            UndoManager.getInstance().addUndoCommand(NoteTool.this.m_note.createRestoreCommand(), Localizer.get("N_FIGURE"));
            NoteTool.this.m_note.setFigure(this.m_newFigure);
        }
    }
    
    private class ToggleRestStateAction extends UserAction
    {
        private static final long serialVersionUID = 1L;
        
        public ToggleRestStateAction() {
            super(null, Localizer.get("TT_REST"), ToolIcon.getIcon((byte)7));
        }
        
        @Override
        public void run() {
            UndoManager.getInstance().addUndoCommand(NoteTool.this.m_note.createRestoreCommand(), Localizer.get("TT_REST"));
            NoteTool.this.m_note.setRest(!NoteTool.this.m_note.isRest());
            NoteTool.this.tieCheck();
        }
    }
    
    private class ToggleDottedStateAction extends UserAction
    {
        private static final long serialVersionUID = 1L;
        
        public ToggleDottedStateAction() {
            super(null, Localizer.get("TT_DOTTED"), ToolIcon.getIcon((byte)8));
        }
        
        @Override
        public void run() {
            final Figure figure = NoteTool.this.m_note.getFigure();
            UndoManager.getInstance().addUndoCommand(figure.createRestoreCommand(), Localizer.get("TT_DOTTED"));
            figure.setDotted(!figure.isDotted());
        }
    }
    
    private class ToggleTiedStateAction extends UserAction
    {
        private static final long serialVersionUID = 1L;
        
        public ToggleTiedStateAction() {
            super(null, Localizer.get("TT_TIED"), ToolIcon.getIcon((byte)19));
        }
        
        @Override
        public void run() {
            UndoManager.getInstance().addUndoCommand(NoteTool.this.m_note.createRestoreCommand(), Localizer.get("TT_TIED"));
            NoteTool.this.m_note.setTied(!NoteTool.this.m_note.isTied());
            NoteTool.this.tieCheck();
        }
    }
    
    private class ChangeTrippletStateAction extends UserAction
    {
        private static final long serialVersionUID = 1L;
        
        public ChangeTrippletStateAction() {
            super(null, Localizer.get("TT_TRIPLET"), ToolIcon.getIcon((byte)20));
        }
        
        @Override
        public void run() {
            final Figure figure = NoteTool.this.m_note.getFigure();
            UndoManager.getInstance().addUndoCommand(figure.createRestoreCommand(), Localizer.get("TT_TRIPLET"));
            figure.setTriplet(NoteTool.this.m_tripletButton.isSelected());
        }
    }
}
