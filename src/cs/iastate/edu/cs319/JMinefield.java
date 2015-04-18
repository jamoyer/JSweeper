package cs.iastate.edu.cs319;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * This represents the view of the minefield that the main game is played on.
 * 
 * @author Matt Clucas, Mike Mead, and Jacob Moyer
 *
 */
@SuppressWarnings("serial")
public class JMinefield extends JTable
{
    /**
     * Constructs the view of the minefield, given a JMinefieldModel. Formats
     * the table so it can look and feel like a Minesweeper field.
     * 
     * @param model
     */
    public JMinefield(JMinefieldModel model)
    {
        super(model);
        setBorder(new MatteBorder(1, 1, 0, 0, Color.BLACK));
        setRowHeight(16);
        setFillsViewportHeight(true);
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rowSelectionAllowed = false;

        for (int x = 0; x < getColumnCount(); x++)
        {
            getColumnModel().getColumn(x).setMaxWidth(16);
            getColumnModel().getColumn(x).setCellRenderer(
                    new JMinefieldCellRenderer());
        }
    }

    // overrides the DefaultTableCellRenderer so that it displays the icons
    // within JTiles
    private class JMinefieldCellRenderer extends DefaultTableCellRenderer
    {
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int col)
        {
            JTile tile = (JTile) value;
            JLabel label = new JLabel();
            label.setIcon(tile.getIcon());
            return label;
        }
    }
}
