// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop;

import javax.swing.SwingUtilities;
import harmotab.core.HarmoTabObjectEvent;
import harmotab.renderer.LocationItemFlag;
import java.util.ListIterator;
import harmotab.core.undo.UndoManager;
import harmotab.desktop.tools.Tool;
import java.awt.Point;
import harmotab.track.Track;
import java.awt.Container;
import harmotab.desktop.tools.ToolFactory;
import java.awt.Component;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import harmotab.renderer.renderingelements.EmptyArea;
import harmotab.renderer.ScoreRenderer;
import harmotab.renderer.RenderingMode;
import harmotab.sound.SoundItem;
import harmotab.core.Localizer;
import harmotab.sound.ScorePlayerEvent;
import java.awt.image.ImageObserver;
import java.awt.Graphics2D;
import java.awt.Graphics;
import harmotab.element.Element;
import harmotab.element.TrackElement;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentEvent;
import harmotab.core.HarmoTabObjectListener;
import java.awt.LayoutManager;
import harmotab.core.ScoreView;
import harmotab.core.ScoreController;
import java.awt.Cursor;
import java.awt.Color;
import harmotab.renderer.LocationItem;
import harmotab.core.ScoreViewSelection;
import harmotab.renderer.LocationList;
import harmotab.sound.ScorePlayer;
import harmotab.core.Score;
import java.awt.event.KeyListener;
import harmotab.core.ScoreControllerListener;
import harmotab.sound.ScorePlayerListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.ComponentListener;
import harmotab.core.ScoreViewListener;
import javax.swing.JPanel;

public class ScorePane extends JPanel implements ScoreViewListener, ComponentListener, MouseListener, MouseMotionListener, MouseWheelListener, ScorePlayerListener, ScoreControllerListener, KeyListener
{
    private static final long serialVersionUID = 1L;
    private Score m_score;
    private ImageScoreView m_scoreView;
    private ScorePlayer m_player;
    private LocationList m_locations;
    private ScoreViewSelection m_selection;
    private LocationItem m_played;
    private LocationItem m_mouseOver;
    private DesktopController m_desktopController;
    private boolean m_allowEdition;
    private static Color ELEMENT_COLOR;
    private static Color NO_ACTION_ELEMENT_COLOR;
    private static Color STAFF_COLOR;
    private static Color TEMPORARY_ELEMENT_COLOR;
    private static Color OVER_COLOR;
    private static Color PLAYED_ELEMENT_COLOR;
    private static Cursor DEFAULT_CURSOR;
    private static Cursor ELEMENT_CURSOR;
    
    static {
        ScorePane.ELEMENT_COLOR = new Color(16777152);
        ScorePane.NO_ACTION_ELEMENT_COLOR = new Color(10404831);
        ScorePane.STAFF_COLOR = new Color(16316664);
        ScorePane.TEMPORARY_ELEMENT_COLOR = new Color(12648384);
        ScorePane.OVER_COLOR = new Color(15790320);
        ScorePane.PLAYED_ELEMENT_COLOR = new Color(12510207);
        ScorePane.DEFAULT_CURSOR = new Cursor(0);
        ScorePane.ELEMENT_CURSOR = new Cursor(12);
    }
    
    public ScorePane(final ScoreController controller) {
        this.m_score = null;
        this.m_scoreView = null;
        this.m_player = null;
        this.m_locations = null;
        this.m_selection = null;
        this.m_played = null;
        this.m_mouseOver = null;
        this.m_desktopController = null;
        this.m_allowEdition = true;
        this.m_player = null;
        this.m_desktopController = DesktopController.getInstance();
        this.m_scoreView = new ImageScoreView(controller);
        DesktopController.getInstance().setScoreView(this.m_scoreView);
        this.setSoundPlayer(controller.getScorePlayer());
        this.setScore(controller.getScore());
        this.setLayout(new SmoothLayout());
        this.setFocusable(true);
        this.addComponentListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        this.addKeyListener(this);
        this.m_scoreView.addScoreViewListener(this);
        controller.addScoreControllerListener(this);
        this.m_scoreView.refresh();
    }
    
    protected void setScore(final Score score) {
        this.m_score = score;
        this.m_locations = new LocationList();
        this.unselect();
        if (this.m_score != null) {
            this.m_score.addObjectListener(new ScoreChangesObserver((ScoreChangesObserver)null));
            this.m_allowEdition = this.m_scoreView.getScoreController().isScoreEditable();
        }
        else {
            this.setSelected((LocationItem)null, this.m_allowEdition = false);
            this.m_scoreView.refresh();
        }
        this.updateRenderingMode();
        this.m_scoreView.refresh();
    }
    
    public Score getScore() {
        return this.m_score;
    }
    
