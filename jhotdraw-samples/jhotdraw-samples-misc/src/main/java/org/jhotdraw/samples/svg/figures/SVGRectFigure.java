/*
 * @(#)SVGRect.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.samples.svg.figures;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

import org.jhotdraw.draw.*;
import static org.jhotdraw.draw.AttributeKeys.FILL_COLOR;
import static org.jhotdraw.draw.AttributeKeys.STROKE_CAP;
import static org.jhotdraw.draw.AttributeKeys.STROKE_JOIN;
import static org.jhotdraw.draw.AttributeKeys.STROKE_MITER_LIMIT;
import static org.jhotdraw.draw.AttributeKeys.TRANSFORM;
import org.jhotdraw.draw.handle.BoundsOutlineHandle;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.handle.ResizeHandleKit;
import org.jhotdraw.draw.handle.TransformHandleKit;
import org.jhotdraw.geom.Geom;
import org.jhotdraw.geom.GrowStroke;
import org.jhotdraw.samples.svg.Gradient;
import org.jhotdraw.samples.svg.SVGAttributeKeys;
import static org.jhotdraw.samples.svg.SVGAttributeKeys.*;

/**
 * SVGRect.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class SVGRectFigure extends SVGAttributedFigure implements SVGFigure {

    private static final long serialVersionUID = 1L;
    /**
     * Identifies the {@code arcWidth} JavaBeans property.
     */
    public static final String ARC_WIDTH_PROPERTY = "arcWidth";
    /**
     * Identifies the {@code arcHeight} JavaBeans property.
     */
    public static final String ARC_HEIGHT_PROPERTY = "arcHeight";

    /**
     * Creates a new instance of SVGRect
     * it handles the rectangle properties.
     */
    private transient SVGRectangleShape rectangleShape;

    /**
     * This is used to store the rectangle properties.
     */
    double arcWidth;
    double arcHeight;
    double x;
    double y;
    double width;
    double height;

    /**
     * This is used to perform faster drawing.
     */
    private transient Shape cachedTransformedShape;
    /**
     * This is used to perform faster hit testing.
     */
    private transient Shape cachedHitShape;

    /**
     * Creates a new instance.
     */
    public SVGRectFigure() {
        this(0, 0, 0, 0);
    }

    public SVGRectFigure(double x, double y, double width, double height) {
        this(x, y, width, height, 0, 0);
    }

    public SVGRectFigure(double x, double y, double width, double height, double rx, double ry) {
        rectangleShape  = new SVGRectangleShape(x, y, width, height, rx, ry);
        SVGAttributeKeys.setDefaults(this);
        setConnectable(false);
    }

    /**
     * Gets the Rectangle2D.Double representing the rectangular shape.
     */
    private RoundRectangle2D.Double getRoundRectangle2DDouble() {
        getRectangleShape();
        return new RoundRectangle2D.Double(
                this.x, this.y, this.width, this.height, this.arcWidth, this.arcHeight
        );
    }

    /**
     * Gets the Rectangle shape properties and stores them in private variables.
     */
    private void getRectangleShape() {
        this.arcWidth = rectangleShape.getArcWidth();
        this.arcHeight = rectangleShape.getArcHeight();
        this.x = rectangleShape.getX();
        this.y = rectangleShape.getY();
        this.width = rectangleShape.getWidth();
        this.height = rectangleShape.getHeight();
    }


    /**
     * Draws the figure.
     *
     * @param g the graphics to draw into
     */
    @Override
    protected void drawFill(Graphics2D g) {
        // Create a RoundRectangle2D for drawing
        RoundRectangle2D.Double shape = getRoundRectangle2DDouble();

        // Draw the shape
        if (arcHeight == 0d && arcWidth == 0d) {
            g.fill(shape.getBounds2D());
        } else {
            g.fill(shape);
        }
    }



    /**
     * Draws the figure's stroke based on the shape properties.
     *
     * @param g the graphics to draw into
     */
    @Override
    protected void drawStroke(Graphics2D g) {
        if (arcHeight == 0 && arcWidth == 0) {
            drawRectangle(g);
        } else {
            drawRoundedRectangle(g);
        }
    }

    private void drawRectangle(Graphics2D g) {
        g.draw(new Rectangle2D.Double(this.x, this.y, this.width, this.height));
    }

    private void drawRoundedRectangle(Graphics2D g) {
        Path2D.Double path = createRoundedRectanglePath();
        g.draw(path);
    }

    /**
     * Creates a rounded rectangle path based on the shape properties.
     *
     * @return the rounded rectangle path
     */
    private Path2D.Double createRoundedRectanglePath() {
        Path2D.Double path = new Path2D.Double();
        double arcWidthHalf = arcWidth / 2d;
        double arcHeightHalf = arcHeight / 2d;
        double controlValue = ArcControlValueCalculator.calculateArcControlValue();

        path.moveTo((x + arcWidthHalf), y);
        path.lineTo((x + width - arcWidthHalf), y);
        path.curveTo(
                (x + width - arcWidthHalf * controlValue), y,
                (x + width), (y + arcHeightHalf * controlValue),
                (x + width), (y + arcHeightHalf)
        );
        path.lineTo((x + width), (y + height - arcHeightHalf));
        path.curveTo(
                (x + width), (y + height - arcHeightHalf * controlValue),
                (x + width - arcWidthHalf * controlValue), (y + height),
                (x + width - arcWidthHalf), (y + height)
        );
        path.lineTo((x + arcWidthHalf), (y + height));
        path.curveTo((x + arcWidthHalf * controlValue), (y + height),
                x, (y + height - arcHeightHalf * controlValue),
                x, (y + height - arcHeightHalf));
        path.lineTo(x, (y + arcHeightHalf));
        path.curveTo(x, (y + arcHeightHalf * controlValue),
                (x + arcWidthHalf * controlValue), y,
                (x + arcWidthHalf), y);
        path.closePath();

        return path;
    }

    /**
     * Shape and bounds
     * Delegate methods to rectangleShape
     */
    public double getX() {
        return rectangleShape.getX();
    }

    public double getY() {
        return rectangleShape.getY();
    }

    public double getWidth() {
        return rectangleShape.getWidth();
    }

    public double getHeight() {
        return rectangleShape.getHeight();
    }

    public double getArcWidth() {
        return rectangleShape.getArcWidth();
    }

    public double getArcHeight() {
        return rectangleShape.getArcHeight();
    }

    public void setArcWidth(double newValue) {
        double oldValue = getArcWidth();
        rectangleShape.setArcWidth(newValue);
        firePropertyChange(ARC_WIDTH_PROPERTY, oldValue, newValue);
    }

    public void setArcHeight(double newValue) {
        double oldValue = getArcHeight();
        rectangleShape.setArcHeight(newValue);
        firePropertyChange(ARC_HEIGHT_PROPERTY, oldValue, newValue);
    }


    /**
     * Convenience method for setting both the arc width and the arc height.
     */
    public void setArc(double width, double height) {
        setArcWidth(width);
        setArcHeight(height);
    }

    @Override
    public Rectangle2D.Double getBounds() {
        // Use properties from the RectangleShape instance
        getRectangleShape();

        // Create and return a new Rectangle2D.Double representing the bounds
        return new Rectangle2D.Double(this.x, this.y, this.width, this.height);
    }

    @Override
    public Rectangle2D.Double getDrawingArea() {
        Rectangle2D rx = getTransformedShape().getBounds2D();
        Rectangle2D.Double r = (rx instanceof Rectangle2D.Double) ?
                (Rectangle2D.Double) rx :
                new Rectangle2D.Double(
                        rx.getX(), rx.getY(), rx.getWidth(), rx.getHeight()
                );
        if (get(TRANSFORM) == null) {
            double g = SVGAttributeKeys.getPerpendicularHitGrowth(this, 1.0) * 2d + 1d;
            Geom.grow(r, g, g);
        } else {
            double strokeTotalWidth = AttributeKeys.getStrokeTotalWidth(this, 1.0);
            double strokeWidth = strokeTotalWidth / 2d;
            if (get(STROKE_JOIN) == BasicStroke.JOIN_MITER) {
                strokeWidth *= get(STROKE_MITER_LIMIT);
            }
            if (get(STROKE_CAP) != BasicStroke.CAP_BUTT) {
                strokeWidth += strokeTotalWidth * 2;
            }
            strokeWidth++;
            Geom.grow(r, strokeWidth, strokeWidth);
        }
        return r;
    }

    /**
     * Checks if a Point2D.Double is inside the figure.
     */
    @Override
    public boolean contains(Point2D.Double p) {
        return getHitShape().contains(p);
    }

    @Override
    public void setBounds(Point2D.Double anchor, Point2D.Double lead) {
        invalidateTransformedShape();
        double setX = Math.min(anchor.x, lead.x);
        double setY = Math.min(anchor.y, lead.y);
        double setWidth = Math.max(0.1, Math.abs(lead.x - anchor.x));
        double setHeight = Math.max(0.1, Math.abs(lead.y - anchor.y));
        rectangleShape.setX(setX);
        rectangleShape.setY(setY);
        rectangleShape.setWidth(setWidth);
        rectangleShape.setHeight(setHeight);
        invalidate();
    }

    private void invalidateTransformedShape() {
        cachedTransformedShape = null;
        cachedHitShape = null;
    }

    private Shape getTransformedShape() {
        getRoundRectangle2DDouble();
        if (cachedTransformedShape == null) {
            if (getArcHeight() == 0 || getArcWidth() == 0) {
                cachedTransformedShape = getRoundRectangle2DDouble().getBounds2D();
            } else {
                cachedTransformedShape = (Shape) getRoundRectangle2DDouble().clone();
            }
            if (get(TRANSFORM) != null) {
                cachedTransformedShape = get(TRANSFORM).createTransformedShape(cachedTransformedShape);
            }
        }
        return cachedTransformedShape;
    }

    private Shape getHitShape() {
        if (cachedHitShape == null) {
            if (get(FILL_COLOR) != null || get(FILL_GRADIENT) != null) {
                cachedHitShape = new GrowStroke(
                        (float) AttributeKeys.getStrokeTotalWidth(this, 1.0) / 2f,
                        (float) AttributeKeys.getStrokeTotalMiterLimit(this, 1.0)).createStrokedShape(getTransformedShape());
            } else {
                cachedHitShape = AttributeKeys.getHitStroke(this, 1.0).createStrokedShape(getTransformedShape());
            }
        }
        return cachedHitShape;
    }

    /**
     * Transforms the figure.
     *
     * @param tx The transformation.
     */
    @Override
    public void transform(AffineTransform tx) {
        invalidateTransformedShape();
        if (get(TRANSFORM) != null
                || //              (tx.getType() & (AffineTransform.TYPE_TRANSLATION | AffineTransform.TYPE_MASK_SCALE)) != tx.getType()) {
                (tx.getType() & (AffineTransform.TYPE_TRANSLATION)) != tx.getType()) {
            if (get(TRANSFORM) == null) {
                set(TRANSFORM, (AffineTransform) tx.clone());
            } else {
                AffineTransform t = TRANSFORM.getClone(this);
                t.preConcatenate(tx);
                set(TRANSFORM, t);
            }
        } else {
            Point2D.Double anchor = getStartPoint();
            Point2D.Double lead = getEndPoint();
            setBounds(
                    (Point2D.Double) tx.transform(anchor, anchor),
                    (Point2D.Double) tx.transform(lead, lead));
            if (get(FILL_GRADIENT) != null
                    && !get(FILL_GRADIENT).isRelativeToFigureBounds()) {
                Gradient g = FILL_GRADIENT.getClone(this);
                g.transform(tx);
                set(FILL_GRADIENT, g);
            }
            if (get(STROKE_GRADIENT) != null
                    && !get(STROKE_GRADIENT).isRelativeToFigureBounds()) {
                Gradient g = STROKE_GRADIENT.getClone(this);
                g.transform(tx);
                set(STROKE_GRADIENT, g);
            }
        }
    }

    @Override
    public void restoreTransformTo(Object geometry) {
        invalidateTransformedShape();
        Object[] restoreData = (Object[]) geometry;

        TRANSFORM.setClone(this, (AffineTransform) restoreData[1]);
        FILL_GRADIENT.setClone(this, (Gradient) restoreData[2]);
        STROKE_GRADIENT.setClone(this, (Gradient) restoreData[3]);
    }

    @Override
    public Object getTransformRestoreData() {
        return new Object[]{
            getRoundRectangle2DDouble().clone(),
            TRANSFORM.getClone(this),
            FILL_GRADIENT.getClone(this),
            STROKE_GRADIENT.getClone(this)};
    }

    // EDITING
    @Override
    public Collection<Handle> createHandles(int detailLevel) {
        LinkedList<Handle> handles = new LinkedList<>();
        switch (detailLevel % 2) {
            case -1: // Mouse hover handles
                handles.add(new BoundsOutlineHandle(this, false, true));
                break;
            case 0:
                ResizeHandleKit.addResizeHandles(this, handles);
                handles.add(new SVGRectRadiusHandle(this));
                handles.add(new LinkHandle(this));
                break;
            case 1:
                TransformHandleKit.addTransformHandles(this, handles);
                break;
            default:
                break;
        }
        return handles;
    }

    @Override
    public SVGRectFigure clone() {
        SVGRectFigure that = (SVGRectFigure) super.clone();
        that.rectangleShape = new SVGRectangleShape(this.rectangleShape);
        that.cachedTransformedShape = null;
        that.cachedHitShape = null;
        return that;
    }

    @Override
    public boolean isEmpty() {
        Rectangle2D.Double b = getBounds();
        return b.width <= 0 || b.height <= 0;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        invalidateTransformedShape();
    }
}
