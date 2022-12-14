package com.example.user.plalarm.service;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class TtsService{

    private TextToSpeech tts;
    private final Context context;

    public TtsService(Context context) {
        this.context = context;
    }

    public void speak(String text) {
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                int result = tts.setLanguage(Locale.KOREA); // 언어 선택
                if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                    Log.e("TTS", "This Language is not supported");
                }else{
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });
    }
}
