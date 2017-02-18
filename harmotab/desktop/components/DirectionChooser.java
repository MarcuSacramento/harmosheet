// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import javax.swing.Icon;
import javax.swing.JList;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import harmotab.core.Localizer;
import harmotab.core.GlobalPreferences;
import harmotab.element.Tab;
import res.ResourceLoader;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

public class DirectionChooser extends JComboBox
{
    private static final long serialVersionUID = 1L;
    private static final int UP_FULL_BEND_ID = 0;
    private static final int UP_HALF_BEND_ID = 1;
    private static final int UP_NO_BEND_ID = 2;
    private static final int UNDEFINED_ID = 3;
    private static final int DOWN_NO_BEND_ID = 4;
    private static final int DOWN_HALF_BEND_ID = 5;
    private static final int DOWN_FULL_BEND_ID = 6;
    private static ImageIcon[] m_icons;
    private JComboBox m_instance;
    private boolean m_blowUp;
    private String[] m_labels;
    
    static {
        DirectionChooser.m_icons = null;
        final ResourceLoader loader = ResourceLoader.getInstance();
        (DirectionChooser.m_icons = new ImageIcon[7])[0] = new ImageIcon(loader.loadImage("/res/controllers/up-full-bend.png"));
        DirectionChooser.m_icons[1] = new ImageIcon(loader.loadImage("/res/controllers/up-half-bend.png"));
        DirectionChooser.m_icons[2] = new ImageIcon(loader.loadImage("/res/controllers/up-no-bend.png"));
        DirectionChooser.m_icons[3] = new ImageIcon(loader.loadImage("/res/controllers/undefined.png"));
        DirectionChooser.m_icons[4] = new ImageIcon(loader.loadImage("/res/controllers/down-no-bend.png"));
        DirectionChooser.m_icons[5] = new ImageIcon(loader.loadImage("/res/controllers/down-half-bend.png"));
        DirectionChooser.m_icons[6] = new ImageIcon(loader.loadImage("/res/controllers/down-full-bend.png"));
    }
    
    public DirectionChooser(final Tab tab) {
        this.m_blowUp = true;
        this.m_labels = null;
        this.m_instance = this;
        this.m_blowUp = (GlobalPreferences.getTabBlowDirection() == 1);
        this.m_labels = new String[7];
        if (this.m_blowUp) {
            this.m_labels[0] = Localizer.get("N_FULL_OVERBLOW");
            this.m_labels[1] = Localizer.get("N_HALF_OVERBLOW");
            this.m_labels[2] = Localizer.get("N_BLOW");
            this.m_labels[3] = "";
            this.m_labels[4] = Localizer.get("N_DRAW");
            this.m_labels[5] = Localizer.get("N_HALF_BEND");
            this.m_labels[6] = Localizer.get("N_FULL_BEND");
        }
        else {
            this.m_labels[0] = Localizer.get("N_FULL_BEND");
            this.m_labels[1] = Localizer.get("N_HALF_BEND");
            this.m_labels[2] = Localizer.get("N_DRAW");
            this.m_labels[3] = "";
            this.m_labels[4] = Localizer.get("N_BLOW");
            this.m_labels[5] = Localizer.get("N_HALF_OVERBLOW");
            this.m_labels[6] = Localizer.get("N_FULL_OVERBLOW");
        }
        this.setOpaque(true);
        this.setRenderer(new DirectionLabelRenderer());
        this.addItem("0");
        this.addItem("1");
        this.addItem("2");
        this.addItem("3");
        this.addItem("4");
        this.addItem("5");
        this.addItem("6");
        this.setTab(tab);
    }
    
    public DirectionChooser() {
        this((Tab)null);
    }
    
    public Tab getTab(final int hole) {
        final int selected = this.getSelectedIndex();
        byte direction = 0;
        if (selected > 3) {
            direction = (byte)(this.m_blowUp ? 2 : 1);
        }
        if (selected < 3) {
            direction = (byte)(this.m_blowUp ? 1 : 2);
        }
        byte bend = 0;
        if (selected == 5 || selected == 1) {
            bend = 1;
        }
        if (selected == 6 || selected == 0) {
            bend = 2;
        }
        return new Tab(hole, direction, bend);
    }
    
    public void setTab(final Tab tab) {
        if (tab == null) {
            this.setSelectedIndex(3);
            return;
        }
        switch (tab.getDirection()) {
            case 0: {
                this.setSelectedIndex(3);
                break;
            }
            case 1: {
                if (tab.getBend() == 0) {
                    this.setSelectedIndex(this.m_blowUp ? 2 : 4);
                    break;
                }
                if (tab.getBend() == 1) {
                    this.setSelectedIndex(this.m_blowUp ? 1 : 5);
                    break;
                }
                if (tab.getBend() == 2) {
                    this.setSelectedIndex(this.m_blowUp ? 0 : 6);
                    break;
                }
                break;
            }
            case 2: {
                if (tab.getBend() == 0) {
                    this.setSelectedIndex(this.m_blowUp ? 4 : 2);
                    break;
                }
                if (tab.getBend() == 1) {
                    this.setSelectedIndex(this.m_blowUp ? 5 : 1);
                    break;
                }
                if (tab.getBend() == 2) {
                    this.setSelectedIndex(this.m_blowUp ? 6 : 0);
                    break;
                }
                break;
            }
        }
    }
    
    class DirectionLabelRenderer extends JPanel implements ListCellRenderer
    {
        private static final long serialVersionUID = 1L;
        private JLabel m_iconLabel;
        private JLabel m_stringLabel;
        
        public DirectionLabelRenderer() {
            this.m_iconLabel = null;
            this.m_stringLabel = null;
            (this.m_iconLabel = new JLabel()).setHorizontalAlignment(0);
            this.m_iconLabel.setVerticalAlignment(0);
            this.m_stringLabel = new JLabel();
            this.setLayout(new BorderLayout(5, 5));
            this.add(this.m_iconLabel, "West");
            this.add(this.m_stringLabel, "Center");
            this.setBackground(Color.WHITE);
            this.setPreferredSize(new Dimension(170, 25));
        }
        
        @Override
        public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
            int selectedIndex = index;
            if (selectedIndex == -1) {
                selectedIndex = DirectionChooser.this.m_instance.getSelectedIndex();
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
            this.setToolTipText(DirectionChooser.this.m_labels[selectedIndex]);
            this.m_iconLabel.setIcon(DirectionChooser.m_icons[selectedIndex]);
            this.m_stringLabel.setText(DirectionChooser.this.m_labels[selectedIndex]);
            return this;
        }
    }
}
