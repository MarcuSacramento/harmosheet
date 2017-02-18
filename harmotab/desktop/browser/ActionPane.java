// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.browser;

import java.util.Iterator;
import java.io.File;
import harmotab.desktop.RecentFilesManager;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;
import harmotab.desktop.ActionIcon;
import java.awt.BorderLayout;
import javax.swing.Icon;
import javax.swing.Action;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import harmotab.core.HarmoTabObjectEvent;
import harmotab.sound.ScorePlayer;
import harmotab.core.HarmoTabObjectListener;
import harmotab.core.Score;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import java.awt.LayoutManager;
import rvt.util.gui.VerticalLayout;
import harmotab.desktop.actions.ShowPreferencesAction;
import harmotab.desktop.actions.ShowModelEditorAction;
import harmotab.desktop.actions.PrintScoreAction;
import harmotab.desktop.actions.RecordPerformanceFromHt3Action;
import harmotab.core.Localizer;
import harmotab.desktop.actions.SaveScoreAsAction;
import harmotab.desktop.actions.SaveAction;
import harmotab.desktop.actions.CloseScoreAction;
import harmotab.desktop.actions.NewScoreAction;
import harmotab.desktop.actions.ShowAboutAction;
import harmotab.desktop.actions.ShowHelpContentAction;
import harmotab.desktop.actions.ExportAsHt3xAction;
import harmotab.core.GlobalPreferences;
import harmotab.desktop.actions.ExportAsImageAction;
import harmotab.desktop.actions.ExportAsMidiAction;
import javax.swing.JPopupMenu;
import harmotab.desktop.actions.OpenFolderAction;
import javax.swing.JMenuItem;
import harmotab.desktop.actions.UserAction;
import harmotab.desktop.actions.OpenScoreAction;
import harmotab.core.ScoreController;
import harmotab.core.ScoreControllerListener;
import javax.swing.JToolBar;

public class ActionPane extends JToolBar implements ScoreControllerListener
{
    private static final long serialVersionUID = 1L;
    private ScoreController m_scoreController;
    private ActionButton m_newButton;
    private ActionButton m_openButton;
    private ActionButton m_closeButton;
    private ActionButton m_saveButton;
    private ActionButton m_saveAsButton;
    private ActionButton m_printButton;
    private ActionButton m_exportButton;
    private ActionButton m_recordButton;
    private ActionButton m_modelEditorButton;
    private ActionButton m_preferencesButton;
    private ActionButton m_helpButton;
    
    public ActionPane(final ScoreController controller) {
        super(1);
        this.m_scoreController = null;
        this.m_newButton = null;
        this.m_openButton = null;
        this.m_closeButton = null;
        this.m_saveButton = null;
        this.m_saveAsButton = null;
        this.m_printButton = null;
        this.m_exportButton = null;
        this.m_recordButton = null;
        this.m_modelEditorButton = null;
        this.m_preferencesButton = null;
        this.m_helpButton = null;
        this.m_scoreController = controller;
        final JPopupMenu openSubMenu = new RecentFilesPopulatedPopupMenu();
        openSubMenu.add(new ActionItem(new OpenScoreAction()));
        openSubMenu.add(new ActionItem(new OpenFolderAction()));
        openSubMenu.addSeparator();
        final JPopupMenu exportSubMenu = new JPopupMenu();
        exportSubMenu.add(new ActionItem(new ExportAsMidiAction()));
        exportSubMenu.add(new ActionItem(new ExportAsImageAction()));
        if (GlobalPreferences.getPerformancesFeatureEnabled()) {
            exportSubMenu.add(new ActionItem(new ExportAsHt3xAction()));
        }
        final JPopupMenu helpSubMenu = new JPopupMenu();
        helpSubMenu.add(new ActionItem(new ShowHelpContentAction()));
        helpSubMenu.addSeparator();
        helpSubMenu.add(new ActionItem(new ShowAboutAction()));
        this.m_newButton = new ActionButton(new NewScoreAction(), 78);
        this.m_openButton = new ActionButton(new OpenScoreAction(), openSubMenu, 79);
        this.m_closeButton = new ActionButton(new CloseScoreAction(), 87);
        this.m_saveButton = new ActionButton(new SaveAction(), 83);
        this.m_saveAsButton = new ActionButton(new SaveScoreAsAction());
        (this.m_exportButton = new ActionButton(new ExportAsImageAction(), exportSubMenu)).setText(Localizer.get("MENU_EXPORT"));
        this.m_recordButton = new ActionButton(new RecordPerformanceFromHt3Action());
        this.m_printButton = new ActionButton(new PrintScoreAction(), 80);
        this.m_modelEditorButton = new ActionButton(new ShowModelEditorAction());
        this.m_preferencesButton = new ActionButton(new ShowPreferencesAction());
        this.m_helpButton = new ActionButton(new ShowHelpContentAction(), helpSubMenu, 112);
        this.setLayout(new VerticalLayout(0, 3, 1));
        this.addSeparator();
        this.add(this.m_newButton);
        this.add(this.m_openButton);
        this.add(this.m_closeButton);
        this.add(this.m_saveButton);
        this.add(this.m_saveAsButton);
        this.addSeparator();
        this.add(this.m_printButton);
        if (GlobalPreferences.getPerformancesFeatureEnabled()) {
            this.add(this.m_exportButton);
            this.add(this.m_recordButton);
        }
        this.addSeparator();
        this.add(this.m_modelEditorButton);
        this.add(this.m_preferencesButton);
        this.addSeparator();
        this.add(this.m_helpButton);
        this.addSeparator();
        this.m_scoreController.addScoreControllerListener(this);
        this.onControlledScoreChanged(this.m_scoreController, this.m_scoreController.getScore());
        this.setFloatable(false);
        this.setOpaque(false);
        this.setBorder(new EmptyBorder(5, 0, 10, 0));
    }
    
