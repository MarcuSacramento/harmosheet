// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.performance;

import java.io.IOException;
import java.util.Iterator;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;
import harmotab.core.Score;
import harmotab.io.score.HT3XScoreWriter;

public class Ht3XPerformanceWriter extends HT3XScoreWriter
{
    protected PerformancesList m_performanceList;
    
    public Ht3XPerformanceWriter(final Score score, final String path, final PerformancesList performancesList) {
        super(score, path);
        this.setPerformanceList(performancesList);
    }
    
    public PerformancesList getPerformanceList() {
        return this.m_performanceList;
    }
    
    public void setPerformanceList(final PerformancesList performanceList) {
        this.m_performanceList = performanceList;
    }
    
    @Override
    protected void createTemporaryFiles() throws IOException {
        super.createTemporaryFiles();
        final PerformanceListWriter writer = new PerformanceListWriter();
        final File performanceListFile = File.createTempFile("perfs_", ".perfslist");
        performanceListFile.deleteOnExit();
        final FileOutputStream fos = new FileOutputStream(performanceListFile);
        writer.write(fos, this.m_performanceList);
        fos.close();
        this.m_temporaryFilesPaths.add(performanceListFile);
        for (final Performance perf : this.m_performanceList) {
            this.m_temporaryFilesPaths.add(perf.getFile());
        }
    }
}
