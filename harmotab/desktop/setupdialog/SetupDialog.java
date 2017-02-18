// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.setupdialog;

import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;
import java.awt.event.WindowEvent;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import java.awt.Dialog;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Component;
import harmotab.desktop.actions.UserAction;
import harmotab.desktop.actions.ActionButton;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import java.awt.GridLayout;
import harmotab.desktop.DesktopController;
import java.awt.Window;
import java.util.Vector;
import javax.swing.JTabbedPane;
import java.awt.event.WindowListener;
import javax.swing.JDialog;

public abstract class SetupDialog extends JDialog implements WindowListener
{
    private static final long serialVersionUID = 1L;
    private JTabbedPane m_tabbedPane;
    private Vector<SetupCategory> m_categories;
    
    public SetupDialog(final Window parent, final String title) {
        super((parent != null) ? parent : DesktopController.getInstance().getGuiWindow(), title);
        this.m_tabbedPane = null;
        this.m_categories = null;
        this.setDefaultCloseOperation(2);
        this.m_categories = new Vector<SetupCategory>();
        this.m_tabbedPane = new JTabbedPane();
        final JPanel dialogButtonsPane = new JPanel(new GridLayout(1, 2, 5, 5));
        dialogButtonsPane.add(new ActionButton(new CancelAction()));
        dialogButtonsPane.add(new ActionButton(new OkAction()));
        final JPanel bottomPane = new JPanel(new BorderLayout());
        bottomPane.add(dialogButtonsPane, "East");
        final JPanel contentPane = (JPanel)this.getContentPane();
        contentPane.setLayout(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.add(this.m_tabbedPane, "Center");
        contentPane.add(bottomPane, "South");
        this.addWindowListener(this);
        this.setSize(600, 600);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setLocationRelativeTo(this.getParent());
    }
    
    protected abstract boolean save();
    
    protected abstract void discard();
    
    protected Window getWindow() {
        return this;
    }
    
    public void setSelectedTab(final int index) {
        this.m_tabbedPane.setSelectedIndex(index);
    }
    
    protected void displayCategory(final SetupCategory setupCategory) {
        this.m_tabbedPane.setSelectedIndex(this.m_categories.indexOf(setupCategory));
    }
    
    protected void addSetupCategory(final SetupCategory setupCategory) {
        this.m_categories.add(setupCategory);
        final JScrollPane scrollPane = new JScrollPane(setupCategory.getPanel());
        scrollPane.setHorizontalScrollBarPolicy(31);
        this.m_tabbedPane.addTab(setupCategory.getTitle(), scrollPane);
    }
    
    protected Component getSetupField(final String title, final JComponent component, final String help) {
        final JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setOpaque(false);
        if (title != null) {
            final JLabel label = new JLabel(String.valueOf(title) + ((title.length() > 0) ? " : " : ""));
            label.setPreferredSize(new Dimension(120, 20));
            label.setOpaque(false);
            fieldPanel.add(label, "West");
        }
        fieldPanel.add(component, "Center");
        if (help != null) {
            final JTextArea helpLabel = new JTextArea();
            helpLabel.setLineWrap(true);
            helpLabel.setText(help);
            helpLabel.setFont(new JTextField().getFont());
            helpLabel.setOpaque(false);
            helpLabel.setEditable(false);
            helpLabel.setForeground(Color.GRAY);
            helpLabel.setWrapStyleWord(true);
            helpLabel.setBorder(new EmptyBorder(5, 0, 0, 0));
            final JPanel centerPane = new JPanel(new BorderLayout());
            centerPane.setOpaque(false);
            centerPane.add(component, "Center");
            centerPane.add(helpLabel, "South");
            fieldPanel.add(centerPane, "Center");
        }
        return fieldPanel;
    }
    
    protected Component createSetupField(final String title, final JComponent component) {
        return this.getSetupField(title, component, null);
    }
    
    protected Component createSetupSeparator(final String title) {
        final JPanel separatorPane = new JPanel(new BorderLayout());
        final JLabel titleLabel = new JLabel(title);
        titleLabel.setPreferredSize(new Dimension(200, 25));
        titleLabel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        titleLabel.setFont(titleLabel.getFont().deriveFont(1));
        titleLabel.setForeground(new Color(6456255));
        titleLabel.setOpaque(false);
        separatorPane.add(titleLabel, "Center");
        separatorPane.setBorder(new EmptyBorder(8, 0, 5, 0));
        separatorPane.setOpaque(false);
        return separatorPane;
    }
    
    @Override
    public void windowClosing(final WindowEvent event) {
        this.save();
    }
    
    @Override
    public void windowActivated(final WindowEvent event) {
    }
    
    @Override
    public void windowClosed(final WindowEvent event) {
    }
    
    @Override
    public void windowDeactivated(final WindowEvent event) {
    }
    
    @Override
    public void windowDeiconified(final WindowEvent event) {
    }
    
    @Override
    public void windowIconified(final WindowEvent event) {
    }
    
    @Override
    public void windowOpened(final WindowEvent event) {
    }
    
    private class OkAction extends UserAction
    {
        private static final long serialVersionUID = 1L;
        
        public OkAction() {
            super(Localizer.get("ET_OK"), ActionIcon.getIcon((byte)36));
        }
        
        @Override
        public void run() {
            if (SetupDialog.this.save()) {
                SetupDialog.this.dispose();
            }
        }
    }
    
    private class CancelAction extends UserAction
    {
        private static final long serialVersionUID = 1L;
        
        public CancelAction() {
            super(Localizer.get("ET_CANCEL"), ActionIcon.getIcon((byte)37));
        }
        
        @Override
        public void run() {
            SetupDialog.this.discard();
            SetupDialog.this.dispose();
        }
    }
}
