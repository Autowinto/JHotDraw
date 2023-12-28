package org.jhotdraw.samples.svg.figures;

/**
 * Represents a rectangular shape with rounded corners.
 */
public class SVGRectangleShape{
    private double x;
    private double y;
    private double width;
    private double height;
    private double arcWidth;
    private double arcHeight;

    /**
     * Constructs a SVGRectangleShape with the specified dimensions and corner radii.
     *
     * @param x the x-coordinate of the shape's top-left corner
     * @param y the y-coordinate of the shape's top-left corner
     * @param width the width of the shape
     * @param height the height of the shape
     * @param arcWidth the horizontal diameter of the corners
     * @param arcHeight the vertical diameter of the corners
     */
    public SVGRectangleShape(double x, double y, double width, double height, double arcWidth, double arcHeight) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
    }

    /**
     * Constructs a SVGRectangleShape by copying another SVGRectangleShape.
     *
     * @param other the SVGRectangleShape to copy
     */
    public SVGRectangleShape(SVGRectangleShape other) {
        this.x = other.x;
        this.y = other.y;
        this.width = other.width;
        this.height = other.height;
        this.arcWidth = other.arcWidth;
        this.arcHeight = other.arcHeight;
    }

    /**
     * Gets the x-coordinate of the shape's top-left corner.
     *
     * @return the x-coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the shape's top-left corner.
     *
     * @param x the x-coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate of the shape's top-left corner.
     *
     * @return the y-coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the shape's top-left corner.
     *
     * @param y the y-coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets the width of the shape.
     *
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the width of the shape.
     *
     * @param width the width
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Gets the height of the shape.
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height of the shape.
     *
     * @param height the height
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Gets the horizontal diameter of the corners.
     *
     * @return the arc width
     */
    public double getArcWidth() {
        return arcWidth;
    }

    /**
     * Sets the horizontal diameter of the corners.
     *
     * @param arcWidth the arc width
     */
    public void setArcWidth(double arcWidth) {
        this.arcWidth = arcWidth;
    }

    /**
     * Gets the vertical diameter of the corners.
     *
     * @return the arc height
     */
    public double getArcHeight() {
        return arcHeight;
    }

    /**
     * Sets the vertical diameter of the corners.
     *
     * @param arcHeight the arc height
     */
    public void setArcHeight(double arcHeight) {
        this.arcHeight = arcHeight;
    }

}