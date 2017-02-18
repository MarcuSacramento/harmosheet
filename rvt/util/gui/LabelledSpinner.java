// 
// Decompiled by Procyon v0.5.30
// 

package rvt.util.gui;

import javax.swing.event.DocumentEvent;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeListener;
import javax.swing.SpinnerModel;
import javax.swing.JComponent;
import javax.swing.JSpinner;

public class LabelledSpinner extends JSpinner
{
    private static final long serialVersionUID = 1L;
    public static final int COLUMNS_FIT_CONTENT = 0;
    protected LabelledSpinnerEditor m_labelledSpinnerEditor;
    protected LabelledSpinner m_labelledSpinnerInstance;
    protected int m_editorColumns;
    
    public LabelledSpinner(final String label) {
        this.m_labelledSpinnerEditor = null;
        this.m_labelledSpinnerInstance = null;
        this.m_editorColumns = 5;
        (this.m_labelledSpinnerInstance = this).setEditor(this.m_labelledSpinnerEditor = new LabelledSpinnerEditor(label));
        this.m_labelledSpinnerEditor.updateView();
    }
    
    public LabelledSpinner(final String label, final SpinnerModel model) {
        this.m_labelledSpinnerEditor = null;
        this.m_labelledSpinnerInstance = null;
        this.m_editorColumns = 5;
        (this.m_labelledSpinnerInstance = this).setEditor(this.m_labelledSpinnerEditor = new LabelledSpinnerEditor(label));
        this.setModel(model);
        this.m_labelledSpinnerEditor.updateView();
    }
    
    public LabelledSpinner(final String label, final SpinnerModel model, final int editorColumns) {
        this.m_labelledSpinnerEditor = null;
        this.m_labelledSpinnerInstance = null;
        this.m_editorColumns = 5;
        this.m_labelledSpinnerInstance = this;
        this.m_editorColumns = editorColumns;
        this.setEditor(this.m_labelledSpinnerEditor = new LabelledSpinnerEditor(label));
        this.setModel(model);
        this.m_labelledSpinnerEditor.updateView();
    }
    
    protected class LabelledSpinnerEditor extends DefaultEditor implements ChangeListener, ActionListener, DocumentListener
    {
        private static final long serialVersionUID = 1L;
        protected JLabel m_label;
        protected JTextField m_textField;
        protected String m_labelText;
        
        public LabelledSpinnerEditor(final String label) {
            super(LabelledSpinner.this.m_labelledSpinnerInstance);
            this.m_label = null;
            this.m_textField = null;
            this.m_labelText = null;
            this.m_labelText = label;
            this.m_textField = new JTextField(LabelledSpinner.this.m_editorColumns);
            this.m_label = new JLabel(" " + this.m_labelText + " ");
            this.setBorder(this.m_textField.getBorder());
            this.setBackground(this.m_textField.getBackground());
            this.setForeground(this.m_textField.getForeground());
            this.m_label.setOpaque(false);
            this.m_label.setFont(this.m_textField.getFont());
            this.m_textField.setOpaque(false);
            this.m_textField.setBorder(new EmptyBorder(0, 0, 0, 0));
            this.m_textField.setHorizontalAlignment(4);
            final BorderLayout layout = new BorderLayout();
            layout.setHgap(0);
            layout.setVgap(0);
            this.setLayout(layout);
            this.add(this.m_textField, "Center");
            this.add(this.m_label, "East");
            this.updateView();
            this.m_textField.requestFocus();
            LabelledSpinner.this.m_labelledSpinnerInstance.addChangeListener(this);
            this.m_textField.addActionListener(this);
            this.m_textField.getDocument().addDocumentListener(this);
            LabelledSpinner.this.m_labelledSpinnerInstance.addPropertyChangeListener(this);
        }
        
        public void updateModel() {
            try {
                final Object value = LabelledSpinner.this.m_labelledSpinnerInstance.getModel().getValue();
                if (value instanceof Integer) {
                    LabelledSpinner.this.m_labelledSpinnerInstance.setValue(new Integer(this.m_textField.getText()));
                }
                else if (value instanceof Float) {
                    LabelledSpinner.this.m_labelledSpinnerInstance.setValue(new Float(this.m_textField.getText()));
                }
                else if (value instanceof Double) {
                    LabelledSpinner.this.m_labelledSpinnerInstance.setValue(new Double(this.m_textField.getText()));
                }
            }
            catch (Throwable t) {}
        }
        
        public void updateView() {
            final String valueString = String.valueOf(LabelledSpinner.this.m_labelledSpinnerInstance.getValue());
            this.m_textField.setText(valueString);
        }
        
        @Override
        public void stateChanged(final ChangeEvent event) {
            this.updateView();
        }
        
        @Override
        public void actionPerformed(final ActionEvent event) {
            this.updateModel();
        }
        
        @Override
        public void changedUpdate(final DocumentEvent event) {
            this.updateModel();
        }
        
        @Override
        public void insertUpdate(final DocumentEvent event) {
            this.updateModel();
        }
        
        @Override
        public void removeUpdate(final DocumentEvent event) {
            this.updateModel();
        }
    }
}
