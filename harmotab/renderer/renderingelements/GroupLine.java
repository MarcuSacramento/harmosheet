// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer.renderingelements;

public class GroupLine
{
    private float m_lineCoefficient;
    private float m_originOrdinate;
    private int m_direction;
    private int m_x1;
    private int m_x2;
    
    public GroupLine(final float m, final float p, final int direction, final int x1, final int x2) {
        this.m_lineCoefficient = m;
        this.m_originOrdinate = p;
        this.m_direction = direction;
        this.m_x1 = x1;
        this.m_x2 = x2;
    }
    
    public float getLineCoefficient() {
        return this.m_lineCoefficient;
    }
    
    public float getOriginOrdinate() {
        return this.m_originOrdinate;
    }
    
    public int getDirection() {
        return this.m_direction;
    }
    
    public int getX1() {
        return this.m_x1;
    }
    
    public int getX2() {
        return this.m_x2;
    }
    
    public int getY(final int x) {
        return (int)(this.m_lineCoefficient * x + this.m_originOrdinate);
    }
    
    public int getX(final int y) {
        return (int)((y - this.m_originOrdinate) / this.m_lineCoefficient);
    }
}
