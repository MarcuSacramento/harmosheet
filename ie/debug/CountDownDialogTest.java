// 
// Decompiled by Procyon v0.5.30
// 

package ie.debug;

import harmotab.desktop.components.CountDownDialog;

public class CountDownDialogTest
{
    public static void main(final String[] args) {
        final CountDownDialog dlg = new CountDownDialog("Test", "Test %COUNTDOWN%");
        dlg.countDown(3.5f, 10.0f);
        System.exit(0);
    }
}
