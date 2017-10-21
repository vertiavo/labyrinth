package text;

import algorithms.Algorithms;
import algorithms.BfsAlgorithm;
import algorithms.Vertex;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.dialogs.FileDialog;
import com.googlecode.lanterna.gui2.dialogs.ListSelectDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.Queue;

@SuppressWarnings("Duplicates")
public class MainWindow extends BasicWindow {

    private static final String EXAMPLE_PATH = "src/main/resources/Examples";
    private static final String WRONG_SIZE_ARRAY_EXCEPTION = "Labyrinth file should contain 10x10 dimension array.";
    private static final String HOW_TO_MESSAGE = "I CREATE\n" +
            "\tRead legend and build a new labyrinth with buttons. \n" +
            "<b>II Load</b>\n" +
            "\tIn the File panel choose 'Load' option and find .txt file\n" +
            "with coded labyrinth (10x10 dimension) as shown in legend.\n" +
            "III Running algorithm\n" +
            "\tChoose desired algorithm. After finishing, the result will \n" +
            "be shown on buttons in grid panel and below them, in the label.\n" +
            "IV Cleaning\n" +
            "\tIf you want to clean up a road built by algorithm - choose \n" +
            "'Clean route' in the Algorithms panel, otherwise if you want \n" +
            "to completely reset labyrinth - choose 'Clean' option located \n" +
            "in the File panel.";

    private WindowBasedTextGUI guiScreen;
    private Label label;
    private boolean startExists;
    private Button startButton;
    private Button finishButton;
    private boolean finishExists;
    private List<NamedButton> buttons;

