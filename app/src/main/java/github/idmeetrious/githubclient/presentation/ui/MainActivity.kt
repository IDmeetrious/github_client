package github.idmeetrious.githubclient.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import github.idmeetrious.githubclient.R
import github.idmeetrious.githubclient.presentation.ui.splash.SplashDialogFragment

class MainActivity : AppCompatActivity() {
    private var splashDialog: SplashDialogFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        splashDialog = SplashDialogFragment()
    }

    override fun onStart() {
        super.onStart()
        splashDialog?.show(supportFragmentManager, "SplashDialogFragment")
    }

    override fun onDestroy() {
        super.onDestroy()
        splashDialog = null
    }
}