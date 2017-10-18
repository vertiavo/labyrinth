package text;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.ListSelectDialog;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;

public class MainWindow extends BasicWindow {

    private WindowBasedTextGUI guiScreen;

    public MainWindow(WindowBasedTextGUI guiScreen) {
        super("Labyrinth");
        this.guiScreen = guiScreen;
    }

    public void makeGUI() {
        Panel contentPanel = new Panel();
        contentPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Panel menuPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        final Panel gridPanel = new Panel();
        Panel resultPanel = new Panel(new LinearLayout(Direction.VERTICAL));

        // ************* MENU PANEL *****************************************
        Button[] fileButtons = new NamedButton[4];
        fileButtons[0] = new NamedButton("Clean");
        fileButtons[1] = new NamedButton("Load");
        fileButtons[2] = new NamedButton("Save");
        fileButtons[3] = new NamedButton("Exit");

        Button menuFileButton = new Button("File", () -> {
            Button selected = ListSelectDialog.showDialog(guiScreen, "File", "Main options", fileButtons);
            if (selected != null) {
                if (selected.equals(fileButtons[0])) {
                    cleanBoard(gridPanel);
                } else if (selected.equals(fileButtons[1])) {
                    String loadFilename = TextInputDialog.showDialog(guiScreen, "Load", "What's full name of file?", "");
                }
            }
        });

        Button[] algorithmButtons = new NamedButton[2];
        algorithmButtons[0] = new NamedButton("Depth First Search");
        algorithmButtons[1] = new NamedButton("Breadth First Search");
        Button menuAlgorithmButton = new Button("Algorithms", () -> {
            ListSelectDialog.showDialog(guiScreen, "Algorithms", "Choose algorithm", algorithmButtons);
        });
        menuPanel.addComponent(menuFileButton);
        menuPanel.addComponent(menuAlgorithmButton);
        contentPanel.addComponent(menuPanel);

        // ************* GRID PANEL *****************************************
        GridLayout board = new GridLayout(10);
        gridPanel.setLayoutManager(board);

        for (int i = 0; i < 100; i++) {
            NamedButton button = new NamedButton("S");
            button.addListener(button1 -> {
                NamedButton b = (NamedButton) button1;
                b.setLabel("K");
            });
            gridPanel.addComponent(button);
        }

        contentPanel.addComponent(gridPanel);

        // ************* GRID PANEL *****************************************
        contentPanel.addComponent(resultPanel);

        // This ultimately links in the panels as the window content
        setComponent(contentPanel);
    }

    private void cleanBoard(Panel gridPanel) {
        for (Component component : gridPanel.getChildren()) {
            NamedButton button = (NamedButton) component;
            button.setLabel("S");
        }
    }
}
