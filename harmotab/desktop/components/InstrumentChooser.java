// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import harmotab.core.Localizer;
import java.util.Vector;
import javax.swing.JComboBox;

public class InstrumentChooser extends JComboBox
{
    private static final long serialVersionUID = 1L;
    public static final int INSTRUMENTS_NUMBER = 128;
    private static Vector<String> m_instruments;
    
    static {
        InstrumentChooser.m_instruments = new Vector<String>(128);
        for (int i = 0; i < 128; ++i) {
            InstrumentChooser.m_instruments.add(Localizer.get("MIDI_INSTRUMENT_%".replace("%", new StringBuilder(String.valueOf(i)).toString())));
        }
    }
    
    public InstrumentChooser() {
        super(InstrumentChooser.m_instruments);
    }
    
    public InstrumentChooser(final int value) {
        super(InstrumentChooser.m_instruments);
        this.setSelectedIndex(value);
    }
}
