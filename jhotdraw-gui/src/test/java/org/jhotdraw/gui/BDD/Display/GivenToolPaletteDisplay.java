package org.jhotdraw.gui.BDD.Display;

import com.tngtech.jgiven.Stage;
import org.jhotdraw.gui.plaf.palette.PaletteToolBarUI;
import javax.swing.*;

public class GivenToolPaletteDisplay extends Stage<GivenToolPaletteDisplay> {
    JToolBar toolbar;
    PaletteToolBarUI toolbarUI;

    public GivenToolPaletteDisplay a_tool_palette_is_shown() {
        toolbar = new JToolBar(); // Assuming real instance is used
        toolbarUI = new PaletteToolBarUI(); // Assuming real instance or properly initialized mock
        toolbar.setUI(toolbarUI);
        return self();
    }
}
