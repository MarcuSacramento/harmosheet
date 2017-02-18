// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.tools;

import harmotab.core.ScoreController;
import harmotab.element.HarmoTabElement;
import java.awt.Window;
import harmotab.desktop.setupdialog.ScoreSetupDialog;
import harmotab.desktop.DesktopController;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeEvent;
import harmotab.throwables.OutOfBoundsError;
import harmotab.core.undo.UndoManager;
import java.awt.event.KeyEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.awt.Component;
import harmotab.core.Localizer;
import harmotab.harmonica.HarmonicaType;
import harmotab.track.HarmoTabTrack;
import harmotab.renderer.LocationItem;
import harmotab.core.Score;
import java.awt.Container;
import harmotab.desktop.components.EffectChooser;
import harmotab.desktop.components.TabChooser;
import javax.swing.JButton;
import harmotab.element.Tab;

public class TabTool extends Tool
{
    private static final long serialVersionUID = 1L;
    private Tab m_tab;
    private JButton m_modelSetupButton;
    private TabChooser m_tabChooser;
    private EffectChooser m_effectChooser;
    
    public TabTool(final Container container, final Score score, final LocationItem item) {
        super(container, score, item);
        this.m_tab = null;
        this.m_modelSetupButton = null;
        this.m_tabChooser = null;
        this.m_effectChooser = null;
        this.m_tab = (Tab)item.getElement();
        final boolean showPushChooser = ((HarmoTabTrack)this.getTrack()).getHarmonica().getModel().getHarmonicaType() == HarmonicaType.CHROMATIC;
        this.m_tabChooser = new TabChooser(this.m_tab, showPushChooser);
        this.m_effectChooser = new EffectChooser(this.m_tab.getEffect());
        this.add(this.m_modelSetupButton = new ToolButton(Localizer.get("N_TAB_NOTE_MAPPING"), (byte)16));
        this.addSeparator();
        this.add(this.m_tabChooser);
        this.addSeparator();
        this.add(this.m_effectChooser);
        final UserActionObserver listener = new UserActionObserver();
        this.m_modelSetupButton.addActionListener(listener);
        this.m_tabChooser.addChangeListener(listener);
        this.m_effectChooser.addActionListener(listener);
    }
    
    @Override
    public void keyTyped(final KeyEvent event) {
        switch (event.getKeyChar()) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9': {
                final String value = new StringBuilder(String.valueOf(event.getKeyChar())).toString();
                final int hole = Integer.parseInt(String.valueOf((this.m_tab != null) ? new StringBuilder(String.valueOf(this.m_tab.getHole())).toString() : "") + value);
                if (this.m_tab == null) {
                    this.m_tab = new Tab();
                }
                try {
                    UndoManager.getInstance().addUndoCommand(this.m_tab.createRestoreCommand(), Localizer.get("N_HOLE"));
                    this.m_tab.setHole(hole);
                }
                catch (OutOfBoundsError e) {
                    this.m_tab.setHole(0);
                }
                break;
            }
            case ' ': {
                if (this.m_tab == null) {
                    this.m_tab = new Tab((byte)1);
                    break;
                }
                UndoManager.getInstance().addUndoCommand(this.m_tab.createRestoreCommand(), Localizer.get("N_BLOW"));
                this.m_tab.toggleDirection();
                break;
            }
            case '/': {
                if (this.m_tab != null) {
                    UndoManager.getInstance().addUndoCommand(this.m_tab.createRestoreCommand(), Localizer.get("N_PUSHED"));
                    this.m_tab.setPushed(!this.m_tab.isPushed());
                    break;
                }
                break;
            }
        }
    }
    
    class UserActionObserver implements ChangeListener, ActionListener
    {
        @Override
        public void stateChanged(final ChangeEvent event) {
            if (event.getSource() == TabTool.this.m_tabChooser) {
                UndoManager.getInstance().addUndoCommand(TabTool.this.m_tab.createRestoreCommand(), Localizer.get("N_TAB"));
                TabTool.this.m_tab.set(TabTool.this.m_tabChooser.getTab());
            }
        }
        
        @Override
        public void actionPerformed(final ActionEvent event) {
            if (event.getSource() == TabTool.this.m_modelSetupButton) {
                final ScoreController controller = DesktopController.getInstance().getScoreController();
                UndoManager.getInstance().addUndoCommand(controller.getScore().createRestoreCommand(), Localizer.get("ET_SCORE_SETUP"));
                final ScoreSetupDialog dlg = new ScoreSetupDialog(null, controller);
                dlg.setSelectedTabMappingHeight(((HarmoTabElement)TabTool.this.m_locationItem.getParent()).getHeight());
                dlg.setSelectedTab(ScoreSetupDialog.HARMONICA_PROPERTIES_TAB);
                dlg.setVisible(true);
            }
            else if (event.getSource() == TabTool.this.m_effectChooser) {
                UndoManager.getInstance().addUndoCommand(TabTool.this.m_tab.createRestoreCommand(), Localizer.get("N_EFFECT"));
                TabTool.this.m_tab.setEffect(TabTool.this.m_effectChooser.getEffect());
            }
        }
    }
}
