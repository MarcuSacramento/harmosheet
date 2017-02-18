// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.setupdialog;

import harmotab.desktop.RecentFilesManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import harmotab.core.Localizer;
import harmotab.core.GlobalPreferences;
import java.awt.Window;
import harmotab.desktop.components.TabBlowDirectionChooser;
import harmotab.desktop.components.TabStyleChooser;
import rvt.util.gui.FileField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import harmotab.desktop.components.LanguageChooser;

public class PreferencesSetupDialog extends SetupDialog
{
    private static final long serialVersionUID = 1L;
    private LanguageChooser m_languageCombo;
    private JCheckBox m_systemAppearanceBox;
    private JButton m_restoreDefaultsButton;
    private FileField m_scoresFolder;
    private FileField m_modelsFolder;
    private JCheckBox m_autoTabBox;
    private JCheckBox m_autoCompleteModelBox;
    private JCheckBox m_displayBarNumbersBox;
    private JCheckBox m_displayEditingHelpersBox;
    private TabStyleChooser m_tabStyleChooser;
    private TabBlowDirectionChooser m_tabBlowDirectionChooser;
    private JCheckBox m_networkEnabledBox;
    private JCheckBox m_enablePerformancesFeatures;
    private JCheckBox m_enableMetronomeFeature;
    private JCheckBox m_countDownCheckBox;
    
