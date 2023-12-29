package org.jhotdraw.draw.action;

import org.jhotdraw.draw.*;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.RectangleFigure;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.*;
import static org.junit.Assert.assertSame;

public class SendToBackActionTest {

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

    @Test
    public void testSendToBackWithOneFigureAndOneFigureSelected() {
        // Add figure to drawing
        this.drawing.add(this.figureA);

        // Set selected figures
        Set<Figure> selectedFigures = new LinkedHashSet<>();
        selectedFigures.add(this.figureA);

        // Mock which selected figures is selected
        Mockito.when(this.view.getSelectedFigures()).thenReturn(selectedFigures);

        // Get figure in back
        Figure figureInBackBeforeSorting = this.drawing.getFiguresFrontToBack().get(this.drawing.getFiguresFrontToBack().size() - 1);

        // Check that figure in back is figure A
        assertSame(figureInBackBeforeSorting, this.figureA);

        // Send figure to back
        SendToBackAction.sendToBack(this.view, selectedFigures);

        // Get figure in back after sorting
        Figure figureInBackAfterSorting = this.drawing.getFiguresFrontToBack().get(this.drawing.getFiguresFrontToBack().size() - 1);

        // Check that figure in back after sorting is figure A
        assertSame(figureInBackAfterSorting, this.figureA);
    }

    @Test
    public void testSendToBackWithTwoFiguresAndOneFigureSelected() {
        // Add figures to drawing
        this.drawing.add(this.figureA);
        this.drawing.add(this.figureB);

        // Set selected figures
        Set<Figure> selectedFigures = new LinkedHashSet<>();
        selectedFigures.add(this.figureB);

        // Mock which selected figures is selected
        Mockito.when(this.view.getSelectedFigures()).thenReturn(selectedFigures);

        // Get figure in back
        Figure figureInBackBeforeSorting = this.drawing.getFiguresFrontToBack().get(this.drawing.getFiguresFrontToBack().size() - 1);

        // Check that figure in back before sorting is figure A
        assertSame(figureInBackBeforeSorting, this.figureA);

        // Send figure to back
        SendToBackAction.sendToBack(this.view, selectedFigures);

        // Get figure in back after sorting
        Figure figureInBackAfterSorting = this.drawing.getFiguresFrontToBack().get(this.drawing.getFiguresFrontToBack().size() - 1);

        // Check that figure in back after sorting is figure B
        assertSame(figureInBackAfterSorting, this.figureB);
    }

    @Test
    public void testSendToBackWithThreeFiguresAndOneFigureSelected() {
        // Add figures to drawing
        this.drawing.add(this.figureA);
        this.drawing.add(this.figureB);
        this.drawing.add(this.figureC);

        // Set selected figures
        Set<Figure> selectedFigures = new LinkedHashSet<>();
        selectedFigures.add(this.figureC);

        // Mock which selected figures is selected
        Mockito.when(this.view.getSelectedFigures()).thenReturn(selectedFigures);

        // Get figure in back
        Figure figureInBackBeforeSorting = this.drawing.getFiguresFrontToBack().get(this.drawing.getFiguresFrontToBack().size() - 1);

        // Check that figure in back before sorting is figure A
        assertSame(figureInBackBeforeSorting, this.figureA);

        // Send figure to back
        SendToBackAction.sendToBack(this.view, selectedFigures);

        // Get figure in back after sorting
        Figure figureInBackAfterSorting = this.drawing.getFiguresFrontToBack().get(this.drawing.getFiguresFrontToBack().size() - 1);

        // Check that figure in back after sorting is figure C
        assertSame(figureInBackAfterSorting, this.figureC);
    }
}
