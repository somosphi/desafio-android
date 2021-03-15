package br.com.andreviana.phi.desafioandroid

import android.app.Activity
import android.app.Application
import android.os.Bundle
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class Main : Application() {

    override fun onCreate() {
        super.onCreate()
        timberDebug()
        registerCallback()
    }

    private fun timberDebug() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun registerCallback() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                Timber.tag(TAG).i("onActivityCreated $activity")
            }

            override fun onActivityStarted(activity: Activity) {
                Timber.tag(TAG).i("onActivityStarted $activity")
            }

            override fun onActivityResumed(activity: Activity) {
                Timber.tag(TAG).i("onActivityResumed $activity")
            }

            override fun onActivityPaused(activity: Activity) {
                Timber.tag(TAG).i("onActivityPaused $activity")
            }

            override fun onActivityStopped(activity: Activity) {
                Timber.tag(TAG).i("onActivityStopped $activity")
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                Timber.tag(TAG).i("onActivitySaveInstanceState $activity")
            }

            override fun onActivityDestroyed(activity: Activity) {
                Timber.tag(TAG).i("onActivityDestroyed $activity")
            }
        })
    }

    companion object {
        private const val TAG = "MAIN"
    }
}