    public PreferencesSetupDialog(final Window parent) {
        super(parent, "Software preferences");
        this.m_languageCombo = null;
        this.m_systemAppearanceBox = null;
        this.m_restoreDefaultsButton = null;
        this.m_scoresFolder = null;
        this.m_modelsFolder = null;
        this.m_autoTabBox = null;
        this.m_autoCompleteModelBox = null;
        this.m_displayBarNumbersBox = null;
        this.m_displayEditingHelpersBox = null;
        this.m_tabStyleChooser = null;
        this.m_tabBlowDirectionChooser = null;
        this.m_networkEnabledBox = null;
        this.m_enablePerformancesFeatures = null;
        this.m_enableMetronomeFeature = null;
        this.m_countDownCheckBox = null;
        this.m_languageCombo = new LanguageChooser(GlobalPreferences.getLanguage());
        (this.m_systemAppearanceBox = new JCheckBox(Localizer.get("ET_USE_SYSTEM_APPEARANCE"))).setSelected(GlobalPreferences.useSystemAppearance());
        this.m_restoreDefaultsButton = new JButton(Localizer.get("ET_RESTORE_BUTTON"));
        this.m_systemAppearanceBox.setOpaque(false);
        (this.m_autoTabBox = new JCheckBox(Localizer.get("ET_AUTO_TAB"))).setSelected(GlobalPreferences.isAutoTabEnabled());
        this.m_autoTabBox.setOpaque(false);
        (this.m_autoCompleteModelBox = new JCheckBox(Localizer.get("ET_AUTOMATICS_MODEL_COMPLETION"))).setSelected(GlobalPreferences.isTabMappingCompletionEnabled());
        this.m_autoCompleteModelBox.setOpaque(false);
        (this.m_displayBarNumbersBox = new JCheckBox(Localizer.get("ET_DISPLAY_BAR_NUMBERS"))).setSelected(GlobalPreferences.isBarNumbersDisplayed());
        this.m_displayBarNumbersBox.setOpaque(false);
        (this.m_displayEditingHelpersBox = new JCheckBox(Localizer.get("ET_DISPLAY_EDITING_HELPERS"))).setSelected(GlobalPreferences.isEditingHelpersDisplayed());
        this.m_displayEditingHelpersBox.setOpaque(false);
        this.m_tabStyleChooser = new TabStyleChooser(GlobalPreferences.getTabStyle());
        this.m_tabBlowDirectionChooser = new TabBlowDirectionChooser(GlobalPreferences.getTabBlowDirection());
        this.m_scoresFolder = new FileField(GlobalPreferences.getScoresBrowsingFolder(), true);
        this.m_modelsFolder = new FileField(GlobalPreferences.getModelsFolder(), true);
        (this.m_networkEnabledBox = new JCheckBox(Localizer.get("ET_NETWORK_ENABLED"))).setSelected(GlobalPreferences.isNetworkEnabled());
        this.m_networkEnabledBox.setOpaque(false);
        (this.m_enablePerformancesFeatures = new JCheckBox(Localizer.get("ET_PERFORMANCES_FEATURE"), GlobalPreferences.getPerformancesFeatureEnabled())).setOpaque(false);
        (this.m_enableMetronomeFeature = new JCheckBox(Localizer.get("ET_METRONOME_FEATURE"), GlobalPreferences.getMetronomeFeatureEnabled())).setOpaque(false);
        (this.m_countDownCheckBox = new JCheckBox(Localizer.get("ET_COUNTDOWN_FEATURE"), GlobalPreferences.getPlaybackCountdownEnabled())).setOpaque(false);
        final SetupCategory globalSetupCategory = new SetupCategory(Localizer.get("ET_GLOBAL"));
        final JPanel globalSetupPane = globalSetupCategory.getPanel();
        globalSetupPane.add(this.createSetupSeparator(Localizer.get("ET_LOCALIZATION")));
        globalSetupPane.add(this.getSetupField(null, this.m_languageCombo, Localizer.get("M_LANGUAGE_DESC")));
        globalSetupPane.add(this.createSetupSeparator(Localizer.get("ET_APPEARANCE")));
        globalSetupPane.add(this.getSetupField(null, this.m_systemAppearanceBox, Localizer.get("M_SYSTEM_APPEARANCE_DESC")));
        globalSetupPane.add(this.createSetupSeparator(Localizer.get("ET_RESTORE")));
        globalSetupPane.add(this.getSetupField(null, this.m_restoreDefaultsButton, Localizer.get("M_RESTORE_DEFAULTS_DESC")));
        final SetupCategory foldersSetupCategory = new SetupCategory(Localizer.get("ET_FOLDERS"));
        final JPanel foldersSetupPane = foldersSetupCategory.getPanel();
        foldersSetupPane.add(this.createSetupSeparator(Localizer.get("ET_SCORE_FOLDERS")));
        foldersSetupPane.add(this.getSetupField(null, this.m_scoresFolder, Localizer.get("M_SCORE_FOLDERS_DESC")));
        foldersSetupPane.add(this.createSetupSeparator(Localizer.get("ET_MODELS_FOLDER")));
        foldersSetupPane.add(this.getSetupField(null, this.m_modelsFolder, Localizer.get("M_MODELS_FOLDER_DESC")));
        final SetupCategory editorSetupCategory = new SetupCategory(Localizer.get("ET_EDITOR_SETUP_CATEGORY"));
        final JPanel editorSetupPane = editorSetupCategory.getPanel();
        editorSetupPane.add(this.createSetupSeparator(Localizer.get("ET_SCORE_EDITOR")));
        editorSetupPane.add(this.getSetupField(null, this.m_autoTabBox, Localizer.get("M_AUTO_TAB_DESC")));
        editorSetupPane.add(this.getSetupField(null, this.m_autoCompleteModelBox, Localizer.get("M_MODEL_AUTO_COMPLETION_DESC")));
        editorSetupPane.add(this.createSetupSeparator(Localizer.get("ET_SCORE_DRAWING")));
        editorSetupPane.add(this.getSetupField(null, this.m_displayBarNumbersBox, Localizer.get("M_BAR_NUMBERS_DESC")));
        editorSetupPane.add(this.getSetupField(null, this.m_displayEditingHelpersBox, Localizer.get("M_EDITING_HELPERS_DESC")));
        editorSetupPane.add(this.createSetupSeparator(Localizer.get("ET_TAB_STYLE")));
        editorSetupPane.add(this.getSetupField(null, this.m_tabStyleChooser, Localizer.get("M_TAB_STYLE_DESC")));
        editorSetupPane.add(this.getSetupField(null, this.m_tabBlowDirectionChooser, Localizer.get("M_TAB_BLOW_DIRECTION")));
        final SetupCategory networkSetupCategory = new SetupCategory(Localizer.get("ET_NETWORK_CATEGORY"));
        final JPanel networkSetupPane = networkSetupCategory.getPanel();
        networkSetupPane.add(this.createSetupSeparator(Localizer.get("ET_NETWORK_ACTIVATION")));
        networkSetupPane.add(this.getSetupField(null, this.m_networkEnabledBox, Localizer.get("M_NETWORK_DESC")));
        final SetupCategory advancedSetupCategory = new SetupCategory(Localizer.get("ET_ADVANCED_SETUP"));
        final JPanel advancedSetupPane = advancedSetupCategory.getPanel();
        advancedSetupPane.add(this.createSetupSeparator(Localizer.get("ET_BETA_FEATURES_SETUP")));
        advancedSetupPane.add(this.getSetupField(null, this.m_enablePerformancesFeatures, Localizer.get("M_PERFORMANCES_FEATURE_DESC")));
        advancedSetupPane.add(this.getSetupField(null, this.m_enableMetronomeFeature, Localizer.get("M_METRONOME_FEATURE_DESC")));
        advancedSetupPane.add(this.getSetupField(null, this.m_countDownCheckBox, Localizer.get("M_COUNTDOWN_FEATURE_DESC")));
        this.m_restoreDefaultsButton.addActionListener(new RestoreDefaultsAction((RestoreDefaultsAction)null));
        this.addSetupCategory(globalSetupCategory);
        this.addSetupCategory(editorSetupCategory);
        this.addSetupCategory(foldersSetupCategory);
        this.addSetupCategory(networkSetupCategory);
        this.addSetupCategory(advancedSetupCategory);
    }
    
