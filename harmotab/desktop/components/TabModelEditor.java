// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import harmotab.renderer.renderingelements.HarmonicaProperties;
import java.awt.Graphics2D;
import harmotab.renderer.AwtPrintingElementRendererBundle;
import harmotab.core.HarmoTabObjectEvent;
import java.awt.Point;
import javax.swing.JPopupMenu;
import harmotab.core.Localizer;
import harmotab.desktop.tools.ToolIcon;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import rvt.util.gui.VerticalLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import java.util.Iterator;
import harmotab.renderer.LocationList;
import java.util.ConcurrentModificationException;
import javax.swing.SwingUtilities;
import harmotab.renderer.LocationItem;
import harmotab.element.HarmoTabElement;
import harmotab.core.Figure;
import harmotab.element.Tab;
import harmotab.core.Height;
import harmotab.element.Element;
import harmotab.element.TimeSignature;
import harmotab.element.Bar;
import harmotab.core.RepeatAttribute;
import harmotab.element.KeySignature;
import harmotab.element.Key;
import java.awt.Dimension;
import java.awt.Cursor;
import javax.swing.Action;
import harmotab.core.HarmoTabObjectListener;
import java.awt.event.ActionListener;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Component;
import harmotab.renderer.ElementRendererBundle;
import harmotab.track.HarmoTabTrack;
import harmotab.core.Score;
import javax.swing.JScrollPane;
import harmotab.harmonica.TabModel;
import harmotab.track.Track;
import javax.swing.JPanel;

public class TabModelEditor extends JPanel
{
    private static final long serialVersionUID = 1L;
    private Track m_track;
    private TabModel m_tabModel;
    private TrackPane m_staffPane;
    private JScrollPane m_scrollPane;
    private boolean m_allowChromaButton;
    private boolean m_modelChanged;
    
    public TabModelEditor(final TabModel tabModel, final boolean showChromaButton) {
        this.m_track = null;
        this.m_tabModel = null;
        this.m_staffPane = null;
        this.m_scrollPane = null;
        this.m_allowChromaButton = false;
        this.m_modelChanged = false;
        this.m_tabModel = tabModel;
        this.m_allowChromaButton = showChromaButton;
        this.m_modelChanged = false;
        final Score voidScore = new Score();
        this.m_track = new HarmoTabTrack(voidScore);
        this.resetTrack();
        this.m_staffPane = new TrackPane(this.m_track, new CustomElementRenderer());
        this.m_scrollPane = new JScrollPane(this.m_staffPane);
        this.setLayout(new BorderLayout());
        this.add(this.m_scrollPane, "Center");
        this.m_staffPane.addActionListener(new HeightChoiceAction());
        this.m_tabModel.addObjectListener(new TabModelObserver());
        this.m_staffPane.setPopupTriggerAction(new TabPopupMenuAction());
        this.setCursor(new Cursor(12));
        this.setPreferredSize(new Dimension(150, 200));
    }
    
    private void resetTrack() {
        this.m_track.clear();
        this.m_track.add(new Bar(new Key(), new KeySignature(), new CustomTimeSignature(), new RepeatAttribute()));
        for (int i = 36; i < 84; ++i) {
            final Height height = new Height(i);
            Tab tab = this.m_tabModel.getTab(height);
            if (tab == null) {
                tab = new Tab();
            }
            final HarmoTabElement element = new HarmoTabElement(height, new Figure());
            this.m_track.add(element);
            element.setTab(tab);
        }
    }
    
    public TabModel getTabModel() {
        return this.m_tabModel;
    }
    
    public boolean hasTabModelChanged() {
        return this.m_modelChanged;
    }
    
    public void goTo(final Height height) {
        final LocationList locations = this.m_staffPane.getCurrentLocations();
        if (locations == null || locations.getSize() == 0) {
            System.err.println("TabModelEditor:goTo: No locations computed.");
            return;
        }
        try {
            for (final LocationItem item : locations) {
                final Element element = item.getElement();
                if (element instanceof HarmoTabElement) {
                    final HarmoTabElement htElement = (HarmoTabElement)element;
                    if (!htElement.getHeight().equals(height)) {
                        continue;
                    }
                    this.m_staffPane.setSelectedItem(item);
                    SwingUtilities.invokeLater(new ScrollLaterWoker(item.getX1() - 100));
                }
            }
        }
        catch (ConcurrentModificationException ex) {}
    }
    
