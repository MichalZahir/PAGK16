package com.example.michalzahir.pagk16;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FinalRegisterTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void finalRegisterTest() {

       for(int i =0 ; i<200 ; i++){

        String ID = generatePasswordLogin();
       System.out.println("i =" + i+ " ID "+ID);

           try {
               TimeUnit.SECONDS.sleep(3);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btnLinkToRegisterScreen), withText("Not a member? Sign up now."), isDisplayed()));
           try {
               TimeUnit.SECONDS.sleep(3);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           appCompatButton.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.name), isDisplayed()));
        editText.perform(replaceText(ID), closeSoftKeyboard());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.password), isDisplayed()));
        editText2.perform(replaceText(ID), closeSoftKeyboard());

        ViewInteraction button = onView(
                allOf(withId(R.id.btnRegister), withText("REGISTER"), isDisplayed()));
        button.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button2), withText("Log tf out"), isDisplayed()));
        appCompatButton2.perform(click());
       }
    }
    public String generatePasswordLogin(){
        String IDpwd = String.valueOf(java.util.UUID.randomUUID());
        return IDpwd;
    }

}
