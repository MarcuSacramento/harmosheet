// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

class AttributeChangesObserver implements HarmoTabObjectListener
{
    protected HarmoTabObject m_owner;
    protected String m_attribute;
    
    public AttributeChangesObserver(final HarmoTabObject owner, final String attribute) {
        this.m_owner = null;
        this.m_owner = owner;
        this.m_attribute = attribute;
    }
    
    @Override
    public void onObjectChanged(final HarmoTabObjectEvent event) {
        final HarmoTabObjectEvent dispatchEvent = new HarmoTabObjectEvent(this.m_owner, this.m_attribute, event);
        this.m_owner.dispatchEvent(dispatchEvent);
    }
}
