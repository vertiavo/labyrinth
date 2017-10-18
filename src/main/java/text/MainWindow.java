package text;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.gui2.table.TableModel;

public class MainWindow extends BasicWindow {
    public MainWindow() {
        super("My Window!");
        Panel contentPanel = new Panel();
        contentPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        String[] columnLabel = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        Table table = new Table(columnLabel);
        TableModel<Button> tableModel = table.getTableModel();

        for (int i = 0; i < 10; i++) {
            Button[] buttonRow = new NamedButton[10];
            for (int j = 0; j < 10; j++) {
                buttonRow[j] = new NamedButton("S");
            }
            tableModel.addRow(buttonRow);
        }

        contentPanel.addComponent(table);

        // This ultimately links in the panels as the window content
        setComponent(contentPanel);
    }
}
