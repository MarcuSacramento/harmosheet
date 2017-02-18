// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop;

import javax.swing.Action;
import javax.swing.KeyStroke;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.Icon;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import harmotab.core.Localizer;
import harmotab.core.HarmoTabObjectEvent;
import harmotab.sound.ScorePlayerEvent;
import harmotab.sound.ScorePlayer;
import harmotab.core.Score;
import harmotab.renderer.LocationItem;
import harmotab.element.TrackElement;
import harmotab.core.undo.UndoManager;
import harmotab.core.ScoreViewSelection;
import harmotab.core.HarmoTabObjectListener;
import javax.swing.Box;
import java.awt.Component;
import harmotab.desktop.actions.RetabAction;
import harmotab.desktop.actions.ShowScorePropertiesAction;
import harmotab.desktop.actions.InsertLastAction;
import harmotab.desktop.actions.InsertAfterAction;
import harmotab.desktop.actions.InsertBeforeAction;
import harmotab.desktop.actions.DeleteAction;
import harmotab.desktop.actions.RedoAction;
import harmotab.desktop.actions.UserAction;
import harmotab.desktop.actions.UndoAction;
import harmotab.core.ScoreController;
import harmotab.sound.ScorePlayerListener;
import harmotab.core.ScoreControllerListener;
import javax.swing.JToolBar;

public class EditionToolBar extends JToolBar implements ScoreControllerListener, SelectionListener, ScorePlayerListener
{
    private static final long serialVersionUID = 1L;
    private ScoreController m_scoreController;
    private ActionButton m_undoButton;
    private ActionButton m_redoButton;
    private ActionButton m_deleteButton;
    private ActionButton m_insertBeforeButton;
    private ActionButton m_insertAfterButton;
    private ActionButton m_insertEndButton;
    private ActionButton m_scorePropertiesButton;
    private ActionButton m_retabButton;
    
    public EditionToolBar(final ScoreController scoreController) {
        this.m_scoreController = null;
        this.m_undoButton = null;
        this.m_redoButton = null;
        this.m_deleteButton = null;
        this.m_insertBeforeButton = null;
        this.m_insertAfterButton = null;
        this.m_insertEndButton = null;
        this.m_scorePropertiesButton = null;
        this.m_retabButton = null;
        this.m_scoreController = scoreController;
        this.m_undoButton = new ActionButton(new UndoAction(), 90, false);
        this.m_redoButton = new ActionButton(new RedoAction(), 89, false);
        this.m_deleteButton = new ActionButton(new DeleteAction(), false);
        this.m_insertBeforeButton = new ActionButton(new InsertBeforeAction(), false);
        this.m_insertAfterButton = new ActionButton(new InsertAfterAction(), false);
        this.m_insertEndButton = new ActionButton(new InsertLastAction(), false);
        this.m_scorePropertiesButton = new ActionButton(new ShowScorePropertiesAction(), 115, false);
        this.m_retabButton = new ActionButton(new RetabAction(), 82, false);
        this.add(this.m_undoButton);
        this.add(this.m_redoButton);
        this.addSeparator();
        this.add(this.m_insertBeforeButton);
        this.add(this.m_insertAfterButton);
        this.add(this.m_insertEndButton);
        this.add(this.m_deleteButton);
        this.addSeparator();
        this.add(Box.createHorizontalGlue());
        this.addSeparator();
        this.add(this.m_scorePropertiesButton);
        this.add(this.m_retabButton);
        this.addSeparator();
        this.m_scoreController.addScoreControllerListener(this);
        if (this.m_scoreController.hasScore()) {
            this.m_scoreController.getScore().addObjectListener(new ScoreChangesObserver());
        }
        DesktopController.getInstance().addSelectionListener(this);
    }
    
    @Override
    public void onSelectionChanged(final ScoreViewSelection selection) {
        this.m_undoButton.setEnabled(UndoManager.getInstance().hasUndoCommands());
        this.m_redoButton.setEnabled(UndoManager.getInstance().hasRedoCommands());
        if (selection == null) {
            this.m_deleteButton.setEnabled(false);
            this.m_insertBeforeButton.setEnabled(false);
            this.m_insertAfterButton.setEnabled(false);
            this.m_insertEndButton.setEnabled(false);
        }
        else {
            final LocationItem selected = selection.getLocationItem();
            final boolean editionEnabled = this.m_scoreController.isScoreEditable() && !selected.getFlag(4) && selected.getElement() instanceof TrackElement;
            this.m_deleteButton.setEnabled(editionEnabled);
            this.m_insertBeforeButton.setEnabled(editionEnabled);
            this.m_insertAfterButton.setEnabled(editionEnabled);
            this.m_insertEndButton.setEnabled(editionEnabled);
        }
    }
    
    @Override
    public void onControlledScoreChanged(final ScoreController controller, final Score scoreControlled) {
        if (scoreControlled != null) {
            scoreControlled.addObjectListener(new ScoreChangesObserver());
            this.m_scorePropertiesButton.setEnabled(true);
            this.m_retabButton.setEnabled(true);
        }
        else {
            this.m_scorePropertiesButton.setEnabled(false);
            this.m_retabButton.setEnabled(false);
        }
    }
    
