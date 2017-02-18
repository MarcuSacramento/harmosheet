// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.io;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import harmotab.core.HarmoTabObjectFactory;
import harmotab.core.HarmoTabObject;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XmlSerializedObject implements SerializedObject
{
    protected Element m_element;
    protected XmlObjectSerializer m_serializer;
    
    XmlSerializedObject(final XmlObjectSerializer serializer, final String objectName) {
        this.m_serializer = serializer;
        this.m_element = serializer.m_document.createElement(objectName);
    }
    
    public XmlSerializedObject(final XmlObjectSerializer serializer, final Node xmlNode) {
        this.m_serializer = serializer;
        this.m_element = (Element)xmlNode;
    }
    
    public Node getXmlNode() {
        return this.m_element;
    }
    
    @Override
    public String getObjectType() {
        return this.m_element.getNodeName();
    }
    
    @Override
    public boolean hasAttribute(final String attribute) {
        return this.m_element.hasAttribute(attribute);
    }
    
    @Override
    public String getAttribute(final String attribute) {
        return this.m_element.getAttribute(attribute);
    }
    
    @Override
    public void setAttribute(final String attribute, final String value) {
        this.m_element.setAttribute(attribute, value);
    }
    
    @Override
    public HarmoTabObject getElementAttribute(final String attribute) {
        final int objectNodeId = Integer.decode(this.m_element.getAttribute(attribute));
        final NodeList nodeList = this.m_element.getChildNodes();
        for (int length = nodeList.getLength(), i = 0; i < length; ++i) {
            final Node node = nodeList.item(i);
            final NamedNodeMap attributes = node.getAttributes();
            if (attributes != null) {
                final Element curEle = (Element)node;
                if (curEle.hasAttribute("id")) {
                    final int curId = Integer.decode(curEle.getAttribute("id"));
                    if (curId == objectNodeId) {
                        return HarmoTabObjectFactory.create(this.m_serializer, new XmlSerializedObject(this.m_serializer, node));
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    public void setElementAttribute(final String attribute, final HarmoTabObject object) {
        final Node objectNode = ((XmlSerializedObject)object.serialize(this.m_serializer)).getXmlNode();
        this.m_element.setAttribute(attribute, objectNode.getAttributes().getNamedItem("id").getNodeValue());
        this.m_element.appendChild(objectNode);
    }
    
    @Override
    public void addChild(final SerializedObject object) {
        this.m_element.appendChild(((XmlSerializedObject)object).getXmlNode());
    }
    
    @Override
    public int getChildsNumber() {
        return this.m_element.getChildNodes().getLength();
    }
    
    @Override
    public SerializedObject getChild(final int index) {
        final Node node = this.m_element.getChildNodes().item(index);
        if (node.getNodeType() == 1) {
            return new XmlSerializedObject(this.m_serializer, node);
        }
        return null;
    }
}
