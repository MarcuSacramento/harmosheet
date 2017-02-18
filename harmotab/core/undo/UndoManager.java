// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core.undo;

import java.util.Iterator;
import java.util.LinkedList;

public class UndoManager
{
    public static final int UNDO_MAX_LEVEL = 20;
    private static UndoManager m_instance;
    private LinkedList<RestoreCommand> m_undoCommands;
    private LinkedList<String> m_undoLabels;
    private LinkedList<RestoreCommand> m_redoCommands;
    private LinkedList<String> m_redoLabels;
    
    static {
        UndoManager.m_instance = null;
    }
    
    private UndoManager() {
        this.m_undoCommands = null;
        this.m_undoLabels = null;
        this.m_redoCommands = null;
        this.m_redoLabels = null;
        this.m_undoCommands = new LinkedList<RestoreCommand>();
        this.m_undoLabels = new LinkedList<String>();
        this.m_redoCommands = new LinkedList<RestoreCommand>();
        this.m_redoLabels = new LinkedList<String>();
    }
    
    public static synchronized UndoManager getInstance() {
        if (UndoManager.m_instance == null) {
            UndoManager.m_instance = new UndoManager();
        }
        return UndoManager.m_instance;
    }
    
    public boolean hasUndoCommands() {
        return !this.m_undoCommands.isEmpty();
    }
    
    public String getTopUndoLabel() {
        return this.m_undoLabels.peek();
    }
    
    public boolean hasRedoCommands() {
        return !this.m_redoCommands.isEmpty();
    }
    
    public String getTopRedoLabel() {
        return this.m_undoLabels.peek();
    }
    
    public void reset() {
        this.m_undoCommands.clear();
        this.m_undoCommands.clear();
        this.m_redoCommands.clear();
        this.m_redoLabels.clear();
    }
    
    public void undo() {
        if (this.m_undoCommands.size() > 0) {
            final RestoreCommand command = this.m_undoCommands.pop();
            final String label = this.m_undoLabels.pop();
            this.addRedoCommand(command.getInvertCommand(), label);
            command.execute();
        }
    }
    
    public void redo() {
        if (this.m_redoCommands.size() > 0) {
            final RestoreCommand command = this.m_redoCommands.pop();
            final String label = this.m_redoLabels.pop();
            this.addUndoCommandKeepingRedoStack(command.getInvertCommand(), label);
            command.execute();
        }
    }
    
    public void addUndoCommand(final RestoreCommand command, final String label) {
        this.m_redoCommands.clear();
        this.m_redoLabels.clear();
        this.addUndoCommandKeepingRedoStack(command, label);
    }
    
    public void appendToLastUndoCommand(final RestoreCommand command) {
        RestoreCommand lastCommand = this.m_undoCommands.pop();
        if (!(lastCommand instanceof RestoreCommandGroup)) {
            final RestoreCommandGroup commandGroup = new RestoreCommandGroup();
            commandGroup.add(lastCommand);
            lastCommand = commandGroup;
        }
        final RestoreCommandGroup commandGroup = (RestoreCommandGroup)lastCommand;
        commandGroup.add(command);
        this.m_undoCommands.push(commandGroup);
    }
    
    private void addUndoCommandKeepingRedoStack(final RestoreCommand command, final String label) {
        this.m_undoCommands.push(command);
        this.m_undoLabels.push(label);
        if (this.m_undoCommands.size() > 20) {
            this.m_undoCommands.removeLast();
            this.m_undoLabels.removeLast();
        }
    }
    
    private void addRedoCommand(final RestoreCommand command, final String label) {
        this.m_redoCommands.push(command);
        this.m_redoLabels.push(label);
        if (this.m_redoCommands.size() > 20) {
            this.m_redoCommands.removeLast();
            this.m_redoLabels.removeLast();
        }
    }
    
    public void printStackTrace() {
        System.out.println("-----------");
        System.out.println("Undo stack:");
        int index = 0;
        for (final RestoreCommand command : this.m_undoCommands) {
            System.out.println(String.valueOf(index) + ". " + this.m_undoLabels.get(index) + " \t" + command);
            ++index;
        }
        System.out.println("Redo stack:");
        index = 0;
        for (final RestoreCommand command : this.m_redoCommands) {
            System.out.println(String.valueOf(index) + ". " + this.m_redoLabels.get(index) + " \t" + command);
            ++index;
        }
    }
}
