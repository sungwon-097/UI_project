package com.example.user.plalarm.service;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.user.plalarm.R;

import java.util.Locale;

public class TtsService extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private Button speak_out;
    private TextView intentApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // TODO: 후에 tts 를 사용할 view 로 수정해야함

        tts = new TextToSpeech(this, this);
        speak_out = new Button(this);
        intentApp = new TextView(this);

        speak_out.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                speakOut();
            }
        });
    }

    private void speakOut(){
        CharSequence text = intentApp.getText();
        tts.setPitch((float)0.6); // 음성 톤 높이 지정
        tts.setSpeechRate((float)0.1); // 음성 속도 지정

        // 첫 번째 매개변수: 음성 출력을 할 텍스트
        // 두 번째 매개변수: 1. TextToSpeech.QUEUE_FLUSH - 진행중인 음성 출력을 끊고 이번 TTS의 음성 출력
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "id1");
    }

    @Override
    public void onDestroy() {
        if(tts!=null){ // 사용한 TTS객체 제거
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
    @Override
    public void onInit(int status) { // OnInitListener를 통해서 TTS 초기화
        if(status == TextToSpeech.SUCCESS){
            int result = tts.setLanguage(Locale.KOREA); // TTS언어 한국어로 설정

            if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                Log.e("TTS", "This Language is not supported");
            }else{
                speak_out.setEnabled(true);
                speakOut();// onInit에 음성출력할 텍스트를 넣어줌
            }
        }else{
            Log.e("TTS", "Initialization Failed!");
        }
    }

}