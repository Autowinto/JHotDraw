package org.jhotdraw.draw.figure;

import org.jhotdraw.draw.*;
import org.jhotdraw.draw.tool.TextEditingTool;
import org.jhotdraw.draw.tool.TextToolUtility;
import org.jhotdraw.undo.UndoRedoManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.w3c.dom.Text;

import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import java.awt.event.MouseEvent;


public class TextEditingToolTest {

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
}
