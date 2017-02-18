// 
// Decompiled by Procyon v0.5.30
// 

package rvt.util.gui;

import java.io.File;

public class FileUtilities
{
    public static String getExtension(final File file) {
        if (file != null) {
            return getExtension(file.getName());
        }
        return null;
    }
    
    public static String getExtension(final String filename) {
        int extPos;
        char cur;
        for (extPos = 0, extPos = filename.length() - 1; extPos > 0; --extPos) {
            cur = filename.charAt(extPos);
            if (cur == '.') {
                return filename.substring(extPos + 1).toLowerCase();
            }
            if (cur == '/' || cur == '\\') {
                return null;
            }
        }
        return null;
    }
    
    public static String getNameWithoutExtension(final String filename) {
        final String ext = getExtension(filename);
        if (ext == null) {
            return filename;
        }
        return filename.substring(0, filename.length() - ext.length() - 1);
    }
}
