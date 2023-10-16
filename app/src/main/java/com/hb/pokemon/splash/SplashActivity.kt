package com.hb.pokemon.splash

import android.content.Intent
import android.os.CountDownTimer
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import com.hb.base.BaseActivity
import com.hb.pokemon.R
import com.hb.pokemon.databinding.ActivitySplashBinding
import com.hb.pokemon.search.SearchActivity

/**
 * Splash screen activity displayed when the app is launched.
 *
 * @author Robin
 * @version 1.0
 * @since 2023-10-13
 */
class SplashActivity : BaseActivity<ActivitySplashBinding, AndroidViewModel>() {

    val countdownMillis: Long = 3000
    val countDownInterval: Long = 1000

    /**
     * Get the layout resource ID for this activity.
     * @return Int The layout resource ID.
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    /**
     * Setup the action bar for this activity.
     */
    override fun setupActionBar() {
    }

    /**
     * Initialize the splash screen.
     */
    override fun init() {
        object : CountDownTimer(countdownMillis, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                findViewById<TextView>(R.id.countdownTextView).text = "$secondsRemaining"
            }

            override fun onFinish() {
                startActivity(Intent(this@SplashActivity, SearchActivity::class.java))
                finish()
            }
        }.start()
    }

    /**
     * Initialize the ViewModel. Since this activity does not use a ViewModel, it returns null.
     * @return AndroidViewModel? The ViewModel instance (null in this case).
     */
    override fun initViewModel(): AndroidViewModel? {
        return null
    }
}