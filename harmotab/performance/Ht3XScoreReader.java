// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.performance;

import java.io.FileOutputStream;
import harmotab.throwables.FileFormatException;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.Map;
import java.io.IOException;
import harmotab.io.score.HT3ScoreReader;
import rvt.util.gui.FileUtilities;
import java.io.InputStream;
import java.util.zip.ZipInputStream;
import java.util.HashMap;
import java.io.FileInputStream;
import java.io.File;
import harmotab.core.Score;
import harmotab.io.score.ScoreReader;

public class Ht3XScoreReader extends ScoreReader
{
    protected PerformancesList m_performanceList;
    
    public Ht3XScoreReader(final Score score, final String path) {
        super(score, path);
        this.m_performanceList = null;
        this.m_performanceList = null;
    }
    
    @Override
    public boolean isExportedScore() {
        return true;
    }
    
    @Override
    public PerformancesList getPerformancesList() {
        return this.m_performanceList;
    }
    
    protected void setPerformancesList(final PerformancesList list) {
        this.m_performanceList = list;
    }
    
    @Override
    protected void read(final Score score, final File file) throws IOException, FileFormatException {
        final FileInputStream input = new FileInputStream(file);
        final Map<String, File> oldNamesMapping = new HashMap<String, File>();
        this.m_performanceList = new PerformancesList();
        try {
            final ZipInputStream zin = new ZipInputStream(input);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                final String entryName = ze.getName();
                if (entryName.endsWith(".ht3")) {
                    final File scoreFile = this.writeAsTemporaryFile(zin, FileUtilities.getNameWithoutExtension(ze.getName()), ".ht3");
                    final HT3ScoreReader reader = new HT3ScoreReader(score, scoreFile.getAbsolutePath());
                    reader.open();
                }
                else {
                    if (entryName.endsWith(".mid")) {
                        continue;
                    }
                    if (entryName.endsWith(".png")) {
                        continue;
                    }
                    if (entryName.endsWith(".smap")) {
                        continue;
                    }
                    if (entryName.endsWith(".perfslist")) {
                        final File tempFile = this.writeAsTemporaryFile(zin, "ht_", ".xml");
                        final InputStream inStream = new FileInputStream(tempFile);
                        final PerformanceListReader reader2 = new PerformanceListReader();
                        reader2.read(this.m_performanceList, inStream);
                        inStream.close();
                        tempFile.delete();
                    }
                    else {
                        if (!entryName.endsWith(".pcm")) {
                            continue;
                        }
                        final File newPcmFile = this.writeAsTemporaryFile(zin, "perf_", ".pcm");
                        oldNamesMapping.put(ze.getName(), newPcmFile);
                    }
                }
            }
            try {
                zin.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            for (final Performance perf : this.m_performanceList) {
                final File newFile = oldNamesMapping.get(perf.getFile().getName());
                if (newFile != null) {
                    perf.setFile(newFile);
                }
                else {
                    System.err.println("HT3XScoreReader: Extracted sound file for performance " + perf.getName() + " not found !");
                }
            }
        }
        catch (Exception e2) {
            throw new IOException(e2.getMessage());
        }
    }
    
    protected File writeAsTemporaryFile(final InputStream is, final String prefix, final String suffix) throws IOException {
        final File file = File.createTempFile(prefix, suffix);
        file.deleteOnExit();
        final FileOutputStream os = new FileOutputStream(file);
        int read = 0;
        final byte[] buffer = new byte[4096];
        while ((read = is.read(buffer, 0, buffer.length)) != -1) {
            os.write(buffer, 0, read);
        }
        return file;
    }
}