    public MainWindow(WindowBasedTextGUI guiScreen) {
        super("Labyrinth");
        this.guiScreen = guiScreen;
        label = new Label("The results will be shown there.");
        startExists = false;
        finishExists = false;
        startButton = null;
        finishButton = null;
        buttons = new ArrayList<>();
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
                    loadFile();
                } else if (selected.equals(fileButtons[2])) {
                    saveFile();
                } else {
                    System.exit(1);
                }
            }
        });

        Button[] algorithmButtons = new NamedButton[3];
        algorithmButtons[0] = new NamedButton("Depth First Search");
        algorithmButtons[1] = new NamedButton("Breadth First Search");
        algorithmButtons[2] = new NamedButton("Clean route");

        Button menuAlgorithmButton = new Button("Algorithms", () -> {

            Button selected = ListSelectDialog.showDialog(guiScreen, "Algorithms", "Choose algorithm", algorithmButtons);
            if (selected != null) {
                final String errorTitle = "Labyrinth is empty";
                final String errorMessage = "Please check if your labyrinth has start and end";
                if (selected.equals(algorithmButtons[0])) {
                    try {
                        runDfs();
                    } catch (Exception e) {
                        showMessageDialog(errorTitle, errorMessage);
                    }
                } else if (selected.equals(algorithmButtons[1])) {
                    try {
                        runBfs();
                    } catch (Exception e) {
                        showMessageDialog(errorTitle, errorMessage);
                    }
                } else if (selected.equals(algorithmButtons[2])) {
                    cleanRoute();
                }
            }
        });

        Button[] helpButtons = new NamedButton[3];
        helpButtons[0] = new NamedButton("Instructions");
        helpButtons[1] = new NamedButton("Button legend");
        helpButtons[2] = new NamedButton("About");

        Button menuHelpButton = new Button("Help", () -> {

            Button selected = ListSelectDialog.showDialog(guiScreen, "Help", null, helpButtons);
            if (selected != null) {
                if (selected.equals(helpButtons[0])) {
                    showMessageDialog("How to use", HOW_TO_MESSAGE);
                } else if (selected.equals(helpButtons[1])) {
                    showMessageDialog("Button legend", "N - Nothing, \nS - Start, \nF - Finish, \nW - Wall, \nR - Road");
                } else {
                    showMessageDialog("About", "Labyrinth \u00A9 2017 \nMarek Jakubowski & Piotr Otapowicz");
                }
            }
        });

        menuPanel.addComponent(menuFileButton);
        menuPanel.addComponent(menuAlgorithmButton);
        menuPanel.addComponent(menuHelpButton);
        contentPanel.addComponent(menuPanel);

        // ************* GRID PANEL *****************************************

        GridLayout board = new GridLayout(10);
        gridPanel.setLayoutManager(board);
        board.setVerticalSpacing(2);

        for (int i = 0; i < 100; i++) {
            NamedButton button = new NamedButton("N");
            button.addListener(button1 -> {
                NamedButton b = (NamedButton) button1;
                switch (b.getLabel()) {
                    case "N":
                        if (checkStart()) {
                            startButton = b;
                            b.setLabel("S");
                            b.setTheme(new SimpleTheme(TextColor.ANSI.DEFAULT, new TextColor.RGB(0, 255, 0), SGR.BOLD));
                            break;
                        }
                    case "S":
                        if (b == startButton) {
                            disableStart();
                            b.setTheme(new Button("").getTheme());
                        }
                        if (checkFinish()) {
                            finishButton = b;
                            b.setLabel("F");
                            b.setTheme(new SimpleTheme(TextColor.ANSI.DEFAULT, new TextColor.RGB(0, 0, 255), SGR.BOLD));
                            break;
                        }
                    case "F":
                        if (b == finishButton) {
                            disableFinish();
                            b.setTheme(new Button("").getTheme());
                        }
                        b.setLabel("W");
                        b.setTheme(new SimpleTheme(TextColor.ANSI.DEFAULT, new TextColor.RGB(0, 0, 0), SGR.BOLD));
                        break;
                    case "W":
                        b.setLabel("N");
                        b.setTheme(null);
                        break;
                }
            });
            buttons.add(button);
            gridPanel.addComponent(button);
        }

        contentPanel.addComponent(gridPanel);

        // ************* RESULT PANEL *****************************************

        label.setLabelWidth(guiScreen.getScreen().getTerminalSize().getColumns());
        resultPanel.addComponent(label);
        contentPanel.addComponent(resultPanel);

        // ************* HELP MESSAGE *****************************************

        showMessageDialog("How to use", HOW_TO_MESSAGE);

        // This ultimately links in the panels as the window content
        setComponent(contentPanel);
    }

    private void showMessageDialog(String title, String message) {
        MessageDialog.showMessageDialog(guiScreen, title, message);
    }

    private void cleanBoard(Panel gridPanel) {
        startExists = false;
        finishExists = false;
        label.setText("The results will be shown there.");

        for (Component component : gridPanel.getChildren()) {
            NamedButton button = (NamedButton) component;
            button.setLabel("N");
            button.setTheme(null);
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

    private int[][] buildLabyrinthTable() {
        int[][] array = new int[10][10];

        for (int[] anArray : array) {
            Arrays.fill(anArray, 0);
        }

        if (buttons.size() < 100) {
            throw new IllegalArgumentException("Not enough buttons");
        }

        Iterator<NamedButton> iterator = buttons.iterator();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                NamedButton button = iterator.next();
                switch (button.getLabel()) {
                    case "N":
                        array[i][j] = 0;
                        break;
                    case "S":
                        array[i][j] = 2;
                        break;
                    case "F":
                        array[i][j] = 3;
                        break;
                    case "W":
                        array[i][j] = 1;
                        break;
                }
            }
        }

        return array;
    }

    private void runDfs() {
        cleanRoute();

        int[][] labyrinthTable = buildLabyrinthTable();
        Algorithms a = new Algorithms(labyrinthTable);
        a.DFS(a.startY, a.startX);
        List<Point> points = a.getPoints();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Lenght: ").append(points.size()).append("\nRoute:");

        for (Point p : points) {
            stringBuilder.append(p.x).append(", ").append(p.y).append(" -> ");
        }

        drawRoute(points);
        printResult(stringBuilder.toString());

        System.out.println(stringBuilder.toString());
    }

    private void runBfs() {
        cleanRoute();

        int[][] labyrinthTable = buildLabyrinthTable();
        BfsAlgorithm bfs = new BfsAlgorithm();
        Vertex start = null;
        ArrayList<Vertex> list = new ArrayList<>();
        for (int i = 0; i < labyrinthTable.length; i++) {
            for (int j = 0; j < labyrinthTable[i].length; j++) {
                if (labyrinthTable[i][j] != 1) {
                    Vertex v = new Vertex(i, j);
                    if (labyrinthTable[i][j] == 3) v.setEnd(true);
                    if (labyrinthTable[i][j] == 2) {
                        v.setRoot(true);
                        start = v;
                    }
                    list.add(v);
                }
            }
        }
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i != j) {
                    if (Math.abs(list.get(i).getX() - list.get(j).getX()) <= 1 && Math.abs(list.get(i).getY() - list.get(j).getY()) <= 1 &&
                            !(Math.abs(list.get(i).getX() - list.get(j).getX()) == 1 && Math.abs(list.get(i).getY() - list.get(j).getY()) == 1)) {
                        list.get(i).addNeighour(list.get(j));
                    }
                }
            }
        }
        Queue<Vertex> finalTour = bfs.traverse(start);
        List<Vertex> result = new LinkedList<>();
        while (!finalTour.isEmpty()) {
            Vertex vertex = finalTour.poll();
            result.add(vertex);
            labyrinthTable[vertex.getX()][vertex.getY()] = 4;
        }
        Collections.reverse(result);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Lenght: ").append(result.size()).append("\nRoute:");

        for (Vertex p : result) {
            if (result.indexOf(p) == result.size() - 1)
                stringBuilder.append(p.getX()).append(", ").append(p.getY()).append(".");
            else
                stringBuilder.append(p.getX()).append(", ").append(p.getY()).append(" -> ");
        }

        drawRoute(result);
        printResult(stringBuilder.toString());

        System.out.println(stringBuilder.toString());

    }

    private void drawRoute(List<?> points) {
        try {
            if (points.get(0) instanceof Point) {
                for (Point p : (List<Point>) (points)) {
                    Button currentButton = buttons.get(p.x * 10 + p.y);
                    if (currentButton.equals(startButton)) {
                        currentButton.setTheme(new SimpleTheme(TextColor.ANSI.DEFAULT, new TextColor.RGB(0, 255, 0), SGR.BOLD));
                    } else if (currentButton.equals(finishButton)) {
                        currentButton.setTheme(new SimpleTheme(TextColor.ANSI.DEFAULT, new TextColor.RGB(0, 0, 255), SGR.BOLD));
                    } else {
                        currentButton.setLabel("R");
                        currentButton.setTheme(new SimpleTheme(TextColor.ANSI.DEFAULT, new TextColor.RGB(255, 0, 0), SGR.BOLD));
                    }
                }
            } else {
                for (Vertex v : (List<Vertex>) (points)) {
                    Button currentButton = buttons.get(v.getX() * 10 + v.getY());
                    if (currentButton.equals(startButton)) {
                        currentButton.setTheme(new SimpleTheme(TextColor.ANSI.DEFAULT, new TextColor.RGB(0, 255, 0), SGR.BOLD));
                    } else if (currentButton.equals(finishButton)) {
                        currentButton.setTheme(new SimpleTheme(TextColor.ANSI.DEFAULT, new TextColor.RGB(0, 0, 255), SGR.BOLD));
                    } else {
                        currentButton.setLabel("R");
                        currentButton.setTheme(new SimpleTheme(TextColor.ANSI.DEFAULT, new TextColor.RGB(255, 0, 0), SGR.BOLD));
                    }
                }
            }
        } catch (ClassCastException e) {
            label.setText("Error occured, application needs to be restarted.");
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void cleanRoute() {
        for (Button b : buttons) {
            if (b.getLabel().equals("R")) {
                b.setLabel("N");
                b.setTheme(new Button("").getTheme());
            }
        }
    }

    private void printResult(String s) {
        label.setText(s);
    }

    private void loadFile() {
        File defaultExampleFile = new File(EXAMPLE_PATH);
        com.googlecode.lanterna.gui2.dialogs.FileDialog loadInterface = new FileDialog(
                "Load file",
                "Choose a file with labyrinth to load",
                "Open", new TerminalSize(15, 5),
                false,
                defaultExampleFile);
        File loadFile = loadInterface.showDialog(guiScreen);

        if (loadFile != null) {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(loadFile.getPath()))) {

                String line;
                Iterator<NamedButton> iterator = buttons.iterator();
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ");
                    if (parts.length > 10) {
                        throw new IllegalArgumentException(WRONG_SIZE_ARRAY_EXCEPTION);
                    }
                    for (String s : parts) {
                        NamedButton button = iterator.next();
                        switch (Integer.valueOf(s)) {
                            case 0:
                                button.setLabel("N");
                                button.setTheme(null);
                                break;
                            case 2:
                                button.setLabel("S");
                                button.setTheme(new SimpleTheme(TextColor.ANSI.DEFAULT, new TextColor.RGB(0, 255, 0), SGR.BOLD));
                                startExists = true;
                                startButton = button;
                                break;
                            case 3:
                                button.setLabel("F");
                                button.setTheme(new SimpleTheme(TextColor.ANSI.DEFAULT, new TextColor.RGB(0, 0, 255), SGR.BOLD));
                                finishExists = true;
                                finishButton = button;
                                break;
                            case 1:
                                button.setLabel("W");
                                button.setTheme(new SimpleTheme(TextColor.ANSI.DEFAULT, new TextColor.RGB(0, 0, 0), SGR.BOLD));
                                break;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
            }
        }
    }

    private void saveFile() {
        File defaultExampleFile = new File(EXAMPLE_PATH);
        com.googlecode.lanterna.gui2.dialogs.FileDialog saveInterface = new FileDialog(
                "Save file",
                "Choose a path to save current labyrinth",
                "Save", new TerminalSize(15, 5),
                false,
                defaultExampleFile);
        File saveFile = saveInterface.showDialog(guiScreen);

        if (saveFile != null) {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(saveFile.getPath()), "utf-8"))) {
                StringBuilder builder = new StringBuilder();

                Iterator<NamedButton> iterator = buttons.iterator();
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        NamedButton button = iterator.next();
                        switch (button.getLabel()) {
                            case "N":
                                builder.append(0).append(" ");
                                break;
                            case "R":
                                builder.append(0).append(" ");
                                break;
                            case "S":
                                builder.append(2).append(" ");
                                break;
                            case "F":
                                builder.append(3).append(" ");
                                break;
                            case "W":
                                builder.append(1).append(" ");
                                break;
                        }
                    }
                    builder.append("\n");
                }

                writer.write(builder.toString());
            } catch (IOException e) {
                System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
