package router.fu;

import org.junit.After;
import org.junit.Before;
import org.realityforge.braincheck.BrainCheckTestUtil;

public abstract class AbstractRouterFuTest {
    @Before
    public void beforeTest() {
        BrainCheckTestUtil.resetConfig(false);
    }

    @After
    public void afterTest() {
        BrainCheckTestUtil.resetConfig(true);
    }
}