    public ScoreView getScoreView() {
        return this.m_scoreView;
    }
    
    protected void setSoundPlayer(final ScorePlayer player) {
        if (this.m_player != null) {
            this.m_player.removeSoundPlayerListener(this);
        }
        (this.m_player = player).addSoundPlayerListener(this);
    }
    
    public ScorePlayer getSoundPlayer() {
        return this.m_player;
    }
    
    @Override
    public void componentResized(final ComponentEvent e) {
        this.m_scoreView.setViewSize(this.getWidth(), this.getHeight());
        this.m_scoreView.refresh();
    }
    
    @Override
    public void componentHidden(final ComponentEvent event) {
    }
    
    @Override
    public void componentMoved(final ComponentEvent event) {
    }
    
    @Override
    public void componentShown(final ComponentEvent event) {
    }
    
    @Override
    public void mouseMoved(final MouseEvent event) {
        if (this.updateOverItem()) {
            this.repaint();
        }
    }
    
    @Override
    public void mousePressed(final MouseEvent event) {
        final LocationItem selected = this.m_locations.at(event.getX(), event.getY());
        this.setSelected(selected, true);
        if (event.isPopupTrigger()) {
            this.showPopupMenu(selected, event.getPoint());
        }
    }
    
    @Override
    public void mouseWheelMoved(final MouseWheelEvent event) {
        this.m_scoreView.translateView(event.getWheelRotation() * 30);
    }
    
    @Override
    public void mouseReleased(final MouseEvent event) {
        if (event.isPopupTrigger()) {
            final LocationItem selected = this.m_locations.at(event.getX(), event.getY());
            this.showPopupMenu(selected, event.getPoint());
        }
    }
    
    @Override
    public void mouseClicked(final MouseEvent event) {
    }
    
    @Override
    public void mouseEntered(final MouseEvent event) {
    }
    
    @Override
    public void mouseExited(final MouseEvent event) {
    }
    
    @Override
    public void mouseDragged(final MouseEvent event) {
    }
    
