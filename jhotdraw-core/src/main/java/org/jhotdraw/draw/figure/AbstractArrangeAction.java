package org.jhotdraw.draw.figure;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.action.AbstractSelectedAction;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.LinkedList;

/**
 * AbstractArrangeAction
 *
 * The class is responsible for dealing with the feature of arranging the figures
 *
 * @Author Michael Ringhus Gertz
 *
 */
public abstract class AbstractArrangeAction extends AbstractSelectedAction {

	/**
	 * Creates an action which acts on the selected figures on the current view
	 * of the specified editor.
	 *
	 * @param editor
	 */
	protected AbstractArrangeAction(DrawingEditor editor) {
		super(editor);
		ResourceBundleUtil labels
			= ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
		labels.configureAction(this, getID());
		updateEnabledState();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final DrawingView view = getView();
		final LinkedList<Figure> figures = new LinkedList<>(view.getSelectedFigures());
		order(view, figures);
		fireUndoableEditHappened(new AbstractUndoableEdit() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getPresentationName() {
				ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
				return labels.getTextProperty(getID());
			}

			@Override
			public void redo() throws CannotRedoException {
				super.redo();
				AbstractArrangeAction.this.order(view, figures);
			}

			@Override
			public void undo() throws CannotUndoException {
				super.undo();
				AbstractArrangeAction.this.reverseOrder(view, figures);
			}
		});
	}

	public abstract void order(DrawingView view, Collection<Figure> figures);

	public abstract void reverseOrder(DrawingView view, Collection<Figure> figures);

	protected abstract String getID();
}
