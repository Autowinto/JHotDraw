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
    private TextToolUtility ttu;

    /**
     * Creates a new instance.
     */
    @FeatureEntryPoint("Text tool - Edit")
    public TextEditingTool(TextHolderFigure typingTarget) {
        this.typingTarget = typingTarget;
        ttu = new TextToolUtility(this);
    }

    @FeatureEntryPoint("Text tool - Edit")
    @Override
    public void deactivate(DrawingEditor editor) {
        endOverlay();
        super.deactivate(editor);
    }

    /**
     * If the pressed figure is a TextHolderFigure it can be edited.
     */
    @FeatureEntryPoint("Text tool - Edit")
    @Override
    public void mousePressed(MouseEvent e) {
        if (typingTarget != null) {
            ttu.beginEdit(typingTarget);
            updateCursor(getView(), e.getPoint());
        }
    }

    //Override willChange
    @FeatureEntryPoint("Text tool - Edit")
    protected void endOverlay() {
        if (typingTarget != null) {
            typingTarget.willChange();

            typingTarget.changed();
            typingTarget = null;
            textField.endOverlay();
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
        ttu.endEdit();
        fireToolDone();
    }

    @Override
    public void updateCursor(DrawingView view, Point p) {
        if (view.isEnabled()) {
            view.setCursor(Cursor.getPredefinedCursor(ttu.isEditing() ? Cursor.DEFAULT_CURSOR : Cursor.CROSSHAIR_CURSOR));
        } else {
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
