package com.esaudev.aristicomp.auth.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.esaudev.aristicomp.auth.ui.LoginActivity
import com.esaudev.aristicomp.auth.utils.AuthConstants.WALKER_USER
import com.esaudev.aristicomp.model.Session
import com.esaudev.aristicomp.owner.ui.OwnerActivity
import com.esaudev.aristicomp.utils.Constants.SHARED_EMAIL
import com.esaudev.aristicomp.utils.Constants.SHARED_PASSWORD
import com.esaudev.aristicomp.walker.ui.WalkerActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()

        if (isUserLoggedIn()){
            viewModel.login(getSavedEmail()!!, getSavedPassword()!!)
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun initObservers(){
        viewModel.loginSuccessful.observe(this, { isSuccessful ->
            if (isSuccessful){
                viewModel.getUserData()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })

        viewModel.userReadyToContinue.observe(this, { userReadyToContinue ->
            if (userReadyToContinue) {
                if (Session.USER_LOGGED.type == WALKER_USER){
                    goWalkerActivity()
                } else {
                    goOwnerActivity()
                }
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })
    }

    private fun goWalkerActivity(){
        val intent = Intent(this, WalkerActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun goOwnerActivity(){
        val intent = Intent(this, OwnerActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isUserLoggedIn(): Boolean{
        return getSavedEmail()?.isNotEmpty() == true && getSavedPassword()?.isNotEmpty() == true
    }

    private fun getSavedEmail() = sharedPrefs.getString(SHARED_EMAIL, "")
    private fun getSavedPassword() = sharedPrefs.getString(SHARED_PASSWORD, "")
}