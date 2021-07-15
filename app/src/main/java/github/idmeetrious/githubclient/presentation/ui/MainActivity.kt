package github.idmeetrious.githubclient.presentation.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import github.idmeetrious.githubclient.R
import github.idmeetrious.githubclient.presentation.ui.downloaded.DownloadedFragment
import github.idmeetrious.githubclient.presentation.ui.splash.SplashDialogFragment

class MainActivity : AppCompatActivity() {
    private var splashDialog: SplashDialogFragment? = null
    private var downloadFragment: DownloadedFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        splashDialog = SplashDialogFragment()
        splashDialog?.show(supportFragmentManager, "SplashDialogFragment")

        downloadFragment = DownloadedFragment()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.downloaded -> {
                downloadFragment?.let {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, it)
                        .addToBackStack("MainActivity")
                        .commit()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.appbar_menu, menu)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        splashDialog = null
    }
}