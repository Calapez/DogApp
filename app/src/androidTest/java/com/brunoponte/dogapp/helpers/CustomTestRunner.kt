package com.brunoponte.dogapp.helpers

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.LayoutInflaterCompat
import androidx.test.runner.AndroidJUnitRunner
import com.google.android.material.progressindicator.CircularProgressIndicator
import dagger.hilt.android.testing.HiltTestApplication

// A custom runner to set up the instrumented application class for tests.
class CustomTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }

    override fun callActivityOnCreate(activity: Activity?, bundle: Bundle?) {
        if (activity is AppCompatActivity) {
            LayoutInflaterCompat.setFactory2(activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                NoAnimationInflaterFactory(activity.delegate)
            )
        }

        super.callActivityOnCreate(activity, bundle)
    }

    private class NoAnimationInflaterFactory(private val appCompatDelegate: AppCompatDelegate) : LayoutInflater.Factory2 {

        override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? =
            when (name) {
                CircularProgressIndicator::class.java.name -> {
                    StubCustomProgressButton(context, attrs)
                }
                else -> appCompatDelegate.createView(null, name, context, attrs)
            }

        override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? =
            onCreateView(null, name, context, attrs)

        private class StubCustomProgressButton @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0)
            : ProgressBar(context, attrs, defStyleAttr) {

            override fun startAnimation(animation: Animation) {
                // no-op
            }
        }
    }
}
