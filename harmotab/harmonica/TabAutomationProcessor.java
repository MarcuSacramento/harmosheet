// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.harmonica;

import harmotab.element.Tab;
import harmotab.core.GlobalPreferences;
import harmotab.element.HarmoTabElement;
import harmotab.core.HarmoTabObjectEvent;
import harmotab.core.HarmoTabObjectListener;

public class TabAutomationProcessor implements HarmoTabObjectListener
{
    private TabModel m_tabModel;
    
    public TabAutomationProcessor(final TabModel tabModel) {
        this.m_tabModel = null;
        this.setTabModel(tabModel);
    }
    
    public void setTabModel(final TabModel tabModel) {
        if (tabModel == null) {
            throw new NullPointerException();
        }
        this.m_tabModel = tabModel;
    }
    
    public TabModel getTabModel() {
        return this.m_tabModel;
    }
    
    @Override
    public void onObjectChanged(final HarmoTabObjectEvent event) {
        if (!(event.getSource() instanceof HarmoTabElement)) {
            System.err.println("TabAutomationProcessor: event not comming from an HarmoTabElement.");
        }
        if (event.propertyIs("height")) {
            this.doAutoTab((HarmoTabElement)event.getSource());
        }
        else if (event.propertyIs("tab")) {
            this.updateTabModel((HarmoTabElement)event.getSource());
        }
    }
    
    public void doAutoTab(final HarmoTabElement htElement) {
        if (GlobalPreferences.isAutoTabEnabled()) {
            final Tab modelTab = this.m_tabModel.getTab(htElement.getHeight());
            final Tab elementTab = htElement.getTab();
            htElement.setDispachEvents(false, null);
            if (modelTab != null && modelTab.isDefined() && !modelTab.equals(elementTab)) {
                modelTab.setEffect(elementTab.getEffect());
                htElement.setTab(modelTab);
            }
            else if (modelTab == null && elementTab.isDefined()) {
                final Tab tab = new Tab();
                tab.setEffect(elementTab.getEffect());
                htElement.setTab(tab);
            }
            htElement.setDispachEvents(true, null);
        }
    }
    
    public void updateTabModel(final HarmoTabElement htElement) {
        if (GlobalPreferences.isTabMappingCompletionEnabled()) {
            final Tab modelTab = this.m_tabModel.getTab(htElement.getHeight());
            final Tab elementTab = htElement.getTab();
            if (elementTab.isDefined() && (modelTab == null || !modelTab.equals(elementTab))) {
                this.m_tabModel.setTab(htElement.getHeight(), elementTab);
            }
        }
    }
}
