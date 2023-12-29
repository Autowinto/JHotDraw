package org.jhotdraw.draw.tool;

import org.jhotdraw.draw.figure.TextHolderFigure;
import org.jhotdraw.draw.text.FloatingTextField;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoableEdit;
import java.awt.event.*;


public class TextToolUtility {

    private FloatingTextField textField;
    private TextHolderFigure typingTarget;
    private CreationTool creationTool;
    private final AbstractTool abstractTool;

    public TextToolUtility(CreationTool tool) {
        this.creationTool = tool;
        this.abstractTool = tool;
    }
    public TextToolUtility(AbstractTool tool) {
        this.abstractTool = tool;
    }
    private ActionListener actionListener() {

        return evt -> {
            endEdit();
            abstractTool.fireToolDone();
        };
    }
    protected void endEdit() {
        if (typingTarget != null) {
            typingTarget.willChange();
            final TextHolderFigure editedFigure = typingTarget;
            final String oldText = typingTarget.getText();
            final String newText = textField.getText();

            if (newText.length() > 0) {
                typingTarget.setText(newText);
                System.out.println(newText);
            } else {
                handleEmptyText();
            }

            UndoableEdit edit = createUndoableEdit(editedFigure, oldText, newText);
            abstractTool.getDrawing().fireUndoableEditHappened(edit);

            typingTarget.changed();
            typingTarget = null;
            textField.endOverlay();
        }
    }

    private void handleEmptyText() {
        if (creationTool.createdFigure != null) {
            abstractTool.getDrawing().remove(creationTool.getAddedFigure());
            // XXX - Fire undoable edit here!!
        } else {
            typingTarget.setText("");
            typingTarget.changed();
        }
    }
    public void beginEdit(TextHolderFigure textHolder) {
        if (textField == null) {
            textField = new FloatingTextField();
            textField.addActionListener(actionListener());
        }
        if (textHolder != typingTarget && typingTarget != null) {
            endEdit();
        }
        textField.createOverlay(abstractTool.getView(), textHolder);
        textField.requestFocus();
        typingTarget = textHolder;
    }
    public static UndoableEdit createUndoableEdit(TextHolderFigure editedFigure, String oldText, String newText) {
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
}