    @Override
    public void keyPressed(final KeyEvent event) {
        Label_0169: {
            if (this.m_selection != null) {
                switch (event.getKeyChar()) {
                    case '\n':
                    case '\u001b': {
                        this.unselect();
                        return;
                    }
                    default: {
                        if (this.m_allowEdition) {
                            this.m_selection.getTool().keyTyped(event);
                        }
                        switch (event.getKeyCode()) {
                            case 39: {
                                this.selectNext();
                                break Label_0169;
                            }
                            case 37: {
                                this.selectPrevious();
                                break Label_0169;
                            }
                            case 8:
                            case 127: {
                                if (this.m_selection != null && this.m_selection.getElement() instanceof TrackElement && !this.m_selection.getLocationItem().getFlag(4)) {
                                    this.m_selection.getTrack().remove(this.m_selection.getElement());
                                    break Label_0169;
                                }
                                break Label_0169;
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
    
    @Override
    public void keyReleased(final KeyEvent event) {
    }
    
    @Override
    public void keyTyped(final KeyEvent event) {
    }
    
    @Override
    public void onScoreViewChanged(final ScoreView scoreView) {
        this.updateRenderingMode();
        this.m_locations = this.m_scoreView.getLocations();
        if (this.m_selection != null) {
            int prevSelectionIndex = this.m_selection.getElementIndex();
            if (!this.m_selection.getLocationItem().getFlag(4) && prevSelectionIndex != -1) {
                if (prevSelectionIndex > this.m_selection.getTrack().size() - 1) {
                    --prevSelectionIndex;
                }
                if (prevSelectionIndex >= 0) {
                    final Element correspondingElement = this.m_selection.getTrack().get(prevSelectionIndex);
                    this.setSelected(this.m_locations.get(correspondingElement), false);
                }
            }
            else {
                final int selIndex = this.m_selection.getLocationItemIndex();
                this.setSelected((selIndex != -1 && selIndex < this.m_locations.getSize()) ? this.m_locations.get(selIndex) : null, false);
            }
        }
        if (this.m_selection != null) {
            this.m_selection.getTool().updateLocation();
        }
        if (this.m_played != null) {
            this.m_played = this.m_locations.get(this.m_played.getElement());
        }
        this.repaint();
    }
    
    public void paintComponent(final Graphics g) {
        final Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        if (this.m_selection != null) {
            final LocationItem selected = this.m_selection.getLocationItem();
            g2d.setColor(ScorePane.STAFF_COLOR);
            g2d.fillRect(0, selected.getY1(), this.getWidth(), selected.getHeight());
            Color color = ScorePane.ELEMENT_COLOR;
            if (selected.getFlag(4)) {
                color = ScorePane.TEMPORARY_ELEMENT_COLOR;
            }
            if (!this.m_allowEdition) {
                color = ScorePane.NO_ACTION_ELEMENT_COLOR;
            }
            g2d.setColor(color);
            g2d.fillRect(selected.getX1(), selected.getY1(), selected.getWidth(), selected.getHeight());
        }
        this.updateOverItem();
        if (this.m_played != null) {
            g2d.setColor(ScorePane.PLAYED_ELEMENT_COLOR);
            g2d.fillRect(this.m_played.getX1(), this.m_played.getY1(), this.m_played.getWidth(), this.m_played.getHeight());
        }
        if (this.m_mouseOver != null && !this.m_player.isPlaying() && (this.m_selection == null || this.m_mouseOver != this.m_selection.getLocationItem())) {
            g2d.setColor(ScorePane.OVER_COLOR);
            g2d.fillRect(this.m_mouseOver.getX1(), this.m_mouseOver.getY1(), this.m_mouseOver.getWidth(), this.m_mouseOver.getHeight());
        }
        g2d.drawImage(this.m_scoreView.getImage(), 0, 0, this);
    }
    
    @Override
    public void onScorePlayerStateChanged(final ScorePlayerEvent event) {
    }
    
    @Override
    public void onScorePlayerError(final ScorePlayerEvent event, final Throwable error) {
        ErrorMessenger.showErrorMessage(Localizer.get("M_SOUND_OUTPUT_OPEN_ERROR"));
        error.printStackTrace();
    }
    
    @Override
    public void onPlayedSoundItemChanged(final ScorePlayerEvent event) {
        final SoundItem item = event.getPlayedItem();
        if (item != null) {
            this.goTo(this.m_played = this.m_locations.get(event.getPlayedItem().getElement()), true);
        }
        else {
            this.m_played = null;
        }
        this.repaint();
    }
    
    @Override
    public void onPlaybackStarted(final ScorePlayerEvent event) {
        this.setSelected(this.m_played = null, true);
        this.updateRenderingMode();
        this.m_scoreView.updateScoreView();
        this.repaint();
    }
    
    @Override
    public void onPlaybackPaused(final ScorePlayerEvent event) {
        this.repaint();
    }
    
    @Override
    public void onPlaybackStopped(final ScorePlayerEvent event, final boolean endOfPlayback) {
        this.m_played = null;
        this.updateRenderingMode();
        this.m_scoreView.updateScoreView();
        this.repaint();
    }
    
    private void updateRenderingMode() {
        final ScoreRenderer renderer = this.m_scoreView.getScoreRenderer();
        if (renderer != null) {
            final boolean onlyView = !this.m_allowEdition || this.m_player.isPlaying();
            renderer.getElementRenderer().setMode(onlyView ? RenderingMode.VIEW_MODE : RenderingMode.EDIT_MODE);
        }
    }
    
    private void unselect() {
        if (this.m_selection != null) {
            this.m_selection.getTool().setVisible(false);
            this.m_selection.getLocationItem().m_isSelection = false;
        }
        this.m_selection = null;
    }
    
    public void setSelected(final LocationItem selected, final boolean scrollOnSelection) {
        this.unselect();
        if (this.m_player.isPlaying()) {
            return;
        }
        if (selected != null) {
            if (selected.getElement() instanceof EmptyArea) {
                if (this.m_allowEdition) {
                    final JPopupMenu menu = new JPopupMenu();
                    final Track track = this.m_score.getTrack(selected.getTrackId());
                    menu.add(AddElementMenu.createInsertLast(track));
                    final Point p = this.getMousePosition();
                    if (p != null) {
                        menu.show(this, p.x, p.y);
                    }
                }
            }
            else {
                final Tool controller = ToolFactory.createController(this, this.m_score, selected);
                if (controller != null) {
                    this.m_selection = new ScoreViewSelection(controller, this.m_locations.getItemIndex(selected));
                    selected.m_isSelection = true;
                    controller.setVisible(this.m_allowEdition);
                    if (scrollOnSelection) {
                        this.goTo(selected);
                    }
                }
            }
        }
        if (this.m_selection != null) {
            this.m_player.setHighlightedTrack(this.m_selection.getTrackId());
            this.m_player.setPlayFromElement(this.m_selection.getElement());
        }
        else {
            this.m_player.setHighlightedTrack(-1);
            this.m_player.setPlayFromElement(null);
        }
        this.m_desktopController.fireSelectionChanged(this.m_selection);
        this.requestFocus();
        this.repaint();
    }
    
    public void setSelected(final Element element, final boolean scrollOnSelection) {
        if (element == null) {
            this.setSelected((LocationItem)null, scrollOnSelection);
        }
        else {
            this.setSelected(this.m_locations.get(element), scrollOnSelection);
        }
    }
    
    public void selectNext() {
        if (!this.m_selection.getLocationItem().getFlag(4)) {
            final Track track = this.m_selection.getTrack();
            if (track != null) {
                final ListIterator<Element> iterator = track.listIterator(this.m_selection.getLocationItem());
                if (iterator != null) {
                    iterator.next();
                    if (iterator.hasNext()) {
                        this.setSelected(iterator.next(), true);
                    }
                    else if (this.m_allowEdition) {
                        final Element newElement = (Element)this.m_selection.getLocationItem().getRootElement().clone();
                        UndoManager.getInstance().addUndoCommand(track.createRestoreCommand(), Localizer.get("ACT_ADD_ELEMENT"));
                        track.add(newElement);
                        this.selectNext();
                    }
                }
                return;
            }
        }
        final int selectionIndex = this.m_selection.getLocationItemIndex();
        if (selectionIndex != -1 && selectionIndex < this.m_locations.getSize() - 1) {
            this.setSelected(this.m_locations.get(selectionIndex + 1), true);
        }
    }
    
    public void selectPrevious() {
        if (!this.m_selection.getLocationItem().getFlag(4)) {
            final Track track = this.m_selection.getTrack();
            if (track != null) {
                final ListIterator<Element> iterator = track.listIterator(this.m_selection.getLocationItem());
                if (iterator != null) {
                    if (iterator.hasPrevious()) {
                        this.setSelected(iterator.previous(), true);
                    }
                    return;
                }
            }
        }
        final int selectionIndex = this.m_selection.getLocationItemIndex();
        if (selectionIndex > 0) {
            this.setSelected(this.m_locations.get(selectionIndex - 1), true);
        }
    }
    
    public void goTo(final LocationItem item) {
        this.goTo(item, false);
    }
    
    public void goTo(final LocationItem item, final boolean centered) {
        if (item == null) {
            return;
        }
        final int lineStart = this.m_scoreView.getLineOffset(item.m_line);
        final int lineEnd = this.m_scoreView.getLineOffset(item.m_line + 1);
        if (centered) {
            this.m_scoreView.translateView(lineStart);
        }
        else {
            if (lineStart < 0) {
                this.m_scoreView.translateView(lineStart);
            }
            if (lineEnd > this.m_scoreView.getViewHeight()) {
                this.m_scoreView.translateView(lineEnd - this.m_scoreView.getViewHeight() + 35);
            }
        }
    }
    
    private boolean updateOverItem() {
        final LocationItem previous = this.m_mouseOver;
        final Point mousePosition = this.getMousePosition();
        if (mousePosition != null) {
            this.m_mouseOver = this.m_locations.at(mousePosition.x, mousePosition.y);
            if (this.m_mouseOver != null && this.m_mouseOver.getFlag(0)) {
                this.setToolTipText(LocationItemFlag.getErrorFlagToolTip(this.m_mouseOver));
            }
            else {
                this.setToolTipText(null);
            }
            this.updateCursor();
            return this.m_mouseOver != previous;
        }
        return false;
    }
    
    public ScoreViewSelection getScorePaneSelection() {
        return this.m_selection;
    }
    
    public void showPopupMenu(final LocationItem item, final Point mousePosition) {
        if (!this.m_allowEdition) {
            return;
        }
        if (item == null) {
            return;
        }
        if (item.getFlag(4)) {
            return;
        }
        if (!(item.getElement() instanceof TrackElement)) {
            return;
        }
        new ElementPopupMenu(this, mousePosition, this.m_score, item);
    }
    
    private void updateCursor() {
        if (this.m_mouseOver != null) {
            this.setCursor(ScorePane.ELEMENT_CURSOR);
        }
        else {
            this.setCursor(ScorePane.DEFAULT_CURSOR);
        }
    }
    
    @Override
    public void onControlledScoreChanged(final ScoreController controller, final Score scoreControlled) {
        this.setScore(scoreControlled);
    }
    
    @Override
    public void onScorePlayerChanged(final ScoreController controller, final ScorePlayer soundPlayer) {
        this.setSoundPlayer(soundPlayer);
    }
    
    private class ScoreChangesObserver implements HarmoTabObjectListener
    {
        private Element m_element;
        
        private ScoreChangesObserver() {
            this.m_element = null;
        }
        
        @Override
        public void onObjectChanged(final HarmoTabObjectEvent event) {
            if (event.hierarchyContains("elementListChanged")) {
                final HarmoTabObjectEvent elementEvent = event.getHierarchyEvent("elementListChanged").getParent();
                final Element element = (Element)elementEvent.getSource();
                this.m_element = element;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ScorePane.this.setSelected(ScoreChangesObserver.this.m_element, true);
                    }
                });
            }
        }
    }
}
