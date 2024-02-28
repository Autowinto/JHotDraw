/*
 * @(#)OpenFileAction.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.app.action.file;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import org.jhotdraw.action.AbstractApplicationAction;
import org.jhotdraw.api.app.Application;
import org.jhotdraw.api.app.View;
import org.jhotdraw.api.gui.URIChooser;
import org.jhotdraw.gui.JSheet;
import org.jhotdraw.net.URIUtil;
import org.jhotdraw.util.ResourceBundleUtil;
import org.jhotdraw.util.prefs.PreferencesUtil;

/**
 * Presents an {@code URIChooser} and loads the selected URI into an
 * empty view. If no empty view is available, a new view is created.
 * <p>
 * This action is called when the user selects the Open item in the File
 * menu. The menu item is automatically created by the application.
 * A Recent Files sub-menu is also automatically generated.
 * <p>
 * If you want this behavior in your application, you have to create it
 * and put it in your {@code ApplicationModel} in method
 * {@link org.jhotdraw.app.ApplicationModel#initApplication}.
 * <p>
 * This action is designed for applications which automatically
 * create a new view for each opened file. This action goes together with
 * {@link NewFileAction}, {@link OpenDirectoryAction} and
 * {@link CloseFileAction}.
 * This action should not be used together with {@link LoadFileAction}.
 * <hr>
 * <b>Features</b>
 *
 * <p>
 * <em>Allow multiple views per URI</em><br>
 * When the feature is disabled, {@code OpenFileAction} prevents opening an URI
 * which* is opened in another view.<br>
 * See {@link org.jhotdraw.app} for a description of the feature.
 * </p>
 *
 * <p>
 * <em>Open last URI on launch</em><br>
 * {@code OpenFileAction} supplies data for this feature by calling
 * {@link Application#addRecentURI} when it successfully opened a file.
 * See {@link org.jhotdraw.app} for a description of the feature.
 * </p>
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class OpenFileAction extends AbstractApplicationAction {

    private static final long serialVersionUID = 1L;
    public static final String ID = "file.open";
    private static final String BUNDLE = "org.jhotdraw.app.Labels";

    public OpenFileAction(Application app) {
        super(app);
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle(BUNDLE);
        labels.configureAction(this, ID);
    }

    protected URIChooser getChooser(View view) {
        // Note: We pass null here, because we want the application-wide chooser
        return getApplication().getOpenChooser(view);
    }

    protected URIChooser getChooser() {
        // Note: We pass null here, because we want the application-wide chooser
        return getApplication().getOpenChooser(null);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        final Application app = getApplication();
        if (!app.isEnabled()) {
            return;
        }

        disableApplication();

        View view = findEmptyView();
        boolean disposeView = view == null;

        URIChooser chooser = getChooser(view);
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);

        if (showDialog(chooser, app.getComponent()) != JFileChooser.APPROVE_OPTION) {
            if (disposeView) {
                app.dispose(view);
            }
            enableApplication();
            return;
        }

        app.show(view);
        URI uri = chooser.getSelectedURI();

        processViewsForUniqueURI(uri, disposeView);

        openViewFromURI(view, uri, chooser);

    }

    private void processViewsForUniqueURI(URI uri, boolean disposeView) {
        Application app = getApplication();

        if (!app.getModel().isAllowMultipleViewsPerURI()) {
            for (View view : app.getViews()) {
                if (view.getURI() != null && view.getURI().equals(uri)) {
                    view.getComponent().requestFocus();
                    if (disposeView) {
                        app.dispose(view);
                    }
                    enableApplication();
                    return;
                }
            }
        }
    }

    private View findEmptyView() {
        Application app = getApplication();
        View emptyView = app.getActiveView();
        if (emptyView == null
                || !emptyView.isEmpty()
                || !emptyView.isEnabled()) {
            emptyView = null;
        }
        return emptyView;
    }

    private void enableApplication() {
        Application app = getApplication();
        app.setEnabled(true);
    }

    private void disableApplication() {
        Application app = getApplication();
        app.setEnabled(false);
    }

    protected void openViewFromURI(final View view, final URI uri, final URIChooser chooser) {
        final Application app = getApplication();
        enableApplication();
        view.setEnabled(false);

        int multipleOpenId = 1;
        for (View aView : app.views()) {
            if (aView != view
                    && aView.isEmpty()) {
                multipleOpenId = Math.max(multipleOpenId, aView.getMultipleOpenId() + 1);
            }
        }
        view.setMultipleOpenId(multipleOpenId);
        view.setEnabled(false);

        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                boolean exists = true;
                exists = new File(uri).exists();

                if (exists) {
                    view.read(uri, chooser);
                } else {
                    ResourceBundleUtil labels = ResourceBundleUtil.getBundle(BUNDLE);
                    throw new IOException(
                            labels.getFormatted("file.open.fileDoesNotExist.message", URIUtil.getName(uri)));
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    final Application app = getApplication();
                    view.setURI(uri);
                    view.setEnabled(true);
                    Frame w = (Frame) SwingUtilities.getWindowAncestor(view.getComponent());
                    if (w != null) {
                        w.setExtendedState(w.getExtendedState() & ~Frame.ICONIFIED);
                        w.toFront();
                    }
                    view.getComponent().requestFocus();
                    app.addRecentURI(uri);
                    enableApplication();
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(OpenFileAction.class.getName()).log(Level.SEVERE, null, ex);
                    failed(ex);
                }
            }

            protected void failed(Throwable value) {
                value.printStackTrace();
                view.setEnabled(true);
                enableApplication();
                String message = value.getMessage() != null ? value.getMessage() : value.toString();
                ResourceBundleUtil labels = ResourceBundleUtil.getBundle(BUNDLE);
                JSheet.showMessageSheet(view.getComponent(),
                        "<html>" + UIManager.getString("OptionPane.css")
                                + "<b>" + labels.getFormatted("file.open.couldntOpen.message", URIUtil.getName(uri))
                                + "</b><p>"
                                + ((message == null) ? "" : message),
                        JOptionPane.ERROR_MESSAGE);
            }
        }.execute();
    }

    /**
     * We implement JFileChooser.showDialog by ourselves, so that we can center
     * dialogs properly on screen on Mac OS X.
     */
    public int showDialog(URIChooser chooser, Component parent) {
        final int[] returnValue = new int[1];
        final JDialog dialog = createDialog(chooser, parent);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                returnValue[0] = JFileChooser.CANCEL_OPTION;
            }
        });

        chooser.addActionListener(e -> {
            if ("CancelSelection".equals(e.getActionCommand())) {
                returnValue[0] = JFileChooser.CANCEL_OPTION;
                dialog.setVisible(false);
            } else if ("ApproveSelection".equals(e.getActionCommand())) {
                returnValue[0] = JFileChooser.APPROVE_OPTION;
                dialog.setVisible(false);
            }
        });

        returnValue[0] = JFileChooser.ERROR_OPTION;
        chooser.rescanCurrentDirectory();
        dialog.setVisible(true);
        dialog.removeAll();
        dialog.dispose();
        return returnValue[0];
    }

    /**
     * We implement JFileChooser.showDialog by ourselves, so that we can center
     * dialogs properly on screen on Mac OS X.
     */
    protected JDialog createDialog(URIChooser chooser, Component parent) throws HeadlessException {
        String title = chooser.getDialogTitle();
        if (chooser instanceof JFileChooser) {
            ((JFileChooser) chooser).getAccessibleContext().setAccessibleDescription(title);
        }
        JDialog dialog;
        Window window = (parent == null || (parent instanceof Window)) ? (Window) parent
                : SwingUtilities.getWindowAncestor(parent);
        dialog = new JDialog(window, title, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setComponentOrientation(chooser.getComponent().getComponentOrientation());
        Container contentPane = dialog.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(chooser.getComponent(), BorderLayout.CENTER);
        if (JDialog.isDefaultLookAndFeelDecorated()) {
            boolean supportsWindowDecorations = UIManager.getLookAndFeel().getSupportsWindowDecorations();
            if (supportsWindowDecorations) {
                dialog.getRootPane().setWindowDecorationStyle(JRootPane.FILE_CHOOSER_DIALOG);
            }
        }
        Preferences prefs = PreferencesUtil.userNodeForPackage(getApplication().getModel().getClass());
        PreferencesUtil.installFramePrefsHandler(prefs, "openChooser", dialog);
        return dialog;
    }
}
