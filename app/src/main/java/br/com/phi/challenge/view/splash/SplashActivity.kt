package br.com.phi.challenge.view.splash

import android.content.Intent
import androidx.constraintlayout.motion.widget.MotionLayout
import br.com.phi.challenge.R
import br.com.phi.challenge.databinding.ActivitySplashBinding
import br.com.phi.challenge.view.base.BaseActivity
import br.com.phi.challenge.view.statement.StatementActivity
import br.com.phi.challenge.viewmodel.splash.SplashViewModel

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(SplashViewModel::class, R.layout.activity_splash) {

    override fun initActivity() {
         viewBinding.contentMotionLayout.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) { openStatementActivity() }
        })
    }

    private fun openStatementActivity() {
        startActivity(Intent(this, StatementActivity::class.java)).apply {
            finish()
        }
    }

    override fun setupToolbar() {}
}