package com.neppplus.a20220530_keepthetime.ui.appointment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.a20220530_keepthetime.BaseActivity
import com.neppplus.a20220530_keepthetime.R
import com.neppplus.a20220530_keepthetime.adapters.MyFriendSpinnerAdapter
import com.neppplus.a20220530_keepthetime.adapters.StartPlaceSpinnerAdapter
import com.neppplus.a20220530_keepthetime.databinding.ActivityEditAppointmentBinding
import com.neppplus.a20220530_keepthetime.models.BasicResponse
import com.neppplus.a20220530_keepthetime.models.PlaceData
import com.neppplus.a20220530_keepthetime.models.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EditAppointmentActivity : BaseActivity() {

    lateinit var binding : ActivityEditAppointmentBinding

//    선택한 약속 일시를 저장할 멤버변수
    val mSelectedDateTime = Calendar.getInstance()  // 기본값 : 현재시간

//    출발 장소를 담고있는 Spinner 관련 변수
    var mStartPlaceList = ArrayList<PlaceData>()
    lateinit var mStartPlaceSpinnerAdapter : StartPlaceSpinnerAdapter

//    친구 목록을 담고 있는 Spinner 관련 변수
    var mFriendsList = ArrayList<UserData>()
    lateinit var mFriendsSpinnerAdapter: MyFriendSpinnerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_appointment)
        binding.mapView.onCreate(savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//            날짜 선택
        binding.dateTxt.setOnClickListener {
//            날짜를 선택하고 할 일(인터페이스)를 작성
            val dl = object : DatePickerDialog.OnDateSetListener{
                override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
                    mSelectedDateTime.set(year, month, day)

                    val sdf = SimpleDateFormat("yyyy. M. d (E)")
                    Log.d("선택된 시간", sdf.format(mSelectedDateTime.time))

                    binding.dateTxt.text = sdf.format(mSelectedDateTime.time)
                }
            }

//            DatePickerDialog 팝업
             val dpd = DatePickerDialog(
                 mContext,
                 dl,
                 mSelectedDateTime.get(Calendar.YEAR),
                 mSelectedDateTime.get(Calendar.MONTH),
                 mSelectedDateTime.get(Calendar.DAY_OF_MONTH)
             )

            dpd.show()
        }

//            시간 선택
        binding.timeTxt.setOnClickListener {
            val tsl = object : TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
                    mSelectedDateTime.set(Calendar.HOUR_OF_DAY, hour)
                    mSelectedDateTime.set(Calendar.MINUTE, minute)

//                    오후 12:10 형태로 가공
                    val sdf = SimpleDateFormat("a h:mm")
                    binding.timeTxt.text = sdf.format(mSelectedDateTime.time)

                }
            }

            TimePickerDialog(
                mContext,
                tsl,
                mSelectedDateTime.get(Calendar.HOUR_OF_DAY),
                mSelectedDateTime.get(Calendar.MINUTE),
                false
            ).show()
        }



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

//            3. 날짜/시간이 선택이 되었는가?
//             =>날짜 / 기간 중 선택 안한게 있다면, 선택하라고 토스트 함수를 강제 종료하자.
            if (binding.dateTxt.text == "일자 선택") {
                Toast.makeText(mContext, "약속 일자를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.timeTxt.text == "시간 선택") {
                Toast.makeText(mContext, "약속 시간을 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            지금시간과 선택된(mSelectedDateTime)과의 시간차를 계산
            if (mSelectedDateTime.timeInMillis < Calendar.getInstance().timeInMillis) {
                Toast.makeText(mContext, "현재 시간 이후의 시간으로 선택해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


        }
    }

    override fun setValues() {
        titleTxt.text = "새 약속 만들기"

        mStartPlaceSpinnerAdapter = StartPlaceSpinnerAdapter(mContext, R.layout.list_item_place, mStartPlaceList)
        binding.startPlaceSpinner.adapter = mStartPlaceSpinnerAdapter

        mFriendsSpinnerAdapter = MyFriendSpinnerAdapter(mContext, R.layout.list_item_user, mFriendsList)
        binding.invitedFriendSpinner.adapter = mFriendsSpinnerAdapter

        getMyPlaceListFromServer()
        getMyFriendsListFromServer()

        binding.mapView.getMapAsync {
            val naverMap = it
        }
    }

    fun getMyPlaceListFromServer () {
        apiList.getRequestMyPlace().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {

                    mStartPlaceList.clear()
                    mStartPlaceList.addAll(response.body()!!.data.places)
                    mStartPlaceSpinnerAdapter.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

    fun getMyFriendsListFromServer () {
        apiList.getRequestMyFriendsList("my").enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {
                    mFriendsList.clear()
                    mFriendsList.addAll(response.body()!!.data.friends)

                    mFriendsSpinnerAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }
}