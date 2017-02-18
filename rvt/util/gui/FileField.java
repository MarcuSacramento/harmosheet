// 
// Decompiled by Procyon v0.5.30
// 

package rvt.util.gui;

import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import java.awt.event.KeyListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.event.EventListenerList;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;

public class FileField extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JTextField m_pathField;
    private JButton m_browseButton;
    private boolean m_onlyFolder;
    private EventListenerList m_listeners;
    
    public FileField(final String path, final boolean folder) {
        this.m_pathField = null;
        this.m_browseButton = null;
        this.m_onlyFolder = false;
        this.m_listeners = new EventListenerList();
        this.m_onlyFolder = folder;
        this.m_pathField = new JTextField(path);
        this.m_browseButton = new JButton("...");
        this.setLayout(new BorderLayout(5, 5));
        this.add(this.m_pathField, "Center");
        this.add(this.m_browseButton, "East");
        this.m_browseButton.addActionListener(new BrowseAction((BrowseAction)null));
        this.m_pathField.addActionListener(new PathChangedObserver((PathChangedObserver)null));
        this.setOpaque(false);
    }
    
    public FileField() {
        this("", false);
    }
    
    public FileField(final boolean folder) {
        this("", folder);
    }
    
    public File getFile() {
        if (this.m_pathField.getText().trim().equals("")) {
            return null;
        }
        final File file = new File(this.m_pathField.getText());
        return file;
    }
    
    public void setFile(final String path) {
        this.m_pathField.setText(path);
    }
    
    public void setFile(final File file) {
        this.setFile(file.getAbsolutePath());
    }
    
    @Override
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        this.m_pathField.setEnabled(enabled);
        this.m_browseButton.setEnabled(enabled);
    }
    
    public void addChangeListener(final ChangeListener listener) {
        this.m_listeners.add(ChangeListener.class, listener);
    }
    
    public void removeChangeListener(final ChangeListener listener) {
        this.m_listeners.remove(ChangeListener.class, listener);
    }
    
    public void firePathChange() {
        ChangeListener[] array;
        for (int length = (array = this.m_listeners.getListeners(ChangeListener.class)).length, i = 0; i < length; ++i) {
            final ChangeListener listener = array[i];
            listener.stateChanged(new ChangeEvent(this));
        }
    }
    
    @Override
    public void addKeyListener(final KeyListener listener) {
        this.m_pathField.addKeyListener(listener);
    }
    
    @Override
    public void requestFocus() {
        this.m_pathField.requestFocus();
    }
    
    private class BrowseAction extends AbstractAction
    {
        private static final long serialVersionUID = 1L;
        
        @Override
        public void actionPerformed(final ActionEvent event) {
            final JFileChooser chooser = new JFileChooser(FileField.this.getFile());
            if (FileField.this.m_onlyFolder) {
                chooser.setFileSelectionMode(1);
            }
            if (chooser.showOpenDialog(null) == 0) {
                FileField.this.m_pathField.setText(chooser.getSelectedFile().getAbsolutePath());
                FileField.this.firePathChange();
            }
        }
    }
    
    private class PathChangedObserver implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e) {
            FileField.this.firePathChange();
        }
    }
}
