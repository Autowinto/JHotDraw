package org.jhotdraw.gui;

import org.jhotdraw.gui.plaf.palette.PaletteToolBarUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
class PaletteToolBarUITest {

    private PaletteToolBarUI paletteToolBarUI;
    private JToolBar toolBar;
    private PaletteToolBarUI.DragWindow dragWindow;
    private Container dockingSource;

    @BeforeEach
    void setUp() {
        toolBar = mock(JToolBar.class);
        dragWindow = mock(PaletteToolBarUI.DragWindow.class);
        dockingSource = mock(Container.class);
        paletteToolBarUI = new PaletteToolBarUI();
        paletteToolBarUI.toolBar = toolBar;
        paletteToolBarUI.dragWindow = dragWindow;
        paletteToolBarUI.dockingSource = dockingSource;

        when(toolBar.isFloatable()).thenReturn(true);
        when(dragWindow.getOffset()).thenReturn(new Point(0, 0));
        when(dockingSource.getLocationOnScreen()).thenReturn(new Point(0, 0));
    }

    @Test
    void testSetFloatingTrue() {
        Point p = new Point(100, 100);
        paletteToolBarUI.setFloating(true, p);
        assertTrue(paletteToolBarUI.isFloating());

    }

    @Test
    void testSetFloatingFalse() {
        Point p = new Point(100, 100);
        paletteToolBarUI.setFloating(false, p);
        assertFalse(paletteToolBarUI.isFloating());

    }

    @Test
    void testSetFloatingLocation() {
        int x = 200, y = 300;
        paletteToolBarUI.setFloatingLocation(x, y);
        assertEquals(x, paletteToolBarUI.floatingX);
        assertEquals(y, paletteToolBarUI.floatingY);
    }

    @Test
    void testFloatAt() {
        Point position = new Point(50, 50);
        Point origin = new Point(20, 30);

        paletteToolBarUI.floatAt(position, origin);

    }

}
