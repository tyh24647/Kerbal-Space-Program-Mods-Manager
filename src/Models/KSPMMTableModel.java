package Models;

import Utils.Log;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;

import static AppData.Constants.*;

/**
 * The table model object which corresponds with the data in the user's mods
 * folder, which is in the SteamApps directory
 *
 * This class allows for the modification of the mods through the UI
 *
 * @author Tyler Hostager
 * @version 1/20/17
 */
public class KSPMMTableModel extends AbstractTableModel implements TableModel {
    private JTable table;
    private TableColumn enabledColumn;
    private JScrollPane scrollPane;
    private Object[][] data = TEST_DATA;
    //private Object[][] data;

    public KSPMMTableModel() {
        initTableData();
    }

    private void initTableData() {
        final KSPMMTableModel THIS_MODEL = this;

        //setTableData();

        // create JTable object
        table = new JTable(this) {
            @Override
            public void setColumnSelectionAllowed(boolean columnSelectionAllowed) {
                super.setColumnSelectionAllowed(true);
            }

            @Override
            public void setGridColor(Color gridColor) {
                super.setGridColor(Color.LIGHT_GRAY);
            }

            @Override
            public void setSelectionBackground(Color selectionBackground) {
                super.setSelectionBackground(DEFAULT_SELECTION_COLOR);
            }

            @Override
            public void setSelectionForeground(Color selectionForeground) {
                super.setSelectionForeground(Color.BLACK);
            }

            @Override
            public void setCellSelectionEnabled(boolean cellSelectionEnabled) {
                super.setCellSelectionEnabled(true);
            }

            @Override
            public void setBorder(Border border) {
                super.setBorder(BorderFactory.createEtchedBorder());
            }

            @Override
            public void setRowHeight(int rowHeight) {
                super.setRowHeight(DEFAULT_ROW_HEIGHT);
            }
        };

        // add row sorter for each column
        RowSorter<TableModel> sorter = new TableRowSorter<>(THIS_MODEL);
        table.setRowSorter(sorter);
        resizeColumnWidth(table);
        table.getColumnModel().setColumnSelectionAllowed(true);

        // set specific column widths
        for (int i = 0; i < table.getColumnCount(); i++) {
            int width;
            switch (i) {
                case 0:
                    width = DEFAULT_ENABLED_COLUMN_WIDTH;
                    break;
                case 3:
                    width = DEFAULT_DATE_COLUMN_WIDTH;
                    break;
                default:
                    width = DEFAULT_STRING_COLUMN_WIDTH;
                    break;
            }

            table.getColumnModel().getColumn(i).setPreferredWidth(width);
        }

        String tableHeadersFontName = table.getTableHeader().getFont().deriveFont(Font.PLAIN).getName();
        table.getTableHeader().setFont(new Font(
                tableHeadersFontName,
                Font.PLAIN,
                12
        ));

        table.setAutoCreateColumnsFromModel(true);
        table.setPreferredScrollableViewportSize(new Dimension(1280, 800));
        scrollPane = new JScrollPane(table);
        enabledColumn = table.getColumnModel().getColumn(0);
        getTable();
    }

    public JTable getTable() {
        return table;
    }

    public Object[][] getData() {
        return data;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setTableData(Object[][] data) {
        if (data != null) {
            this.data = data;
            return;
        }

        Log.e("Unable to set table data -- the specified data is null");
    }

    public void addRow(String modName, String installationDir, String dateAdded) {
        DefaultTableModel model = ((DefaultTableModel) table.getModel());
        Object[] rowData = {Boolean.TRUE, modName, installationDir, dateAdded};
        model.addRow(rowData);
    }

    public KSPMMTableModel getModel() {
        return this;
    }

    //region OVERRIDE METHODS
    @Override
    public void fireTableDataChanged() {
        //super.fireTableDataChanged();
        fireTableCellUpdated(table.getSelectedRow(), table.getSelectedColumn());
        // TODO add functionality here

        table.repaint();
    }

    @Override
    public void fireTableCellUpdated(int row, int column) {
        Log.d("Updating cell at position (" + row + ", " + column + ")...");
        String typeStr;
        if (column == 0) {
            typeStr = "Boolean";
        } else {
            typeStr = "String";
        }

        Log.d("\t- Title: 'Enabled'");
        Log.d("\t- Type:  '" + typeStr + "'");
        Log.d("\t- Value: '" + getValueAt(row, column) + "'");
        Log.d("Cell updated successfully\n>");

        if (getValueAt(row, column) == Boolean.TRUE) {
            // TODO move mod file into primary location
        } else {
            // TODO move mod into disabled folder
        }

        table.repaint();
    }

    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();

        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width

            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }

            if(width > 300) {
                width = 300;
            }

            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    @Override
    public void fireTableChanged(TableModelEvent e) {
        fireTableCellUpdated(e.getFirstRow(), e.getColumn());
        super.fireTableChanged(e);
    }

    @Override
    public void fireTableRowsDeleted(int firstRow, int lastRow) {
        super.fireTableRowsDeleted(firstRow, lastRow);
    }

    @Override
    public void fireTableRowsInserted(int firstRow, int lastRow) {
        super.fireTableRowsInserted(firstRow, lastRow);
        fireTableCellUpdated(table.getSelectedRow(), table.getSelectedColumn());
    }

    @Override
    public void fireTableRowsUpdated(int firstRow, int lastRow) {
        super.fireTableRowsUpdated(firstRow, lastRow);
    }

    @Override
    public void fireTableStructureChanged() {
        super.fireTableStructureChanged();
        fireTableCellUpdated(table.getSelectedRow(), table.getSelectedColumn());
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = value;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        super.addTableModelListener(l);
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 0 ? Boolean.class : String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex < 2;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }
    //endregion
}
