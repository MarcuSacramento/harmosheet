// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop;

import javax.swing.Spring;
import javax.swing.SpringLayout;
import java.awt.Container;
import java.awt.Component;

public class SpringUtilities
{
    public static void printSizes(final Component c) {
        System.out.println("minimumSize = " + c.getMinimumSize());
        System.out.println("preferredSize = " + c.getPreferredSize());
        System.out.println("maximumSize = " + c.getMaximumSize());
    }
    
    public static void makeGrid(final Container parent, final int rows, final int cols, final int initialX, final int initialY, final int xPad, final int yPad) {
        SpringLayout layout;
        try {
            layout = (SpringLayout)parent.getLayout();
        }
        catch (ClassCastException exc) {
            System.err.println("The first argument to makeGrid must use SpringLayout.");
            return;
        }
        final Spring xPadSpring = Spring.constant(xPad);
        final Spring yPadSpring = Spring.constant(yPad);
        final Spring initialXSpring = Spring.constant(initialX);
        final Spring initialYSpring = Spring.constant(initialY);
        final int max = rows * cols;
        Spring maxWidthSpring = layout.getConstraints(parent.getComponent(0)).getWidth();
        Spring maxHeightSpring = layout.getConstraints(parent.getComponent(0)).getWidth();
        for (int i = 1; i < max; ++i) {
            final SpringLayout.Constraints cons = layout.getConstraints(parent.getComponent(i));
            maxWidthSpring = Spring.max(maxWidthSpring, cons.getWidth());
            maxHeightSpring = Spring.max(maxHeightSpring, cons.getHeight());
        }
        for (int i = 0; i < max; ++i) {
            final SpringLayout.Constraints cons = layout.getConstraints(parent.getComponent(i));
            cons.setWidth(maxWidthSpring);
            cons.setHeight(maxHeightSpring);
        }
        SpringLayout.Constraints lastCons = null;
        SpringLayout.Constraints lastRowCons = null;
        for (int j = 0; j < max; ++j) {
            final SpringLayout.Constraints cons2 = layout.getConstraints(parent.getComponent(j));
            if (j % cols == 0) {
                lastRowCons = lastCons;
                cons2.setX(initialXSpring);
            }
            else {
                cons2.setX(Spring.sum(lastCons.getConstraint("East"), xPadSpring));
            }
            if (j / cols == 0) {
                cons2.setY(initialYSpring);
            }
            else {
                cons2.setY(Spring.sum(lastRowCons.getConstraint("South"), yPadSpring));
            }
            lastCons = cons2;
        }
        final SpringLayout.Constraints pCons = layout.getConstraints(parent);
        pCons.setConstraint("South", Spring.sum(Spring.constant(yPad), lastCons.getConstraint("South")));
        pCons.setConstraint("East", Spring.sum(Spring.constant(xPad), lastCons.getConstraint("East")));
    }
    
    private static SpringLayout.Constraints getConstraintsForCell(final int row, final int col, final Container parent, final int cols) {
        final SpringLayout layout = (SpringLayout)parent.getLayout();
        final Component c = parent.getComponent(row * cols + col);
        return layout.getConstraints(c);
    }
    
    public static void makeCompactGrid(final Container parent, final int rows, final int cols, final int initialX, final int initialY, final int xPad, final int yPad) {
        SpringLayout layout;
        try {
            layout = (SpringLayout)parent.getLayout();
        }
        catch (ClassCastException exc) {
            System.err.println("The first argument to makeCompactGrid must use SpringLayout.");
            return;
        }
        Spring x = Spring.constant(initialX);
        for (int c = 0; c < cols; ++c) {
            Spring width = Spring.constant(0);
            for (int r = 0; r < rows; ++r) {
                width = Spring.max(width, getConstraintsForCell(r, c, parent, cols).getWidth());
            }
            for (int r = 0; r < rows; ++r) {
                final SpringLayout.Constraints constraints = getConstraintsForCell(r, c, parent, cols);
                constraints.setX(x);
                constraints.setWidth(width);
            }
            x = Spring.sum(x, Spring.sum(width, Spring.constant(xPad)));
        }
        Spring y = Spring.constant(initialY);
        for (int r2 = 0; r2 < rows; ++r2) {
            Spring height = Spring.constant(0);
            for (int c2 = 0; c2 < cols; ++c2) {
                height = Spring.max(height, getConstraintsForCell(r2, c2, parent, cols).getHeight());
            }
            for (int c2 = 0; c2 < cols; ++c2) {
                final SpringLayout.Constraints constraints2 = getConstraintsForCell(r2, c2, parent, cols);
                constraints2.setY(y);
                constraints2.setHeight(height);
            }
            y = Spring.sum(y, Spring.sum(height, Spring.constant(yPad)));
        }
        final SpringLayout.Constraints pCons = layout.getConstraints(parent);
        pCons.setConstraint("South", y);
        pCons.setConstraint("East", x);
    }
}
