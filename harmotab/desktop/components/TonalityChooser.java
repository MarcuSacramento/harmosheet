// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import javax.swing.Icon;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import harmotab.renderer.awtrenderers.AwtKeySignatureRenderer;
import java.awt.image.BufferedImage;
import harmotab.element.Element;
import harmotab.renderer.LocationItem;
import harmotab.element.KeySignature;
import javax.swing.JList;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.JComboBox;

public class TonalityChooser extends JComboBox
{
    private static final long serialVersionUID = 1L;
    private JComboBox m_instance;
    
    public TonalityChooser(final byte tonality) {
        this.m_instance = null;
        (this.m_instance = this).setRenderer(new TonalityRenderer());
        for (int i = -6; i <= 6; ++i) {
            this.addItem(i);
        }
        this.setTonality(tonality);
    }
    
    public void setTonality(final byte tonality) {
        final int value = tonality + 6;
        this.setSelectedIndex(value);
    }
    
    public byte getTonality() {
        final int value = (byte)this.getSelectedIndex() - 6;
        return (byte)value;
    }
    
    class TonalityRenderer extends JPanel implements ListCellRenderer
    {
        private static final long serialVersionUID = 1L;
        private static final int IMAGE_WIDTH = 90;
        private static final int IMAGE_HEIGHT = 40;
        private JLabel m_alterationsLabel;
        private JLabel m_tonalityNameLabel;
        
        public TonalityRenderer() {
            this.m_alterationsLabel = null;
            this.m_tonalityNameLabel = null;
            this.m_alterationsLabel = new JLabel();
            final Dimension size = new Dimension(90, 40);
            this.m_alterationsLabel.setPreferredSize(size);
            this.m_alterationsLabel.setOpaque(false);
            (this.m_tonalityNameLabel = new JLabel()).setOpaque(false);
            this.setLayout(new BorderLayout());
            this.add(this.m_tonalityNameLabel, "Center");
            this.add(this.m_alterationsLabel, "East");
            this.setOpaque(true);
        }
        
        @Override
        public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
            int selectedIndex = index;
            if (selectedIndex == -1) {
                selectedIndex = TonalityChooser.this.m_instance.getSelectedIndex();
            }
            if (selectedIndex == -1) {
                selectedIndex = 0;
            }
            if (isSelected) {
                this.setBackground(list.getSelectionBackground());
                this.setForeground(list.getSelectionForeground());
            }
            else {
                this.setBackground(list.getBackground());
                this.setForeground(list.getForeground());
            }
            if (selectedIndex >= 0) {
                final int tonality = selectedIndex - 6;
                this.m_tonalityNameLabel.setText(KeySignature.getTonalityName(tonality));
                final KeySignature signature = new KeySignature((byte)tonality);
                final LocationItem item = new LocationItem(signature, 0, 15, 0, 0, 90, 40, -1, 0, 0.0f, 2);
                final BufferedImage image = new BufferedImage(90, 40, 2);
                new AwtKeySignatureRenderer().paint((Graphics2D)image.getGraphics(), signature, item);
                this.m_alterationsLabel.setIcon(new ImageIcon(image));
            }
            return this;
        }
    }
}
