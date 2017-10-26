package gui;

import algorithms.Algorithms;
import algorithms.BfsAlgorithm;
import algorithms.Vertex;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Queue;

@SuppressWarnings("Duplicates")
public class Main extends Application {

    private static final String WELCOME_MESSAGE = "Hello!\n" +
            "This application lets you build a labyrinth and find a way from beginning to end using two algorithms.\n" +
            "To know, how to use this app, visit 'Help' tab in the upper panel.";
    private static final String HOW_TO_MESSAGE = "I CREATE\n" +
            "\tRead legend and build a new labyrinth with buttons. \n" +
            "II Load\n" +
            "\tIn the File panel choose 'Load' option and find .txt file " +
            "with coded labyrinth (20x20 dimension) as shown in legend.\n" +
            "III Running algorithm\n" +
            "\tChoose desired algorithm. After finishing, the result will " +
            "be shown on buttons in grid panel and below them, in the label.\n" +
            "IV Cleaning\n" +
            "\tChoose 'Clean' option in File panel to completely remove data from labyrinth.";
    private static final String BUTTON_LEGEND_MESSAGE = "Left Mouse Click - Place Wall\n" +
            "First Right Mouse Click - Place Start\n" +
            "Second Right Mouse Click - Place Finish";

    int[][] labyrinthElements;
    int counter = 0;
    Stage primaryStage;
    GridPane grid;
    BorderPane mainLayout;
    ScrollPane scrollPane;

    public static void main(String[] args) {
        // write your code here
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Labyrinth");
        mainLayout = new BorderPane();
        Scene scene = new Scene(mainLayout, 800, 700);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();

        labyrinthElements = new int[20][20];
        for (int[] row : labyrinthElements)
            Arrays.fill(row, 0);

        mainLayout.setCenter(setCenterGrid());
        mainLayout.setTop(setTopMenu());
        mainLayout.setBottom(setBottomLabel());

        showMessageDialog("Welcome", WELCOME_MESSAGE);
    }

    private ScrollPane setBottomLabel() {
        scrollPane = new ScrollPane();
        scrollPane.setMaxHeight(150);
        scrollPane.setFitToWidth(true);

        return scrollPane;
    }

    private MenuBar setTopMenu() {
        MenuBar topMenu = new MenuBar();

        Menu menuFile = new Menu("File");
        Menu menuHelp = new Menu("Help");

        MenuItem clear = new MenuItem("Clear");
        MenuItem load = new MenuItem("Load from file");
        MenuItem save = new MenuItem("Save to file");
        MenuItem exit = new MenuItem("Exit");

        MenuItem instructions = new MenuItem("Instruction");
        MenuItem buttonLegend = new MenuItem("Button legend");

        menuFile.getItems().addAll(clear, load, save, exit);
        menuHelp.getItems().addAll(instructions, buttonLegend);

        clear.setOnAction(e -> clearMaze());
        save.setOnAction(e -> saveToFile());
        load.setOnAction(e -> {
            loadFromFile();
            mainLayout.setCenter(null);
            mainLayout.setCenter(setCenterGrid());

            updateGUI();
        });
        exit.setOnAction(e -> System.exit(0));

        instructions.setOnAction(e ->
                showMessageDialog("Instructions", HOW_TO_MESSAGE));
        buttonLegend.setOnAction(e ->
                showMessageDialog("Button Legend", BUTTON_LEGEND_MESSAGE));

        topMenu.getMenus().addAll(menuFile, menuHelp);

        return topMenu;
    }

    private void dfsSolution() {
        Algorithms a = new Algorithms(labyrinthElements);
        a.DFS(a.startY, a.startX);
        List<Point> points = a.getPoints();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Lenght: ").append(points.size()).append("\nRoute:");

        for (Point p : points) {
            labyrinthElements[p.x][p.y] = 4;
            stringBuilder.append(p.x).append(", ").append(p.y).append(" -> ");
        }

        Text text = new Text(stringBuilder.toString());
        text.wrappingWidthProperty().bind(scrollPane.widthProperty());
        scrollPane.setContent(text);
        updateGUI();
    }

