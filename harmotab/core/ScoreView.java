// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import javax.swing.event.ChangeEvent;
import harmotab.sound.ScorePlayer;
import javax.swing.event.ChangeListener;
import harmotab.renderer.ScoreRenderer;
import harmotab.renderer.LocationList;
import javax.swing.event.EventListenerList;

public abstract class ScoreView implements ScoreControllerListener
{
    public static final int MIN_VIEW_HEIGHT = 50;
    public static final int MIN_VIEW_WIDTH = 50;
    private EventListenerList m_listeners;
    protected LocationList m_locations;
    protected ScoreController m_scoreController;
    protected ScoreRenderer m_renderer;
    protected int m_scoreWidth;
    protected int m_scoreHeight;
    protected int m_viewWidth;
    protected int m_viewHeight;
    protected int m_viewOffset;
    
    public ScoreView(final ScoreController controller) {
        this.m_listeners = new EventListenerList();
        this.m_locations = new LocationList();
        this.m_renderer = null;
        this.m_scoreController = controller;
        this.setScore(controller.getScore());
        this.m_scoreWidth = 500;
        this.m_scoreHeight = 0;
        this.m_viewWidth = 500;
        this.m_viewHeight = 300;
        this.m_viewOffset = 0;
        controller.addScoreControllerListener(this);
        GlobalPreferences.addChangeListener(new PreferencesObserver());
    }
    
    private void updateLocations() {
        this.m_locations.reset();
        if (this.m_renderer != null) {
            this.m_renderer.setPageSize(this.m_viewWidth, this.m_viewHeight);
            this.m_renderer.layout(this.m_locations);
            this.m_scoreWidth = this.m_viewWidth;
            this.m_scoreHeight = this.m_locations.getBottomOrdinate();
            this.m_locations.addVerticalScrolling(this.m_viewOffset);
        }
    }
    
    protected abstract void updateScoreView();
    
    public void refresh() {
        this.updateLocations();
        this.updateScoreView();
        this.notifyScoreViewChanged();
    }
    
    protected void setScore(final Score score) {
        if (score != null) {
            score.addObjectListener(new ScoreObserver());
            this.m_renderer = new ScoreRenderer(score);
        }
        else {
            this.m_renderer = null;
        }
    }
    
    public ScoreController getScoreController() {
        return this.m_scoreController;
    }
    
    public int getViewHeight() {
        return this.m_viewHeight;
    }
    
    public int getViewWidth() {
        return this.m_viewWidth;
    }
    
    public void setViewSize(final int width, final int height) {
        this.m_viewHeight = height;
        this.m_viewWidth = width;
        if (this.m_viewHeight < 50) {
            this.m_viewHeight = 50;
        }
        if (this.m_viewWidth < 50) {
            this.m_viewWidth = 50;
        }
    }
    
    public int getViewOffset() {
        return this.m_viewOffset;
    }
    
    public void setViewOffset(final int offset) {
        this.m_viewOffset = offset;
    }
    
    public int getMaxOffset() {
        final int max = this.m_scoreHeight - this.m_viewHeight;
        return (max >= 0) ? max : 0;
    }
    
    public int getScoreWidth() {
        return this.m_scoreWidth;
    }
    
    public int getScoreHeight() {
        return this.m_scoreHeight;
    }
    
    public void setScoreSize(final int width, final int height) {
        this.m_scoreWidth = width;
        this.m_scoreHeight = height;
    }
    
    public ScoreRenderer getScoreRenderer() {
        return this.m_renderer;
    }
    
    public LocationList getLocations() {
        return this.m_locations;
    }
    
    public void translateView(final int dOffset) {
        final int oldOffset = this.getViewOffset();
        int newOffset = oldOffset + dOffset;
        final int maxOffset = this.getMaxOffset();
        if (newOffset < 0) {
            newOffset = 0;
        }
        if (newOffset > maxOffset) {
            newOffset = maxOffset;
        }
        if (oldOffset != newOffset) {
            final int deltaY = newOffset - oldOffset;
            this.m_locations.addVerticalScrolling(deltaY);
            this.setViewOffset(newOffset);
            this.updateScoreView();
            this.notifyScoreViewChanged();
        }
    }
    
    public int getLineOffset(final int line) {
        return this.m_renderer.getLineOffset(line) - this.getViewOffset();
    }
    
    @Override
    public void onControlledScoreChanged(final ScoreController controller, final Score scoreControlled) {
        this.setScore(scoreControlled);
    }
    
    @Override
    public void onScorePlayerChanged(final ScoreController controller, final ScorePlayer soundPlayer) {
    }
    
    public void addScoreViewListener(final ScoreViewListener listener) {
        this.m_listeners.add(ScoreViewListener.class, listener);
    }
    
    protected void notifyScoreViewChanged() {
        ScoreViewListener[] array;
        for (int length = (array = this.m_listeners.getListeners(ScoreViewListener.class)).length, i = 0; i < length; ++i) {
            final ScoreViewListener listener = array[i];
            listener.onScoreViewChanged(this);
        }
    }
    
    private class ScoreObserver implements HarmoTabObjectListener
    {
        @Override
        public void onObjectChanged(final HarmoTabObjectEvent event) {
            ScoreView.this.refresh();
        }
    }
    
    private class PreferencesObserver implements ChangeListener
    {
        @Override
        public void stateChanged(final ChangeEvent e) {
            ScoreView.this.setScore(ScoreView.this.m_scoreController.getScore());
            ScoreView.this.refresh();
        }
    }
}
