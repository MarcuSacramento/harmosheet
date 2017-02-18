// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.io.score;

import javax.xml.transform.Transformer;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import harmotab.renderer.LocationItem;
import harmotab.sound.SoundItem;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.Node;
import harmotab.element.Element;
import harmotab.track.Track;
import javax.xml.parsers.DocumentBuilderFactory;
import harmotab.sound.SoundSequence;
import java.awt.image.BufferedImage;
import java.awt.GraphicsConfiguration;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.awt.Point;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import harmotab.renderer.ElementRendererBundle;
import harmotab.renderer.AwtPrintingElementRendererBundle;
import harmotab.renderer.ScoreRenderer;
import java.util.Iterator;
import harmotab.renderer.LocationList;
import harmotab.sound.ScorePlayer;
import harmotab.sound.ScorePlayerController;
import harmotab.core.ScoreController;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.zip.ZipOutputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import harmotab.core.Score;
import java.io.File;
import java.util.ArrayList;

public class HT3XScoreWriter extends ScoreWriter
{
    private static final int BUFFER_SIZE = 2048;
    private static final int SOURCE_FILE = 0;
    private static final int IMAGE_FILE = 1;
    private static final int SOUND_FILE = 2;
    private static final int MAPPING_FILE = 3;
    protected ArrayList<File> m_temporaryFilesPaths;
    
    public HT3XScoreWriter(final Score score, final String path) {
        super(score, path);
        (this.m_temporaryFilesPaths = new ArrayList<File>(4)).add(null);
        this.m_temporaryFilesPaths.add(null);
        this.m_temporaryFilesPaths.add(null);
        this.m_temporaryFilesPaths.add(null);
    }
    
    @Override
    protected void write(final Score score, final File file) throws IOException {
        try {
            this.createTemporaryFiles();
            BufferedInputStream origin = null;
            final FileOutputStream dest = new FileOutputStream(file);
            final ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            final byte[] data = new byte[2048];
            for (int i = 0; i < this.m_temporaryFilesPaths.size(); ++i) {
                final File currentFile = this.m_temporaryFilesPaths.get(i);
                final FileInputStream fi = new FileInputStream(currentFile);
                origin = new BufferedInputStream(fi, 2048);
                final ZipEntry entry = new ZipEntry(this.m_temporaryFilesPaths.get(i).getName());
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, 2048)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }
            out.close();
            this.deleteTemporaryFiles();
        }
        catch (Throwable exception) {
            this.deleteTemporaryFiles();
            throw new IOException(exception);
        }
    }
    
    protected void createTemporaryFiles() throws IOException {
        final ScoreController controller = new ScoreController(this.m_score);
        final File ht3File = File.createTempFile("src_", ".ht3");
        ht3File.deleteOnExit();
        this.m_temporaryFilesPaths.set(0, ht3File);
        controller.saveScoreAs(this.m_temporaryFilesPaths.get(0).getAbsolutePath());
        final File pngFile = File.createTempFile("img_", ".png");
        pngFile.deleteOnExit();
        this.m_temporaryFilesPaths.set(1, pngFile);
        final LocationList locations = this.createImage(this.m_temporaryFilesPaths.get(1).getAbsolutePath());
        final File midFile = File.createTempFile("snd_", ".mid");
        midFile.deleteOnExit();
        this.m_temporaryFilesPaths.set(2, midFile);
        controller.exportAsMidi(this.m_temporaryFilesPaths.get(2).getAbsolutePath());
        final File smapFile = File.createTempFile("mapping_", ".smap");
        smapFile.deleteOnExit();
        this.m_temporaryFilesPaths.set(3, smapFile);
        final ScorePlayerController scorePlayerController = new ScorePlayerController(null, this.m_score);
        this.createSmapFile(locations, scorePlayerController.createSoundSequence(), this.m_temporaryFilesPaths.get(3).getAbsolutePath());
    }
    
    protected void deleteTemporaryFiles() {
        for (final File file : this.m_temporaryFilesPaths) {
            if (file != null && !file.delete()) {
                System.err.println("Error deleting file " + file.getAbsolutePath());
            }
        }
    }
    
    private LocationList createImage(final String path) throws IOException {
        final ScoreRenderer renderer = new ScoreRenderer(this.m_score);
        renderer.setElementRenderer(new AwtPrintingElementRendererBundle());
        final int width = 900;
        int height = Integer.MAX_VALUE;
        renderer.setPageSize(width, height);
        renderer.setMultiline(true);
        final LocationList locations = new LocationList();
        renderer.layout(locations);
        height = locations.getBottomOrdinate() + renderer.getInterlineHeight();
        final GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        final BufferedImage image = graphicsConfiguration.createCompatibleImage(width, height);
        final Graphics2D g2d = (Graphics2D)image.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        renderer.paint(g2d, locations, new Point(0, 0));
        ImageIO.write(image, "PNG", new File(path));
        return locations;
    }
    
    private void createSmapFile(final LocationList locations, final SoundSequence sounds, final String path) throws IOException {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document doc = builder.newDocument();
            final org.w3c.dom.Element root = doc.createElement("harmotab");
            root.setAttribute("file-format-version", "3.0");
            root.setAttribute("file-type", "score-export-mapping");
            root.setAttribute("harmotab-version", "3.0");
            for (final Track track : this.m_score) {
                final int trackId = track.getTrackIndex();
                for (final Element element : track) {
                    final SoundItem soundItem = sounds.get(element);
                    final LocationItem locationItem = locations.get(element);
                    if (soundItem != null && locationItem != null) {
                        final org.w3c.dom.Element item = doc.createElement("element");
                        item.setAttribute("trackId", Integer.toString(trackId));
                        item.setAttribute("elementId", "#" + Integer.toHexString(element.hashCode()));
                        item.setAttribute("startTime", Float.toString(soundItem.m_startTime));
                        item.setAttribute("duration", Float.toString(soundItem.m_durationTime));
                        item.setAttribute("x", Integer.toString(locationItem.getX1()));
                        item.setAttribute("y", Integer.toString(locationItem.getY1()));
                        item.setAttribute("width", Integer.toString(locationItem.getWidth()));
                        item.setAttribute("height", Integer.toString(locationItem.getHeight()));
                        root.appendChild(item);
                    }
                }
            }
            doc.appendChild(root);
            final Source source = new DOMSource(doc);
            final Result resultat = new StreamResult(path);
            final TransformerFactory fabrique = TransformerFactory.newInstance();
            final Transformer transformer = fabrique.newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("encoding", "ISO-8859-1");
            transformer.transform(source, resultat);
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        catch (TransformerException e2) {
            e2.printStackTrace();
            throw new IOException(e2);
        }
    }
}
