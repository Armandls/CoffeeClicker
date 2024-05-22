package Presentation.Views;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class JTableRender extends DefaultTableCellRenderer {

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
