// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import harmotab.sound.RecorderException;
import harmotab.sound.Recorder;
import javax.swing.JProgressBar;

public class InputLevelViewer extends JProgressBar
{
    private static final long serialVersionUID = 1L;
    Thread inputLevelViewerThread;
    protected Recorder m_recorder;
    protected boolean m_run;
    
    public InputLevelViewer(final Recorder recorder) {
        super(0, 0, 100);
        this.m_recorder = recorder;
        this.m_run = false;
    }
    
    public void start() {
        this.m_run = true;
        try {
            this.m_recorder.open();
            this.m_recorder.startMonitoring();
            (this.inputLevelViewerThread = new InputLevelViewerThread((InputLevelViewerThread)null)).start();
        }
        catch (RecorderException e) {
            e.printStackTrace();
        }
    }
    
    public void stop() {
        this.m_run = false;
        try {
            this.inputLevelViewerThread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            this.m_recorder.stop();
            this.m_recorder.close();
        }
        catch (RecorderException e2) {
            e2.printStackTrace();
        }
    }
    
    private class InputLevelViewerThread extends Thread
    {
        @Override
        public void run() {
            try {
                while (InputLevelViewer.this.m_run && InputLevelViewer.this.m_recorder.isOpenned()) {
                    if (!InputLevelViewer.this.m_recorder.isRunning()) {
                        break;
                    }
                    final int value = (int)(InputLevelViewer.this.m_recorder.getLevel() * 100.0f);
                    InputLevelViewer.this.setValue(value);
                    try {
                        Thread.sleep(20L);
                    }
                    catch (InterruptedException ex) {}
                }
            }
            catch (RecorderException e) {
                e.printStackTrace();
            }
        }
    }
}
