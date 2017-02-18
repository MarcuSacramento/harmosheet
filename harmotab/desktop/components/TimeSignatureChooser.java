// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import javax.swing.JComboBox;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import harmotab.element.TimeSignature;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class TimeSignatureChooser extends JPanel implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private NumberCombo m_numberCombo;
    private ReferenceCombo m_referenceCombo;
    private final ArrayList<TimeSignatureListener> m_listeners;
    private byte m_number;
    private byte m_reference;
    
    public TimeSignatureChooser(final byte number, final byte reference) {
        this.m_listeners = new ArrayList<TimeSignatureListener>();
        this.m_number = 0;
        this.m_reference = 0;
        this.m_number = number;
        this.m_reference = reference;
        this.m_numberCombo = new NumberCombo();
        this.m_referenceCombo = new ReferenceCombo();
        this.setOpaque(false);
        this.setLayout(new FlowLayout());
        this.add(this.m_numberCombo);
        this.add(this.m_referenceCombo);
        this.m_numberCombo.addActionListener(this);
        this.m_referenceCombo.addActionListener(this);
    }
    
    public TimeSignatureChooser(final TimeSignature timeSignature) {
        this(timeSignature.getNumber(), timeSignature.getReference());
    }
    
    public TimeSignatureChooser() {
        this(new TimeSignature());
    }
    
    public byte getNumber() {
        return this.m_number;
    }
    
    public byte getReference() {
        return this.m_reference;
    }
    
    @Override
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        this.m_numberCombo.setEnabled(enabled);
        this.m_referenceCombo.setEnabled(enabled);
    }
    
    @Override
    public void actionPerformed(final ActionEvent event) {
        if (event.getSource() == this.m_numberCombo) {
            this.m_number = (byte)(this.m_numberCombo.getSelectedIndex() + 1);
            this.fireNumberChanged();
        }
        else if (event.getSource() == this.m_referenceCombo) {
            this.m_reference = (byte)Integer.parseInt((String)this.m_referenceCombo.getSelectedItem());
            this.fireReferenceChanged();
        }
    }
    
    public void addTimeSignatureListener(final TimeSignatureListener listener) {
        this.m_listeners.add(listener);
    }
    
    public void removeTimeSignatureListener(final TimeSignatureListener listener) {
        this.m_listeners.remove(listener);
    }
    
    protected void fireNumberChanged() {
        for (final TimeSignatureListener listener : this.m_listeners) {
            listener.onNumberChanged(this.m_number);
        }
    }
    
    protected void fireReferenceChanged() {
        for (final TimeSignatureListener listener : this.m_listeners) {
            listener.onReferenceChanged(this.m_reference);
        }
    }
    
    class NumberCombo extends JComboBox
    {
        private static final long serialVersionUID = 1L;
        
        public NumberCombo() {
            int selected = 0;
            for (byte i = 1; i <= 16; ++i) {
                this.addItem(new StringBuilder(String.valueOf(i)).toString());
                if (TimeSignatureChooser.this.m_number == i) {
                    selected = this.getItemCount() - 1;
                }
            }
            this.setSelectedIndex(selected);
        }
    }
    
    class ReferenceCombo extends JComboBox
    {
        private static final long serialVersionUID = 1L;
        
        public ReferenceCombo() {
            int selected = 0;
            for (byte i = 1; i <= 16; ++i) {
                if (TimeSignature.isReferenceValid(i)) {
                    this.addItem(new StringBuilder(String.valueOf(i)).toString());
                    if (TimeSignatureChooser.this.m_reference == i) {
                        selected = this.getItemCount() - 1;
                    }
                }
            }
            this.setSelectedIndex(selected);
        }
    }
    
    public interface TimeSignatureListener
    {
        void onNumberChanged(final byte p0);
        
        void onReferenceChanged(final byte p0);
    }
}
