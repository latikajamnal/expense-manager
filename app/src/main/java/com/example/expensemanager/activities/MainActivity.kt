package com.example.expensemanager.activities

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.expensemanager.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home.*

class MainActivity : BaseActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val settingFragment = SettingFragment()
        val homeFragment = HomeFragment()
        setCurrentFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_settings -> setCurrentFragment(settingFragment)
                R.id.menu_home -> setCurrentFragment(homeFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}

