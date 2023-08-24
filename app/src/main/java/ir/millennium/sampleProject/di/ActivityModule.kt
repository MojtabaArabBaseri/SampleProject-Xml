package ir.millennium.sampleProject.di

import android.app.Activity
import android.content.Context
import androidx.window.layout.WindowMetricsCalculator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import ir.millennium.sampleProject.di.qualifiers.WidthDisplay

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    @ActivityScoped
    @WidthDisplay
    fun provideWidthDisplay(@ActivityContext context: Context): Int {
        val windowMetrics = WindowMetricsCalculator.getOrCreate()
            .computeCurrentWindowMetrics(context as Activity)
        val currentBounds = windowMetrics.bounds
        return currentBounds.width()
    }
}