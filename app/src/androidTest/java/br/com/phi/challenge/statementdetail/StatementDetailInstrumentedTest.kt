package br.com.phi.challenge.statementdetail

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import br.com.phi.challenge.R
import br.com.phi.challenge.view.statementdetail.StatementDetailActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by pcamilo on 12/01/2021.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class StatementDetailInstrumentedTest {

    @Before fun setUp() {
        launch(StatementDetailActivity::class.java)
    }

    @Test
    fun views_displayed() {
        onView(withId(R.id.tv_receipt_title)).check(matches(isDisplayed()))
        onView(withId(R.id.label_value)).check(matches(isDisplayed()))
        onView(withId(R.id.label_destiny)).check(matches(isDisplayed()))
        onView(withId(R.id.label_bank)).check(matches(isDisplayed()))
        onView(withId(R.id.label_datetime)).check(matches(isDisplayed()))
        onView(withId(R.id.label_authentication)).check(matches(isDisplayed()))
    }

    @Test
    fun views_withText() {
        onView(withId(R.id.tv_receipt_title)).check(matches(withText(R.string.title_receipt)))
        onView(withId(R.id.label_value)).check(matches(withText(R.string.title_value)))
        onView(withId(R.id.label_destiny)).check(matches(withText(R.string.title_destiny)))
        onView(withId(R.id.label_bank)).check(matches(withText(R.string.title_bank)))
        onView(withId(R.id.label_datetime)).check(matches(withText(R.string.title_date_time)))
        onView(withId(R.id.label_authentication)).check(matches(withText(R.string.title_authentication)))
        onView(withId(R.id.btn_share_receipt)).check(matches(withText(R.string.title_btn_share)))
        onView(withId(R.id.toolbar_title)).check(matches(withText(R.string.string_empty)))
    }
}