    @Override
    public void onScorePlayerChanged(final ScoreController controller, final ScorePlayer soundPlayer) {
        soundPlayer.addSoundPlayerListener(this);
    }
    
    @Override
    public void onPlaybackStarted(final ScorePlayerEvent event) {
        this.m_undoButton.setEnabled(false);
        this.m_redoButton.setEnabled(false);
        this.m_deleteButton.setEnabled(false);
        this.m_insertBeforeButton.setEnabled(false);
        this.m_insertAfterButton.setEnabled(false);
        this.m_insertEndButton.setEnabled(false);
        this.m_scorePropertiesButton.setEnabled(false);
        this.m_retabButton.setEnabled(false);
    }
    
    @Override
    public void onPlaybackStopped(final ScorePlayerEvent event, final boolean endOfPlayback) {
        this.m_scorePropertiesButton.setEnabled(true);
        this.m_retabButton.setEnabled(true);
        this.m_undoButton.setEnabled(UndoManager.getInstance().hasUndoCommands());
        this.m_redoButton.setEnabled(UndoManager.getInstance().hasRedoCommands());
    }
    
    @Override
    public void onPlaybackPaused(final ScorePlayerEvent event) {
        this.m_scorePropertiesButton.setEnabled(true);
    }
    
    @Override
    public void onScorePlayerStateChanged(final ScorePlayerEvent event) {
    }
    
    @Override
    public void onScorePlayerError(final ScorePlayerEvent event, final Throwable error) {
    }
    
    @Override
    public void onPlayedSoundItemChanged(final ScorePlayerEvent event) {
    }
    
    private class ScoreChangesObserver implements HarmoTabObjectListener
    {
        private UndoManager m_undoManager;
        
        public ScoreChangesObserver() {
            this.m_undoManager = null;
            this.m_undoManager = UndoManager.getInstance();
        }
        
        @Override
        public void onObjectChanged(final HarmoTabObjectEvent event) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    final boolean hasUndoCommands = ScoreChangesObserver.this.m_undoManager.hasUndoCommands();
                    EditionToolBar.this.m_undoButton.setEnabled(hasUndoCommands);
                    if (hasUndoCommands) {
                        EditionToolBar.this.m_undoButton.setToolTipText(String.valueOf(Localizer.get("ET_MODIFICATION")) + " " + ScoreChangesObserver.this.m_undoManager.getTopUndoLabel());
                    }
                    else {
                        EditionToolBar.this.m_undoButton.setToolTipText("");
                    }
                    final boolean hasRedoCommand = ScoreChangesObserver.this.m_undoManager.hasRedoCommands();
                    EditionToolBar.this.m_redoButton.setEnabled(hasRedoCommand);
                    if (hasRedoCommand) {
                        EditionToolBar.this.m_redoButton.setToolTipText(String.valueOf(Localizer.get("ET_MODIFICATION")) + " " + ScoreChangesObserver.this.m_undoManager.getTopRedoLabel());
                    }
                    else {
                        EditionToolBar.this.m_redoButton.setToolTipText("");
                    }
                }
            });
        }
    }
    
    private class ActionButton extends JButton
    {
        private static final long serialVersionUID = 1L;
        private final Dimension BUTTON_DIMENSION;
        private UserAction m_action;
        
        public ActionButton(final UserAction action) {
            super(action.getIcon());
            this.BUTTON_DIMENSION = new Dimension(40, 32);
            this.m_action = null;
            this.m_action = action;
            this.setSize(this.BUTTON_DIMENSION);
            this.setMinimumSize(this.BUTTON_DIMENSION);
            this.setMaximumSize(this.BUTTON_DIMENSION);
            this.setPreferredSize(this.BUTTON_DIMENSION);
            this.setFocusable(false);
            this.setBorder(new EmptyBorder(5, 0, 5, 5));
            this.setToolTipText(action.getLabel());
            this.addActionListener(action);
        }
        
        public ActionButton(final EditionToolBar editionToolBar, final UserAction action, final int accelerator) {
            this(editionToolBar, action);
            this.setAccelerator(accelerator);
        }
        
        public ActionButton(final EditionToolBar editionToolBar, final UserAction action, final int accelerator, final boolean enabled) {
            this(editionToolBar, action, accelerator);
            this.setEnabled(enabled);
        }
        
        public ActionButton(final EditionToolBar editionToolBar, final UserAction action, final boolean enabled) {
            this(editionToolBar, action);
            this.setEnabled(enabled);
        }
        
        public void setAccelerator(final int accelerator) {
            final KeyStroke keyBtn = KeyStroke.getKeyStroke(accelerator, 128);
            this.getInputMap(2).put(keyBtn, "ctrl" + accelerator);
            this.getActionMap().put("ctrl" + accelerator, this.m_action);
        }
    }
}
