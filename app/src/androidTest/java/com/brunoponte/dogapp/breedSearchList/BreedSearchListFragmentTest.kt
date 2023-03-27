package com.brunoponte.dogapp.breedSearchList

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.brunoponte.dogapp.R
import com.brunoponte.dogapp.helpers.RecyclerViewItemCountAssertion.Companion.withItemCount
import com.brunoponte.dogapp.helpers.launchFragmentInHiltContainer
import com.brunoponte.dogapp.ui.breedSearchList.BreedSearchListFragment
import com.brunoponte.dogapp.ui.breedSearchList.listAdapter.BreedSearchListItemViewHolder
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class BreedSearchListFragmentTest {

    private lateinit var fragment: BreedSearchListFragment

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testInitialViews() {
        launchFragmentInHiltContainer<BreedSearchListFragment>(Bundle(), R.style.Theme_DogApp) {
            fragment = (this as BreedSearchListFragment)
        }

        onView(withId(R.id.searchView)).check(matches(withText("")))
        onView(withId(R.id.breedsRecyclerView)).check(withItemCount(0))
    }

    @Test
    fun testSearchBreeds() {
        launchFragmentInHiltContainer<BreedSearchListFragment>(Bundle(), R.style.Theme_DogApp) {
            fragment = (this as BreedSearchListFragment)
        }

        onView(withId(R.id.searchView)).perform(clearText(), typeText("Foo"))
        onView(withId(R.id.breedsRecyclerView)).check(withItemCount(3))
        onView(withId(R.id.searchView)).perform(clearText())
        onView(withId(R.id.breedsRecyclerView)).check(withItemCount(0))
    }

    @Test
    fun testClickBreedNavigateToDetails() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInHiltContainer<BreedSearchListFragment>(Bundle(), R.style.Theme_DogApp) {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.breedSearchListFragment)
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.searchView)).perform(typeText("Foo"))
        onView(withId(R.id.breedsRecyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<BreedSearchListItemViewHolder>(0, ViewActions.click()))

        Assert.assertEquals(navController.currentDestination?.id, R.id.breedDetailsFragment)
    }
}
