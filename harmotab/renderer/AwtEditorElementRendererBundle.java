// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer;

import java.awt.Stroke;
import java.awt.BasicStroke;
import java.util.Iterator;
import harmotab.renderer.renderingelements.GroupLine;
import harmotab.element.Note;
import java.awt.Font;
import java.awt.image.ImageObserver;
import harmotab.element.Element;
import harmotab.renderer.renderingelements.HarmonicaProperties;
import harmotab.element.Lyrics;
import harmotab.element.Silence;
import harmotab.renderer.renderingelements.TripletGroup;
import harmotab.renderer.renderingelements.TiedNoteGroup;
import harmotab.element.Accompaniment;
import harmotab.element.Chord;
import harmotab.renderer.renderingelements.HoockedNoteGroup;
import harmotab.element.TimeSignature;
import harmotab.element.Key;
import harmotab.renderer.renderingelements.Staff;
import harmotab.element.TextElement;
import harmotab.renderer.renderingelements.EmptyArea;
import java.awt.Color;
import harmotab.renderer.awtrenderers.AwtRenderersResources;
import java.awt.Graphics2D;
import harmotab.renderer.awtrenderers.AwtArrowTabAreaRenderer;
import harmotab.renderer.awtrenderers.AwtTabularTabAreaRenderer;
import harmotab.renderer.awtrenderers.AwtKeySignatureRenderer;
import harmotab.renderer.awtrenderers.AwtTempoRenderer;
import harmotab.renderer.awtrenderers.AwtArrowBarRenderer;
import harmotab.renderer.awtrenderers.AwtTabularBarRenderer;
import harmotab.renderer.awtrenderers.AwtTabularTabRenderer;
import harmotab.renderer.awtrenderers.AwtArrowTabRenderer;
import harmotab.desktop.components.TabStyleChooser;
import harmotab.core.GlobalPreferences;
import harmotab.renderer.awtrenderers.AwtHarmoTabElementRenderer;
import harmotab.renderer.awtrenderers.AwtNoteRenderer;

public class AwtEditorElementRendererBundle extends ElementRendererBundle
{
    protected ElementRenderer m_noteRenderer;
    protected ElementRenderer m_harmoTabElementRenderer;
    protected ElementRenderer m_barRenderer;
    protected ElementRenderer m_tabRenderer;
    protected ElementRenderer m_tempoRenderer;
    protected ElementRenderer m_keySignatureRenderer;
    protected ElementRenderer m_tabAreaRenderer;
    
    public AwtEditorElementRendererBundle() {
        this.m_noteRenderer = null;
        this.m_harmoTabElementRenderer = null;
        this.m_barRenderer = null;
        this.m_tabRenderer = null;
        this.m_tempoRenderer = null;
        this.m_keySignatureRenderer = null;
        this.m_tabAreaRenderer = null;
        this.setMode(RenderingMode.EDIT_MODE);
        this.initRenderers();
    }
    
    @Override
    public void reset() {
        this.initRenderers();
    }
    
    public void initRenderers() {
        this.m_noteRenderer = new AwtNoteRenderer();
        this.m_harmoTabElementRenderer = new AwtHarmoTabElementRenderer();
        this.m_tabRenderer = TabStyleChooser.getRenderer(GlobalPreferences.getTabStyle());
        if (this.m_tabRenderer == null) {
            this.m_tabRenderer = new AwtArrowTabRenderer();
        }
        if (this.m_tabRenderer instanceof AwtTabularTabRenderer) {
            this.m_barRenderer = new AwtTabularBarRenderer();
        }
        else {
            this.m_barRenderer = new AwtArrowBarRenderer();
        }
        this.m_tempoRenderer = new AwtTempoRenderer();
        this.m_keySignatureRenderer = new AwtKeySignatureRenderer();
        if (this.m_tabRenderer instanceof AwtTabularTabRenderer) {
            this.m_tabAreaRenderer = new AwtTabularTabAreaRenderer();
        }
        else {
            this.m_tabAreaRenderer = new AwtArrowTabAreaRenderer();
        }
    }
    
