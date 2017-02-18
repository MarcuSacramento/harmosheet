// 
// Decompiled by Procyon v0.5.30
// 

package ie.debug;

import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager;
import harmotab.core.GlobalPreferences;
import java.awt.Container;
import harmotab.desktop.tools.AccompanimentTool;
import harmotab.element.Element;
import harmotab.renderer.LocationItem;
import harmotab.element.Accompaniment;
import harmotab.core.Score;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.LayoutManager;
import harmotab.desktop.SmoothLayout;
import harmotab.desktop.tools.Tool;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

public class ToolBoxTest extends JFrame implements MouseListener
{
    private static final long serialVersionUID = 1L;
    private Tool visibleTool;
    
    public ToolBoxTest() {
        super("Test");
        this.visibleTool = null;
        this.setDefaultCloseOperation(3);
        this.getContentPane().setLayout(new SmoothLayout());
        this.getContentPane().setBackground(Color.WHITE);
        this.addMouseListener(this);
        this.setSize(300, 150);
        this.setVisible(true);
    }
    
    @Override
    public void mouseClicked(final MouseEvent event) {
        if (this.visibleTool == null) {
            final Score score = new Score();
            final LocationItem item = new LocationItem(new Accompaniment(), 10, 10, 50, 50, 10, 10, 0, 0, 0.0f, 0);
            (this.visibleTool = new AccompanimentTool(this, score, item)).setVisible(true);
        }
        else {
            this.visibleTool.setVisible(false);
            this.visibleTool = null;
        }
    }
    
    @Override
    public void mouseEntered(final MouseEvent event) {
    }
    
    @Override
    public void mouseExited(final MouseEvent event) {
    }
    
    @Override
    public void mousePressed(final MouseEvent event) {
    }
    
    @Override
    public void mouseReleased(final MouseEvent event) {
    }
    
    public static void setLookAndFeel() {
        try {
            if (GlobalPreferences.useSystemAppearance()) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            else {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }
        }
        catch (UnsupportedLookAndFeelException ex) {}
        catch (ClassNotFoundException ex2) {}
        catch (InstantiationException ex3) {}
        catch (IllegalAccessException ex4) {}
    }
    
    public static void main(final String[] args) {
        new ToolBoxTest();
    }
}
