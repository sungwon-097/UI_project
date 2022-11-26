package com.example.user.plalarm.service;

import static android.net.wifi.p2p.WifiP2pManager.ERROR;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.user.plalarm.R;

import java.util.Locale;

public class TtsService{

    private TextToSpeech tts;
    private Button speak_out;
    private EditText input_text;
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
