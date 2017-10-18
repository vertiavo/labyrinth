package text;

import com.googlecode.lanterna.gui2.Button;

public class NamedButton extends Button {

    public NamedButton(String label) {
        super(label);
    }

    public NamedButton(String label, Runnable action) {
        super(label, action);
    }

    @Override
    public String toString() {
        return super.getLabel();
    }

}
