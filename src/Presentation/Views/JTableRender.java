package Presentation.Views;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Custom renderer for JTable cells.
 */
public class JTableRender extends DefaultTableCellRenderer {

    /**
     * Returns the custom renderer component for the specified cell.
     *
     * @param table      The JTable object.
     * @param value      The value to be rendered.
     * @param isSelected Whether or not the cell is selected.
     * @param hasFocus   Whether or not the cell has focus.
     * @param row        The row index of the cell.
     * @param column     The column index of the cell.
     * @return The custom renderer component.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setHorizontalAlignment(SwingConstants.CENTER);
        if (column == 0) {
            setForeground(Color.GREEN);
        } else if (column == 1) {
            setForeground(Color.BLUE);
        } else {
            setForeground(Color.WHITE);  // Default color for other columns
        }
        return this;
    }
}
