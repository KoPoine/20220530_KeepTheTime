package com.neppplus.a20220530_keepthetime.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.a20220530_keepthetime.BaseActivity
import com.neppplus.a20220530_keepthetime.R
import com.neppplus.a20220530_keepthetime.databinding.ActivityMyPlaceListBinding

class MyPlaceListActivity : BaseActivity() {

    lateinit var binding : ActivityMyPlaceListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_place_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

    }
}