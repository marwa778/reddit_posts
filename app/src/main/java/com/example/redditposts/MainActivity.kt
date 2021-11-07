package com.example.redditposts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.SplitInstallRequest
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.Nullable

import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode
import com.google.android.play.core.splitinstall.SplitInstallSessionState
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallManager

class MainActivity : AppCompatActivity(), SplitInstallStateUpdatedListener {
    private val LOG_TAG: String = MainActivity::class.java.getSimpleName()

    private var request: SplitInstallRequest? = null
    private var sim: SplitInstallManager? = null

    private var moduleName: String? = null
    private var className: String? = null

    fun MainActivity() {}

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* instance the {@link SplitInstallManager}: */
        sim = SplitInstallManagerFactory.create(this.applicationContext)

        /* obtain the feature module & class name from arguments */
        if (this.intent != null) {
            moduleName = "postlist"
            className = "com.example.postfavorite.MainActivity"
            if (moduleName != null && className != null) {
                startFeatureActivity(moduleName, className)
            } else {
                Log.e(LOG_TAG, "module and class are required.")
            }
        }
    }

    /** it listens for the split-install session state  */
    override fun onStateUpdate(state: SplitInstallSessionState) {
        if (state.errorCode() == SplitInstallErrorCode.NO_ERROR && state.status() == SplitInstallSessionStatus.INSTALLED) {
            Log.d(LOG_TAG, "dynamic feature " + moduleName + " had been installed.")
            startFeatureActivity(moduleName, className)
        } else {
            // this.OnSplitInstallStatus(state);
        }
    }

    /** it checks if the dynamic feature module is installed and then either installs it - or starts the desired activity  */
    private fun startFeatureActivity(@NonNull moduleName: String?, @NonNull className: String?) {
        if (sim!!.installedModules.contains(moduleName)) {
            Log.d(LOG_TAG, "dynamic feature module $moduleName already installed.")
            val intent = this.intent
            intent.setClassName(BuildConfig.APPLICATION_ID, className!!)
            this.startActivity(intent)
            finish()
        } else {
            Log.d(LOG_TAG, "dynamic feature module $moduleName is not installed.")
            installFeatureModule(moduleName)
        }
    }

    /** it installs a dynamic feature module on demand  */
    private fun installFeatureModule(@NonNull moduleName: String?) {
        Log.d(LOG_TAG, "dynamic feature module $moduleName will be installed.")
        request = SplitInstallRequest.newBuilder().addModule(moduleName).build()
        sim!!.registerListener(this)
        sim!!.startInstall(request!!)
    }
}
