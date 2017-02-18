// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import java.awt.Dimension;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionEvent;
import harmotab.core.Height;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.event.EventListenerList;
import javax.swing.JComboBox;
import harmotab.element.Chord;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class ChordChooser extends JPanel implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private Chord m_chord;
    private JComboBox m_fundamentalCombo;
    private JComboBox m_fundamentalAlterationCombo;
    private JComboBox m_typeCombo;
    private JComboBox m_bassCombo;
    private JComboBox m_bassAlterationCombo;
    private final EventListenerList m_listeners;
    
    public ChordChooser() {
        this.m_chord = null;
        this.m_fundamentalCombo = null;
        this.m_fundamentalAlterationCombo = null;
        this.m_typeCombo = null;
        this.m_bassCombo = null;
        this.m_bassAlterationCombo = null;
        this.m_listeners = new EventListenerList();
        this.setChord(null);
        this.create();
    }
    
    public ChordChooser(final Chord chord) {
        this.m_chord = null;
        this.m_fundamentalCombo = null;
        this.m_fundamentalAlterationCombo = null;
        this.m_typeCombo = null;
        this.m_bassCombo = null;
        this.m_bassAlterationCombo = null;
        this.m_listeners = new EventListenerList();
        this.setChord(chord);
        this.create();
    }
    
    private void create() {
        this.setOpaque(false);
        this.m_fundamentalCombo = this.createCombo(Chord.m_heightNames, false);
        this.m_fundamentalAlterationCombo = this.createCombo(Chord.m_alterationsNames, false);
        this.m_typeCombo = this.createCombo(Chord.m_typesNames, true);
        this.m_bassCombo = this.createCombo(Chord.m_heightNames, true);
        this.m_bassAlterationCombo = this.createCombo(Chord.m_alterationsNames, false);
        this.add(this.m_fundamentalCombo);
        this.add(this.m_fundamentalAlterationCombo);
        this.add(this.m_typeCombo);
        this.add(new JLabel(" / "));
        this.add(this.m_bassCombo);
        this.add(this.m_bassAlterationCombo);
        this.m_fundamentalCombo.addActionListener(this);
        this.m_fundamentalAlterationCombo.addActionListener(this);
        this.m_typeCombo.addActionListener(this);
        this.m_bassCombo.addActionListener(this);
        this.m_bassAlterationCombo.addActionListener(this);
        this.updateChordView();
    }
    
    private void updateChordView() {
        final Height fundamentalHeight = this.m_chord.extractNoteHeight();
        final String chordType = this.m_chord.extractType();
        final Height bassHeight = this.m_chord.extractBassHeight();
        this.m_fundamentalCombo.setSelectedItem(fundamentalHeight.getNoteChar());
        this.m_fundamentalAlterationCombo.setSelectedItem(fundamentalHeight.getAlterationChar());
        if (chordType == null) {
            this.m_typeCombo.setSelectedIndex(0);
        }
        else {
            this.m_typeCombo.setSelectedItem(chordType);
        }
        this.m_bassCombo.setSelectedItem((bassHeight != null) ? bassHeight.getNoteChar() : " ");
        this.m_bassAlterationCombo.setSelectedItem((bassHeight != null) ? bassHeight.getAlterationChar() : " ");
    }
    
    private void updateChord() {
        final Height fundamentalHeight = new Height(String.valueOf(this.m_fundamentalCombo.getSelectedItem()) + ((String)this.m_fundamentalAlterationCombo.getSelectedItem()).trim());
        fundamentalHeight.setOctave(4);
        final String type = ((String)this.m_typeCombo.getSelectedItem()).trim();
        Height bassHeight = null;
        if (!((String)this.m_bassCombo.getSelectedItem()).trim().equals("")) {
            bassHeight = new Height(String.valueOf(((String)this.m_bassCombo.getSelectedItem()).trim()) + ((String)this.m_bassAlterationCombo.getSelectedItem()).trim());
            bassHeight.setOctave(3);
        }
        this.m_chord = new Chord(fundamentalHeight, type, bassHeight);
    }
    
    public void setChord(final Chord chord) {
        if (chord == null || !chord.isDefined()) {
            this.m_chord = new Chord();
        }
        else {
            this.m_chord = (Chord)chord.clone();
        }
    }
    
    public Chord getChord() {
        return this.m_chord;
    }
    
    @Override
    public void actionPerformed(final ActionEvent event) {
        this.updateChord();
        this.fireActionPerformed(this, event.getID(), event.getActionCommand());
    }
    
    public void addActionListener(final ActionListener listener) {
        this.m_listeners.add(ActionListener.class, listener);
    }
    
    private void fireActionPerformed(final Object source, final int id, final String command) {
        final ActionEvent event = new ActionEvent(source, id, command);
        ActionListener[] array;
        for (int length = (array = this.m_listeners.getListeners(ActionListener.class)).length, i = 0; i < length; ++i) {
            final ActionListener listener = array[i];
            listener.actionPerformed(event);
        }
    }
    
    private JComboBox createCombo(final String[] values, final boolean addEmptyChoice) {
        final DefaultComboBoxModel model = new DefaultComboBoxModel();
        if (addEmptyChoice) {
            model.addElement(new String(" "));
        }
        for (final String value : values) {
            model.addElement(value);
        }
        final JComboBox combo = new JComboBox(model);
        final Dimension size = combo.getPreferredSize();
        size.width = 60;
        combo.setPreferredSize(size);
        combo.setOpaque(false);
        return combo;
    }
}
