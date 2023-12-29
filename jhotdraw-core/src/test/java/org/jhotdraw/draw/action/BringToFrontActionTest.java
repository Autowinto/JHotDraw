package org.jhotdraw.draw.action;

import org.jhotdraw.draw.*;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.RectangleFigure;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.*;
import static org.junit.Assert.assertSame;

public class BringToFrontActionTest {

    private DrawingView view;
    private Drawing drawing;
    private Figure figureA, figureB, figureC;

    @BeforeMethod
    public void setUp() {
        // Create mock view and drawing
        this.view = Mockito.mock(DrawingView.class);

        // Create new drawing
        this.drawing = new DefaultDrawing();

        // Create three new figures
        this.figureA = new RectangleFigure(0, 0, 10, 10);
        this.figureB = new RectangleFigure(2, 2, 10, 10);
        this.figureC = new RectangleFigure(4, 4, 10, 10);

        // Mock the drawing which is returned when the view is asked for the drawing
        Mockito.when(this.view.getDrawing()).thenReturn(this.drawing);
    }

    @AfterMethod
    public void tearDown() {
    }

    @Test
    public void testBringToFrontWithOneFigureAndOneFigureSelected() {
        // Add figure to drawing
        this.drawing.add(this.figureA);

        // Set selected figures
        Set<Figure> selectedFigures = new LinkedHashSet<>();
        selectedFigures.add(this.figureA);

        // Mock which selected figures is selected
        Mockito.when(this.view.getSelectedFigures()).thenReturn(selectedFigures);

        // Get figure in front
        Figure figureInFront = this.drawing.getFiguresFrontToBack().get(0);

        // Check that figure in front is figure A
        assertSame(figureInFront, this.figureA);

        // Bring figure to front
        BringToFrontAction.bringToFront(this.view, selectedFigures);

        // Get figure in front after sorting
        Figure figureInFrontAfter = this.drawing.getFiguresFrontToBack().get(0);

        // Check that figure in front after sorting is figure A
        assertSame(figureInFrontAfter, this.figureA);
    }

    @Test
    public void testBringToFrontWithTwoFiguresAndOneFigureSelected() {
        // Add figures to drawing
        this.drawing.add(this.figureA);
        this.drawing.add(this.figureB);

        // Set selected figures
        Set<Figure> selectedFigures = new LinkedHashSet<>();
        selectedFigures.add(this.figureA);

        // Mock which selected figures is selected
        Mockito.when(this.view.getSelectedFigures()).thenReturn(selectedFigures);

        // Get figure in front
        Figure figureInFront = this.drawing.getFiguresFrontToBack().get(0);

        // Check that figure in front before sorting is figure B
        assertSame(figureInFront, this.figureB);

        // Bring figure to front
        BringToFrontAction.bringToFront(this.view, selectedFigures);

        // Get figure in front after sorting
        Figure figureInFrontAfter = this.drawing.getFiguresFrontToBack().get(0);

        // Check that figure in front after sorting is figure A
        assertSame(figureInFrontAfter, this.figureA);
    }

    @Test
    public void testBringToFrontWithThreeFiguresAndOneFigureSelected() {
        // Add figures to drawing
        this.drawing.add(this.figureA);
        this.drawing.add(this.figureB);
        this.drawing.add(this.figureC);

        // Set selected figures
        Set<Figure> selectedFigures = new LinkedHashSet<>();
        selectedFigures.add(this.figureA);

        // Mock which selected figures is selected
        Mockito.when(this.view.getSelectedFigures()).thenReturn(selectedFigures);

        // Get figure in front
        Figure figureInFront = this.drawing.getFiguresFrontToBack().get(0);

        // Check that figure in front before sorting is figure C
        assertSame(figureInFront, this.figureC);

        // Bring figure to front
        BringToFrontAction.bringToFront(this.view, selectedFigures);

        // Get figure in front after sorting
        Figure figureInFrontAfter = this.drawing.getFiguresFrontToBack().get(0);

        // Check that figure in front after sorting is figure A
        assertSame(figureInFrontAfter, this.figureA);
    }
}
