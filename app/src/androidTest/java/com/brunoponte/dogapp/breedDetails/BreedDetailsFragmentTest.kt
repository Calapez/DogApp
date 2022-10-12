package com.brunoponte.dogapp.breedDetails

import androidx.core.os.bundleOf
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.brunoponte.dogapp.helpers.launchFragmentInHiltContainer
import com.brunoponte.dogapp.ui.breedDetails.BreedDetailsFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.brunoponte.dogapp.R

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class BreedDetailsFragmentTest {

    lateinit var fragment: BreedDetailsFragment

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()

        val fragmentArgs = bundleOf("breedId" to 1)
        launchFragmentInHiltContainer<BreedDetailsFragment>(fragmentArgs, R.style.Theme_DogApp) {
            fragment = (this as BreedDetailsFragment)
        }
    }

    @Test
    fun testBreedPropertiesInViews() {
        onView(withId(R.id.breedNameText)).check(matches(ViewMatchers.withText(
            fragment.viewModel.selectedBreed.value?.name ?: "N/A")))
        onView(withId(R.id.breedNameText)).check(matches(ViewMatchers.withText(
            fragment.viewModel.selectedBreed.value?.name ?: "N/A")))
        onView(withId(R.id.breedGroupText)).check(matches(ViewMatchers.withText(
            fragment.viewModel.selectedBreed.value?.breedGroup ?: "N/A")))
        onView(withId(R.id.breedOriginText)).check(matches(ViewMatchers.withText(
            fragment.viewModel.selectedBreed.value?.origin ?: "N/A")))
        onView(withId(R.id.breedTemperamentText)).check(matches(ViewMatchers.withText(
            fragment.viewModel.selectedBreed.value?.temperament ?: "N/A")))
    }
}
