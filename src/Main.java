import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {

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
        Menu algoFile = new Menu("Algorithm");

        MenuItem load = new MenuItem("Load from file");
        MenuItem save = new MenuItem("Save to file");
        MenuItem exit = new MenuItem("Exit");
        MenuItem clear=new MenuItem("Clear");

        MenuItem bfs = new MenuItem("BFS");
        MenuItem dfs = new MenuItem("DFS");

        menuFile.getItems().addAll(clear,load, save, exit);
        algoFile.getItems().addAll(bfs, dfs);

        //obsluga rozwiazania
        dfs.setOnAction(e -> {
            DfsSolution();
        });
        bfs.setOnAction(e -> {
            bfsSolution();
        });
        save.setOnAction(e -> {
            SaveToFile();
        });
        load.setOnAction(e -> {
            LoadFromFile();
            mainLayout.setCenter(null);
            mainLayout.setCenter(setCenterGrid());

            UpdateGUI();
        });
        exit.setOnAction(e -> System.exit(0));
        clear.setOnAction(e->{
            clearMaze();
        });
        topMenu.getMenus().addAll(menuFile);
        topMenu.getMenus().addAll(algoFile);

        return topMenu;
    }

    private void clearMaze() {
        for (int i = 0; i < labyrinthElements.length; i++) {
              Arrays.fill(labyrinthElements[i],0);
        }
        mainLayout.setCenter(null);
        mainLayout.setCenter(setCenterGrid());
    }

    private void DfsSolution() {
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
        UpdateGUI();
    }

    private void bfsSolution() {
        Algorithms a = new Algorithms(labyrinthElements);
        a.BFS(a.startY, a.startX);
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
        UpdateGUI();
    }

    private void UpdateGUI() {
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

    private void SaveToFile() {
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

    private void LoadFromFile() {

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
            AlertBox.display("Blad","Błąd wczytania pliku");
            clearMaze();
        }
    }

    private void printLabyrinth() {
        for (int[] x : labyrinthElements) {
            for (int y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }

    private GridPane setCenterGrid() {
        grid = new GridPane();
        grid.gridLinesVisibleProperty().setValue(true);
        initialize(grid);
        return grid;
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
}
