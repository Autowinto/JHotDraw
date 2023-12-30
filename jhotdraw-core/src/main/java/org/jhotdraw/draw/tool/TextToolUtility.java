package org.jhotdraw.draw.tool;

import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.TextHolderFigure;
import org.jhotdraw.draw.text.FloatingTextField;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.*;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoableEdit;
import java.awt.*;
import java.awt.event.*;


public class TextToolUtility {

    private static TextHolderFigure typingTarget;

    public static TextHolderFigure removeOverlay(TextHolderFigure typingTarget, FloatingTextField textField) {
        if (typingTarget != null) {
            typingTarget.changed();
            typingTarget = null;
            textField.endOverlay();
        }
        return typingTarget;
    }
    public static AbstractUndoableEdit createUndoableEdit(TextHolderFigure editedFigure, String oldText, String newText) {
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

    public static boolean isEditing() {
        return typingTarget != null;
    }
    public static void updateCursor(DrawingView view, Point p) {
        if (view.isEnabled()) {
            view.setCursor(Cursor.getPredefinedCursor(isEditing() ? Cursor.DEFAULT_CURSOR : Cursor.CROSSHAIR_CURSOR));
        } else {
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        }
    }

}