    @Override
    public void paintElement(final Graphics2D g, final LocationItem item) {
        final Element element = item.getElement();
        if (element == null) {
            return;
        }
        g.setColor(AwtRenderersResources.m_defaultForeground);
        g.setFont(AwtRenderersResources.m_defaultFont);
        if (item.getFlag(0)) {
            this.paintWarning(g, item);
            g.setColor(Color.RED);
        }
        switch (element.getType()) {
            case -1: {
                this.paintEmptyArea(g, (EmptyArea)element, item);
                break;
            }
            case 1: {
                this.paintTextElement(g, (TextElement)element, item);
                break;
            }
            case 6: {
                this.paintStaff(g, (Staff)element, item);
                break;
            }
            case 5: {
                if (this.m_barRenderer != null) {
                    this.m_barRenderer.paint(g, element, item);
                }
                this.drawEditingSurroundingHelper(g, item);
                break;
            }
            case 7: {
                this.paintKey(g, (Key)element, item);
                break;
            }
            case 8: {
                if (this.m_keySignatureRenderer != null) {
                    this.m_keySignatureRenderer.paint(g, element, item);
                }
                this.drawEditingSurroundingHelper(g, item);
                break;
            }
            case 9: {
                this.paintTimeSignature(g, (TimeSignature)element, item);
                break;
            }
            case 2: {
                if (this.m_noteRenderer != null) {
                    this.m_noteRenderer.paint(g, element, item);
                }
                this.drawEditingSurroundingHelper(g, item);
                break;
            }
            case 4: {
                if (this.m_harmoTabElementRenderer != null) {
                    this.m_harmoTabElementRenderer.paint(g, element, item);
                }
                this.drawEditingSurroundingHelper(g, item);
                break;
            }
            case 3: {
                if (this.m_tabRenderer != null) {
                    this.m_tabRenderer.paint(g, element, item);
                }
                this.drawEditingSurroundingHelper(g, item);
                break;
            }
            case 11: {
                this.paintHoockedNoteGroup(g, (HoockedNoteGroup)element, item);
                break;
            }
            case 12: {
                this.paintChord(g, (Chord)element, item);
                break;
            }
            case 13: {
                this.paintAccompaniment(g, (Accompaniment)element, item);
                break;
            }
            case 14: {
                if (this.m_tempoRenderer != null) {
                    this.m_tempoRenderer.paint(g, element, item);
                }
                this.drawEditingSurroundingHelper(g, item);
                break;
            }
            case 15: {
                this.paintTiedNoteGroup(g, (TiedNoteGroup)element, item);
                break;
            }
            case 16: {
                this.paintTripletGroup(g, (TripletGroup)element, item);
                break;
            }
            case 17: {
                this.paintSilence(g, (Silence)element, item);
                break;
            }
            case 18: {
                this.paintLyrics(g, (Lyrics)element, item);
                break;
            }
            case 19: {
                this.paintHarmonicaProperties(g, (HarmonicaProperties)element, item);
                break;
            }
            case 20: {
                if (this.m_tabAreaRenderer != null) {
                    this.m_tabAreaRenderer.paint(g, element, item);
                    break;
                }
                break;
            }
            default: {
                System.err.println("AwtElementRenderer::render: No paint method for element " + element.getType());
                break;
            }
        }
        if (item.m_flag >= 268435456) {
            if (item.getFlag(30)) {
                g.setColor(Color.GREEN);
            }
            if (item.getFlag(29)) {
                g.setColor(Color.RED);
            }
            g.drawLine(item.m_x1, item.m_y1, item.m_x2, item.m_y2);
            g.drawLine(item.m_x1, item.m_y2, item.m_x2, item.m_y1);
            g.setColor(Color.BLACK);
        }
    }
    
    protected void paintWarning(final Graphics2D g, final LocationItem item) {
        if (this.getDrawEditingWarnings()) {
            g.drawImage(AwtRenderersResources.m_warningImage, item.getX1() + 2, item.getY1() + 2, null);
        }
    }
    
    protected void paintEmptyArea(final Graphics2D g, final EmptyArea emptyArea, final LocationItem item) {
        if (this.getDrawEditingWarnings()) {
            final int square = 16;
            final int x = item.getX1() + 10;
            final int y = item.getY1() + (item.getHeight() - square) / 2;
            g.drawImage(AwtRenderersResources.m_emptyAreaImage, x, y, null);
        }
    }
    
    protected void paintTextElement(final Graphics2D g, final TextElement e, final LocationItem item) {
        final Font oldFont = g.getFont();
        g.setFont(e.getFont());
        final String text = e.getText();
        int x = item.getX1();
        final int y = item.getY1() + g.getFontMetrics().getHeight();
        final int width = g.getFontMetrics().stringWidth(text);
        final String align = e.getAlignment();
        if (!align.equals("left")) {
            if (align.equals("right")) {
                x = item.getX2() - width;
            }
            else if (align.equals("center")) {
                x += item.getWidth() / 2 - width / 2;
            }
            else {
                System.err.println("ElementRenderer::paintElement(TextElement): Alignment not handled (#" + e.getAlignment() + ") !");
            }
        }
        g.drawString(text, x, y);
        g.setFont(oldFont);
        this.drawEditingSurroundingHelper(g, item);
    }
    
