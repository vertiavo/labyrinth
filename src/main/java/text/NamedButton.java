package text;

import com.googlecode.lanterna.gui2.Button;

import javax.swing.*;

public class NamedButton extends Button {

    private String text;

    public NamedButton(String text) {
        super(text);
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
