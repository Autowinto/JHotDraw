package org.jhotdraw.gui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class JDisclosureToolBarTest {

    private JDisclosureToolBar disclosureToolBar;
    private JButton disclosureButton;

    @BeforeEach
    void setUp() {
        disclosureToolBar = new JDisclosureToolBar();
        disclosureButton = mock(JButton.class);
        disclosureToolBar.disclosureButton = disclosureButton;
    }

    @Test
    void testSetDisclosureStateCount() {
        int newStateCount = 4;
        disclosureToolBar.setDisclosureStateCount(newStateCount);
        verify(disclosureButton).putClientProperty(DisclosureIcon.STATE_COUNT_PROPERTY, newStateCount);
    }

    @Test
    void testGetDisclosureState() {
        int expectedState = 2;
        when(disclosureButton.getClientProperty(DisclosureIcon.CURRENT_STATE_PROPERTY)).thenReturn(expectedState);
        assertEquals(expectedState, disclosureToolBar.getDisclosureState());
    }

    @Test
    void testGetDisclosureStateCount() {
        int expectedStateCount = 3;
        when(disclosureButton.getClientProperty(DisclosureIcon.STATE_COUNT_PROPERTY)).thenReturn(expectedStateCount);
        assertEquals(expectedStateCount, disclosureToolBar.getDisclosureStateCount());
    }
}