    protected void paintStaff(final Graphics2D g, final Staff staff, final LocationItem item) {
        final int x1 = item.getX1();
        final int x2 = this.getDrawEditingHelpers() ? item.getX2() : item.getPointOfInterestX();
        int y = item.getPointOfInterestY();
        final int spacing = item.getExtra();
        g.drawLine(x1, y, x1, y + spacing * 8);
        for (int i = 0; i < 5; ++i) {
            g.drawLine(x1, y, x2, y);
            y += spacing * 2;
        }
    }
    
    protected void paintHarmonicaProperties(final Graphics2D g, final HarmonicaProperties harmoProps, final LocationItem item) {
        final int x = item.getX1();
        final int y = item.getY1();
        g.setFont(AwtRenderersResources.m_harmonicaNameFont);
        g.setColor(AwtRenderersResources.m_harmonicaForeground);
        g.drawString(harmoProps.getHarmonica().getName(), x, y + 45);
        g.setFont(AwtRenderersResources.m_harmonicaKeyFont);
        g.drawString(harmoProps.getHarmonica().getTunning().getNoteName(), x, y + 20);
        this.drawEditingSurroundingHelper(g, item);
    }
    
    protected void paintKey(final Graphics2D g, final Key key, final LocationItem l) {
        final int x = l.getPointOfInterestX() - 10;
        final int y = l.getPointOfInterestY() - 30;
        switch (key.getValue()) {
            case 0: {
                g.drawImage(AwtRenderersResources.m_keyCImage, x, y, null);
                break;
            }
            default: {
                System.err.println("ElementRenderer::paintElement(Key): Key not handled (#" + key.getValue() + ") !");
                break;
            }
        }
    }
    
    protected void paintTimeSignature(final Graphics2D g, final TimeSignature ts, final LocationItem item) {
        final int DIGIT_WIDTH = 19;
        final int DIGIT_HEIGHT = 15;
        final int x = item.getPointOfInterestX() - 15;
        int y = item.getPointOfInterestY() - 15;
        final int number = ts.getNumber();
        final int reference = ts.getReference();
        g.drawImage(AwtRenderersResources.m_digitsImage, x, y, x + 19, y + 15, (number - 1) * 19, 0, number * 19, 15, null);
        y += 15;
        g.drawImage(AwtRenderersResources.m_digitsImage, x, y, x + 19, y + 15, (reference - 1) * 19, 0, reference * 19, 15, null);
        this.drawEditingSurroundingHelper(g, item);
    }
    
    private void paintHoockedNoteGroup(final Graphics2D g, final HoockedNoteGroup hoocked, final LocationItem l) {
        final int LINE_PART_WIDTH = 7;
        final int SECOND_LINE_OFFSET = 6;
        final int notesCount = hoocked.size();
        if (notesCount < 2) {
            hoocked.get(0).setFlag(1, true);
        }
        else {
            final GroupLine line = hoocked.getGroupLine();
            final int x1 = line.getX1();
            final int x2 = line.getX2();
            int y1 = (int)line.getOriginOrdinate();
            int y2 = line.getY(x2 - x1);
            final int direction = line.getDirection();
            g.fillPolygon(new int[] { x1, x1, x2 + 1, x2 + 1 }, new int[] { y1 - 2, y1 + 2, y2 + 2, y2 - 2 }, 4);
            final Iterator<LocationItem> i = hoocked.iterator();
            boolean prevIsSixteenth = false;
            int prevX = 0;
            while (i.hasNext()) {
                final LocationItem item = i.next();
                final Note note = (Note)item.getElement();
                item.setFlag(2, direction == 1);
                item.setFlag(3, direction == -1);
                final int x3 = item.getPointOfInterestX() + ((direction == -1) ? -5 : 3);
                final int y3 = line.getY(x3 - x1);
                if (!note.isRest()) {
                    g.drawLine(x3, y3, x3, item.getPointOfInterestY());
                }
                if (note.getFigure().getType() == 5) {
                    if (prevIsSixteenth) {
                        y1 = line.getY(prevX - x1) + 6 * direction;
                        y2 = line.getY(x3 - x1) + 6 * direction;
                        g.fillPolygon(new int[] { prevX, prevX, x3 + 1, x3 + 1 }, new int[] { y1 - 2, y1 + 2, y2 + 2, y2 - 2 }, 4);
                    }
                    else if (i.hasNext()) {
                        y1 = line.getY(x3 - x1) + 6 * direction;
                        y2 = line.getY(x3 + 7 - x1) + 6 * direction;
                        g.fillPolygon(new int[] { x3, x3, x3 + 7 + 1, x3 + 7 + 1 }, new int[] { y1 - 2, y1 + 2, y2 + 2, y2 - 2 }, 4);
                    }
                    else {
                        y1 = line.getY(x3 - 7 - x1) + 6 * direction;
                        y2 = line.getY(x3 - x1) + 6 * direction;
                        g.fillPolygon(new int[] { x3 - 7, x3 - 7, x3 + 1, x3 + 1 }, new int[] { y1 - 2, y1 + 2, y2 + 2, y2 - 2 }, 4);
                    }
                    prevIsSixteenth = true;
                    prevX = x3;
                }
                else {
                    prevIsSixteenth = false;
                }
            }
        }
    }
    
