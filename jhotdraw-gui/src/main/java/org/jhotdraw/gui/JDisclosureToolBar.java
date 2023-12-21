/**
 * @(#)JDisclosureToolBar.java
 *
 * Copyright (c) 2008 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import org.jhotdraw.gui.plaf.palette.PaletteToolBarUI;

/**
 * A ToolBar with disclosure functionality.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class JDisclosureToolBar extends JToolBar {

    private static final long serialVersionUID = 1L;
    JButton disclosureButton;
    public static final String DISCLOSURE_STATE_PROPERTY = "disclosureState";
    public static final String DISCLOSURE_STATE_COUNT_PROPERTY = "disclosureStateCount";

    /**
     * Creates new form.
     */
    @FeatureEntryPoint("tool palette - tools display")
    public JDisclosureToolBar() {
        setUI(PaletteToolBarUI.createUI(this));
        initComponents();
    }


    /**
     * Refactored code
     *
     * Initializes the components of the toolbar. This method is responsible for
     * setting up the layout of the toolbar, initializing the disclosure button,
     * adding it to the toolbar, and configuring the toolbar properties.
     */

    private void initComponents() {
        setLayout(new GridBagLayout());
        if (disclosureButton == null) {
            initializeDisclosureButton();
        }
        addDisclosureButtonToToolBar();
        configureToolBarProperties();
    }

    /**
     * Refactored code
     *
     * Initializes the disclosure button used in the toolbar. It creates the button,
     * sets its UI, and attaches an action listener to handle state changes.
     */

    private void initializeDisclosureButton() {
        JButton btn = createDisclosureButton();
        btn.addActionListener(createDisclosureButtonActionListener());
        disclosureButton = btn;
    }


    /**
     * Refactored code
     *
     * Creates and returns a new JButton configured as a disclosure button.
     * This button is used to control the disclosure state of the toolbar.
     *
     */
    private JButton createDisclosureButton() {
        JButton btn = new JButton();
        btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
        btn.setBorderPainted(false);
        btn.setIcon(new DisclosureIcon());
        btn.setOpaque(false);
        btn.putClientProperty(DisclosureIcon.CURRENT_STATE_PROPERTY, 1);
        btn.putClientProperty(DisclosureIcon.STATE_COUNT_PROPERTY, 2);
        return btn;
    }

    /**
     * Refactored code
     *
     * Creates and returns an ActionListener for the disclosure button. This listener
     * handles the action of changing the disclosure state of the toolbar.
     *
     */
    private ActionListener createDisclosureButtonActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentState = getDisclosureState();
                int stateCount = getDisclosureStateCount();
                int newState = (currentState + 1) % stateCount;
                setDisclosureState(newState);
            }
        };
    }

    /**
     * Refactored code
     *
     * Adds the disclosure button to the toolbar with appropriate GridBagConstraints.
     * This method is responsible for positioning and configuring the layout of the
     * disclosure button within the toolbar.
     */
    private void addDisclosureButtonToToolBar() {
        GridBagConstraints gbc = createGridBagConstraints(0, 1d, 1d, GridBagConstraints.NONE, GridBagConstraints.SOUTHWEST);
        gbc.insets = new Insets(0, 1, 0, 1);
        add(disclosureButton, gbc);
    }

    /**
     * Refactored code
     *
     * Configures additional properties of the toolbar, such as insets and icons.
     * This method is used to apply any additional custom settings to the toolbar.
     */
    private void configureToolBarProperties() {
        putClientProperty(PaletteToolBarUI.TOOLBAR_INSETS_OVERRIDE_PROPERTY, new Insets(0, 0, 0, 0));
        putClientProperty(PaletteToolBarUI.TOOLBAR_ICON_PROPERTY, new EmptyIcon(10, 8));
    }

    public void setDisclosureStateCount(int newValue) {
        int oldValue = getDisclosureStateCount();
        disclosureButton.putClientProperty(DisclosureIcon.STATE_COUNT_PROPERTY, newValue);
        firePropertyChange(DISCLOSURE_STATE_COUNT_PROPERTY, oldValue, newValue);
    }

    /**
     * Refactored code
     *
     * Sets the disclosure state of the toolbar. This method updates the state of the
     * toolbar and rearranges its components based on the specified state.
     *
     */
    public void setDisclosureState(int newValue) {
        int oldValue = getDisclosureState();
        disclosureButton.putClientProperty(DisclosureIcon.CURRENT_STATE_PROPERTY, newValue);
        removeAll();
        JComponent c = getDisclosedComponent(newValue);

        if (c != null) {
            addComponentWithConstraints(c, createGridBagConstraints(1, 1d, 1d, GridBagConstraints.BOTH, GridBagConstraints.WEST));
            addComponentWithConstraints(disclosureButton, createButtonGridBagConstraints());
        } else {
            addComponentWithConstraints(disclosureButton, createButtonGridBagConstraints());
        }

        validateAncestor();
        repaint();
        firePropertyChange(DISCLOSURE_STATE_PROPERTY, oldValue, newValue);
    }

    /**
     * Refactored code
     *
     * Creates and returns a GridBagConstraints object configured with the specified parameters.
     * This method is a utility for creating constraints for components added to the toolbar.
     */

    private GridBagConstraints createGridBagConstraints(int gridx, double weightx, double weighty, int fill, int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.fill = fill;
        gbc.anchor = anchor;
        return gbc;
    }
    /**
     * Refactored code
     *
     * Creates and returns GridBagConstraints specifically configured for the disclosure button.
     * This method customizes the layout constraints for optimal placement of the disclosure button.
     */
    private GridBagConstraints createButtonGridBagConstraints() {
        GridBagConstraints gbc = createGridBagConstraints(0, 0d, 1d, GridBagConstraints.NONE, GridBagConstraints.SOUTHWEST);
        gbc.insets = new Insets(0, 1, 0, 1);
        return gbc;
    }
    /**
     * Refactored code
     *
     * Adds a component to the toolbar with the specified GridBagConstraints.
     * This method is a utility for adding components to the toolbar with specific layout constraints.
     */
    private void addComponentWithConstraints(JComponent component, GridBagConstraints constraints) {
        add(component, constraints);
    }
    /**
     * Refactored code
     *
     * Validates the ancestor container of this toolbar. This method traverses up
     * the container hierarchy and validates the first non-valid container found.
     */
    private void validateAncestor() {
        Container parent = getParent();
        while (parent.getParent() != null && !parent.getParent().isValid()) {
            parent = parent.getParent();
        }
        parent.validate();
    }

    public int getDisclosureStateCount() {
        Integer value = (Integer) disclosureButton.getClientProperty(DisclosureIcon.STATE_COUNT_PROPERTY);
        return (value == null) ? 2 : value;
    }

    public int getDisclosureState() {
        Integer value = (Integer) disclosureButton.getClientProperty(DisclosureIcon.CURRENT_STATE_PROPERTY);
        return (value == null) ? 1 : value;
    }

    protected JComponent getDisclosedComponent(int state) {
        return new JLabel(Integer.toString(state));
    }
    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     * /
       // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
