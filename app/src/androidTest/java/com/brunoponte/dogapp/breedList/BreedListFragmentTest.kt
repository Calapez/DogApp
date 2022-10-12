package com.brunoponte.dogapp.breedList

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.brunoponte.dogapp.R
import com.brunoponte.dogapp.helpers.launchFragmentInHiltContainer
import com.brunoponte.dogapp.helpers.withDrawable
import com.brunoponte.dogapp.ui.breedList.BreedListFragment
import com.brunoponte.dogapp.ui.breedList.listAdapter.BreedListItemViewHolder
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class BreedListFragmentTest {

    lateinit var fragment: BreedListFragment

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testToggleSortMode() {
        launchFragmentInHiltContainer<BreedListFragment>(Bundle(), R.style.Theme_DogApp) {
            fragment = (this as BreedListFragment)
        }

        onView(withId(R.id.imageSort)).check(matches(withDrawable(R.drawable.ic_asc)))
        onView(withId(R.id.imageSort)).perform(ViewActions.click())
        onView(withId(R.id.imageSort)).check(matches(withDrawable(R.drawable.ic_desc)))
        onView(withId(R.id.imageSort)).perform(ViewActions.click())
        onView(withId(R.id.imageSort)).check(matches(withDrawable(R.drawable.ic_asc)))
    }

    @Test
    fun testToggleListMode() {
        launchFragmentInHiltContainer<BreedListFragment>(Bundle(), R.style.Theme_DogApp) {
            fragment = (this as BreedListFragment)
        }

        onView(withId(R.id.imageListMode)).check(matches(withDrawable(R.drawable.ic_list)))
        onView(withId(R.id.imageListMode)).perform(ViewActions.click())
        onView(withId(R.id.imageListMode)).check(matches(withDrawable(R.drawable.ic_grid)))
        onView(withId(R.id.imageListMode)).perform(ViewActions.click())
        onView(withId(R.id.imageListMode)).check(matches(withDrawable(R.drawable.ic_list)))
    }

    @Test
    fun testClickBreedNavigateToDetails() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInHiltContainer<BreedListFragment>(Bundle(), R.style.Theme_DogApp) {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.breedListFragment)
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.breedListRecyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<BreedListItemViewHolder>(0, click()))

        assertEquals(navController.currentDestination?.id, R.id.breedDetailsFragment)
    }
}
