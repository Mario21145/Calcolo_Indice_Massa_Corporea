package com.example.bodymassindex

import android.app.Activity
import android.app.Instrumentation
import android.app.PendingIntent.getActivity
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.init
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.Intents.release
import androidx.test.espresso.intent.matcher.IntentMatchers.toPackage
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bodymassindex.viewModel.BmiViewModel
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.util.function.Predicate.not

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var viewModel: BmiViewModel

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Before
    fun setup(){
        init()
        viewModel = BmiViewModel()
    }

    @After
    fun afterSetup(){
        release()
    }

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.bodymassindex", appContext.packageName)
    }

    @Test
    fun checkLiveBmi(){
        onView(withId(R.id.LiveBmi)).check(matches(isDisplayed()))
    }

    @Test
    fun checkWeightOnDisplay(){
        onView(withId(R.id.Weight)).perform(typeText("90"))
        onView(withId(R.id.LiveBmi)).check(matches(isDisplayed()))
    }

    @Test
    fun checkHeightOnDisplay(){
        onView(withId(R.id.Height)).perform(typeText("180"))
        onView(withId(R.id.LiveBmi)).check(matches(isDisplayed()))
    }

    @Test
    fun checkClickOnSex(){
        onView(withId(R.id.men)).perform(click())
    }

    @Test
    fun checkIfDialogIsOpen(){
        onView(withId(R.id.CalculateButton)).perform(click()).inRoot(isDialog()).check(matches(
            isDisplayed()))
    }

    @Test
    fun checkIntentItalianTable(){
        val resultData = Intent()
        val linkTableItalian = "https://www.google.com/search?sca_esv=575778037&sxsrf=AM9HkKmv-fHbFlJYV_gAQqWnP_CBs56tFA:1698065827360&q=le+tabelle+per+il+calcolo+del+bmi+sono+uguali+tra+uomo+e+donna%3F&tbm=isch&source=lnms&sa=X&ved=2ahUKEwiQnceTnIyCAxUqgf0HHdnqAiEQ0pQJegQICRAB&biw=1920&bih=923&dpr=1#imgrc=zkjUA8pt2jwLGM"
        resultData.putExtra("linkTable", linkTableItalian)
        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)
        intending(toPackage("com.android.bodymassindex")).respondWith(result)
        onView(withId(R.id.fab)).perform(click())
    }

    @Test
    fun checkIntentEnglishTable(){
        val resultData = Intent()
        val linkTableEnglishMenWomen = "https://www.google.com/search?q=body+mass+index+&tbm=isch&ved=2ahUKEwiH6euvnIyCAxXGh_0HHUN-AI4Q2-cCegQIABAA&oq=body+mass+index+&gs_lcp=CgNpbWcQAzIFCAAQgAQyBQgAEIAEMgUIABCABDIECAAQHjIECAAQHjIECAAQHjIECAAQHjIECAAQHjIECAAQHjIECAAQHjoECCMQJzoHCCMQ6gIQJzoLCAAQgAQQsQMQgwE6CAgAELEDEIMBOggIABCABBCxAzoECAAQAzoKCAAQigUQsQMQQzoHCAAQigUQQ1DdCliTKWCtKmgDcAB4AIABYogBswySAQIxOZgBAKABAaoBC2d3cy13aXotaW1nsAEKwAEB&sclient=img&ei=3m02ZYeCKcaP9u8Pw_yB8Ag&bih=923&biw=1920#imgrc=G-q9svKAv8_qFM"
        resultData.putExtra("linkTable",linkTableEnglishMenWomen)
        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)
        intending(toPackage("com.android.bodymassindex")).respondWith(result)
        onView(withId(R.id.fab)).perform(click())
    }

    @Test
    fun checkErrorWeightToastIsEmpty(){
        onView(withId(R.id.Weight)).perform(typeText("") , closeSoftKeyboard())
        onView(withId(R.id.CalculateButton)).perform(click()).inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }

    @Test
    fun checkErrorHeightToastIsEmpty(){
        onView(withId(R.id.Weight)).perform(typeText("90") , closeSoftKeyboard())
        Thread.sleep(1000)
        onView(withId(R.id.CalculateButton)).perform(click()).inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }

    @Test
    fun checkErrorSexToastIsEmpty(){
        onView(withId(R.id.Weight)).perform(typeText("90")  , closeSoftKeyboard())
        onView(withId(R.id.Height)).perform(typeText("190") , closeSoftKeyboard())
        Thread.sleep(1000)
        onView(withId(R.id.CalculateButton)).perform(click()).inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }


}