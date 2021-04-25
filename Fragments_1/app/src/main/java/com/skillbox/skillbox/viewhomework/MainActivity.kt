package com.skillbox.skillbox.viewhomework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.list_fragment.*

class MainActivity : AppCompatActivity(), ItemSelectListener {
    private val tag = "MainActivity"
    private var uncorrectlyState: FormState = FormState(false, "")
    var successLogin = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startLoginFragment()
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            uncorrectlyState =
                savedInstanceState.getParcelable<FormState>(MainActivity.STATE_KEY)
                    ?: error("Unexpected key")
            successLogin = uncorrectlyState.valid
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        uncorrectlyState = FormState(successLogin, "")
        outState.putParcelable(STATE_KEY, uncorrectlyState)
    }

    private fun startLoginFragment() {
        val alreadyHasFragment = supportFragmentManager.findFragmentById(R.id.container) != null
        if (!alreadyHasFragment) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, LoginFragment())
                .commit()
        }
    }

    companion object {
        private var STATE_KEY = "stateKey"
    }

    override fun onItemSelect(text: String) {
        choosePageTextView.text = text
        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(
                R.id.mainContainer,
                DetailFragment.newInstance(choosePageTextView.text.toString())
            )
            .addToBackStack("backToMainScreen")
            .commit()
    }
}