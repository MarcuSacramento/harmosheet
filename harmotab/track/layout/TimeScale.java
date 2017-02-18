// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.track.layout;

import harmotab.element.Element;
import harmotab.element.Bar;
import harmotab.element.Note;
import harmotab.renderer.LocationItem;
import harmotab.renderer.LocationList;
import java.util.Iterator;
import java.util.LinkedList;

public class TimeScale
{
    protected LinkedList<TimePoint> m_timePoints;
    protected Iterator<TimePoint> m_timeIterator;
    protected TimeVector m_timeIteratorCurrent;
    
    public TimeScale(final LocationList locations, final int trackId) {
        this.m_timePoints = new LinkedList<TimePoint>();
        this.constructTimeScale(locations, trackId);
    }
    
    public void constructTimeScale(final LocationList locations, final int trackId) {
        float currentTime = 0.0f;
        int currentLine = 0;
        this.m_timePoints.clear();
        this.m_timeIterator = null;
        this.m_timeIteratorCurrent = null;
        final Iterator<LocationItem> locationsIterator = locations.getIterator();
        while (locationsIterator.hasNext()) {
            final LocationItem item = locationsIterator.next();
            if (item.getTrackId() == trackId) {
                final Element element = item.getElement();
                if (element instanceof Note) {
                    if (item.m_line > currentLine) {
                        currentLine = item.m_line;
                        this.m_timePoints.add(new TimePoint((byte)1, currentTime, currentLine, item.m_x1));
                    }
                    currentTime = item.m_time + element.getDuration();
                    this.m_timePoints.add(new TimeVector((byte)4, item.m_time, currentTime, item.m_line, item.m_x1, item.m_x2));
                }
                else {
                    if (!(element instanceof Bar)) {
                        continue;
                    }
                    this.m_timePoints.add(new TimePoint((byte)3, currentTime, currentLine, item.m_poiX));
                }
            }
        }
    }
    
    public TimePoint getFirstPointAt(final float time) {
        TimePoint result = null;
        if (this.m_timeIteratorCurrent == null || this.m_timeIteratorCurrent.m_endTime > time) {
            this.m_timeIterator = this.m_timePoints.iterator();
            this.iterateTimeIterator();
        }
        if (this.m_timeIteratorCurrent == null) {
            return null;
        }
        do {
            result = this.m_timeIteratorCurrent.getPoint(time);
            if (result == null) {
                this.iterateTimeIterator();
            }
        } while (result == null && this.m_timeIteratorCurrent != null);
        return result;
    }
    
    public TimePoint getLastPointAt(final float time) {
        TimePoint result = null;
        if (this.m_timeIteratorCurrent == null || this.m_timeIteratorCurrent.m_endTime > time) {
            this.m_timeIterator = this.m_timePoints.iterator();
            this.iterateTimeIterator();
        }
        if (this.m_timeIteratorCurrent == null) {
            return null;
        }
        do {
            result = this.m_timeIteratorCurrent.getPoint(time);
            if (result == null) {
                this.iterateTimeIterator();
            }
        } while (result == null && this.m_timeIteratorCurrent != null);
        TimePoint after = null;
        do {
            after = this.m_timeIteratorCurrent.getPoint(time);
            if (after != null) {
                result = after;
                this.iterateTimeIterator();
            }
        } while (after != null && this.m_timeIteratorCurrent != null);
        return result;
    }
    
    private TimeVector iterateTimeIterator() {
        TimePoint point = null;
        do {
            point = (this.m_timeIterator.hasNext() ? this.m_timeIterator.next() : null);
        } while (point != null && !(point instanceof TimeVector));
        return this.m_timeIteratorCurrent = (TimeVector)point;
    }
    
    public Float getTime(final int line, final int x) {
        for (final TimePoint point : this.m_timePoints) {
            if (point instanceof TimeVector) {
                final TimeVector vector = (TimeVector)point;
                final Float res = vector.getTime(line, x);
                if (res != null) {
                    return res;
                }
                continue;
            }
        }
        return null;
    }
    
    public float getEndTime() {
        float endTime = 0.0f;
        for (final TimePoint point : this.m_timePoints) {
            endTime = Math.max(endTime, point.getEndTime());
        }
        return endTime;
    }
    
    public TimePoint getLineStart(final int line) {
        for (final TimePoint point : this.m_timePoints) {
            if (point.m_type == 1 && point.m_line == line) {
                return new TimePoint(point.m_type, point.m_time, point.m_line, point.m_x);
            }
        }
        return null;
    }
    
    public int getNumberOfLines() {
        int max = 0;
        for (final TimePoint point : this.m_timePoints) {
            max = Math.max(max, point.getLine());
        }
        return max;
    }
    
    public class TimePoint
    {
        public static final byte LINE_STARTS = 1;
        public static final byte LINE_ENDS = 2;
        public static final byte BAR = 3;
        public static final byte TIME = 4;
        protected byte m_type;
        protected float m_time;
        protected int m_line;
        protected int m_x;
        
        public TimePoint(final byte type, final float time, final int line, final int x) {
            this.m_type = type;
            this.m_time = time;
            this.m_line = line;
            this.m_x = x;
        }
        
        public float getTime() {
            return this.m_time;
        }
        
        public int getLine() {
            return this.m_line;
        }
        
        public int getX() {
            return this.m_x;
        }
        
        public float getEndTime() {
            return this.m_time;
        }
    }
    
    public class TimeVector extends TimePoint
    {
        protected float m_endTime;
        protected int m_endX;
        
        public TimeVector(final byte type, final float startTime, final float endTime, final int line, final int startX, final int endX) {
            super(type, startTime, line, startX);
            this.m_endTime = endTime;
            this.m_endX = endX;
        }
        
        public TimePoint getPoint(final float time) {
            if (time >= this.m_time && time <= this.m_endTime) {
                final int posX = (int)(this.m_x + (this.m_endX - this.m_x) * ((time - this.m_time) / (this.m_endTime - this.m_time)));
                return new TimePoint((byte)4, time, this.m_line, posX);
            }
            return null;
        }
        
        public Float getTime(final int line, final int x) {
            if (line == this.m_line && x >= this.m_x && x <= this.m_endX) {
                final float time = this.m_time + (this.m_endTime - this.m_time) * ((x - this.m_x) / (this.m_endX - this.m_x));
                return new Float(time);
            }
            return null;
        }
        
        @Override
        public float getEndTime() {
            return this.m_endTime;
        }
    }
}
