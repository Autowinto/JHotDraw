/*
 * @(#)TextEditingTool.java
 *
 * Copyright (c) 2009-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.tool;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import org.jhotdraw.draw.figure.TextHolderFigure;
import java.awt.*;
import java.awt.event.*;
import javax.swing.undo.UndoableEdit;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.text.*;


/**
 * A tool to edit figures which implement the {@code TextHolderFigure} interface,
 * such as {@code TextFigure}.
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
public class TextEditingTool extends AbstractTool implements ActionListener {

    private static final long serialVersionUID = 1L;
    private FloatingTextField textField;
    private TextHolderFigure typingTarget;

    /**
     * Creates a new instance.
     */
    @FeatureEntryPoint("Text tool - Edit")
    public TextEditingTool(TextHolderFigure typingTarget) {
        this.typingTarget = typingTarget;
    }

    @FeatureEntryPoint("Text tool - Edit")
    @Override
    public void deactivate(DrawingEditor editor) {
        endEdit();
        super.deactivate(editor);
    }

    /**
     * If the pressed figure is a TextHolderFigure it can be edited.
     */
    @FeatureEntryPoint("Text tool - Edit")
    @Override
    public void mousePressed(MouseEvent e) {
        if (typingTarget != null) {
            beginEdit(typingTarget);
            updateCursor(getView(), e.getPoint());
        }
    }

    @FeatureEntryPoint("Text tool - Edit")
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

    //Override willChange
    @FeatureEntryPoint("Text tool - Edit")
    protected void endEdit() {
        if (typingTarget != null) {
            typingTarget.willChange();
            final TextHolderFigure editedFigure = typingTarget;
            final String oldText = typingTarget.getText();
            final String newText = textField.getText();

            handleText(newText);

            UndoableEdit edit = TextToolUtility.createUndoableEdit(editedFigure, oldText, newText);
            getDrawing().fireUndoableEditHappened(edit);

            typingTarget = TextToolUtility.removeOverlay(typingTarget, textField);
        }
    }

    private void handleText(String newText) {
        if (newText.length() > 0) {
            typingTarget.willChange();
            typingTarget.setText(newText);
            typingTarget.changed();
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            fireToolDone();
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        endEdit();
        fireToolDone();
    }

    @Override
    public void updateCursor(DrawingView view, Point p) {
        TextToolUtility.updateCursor(view, p);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
