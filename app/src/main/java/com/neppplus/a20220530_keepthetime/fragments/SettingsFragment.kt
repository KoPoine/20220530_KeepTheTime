package com.neppplus.a20220530_keepthetime.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.neppplus.a20220530_keepthetime.R
import com.neppplus.a20220530_keepthetime.databinding.FragmentSettingsBinding
import com.neppplus.a20220530_keepthetime.dialogs.CustomAlertDialog
import com.neppplus.a20220530_keepthetime.models.BasicResponse
import com.neppplus.a20220530_keepthetime.ui.main.LoginActivity
import com.neppplus.a20220530_keepthetime.utils.ContextUtil
import com.neppplus.a20220530_keepthetime.utils.GlobalData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsFragment : BaseFragment() {

    lateinit var binding : FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
//        프로필 이미지 변경 이벤트
        binding.profileImg.setOnClickListener {
//            갤러리를 개발자가 이용 : 유저 허락을 받아야한다. => 권한 세팅
//            TedPermission 라이브러리
            val pl = object : PermissionListener {
                override fun onPermissionGranted() {
//                  권한 Ok
                    val myIntent = Intent()

//            갤러리로 사진을 가지러 이동(추가작업) => Intent (4)
                    myIntent.action = Intent.ACTION_PICK
                    myIntent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE

                    startForResult.launch(myIntent)
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
//                    권한이 Denied

                }

            }

//            권한이 OK 일때
            TedPermission.create()
                .setPermissionListener(pl)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check()

        }

//        닉네임 변경 이벤트
        binding.changeNickLayout.setOnClickListener {
            val alert = CustomAlertDialog(mContext, requireActivity())
            alert.myDialog()

            alert.binding.titleTxt.text = "닉네임 변경"
            alert.binding.bodyTxt.visibility = View.GONE
            alert.binding.contentEdt.hint = "변경할 닉네임을 입력해주세요."
            alert.binding.contentEdt.inputType = InputType.TYPE_CLASS_TEXT

            alert.binding.positiveBtn.setOnClickListener {
                apiList.patchRequestEditUserInfo(
                    "nickname",
                    alert.binding.contentEdt.text.toString()
                ).enqueue(object : Callback<BasicResponse>{
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            val br = response.body()!!

                            GlobalData.loginUser = br.data.user

                            setUserData()

                            alert.dialog.dismiss()
                        }
//                        뭔가 중복된 닉네임과 같은 문제가 발생
                        else {
                            val errorBodyStr = response.errorBody()!!.string()
                            val jsonObj = JSONObject(errorBodyStr)
                            val message = jsonObj.getString("message")

                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })
            }
            alert.binding.negativeBtn.setOnClickListener {
                alert.dialog.dismiss()
            }

        }

//        외출 준비 시간 변경 이벤트
        binding.readyTimeLayout.setOnClickListener {
            val alert = CustomAlertDialog(mContext, requireActivity())
            alert.myDialog()

            alert.binding.titleTxt.text = "준비 시간 설정"
            alert.binding.bodyTxt.visibility = View.GONE
            alert.binding.contentEdt.hint = "외출 준비에 몇 분 걸리는지"
            alert.binding.contentEdt.inputType = InputType.TYPE_CLASS_NUMBER

            alert.binding.positiveBtn.setOnClickListener {
                apiList.patchRequestEditUserInfo(
                    "ready_minute",
                    alert.binding.contentEdt.text.toString()
                ).enqueue(object : Callback<BasicResponse>{
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            val br = response.body()!!

                            GlobalData.loginUser = br.data.user

                            setUserData()

                            alert.dialog.dismiss()
                        }
//                        뭔가 중복된 닉네임과 같은 문제가 발생
                        else {
                            val errorBodyStr = response.errorBody()!!.string()
                            val jsonObj = JSONObject(errorBodyStr)
                            val message = jsonObj.getString("message")

                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })
            }
            alert.binding.negativeBtn.setOnClickListener {
                alert.dialog.dismiss()
            }
        }

//        비밀번호 변경 이벤트
        binding.changePwLayout.setOnClickListener {

        }

//        출발 장소 변경 이벤트
        binding.myPlaceLayout.setOnClickListener {

        }

//        친구 목록 관리 이벤트
        binding.myFriendsLayout.setOnClickListener {

        }

//        로그아웃
        binding.logoutLayout.setOnClickListener {
            val alert = CustomAlertDialog(mContext, requireActivity())
            alert.myDialog()

            alert.binding.titleTxt.text = "로그아웃"
            alert.binding.bodyTxt.text = "정말 로그아웃 하시겠습니까?"
            alert.binding.contentEdt.visibility = View.GONE
            alert.binding.positiveBtn.setOnClickListener {
//                로그인 토큰 (from ContextUtil)만 제거하고 싶을때 (기본값으로 set하자)
//                ContextUtil.setLoginToken(mContext, "")

                ContextUtil.clear(mContext)

                GlobalData.loginUser = null

                val myIntent = Intent(mContext, LoginActivity::class.java)
                myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(myIntent)

                alert.dialog.dismiss()
            }
            alert.binding.negativeBtn.setOnClickListener {
                alert.dialog.dismiss()
            }
        }
    }

    override fun setValues() {
        setUserData()

        when (GlobalData.loginUser!!.provider) {
            "kakao" -> {}
            "facebook" -> {}
            else -> binding.socialLoginImg.visibility = View.GONE
        }
    }

    fun setUserData () {
        Glide.with(mContext)
            .load(GlobalData.loginUser!!.profileImg)
            .into(binding.profileImg)
        binding.nicknameTxt.text = GlobalData.loginUser!!.nickname

//        [연습문제] if 준비시간이 1시간이 넘을경우 -> x시간 x분으로 나타내보자.
        binding.readyTimeTxt.text = "${GlobalData.loginUser!!.readyMinute}분"
    }

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
//            어떤 사진을 골랏는지? 파악해보자
//            임시 : 고른 사진을 profileImg에 바로 적용만 (서버전송 X)

//            data? => 이전 화면이 넘겨준 intent
//            data?.data => 선택한 사진이 들어있는 경로 정보 (Uri)
            val dataUri = it.data?.data

//            Uri -> 이미지뷰의 사진 (GLide)
            Glide.with(mContext).load(dataUri).into(binding.profileImg)
        }
    }
}