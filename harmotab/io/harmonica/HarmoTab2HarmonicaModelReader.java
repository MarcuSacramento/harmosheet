// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.io.harmonica;

import harmotab.throwables.OutOfBoundsError;
import harmotab.element.Tab;
import harmotab.core.Height;
import java.io.Reader;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import harmotab.harmonica.HarmonicaModel;

public class HarmoTab2HarmonicaModelReader extends HarmonicaModelReader
{
    public HarmoTab2HarmonicaModelReader(final HarmonicaModel model) {
        super(model);
    }
    
    @Override
    public void read(final File file) throws IOException {
        final String filename = file.getName();
        if (filename.endsWith(".md")) {
            this.m_model.setName(filename.substring(0, filename.length() - 3));
        }
        else {
            this.m_model.setName(filename);
        }
        try {
            this.readHarmoTab2HarmonicaModel(new FileInputStream(file));
            this.strechOcatves();
        }
        catch (FileNotFoundException e) {
            throw new IOException(e);
        }
    }
    
    private void readHarmoTab2HarmonicaModel(final InputStream input) {
        try {
            final Reader reader = new InputStreamReader(input);
            final Scanner scanner = new Scanner(reader);
            scanner.useDelimiter("fs17 ");
            scanner.next();
            int index = 0;
            while (scanner.hasNext()) {
                if (index++ != 0) {
                    this.extractHt2Model(scanner.next());
                }
            }
            input.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    private void extractHt2Model(final String input) {
        final Scanner scanner = new Scanner(input);
        scanner.useDelimiter("'a4");
        int index = 0;
        int numberOfHoles = 0;
        final boolean[] altFilled = new boolean[6];
        final int octaveOffset = new Height((byte)0, 3).getSoundId();
        while (scanner.hasNext()) {
            String field = scanner.next();
            field = field.substring(0, field.length() - 1);
            if (index % 6 == 0) {
                for (int alt = 0; alt < 6; ++alt) {
                    altFilled[alt] = false;
                }
            }
            if (field.length() > 0 && field.charAt(0) >= '0') {
                try {
                    final byte alt2 = (byte)(index % 6);
                    final int note = Integer.parseInt(field);
                    if (note > 0) {
                        altFilled[alt2] = true;
                        final int hole = index / 6 + 1;
                        if (hole > this.m_model.getNumberOfHoles()) {
                            this.m_model.setNumberOfHoles(hole + hole / 2);
                        }
                        final Height height = new Height(note - 1 + octaveOffset);
                        this.m_model.setHeight(HarmonicaModel.createTab(hole, alt2), height);
                    }
                }
                catch (NumberFormatException exception) {
                    System.out.println("extractHt2Model NumberFormatException");
                }
                catch (Error error) {
                    error.printStackTrace();
                }
            }
            if (++index % 6 == 0) {
                boolean filled = false;
                for (int alt3 = 0; alt3 < 6; ++alt3) {
                    if (altFilled[alt3]) {
                        filled = true;
                    }
                }
                if (!filled) {
                    continue;
                }
                numberOfHoles = index / 6;
            }
        }
        this.m_model.setNumberOfHoles(numberOfHoles);
    }
    
    private void strechOcatves() {
        final int numberOfHoles = this.m_model.getNumberOfHoles();
        Height currentHeight = this.m_model.getHeight(new Tab(1, (byte)1, (byte)0));
        int maxSoundId = currentHeight.getSoundId();
        int currentOctave = currentHeight.getOctave();
        for (int hole = 1; hole <= numberOfHoles; ++hole) {
            for (byte type = 0; type < 6; ++type) {
                final Tab currentTab = HarmonicaModel.createTab(hole, type);
                currentHeight = this.m_model.getHeight(currentTab);
                if (currentHeight != null) {
                    currentHeight.setOctave(currentOctave);
                    int currentSoundId = currentHeight.getSoundId();
                    if (currentSoundId > maxSoundId + 6) {
                        currentHeight.setOctave(--currentOctave);
                    }
                    if (currentSoundId < maxSoundId - 6) {
                        currentHeight.setOctave(++currentOctave);
                    }
                    currentSoundId = currentHeight.getSoundId();
                    if (currentSoundId > maxSoundId) {
                        maxSoundId = currentSoundId;
                    }
                    try {
                        final Height height = new Height(currentSoundId);
                        this.m_model.setHeight(currentTab, height);
                    }
                    catch (OutOfBoundsError e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
