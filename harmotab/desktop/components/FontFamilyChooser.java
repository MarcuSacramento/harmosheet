// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.ListCellRenderer;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComboBox;

public class FontFamilyChooser extends JComboBox
{
    private static final long serialVersionUID = 1L;
    public static boolean USE_LIGHT_VERSION;
    private static final int FONT_SIZE = 12;
    private static final Font DEFAULT_FONT;
    private static final String DEFAULT_FONT_FAMILY;
    private static final Dimension ITEM_SIZE;
    private static final Dimension DEFAULT_SIZE;
    private static final String[] m_fontsName;
    private static final ArrayList<FontItem> m_fontLabels;
    
    static {
        FontFamilyChooser.USE_LIGHT_VERSION = false;
        DEFAULT_FONT = new Font("Sans-serif", 0, 12);
        DEFAULT_FONT_FAMILY = FontFamilyChooser.DEFAULT_FONT.getFamily();
        ITEM_SIZE = new Dimension(180, 30);
        DEFAULT_SIZE = new Dimension(200, 30);
        m_fontsName = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        m_fontLabels = new ArrayList<FontItem>(0);
    }
    
    public FontFamilyChooser() {
        this(FontFamilyChooser.DEFAULT_FONT_FAMILY);
    }
    
    public FontFamilyChooser(final String fontFamilyName) {
        boolean fontFamilyExists = false;
        int defaultFontIndex = 0;
        this.setEditable(false);
        this.setRenderer(new ComboBoxRenderer());
        String[] fontsName;
        for (int length = (fontsName = FontFamilyChooser.m_fontsName).length, i = 0; i < length; ++i) {
            final String fontName = fontsName[i];
            final FontItem item = new FontItem(fontName);
            FontFamilyChooser.m_fontLabels.add(item);
            this.addItem(item);
            if (fontName.equals(fontFamilyName)) {
                this.setSelectedItem(item);
                fontFamilyExists = true;
            }
            if (fontName.equals(FontFamilyChooser.DEFAULT_FONT_FAMILY)) {
                defaultFontIndex = this.getItemCount() - 1;
            }
        }
        if (!fontFamilyExists) {
            this.setSelectedIndex(defaultFontIndex);
        }
    }
    
    public FontFamilyChooser(final String fontFamilyName, final int x, final int y) {
        this(fontFamilyName);
        this.setBounds(x, y, FontFamilyChooser.DEFAULT_SIZE.width, FontFamilyChooser.DEFAULT_SIZE.height);
    }
    
    public String getSelectedFontFamily() {
        final int selected = this.getSelectedIndex();
        if (selected == -1) {
            return FontFamilyChooser.DEFAULT_FONT_FAMILY;
        }
        return FontFamilyChooser.m_fontsName[selected];
    }
    
    class FontItem
    {
        private Font m_font;
        private String m_fontFamily;
        
        public FontItem(final String fontFamily) {
            this.m_fontFamily = fontFamily;
            if (FontFamilyChooser.USE_LIGHT_VERSION) {
                this.m_font = FontFamilyChooser.DEFAULT_FONT;
            }
            else {
                this.m_font = new Font(fontFamily, 0, 12);
                if (!this.m_font.canDisplay(this.m_fontFamily.charAt(0))) {
                    this.m_font = FontFamilyChooser.DEFAULT_FONT;
                }
            }
        }
        
        public String getFontFamily() {
            return this.m_fontFamily;
        }
        
        public Font getFont() {
            return this.m_font;
        }
    }
    
    class ComboBoxRenderer extends JLabel implements ListCellRenderer
    {
        private static final long serialVersionUID = 1L;
        
        @Override
        public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
            int id = index;
            if (id == -1) {
                id = list.getSelectedIndex();
            }
            if (id == -1) {
                id = 0;
            }
            final FontItem fontItem = FontFamilyChooser.m_fontLabels.get(id);
            this.setText(fontItem.getFontFamily());
            this.setFont(fontItem.getFont());
            this.setOpaque(true);
            this.setPreferredSize(FontFamilyChooser.ITEM_SIZE);
            if (isSelected) {
                this.setBackground(list.getSelectionBackground());
                this.setForeground(list.getSelectionForeground());
            }
            else {
                this.setBackground(Color.WHITE);
                this.setForeground(list.getForeground());
            }
            return this;
        }
    }
}
