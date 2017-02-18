// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.sound;

import java.io.OutputStream;
import java.io.FileNotFoundException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFileFormat;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Line;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import java.io.File;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioFormat;

public class PcmRecorder extends Recorder
{
    protected AudioFormat m_audioFormat;
    protected TargetDataLine m_line;
    protected File m_cacheFile;
    protected Thread m_runningThread;
    protected boolean m_running;
    protected boolean m_openned;
    protected float m_lastLevel;
    
    public PcmRecorder() {
        this.m_audioFormat = null;
        this.m_line = null;
        this.m_cacheFile = null;
        this.m_runningThread = null;
        this.m_running = false;
        this.m_openned = false;
        this.m_lastLevel = 0.0f;
        this.m_running = false;
        this.m_openned = false;
        this.m_lastLevel = 0.0f;
        this.m_audioFormat = new AudioFormat(22050.0f, 16, 1, true, true);
    }
    
    @Override
    public boolean isRunning() throws RecorderException {
        return this.m_running;
    }
    
    @Override
    public boolean isOpenned() throws RecorderException {
        return this.m_openned;
    }
    
    @Override
    public void open() throws RecorderException {
        if (this.m_openned) {
            throw new IllegalStateException("Recorder already openned.");
        }
        final DataLine.Info info = new DataLine.Info(TargetDataLine.class, this.m_audioFormat);
        if (!AudioSystem.isLineSupported(info)) {
            System.err.println("Audio Format specified is not supported");
            return;
        }
        try {
            this.m_line = (TargetDataLine)AudioSystem.getLine(info);
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
            return;
        }
        this.m_openned = true;
        this.m_running = false;
        this.fireRecorderOpened();
    }
    
    @Override
    public void start() throws RecorderException {
        if (!this.m_openned) {
            throw new IllegalStateException("Recorder not openned.");
        }
        if (this.m_running) {
            throw new IllegalStateException("Recorder already running.");
        }
        try {
            this.createNewCacheFile();
        }
        catch (IOException e) {
            throw new RecorderException(e);
        }
        try {
            this.m_line.open(this.m_audioFormat);
        }
        catch (LineUnavailableException e2) {
            e2.printStackTrace();
            return;
        }
        this.m_line.flush();
        this.m_line.start();
        new RecordingThread().start();
    }
    
    @Override
    public void startMonitoring() throws RecorderException {
        if (!this.m_openned) {
            throw new IllegalStateException("Recorder not openned.");
        }
        if (this.m_running) {
            throw new IllegalStateException("Recorder already running.");
        }
        try {
            this.m_line.open(this.m_audioFormat);
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
            return;
        }
        this.m_line.flush();
        this.m_line.start();
        new MonitoringThread().start();
    }
    
    @Override
    public void stop() {
        this.m_line.stop();
        this.m_line.drain();
        this.m_line.close();
        this.m_running = false;
        try {
            this.m_runningThread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void save(final String path) {
        if (this.m_running) {
            throw new IllegalStateException("Recorder currently recording");
        }
        if (this.m_cacheFile == null) {
            throw new IllegalStateException("No sample recorded.");
        }
        FileChannel in = null;
        FileChannel out = null;
        try {
            in = new FileInputStream(this.m_cacheFile).getChannel();
            out = new FileOutputStream(path).getChannel();
            in.transferTo(0L, in.size(), out);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException ex) {}
            }
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException ex2) {}
            }
        }
        if (in != null) {
            try {
                in.close();
            }
            catch (IOException ex3) {}
        }
        if (out != null) {
            try {
                out.close();
            }
            catch (IOException ex4) {}
        }
    }
    
    @Override
    public void close() {
        if (!this.m_openned) {
            throw new IllegalStateException("Recorder not openned.");
        }
        if (this.m_running) {
            this.stop();
        }
        if (this.m_cacheFile != null) {
            this.m_cacheFile.delete();
        }
        this.m_openned = false;
        this.m_running = false;
        this.fireRecorderClosed();
    }
    
    @Override
    public float getLevel() throws RecorderException {
        if (!this.m_running) {
            throw new IllegalStateException("Monitoring not running !");
        }
        return this.m_lastLevel;
    }
    
    @Override
    public float getPositionSec() throws RecorderException {
        return this.m_line.getMicrosecondPosition() * 1000000.0f;
    }
    
    private void createNewCacheFile() throws IOException {
        if (this.m_cacheFile != null) {
            this.m_cacheFile.delete();
        }
        (this.m_cacheFile = File.createTempFile("ht3-tmp-", ".pcm")).deleteOnExit();
    }
    
    private class RecordingThread extends Thread
    {
        @Override
        public void run() {
            PcmRecorder.this.m_running = true;
            PcmRecorder.this.m_runningThread = this;
            PcmRecorder.this.fireRecordingStarted();
            final AudioFileFormat.Type targetType = AudioFileFormat.Type.AU;
            final AudioInputStream audioInputStream = new AudioInputStream(PcmRecorder.this.m_line);
            OutputStream output = null;
            try {
                output = new FileOutputStream(PcmRecorder.this.m_cacheFile);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }
            Label_0149: {
                try {
                    AudioSystem.write(audioInputStream, targetType, output);
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                    try {
                        audioInputStream.close();
                        output.close();
                    }
                    catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    break Label_0149;
                }
                finally {
                    try {
                        audioInputStream.close();
                        output.close();
                    }
                    catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                try {
                    audioInputStream.close();
                    output.close();
                }
                catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            PcmRecorder.this.m_running = false;
            PcmRecorder.this.fireRecordingStopped();
        }
    }
    
    private class MonitoringThread extends Thread
    {
        private static final int BUFFER_SIZE = 2048;
        
        @Override
        public void run() {
            final byte[] buffer = new byte[2048];
            int read = 0;
            PcmRecorder.this.m_running = true;
            PcmRecorder.this.m_runningThread = this;
            PcmRecorder.this.fireMonitoringStarted();
            final AudioInputStream audioInputStream = new AudioInputStream(PcmRecorder.this.m_line);
            try {
                while ((read = audioInputStream.read(buffer, 0, 2048)) > 0) {
                    float sum = 0.0f;
                    for (int i = 1; i < read; i += 2) {
                        sum += Math.abs(buffer[i]);
                    }
                    PcmRecorder.this.m_lastLevel = (127.0f - sum / (read / 2)) / 127.0f;
                    audioInputStream.skip(audioInputStream.available());
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            PcmRecorder.this.m_running = false;
            PcmRecorder.this.fireMonitoringStopped();
        }
    }
}
