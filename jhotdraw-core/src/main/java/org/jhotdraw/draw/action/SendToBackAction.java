/*
 * @(#)SendToBackAction.java
 *
 * Copyright (c) 2003-2008 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.action;

import org.jhotdraw.draw.figure.Figure;
import java.util.*;
import org.jhotdraw.draw.*;

/**
 * SendToBackAction.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class SendToBackAction extends AbstractArrangeAction {

    public static final String ID = "edit.sendToBack";

    /**
     * Creates a new instance.
     */
    public SendToBackAction(DrawingEditor editor) {
        super(editor);
    }

    public static void sendToBack(DrawingView view, Collection<Figure> figures) {
        Drawing drawing = view.getDrawing();
        for (Figure figure : figures) {
            drawing.sendToBack(figure);
        }
    }

    public void order(DrawingView view, Collection<Figure> figures) {
        sendToBack(view, figures);
    }

    public void reverseOrder(DrawingView view, Collection<Figure> figures) {
        BringToFrontAction.bringToFront(view, figures);
    }

    protected String getID() {
        return ID;
    }
}