    static /* synthetic */ void access$4(final TabModelEditor tabModelEditor, final boolean modelChanged) {
        tabModelEditor.m_modelChanged = modelChanged;
    }
    
    private class ScrollLaterWoker implements Runnable
    {
        int m_scrollXRequested;
        
        public ScrollLaterWoker(final int x) {
            this.m_scrollXRequested = x;
        }
        
        @Override
        public void run() {
            TabModelEditor.this.m_scrollPane.getHorizontalScrollBar().setValue(this.m_scrollXRequested);
        }
    }
    
    private class HeightChoiceAction extends AbstractAction
    {
        private static final long serialVersionUID = 1L;
        
        @Override
        public void actionPerformed(final ActionEvent event) {
            final Element selected = TabModelEditor.this.m_staffPane.getSelectedItem();
            if (selected != null && selected instanceof HarmoTabElement) {
                final HarmoTabElement selectedHt = (HarmoTabElement)selected;
                final TabChooser chooser = new TabChooser(selectedHt.getTab(), TabModelEditor.this.m_allowChromaButton);
                final JPanel panel = new JPanel(new VerticalLayout(10, 10));
                panel.add(new JLabel("Veuillez choisir la tablature correspondante \ufffd la note s\ufffdlectionn\ufffde :"));
                panel.add(chooser);
                final int res = JOptionPane.showConfirmDialog(TabModelEditor.this, panel, "Tab model editor", 2);
                if (res == 0) {
                    final Tab tab = chooser.getTab();
                    selectedHt.setTab(tab);
                    TabModelEditor.this.m_tabModel.setTab(selectedHt.getHeight(), tab);
                    TabModelEditor.access$4(TabModelEditor.this, true);
                }
            }
        }
    }
    
    private class DeleteTabAction extends AbstractAction
    {
        private static final long serialVersionUID = 1L;
        
        public DeleteTabAction() {
            this.putValue("SmallIcon", ToolIcon.getIcon((byte)15));
            this.putValue("Name", Localizer.get("MENU_DELETE_TAB"));
        }
        
        @Override
        public void actionPerformed(final ActionEvent event) {
            final Element selected = TabModelEditor.this.m_staffPane.getSelectedItem();
            if (selected != null && selected instanceof HarmoTabElement) {
                final HarmoTabElement selectedHt = (HarmoTabElement)selected;
                selectedHt.setTab(new Tab());
                TabModelEditor.this.m_tabModel.deleteTab(selectedHt.getHeight());
                TabModelEditor.access$4(TabModelEditor.this, true);
            }
        }
    }
    
    private class TabPopupMenuAction extends AbstractAction
    {
        private static final long serialVersionUID = 1L;
        
        public void showPopupMenu(final int x, final int y) {
            final JPopupMenu menu = new JPopupMenu();
            menu.add(new DeleteTabAction());
            menu.show(TabModelEditor.this, x, y);
        }
        
        @Override
        public void actionPerformed(final ActionEvent event) {
            final Point mousePosition = TabModelEditor.this.getMousePosition();
            if (mousePosition != null) {
                this.showPopupMenu((int)mousePosition.getX(), (int)mousePosition.getY());
            }
        }
    }
    
    private class TabModelObserver implements HarmoTabObjectListener
    {
        @Override
        public void onObjectChanged(final HarmoTabObjectEvent event) {
            TabModelEditor.this.resetTrack();
        }
    }
    
    private class CustomTimeSignature extends TimeSignature
    {
        @Override
        public float getTimesPerBar() {
            return Float.MAX_VALUE;
        }
    }
    
    private class CustomElementRenderer extends AwtPrintingElementRendererBundle
    {
        @Override
        protected void paintTimeSignature(final Graphics2D g, final TimeSignature ts, final LocationItem l) {
        }
        
        @Override
        protected void paintHarmonicaProperties(final Graphics2D g, final HarmonicaProperties harmoProps, final LocationItem item) {
        }
    }
}
