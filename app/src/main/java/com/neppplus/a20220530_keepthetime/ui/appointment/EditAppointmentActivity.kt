package com.neppplus.a20220530_keepthetime.ui.appointment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.a20220530_keepthetime.BaseActivity
import com.neppplus.a20220530_keepthetime.R
import com.neppplus.a20220530_keepthetime.databinding.ActivityEditAppointmentBinding

class EditAppointmentActivity : BaseActivity() {

    lateinit var binding : ActivityEditAppointmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_appointment)
        binding.mapView.onCreate(savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.addBtn.setOnClickListener {

//            1. 약속의 제목 정했는가
            val inputTitle = binding.titleEdt.text.toString()
            if (inputTitle.isBlank()) {
                Toast.makeText(mContext, "약속 제목을 정해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            2. 약속 장소명을 정했는가?
            val inputPlaceName = binding.placeNameTxt.text.toString()
            if (inputPlaceName.isBlank()) {
                Toast.makeText(mContext, "약속 장소명을 기입해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            날짜 선택


//            시간 선택

        }
    }

    override fun setValues() {
        titleTxt.text = "새 약속 만들기"

        binding.mapView.getMapAsync {
            val naverMap = it
        }
    }
}