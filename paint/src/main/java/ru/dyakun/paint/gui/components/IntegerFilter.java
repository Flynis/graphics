package ru.dyakun.paint.gui.components;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class IntegerFilter extends DocumentFilter {

    @Override
    public void insertString(FilterBypass fb, int offset, String string,
                             AttributeSet attr) throws BadLocationException {
        Document doc = fb.getDocument();
        StringBuilder builder = new StringBuilder();
        builder.append(doc.getText(0, doc.getLength()));
        builder.insert(offset, string);

        if (test(builder.toString())) {
            super.insertString(fb, offset, string, attr);
        }
    }

    private boolean test(String text) {
        if(text.isEmpty()) {
            return true;
        }
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text,
                        AttributeSet attrs) throws BadLocationException {
        Document doc = fb.getDocument();
        StringBuilder builder = new StringBuilder();
        builder.append(doc.getText(0, doc.getLength()));
        builder.replace(offset, offset + length, text);

        if (test(builder.toString())) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length)
            throws BadLocationException {
        Document doc = fb.getDocument();
        StringBuilder builder = new StringBuilder();
        builder.append(doc.getText(0, doc.getLength()));
        builder.delete(offset, offset + length);

        if (test(builder.toString())) {
            super.remove(fb, offset, length);
        }
    }

}
