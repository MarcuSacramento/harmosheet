// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop;

import javax.swing.event.ChangeEvent;
import harmotab.io.score.ScoreWriter;
import harmotab.core.HarmoTabObjectEvent;
import harmotab.desktop.actions.UserAction;
import harmotab.desktop.actions.OpenModelAction;
import harmotab.io.harmonica.HarmonicaModelReader;
import java.awt.event.ActionEvent;
import harmotab.desktop.actions.OpenScoreAction;
import java.io.File;
import harmotab.io.score.ScoreIOUtilities;
import java.io.IOException;
import harmotab.throwables.ScoreIoException;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import harmotab.sound.ScorePlayer;
import harmotab.core.HarmoTabObjectListener;
import harmotab.core.Score;
import java.awt.Container;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import java.awt.Component;
import harmotab.core.GlobalPreferences;
import harmotab.core.Localizer;
import harmotab.desktop.browser.BrowsersPane;
import javax.swing.JSplitPane;
import harmotab.core.ScoreController;
import harmotab.core.ScoreControllerListener;
import java.awt.event.ComponentListener;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

public class Gui extends JFrame implements WindowListener, ComponentListener, ScoreControllerListener
{
    private static final long serialVersionUID = 1L;
    private ScoreController m_scoreController;
    private JSplitPane m_splitPane;
    private MainPane m_mainPane;
    private BrowsersPane m_browsersPane;
    private boolean m_isBrowsersPaneVisible;
    
