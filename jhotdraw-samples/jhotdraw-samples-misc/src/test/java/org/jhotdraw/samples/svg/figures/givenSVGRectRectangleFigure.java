package org.jhotdraw.samples.svg.figures;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

public class givenSVGRectRectangleFigure extends Stage<givenSVGRectRectangleFigure> {

    @ProvidedScenarioState
    public SVGRectFigure svgRectFigure;

    public givenSVGRectRectangleFigure creatingRectangle() {
        svgRectFigure = new SVGRectFigure(1, 4, 6, 6, 1d, 1d);
        return this;
    }

    public givenSVGRectRectangleFigure existingRectangle() {
        svgRectFigure = new SVGRectFigure(3, 7, 2, 4, 1d, 1d);
        return this;
    }
}
