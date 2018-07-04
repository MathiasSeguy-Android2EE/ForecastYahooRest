/**
 * <ul>
 * <li>WeatherViewTest</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.testview</li>
 * <li>11/04/2016</li>
 * <p/>
 * <li>======================================================</li>
 * <p/>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 * <p/>
 * /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ***************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * <p/>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */

package com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.testview;

import android.app.Activity;
import android.content.ComponentName;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.test.suitebuilder.annotation.LargeTest;

import com.android2ee.formation.restservice.forecastyahoo.withlibs.R;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.model.clientside.current.City;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.transverse.utils.MyLog;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.findcity.CityActivity;
import com.android2ee.formation.restservice.forecastyahoo.withlibs.view.forecast.WeatherActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

/**
 * Created by Mathias Seguy - Android2EE on 11/04/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class WeatherViewTest {
    private static final String TAG = "WeatherViewTest";
    /**
     * {@link IntentsTestRule} is an {@link ActivityTestRule} which inits and releases Espresso
     * Intents before and after each test run.
     *
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public IntentsTestRule<WeatherActivity> weatherActivityIntentsTestRule =
            new IntentsTestRule<>(WeatherActivity.class);

    /**
     * Prepare your test fixture for this test. In this case we register an IdlingResources with
     * Espresso. IdlingResource resource is a great way to tell Espresso when your app is in an
     * idle state. This helps Espresso to synchronize your test actions, which makes tests significantly
     * more reliable.
     */
    @Before
    public void registerIdlingResource() {
        Espresso.registerIdlingResources(
                weatherActivityIntentsTestRule.getActivity().getCountingIdlingResource());
    }

    @Test
    public void testSearchActivity() {
        //track entrance
        MyLog.e(TAG, "testSomething() has been called and the id of menuItem is "+R.id.action_search);
        //open the menu and click on an item
//        openActionBarOverflowOrOptionsMenu(getTargetContext());<-Do not do that with the toolbar
        int citiesSize=((WeatherPresenter)weatherActivityIntentsTestRule.getActivity().getPresenter()).getCities().size();
        assertEquals(4,citiesSize);
        // Click on add picture option
        onView(withId(R.id.action_search))
                .perform(click());


        //You should be in the CityActivity
        intended(hasComponent(new ComponentName(getTargetContext(), CityActivity.class)));
        //check the button search is Disable
        onView(withId(R.id.btn_search_city))
                .check(matches(not(isEnabled())));
        onView(withId(R.id.edt_citySearchedName))
                .perform(typeText("Tou"));
        onView(withId(R.id.btn_search_city))
                .check(matches(isEnabled()));
        onView(withId(R.id.edt_citySearchedName))
                .perform(typeText("louse"));
        onView(withId(R.id.btn_search_city))
                .perform(click());
        //as we have mocked the service, the answer is always the same
        //one city found called Toulouse

        //So now, we browse the ListView and find the first element
        //and we click on it
        onData(withValue("Toulouse"))
                .inAdapterView(withId(R.id.lsvCityList))
                .perform(click());

        //then assert you are back to the WeatherActivity
        onView(withId(R.id.txvTest))
                .check(matches(isDisplayed()))
                .perform(click());
        //and 5 elements should have been displayed
        intended(hasComponent(new ComponentName(getTargetContext(), WeatherActivity.class)));
        WeatherPresenter presenter=((WeatherPresenter)((WeatherActivity)getActivityInstance()).getPresenter());
        assertEquals(5,presenter.getCities().size());
        //scroll the viewpager
        onView(withId(R.id.viewpager))
                .perform(swipeLeft())
                .perform(swipeLeft())
                .perform(swipeLeft())
                .perform(swipeLeft())
                .perform(swipeLeft());
        //so you are deleting Toulouse
        onView(withId(R.id.action_delete))
                .perform(click());
        //ensure the dialog is disaplyed
        onView(withId(android.R.id.message))
                .inRoot(isDialog()) // <---
                .check(matches(isDisplayed()));
        //cancel the action
        onView(withId(android.R.id.button2)).perform(click());
        //check the number of elements again
        assertEquals(5,presenter.getCities().size());
        //so you are deleting Toulouse
        onView(withId(R.id.action_delete))
                .perform(click());
        //ensure the dialog is disaplyed
        onView(withId(android.R.id.message))
                .inRoot(isDialog()) // <---
                .check(matches(isDisplayed()));
        //do the action
        onView(withId(android.R.id.button1)).perform(click());
        //check the number of elements again
        assertEquals(4,presenter.getCities().size());
        //ok perfect
    }
    /***********************************************************
     *  Usefull methods when dealing with espressos
     **********************************************************/
    /**
     * https://github.com/chiuki/espresso-samples/blob/master/list-view-basic/app/src/androidTest/java/com/sqisland/android/espresso/list_view_basic/MainActivityTest.java
     * Chiuki code
     * @param value
     * @return
     */
    public static Matcher<Object> withValue(final String value) {
        return new BoundedMatcher<Object, City>(City.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has value " + value);
            }
            @Override
            public boolean matchesSafely(City item) {
                return item.getName().equals(value);
            }
        };
    }

    /**
     * Obtaining the current activity
     * @return
     */
    Activity currentActivity;
    public Activity getActivityInstance(){

        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()){
                    currentActivity = (Activity) resumedActivities.iterator().next();
                }
            }
        });

        return currentActivity;
    }

}
