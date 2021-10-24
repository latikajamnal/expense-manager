package com.example.expensemanager.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.expensemanager.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val settingFragment = SettingFragment()
        val homeFragment = HomeFragment()
        val expenseActivityFragment = ExpenseActivitiesFragment()
        setCurrentFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_settings -> setCurrentFragment(settingFragment)
                R.id.menu_home -> setCurrentFragment(homeFragment)
                R.id.menu_activities -> setCurrentFragment(expenseActivityFragment)
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

