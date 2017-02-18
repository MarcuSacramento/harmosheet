// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.modeleditor;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import harmotab.core.HarmoTabObjectEvent;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import harmotab.harmonica.HarmonicaType;
import harmotab.element.Tab;
import harmotab.core.Localizer;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import harmotab.core.HarmoTabObjectListener;
import java.awt.Component;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JPanel;
import harmotab.harmonica.Harmonica;
import javax.swing.JScrollPane;

public class HarmonicaModelPane extends JScrollPane
{
    private static final long serialVersionUID = 1L;
    private Harmonica m_harmonica;
    private JPanel innerPane;
    private ModelObserver m_modelObserver;
    private ChromaticButtonObserver m_chromaticButtonObserver;
    private ArrayList<HarmonicaModelDirectionLabel> m_labels;
    
    public HarmonicaModelPane(final Harmonica harmonica) {
        this.m_harmonica = null;
        this.innerPane = null;
        this.m_modelObserver = null;
        this.m_chromaticButtonObserver = null;
        this.m_labels = null;
        this.m_harmonica = harmonica;
        this.m_labels = new ArrayList<HarmonicaModelDirectionLabel>();
        this.m_chromaticButtonObserver = new ChromaticButtonObserver((ChromaticButtonObserver)null);
        (this.innerPane = new JPanel()).setSize(new Dimension(400, 200));
        this.innerPane.setBackground(Color.WHITE);
        this.setViewportView(this.innerPane);
        this.update();
        this.m_modelObserver = new ModelObserver((ModelObserver)null);
        this.m_harmonica.addObjectListener(this.m_modelObserver);
        this.setPreferredSize(new Dimension(400, 200));
    }
    
    public void finalize() {
        this.m_harmonica.removeObjectListener(this.m_modelObserver);
    }
    
    public void update() {
        this.makeUI();
        this.innerPane.revalidate();
        this.innerPane.repaint();
    }
    
    private void makeUI() {
        this.m_labels.clear();
        this.innerPane.removeAll();
        final GridBagLayout layout = new GridBagLayout();
        this.innerPane.setLayout(layout);
        final int numberOfHoles = this.m_harmonica.getModel().getNumberOfHoles();
        final boolean pushed = this.m_chromaticButtonObserver.getButtonPushed();
        this.innerPane.add(new DirectionLabel(Localizer.get("N_FULL_OVERBLOW")), this.getConstraints(0, 2));
        this.innerPane.add(new DirectionLabel(Localizer.get("N_HALF_OVERBLOW")), this.getConstraints(0, 3));
        this.innerPane.add(new DirectionLabel(Localizer.get("N_BLOW")), this.getConstraints(0, 4));
        this.innerPane.add(new DirectionLabel(Localizer.get("N_DRAW")), this.getConstraints(0, 6));
        this.innerPane.add(new DirectionLabel(Localizer.get("N_HALF_BEND")), this.getConstraints(0, 7));
        this.innerPane.add(new DirectionLabel(Localizer.get("N_FULL_BEND")), this.getConstraints(0, 8));
        for (int i = 1; i <= numberOfHoles; ++i) {
            this.innerPane.add(new HoleLabel(new StringBuilder(String.valueOf(i)).toString()), this.getConstraints(i + 1, 0));
        }
        for (int i = 1; i <= numberOfHoles; ++i) {
            final HarmonicaModelDirectionLabel fullOverblowLabel = new HarmonicaModelDirectionLabel(this.m_harmonica, new Tab(i, (byte)1, (byte)2, pushed));
            final HarmonicaModelDirectionLabel halfOverblowLabel = new HarmonicaModelDirectionLabel(this.m_harmonica, new Tab(i, (byte)1, (byte)1, pushed));
            final HarmonicaModelDirectionLabel blowLabel = new HarmonicaModelDirectionLabel(this.m_harmonica, new Tab(i, (byte)1, (byte)0, pushed));
            this.innerPane.add(fullOverblowLabel, this.getConstraints(i + 1, 2));
            this.innerPane.add(halfOverblowLabel, this.getConstraints(i + 1, 3));
            this.innerPane.add(blowLabel, this.getConstraints(i + 1, 4));
            this.m_labels.add(fullOverblowLabel);
            this.m_labels.add(halfOverblowLabel);
            this.m_labels.add(blowLabel);
        }
        this.innerPane.add(new HarmonicaBodyLabel(-1), this.getConstraints(1, 5));
        for (int i = 0; i < numberOfHoles; ++i) {
            this.innerPane.add(new HarmonicaBodyLabel(i + 1), this.getConstraints(i + 2, 5));
        }
        HarmonicaBodyLabel endLabel = null;
        if (this.m_harmonica.getModel().getHarmonicaType() == HarmonicaType.CHROMATIC) {
            endLabel = new HarmonicaBodyLabel(pushed ? -4 : -3);
            endLabel.setActionListener(this.m_chromaticButtonObserver);
        }
        else {
            endLabel = new HarmonicaBodyLabel(-2);
            this.m_chromaticButtonObserver.setButtonPushed(false);
        }
        this.innerPane.add(endLabel, this.getConstraints(numberOfHoles + 2, 5));
        for (int j = 1; j <= numberOfHoles; ++j) {
            final HarmonicaModelDirectionLabel drawLabel = new HarmonicaModelDirectionLabel(this.m_harmonica, new Tab(j, (byte)2, (byte)0, pushed));
            final HarmonicaModelDirectionLabel halfBendLabel = new HarmonicaModelDirectionLabel(this.m_harmonica, new Tab(j, (byte)2, (byte)1, pushed));
            final HarmonicaModelDirectionLabel fullBendLabel = new HarmonicaModelDirectionLabel(this.m_harmonica, new Tab(j, (byte)2, (byte)2, pushed));
            this.innerPane.add(drawLabel, this.getConstraints(j + 1, 6));
            this.innerPane.add(halfBendLabel, this.getConstraints(j + 1, 7));
            this.innerPane.add(fullBendLabel, this.getConstraints(j + 1, 8));
            this.m_labels.add(drawLabel);
            this.m_labels.add(halfBendLabel);
            this.m_labels.add(fullBendLabel);
        }
    }
    
    private GridBagConstraints getConstraints(final int x, final int y) {
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = 1;
        constraints.gridx = x;
        constraints.gridy = y;
        return constraints;
    }
    
    private class ModelObserver implements HarmoTabObjectListener
    {
        @Override
        public void onObjectChanged(final HarmoTabObjectEvent event) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    HarmonicaModelPane.this.update();
                }
            });
        }
    }
    
    private class ChromaticButtonObserver implements ActionListener
    {
        private boolean m_pushed;
        
        private ChromaticButtonObserver() {
            this.m_pushed = false;
        }
        
        @Override
        public void actionPerformed(final ActionEvent event) {
            this.m_pushed = !this.m_pushed;
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    HarmonicaModelPane.this.update();
                }
            });
        }
        
        public void setButtonPushed(final boolean pushed) {
            this.m_pushed = pushed;
        }
        
        public boolean getButtonPushed() {
            return this.m_pushed;
        }
    }
    
    private class DirectionLabel extends JLabel
    {
        private static final long serialVersionUID = 1L;
        
        public DirectionLabel(final String text) {
            super(text);
            this.setHorizontalAlignment(4);
        }
    }
    
    private class HoleLabel extends JLabel
    {
        private static final long serialVersionUID = 1L;
        
        public HoleLabel(final String text) {
            super(text);
            this.setHorizontalAlignment(0);
            this.setBorder(new EmptyBorder(0, 0, 10, 0));
        }
    }
}
