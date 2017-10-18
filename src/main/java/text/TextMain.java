package text;
/*
import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Component;
import com.googlecode.lanterna.gui.Component.Alignment;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.*;
import com.googlecode.lanterna.gui.component.Panel.Orientation;
import com.googlecode.lanterna.gui.dialog.ListSelectDialog;
import com.googlecode.lanterna.gui.dialog.TextInputDialog;
import com.googlecode.lanterna.gui.layout.LinearLayout;
import com.googlecode.lanterna.gui.layout.VerticalLayout;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenCharacterStyle;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalSize;
*/
import java.io.File;

public class TextMain {
/*
    public static void test1() {
        final GUIScreen guiScreen = TerminalFacade.createGUIScreen();
        final Window window = new Window("Labyrinth");
        window.setWindowSizeOverride(new TerminalSize(100, 50));
        window.setSoloWindow(true);

        Panel panelHolder = new Panel(Orientation.VERTICAL);

        Panel menuPane = new Panel(Orientation.HORISONTAL);

        Button[] fileButtons = new NamedButton[4];
        fileButtons[0] = new NamedButton("Create");
        fileButtons[1] = new NamedButton("Load", () -> {
            //Ta akcja się nie wykona
            String loadFilename = TextInputDialog.showTextInputBox(guiScreen, "Load", null, "What's full name of file?");
        });
        fileButtons[2] = new NamedButton("Save");
        fileButtons[3] = new NamedButton("Exit");
        Button menuFileButton = new Button("File", () -> {
            Button selected = ListSelectDialog.showDialog(guiScreen, "File", "Main options", fileButtons);
            if (selected.equals(fileButtons[1])) {
                String loadFilename = TextInputDialog.showTextInputBox(guiScreen, "Load", null, "What's full name of file?");
            }
        });
//chyba ze bedzie w tym menuPane
//        for (Button b :fileButtons) {
//            menuPane.addComponent(b);
//        }

        Button[] algorithmButtons = new NamedButton[2];
        algorithmButtons[0] = new NamedButton("Depth First Search");
        algorithmButtons[1] = new NamedButton("Breadth First Search");
        Button menuAlgorithmButton = new Button("Algorithms", () -> {
            ListSelectDialog.showDialog(guiScreen, "Algorithms", "Choose algorithm", algorithmButtons);
        });
        menuPane.addComponent(menuFileButton);
        menuPane.addComponent(menuAlgorithmButton);
        Panel boardPane = new Panel("Board", Orientation.VERTICAL);
//        Rozwiazanie I - dodatkowy panel na kolumny (poruszanie TYLKO w poziomie)
//        for (int i = 0; i < 10; i++) {
//            Panel colBoardPane = new Panel(Orientation.HORISONTAL);
//            for (int j = 0; j < 10; j++) {
//                colBoardPane.addComponent(new NamedButton("S"));
//            }
//            boardPane.addComponent(colBoardPane);
//        }

//         Rozwiazanie II - komponent tabeli (poruszanie TYLKO w pionie)
        Table table = new Table(10);
        table.setColumnPaddingSize(3);

        for (int i = 0; i < 10; i++) {
            Button[] buttonRow = new NamedButton[10];
            for (int j = 0; j < 10; j++) {
                buttonRow[j] = new NamedButton("S");
            }
            table.addRow(buttonRow);
        }

        boardPane.addComponent(table);

        Panel resultPane = new Panel("Results");
        Label result = new Label("Final results will be displayed here");
        resultPane.addComponent(result);

        panelHolder.addComponent(menuPane);
        panelHolder.addComponent(boardPane);
        panelHolder.addComponent(resultPane);

        window.addComponent(panelHolder);
        window.addComponent(new EmptySpace());

        guiScreen.getScreen().startScreen();
        guiScreen.showWindow(window);
        guiScreen.getScreen().stopScreen();

    }

    public static void test2() {
//Terminal terminal = TerminalFacade.createTerminal(Charset.forName("UTF8"));
        Screen screen = TerminalFacade.createScreen();
        screen.startScreen();
        screen.putString(4, 4, "Witam studenta ponownie!", Terminal.Color.BLUE, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
//false, false, false);
        screen.refresh();
        Thread.currentThread();
        while (screen.readInput() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        screen.stopScreen();
    }

    public static void test3() {
        final GUIScreen guiScreen = TerminalFacade.createGUIScreen();
        final Window window = new Window("Sample window");
        window.setWindowSizeOverride(new TerminalSize(100, 50));
        window.setSoloWindow(true);

        Panel panelHolder = new Panel("Holder panel", Orientation.VERTICAL);

        Panel panel = new Panel("Panel with a right-aligned button");

        panel.setLayoutManager(new VerticalLayout());
        Button button = new Button("Button");
        button.setAlignment(Component.Alignment.RIGHT_CENTER);
        panel.addComponent(button, LinearLayout.GROWS_HORIZONTALLY);

        Table table = new Table(6);
        table.setColumnPaddingSize(5);

        Component[] row1 = new Component[6];
        row1[0] = new Label("Field-1----");
        row1[1] = new Label("Field-2");
        row1[2] = new Label("Field-3");
        row1[3] = new Label("Field-4");
        row1[4] = new Label("Field-5");
        row1[5] = new Label("Field-6");

        table.addRow(row1);
        panel.addComponent(table);

        panelHolder.addComponent(panel);

        window.addComponent(panelHolder);
        window.addComponent(new EmptySpace());

        final Window newWindow = new Window("This window should be of the same size as the previous one");

        Button quitButton = new Button("Show next window", new Action() {

            public void doAction() {

                newWindow.setWindowSizeOverride(new TerminalSize(130, 50));
                newWindow.setSoloWindow(true);

                Button exitBtn = new Button("Exit", new Action() {

                    public void doAction() {
// TODO Auto-generated method stub
                        newWindow.close();
                        window.close();
                    }
                });

                exitBtn.setAlignment(Alignment.RIGHT_CENTER);

                newWindow.addComponent(exitBtn, LinearLayout.GROWS_HORIZONTALLY);

                guiScreen.showWindow(newWindow);
            }
        });
        quitButton.setAlignment(Component.Alignment.RIGHT_CENTER);
        window.addComponent(quitButton, LinearLayout.GROWS_HORIZONTALLY);

        guiScreen.getScreen().startScreen();
        guiScreen.showWindow(window);
        guiScreen.getScreen().stopScreen();
    }
*/
    /**
     * @param args
     */
    public static void main(String[] args) {
//        test1();
//        test2();
// test3();
    }

    //na to nie patrz :D
    public File load(String text) {
        return null;
    }

    private void save() {

    }
}