    Gui() {
        this.m_scoreController = null;
        this.m_splitPane = null;
        this.m_mainPane = null;
        this.m_browsersPane = null;
        this.m_isBrowsersPaneVisible = true;
        DesktopController.getInstance().setGuiWindow(this);
        this.setDefaultCloseOperation(0);
        this.setTitle(String.valueOf(Localizer.get("MAIN_FRAME_TITLE")) + " - HarmoTab");
        this.setIconImage(GuiIcon.getIcon((byte)11).getImage());
        this.setSize(GlobalPreferences.getWindowWidth(), GlobalPreferences.getWindowHeight());
        this.setExtendedState(GlobalPreferences.getWindowMaximized() ? 6 : 0);
        this.m_scoreController = new ScoreController();
        DesktopController.getInstance().setScoreController(this.m_scoreController);
        this.m_mainPane = new MainPane(this.m_scoreController);
        this.m_browsersPane = new BrowsersPane(this.m_scoreController);
        DesktopController.getInstance().setBrowsersPane(this.m_browsersPane);
        (this.m_splitPane = new JSplitPane(1, this.m_browsersPane, this.m_mainPane)).setOneTouchExpandable(true);
        this.m_splitPane.setDividerLocation(240);
        this.setJMenuBar(new HarmoTabMenu());
        final Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(this.m_splitPane, "Center");
        this.addWindowListener(this);
        GlobalPreferences.addChangeListener(new PreferencesObserver());
        this.addComponentListener(this);
        this.setNavigationPanelVisible(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100L);
                }
                catch (InterruptedException ex) {}
                Gui.this.m_splitPane.setDividerLocation(240);
            }
        });
    }
    
    public boolean isBrowsersPaneVisible() {
        return this.m_isBrowsersPaneVisible;
    }
    
    @Override
    public void onControlledScoreChanged(final ScoreController controller, final Score scoreControlled) {
        if (scoreControlled != null) {
            scoreControlled.addObjectListener(new FrameTitleSetter());
        }
    }
    
    @Override
    public void onScorePlayerChanged(final ScoreController controller, final ScorePlayer soundPlayer) {
    }
    
    public void setNavigationPanelVisible(final boolean visible) {
        this.m_isBrowsersPaneVisible = visible;
        if (visible) {
            this.m_splitPane.setLeftComponent(this.m_browsersPane);
            if (this.m_splitPane.getDividerSize() < 7) {
                this.m_splitPane.setDividerSize(7);
            }
        }
        else {
            this.m_splitPane.setLeftComponent(null);
            this.m_splitPane.setDividerSize(0);
        }
    }
    
    @Override
    public void windowClosing(final WindowEvent event) {
        if (this.m_scoreController.close()) {
            System.exit(0);
        }
    }
    
    @Override
    public void windowActivated(final WindowEvent event) {
    }
    
    @Override
    public void windowClosed(final WindowEvent event) {
    }
    
    @Override
    public void windowDeactivated(final WindowEvent event) {
    }
    
    @Override
    public void windowDeiconified(final WindowEvent event) {
    }
    
    @Override
    public void windowIconified(final WindowEvent event) {
    }
    
    @Override
    public void windowOpened(final WindowEvent event) {
    }
    
    @Override
    public void componentResized(final ComponentEvent event) {
        if (this.getExtendedState() == 6) {
            GlobalPreferences.setWindowMaximized(true);
        }
        else {
            GlobalPreferences.setWindowSize(this.getWidth(), this.getHeight());
            GlobalPreferences.setWindowMaximized(false);
        }
    }
    
    @Override
    public void componentHidden(final ComponentEvent event) {
    }
    
    @Override
    public void componentMoved(final ComponentEvent event) {
    }
    
    @Override
    public void componentShown(final ComponentEvent event) {
    }
    
    private static void setLookAndFeel() {
        try {
            if (GlobalPreferences.useSystemAppearance()) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            else {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }
        }
        catch (UnsupportedLookAndFeelException ex) {}
        catch (ClassNotFoundException ex2) {}
        catch (InstantiationException ex3) {}
        catch (IllegalAccessException ex4) {}
    }
    
    public static void main(final String[] args) {
        boolean displayUsage = false;
        if (args.length == 0) {
            displayGUI(null);
        }
        else if (args.length > 1 && args[0].startsWith("--")) {
            ErrorMessenger.setConsoleMode();
            final String command = args[0];
            final ScoreController scoreController = new ScoreController();
            if (command.equals("--ht3-output")) {
                try {
                    scoreController.open(args[2]);
                }
                catch (ScoreIoException e) {
                    e.printStackTrace();
                }
                if (!scoreController.saveScoreAs(args[1])) {
                    ErrorMessenger.showErrorMessage("Cannot create output file.");
                }
            }
            else if (command.equals("--ht3x-output")) {
                try {
                    scoreController.open(args[2]);
                    scoreController.exportAsExportedScore(args[1]);
                }
                catch (IOException e2) {
                    ErrorMessenger.showErrorMessage("Cannot create output file.");
                }
            }
            else if (command.equals("--score-properties")) {
                try {
                    scoreController.open(args[1]);
                    final Score score = scoreController.getScore();
                    System.out.println(String.valueOf(score.getSongwriterString()) + "\n" + score.getTitleString() + "\n" + score.getTempo().getValue() + "\n" + score.getCommentString() + "\n" + score.getDescription() + "\n");
                }
                catch (IOException e2) {
                    ErrorMessenger.showErrorMessage("Cannot create output file.");
                }
            }
            else {
                displayUsage = true;
            }
        }
        else {
            displayGUI(args[0]);
        }
        if (displayUsage) {
            System.err.println("Usage :\n   ./HarmoTab [<score-file>|<model-file>]\n   ./HarmoTab --ht3-output <output-file> <input-file>\n   ./HarmoTab --ht3x-output <output-file> <input-file>\n   ./HarmoTab --score-properties <input-file>\n");
        }
    }
    
    private static void displayGUI(String path) {
        setLookAndFeel();
        final Gui gui = new Gui();
        if (path != null) {
            path = path.trim();
            if (!path.equals("")) {
                if (new ScoreIOUtilities.ReadableScoreFileFilter().accept(new File(path))) {
                    final UserAction action = new OpenScoreAction(path);
                    action.actionPerformed(new ActionEvent(gui, 0, ""));
                }
                else if (new HarmonicaModelReader.ReadableModelFileFilter().accept(new File(path))) {
                    final UserAction action = new OpenModelAction(path);
                    action.actionPerformed(new ActionEvent(gui, 0, ""));
                }
                else {
                    ErrorMessenger.showErrorMessage(Localizer.get("M_UNKNOWN_FILE_TYPE_ERROR"));
                }
            }
        }
    }
    
    private class FrameTitleSetter implements HarmoTabObjectListener
    {
        @Override
        public void onObjectChanged(final HarmoTabObjectEvent event) {
            final String propertyChanged = event.getProperty();
            if (propertyChanged.equals("newScore") || propertyChanged.equals("scoreOpenned") || propertyChanged.equals("scoreSaved")) {
                final ScoreWriter writer = Gui.this.m_scoreController.getCurrentScoreWriter();
                if (writer != null) {
                    Gui.this.setTitle(String.valueOf(writer.getFile().getName()) + " - HarmoTab");
                }
                else {
                    Gui.this.setTitle(String.valueOf(Localizer.get("MAIN_FRAME_TITLE")) + " - HarmoTab");
                }
            }
        }
    }
    
    private class PreferencesObserver implements ChangeListener
    {
        @Override
        public void stateChanged(final ChangeEvent e) {
            setLookAndFeel();
        }
    }
}
