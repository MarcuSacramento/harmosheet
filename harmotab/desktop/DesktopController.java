// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop;

import harmotab.desktop.browser.BrowsersPane;
import harmotab.core.ScoreView;
import harmotab.core.ScoreController;
import harmotab.core.ScoreViewSelection;
import javax.swing.event.EventListenerList;

public class DesktopController
{
    private static DesktopController m_instance;
    private EventListenerList m_listeners;
    private ScoreViewSelection m_scoreViewSelection;
    private ScoreController m_scoreController;
    private ScoreView m_scoreView;
    private BrowsersPane m_browsersPane;
    private Gui m_guiWindow;
    
    static {
        DesktopController.m_instance = null;
    }
    
    private DesktopController() {
        this.m_listeners = null;
        this.m_scoreViewSelection = null;
        this.m_scoreController = null;
        this.m_scoreView = null;
        this.m_browsersPane = null;
        this.m_guiWindow = null;
        this.m_listeners = new EventListenerList();
    }
    
    public static synchronized DesktopController getInstance() {
        if (DesktopController.m_instance == null) {
            DesktopController.m_instance = new DesktopController();
        }
        return DesktopController.m_instance;
    }
    
    public ScoreViewSelection getCurrentSelection() {
        return this.m_scoreViewSelection;
    }
    
    public void setGuiWindow(final Gui gui) {
        this.m_guiWindow = gui;
    }
    
    public Gui getGuiWindow() {
        return this.m_guiWindow;
    }
    
    public ScoreController getScoreController() {
        return this.m_scoreController;
    }
    
    public void setScoreController(final ScoreController scoreController) {
        this.m_scoreController = scoreController;
    }
    
    public ScoreView getScoreView() {
        return this.m_scoreView;
    }
    
    public void setScoreView(final ScoreView scoreView) {
        this.m_scoreView = scoreView;
    }
    
    public void setBrowsersPane(final BrowsersPane pane) {
        this.m_browsersPane = pane;
    }
    
    public BrowsersPane getBrowsersPane() {
        return this.m_browsersPane;
    }
    
    public void addSelectionListener(final SelectionListener listener) {
        this.m_listeners.add(SelectionListener.class, listener);
    }
    
    public void removeSelectionListener(final SelectionListener listener) {
        this.m_listeners.remove(SelectionListener.class, listener);
    }
    
    public void fireSelectionChanged(final ScoreViewSelection selection) {
        this.m_scoreViewSelection = selection;
        SelectionListener[] array;
        for (int length = (array = this.m_listeners.getListeners(SelectionListener.class)).length, i = 0; i < length; ++i) {
            final SelectionListener listener = array[i];
            listener.onSelectionChanged(selection);
        }
    }
}
