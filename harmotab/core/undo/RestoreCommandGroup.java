// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core.undo;

import java.util.Iterator;
import java.util.LinkedList;

public class RestoreCommandGroup extends LinkedList<RestoreCommand> implements RestoreCommand
{
    private static final long serialVersionUID = 1L;
    
    @Override
    public void execute() {
        for (final RestoreCommand command : this) {
            command.execute();
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        final RestoreCommandGroup commands = new RestoreCommandGroup();
        for (final RestoreCommand command : this) {
            commands.add(command.getInvertCommand());
        }
        return commands;
    }
}
