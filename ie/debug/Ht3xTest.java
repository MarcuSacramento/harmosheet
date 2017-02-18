// 
// Decompiled by Procyon v0.5.30
// 

package ie.debug;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.io.File;
import harmotab.performance.Performance;
import java.io.IOException;
import harmotab.core.ScoreController;
import harmotab.desktop.ErrorMessenger;
import harmotab.harmonica.Harmonica;

public class Ht3xTest
{
    private Harmonica h;
    
    public Ht3xTest() {
        this.h = new Harmonica();
        ErrorMessenger.setConsoleMode();
        try {
            final ScoreController controller = new ScoreController();
            controller.open("C:\\Temp\\Test\\test.ht3x");
            controller.getPerformancesList().add(this.createTestPerf());
            controller.getPerformancesList().add(this.createTestPerf());
            controller.saveAs("C:\\Temp\\Test\\test2.ht3x");
            System.out.println("=> save as ok");
            System.out.println("");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            final ScoreController controller = new ScoreController();
            controller.open("C:\\Temp\\Test\\test2.ht3x");
            System.out.println("=> open ok");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Performance createTestPerf() {
        File file = null;
        Performance perf = null;
        try {
            file = File.createTempFile("perf_", ".pcm");
            file.deleteOnExit();
            perf = new Performance(file, "test", this.h);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return perf;
    }
    
    public static void main(final String[] args) {
        new Ht3xTest();
    }
    
    public class Rtx1DataManager
    {
        private ArrayList<Integer> patientIdsList;
        
        public Rtx1DataManager() {
            this.patientIdsList = null;
            this.patientIdsList = new ArrayList<Integer>();
        }
        
        public void exportAllData() {
            this.importPatientData();
            this.patientIdsList.contains(new Integer(40));
        }
        
        public void exportPatientData() {
            this.patientIdsList.clear();
        }
        
        public void importPatientData() {
            this.patientIdsList.clear();
            this.patientIdsList.retainAll(this.patientIdsList);
            for (Integer i : this.patientIdsList) {
                i = Integer.signum(Integer.bitCount(i));
            }
        }
    }
}
