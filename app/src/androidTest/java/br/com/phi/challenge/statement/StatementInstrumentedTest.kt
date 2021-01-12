package br.com.phi.challenge.statement

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import br.com.phi.challenge.R
import br.com.phi.challenge.view.statement.StatementActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by pcamilo on 12/01/2021.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class StatementInstrumentedTest {

    @Before fun setUp() {
        launch(StatementActivity::class.java)
    }

    @Test
    fun views_displayed() {
        onView(withId(R.id.tv_title_amount)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_hide_balance)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_info_statements)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar_title)).check(matches(isDisplayed()))
    }

    @Test
    fun views_withText() {
        onView(withId(R.id.tv_title_amount)).check(matches(withText(R.string.title_my_statement)))
        onView(withId(R.id.tv_info_statements)).check(matches(withText(R.string.title_info_movements)))
        onView(withId(R.id.tv_info_statements)).check(matches(withText(R.string.title_info_movements)))
        onView(withId(R.id.toolbar_title)).check(matches(withText(R.string.title_statement)))
    }
}