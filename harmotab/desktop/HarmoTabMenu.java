// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop;

import java.util.Iterator;
import java.io.File;
import harmotab.renderer.LocationItem;
import harmotab.element.TrackElement;
import harmotab.core.ScoreViewSelection;
import harmotab.desktop.actions.TogglePanelAction;
import java.awt.event.ActionEvent;
import harmotab.core.Score;
import harmotab.sound.ScorePlayerEvent;
import harmotab.sound.ScorePlayer;
import harmotab.sound.ScorePlayerListener;
import javax.swing.SwingUtilities;
import harmotab.core.HarmoTabObjectEvent;
import harmotab.core.undo.UndoManager;
import harmotab.core.HarmoTabObjectListener;
import harmotab.core.ScoreControllerListener;
import harmotab.core.GlobalPreferences;
import harmotab.desktop.actions.ShowAboutAction;
import harmotab.desktop.actions.ShowHelpContentAction;
import harmotab.desktop.actions.ShowPreferencesAction;
import harmotab.desktop.actions.RetabAction;
import harmotab.desktop.actions.ShowModelEditorAction;
import harmotab.desktop.actions.ShowScorePropertiesAction;
import java.awt.event.ActionListener;
import harmotab.desktop.actions.StopAction;
import harmotab.desktop.actions.PauseAction;
import harmotab.desktop.actions.PlayFromAction;
import harmotab.desktop.actions.PlayAction;
import harmotab.desktop.actions.DeleteAction;
import harmotab.element.Element;
import harmotab.track.Track;
import harmotab.desktop.actions.RedoAction;
import harmotab.desktop.actions.UndoAction;
import harmotab.desktop.actions.QuitAction;
import harmotab.desktop.actions.CloseScoreAction;
import harmotab.desktop.actions.PrintScoreAction;
import harmotab.desktop.actions.ExportAsMidiAction;
import harmotab.desktop.actions.ExportAsHt3xAction;
import harmotab.desktop.actions.ExportAsImageAction;
import javax.swing.Icon;
import harmotab.desktop.actions.SaveScoreAsAction;
import harmotab.desktop.actions.SaveAction;
import harmotab.desktop.actions.OpenScoreAction;
import harmotab.desktop.actions.UserAction;
import harmotab.desktop.actions.ActionMenuItem;
import harmotab.desktop.actions.NewScoreAction;
import harmotab.core.Localizer;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import harmotab.core.ScoreController;
import javax.swing.JMenuBar;

public class HarmoTabMenu extends JMenuBar
{
    private static final long serialVersionUID = 1L;
    private ScoreController m_scoreController;
    private JMenu m_fileMenu;
    private JMenuItem m_closeMenu;
    private JMenuItem m_newMenu;
    private JMenuItem m_openMenu;
    private JMenuItem m_saveMenu;
    private JMenuItem m_saveAsMenu;
    private JMenu m_exportMenu;
    private JMenuItem m_exportImageMenu;
    private JMenuItem m_exportMidiMenu;
    private JMenuItem m_exportHt3xMenu;
    private JMenuItem m_printMenu;
    private JMenu m_recentFilesMenu;
    private JMenuItem m_quitMenu;
    private JMenu m_editMenu;
    private JMenuItem m_undoMenu;
    private JMenuItem m_redoMenu;
    private AddElementMenu m_insertBeforeMenu;
    private AddElementMenu m_insertAfterMenu;
    private AddElementMenu m_insertLastMenu;
    private JMenuItem m_deleteMenu;
    private JMenu m_playbackMenu;
    private JMenuItem m_playAllMenu;
    private JMenuItem m_playCurrentMenu;
    private JMenuItem m_pauseMenu;
    private JMenuItem m_stopMenu;
    private JMenu m_viewMenu;
    private JCheckBoxMenuItem m_navigationPanelVisible;
    private JMenuItem m_scorePropertiesMenu;
    private JMenuItem m_modelEditorMenu;
    private JMenuItem m_retabMenu;
    private JMenuItem m_preferencesMenu;
    private JMenu m_helpMenu;
    private JMenuItem m_helpContentMenu;
    private JMenuItem m_aboutMenu;
    
