// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.setupdialog;

import harmotab.desktop.modeleditor.HarmonicaModelEditor;
import java.awt.event.ActionEvent;
import harmotab.element.Tab;
import harmotab.harmonica.TabModelController;
import harmotab.desktop.ErrorMessenger;
import harmotab.harmonica.HarmonicaModel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import java.awt.Component;
import javax.swing.Box;
import harmotab.core.Height;
import javax.swing.Icon;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;
import java.awt.Window;
import harmotab.desktop.components.MappingReferenceChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import harmotab.desktop.components.HarmonicaModelChooser;
import harmotab.harmonica.TabModel;
import harmotab.harmonica.Harmonica;

public class TabModelWizard extends SetupDialog
{
    private static final long serialVersionUID = 1L;
    private Harmonica m_harmonica;
    private TabModel m_tabModel;
    private HarmonicaModelChooser m_harmonicaModelChooser;
    private JButton m_modelEditorButton;
    private JLabel m_harmonicaNameLabel;
    private JComboBox m_tonalityCombo;
    private MappingReferenceChooser m_mappingReferenceChooser;
    
    public TabModelWizard(final Window parent) {
        super(parent, Localizer.get("ET_TAB_MAPPING_WIZARD"));
        this.m_harmonica = null;
        this.m_tabModel = null;
        this.m_harmonicaModelChooser = null;
        this.m_modelEditorButton = null;
        this.m_harmonicaNameLabel = null;
        this.m_tonalityCombo = null;
        this.m_mappingReferenceChooser = null;
        this.m_harmonica = new Harmonica();
        this.m_tabModel = null;
        this.m_harmonicaModelChooser = new HarmonicaModelChooser();
        this.m_modelEditorButton = new JButton(Localizer.get("ET_OPEN_MODEL_EDITOR"), ActionIcon.getIcon((byte)5));
        this.m_tonalityCombo = new JComboBox((E[])Height.getNotesName());
        this.m_mappingReferenceChooser = new MappingReferenceChooser(this.m_harmonica);
        this.m_harmonicaNameLabel = new JLabel("<html><i>" + Localizer.get("ET_NO_HARMONICA_MODEL_LOADED") + "</i></html>");
        final Box buttonsBox = new Box(0);
        buttonsBox.add(this.m_harmonicaModelChooser);
        buttonsBox.add(Box.createHorizontalGlue());
        buttonsBox.add(this.m_modelEditorButton);
        final SetupCategory setupCategory = new SetupCategory(Localizer.get("ET_CREATE_TAB_MODEL"));
        this.addSetupCategory(setupCategory);
        final JPanel panel = setupCategory.getPanel();
        panel.add(this.createSetupSeparator(Localizer.get("N_HARMONICA_MODEL")));
        panel.add(this.getSetupField(null, new JLabel(), Localizer.get("M_HARMONICA_MODEL_DESC")));
        panel.add(this.createSetupField(Localizer.get("N_HARMONICA_MODEL_FILE"), buttonsBox));
        panel.add(this.createSetupField("", this.m_harmonicaNameLabel));
        panel.add(this.createSetupField(Localizer.get("N_TONALITY"), this.m_tonalityCombo));
        panel.add(this.createSetupSeparator(Localizer.get("N_TAB_NOTE_MAPPING")));
        panel.add(this.getSetupField(null, new JLabel(), Localizer.get("M_REFERENCE_DESC")));
        panel.add(this.createSetupField(Localizer.get("N_REFERENCE_NOTE"), this.m_mappingReferenceChooser));
        final UserActionObserver listener = new UserActionObserver();
        this.m_harmonicaModelChooser.addActionListener(listener);
        this.m_modelEditorButton.addActionListener(listener);
        this.m_tonalityCombo.addActionListener(listener);
        final HarmonicaModel defaultModel = this.m_harmonicaModelChooser.getSelectedModel();
        if (defaultModel != null) {
            this.m_harmonica.setModel(defaultModel);
            listener.fireModelUpdated();
        }
    }
    
    @Override
    protected boolean save() {
        if (this.m_tabModel == null) {
            this.m_tabModel = new TabModel();
        }
        final Height referenceHeight = this.m_mappingReferenceChooser.getSelectedHeight();
        final Tab referenceTab = this.m_mappingReferenceChooser.getSelectedTab();
        if (referenceHeight == null || referenceTab == null) {
            ErrorMessenger.showErrorMessage(this.getWindow(), Localizer.get("M_NO_REFERENCE_ERROR"));
            return false;
        }
        final TabModelController controller = new TabModelController(this.m_tabModel);
        controller.populateFromHarmonicaModel(this.m_harmonica, referenceHeight, referenceTab);
        return true;
    }
    
    @Override
    protected void discard() {
        this.m_tabModel = null;
        this.m_harmonica = null;
    }
    
    public void setTabModel(final TabModel model) {
        this.m_tabModel = model;
    }
    
    public TabModel getTabModel() {
        return this.m_tabModel;
    }
    
    public Harmonica getHarmonica() {
        return this.m_harmonica;
    }
    
    private class UserActionObserver implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent event) {
            if (event.getSource() == TabModelWizard.this.m_harmonicaModelChooser) {
                this.fireModelUpdated();
            }
            else if (event.getSource() == TabModelWizard.this.m_modelEditorButton) {
                final HarmonicaModel model = TabModelWizard.this.m_harmonica.getModel();
                final HarmonicaModelEditor editor = new HarmonicaModelEditor(null, model);
                editor.setVisible(true);
                TabModelWizard.this.m_harmonica.setName(TabModelWizard.this.m_harmonica.getModel().getName());
                TabModelWizard.this.m_harmonicaNameLabel.setText(String.valueOf(TabModelWizard.this.m_harmonica.getName()) + ", " + model.getNumberOfHoles() + " " + Localizer.get("N_HOLES") + ".");
                TabModelWizard.this.m_tonalityCombo.setSelectedItem(editor.getHarmonica().getTunning().getNoteName());
            }
            else if (event.getSource() == TabModelWizard.this.m_tonalityCombo) {
                TabModelWizard.this.m_harmonica.setTunning(new Height((String)TabModelWizard.this.m_tonalityCombo.getSelectedItem()));
            }
        }
        
        public void fireModelUpdated() {
            final HarmonicaModel model = TabModelWizard.this.m_harmonicaModelChooser.getSelectedModel();
            TabModelWizard.this.m_harmonica.setModel(model);
            TabModelWizard.this.m_harmonica.setName(model.getName());
            TabModelWizard.this.m_harmonicaNameLabel.setText(String.valueOf(model.getName()) + ", " + model.getNumberOfHoles() + " " + Localizer.get("N_HOLES") + ".");
        }
    }
}
