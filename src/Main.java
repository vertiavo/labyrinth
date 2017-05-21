import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.awt.*;
import java.util.Arrays;

public class Main extends Application{
    int [][]labyrinthElements;
    int counter=0;
    public static void main(String[] args) {
        // write your code here
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Labyrinth");
        BorderPane mainLayout=new BorderPane();
        Scene scene=new Scene(mainLayout,800,700);
        primaryStage.setScene(scene);
        primaryStage.show();

        mainLayout.setCenter(setCenterGrid());
        mainLayout.setTop(setTopMenu());
    }

    private MenuBar setTopMenu() {
        MenuBar topMenu=new MenuBar();

        Menu menuFile = new Menu("File");
        MenuItem add = new MenuItem("Create");
        menuFile.getItems().addAll(add);

       //obsluga rozwiazania
        

       topMenu.getMenus().addAll(menuFile);

        return topMenu;
    }

    private void printLabyrinth() {
        System.out.println(Arrays.deepToString(labyrinthElements));
    }

    private GridPane setCenterGrid() {
         GridPane grid =new GridPane();
         grid.gridLinesVisibleProperty().setValue(true);
         initialize(grid);
        return grid;
    }
    public void initialize(GridPane grid) {
        int numCols = 20 ;
        int numRows = 20 ;
        labyrinthElements=new int[20][20];
        for (int[] row: labyrinthElements)
            Arrays.fill(row, 0);

        for (int i = 0 ; i < numCols ; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            grid.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0 ; i < numRows ; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            grid.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0 ; i < numCols ; i++) {
            for (int j = 0; j < numRows; j++) {
                addPane(i, j,grid);
            }
        }
    }
    private void addPane(int colIndex, int rowIndex,GridPane grid) {
        Pane pane = new Pane();
        pane.setOnMouseClicked((MouseEvent e) -> {
            System.out.printf("Mouse clicked cell [%d, %d]%n",  rowIndex,colIndex);
            if(e.getButton()==MouseButton.PRIMARY){

                try {
                    if(pane.getBackground().getFills().get(0).getFill()==Color.BLACK){
                        pane.setBackground(null);
                        labyrinthElements[rowIndex][colIndex]=0;
                    }
                    else{
                        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                        labyrinthElements[rowIndex][colIndex]=1;
                    }
                } catch (Exception exc) {
                    pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                    labyrinthElements[rowIndex][colIndex]=1;
                }
            }
            if(e.getButton()== MouseButton.SECONDARY ){
                try {
                    if(pane.getBackground().getFills().get(0).getFill()==Color.YELLOW ||pane.getBackground().getFills().get(0).getFill()==Color.GREEN ){
                        pane.setBackground(null);
                        labyrinthElements[rowIndex][colIndex]=0;
                        counter--;
                    }
                    else{
                        if(counter==0){
                            pane.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
                            labyrinthElements[rowIndex][colIndex]=2;
                            counter++;
                        }
                        else if(counter==1){
                            pane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                            labyrinthElements[rowIndex][colIndex]=3;
                            counter++;
                        }

                    }
                } catch (Exception exc) {
                    if(counter==0){
                        pane.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
                        labyrinthElements[rowIndex][colIndex]=2;
                        counter++;
                    }
                    else if (counter==1){
                        pane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                        labyrinthElements[rowIndex][colIndex]=3;
                        counter++;
                    }

                }
            }

        });

        grid.add(pane, colIndex, rowIndex);
    }
}