    public HarmoTabMenu() {
        this.m_scoreController = null;
        this.m_fileMenu = null;
        this.m_closeMenu = null;
        this.m_newMenu = null;
        this.m_openMenu = null;
        this.m_saveMenu = null;
        this.m_saveAsMenu = null;
        this.m_exportMenu = null;
        this.m_exportImageMenu = null;
        this.m_exportMidiMenu = null;
        this.m_exportHt3xMenu = null;
        this.m_printMenu = null;
        this.m_recentFilesMenu = null;
        this.m_quitMenu = null;
        this.m_editMenu = null;
        this.m_undoMenu = null;
        this.m_redoMenu = null;
        this.m_insertBeforeMenu = null;
        this.m_insertAfterMenu = null;
        this.m_insertLastMenu = null;
        this.m_deleteMenu = null;
        this.m_playbackMenu = null;
        this.m_playAllMenu = null;
        this.m_playCurrentMenu = null;
        this.m_pauseMenu = null;
        this.m_stopMenu = null;
        this.m_viewMenu = null;
        this.m_navigationPanelVisible = null;
        this.m_scorePropertiesMenu = null;
        this.m_modelEditorMenu = null;
        this.m_retabMenu = null;
        this.m_preferencesMenu = null;
        this.m_helpMenu = null;
        this.m_helpContentMenu = null;
        this.m_aboutMenu = null;
        this.m_scoreController = DesktopController.getInstance().getScoreController();
        this.m_fileMenu = new JMenu(Localizer.get("MENU_FILE"));
        this.m_newMenu = new ActionMenuItem(new NewScoreAction());
        this.m_openMenu = new ActionMenuItem(new OpenScoreAction());
        this.m_saveMenu = new ActionMenuItem(new SaveAction());
        this.m_saveAsMenu = new ActionMenuItem(new SaveScoreAsAction());
        (this.m_exportMenu = new JMenu(Localizer.get("MENU_EXPORT"))).setIcon(ActionIcon.getIcon((byte)24));
        this.m_exportImageMenu = new ActionMenuItem(new ExportAsImageAction());
        this.m_exportHt3xMenu = new ActionMenuItem(new ExportAsHt3xAction());
        this.m_exportMidiMenu = new ActionMenuItem(new ExportAsMidiAction());
        this.m_printMenu = new ActionMenuItem(new PrintScoreAction());
        this.m_closeMenu = new ActionMenuItem(new CloseScoreAction());
        this.m_recentFilesMenu = new RecentFilesMenu();
        this.m_quitMenu = new ActionMenuItem(new QuitAction());
        this.m_editMenu = new JMenu(Localizer.get("MENU_EDIT"));
        this.m_undoMenu = new ActionMenuItem(new UndoAction());
        this.m_redoMenu = new ActionMenuItem(new RedoAction());
        this.m_insertBeforeMenu = AddElementMenu.createInsertBefore(null, null);
        this.m_insertAfterMenu = AddElementMenu.createInsertAfter(null, null);
        this.m_insertLastMenu = AddElementMenu.createInsertLast(null);
        this.m_deleteMenu = new ActionMenuItem(new DeleteAction());
        this.m_playbackMenu = new JMenu(Localizer.get("MENU_PLAYBACK"));
        this.m_playAllMenu = new ActionMenuItem(new PlayAction());
        this.m_playCurrentMenu = new ActionMenuItem(new PlayFromAction());
        this.m_pauseMenu = new ActionMenuItem(new PauseAction());
        this.m_stopMenu = new ActionMenuItem(new StopAction());
        this.m_viewMenu = new JMenu(Localizer.get("MENU_VIEW"));
        (this.m_navigationPanelVisible = new JCheckBoxMenuItem(Localizer.get("MENU_SHOW_NAVIGATION_PANEL"), DesktopController.getInstance().getGuiWindow().isBrowsersPaneVisible())).addActionListener(new BrowsersPaneCheckboxToggledAction((BrowsersPaneCheckboxToggledAction)null));
        this.m_scorePropertiesMenu = new ActionMenuItem(new ShowScorePropertiesAction());
        this.m_modelEditorMenu = new ActionMenuItem(new ShowModelEditorAction());
        this.m_retabMenu = new ActionMenuItem(new RetabAction());
        this.m_preferencesMenu = new ActionMenuItem(new ShowPreferencesAction());
        this.m_helpMenu = new JMenu(Localizer.get("MENU_HELP"));
        this.m_helpContentMenu = new ActionMenuItem(new ShowHelpContentAction());
        this.m_aboutMenu = new ActionMenuItem(new ShowAboutAction());
        this.m_saveMenu.setEnabled(false);
        this.m_saveAsMenu.setEnabled(false);
        this.m_exportMenu.setEnabled(false);
        this.m_exportMenu.setEnabled(false);
        this.m_printMenu.setEnabled(false);
        this.m_closeMenu.setEnabled(false);
        this.m_undoMenu.setEnabled(false);
        this.m_redoMenu.setEnabled(false);
        this.m_insertBeforeMenu.setEnabled(false);
        this.m_insertAfterMenu.setEnabled(false);
        this.m_insertLastMenu.setEnabled(false);
        this.m_deleteMenu.setEnabled(false);
        this.m_playAllMenu.setEnabled(false);
        this.m_playCurrentMenu.setEnabled(false);
        this.m_pauseMenu.setEnabled(false);
        this.m_stopMenu.setEnabled(false);
        this.m_scorePropertiesMenu.setEnabled(false);
        this.m_retabMenu.setEnabled(false);
        this.m_fileMenu.add(this.m_newMenu);
        this.m_fileMenu.add(this.m_openMenu);
        this.m_fileMenu.addSeparator();
        this.m_fileMenu.add(this.m_closeMenu);
        this.m_fileMenu.addSeparator();
        this.m_fileMenu.add(this.m_saveMenu);
        this.m_fileMenu.add(this.m_saveAsMenu);
        this.m_fileMenu.addSeparator();
        this.m_fileMenu.add(this.m_exportMenu);
        this.m_exportMenu.add(this.m_exportImageMenu);
        this.m_exportMenu.add(this.m_exportMidiMenu);
        if (GlobalPreferences.getPerformancesFeatureEnabled()) {
            this.m_exportMenu.add(this.m_exportHt3xMenu);
        }
        this.m_fileMenu.addSeparator();
        this.m_fileMenu.add(this.m_printMenu);
        this.m_fileMenu.addSeparator();
        this.m_fileMenu.add(this.m_recentFilesMenu);
        this.m_fileMenu.addSeparator();
        this.m_fileMenu.add(this.m_quitMenu);
        this.add(this.m_fileMenu);
        this.m_editMenu.add(this.m_undoMenu);
        this.m_editMenu.add(this.m_redoMenu);
        this.m_editMenu.addSeparator();
        this.m_editMenu.add(this.m_insertBeforeMenu);
        this.m_editMenu.add(this.m_insertAfterMenu);
        this.m_editMenu.add(this.m_insertLastMenu);
        this.m_editMenu.add(this.m_deleteMenu);
        this.add(this.m_editMenu);
        this.m_playbackMenu.add(this.m_playAllMenu);
        this.m_playbackMenu.add(this.m_playCurrentMenu);
        this.m_playbackMenu.add(this.m_pauseMenu);
        this.m_playbackMenu.add(this.m_stopMenu);
        this.add(this.m_playbackMenu);
        this.m_viewMenu.add(this.m_navigationPanelVisible);
        this.m_viewMenu.addSeparator();
        this.m_viewMenu.add(this.m_scorePropertiesMenu);
        this.m_viewMenu.add(this.m_preferencesMenu);
        this.m_viewMenu.addSeparator();
        this.m_viewMenu.add(this.m_modelEditorMenu);
        this.m_viewMenu.add(this.m_retabMenu);
        this.add(this.m_viewMenu);
        this.m_helpMenu.add(this.m_helpContentMenu);
        this.m_helpMenu.addSeparator();
        this.m_helpMenu.add(this.m_aboutMenu);
        this.add(this.m_helpMenu);
        this.m_scoreController.addScoreControllerListener(new ScoreControllerObserver((ScoreControllerObserver)null));
        DesktopController.getInstance().addSelectionListener(new SelectionObserver((SelectionObserver)null));
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
                    HarmoTabMenu.this.m_saveMenu.setEnabled(DesktopController.getInstance().getScoreController().hasScoreChanged());
                }
            });
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    final boolean hasUndoCommands = ScoreChangesObserver.this.m_undoManager.hasUndoCommands();
                    HarmoTabMenu.this.m_undoMenu.setEnabled(hasUndoCommands);
                    if (hasUndoCommands) {
                        HarmoTabMenu.this.m_undoMenu.setToolTipText(String.valueOf(Localizer.get("ET_MODIFICATION")) + " " + ScoreChangesObserver.this.m_undoManager.getTopUndoLabel());
                    }
                    else {
                        HarmoTabMenu.this.m_undoMenu.setToolTipText("");
                    }
                    final boolean hasRedoCommand = ScoreChangesObserver.this.m_undoManager.hasRedoCommands();
                    HarmoTabMenu.this.m_redoMenu.setEnabled(hasRedoCommand);
                    if (hasRedoCommand) {
                        HarmoTabMenu.this.m_redoMenu.setToolTipText(String.valueOf(Localizer.get("ET_MODIFICATION")) + " " + ScoreChangesObserver.this.m_undoManager.getTopRedoLabel());
                    }
                    else {
                        HarmoTabMenu.this.m_redoMenu.setToolTipText("");
                    }
                }
            });
        }
    }
    
    private class SoundPlayerObserver implements ScorePlayerListener
    {
        public SoundPlayerObserver() {
            this.updateEnabledMenu();
        }
        
        private void updateEnabledMenu() {
            final ScorePlayer player = DesktopController.getInstance().getScoreController().getScorePlayer();
            final boolean openned = player.getState() == 2;
            final boolean playing = player.isPlaying();
            final boolean paused = player.isPaused();
            HarmoTabMenu.this.m_playAllMenu.setEnabled(openned && !playing && !paused);
            HarmoTabMenu.this.m_playCurrentMenu.setEnabled(openned && !playing && !paused);
            HarmoTabMenu.this.m_pauseMenu.setEnabled(openned && (playing || paused));
            HarmoTabMenu.this.m_stopMenu.setEnabled(openned && (playing || paused));
        }
        
        @Override
        public void onScorePlayerStateChanged(final ScorePlayerEvent event) {
            this.updateEnabledMenu();
        }
        
        @Override
        public void onPlaybackPaused(final ScorePlayerEvent event) {
            this.updateEnabledMenu();
        }
        
        @Override
        public void onPlaybackStarted(final ScorePlayerEvent event) {
            this.updateEnabledMenu();
            HarmoTabMenu.this.m_undoMenu.setEnabled(false);
            HarmoTabMenu.this.m_redoMenu.setEnabled(false);
            HarmoTabMenu.this.m_deleteMenu.setEnabled(false);
            HarmoTabMenu.this.m_insertBeforeMenu.setEnabled(false);
            HarmoTabMenu.this.m_insertAfterMenu.setEnabled(false);
            HarmoTabMenu.this.m_insertLastMenu.setEnabled(false);
            HarmoTabMenu.this.m_scorePropertiesMenu.setEnabled(false);
            HarmoTabMenu.this.m_retabMenu.setEnabled(false);
        }
        
        @Override
        public void onPlaybackStopped(final ScorePlayerEvent event, final boolean endOfPlayback) {
            HarmoTabMenu.this.m_scorePropertiesMenu.setEnabled(true);
            HarmoTabMenu.this.m_retabMenu.setEnabled(true);
            HarmoTabMenu.this.m_undoMenu.setEnabled(UndoManager.getInstance().hasUndoCommands());
            HarmoTabMenu.this.m_redoMenu.setEnabled(UndoManager.getInstance().hasRedoCommands());
            this.updateEnabledMenu();
        }
        
        @Override
        public void onPlayedSoundItemChanged(final ScorePlayerEvent event) {
        }
        
        @Override
        public void onScorePlayerError(final ScorePlayerEvent event, final Throwable error) {
        }
    }
    
    private class ScoreControllerObserver implements ScoreControllerListener
    {
        @Override
        public void onControlledScoreChanged(final ScoreController controller, final Score scoreControlled) {
            if (scoreControlled != null) {
                final boolean scoreChanged = controller.hasScoreChanged();
                HarmoTabMenu.this.m_closeMenu.setEnabled(true);
                HarmoTabMenu.this.m_saveMenu.setEnabled(scoreChanged);
                HarmoTabMenu.this.m_saveAsMenu.setEnabled(true);
                HarmoTabMenu.this.m_printMenu.setEnabled(true);
                HarmoTabMenu.this.m_exportMenu.setEnabled(true);
                HarmoTabMenu.this.m_playAllMenu.setEnabled(true);
                HarmoTabMenu.this.m_playCurrentMenu.setEnabled(true);
                HarmoTabMenu.this.m_scorePropertiesMenu.setEnabled(true);
                HarmoTabMenu.this.m_retabMenu.setEnabled(true);
                scoreControlled.addObjectListener(new ScoreChangesObserver());
            }
            else {
                HarmoTabMenu.this.m_closeMenu.setEnabled(false);
                HarmoTabMenu.this.m_saveMenu.setEnabled(false);
                HarmoTabMenu.this.m_saveAsMenu.setEnabled(false);
                HarmoTabMenu.this.m_printMenu.setEnabled(false);
                HarmoTabMenu.this.m_exportMenu.setEnabled(false);
                HarmoTabMenu.this.m_playAllMenu.setEnabled(false);
                HarmoTabMenu.this.m_playCurrentMenu.setEnabled(false);
                HarmoTabMenu.this.m_scorePropertiesMenu.setEnabled(false);
                HarmoTabMenu.this.m_retabMenu.setEnabled(false);
            }
        }
        
        @Override
        public void onScorePlayerChanged(final ScoreController controller, final ScorePlayer soundPlayer) {
            if (soundPlayer != null) {
                soundPlayer.addSoundPlayerListener(new SoundPlayerObserver());
            }
        }
    }
    
    private class BrowsersPaneCheckboxToggledAction implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent event) {
            new TogglePanelAction().actionPerformed(event);
            HarmoTabMenu.this.m_navigationPanelVisible.setSelected(DesktopController.getInstance().getGuiWindow().isBrowsersPaneVisible());
        }
    }
    
    private class SelectionObserver implements SelectionListener
    {
        @Override
        public void onSelectionChanged(final ScoreViewSelection selection) {
            if (selection == null) {
                HarmoTabMenu.this.m_deleteMenu.setEnabled(false);
                HarmoTabMenu.this.m_insertBeforeMenu.setEnabled(false);
                HarmoTabMenu.this.m_insertAfterMenu.setEnabled(false);
                HarmoTabMenu.this.m_insertLastMenu.setEnabled(false);
            }
            else {
                final LocationItem selected = selection.getLocationItem();
                final boolean editionEnabled = HarmoTabMenu.this.m_scoreController.isScoreEditable() && !selected.getFlag(4) && selected.getElement() instanceof TrackElement;
                HarmoTabMenu.this.m_deleteMenu.setEnabled(editionEnabled);
                HarmoTabMenu.this.m_insertBeforeMenu.setEnabled(editionEnabled);
                HarmoTabMenu.this.m_insertAfterMenu.setEnabled(editionEnabled);
                HarmoTabMenu.this.m_insertLastMenu.setEnabled(editionEnabled);
                if (editionEnabled) {
                    final Track track = selection.getTrack();
                    final Element ref = selection.getLocationItem().getRootElement();
                    HarmoTabMenu.this.m_insertBeforeMenu.setReference(track, ref);
                    HarmoTabMenu.this.m_insertAfterMenu.setReference(track, ref);
                    HarmoTabMenu.this.m_insertLastMenu.setReference(track, null);
                }
            }
        }
    }
    
    private class RecentFilesMenu extends JMenu
    {
        private static final long serialVersionUID = 1L;
        
        public RecentFilesMenu() {
            super(Localizer.get("MENU_RECENT_FILES"));
        }
        
        @Override
        protected void fireMenuSelected() {
            this.removeAll();
            int i = 0;
            for (final String filepath : RecentFilesManager.getInstance().getRecentFiles()) {
                ++i;
                final File file = new File(filepath);
                final JMenuItem item = new ActionMenuItem(new OpenScoreAction(file.getAbsolutePath()));
                item.setText(String.valueOf(i) + ". " + file.getName());
                item.setToolTipText(file.getAbsolutePath());
                item.setIcon(null);
                this.add(item);
            }
            this.validate();
            super.fireMenuSelected();
        }
    }
}
