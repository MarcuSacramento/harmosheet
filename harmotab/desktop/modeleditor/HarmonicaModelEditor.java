// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.modeleditor;

import javax.swing.event.DocumentEvent;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeEvent;
import harmotab.core.HarmoTabObjectEvent;
import java.awt.event.WindowEvent;
import harmotab.core.HarmoTabObjectListener;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeListener;
import java.awt.event.WindowListener;
import java.awt.Component;
import javax.swing.JLabel;
import rvt.util.gui.GridBagUtilities;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import harmotab.core.Height;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import harmotab.desktop.GuiIcon;
import java.awt.Dialog;
import harmotab.core.Localizer;
import harmotab.harmonica.HarmonicaModel;
import java.awt.Window;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JComboBox;
import harmotab.desktop.components.HarmonicaTypeChooser;
import harmotab.desktop.components.NumberOfHolesChooser;
import javax.swing.JTextField;
import harmotab.harmonica.HarmonicaModelController;
import harmotab.harmonica.Harmonica;
import javax.swing.JDialog;

public class HarmonicaModelEditor extends JDialog
{
    private static final long serialVersionUID = 1L;
    private Harmonica m_harmonica;
    private HarmonicaModelController m_modelController;
    private HarmonicaChangeListener m_harmonicaListener;
    private JTextField m_nameTextField;
    private NumberOfHolesChooser m_numberOfHolesChooser;
    private HarmonicaTypeChooser m_harmonicaTypeChooser;
    private JComboBox m_tunningCombo;
    private JToolBar m_toolBar;
    private HarmonicaModelPane m_modelPane;
    private JButton m_closeButton;
    
    public HarmonicaModelEditor(final Window parent, final HarmonicaModel model) {
        super(parent, Localizer.get("ET_HARMONICA_MODEL"));
        this.m_harmonica = null;
        this.m_modelController = null;
        this.m_harmonicaListener = null;
        this.m_nameTextField = null;
        this.m_numberOfHolesChooser = null;
        this.m_harmonicaTypeChooser = null;
        this.m_tunningCombo = null;
        this.m_toolBar = null;
        this.m_modelPane = null;
        this.m_closeButton = null;
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setIconImage(GuiIcon.getIcon((byte)9).getImage());
        this.setDefaultCloseOperation(0);
        this.m_harmonica = new Harmonica(model);
        this.m_modelController = new HarmonicaModelController(model);
        (this.m_modelPane = new HarmonicaModelPane(this.m_harmonica)).setBorder(new EmptyBorder(20, 20, 10, 20));
        this.m_nameTextField = new JTextField(model.getName(), 0);
        this.m_harmonicaTypeChooser = new HarmonicaTypeChooser(model.getHarmonicaType());
        this.m_numberOfHolesChooser = new NumberOfHolesChooser(model.getNumberOfHoles());
        this.m_tunningCombo = new JComboBox((E[])Height.getNotesName());
        this.m_toolBar = new HarmonicaModelEditorToolbar(this.m_modelController);
        this.m_closeButton = new JButton(Localizer.get("ET_CLOSE_DIALOG"));
        final JPanel propertiesPane = new JPanel();
        propertiesPane.setLayout(new GridBagLayout());
        propertiesPane.setBorder(new EmptyBorder(10, 20, 20, 20));
        GridBagUtilities.setDefaultPadding(new Insets(5, 5, 5, 5), 0, 0);
        GridBagUtilities.setDefaultPositionning(13, 0);
        propertiesPane.add(new JLabel(Localizer.get("ET_TUNNING_NAME")), GridBagUtilities.getConstraints(0, 0));
        propertiesPane.add(new JLabel(Localizer.get("ET_NUMBER_OF_HOLES")), GridBagUtilities.getConstraints(0, 1));
        propertiesPane.add(new JLabel(Localizer.get("ET_TUNNING")), GridBagUtilities.getConstraints(3, 1));
        propertiesPane.add(new JLabel(" "), GridBagUtilities.getConstraints(2, 1, 1.0, 0.0));
        GridBagUtilities.setDefaultPositionning(17, 1);
        propertiesPane.add(this.m_nameTextField, GridBagUtilities.getConstraints(1, 0, 4, 1, 1.0, 0.0));
        propertiesPane.add(this.m_numberOfHolesChooser, GridBagUtilities.getConstraints(1, 1));
        propertiesPane.add(this.m_harmonicaTypeChooser, GridBagUtilities.getConstraints(2, 1));
        propertiesPane.add(this.m_tunningCombo, GridBagUtilities.getConstraints(4, 1));
        propertiesPane.add(this.m_closeButton, GridBagUtilities.getConstraints(3, 2, 2, 1));
        this.add(this.m_toolBar, "North");
        this.add(this.m_modelPane, "Center");
        this.add(propertiesPane, "South");
        this.addWindowListener(new WindowObserver());
        final UserActionOberver userActionObserver = new UserActionOberver();
        this.m_numberOfHolesChooser.addChangeListener(userActionObserver);
        this.m_harmonicaTypeChooser.addActionListener(userActionObserver);
        this.m_tunningCombo.addActionListener(userActionObserver);
        this.m_closeButton.addActionListener(userActionObserver);
        this.m_nameTextField.getDocument().addDocumentListener(userActionObserver);
        this.m_harmonicaListener = new HarmonicaChangeListener();
        this.m_harmonica.addObjectListener(this.m_harmonicaListener);
        this.setSize(550, 400);
        this.setLocationRelativeTo(parent);
    }
    
