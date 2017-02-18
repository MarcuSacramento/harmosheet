// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import harmotab.core.ScoreView;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import harmotab.core.ScoreController;
import harmotab.core.ScoreViewListener;
import javax.swing.JPanel;

public class MainPane extends JPanel implements ScoreViewListener
{
    private static final long serialVersionUID = 1L;
    private JPanel m_corePane;
    private ScorePane m_scorePane;
    private boolean m_hasTopBorder;
    private boolean m_hasBottomBorder;
    private boolean m_hasRightBorder;
    
    public MainPane(final ScoreController controller) {
        this.m_corePane = null;
        this.m_scorePane = null;
        this.m_hasTopBorder = false;
        this.m_hasBottomBorder = false;
        this.m_hasRightBorder = true;
        this.m_scorePane = new ScorePane(controller);
        final ScoreView scoreView = this.m_scorePane.getScoreView();
        final ScoreScrollBar scrollBar = new ScoreScrollBar(scoreView);
        final EditionToolBar scoreEditionToolBar = new EditionToolBar(controller);
        final ScorePlaybackToolBar scorePlaybackToolBar = new ScorePlaybackToolBar(controller, scoreView);
        (this.m_corePane = new JPanel(new BorderLayout())).add(this.m_scorePane, "Center");
        this.m_corePane.setBackground(Color.DARK_GRAY);
        this.setLayout(new BorderLayout());
        this.add(this.m_corePane, "Center");
        this.add(scrollBar, "East");
        this.add(scoreEditionToolBar, "North");
        this.add(scorePlaybackToolBar, "South");
        scoreView.addScoreViewListener(this);
    }
    
    @Override
    public void onScoreViewChanged(final ScoreView scoreView) {
        boolean resetBorder = false;
        if (scoreView.getViewOffset() == 0 && !this.m_scorePane.getSoundPlayer().isPlaying()) {
            if (!this.m_hasTopBorder) {
                resetBorder = true;
            }
            this.m_hasTopBorder = true;
        }
        else {
            if (this.m_hasTopBorder) {
                resetBorder = true;
            }
            this.m_hasTopBorder = false;
        }
        if (scoreView.getViewWidth() > this.m_scorePane.getWidth()) {
            if (this.m_hasRightBorder) {
                resetBorder = true;
            }
            this.m_hasRightBorder = false;
        }
        else {
            if (!this.m_hasRightBorder) {
                resetBorder = true;
            }
            this.m_hasRightBorder = true;
        }
        if (resetBorder) {
            this.m_corePane.setBorder(new EmptyBorder(this.m_hasTopBorder ? 30 : 0, 30, this.m_hasBottomBorder ? 30 : 0, this.m_hasRightBorder ? 30 : 0));
        }
    }
}
