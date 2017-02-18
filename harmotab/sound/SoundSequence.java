// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.sound;

import java.util.NoSuchElementException;
import java.util.List;
import java.util.Collections;
import java.util.Collection;
import harmotab.element.Element;
import java.util.Iterator;
import java.util.Vector;
import java.util.LinkedList;

public class SoundSequence extends LinkedList<SoundItem> implements Cloneable
{
    private static final long serialVersionUID = 1L;
    private Vector<RepeatItem> m_repeats;
    
    public SoundSequence() {
        this.m_repeats = new Vector<RepeatItem>();
    }
    
    @Override
    public Object clone() {
        final SoundSequence soundList = new SoundSequence();
        for (final SoundItem item : this) {
            soundList.add((SoundItem)item.clone());
        }
        soundList.m_repeats = new Vector<RepeatItem>();
        for (final RepeatItem item2 : this.m_repeats) {
            soundList.m_repeats.add((RepeatItem)item2.clone());
        }
        return soundList;
    }
    
    public SoundItem at(final float time) {
        SoundItem applicant = null;
        for (final SoundItem item : this) {
            if (item.m_startTime <= time && item.m_endTime >= time) {
                if (applicant != null) {
                    applicant = ((item.m_startTime >= applicant.m_startTime) ? item : applicant);
                }
                else {
                    applicant = item;
                }
            }
        }
        return applicant;
    }
    
    public SoundItem at(final int trackId, final float time) {
        SoundItem applicant = null;
        for (final SoundItem item : this) {
            if (item.m_trackId == trackId && item.m_startTime <= time && item.m_endTime >= time) {
                if (applicant != null) {
                    applicant = ((item.m_startTime >= applicant.m_startTime) ? item : applicant);
                }
                else {
                    applicant = item;
                }
            }
        }
        return applicant;
    }
    
    public SoundItem get(final Element element) {
        for (final SoundItem item : this) {
            if (item.getElement() == element) {
                return item;
            }
        }
        return null;
    }
    
    public float getLastTime() {
        float lastTime = 0.0f;
        for (final SoundItem item : this) {
            if (item.m_endTime > lastTime) {
                lastTime = item.m_endTime;
            }
        }
        return lastTime;
    }
    
    public void addRepeat(final RepeatItem item) {
        this.m_repeats.add(item);
    }
    
    public void removeRepeat(final RepeatItem item) {
        this.m_repeats.remove(item);
    }
    
    public void clearRepeats() {
        this.m_repeats.clear();
    }
    
    public Collection<RepeatItem> getRepeats() {
        return this.m_repeats;
    }
    
    public void sort() {
        Collections.sort((List<Comparable>)this);
        Collections.sort(this.m_repeats);
    }
    
    public SoundSequence mergeRepeats() {
        final SoundSequence soundList = (SoundSequence)this.clone();
        if (this.isEmpty()) {
            return soundList;
        }
        soundList.sort();
        while (soundList.getRepeats().size() > 0) {
            final RepeatItem repeat = soundList.m_repeats.get(0);
            soundList.m_repeats.remove(0);
            Iterator<SoundItem> iterator;
            SoundItem soundItem;
            for (iterator = soundList.iterator(), soundItem = iterator.next(); soundItem.getStartTime() < repeat.getPhraseStartTime() && iterator.hasNext(); soundItem = iterator.next()) {}
            if (soundItem.getStartTime() >= repeat.getPhraseStartTime()) {
                final int firstItemIndex = soundList.indexOf(soundItem);
                int lastItemIndex = -1;
                try {
                    while (soundItem.getStartTime() < repeat.getPhraseEndTime()) {
                        soundItem = iterator.next();
                    }
                    lastItemIndex = soundList.indexOf(soundItem);
                }
                catch (NoSuchElementException e) {
                    lastItemIndex = soundList.indexOf(soundItem) + 1;
                }
                final SoundSequence phraseSounds = new SoundSequence();
                final float phraseDuration = repeat.getPhraseEndTime() - repeat.getPhraseStartTime();
                for (int currentIteration = 1; currentIteration < repeat.getIterationsNumber(); ++currentIteration) {
                    for (final SoundItem phraseItem : soundList.subList(firstItemIndex, lastItemIndex)) {
                        final SoundItem newSoundItem = (SoundItem)phraseItem.clone();
                        newSoundItem.timeshift(phraseDuration * currentIteration);
                        phraseSounds.add(newSoundItem);
                    }
                }
                final float shift = phraseDuration * (repeat.getIterationsNumber() - 1);
                for (int i = lastItemIndex; i < soundList.size(); ++i) {
                    soundList.get(i).timeshift(shift);
                }
                for (final RepeatItem item : soundList.getRepeats()) {
                    item.timeshift(shift);
                }
                soundList.addAll(lastItemIndex, phraseSounds);
            }
        }
        soundList.clearRepeats();
        return soundList;
    }
    
    public void insertFront(final SoundItem item) {
        final float delay = item.getDurationTime();
        item.m_startTime = 0.0f;
        this.timeShift(item.m_endTime = delay);
        this.add(0, item);
    }
    
    public void insertFront(final SoundSequence sounds) {
        this.timeShift(sounds.getLastTime());
        this.addAll(0, sounds);
    }
    
    public void timeShift(final float delay) {
        for (final SoundItem i : this) {
            i.timeshift(delay);
        }
    }
    
    public void printStackTrace() {
        final Iterator<SoundItem> it = this.iterator();
        System.out.println("Sound list (" + this.size() + " items) : " + this);
        int index = 0;
        while (it.hasNext()) {
            final SoundItem item = it.next();
            System.out.println(String.valueOf(index) + ". " + item.getElement() + "\t" + item.getStartTime() + " to " + item.getEndTime() + "\t" + "(#" + item.getSoundId() + ")" + "\t" + (item.isSilence() ? "SILENCE" : "\t"));
            ++index;
        }
    }
}
