package text;

import com.googlecode.lanterna.gui2.*;

public class MainWindow extends BasicWindow {
    public MainWindow() {
        super("My Window!");
        Panel contentPanel = new Panel();
        contentPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Panel gridPanel = new Panel();
        GridLayout board = new GridLayout(10);
        gridPanel.setLayoutManager(board);

        for (int i = 0; i < 100; i++) {
            gridPanel.addComponent(new NamedButton("S"));
        }

        contentPanel.addComponent(gridPanel);
        // This ultimately links in the panels as the window content
        setComponent(contentPanel);
    }
}
