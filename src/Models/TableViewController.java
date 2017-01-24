package Models;

import Utils.Log;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * @author Tyler Hostager
 * @version 1/21/17
 */
public class TableViewController implements TableModelListener {
    private KSPMMTableModel model;

    public TableViewController() {

    }

    public TableViewController(KSPMMTableModel model) {
        setModel(model);

        model.addTableModelListener(this);
    }

    public void setModel(KSPMMTableModel model) {
        if (model != null) {
            this.model = model;
        }

        this.model = new KSPMMTableModel();
    }

    public KSPMMTableModel getModel() {
        return model;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (e.getColumn() == 0) {
            Log.d("Checkbox value changed to \"" + e.getSource().toString() +  "\"");
        }
    }


}
