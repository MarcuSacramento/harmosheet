// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop;

import java.awt.event.AdjustmentEvent;
import harmotab.core.ScoreView;
import harmotab.core.ScoreViewListener;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollBar;

public class ScoreScrollBar extends JScrollBar implements AdjustmentListener, ScoreViewListener
{
    private static final long serialVersionUID = 1L;
    public static final int INCREMENT_VALUE = 30;
    private ScoreView m_scoreView;
    
    public ScoreScrollBar(final ScoreView scoreView) {
        this.m_scoreView = scoreView;
        this.setMinimum(0);
        this.setMaximum(0);
        this.setValue(0);
        this.setBlockIncrement(150);
        this.addAdjustmentListener(this);
        this.m_scoreView.addScoreViewListener(this);
    }
    
    @Override
    public void adjustmentValueChanged(final AdjustmentEvent event) {
        final int curOffset = this.m_scoreView.getViewOffset();
        final int newOffset = this.getValue() * 30;
        if (curOffset != newOffset) {
            this.m_scoreView.translateView(newOffset - curOffset);
        }
    }
    
    @Override
    public void onScoreViewChanged(final ScoreView scoreView) {
        this.setMaximum(scoreView.getMaxOffset() / 30);
        this.setValue(scoreView.getViewOffset() / 30);
    }
}