    @Override
    public void onControlledScoreChanged(final ScoreController controller, final Score scoreControlled) {
        final Score score = this.m_scoreController.getScore();
        if (score != null) {
            final boolean scoreChanged = this.m_scoreController.hasScoreChanged();
            this.m_closeButton.setEnabled(true);
            this.m_saveButton.setEnabled(scoreChanged);
            this.m_saveAsButton.setEnabled(true);
            this.m_printButton.setEnabled(true);
            this.m_exportButton.setEnabled(true);
            this.m_recordButton.setEnabled(this.m_scoreController.getPerformancesList() == null);
            score.addObjectListener(new ScoreChangesObserver());
        }
        else {
            this.m_closeButton.setEnabled(false);
            this.m_saveButton.setEnabled(false);
            this.m_saveAsButton.setEnabled(false);
            this.m_printButton.setEnabled(false);
            this.m_exportButton.setEnabled(false);
            this.m_recordButton.setEnabled(false);
        }
    }
    
    @Override
    public void onScorePlayerChanged(final ScoreController controller, final ScorePlayer soundPlayer) {
    }
    
    private class ScoreChangesObserver implements HarmoTabObjectListener
    {
        @Override
        public void onObjectChanged(final HarmoTabObjectEvent event) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ActionPane.this.m_saveButton.setEnabled(ActionPane.this.m_scoreController.hasScoreChanged());
                }
            });
        }
    }
    
    private class ActionButton extends JButton implements ActionListener
    {
        private static final long serialVersionUID = 1L;
        private JButton m_subMenuButton;
        private JPopupMenu m_subMenu;
        private Action m_action;
        
        public ActionButton(final ActionPane actionPane, final UserAction action) {
            this(actionPane, action, null);
        }
        
        public ActionButton(final ActionPane actionPane, final UserAction action, final int accelerator) {
            this(actionPane, action, null);
            this.setAccelerator(accelerator);
        }
        
        public ActionButton(final ActionPane actionPane, final UserAction action, final JPopupMenu subMenu, final int accelerator) {
            this(actionPane, action, subMenu);
            this.setAccelerator(accelerator);
        }
        
        public ActionButton(final UserAction action, final JPopupMenu subMenu) {
            super(action.getLabel(), action.getIcon());
            this.m_action = action;
            this.m_subMenu = subMenu;
            this.setLayout(new BorderLayout());
            ActionPane.this.setFloatable(false);
            this.setOpaque(false);
            this.setBorder(new EmptyBorder(0, 0, 0, 0));
            this.setFocusable(false);
            this.setOpaque(false);
            this.setBorder(new EmptyBorder(5, 5, 5, 5));
            this.setHorizontalAlignment(10);
            this.addActionListener(action);
            this.m_subMenuButton = null;
            if (subMenu != null) {
                (this.m_subMenuButton = new JButton(ActionIcon.getIcon((byte)27))).setOpaque(false);
                this.m_subMenuButton.setBorder(new EmptyBorder(5, 10, 5, 10));
                this.m_subMenuButton.setHorizontalAlignment(11);
                this.m_subMenuButton.addActionListener(this);
                this.add(this.m_subMenuButton, "East");
            }
        }
        
        public void showSubMenu() {
            if (this.m_subMenu != null) {
                this.m_subMenu.show(this.m_subMenuButton, 0, this.m_subMenuButton.getHeight());
            }
        }
        
        public void setAccelerator(final int accelerator) {
            final KeyStroke keyBtn = KeyStroke.getKeyStroke(accelerator, 128);
            this.getInputMap(2).put(keyBtn, "ctrl" + accelerator);
            this.getActionMap().put("ctrl" + accelerator, this.m_action);
        }
        
        @Override
        public void actionPerformed(final ActionEvent event) {
            this.showSubMenu();
        }
        
        @Override
        public void setEnabled(final boolean enabled) {
            super.setEnabled(enabled);
            if (this.m_subMenuButton != null) {
                this.m_subMenuButton.setEnabled(enabled);
            }
        }
    }
    
    private class ActionItem extends JMenuItem
    {
        private static final long serialVersionUID = 1L;
        
        public ActionItem(final UserAction action) {
            super(action.getLabel(), action.getIcon());
            this.addActionListener(action);
            this.setBorder(new EmptyBorder(5, 0, 5, 5));
        }
    }
    
    private class RecentFilesPopulatedPopupMenu extends JPopupMenu
    {
        private static final long serialVersionUID = 1L;
        private ArrayList<JMenuItem> m_items;
        
        public RecentFilesPopulatedPopupMenu() {
            this.m_items = null;
            this.m_items = new ArrayList<JMenuItem>();
        }
        
        @Override
        public void show(final Component invoker, final int x, final int y) {
            for (final String filepath : RecentFilesManager.getInstance().getRecentFiles()) {
                final File file = new File(filepath);
                final JMenuItem item = new ActionItem(new OpenScoreAction(file.getAbsolutePath()));
                item.setText(String.valueOf(this.m_items.size() + 1) + ". " + file.getName());
                item.setToolTipText(file.getAbsolutePath());
                item.setIcon(null);
                this.m_items.add(item);
            }
            for (final JMenuItem item2 : this.m_items) {
                this.add(item2);
            }
            this.validate();
            super.show(invoker, x, y);
        }
        
        @Override
        protected void firePopupMenuWillBecomeInvisible() {
            for (final JMenuItem item : this.m_items) {
                this.remove(item);
            }
            this.m_items.clear();
            super.firePopupMenuWillBecomeInvisible();
        }
    }
}
