// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.modeleditor;

import javax.swing.JOptionPane;
import javax.swing.Icon;
import harmotab.desktop.GuiIcon;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import harmotab.core.Height;
import java.awt.event.MouseListener;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import harmotab.core.Localizer;
import java.awt.Color;
import harmotab.element.Tab;
import harmotab.harmonica.Harmonica;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.JPanel;

class HarmonicaModelDirectionLabel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private final Dimension m_size;
    private JLabel m_label;
    private Harmonica m_harmonica;
    private Tab m_tab;
    
    public HarmonicaModelDirectionLabel(final Harmonica harmonica, final Tab tab) {
        this.m_size = new Dimension(25, 20);
        this.m_label = null;
        this.m_harmonica = null;
        this.m_tab = null;
        this.m_harmonica = harmonica;
        this.m_tab = tab;
        (this.m_label = new JLabel()).setOpaque(true);
        this.m_label.setPreferredSize(this.m_size);
        this.m_label.setHorizontalAlignment(0);
        final Height h = harmonica.getHeight(this.m_tab);
        this.m_label.setText((h != null) ? (String.valueOf(h.getNoteName()) + h.getOctave()) : "");
        this.m_label.setBackground(Color.WHITE);
        this.m_label.setToolTipText(String.valueOf(Localizer.get("N_HOLE")) + " " + this.m_tab.getHole() + ", " + this.m_tab.getBreathName());
        this.m_label.setFont(this.m_label.getFont().deriveFont(10.0f));
        this.m_label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setLayout(new FlowLayout(0, 0, 0));
        this.add(this.m_label);
        final MouseObserver mouseObserver = new MouseObserver((MouseObserver)null);
        this.addMouseListener(mouseObserver);
        this.m_label.addMouseListener(mouseObserver);
        this.setPreferredSize(this.m_size);
        this.setOpaque(false);
        this.setBackground(Color.WHITE);
    }
    
    public void updateContent() {
        final Height h = this.m_harmonica.getHeight(this.m_tab);
        this.m_label.setText((h != null) ? (String.valueOf(h.getNoteName()) + h.getOctave()) : "");
    }
    
    private Object[] getAllNoteNames() {
        final ArrayList<Object> result = new ArrayList<Object>();
        for (int octave = 3; octave <= 6; ++octave) {
            String[] notesName;
            for (int length = (notesName = Height.getNotesName()).length, i = 0; i < length; ++i) {
                final String noteName = notesName[i];
                result.add(String.valueOf(noteName) + octave);
            }
        }
        return result.toArray();
    }
    
    private class MouseObserver implements MouseListener
    {
        @Override
        public void mouseEntered(final MouseEvent event) {
            if (HarmonicaModelDirectionLabel.this.m_harmonica.isSet(HarmonicaModelDirectionLabel.this.m_tab)) {
                HarmonicaModelDirectionLabel.this.m_label.setBackground(Color.YELLOW);
            }
            else {
                HarmonicaModelDirectionLabel.this.m_label.setIcon(GuiIcon.getIcon((byte)8));
            }
        }
        
        @Override
        public void mouseExited(final MouseEvent event) {
            HarmonicaModelDirectionLabel.this.m_label.setBackground(Color.WHITE);
            HarmonicaModelDirectionLabel.this.m_label.setIcon(null);
        }
        
        @Override
        public void mousePressed(final MouseEvent event) {
            Height height = HarmonicaModelDirectionLabel.this.m_harmonica.getHeight(HarmonicaModelDirectionLabel.this.m_tab);
            if (height == null) {
                height = new Height();
            }
            if (event.getButton() == 1) {
                final String choice = (String)JOptionPane.showInputDialog(null, Localizer.get("M_MODEL_NOTE_SELECTION").replace("%HOLE%", new StringBuilder(String.valueOf(HarmonicaModelDirectionLabel.this.m_tab.getHole())).toString()).replace("%TYPE%", HarmonicaModelDirectionLabel.this.m_tab.getBendStr()), Localizer.get("ET_NOTE_SELECTION"), 3, null, HarmonicaModelDirectionLabel.this.getAllNoteNames(), String.valueOf(height.getNoteName()) + height.getOctave());
                if (choice != null) {
                    HarmonicaModelDirectionLabel.this.m_harmonica.setHeight(HarmonicaModelDirectionLabel.this.m_tab, new Height(choice));
                }
            }
            else if (event.getButton() == 3) {
                HarmonicaModelDirectionLabel.this.m_harmonica.getModel().unset(HarmonicaModelDirectionLabel.this.m_tab);
            }
        }
        
        @Override
        public void mouseClicked(final MouseEvent event) {
        }
        
        @Override
        public void mouseReleased(final MouseEvent event) {
        }
    }
}
