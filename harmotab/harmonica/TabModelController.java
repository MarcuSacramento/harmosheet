// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.harmonica;

import harmotab.core.undo.UndoManager;
import java.util.Iterator;
import harmotab.element.HarmoTabElement;
import harmotab.element.Element;
import harmotab.track.HarmoTabTrack;
import harmotab.throwables.OutOfBoundsError;
import harmotab.element.Tab;
import harmotab.core.Height;

public class TabModelController
{
    private TabModel m_tabModel;
    
    public TabModelController(final TabModel tabModel) {
        this.m_tabModel = null;
        this.m_tabModel = tabModel;
    }
    
    public TabModel getTabModel() {
        return this.m_tabModel;
    }
    
    public void populateFromHarmonicaModel(final Harmonica harmonica, final Height referenceHeight, final Tab referenceTab) {
        this.m_tabModel.setDispachEvents(false, null);
        final int numberOfHoles = harmonica.getModel().getNumberOfHoles();
        this.m_tabModel.reset();
        final Height standardHeight = harmonica.getHeight(referenceTab);
        final int offset = referenceHeight.getOctave() - standardHeight.getOctave();
        for (int hole = 1; hole <= numberOfHoles; ++hole) {
            for (byte i = 0; i < 6; ++i) {
                final Tab tab = HarmonicaModel.createTab(hole, i);
                final Height height = harmonica.getHeight(tab);
                if (height != null) {
                    try {
                        height.setOctave(height.getOctave() + offset);
                        this.m_tabModel.setTab(height, tab);
                    }
                    catch (OutOfBoundsError outOfBoundsError) {}
                }
            }
        }
        this.m_tabModel.setDispachEvents(true, "mapping");
    }
    
    public void populateFromHarmoTabTrack(final HarmoTabTrack htTrack) {
        this.m_tabModel.setDispachEvents(false, null);
        final TabAutomationProcessor tabAutoProc = new TabAutomationProcessor(this.m_tabModel);
        for (final Element element : htTrack) {
            if (element instanceof HarmoTabElement) {
                final HarmoTabElement htElement = (HarmoTabElement)element;
                tabAutoProc.updateTabModel(htElement);
            }
        }
        this.m_tabModel.setDispachEvents(true, "mapping");
    }
    
    public void updateTabs(final HarmoTabTrack track) {
        track.setDispachEvents(false, null);
        final UndoManager undo = UndoManager.getInstance();
        for (final Element element : track) {
            if (element instanceof HarmoTabElement) {
                final HarmoTabElement htElement = (HarmoTabElement)element;
                final Tab tab = this.m_tabModel.getTab(htElement.getHeight());
                if (tab == null) {
                    continue;
                }
                undo.appendToLastUndoCommand(htElement.createRestoreCommand());
                htElement.setTab(tab);
            }
        }
        track.setDispachEvents(true, "elementChanged");
    }
}
