// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class LanguageChooser extends JComboBox
{
    private static final long serialVersionUID = 1L;
    
    public LanguageChooser(final String language) {
        final DefaultComboBoxModel model = (DefaultComboBoxModel)this.getModel();
        model.addElement("en - " + new Locale("en").getDisplayName());
        model.addElement("fr - " + new Locale("fr").getDisplayName());
        final Locale locale = new Locale(language);
        this.setSelectedItem(String.valueOf(language) + " - " + locale.getDisplayName());
    }
    
    public String getSelectedLanguageIdentifier() {
        if (this.getSelectedIndex() != -1) {
            final String selected = (String)this.getSelectedItem();
            return selected.substring(0, 2);
        }
        return null;
    }
}
