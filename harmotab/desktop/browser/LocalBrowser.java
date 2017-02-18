// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.browser;

import java.awt.event.ActionEvent;
import harmotab.desktop.actions.OpenScoreAction;
import java.util.Iterator;
import java.io.File;
import java.util.List;
import java.util.Collections;
import java.io.FileFilter;
import harmotab.io.score.ScoreIOUtilities;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.KeyEvent;
import javax.swing.event.ChangeEvent;
import java.awt.Cursor;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import harmotab.core.Localizer;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.ListModel;
import javax.swing.DefaultListModel;
import harmotab.core.GlobalPreferences;
import harmotab.core.ScoreController;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import rvt.util.gui.FileField;

public class LocalBrowser extends Browser
{
    private static final long serialVersionUID = 1L;
    private FileField m_fileField;
    private JScrollPane m_listScrollPane;
    private JList m_scoreList;
    private JPanel m_noScorePane;
    private String m_currentPath;
    
    public LocalBrowser(final ScoreController controller) {
        this.m_fileField = null;
        this.m_listScrollPane = null;
        this.m_scoreList = null;
        this.m_noScorePane = null;
        this.m_currentPath = null;
        this.m_currentPath = GlobalPreferences.getScoresBrowsingFolder();
        this.m_fileField = new FileField((String)null, true);
        this.m_scoreList = new JList((ListModel<E>)new DefaultListModel<Object>());
        (this.m_listScrollPane = new JScrollPane(this.m_scoreList)).setOpaque(false);
        this.m_listScrollPane.getViewport().setOpaque(false);
        (this.m_noScorePane = new JPanel(new BorderLayout())).add(new JLabel("<html><i>" + Localizer.get("ET_NO_SCORE_IN_FOLDER") + "</i></html>", 0), "Center");
        this.m_noScorePane.setOpaque(false);
        this.setLayout(new BorderLayout(5, 5));
        this.add(this.m_fileField, "North");
        this.add(this.m_listScrollPane, "Center");
        this.m_fileField.addChangeListener(new PathValidationObserver());
        this.m_scoreList.addListSelectionListener(new ScoreSelectionObserver());
        this.m_fileField.addKeyListener(new PathChangeObserver());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setFolder(this.m_currentPath);
    }
    
    public void setFolder(final String folder) {
        this.m_fileField.setFile(folder);
        this.setWaitMode(true);
        new FileListUpdater().start();
    }
    
    private void setWaitMode(final boolean wait) {
        if (wait) {
            this.m_fileField.setEnabled(false);
            this.m_scoreList.setEnabled(false);
            this.setCursor(Cursor.getPredefinedCursor(3));
        }
        else {
            this.m_fileField.setEnabled(true);
            this.m_scoreList.setEnabled(true);
            this.setCursor(Cursor.getPredefinedCursor(0));
        }
    }
    
    static /* synthetic */ void access$3(final LocalBrowser localBrowser, final String currentPath) {
        localBrowser.m_currentPath = currentPath;
    }
    
    private class PathValidationObserver implements ChangeListener
    {
        @Override
        public void stateChanged(final ChangeEvent event) {
            LocalBrowser.this.setWaitMode(true);
            new FileListUpdater().start();
        }
    }
    
    private class PathChangeObserver implements KeyListener
    {
        @Override
        public void keyReleased(final KeyEvent event) {
            if (LocalBrowser.this.m_fileField.getFile().isDirectory()) {
                LocalBrowser.this.setWaitMode(true);
                new FileListUpdater().start();
            }
        }
        
        @Override
        public void keyTyped(final KeyEvent event) {
        }
        
        @Override
        public void keyPressed(final KeyEvent event) {
        }
    }
    
    private class ScoreSelectionObserver implements ListSelectionListener
    {
        @Override
        public void valueChanged(final ListSelectionEvent event) {
            if (!event.getValueIsAdjusting() && LocalBrowser.this.m_scoreList.getSelectedValue() != null) {
                LocalBrowser.this.setWaitMode(true);
                new ScoreLoader().start();
            }
        }
    }
    
    private class FileListUpdater extends Thread
    {
        @Override
        public void run() {
            final ArrayList<String> files = new ArrayList<String>();
            final DefaultListModel model = (DefaultListModel)LocalBrowser.this.m_scoreList.getModel();
            model.clear();
            final File folder = LocalBrowser.this.m_fileField.getFile();
            LocalBrowser.access$3(LocalBrowser.this, folder.getAbsolutePath());
            if (folder != null && folder.isDirectory()) {
                File[] listFiles;
                for (int length = (listFiles = folder.listFiles(new ScoreIOUtilities.ReadableScoreFileFilter())).length, i = 0; i < length; ++i) {
                    final File file = listFiles[i];
                    files.add(file.getName());
                }
            }
            if (files.size() > 0) {
                Collections.sort(files);
                for (final String file2 : files) {
                    model.addElement(file2);
                }
                LocalBrowser.this.m_listScrollPane.setViewportView(LocalBrowser.this.m_scoreList);
                GlobalPreferences.setScoresBrowsingFolder(folder.getAbsolutePath());
            }
            else {
                LocalBrowser.this.m_listScrollPane.setViewportView(LocalBrowser.this.m_noScorePane);
            }
            LocalBrowser.this.setWaitMode(false);
            LocalBrowser.this.m_listScrollPane.repaint();
            LocalBrowser.this.m_scoreList.repaint();
            LocalBrowser.this.m_fileField.requestFocus();
        }
    }
    
    private class ScoreLoader extends Thread
    {
        @Override
        public void run() {
            final String fileName = LocalBrowser.this.m_scoreList.getSelectedValue();
            if (fileName != null) {
                final String filePath = String.valueOf(LocalBrowser.this.m_currentPath) + File.separator + fileName;
                final OpenScoreAction action = new OpenScoreAction(filePath);
                action.actionPerformed(new ActionEvent(LocalBrowser.this, 0, ""));
            }
            LocalBrowser.this.setWaitMode(false);
        }
    }
}
