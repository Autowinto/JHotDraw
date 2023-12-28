package org.jhotdraw.draw.tool;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.TextHolderFigure;
import org.jhotdraw.draw.text.FloatingTextField;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoableEdit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Map;

public abstract class TextTool extends CreationTool implements ActionListener {
    private static final long serialVersionUID = 1L;
    private FloatingTextField textField;
    private TextHolderFigure typingTarget;

    String newText;
    String oldText;

    /**
     * Creates a new instance.
     */
    public TextTool(TextHolderFigure prototype) {
        super(prototype);
    }

    /**
     * Creates a new instance.
     */
    public TextTool(TextHolderFigure prototype, Map<AttributeKey<?>, Object> attributes) {
        super(prototype, attributes);
    }

    @FeatureEntryPoint("Text tool - Create")
    @Override
    public void deactivate(DrawingEditor editor) {
        endEdit();
        super.deactivate(editor);
    }

    /**
     * Creates a new figure at the location where the mouse was pressed.
     */
    @FeatureEntryPoint("Text tool - Create")
    @Override
    public void mousePressed(MouseEvent e) {
        // Note: The search sequence used here, must be
        // consistent with the search sequence used by the
        // HandleTracker, SelectAreaTracker, DelegationSelectionTool, SelectionTool.
        if (typingTarget != null) {
            endEdit();
            if (isToolDoneAfterCreation()) {
                fireToolDone();
            }
        } else {
            super.mousePressed(e);
            // update view so the created figure is drawn before the floating text
            // figure is overlaid.
            TextHolderFigure textHolder = (TextHolderFigure) getCreatedFigure();
            getView().clearSelection();
            getView().addToSelection(textHolder);
            beginEdit(textHolder);
            updateCursor(getView(), e.getPoint());
        }
    }

    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {
    }

    @FeatureEntryPoint("Text tool - Create")
    protected void beginEdit(TextHolderFigure textHolder) {
        if (textField == null) {
            textField = new FloatingTextField();
            textField.addActionListener(this);
        }
        if (textHolder != typingTarget && typingTarget != null) {
            endEdit();
        }
        textField.createOverlay(getView(), textHolder);
        textField.requestFocus();
        typingTarget = textHolder;
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
    }
    protected void endEdit() {
        if (typingTarget != null) {
            typingTarget.willChange();
            final TextHolderFigure editedFigure = typingTarget;
            oldText = typingTarget.getText();
            newText = textField.getText();
            if (newText.length() > 0) {
                typingTarget.setText(newText);
            } else {
                if (createdFigure != null) {
                    getDrawing().remove(getAddedFigure());
                    // XXX - Fire undoable edit here!!
                } else {
                    typingTarget.setText("");
                    typingTarget.changed();
                }
            }

            getDrawing().fireUndoableEditHappened(initiateEdit(editedFigure));
            typingTarget.changed();
            typingTarget = null;
            textField.endOverlay();
        }
        //         view().checkDamage();
    }

    public UndoableEdit initiateEdit(TextHolderFigure editedFigure) {
        UndoableEdit edit = new AbstractUndoableEdit() {
            private static final long serialVersionUID = 1L;

            @Override
            public String getPresentationName() {
                ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
                return labels.getString("attribute.text.text");
            }

            @Override
            public void undo() {
                super.undo();
                editedFigure.willChange();
                editedFigure.setText(oldText);
                editedFigure.changed();
            }

            @Override
            public void redo() {
                super.redo();
                editedFigure.willChange();
                editedFigure.setText(newText);
                editedFigure.changed();
            }
        };
        return edit;
    }
    @Override
    public void keyReleased(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE || isToolDoneAfterCreation()) {
            fireToolDone();
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        endEdit();
        if (isToolDoneAfterCreation()) {
            fireToolDone();
        }
    }

    @Override
    protected void creationFinished(Figure createdFigure) {
        beginEdit((TextHolderFigure) createdFigure);
        updateCursor(getView(), new Point(0, 0));
    }

    public boolean isEditing() {
        return typingTarget != null;
    }

    @Override
    public void updateCursor(DrawingView view, Point p) {
        if (view.isEnabled()) {
            view.setCursor(Cursor.getPredefinedCursor(isEditing() ? Cursor.DEFAULT_CURSOR : Cursor.CROSSHAIR_CURSOR));
        } else {
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        }
    }
}
