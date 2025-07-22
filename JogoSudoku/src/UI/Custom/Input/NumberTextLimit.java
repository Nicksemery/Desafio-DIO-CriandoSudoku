package UI.Custom.Input;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.util.List;

import static java.util.Objects.isNull;

public class NumberTextLimit extends PlainDocument {

    private final List<String>Number = List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (isNull(str) || (!Number.contains(str)))return;

        if(getLength() + str.length() <=1){
            super.insertString(offset, str, attr);
        }
    }
}
