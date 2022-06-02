package com.neppplus.a20220530_keepthetime

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class KeepTheTime : Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "2c61faca06c1049e41aba06435ea8f50")
    }

}