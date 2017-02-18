// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import javax.swing.Icon;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.ListCellRenderer;
import harmotab.core.Figure;
import res.ResourceLoader;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

public class FigureChooser extends JComboBox
{
    private static final long serialVersionUID = 1L;
    private static ImageIcon[] m_figureIcons;
    private static ImageIcon[] m_restIcons;
    private JComboBox m_instance;
    private boolean m_displayRests;
    
    static {
        FigureChooser.m_figureIcons = null;
        FigureChooser.m_restIcons = null;
        final ResourceLoader loader = ResourceLoader.getInstance();
        FigureChooser.m_figureIcons = new ImageIcon[6];
        FigureChooser.m_restIcons = new ImageIcon[6];
        FigureChooser.m_figureIcons[0] = new ImageIcon(loader.loadImage("/res/controllers/whole.png"));
        FigureChooser.m_figureIcons[1] = new ImageIcon(loader.loadImage("/res/controllers/half.png"));
        FigureChooser.m_figureIcons[2] = new ImageIcon(loader.loadImage("/res/controllers/quarter.png"));
        FigureChooser.m_figureIcons[3] = new ImageIcon(loader.loadImage("/res/controllers/eighth.png"));
        FigureChooser.m_figureIcons[4] = new ImageIcon(loader.loadImage("/res/controllers/sixteenth.png"));
        FigureChooser.m_figureIcons[5] = new ImageIcon(loader.loadImage("/res/controllers/appogiature.png"));
        FigureChooser.m_restIcons[0] = new ImageIcon(loader.loadImage("/res/controllers/rest-whole.png"));
        FigureChooser.m_restIcons[1] = new ImageIcon(loader.loadImage("/res/controllers/rest-half.png"));
        FigureChooser.m_restIcons[2] = new ImageIcon(loader.loadImage("/res/controllers/rest-quarter.png"));
        FigureChooser.m_restIcons[3] = new ImageIcon(loader.loadImage("/res/controllers/rest-eighth.png"));
        FigureChooser.m_restIcons[4] = new ImageIcon(loader.loadImage("/res/controllers/rest-sixteenth.png"));
        FigureChooser.m_restIcons[5] = new ImageIcon(loader.loadImage("/res/controllers/rest-appogiature.png"));
    }
    
    public FigureChooser(final Figure figure) {
        ((FigureChooser)(this.m_instance = this)).setDisplayRests(false);
        this.setOpaque(true);
        this.setRenderer(new FigureLabelRenderer());
        for (int i = 1; i <= 6; ++i) {
            this.addItem(new StringBuilder(String.valueOf(i)).toString());
        }
        switch (figure.getType()) {
            case 1: {
                this.setSelectedIndex(0);
                break;
            }
            case 2: {
                this.setSelectedIndex(1);
                break;
            }
            case 3: {
                this.setSelectedIndex(2);
                break;
            }
            case 4: {
                this.setSelectedIndex(3);
                break;
            }
            case 5: {
                this.setSelectedIndex(4);
                break;
            }
            case 6: {
                this.setSelectedIndex(5);
                break;
            }
        }
    }
    
    public void setDisplayRests(final boolean displayRests) {
        this.m_displayRests = displayRests;
    }
    
    public boolean getDisplayRests() {
        return this.m_displayRests;
    }
    
    public Figure getSelectedFigure() {
        switch (this.getSelectedIndex()) {
            case 0: {
                return new Figure((byte)1);
            }
            case 1: {
                return new Figure((byte)2);
            }
            case 2: {
                return new Figure((byte)3);
            }
            case 3: {
                return new Figure((byte)4);
            }
            case 4: {
                return new Figure((byte)5);
            }
            case 5: {
                return new Figure((byte)6);
            }
            default: {
                return null;
            }
        }
    }
    
    class FigureLabelRenderer extends JLabel implements ListCellRenderer
    {
        private static final long serialVersionUID = 1L;
        
        public FigureLabelRenderer() {
            this.setOpaque(true);
            this.setHorizontalAlignment(0);
            this.setVerticalAlignment(0);
        }
        
        @Override
        public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
            int selectedIndex = index;
            if (selectedIndex == -1) {
                selectedIndex = FigureChooser.this.m_instance.getSelectedIndex();
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
            if (!FigureChooser.this.m_displayRests) {
                this.setIcon(FigureChooser.m_figureIcons[selectedIndex]);
            }
            else {
                this.setIcon(FigureChooser.m_restIcons[selectedIndex]);
            }
            return this;
        }
    }
}
