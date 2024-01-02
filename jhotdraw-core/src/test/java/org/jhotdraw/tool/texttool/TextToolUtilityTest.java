package org.jhotdraw.tool.texttool;

import org.jhotdraw.draw.figure.TextFigure;
import org.jhotdraw.draw.figure.TextHolderFigure;
import org.jhotdraw.draw.text.FloatingTextField;
import org.jhotdraw.draw.tool.TextToolUtility;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.swing.undo.UndoableEdit;


public class TextToolUtilityTest {

    TextHolderFigure txt;
    UndoableEdit edit;
    String initial = "Initial Text";
    String newText = "Redone Text";

    @Before
    public void setUp() {
        txt = new TextFigure();
        txt.setText(initial);
        edit = TextToolUtility.createUndoableEdit(txt, txt.getText(), newText);
    }
    @Test
    public void testUndo() {
        String moreText = "New Text";
        txt.setText(moreText);
        Assert.assertSame(moreText, txt.getText());
        edit.undo();
        Assert.assertSame(initial, txt.getText());
    }
    @Test
    public void testRedo() {
        edit.undo();
        Assert.assertSame(initial, txt.getText());
        edit.redo();
        Assert.assertSame(newText, txt.getText());
    }
    @Test
    public void testRemoveOverlay() {
        FloatingTextField field = Mockito.mock(FloatingTextField.class);
        txt.willChange();
        txt = TextToolUtility.removeOverlay(txt,field);
        Assert.assertNull(txt);
    }
}
