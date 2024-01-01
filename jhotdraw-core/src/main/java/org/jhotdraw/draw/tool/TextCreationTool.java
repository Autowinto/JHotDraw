/*
 * @(#)TextCreationTool.java
 *
 * Copyright (c) 2009-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.tool;

import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.TextHolderFigure;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.undo.UndoableEdit;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.text.*;

/**
 * A tool to create figures which implement the {@code TextHolderFigure}
 * interface, such as {@code TextFigure}. The figure to be created is specified
 * by a prototype.
 * <p>
 * To create a figure using this tool, the user does the following mouse
 * gestures on a DrawingView:
 * <ol>
 * <li>Press the mouse button over an area on the DrawingView on which there
 * isn't a text figure present. This defines the location of the figure.</li>
 * </ol>
 * When the user has performed this mouse gesture, the TextCreationTool overlays
 * a text field over the drawing where the user can enter the text for the Figure.
 *
 * <hr>
 * <b>Design Patterns</b>
 *
 * <p>
 * <em>Framework</em><br>
 * The text creation and editing tools and the {@code TextHolderFigure}
 * interface define together the contracts of a smaller framework inside of the
 * JHotDraw framework for structured drawing editors.<br>
 * Contract: {@link TextHolderFigure}, {@link TextCreationTool},
 * {@link TextAreaCreationTool}, {@link TextEditingTool},
 * {@link TextAreaEditingTool}, {@link FloatingTextField},
 * {@link FloatingTextArea}.
 *
 * <p>
 * <em>Prototype</em><br>
 * The text creation tools create new figures by cloning a prototype
 * {@code TextHolderFigure} object.<br>
 * Prototype: {@link TextHolderFigure}; Client: {@link TextCreationTool},
 * {@link TextAreaCreationTool}.
 * <hr>
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class TextCreationTool extends CreationTool implements ActionListener {

    private static final long serialVersionUID = 1L;
    private FloatingTextField textField;
    private TextHolderFigure typingTarget;

    /**
     * Creates a new instance.
     */
    public TextCreationTool(TextHolderFigure prototype) {
        super(prototype);
    }

    /**
     * Creates a new instance.
     */
    public TextCreationTool(TextHolderFigure prototype, Map<AttributeKey<?>, Object> attributes) {
        super(prototype, attributes);
    }

    @Override
    public void deactivate(DrawingEditor editor) {
        endEdit();
        super.deactivate(editor);
    }

    /**
     * Creates a new figure at the location where the mouse was pressed.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (typingTarget != null) {
            endEdit();
            checkFireTool();
        } else {
            super.mousePressed(e);
            addToView();
            updateCursor(getView(), e.getPoint());
        }
    }
    private void checkFireTool(){
        if (isToolDoneAfterCreation()) {
            fireToolDone();
        }
    }
    private void addToView() {
        TextHolderFigure textHolder = (TextHolderFigure) getCreatedFigure();
        getView().clearSelection();
        getView().addToSelection(textHolder);
        beginEdit(textHolder);
    }

    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {
        //This override is critical for undo to work
    }

    protected void beginEdit(TextHolderFigure textHolder) {
        addTextField();

        if (textHolder != typingTarget && typingTarget != null) {
            endEdit();
        }
        overlayCreator(textHolder);
    }
    private void overlayCreator(TextHolderFigure textHolder) {
        textField.createOverlay(getView(), textHolder);
        textField.requestFocus();
        typingTarget = textHolder;
    }
    private void addTextField() {
        if (textField == null) {
            textField = new FloatingTextField();
            textField.addActionListener(this);
        }
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
        //This override is critical for undo to work
    }

    private void endEdit() {
        if (typingTarget != null) {
            typingTarget.willChange();
            final TextHolderFigure editedFigure = typingTarget;
            final String oldText = typingTarget.getText();
            final String newText = textField.getText();
            textHandler(newText);

            UndoableEdit edit = TextToolUtility.createUndoableEdit(editedFigure, oldText, newText);
            getDrawing().fireUndoableEditHappened(edit);

            typingTarget = TextToolUtility.removeOverlay(typingTarget, textField);
        }
    }
    private void textHandler(String newText) {
        if (newText.length() > 0) {
            typingTarget.setText(newText);
        } else {
            handleEmptyText();
        }
    }
    private void handleEmptyText() {
        if (createdFigure != null) {
            getDrawing().remove(getAddedFigure());
        } else {
            typingTarget.setText("");
            typingTarget.changed();
        }
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
        checkFireTool();
    }

    @Override
    protected void creationFinished(Figure createdFigure) {
        beginEdit((TextHolderFigure) createdFigure);
        updateCursor(getView(), new Point(0, 0));
    }

    @Override
    public void updateCursor(DrawingView view, Point p) {
        TextToolUtility.updateCursor(view, p);
    }
}