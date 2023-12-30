package org.jhotdraw.samples.svg.figures;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class whenSVGRectFigureDrawing extends Stage<whenSVGRectFigureDrawing> {
    @ExpectedScenarioState
    @ProvidedScenarioState
    public SVGRectFigure svgRectFigure;

    @ProvidedScenarioState
    private AffineTransform transform;

    public whenSVGRectFigureDrawing drawingRectangle(Graphics2D g) {
        this.svgRectFigure.drawStroke(g);
        return this;
    }

    public whenSVGRectFigureDrawing movingRectangle() {
        //Create a new AffineTransform
        transform = new AffineTransform();
        //Translate the rectangle
        transform.translate(5, -5);
        //transform the rectangle
        svgRectFigure.transform(transform);
        //return the rectangle
        return this;
    }
}