    protected void paintTiedNoteGroup(final Graphics2D g, final TiedNoteGroup tiedNotes, final LocationItem item) {
        final int TIE_HEIGHT = 8;
        final LocationItem firstNote = tiedNotes.get(0);
        final LocationItem secondNote = tiedNotes.get(1);
        if (firstNote.getLine() == secondNote.getLine()) {
            final int width = secondNote.getX1() - firstNote.getX1() - 15;
            final int x = firstNote.getX1() + 15;
            final int y = firstNote.getPointOfInterestY() - 4 + 5;
            g.drawArc(x, y, width, 8, 0, -180);
        }
        else {
            final int width = 30;
            int x = firstNote.getX1() + 15;
            int y = firstNote.getPointOfInterestY() - 4 + 5;
            g.drawArc(x, y, width, 8, 270, -90);
            x = secondNote.getX1() - 30;
            y = secondNote.getPointOfInterestY() - 4 + 5;
            g.drawArc(x, y, width, 8, 270, 90);
        }
    }
    
    protected void paintTripletGroup(final Graphics2D g, final TripletGroup tripletGroup, final LocationItem item) {
        final int TRIPLET_SIGN_GAP = 10;
        final int BRACE_HEIGHT = 4;
        final GroupLine line = tripletGroup.getGroupLine();
        int x1 = line.getX1() - 8;
        int x2 = line.getX2();
        if (tripletGroup.getTripletFigure().isHookable()) {
            final int direction = line.getDirection();
            final int x3 = (x2 - x1) / 2;
            final int y = line.getY(x3) - 10 * direction + 4;
            g.drawString("3", x3 + x1, y);
        }
        else {
            final int direction = line.getDirection();
            final int x3 = (x2 - x1) / 2;
            final int y = line.getY(x3) - 20 * direction + 4;
            g.drawString("3", x3 + x1, y);
            x1 -= 4;
            x2 += 4;
            final int y2 = (int)line.getOriginOrdinate() - 10 * direction;
            final int y3 = line.getY(x2 - x1) - 10 * direction;
            g.drawLine(x1, y2, x2, y3);
            g.drawLine(x1, y2, x1, y2 + 4 * direction);
            g.drawLine(x2, y3, x2, y3 + 4 * direction);
        }
    }
    
    protected void paintChord(final Graphics2D g, final Chord chord, final LocationItem item) {
        if (!item.getFlag(5) && !item.getFlag(4)) {
            g.drawString(chord.getName(), item.m_x1, item.m_y1 + g.getFontMetrics().getHeight());
        }
    }
    
    protected void paintAccompaniment(final Graphics2D g, final Accompaniment acc, final LocationItem item) {
        this.paintChord(g, acc.getChord(), item);
        this.drawEditingSurroundingHelper(g, item);
    }
    
    protected void paintSilence(final Graphics2D g, final Silence silence, final LocationItem item) {
        this.drawEditingSurroundingHelper(g, item);
    }
    
    protected void paintLyrics(final Graphics2D g, final Lyrics lyrics, final LocationItem item) {
        if (!item.getFlag(4)) {
            g.setFont(AwtRenderersResources.m_lyricsFont);
            g.setColor(AwtRenderersResources.m_lyricsForeground);
            final int y = item.getY2() - g.getFontMetrics().getHeight() / 2;
            g.drawString(lyrics.getText(), item.getX1(), y);
            this.drawEditingSurroundingHelper(g, item);
        }
    }
    
    protected void drawEditingSurroundingHelper(final Graphics2D g, final LocationItem item) {
        if (this.getDrawEditingHelpers() && GlobalPreferences.isEditingHelpersDisplayed()) {
            final float[] dash = { 5.0f };
            final Stroke prevStroke = g.getStroke();
            g.setStroke(new BasicStroke(0.0f, 0, 0, 5.0f, dash, 0.0f));
            g.setColor(Color.DARK_GRAY);
            g.drawRect(item.getX1(), item.getY1(), item.getWidth() - 1, item.getHeight() - 1);
            g.setStroke(prevStroke);
        }
    }
}