    @Override
    protected void discard() {
    }
    
    @Override
    protected boolean save() {
        final boolean showRebootMessage = !this.m_languageCombo.getSelectedLanguageIdentifier().equals(GlobalPreferences.getLanguage()) || this.m_systemAppearanceBox.isSelected() != GlobalPreferences.useSystemAppearance() || this.m_enablePerformancesFeatures.isSelected() != GlobalPreferences.getPerformancesFeatureEnabled() || this.m_enableMetronomeFeature.isSelected() != GlobalPreferences.getMetronomeFeatureEnabled();
        GlobalPreferences.setLanguage(this.m_languageCombo.getSelectedLanguageIdentifier());
        GlobalPreferences.useSystemAppearance(this.m_systemAppearanceBox.isSelected());
        GlobalPreferences.setScoresBrowsingFolder(this.m_scoresFolder.getFile().getAbsolutePath());
        GlobalPreferences.setModelsFolder(this.m_modelsFolder.getFile().getAbsolutePath());
        GlobalPreferences.setAutoTabEnabled(this.m_autoTabBox.isSelected());
        GlobalPreferences.setTabMappingCompletionEnabled(this.m_autoCompleteModelBox.isSelected());
        GlobalPreferences.setBarNumbersDisplayed(this.m_displayBarNumbersBox.isSelected());
        GlobalPreferences.setEditingHelpersDisplayed(this.m_displayEditingHelpersBox.isSelected());
        GlobalPreferences.setTabStyle(this.m_tabStyleChooser.getSelectedIndex());
        GlobalPreferences.setTabBlowDirection(this.m_tabBlowDirectionChooser.getSelectedBlowDirection());
        GlobalPreferences.setNetworkEnabled(this.m_networkEnabledBox.isSelected());
        GlobalPreferences.setPerformancesFeatureEnabled(this.m_enablePerformancesFeatures.isSelected());
        GlobalPreferences.setMetronomeFeatureEnabled(this.m_enableMetronomeFeature.isSelected());
        GlobalPreferences.setPlaybackCountdownEnabeld(this.m_countDownCheckBox.isSelected());
        GlobalPreferences.save();
        if (showRebootMessage) {
            JOptionPane.showMessageDialog(this.getWindow(), Localizer.get("M_PREFERENCE_NEED_RESTART"));
        }
        return true;
    }
    
    private class RestoreDefaultsAction extends AbstractAction
    {
        private static final long serialVersionUID = 1L;
        
        @Override
        public void actionPerformed(final ActionEvent event) {
            GlobalPreferences.restoreDefaultPreferences();
            RecentFilesManager.getInstance().reset();
            JOptionPane.showMessageDialog(PreferencesSetupDialog.this.getWindow(), Localizer.get("M_PREFERENCE_NEED_RESTART"));
            PreferencesSetupDialog.this.dispose();
        }
    }
}
