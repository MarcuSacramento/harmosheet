// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.track.layout;

import java.util.Iterator;
import harmotab.renderer.renderingelements.EmptyArea;
import harmotab.element.Bar;
import harmotab.element.Element;
import harmotab.renderer.renderingelements.TabArea;
import harmotab.element.HarmoTabElement;
import harmotab.renderer.renderingelements.HarmonicaProperties;
import harmotab.track.HarmoTabTrack;
import harmotab.renderer.LocationItem;
import harmotab.renderer.renderingelements.Staff;
import harmotab.renderer.LocationList;
import harmotab.track.Track;

public class HarmoTabTrackLayout extends StaffTrackLayout
{
    private static final int TAB_TRACK_HEIGHT = 45;
    private static final int TAB_AREA_HEADER_WIDTH = 30;
    
    public HarmoTabTrackLayout(final Track track) {
        super(track);
    }
    
    @Override
    public void processElementsPositionning(final LocationList locations, final int areaWidth, final float scoreDuration) {
        super.processElementsPositionning(locations, areaWidth, scoreDuration);
        final LocationList localHeadLocations = new LocationList();
        final LocationList localTailLocations = new LocationList();
        Iterator<LocationItem> itemIterator;
        LocationItem staffLocationItem;
        for (itemIterator = locations.getIterator(), staffLocationItem = null; itemIterator.hasNext() && (staffLocationItem == null || !(staffLocationItem.getElement() instanceof Staff)); staffLocationItem = itemIterator.next()) {}
        if (staffLocationItem != null) {
            final HarmonicaProperties harmoProps = new HarmonicaProperties(((HarmoTabTrack)this.m_track).getHarmonica());
            final LocationItem harmoPropsItem = new LocationItem(staffLocationItem);
            harmoPropsItem.translate(0, harmoPropsItem.m_height - 40);
            harmoPropsItem.resize(60, 50);
            harmoPropsItem.m_element = harmoProps;
            localHeadLocations.add(harmoPropsItem);
        }
        else {
            System.err.println("HarmoTabTrackLayout::processElementsPositionning: First staff not found !");
        }
        final int staffTrackHeight = super.getTrackHeight();
        LocationItem firstNoteOfTheLine = null;
        for (final LocationItem item : locations) {
            final Element element = item.getElement();
            if (element instanceof HarmoTabElement) {
                final HarmoTabElement htElement = (HarmoTabElement)element;
                if (firstNoteOfTheLine == null) {
                    firstNoteOfTheLine = item;
                }
                if (!htElement.canHaveTab()) {
                    continue;
                }
                item.resize(item.m_width, staffTrackHeight);
                final LocationItem newItem = (LocationItem)item.clone();
                newItem.m_element = ((HarmoTabElement)element).getTab();
                newItem.resize(item.m_width, 45);
                newItem.translate(0, staffTrackHeight);
                newItem.setParent(element);
                localHeadLocations.add(newItem);
            }
            else if (element instanceof Staff) {
                final LocationItem newItem2 = (LocationItem)item.clone();
                final int dx = (firstNoteOfTheLine != null) ? firstNoteOfTheLine.getX1() : item.getX1();
                final int delta = dx - item.getX1() - 30;
                final int newWidth = item.m_width - delta;
                newItem2.m_element = new TabArea();
                final LocationItem locationItem = newItem2;
                locationItem.m_poiX -= item.m_width - newWidth;
                newItem2.resize(newWidth, 45);
                newItem2.translate(delta, staffTrackHeight);
                newItem2.setParent(null);
                localTailLocations.add(newItem2);
                firstNoteOfTheLine = null;
            }
            else if (element instanceof Bar) {
                final LocationItem newItem2 = (LocationItem)item.clone();
                newItem2.resize(0, 45);
                newItem2.translate(0, staffTrackHeight - 42);
                newItem2.setParent(element);
                newItem2.m_flag = 0;
                localTailLocations.add(newItem2);
                if (firstNoteOfTheLine != null) {
                    continue;
                }
                firstNoteOfTheLine = item;
            }
            else {
                if (!(element instanceof EmptyArea) || firstNoteOfTheLine != null) {
                    continue;
                }
                firstNoteOfTheLine = item;
            }
        }
        locations.add(0, localHeadLocations);
        locations.add(localTailLocations);
    }
    
    @Override
    public int getTrackHeight() {
        return super.getTrackHeight() + 45;
    }
}
