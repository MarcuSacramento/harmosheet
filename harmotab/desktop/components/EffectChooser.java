// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import javax.swing.JList;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListCellRenderer;
import harmotab.core.Effect;
import javax.swing.JComboBox;

public class EffectChooser extends JComboBox
{
    private static final long serialVersionUID = 1L;
    private JComboBox m_instance;
    
    public EffectChooser(final Effect effect) {
        this.m_instance = null;
        (this.m_instance = this).setRenderer(new EffectRenderer());
        final DefaultComboBoxModel model = (DefaultComboBoxModel)this.getModel();
        for (int i = 0; i < 3; ++i) {
            model.addElement(i);
        }
        this.setEffect(effect);
    }
    
    public void setEffect(final Effect effect) {
        final byte value = effect.getType();
        this.setSelectedIndex(value);
    }
    
    public Effect getEffect() {
        final byte value = (byte)this.getSelectedIndex();
        return new Effect(value);
    }
    
    class EffectRenderer extends JPanel implements ListCellRenderer
    {
        private static final long serialVersionUID = 1L;
        private static final int IMAGE_WIDTH = 40;
        private static final int IMAGE_HEIGHT = 25;
        private JLabel m_effectLabel;
        private JLabel m_effectNameLabel;
        
        public EffectRenderer() {
            this.m_effectLabel = null;
            this.m_effectNameLabel = null;
            this.m_effectLabel = new JLabel();
            final Dimension size = new Dimension(40, 25);
            this.m_effectLabel.setPreferredSize(size);
            this.m_effectLabel.setOpaque(false);
            (this.m_effectNameLabel = new JLabel()).setOpaque(false);
            this.setLayout(new BorderLayout());
            this.add(this.m_effectNameLabel, "Center");
            this.add(this.m_effectLabel, "East");
            this.setOpaque(true);
        }
        
        @Override
        public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
            int selectedIndex = index;
            if (selectedIndex == -1) {
                selectedIndex = EffectChooser.this.m_instance.getSelectedIndex();
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
                final byte effectId = (byte)selectedIndex;
                this.m_effectNameLabel.setText(Effect.getEffectLocalizedName(effectId));
            }
            return this;
        }
    }
}
