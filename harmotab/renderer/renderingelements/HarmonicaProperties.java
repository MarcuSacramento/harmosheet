// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer.renderingelements;

import harmotab.harmonica.Harmonica;

public class HarmonicaProperties extends RenderingElement
{
    private Harmonica m_harmonica;
    
    public HarmonicaProperties(final Harmonica harmonica) {
        super((byte)19);
        this.m_harmonica = null;
        this.m_harmonica = harmonica;
    }
    
    public void setHarmonica(final Harmonica harmonica) {
        this.m_harmonica = harmonica;
    }
    
    public Harmonica getHarmonica() {
        return this.m_harmonica;
    }
}
