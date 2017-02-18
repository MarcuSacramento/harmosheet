// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.tools;

import harmotab.renderer.LocationItem;
import harmotab.core.Score;
import java.awt.Container;

public class ToolFactory
{
    public static Tool createController(final Container container, final Score score, final LocationItem location) {
        switch (location.getElement().getType()) {
            case 1: {
                return new TextTool(container, score, location);
            }
            case 2: {
                return new NoteTool(container, score, location);
            }
            case 9: {
                return new TimeSignatureTool(container, score, location);
            }
            case 4: {
                return new HarmoTabTool(container, score, location);
            }
            case 3: {
                return new TabTool(container, score, location);
            }
            case 5: {
                return new BarTool(container, score, location);
            }
            case 8: {
                return new KeySignatureTool(container, score, location);
            }
            case 7: {
                return new KeyTool(container, score, location);
            }
            case 12: {
                return new ChordTool(container, score, location);
            }
            case 13: {
                return new AccompanimentTool(container, score, location);
            }
            case 14: {
                return new TempoTool(container, score, location);
            }
            case 17: {
                return new SilenceTool(container, score, location);
            }
            case 18: {
                return new LyricsTool(container, score, location);
            }
            case 19: {
                return new HarmonicaPropertiesTool(container, score, location);
            }
            default: {
                System.err.println("ControllerFactory::createController: Cannot set controller: unhandled element type (#" + location.getElement().getType() + ").");
                return null;
            }
        }
    }
}