    private void bfsSolution() {
        BfsAlgorithm bfs = new BfsAlgorithm();
        Vertex start = null;
        ArrayList<Vertex> list = new ArrayList<>();
        for (int i = 0; i < labyrinthElements.length; i++) {
            for (int j = 0; j < labyrinthElements[i].length; j++) {
                if (labyrinthElements[i][j] != 1) {
                    Vertex v = new Vertex(i, j);
                    if (labyrinthElements[i][j] == 3) v.setEnd(true);
                    if (labyrinthElements[i][j] == 2) {
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
            labyrinthElements[vertex.getX()][vertex.getY()] = 4;
        }
        Collections.reverse(result);
        // algorithms.Vertex start=list.contains()
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Lenght: ").append(result.size()).append("\nRoute:");

        for (Vertex p : result) {
            labyrinthElements[p.getX()][p.getY()] = 4;
            if (result.indexOf(p) == result.size() - 1)
                stringBuilder.append(p.getX()).append(", ").append(p.getY()).append(".");
            else
                stringBuilder.append(p.getX()).append(", ").append(p.getY()).append(" -> ");
        }

        Text text = new Text(stringBuilder.toString());
        text.wrappingWidthProperty().bind(scrollPane.widthProperty());
        scrollPane.setContent(text);
        updateGUI();
    }

    private void clearMaze() {
        for (int i = 0; i < labyrinthElements.length; i++) {
            Arrays.fill(labyrinthElements[i], 0);
        }

        mainLayout.setCenter(null);
        mainLayout.setCenter(setCenterGrid());
        mainLayout.setBottom(null);
        mainLayout.setBottom(setBottomLabel());
    }

    private void updateGUI() {
        for (int i = 0; i < labyrinthElements.length; i++) {
            for (int j = 0; j < labyrinthElements[i].length; j++) {
                Pane pane = new Pane();
                if (labyrinthElements[i][j] == 0) {
                    grid.add(pane, j, i);
                }
                if (labyrinthElements[i][j] == 1) {
                    pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                    grid.add(pane, j, i);
                }
                if (labyrinthElements[i][j] == 2) {
                    pane.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
                    grid.add(pane, j, i);
                }
                if (labyrinthElements[i][j] == 3) {
                    pane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                    grid.add(pane, j, i);
                }
                if (labyrinthElements[i][j] == 4) {
                    pane.setBackground(new Background(new BackgroundFill(Color.BLANCHEDALMOND, CornerRadii.EMPTY, Insets.EMPTY)));
                    grid.add(pane, j, i);
                }

            }
        }
    }

    private void saveToFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        File file = fileChooser.showSaveDialog(primaryStage);
        printLabyrinth();
        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter output = new BufferedWriter(writer);
            for (int[] array : labyrinthElements) {
                for (int item : array) {
                    output.write(String.valueOf(item));
                    output.write(" ");
                }
                output.write("\n");
            }
            output.flush();
            output.close();

        } catch (IOException ex) {
            AlertBox.display("Error", "Bład zapisu pliku");
        }

    }

    private void loadFromFile() {

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load from file");
            File file = fileChooser.showOpenDialog(primaryStage);
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                for (int i = 0; i < 20; i++) {
                    for (int j = 0; j < 20; j++) {
                        try {//    System.out.println("number is ");
                            labyrinthElements[i][j] = input.nextInt();
                        } catch (java.util.NoSuchElementException e) {
                            // e.printStackTrace();
                        }
                    }
                }
            }
            printLabyrinth();
        } catch (Exception e) {
            //e.printStackTrace();
            AlertBox.display("Blad", "Błąd wczytania pliku");
            clearMaze();
        }
    }

    private void showMessageDialog(String title, String content) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setTitle(title);

        Button button = new Button("Ok");
        button.setOnAction(e -> {
            dialogStage.close();
        });
        VBox vbox = new VBox(new Text(content), new Text(""), button);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(15));

        dialogStage.setScene(new Scene(vbox));
        dialogStage.showAndWait();
    }

    private void printLabyrinth() {
        for (int[] x : labyrinthElements) {
            for (int y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }

    private BorderPane setCenterGrid() {
        BorderPane borderPane = new BorderPane();
        HBox buttonBox = addHBox();

        grid = new GridPane();
        grid.gridLinesVisibleProperty().setValue(true);
        initialize(grid);

        borderPane.setTop(buttonBox);
        borderPane.setCenter(grid);

        return borderPane;
    }

    private HBox addHBox() {
        HBox contentHBox = new HBox();
        contentHBox.setPadding(new Insets(15, 12, 15, 12));
        contentHBox.setSpacing(10);
        contentHBox.setAlignment(Pos.CENTER);

        Label label = new Label("Choose algorithm: ");

        ToggleGroup group = new ToggleGroup();
        RadioButton bfsButton = new RadioButton("BFS");
        bfsButton.setToggleGroup(group);
        bfsButton.setSelected(true);
        RadioButton dfsButton = new RadioButton("DFS");
        dfsButton.setToggleGroup(group);

        javafx.scene.control.Button runButton = new javafx.scene.control.Button("Run algorithm");
        runButton.setOnAction(e -> {
            if (checkConditions()) {
                if (group.getSelectedToggle().toString().contains("DFS")) {
                    dfsSolution();
                } else {
                    bfsSolution();
                }
            } else {
                showMessageDialog("Warning", "You have to select Start and Finish");
            }
        });

        contentHBox.getChildren().addAll(label, bfsButton, dfsButton, runButton);
        return contentHBox;
    }

    public void initialize(GridPane grid) {
        int numCols = 20;
        int numRows = 20;

        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            grid.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            grid.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                addPane(i, j, grid);
            }
        }
    }

    private void addPane(int colIndex, int rowIndex, GridPane grid) {
        Pane pane = new Pane();
        pane.setOnMouseClicked((MouseEvent e) -> {
            System.out.printf("Mouse clicked cell [%d, %d]%n", rowIndex, colIndex);
            if (e.getButton() == MouseButton.PRIMARY) {

                try {
                    if (pane.getBackground().getFills().get(0).getFill() == Color.BLACK) {
                        pane.setBackground(null);
                        labyrinthElements[rowIndex][colIndex] = 0;
                    } else {
                        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                        labyrinthElements[rowIndex][colIndex] = 1;
                    }
                } catch (Exception exc) {
                    pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                    labyrinthElements[rowIndex][colIndex] = 1;
                }
            }
            if (e.getButton() == MouseButton.SECONDARY) {
                try {
                    if (pane.getBackground().getFills().get(0).getFill() == Color.YELLOW || pane.getBackground().getFills().get(0).getFill() == Color.GREEN) {
                        pane.setBackground(null);
                        labyrinthElements[rowIndex][colIndex] = 0;
                        counter--;
                    } else {
                        if (counter == 0) {
                            pane.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
                            labyrinthElements[rowIndex][colIndex] = 2;
                            counter++;
                        } else if (counter == 1) {
                            pane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                            labyrinthElements[rowIndex][colIndex] = 3;
                            counter++;
                        }

                    }
                } catch (Exception exc) {
                    if (counter == 0) {
                        pane.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
                        labyrinthElements[rowIndex][colIndex] = 2;
                        counter++;
                    } else if (counter == 1) {
                        pane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                        labyrinthElements[rowIndex][colIndex] = 3;
                        counter++;
                    }

                }
            }

        });

        grid.add(pane, colIndex, rowIndex);
    }

    private boolean checkConditions() {
        boolean startExists = false, finishExists = false;

        for (int i = 0; i < labyrinthElements.length; i++) {
            for (int j = 0; j < labyrinthElements.length; j++) {
                if (labyrinthElements[i][j] == 2) {
                    startExists = true;
                } else if (labyrinthElements[i][j] == 3) {
                    finishExists = true;
                }
            }
        }

        if (startExists && finishExists) {
            return true;
        } else {
            return false;
        }
    }
}
