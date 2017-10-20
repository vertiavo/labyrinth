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
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.Queue;

@SuppressWarnings("Duplicates")
public class MainWindow extends BasicWindow {

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
//                    String loadFilename = TextInputDialog.showDialog(guiScreen, "Load", "What's full name of file?", "");
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
                    runDfs();
                } else if (selected.equals(algorithmButtons[1])) {
                    runBfs();
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
                        break;
                    case "W":
                        b.setLabel("N");
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

        // This ultimately links in the panels as the window content
        setComponent(contentPanel);
    }

    private void cleanBoard(Panel gridPanel) {
        startExists = false;
        finishExists = false;
        label.setText("The results will be shown there.");
        for (Component component : gridPanel.getChildren()) {
            NamedButton button = (NamedButton) component;
            button.setLabel("N");
            button.setTheme(new Button("").getTheme());
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
                    case "N": array[i][j] = 0; break;
                    case "S": array[i][j] = 2; break;
                    case "F": array[i][j] = 3; break;
                    case "W": array[i][j] = 1; break;
                }
            }
        }

        return array;
    }

    private void runDfs() {
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

    private void printResult(String s) {
        label.setText(s);
    }

    private void loadFile() {
        String pathToExampleFolder = "/run/media/vertiavo/Marek/IdeaProjects/labyrinth-kck/labyrinth/src/main/resources/Examples";
        File defaultExampleFile = new File(pathToExampleFolder);
        com.googlecode.lanterna.gui2.dialogs.FileDialog loadInterface = new FileDialog(
                "Load file",
                "Choose a file with labyrinth to load",
                "Open", new TerminalSize(15, 5),
                false,
                defaultExampleFile);
        File loadFile = loadInterface.showDialog(guiScreen);

        try(BufferedReader reader = Files.newBufferedReader(Paths.get(loadFile.getPath()))) {
            String line;
            Iterator<NamedButton> iterator = buttons.iterator();
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length > 10) {
                    throw new IllegalArgumentException("Labyrinth file should contain 10x10 dimension array.");
                }
                for (String s : parts) {
                    NamedButton button = iterator.next();
                    switch (Integer.valueOf(s)) {
                        case 0:
                            button.setLabel("N");
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
                            break;
                    }
                }
            }
        } catch(Exception e) {
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }

}
