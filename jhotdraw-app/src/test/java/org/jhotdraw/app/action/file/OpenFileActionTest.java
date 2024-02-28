package org.jhotdraw.app.action.file;

import org.jhotdraw.api.app.View;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.jhotdraw.api.app.Application;
import org.mockito.Mockito;

public class OpenFileActionTest {

    private OpenFileAction openFileAction;

    @Before
    public void setUp() {
        Application app = Mockito.mock(Application.class);
        openFileAction = new OpenFileAction(app);
    }

    @Test
    public void testDisableApplication() {
        openFileAction.disableApplication();
        Assert.assertFalse(openFileAction.getApplication().isEnabled());
    }

    @Test
    public void testEnableApplication() {
        openFileAction.disableApplication();
        Assert.assertFalse(openFileAction.getApplication().isEnabled());
    }

    @Test
    public void testFindEmptyViewWithNoView() {
        View view = openFileAction.findEmptyView();
        //Should return null
        Assert.assertNull(view);
    }

    @Test
    public void testFindEmptyViewWithEmptyView() {
        openFileAction.getApplication().add(openFileAction.getApplication().createView());
        View view = openFileAction.findEmptyView();
        //Should return null
        Assert.assertNull(view);
    }
}
