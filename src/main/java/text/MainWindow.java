package text;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.ListSelectDialog;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;

public class MainWindow extends BasicWindow {

    private WindowBasedTextGUI guiScreen;
    private boolean startExists;
    private Button startButton;
    private Button finishButton;
    private boolean finishExists;

    public MainWindow(WindowBasedTextGUI guiScreen) {
        super("Labyrinth");
        this.guiScreen = guiScreen;
        this.startExists = false;
        this.finishExists = false;
        this.startButton = null;
        this.finishButton = null;
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
//                    TODO
//                    loadFile(loadFilename);
                } else if (selected.equals(fileButtons[2])) {
                    String saveFilename = TextInputDialog.showDialog(guiScreen, "Save", "Insert file name.", "");
//                    TODO
//                    saveFile(saveFilename);
                } else {
                    System.exit(1);
                }
            }
        });

        Button[] algorithmButtons = new NamedButton[2];
        algorithmButtons[0] = new NamedButton("Depth First Search");
        algorithmButtons[1] = new NamedButton("Breadth First Search");
        Button menuAlgorithmButton = new Button("Algorithms", () -> {
            Button selected = ListSelectDialog.showDialog(guiScreen, "Algorithms", "Choose algorithm", algorithmButtons);
            if (selected != null) {
                if (selected.equals(algorithmButtons[0])) {
//                    TODO
//                Wywolanie algorytm DFS
                } else if (selected.equals(algorithmButtons[1])) {
//                    TODO
//                Wywolanie algorytmu BFS
                }
            }
        });
        menuPanel.addComponent(menuFileButton);
        menuPanel.addComponent(menuAlgorithmButton);
        contentPanel.addComponent(menuPanel);

        // ************* GRID PANEL *****************************************
        GridLayout board = new GridLayout(10);
        gridPanel.setLayoutManager(board);

        for (int i = 0; i < 100; i++) {
            NamedButton button = new NamedButton("N");
            button.addListener(button1 -> {
                NamedButton b = (NamedButton) button1;
                switch(b.getLabel()) {
                    case "N":
                        if (checkStart()) {
                            startButton = b;
                            b.setLabel("S");
                            break;
                        }
                    case "S":
                        if (b == startButton) {
                            disableStart();
                        }
                        if (checkFinish()) {
                            finishButton = b;
                            b.setLabel("F");
                            break;
                        }
                    case "F":
                        if (b == finishButton) {
                            disableFinish();
                        }
                        b.setLabel("W");
                        break;
                    case "W":
                        b.setLabel("N");
                        break;
                }
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
        startExists = false;
        finishExists = false;
        for (Component component : gridPanel.getChildren()) {
            NamedButton button = (NamedButton) component;
            button.setLabel("N");
        }
    }

    private boolean checkStart() {
        if (startExists) {
            return false;
        } else {
            startExists = true;
            return true;
        }
    }

    private void disableStart() {
        startExists = false;
        startButton = null;
    }

    private boolean checkFinish() {
        if (finishExists) {
            return false;
        } else {
            finishExists = true;
            return true;
        }
    }

    private void disableFinish() {
        finishExists = false;
        finishButton = null;
    }

}
