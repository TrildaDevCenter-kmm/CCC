package mustafaozhan.github.com.mycurrencies.ui.splash

import android.content.Intent
import android.os.Bundle
import mustafaozhan.github.com.mycurrencies.base.activity.BaseActivity
import mustafaozhan.github.com.mycurrencies.ui.main.activity.MainActivity
import mustafaozhan.github.com.mycurrencies.ui.slider.SliderActivity
import javax.inject.Inject

/**
 * Created by Mustafa Ozhan on 2018-07-20.
 */
class SplashActivity : BaseActivity() {
    @Inject
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(
            Intent(
                this,
                if (splashViewModel.isSliderShown()) {
                    MainActivity::class.java
                } else {
                    SliderActivity::class.java
                }
            )
        )

        finish()
    }
}
