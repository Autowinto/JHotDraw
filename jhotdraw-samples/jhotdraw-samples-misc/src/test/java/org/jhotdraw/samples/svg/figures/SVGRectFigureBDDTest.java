package org.jhotdraw.samples.svg.figures;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SVGRectFigureBDDTest extends ScenarioTest<
        givenSVGRectRectangleFigure,
        whenSVGRectFigureDrawing,
        thenSVGRectFigureDrawn> {

    //BDD test for drawing a rectangle
    @Test
    public void drawingRectangleTest() {
        //Create BufferedImage instance
        BufferedImage bufferedImage = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
        //Create Graphics2D instance
        Graphics2D g = bufferedImage.createGraphics();
        //Create SVGRectFigure instance
        given().creatingRectangle();
        //Draw the rectangle
        when().drawingRectangle(g);
        //Check if the rectangle exists
        then().rectangleExists(bufferedImage);
    }

    //BDD test for moving a rectangle
    @Test
    public void movingRectangleTest() {
        //Create SVGRectFigure instance
        given().existingRectangle();
        //Move the rectangle
        when().movingRectangle();
        //Check if the rectangle exists
        then().rectangleHasMoved();
    }
}
