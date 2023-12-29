package org.jhotdraw.draw.tool;

import org.jhotdraw.draw.figure.TextHolderFigure;
import org.jhotdraw.draw.text.FloatingTextField;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.*;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoableEdit;
import java.awt.event.*;


public class TextToolUtility {

    private FloatingTextField textField;
    private TextHolderFigure typingTarget;
    private AbstractTool abstractTool;

    /*public TextToolUtility(CreationTool tool) {
        this.creationTool = tool;
    }*/
    public TextToolUtility(AbstractTool tool) {
        this.abstractTool = tool;
    }

    public UndoableEdit createUndoableEdit(TextHolderFigure editedFigure, String oldText, String newText) {
        return new AbstractUndoableEdit() {
            private static final long serialVersionUID = 1L;
            @Override
            public String getPresentationName() {
                ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
                return labels.getString("attribute.text.text");
            }
            private void setText(String text) {
                editedFigure.willChange();
                editedFigure.setText(text);
                editedFigure.changed();
            }

            @Override
            public void undo() {
                super.undo();
                setText(oldText);
            }

            @Override
            public void redo() {
                super.redo();
                setText(newText);
            }
        };
    }

    public boolean isEditing() {
        return typingTarget != null;
    }

    public TextHolderFigure getTypingTarget() {
        return typingTarget;
    }
}
