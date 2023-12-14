package com.sharma.lokalassignment.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sharma.lokalassignment.R
import com.sharma.lokalassignment.databinding.ActivityMainBinding
import com.sharma.lokalassignment.presentation.fragment.AllProductsFragment
import com.sharma.lokalassignment.presentation.helpers.LoaderHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        LoaderHelper.Builder(this)
        addFragment(AllProductsFragment(), null)
    }

    fun addFragment(fragment: Fragment, productId: Int?) {
        fragment.arguments = Bundle().also { b -> productId?.let { b.putInt(PRODUCT_ID_KEY, productId) }  }
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .commit()
    }

    fun removeFragment() {
        supportFragmentManager.apply {
            popBackStack()
        }
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 1) finish()
        else super.onBackPressed()
    }

    companion object {
        const val PRODUCT_ID_KEY = "Product_Id_Key"
    }
}