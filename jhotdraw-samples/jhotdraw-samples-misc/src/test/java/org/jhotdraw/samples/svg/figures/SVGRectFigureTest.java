package org.jhotdraw.samples.svg.figures;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertNotEquals;

public class SVGRectFigureTest {

    private SVGRectFigure rectFigure;
    @Before
    public void setUp(){
        double x = 15.3;
        double y = 16.3;
        double width = 8.7;
        double height = 9.1;
        double rx = 1d;
        double ry = 1d;
        rectFigure = new SVGRectFigure(x, y,width, height,rx, ry);

    }

    // Getting the x coordinate of the rectangle
    @Test
    public void testGetX() {
        double x = 15.3;
        Assert.assertEquals(x, rectFigure.getX(), 0.0001);
    }

    //Getting the y coordinate of the rectangle
    @Test
    public void testGetY() {
        double y = 16.3;
        Assert.assertEquals(y, rectFigure.getY(), 0.0001);
    }
    // Getting the width of the rectangle
    @Test
    public void testGetWidth() {
        double width = 8.7;
        Assert.assertEquals(width, rectFigure.getWidth(), 0.0001);
    }

    // Getting the height of the rectangle
    @Test
    public void testGetHeight() {
        double height = 9.1;
        Assert.assertEquals(height, rectFigure.getHeight(), 0.0001);
    }

    // Testing drawstroke method
    @Test
    public void testDrawStroke() {
        // instance of variables for BufferedImage
        double x = 3;
        double height = 8;

        //Create a BufferedImage
        BufferedImage buf = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);

        //Create a Graphics2D object
        Graphics2D g = buf.createGraphics();

        //Check if the pixel color is white
        Assert.assertEquals(0, buf.getRGB((int)x, (int)(height / 2)));

        //Set the pixel color to black
        buf.setRGB((int)x, (int)(height / 2), Color.BLACK.getRGB());

        //Check if the pixel color is black
        Assert.assertEquals(-16777216, buf.getRGB((int)x, (int)(height / 2)));

        //Draw the stroke
        rectFigure.drawStroke(g);

        //Check if something was drawn and the pixel color has changed
        assertNotEquals(0, buf.getRGB((int)x, (int)(height / 2)));
    }

}