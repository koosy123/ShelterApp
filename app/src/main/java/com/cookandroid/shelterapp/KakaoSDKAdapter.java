package com.cookandroid.shelterapp;


import android.app.Activity;
import android.content.Context;
import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;

import com.cookandroid.shelterapp.GlobalApplication;

public class KakaoSDKAdapter extends KakaoAdapter {

    @Override
    public ISessionConfig getSessionConfig() {
        return new ISessionConfig() {
            @Override
            public AuthType[] getAuthTypes() {
                return new AuthType[] {AuthType.KAKAO_ACCOUNT};
            }
            // 로그인시 인증받을 타입을 지정한다. 지정하지 않을 시 가능한 모든 옵션이 지정된다.

            //1.KAKAO_TALK :  kakaotalk으로 login을 하고 싶을 때 지정.
            //2.KAKAO_STORY : kakaostory으로 login을 하고 싶을 때 지정.
            //3.KAKAO_ACCOUNT : 웹뷰 Dialog를 통해 카카오 계정연결을 제공하고 싶을 경우 지정.
            //4.KAKAO_TALK_EXCLUDE_NATIVE_LOGIN : 카카오톡으로만 로그인을 유도하고 싶으면서 계정이 없 을때 계정생성을 위한
            //버튼도 같이 제공을 하고 싶다면 지정. KAKAO_TALK과 중복 지정불가.
            //5.KAKAO_LOGIN_ALL : 모든 로그인방식을 사용하고 싶을 때 지정.

            @Override
            public boolean isUsingWebviewTimer() {
                return false;
            }
            // SDK 로그인시 사용되는 WebView에서 pause와 resume시에 Timer를 설정하여 CPU소모를 절약한다.
            // true 를 리턴할경우 webview로그인을 사용하는 화면서 모든 webview에 onPause와 onResume 시에
            // Timer를 설정해 주어야 한다. 지정하지 않을 시 false로 설정된다.

            @Override
            public ApprovalType getApprovalType() {
                return ApprovalType.INDIVIDUAL;
            }

            @Override
            public boolean isSaveFormData() {
                return true;
            }
        };
    }

    @Override
    public IApplicationConfig getApplicationConfig() {
        return new IApplicationConfig() {
            @Override
            public Activity getTopActivity() {
                return GlobalApplication.getCurrentActivity();
            }

            @Override
            public Context getApplicationContext() {
                return GlobalApplication.getGlobalApplicationContext();
            }
        };
    }

}