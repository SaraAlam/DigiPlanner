
public class CheckBoxCellFactory implements Callback {
    @Override
    public TableCell call(Object param) {
        CheckBoxTableCell<Boolean> checkBoxCell = new CheckBoxTableCell();
        return checkBoxCell;
    }
}