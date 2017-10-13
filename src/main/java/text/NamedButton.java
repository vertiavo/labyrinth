package text;

import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.component.Button;

public class NamedButton extends Button {

    private String text;

    public NamedButton(String text) {
        super(text);
        this.text = text;
    }

    public NamedButton(String text, Action onPressEvent) {
        super(text, onPressEvent);
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
