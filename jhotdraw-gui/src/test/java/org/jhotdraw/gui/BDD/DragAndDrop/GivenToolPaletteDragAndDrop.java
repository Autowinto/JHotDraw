package org.jhotdraw.gui.BDD.DragAndDrop;

import com.tngtech.jgiven.Stage;
import org.jhotdraw.gui.plaf.palette.PaletteToolBarUI;
import javax.swing.*;

public class GivenToolPaletteDragAndDrop extends Stage<GivenToolPaletteDragAndDrop> {
    JToolBar toolbar;
    PaletteToolBarUI toolbarUI;

    public GivenToolPaletteDragAndDrop the_tool_palette_is_visible_and_can_be_interacted_with() {
        toolbar = new JToolBar();
        toolbarUI = new PaletteToolBarUI();
        toolbar.setUI(toolbarUI);
        return self();
    }
}