    public void finalize() {
        this.m_harmonica.removeObjectListener(this.m_harmonicaListener);
    }
    
    public Harmonica getHarmonica() {
        return this.m_harmonica;
    }
    
    private class WindowObserver implements WindowListener
    {
        @Override
        public void windowClosing(final WindowEvent event) {
            if (HarmonicaModelEditor.this.m_modelController.close()) {
                HarmonicaModelEditor.this.dispose();
            }
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
    }
    
    private class HarmonicaChangeListener implements HarmoTabObjectListener
    {
        @Override
        public void onObjectChanged(final HarmoTabObjectEvent event) {
            final HarmonicaModel model = HarmonicaModelEditor.this.m_harmonica.getModel();
            if (model.getNumberOfHoles() != (int)HarmonicaModelEditor.this.m_numberOfHolesChooser.getValue()) {
                HarmonicaModelEditor.this.m_numberOfHolesChooser.setValue(model.getNumberOfHoles());
            }
            if (!model.getName().equals(HarmonicaModelEditor.this.m_nameTextField.getText())) {
                HarmonicaModelEditor.this.m_nameTextField.setText(model.getName());
            }
            if (model.getHarmonicaType() != HarmonicaModelEditor.this.m_harmonicaTypeChooser.getSelectedHarmonicaType()) {
                HarmonicaModelEditor.this.m_harmonicaTypeChooser.setSelectedHarmonicaType(model.getHarmonicaType());
            }
        }
    }
    
    private class UserActionOberver implements ChangeListener, ActionListener, DocumentListener
    {
        @Override
        public void stateChanged(final ChangeEvent event) {
            if (event.getSource() == HarmonicaModelEditor.this.m_numberOfHolesChooser) {
                final int numberOfHoles = (int)HarmonicaModelEditor.this.m_numberOfHolesChooser.getValue();
                if (numberOfHoles != HarmonicaModelEditor.this.m_harmonica.getModel().getNumberOfHoles()) {
                    HarmonicaModelEditor.this.m_harmonica.getModel().setNumberOfHoles(numberOfHoles);
                }
            }
        }
        
        @Override
        public void actionPerformed(final ActionEvent event) {
            if (event.getSource() == HarmonicaModelEditor.this.m_tunningCombo) {
                HarmonicaModelEditor.this.m_harmonica.setTunning(new Height((String)HarmonicaModelEditor.this.m_tunningCombo.getSelectedItem()));
            }
            else if (event.getSource() == HarmonicaModelEditor.this.m_harmonicaTypeChooser) {
                HarmonicaModelEditor.this.m_harmonica.getModel().setHarmonicaType(HarmonicaModelEditor.this.m_harmonicaTypeChooser.getSelectedHarmonicaType());
            }
            else if (event.getSource() == HarmonicaModelEditor.this.m_closeButton && HarmonicaModelEditor.this.m_modelController.close()) {
                HarmonicaModelEditor.this.dispose();
            }
        }
        
        private void modelNameChanged() {
            HarmonicaModelEditor.this.m_harmonica.getModel().setName(HarmonicaModelEditor.this.m_nameTextField.getText());
        }
        
        @Override
        public void changedUpdate(final DocumentEvent event) {
            this.modelNameChanged();
        }
        
        @Override
        public void insertUpdate(final DocumentEvent event) {
            this.modelNameChanged();
        }
        
        @Override
        public void removeUpdate(final DocumentEvent event) {
            this.modelNameChanged();
        }
    }
}
