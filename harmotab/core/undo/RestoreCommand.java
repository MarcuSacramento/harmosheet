// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core.undo;

public interface RestoreCommand
{
    void execute();
    
    RestoreCommand getInvertCommand();
}
