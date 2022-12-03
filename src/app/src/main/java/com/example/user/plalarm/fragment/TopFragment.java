package com.example.user.plalarm.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.user.plalarm.R;
import com.example.user.plalarm.activity.SettingActivity;

import javax.annotation.Nullable;


public class TopFragment extends Fragment implements View.OnClickListener{

    Button soundButton;
    ImageButton settingButton;

    ImageView notificationOnIcon;
    ImageView notificationOffIcon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);

        soundButton = (Button) view.findViewById(R.id.sound_button);
        settingButton = (ImageButton) view.findViewById(R.id.setting_button);

        //soundButton 작동 시 표시 Image 전환 위한 View 선언
        notificationOnIcon = (ImageView) view.findViewById(R.id.notification_on_icon);
        notificationOffIcon = (ImageView) view.findViewById(R.id.notification_mute_icon);


        // SharedPreferences 로 mute 상태 불러오기
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean NOTIFICATION_MUTE = sharedPref.getBoolean("NOTIFICATION_MUTE", false);
        if (NOTIFICATION_MUTE) {
            notificationOnIcon.setVisibility(View.INVISIBLE);
            notificationOffIcon.setVisibility(View.VISIBLE);
        }else {
            notificationOnIcon.setVisibility(View.VISIBLE);
            notificationOffIcon.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        soundButton.setOnClickListener((View.OnClickListener) this);
        settingButton.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View view) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (view == soundButton) {
            if(notificationOnIcon.getVisibility() == View.VISIBLE) {
                notificationOnIcon.setVisibility(View.INVISIBLE);
                notificationOffIcon.setVisibility(View.VISIBLE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("NOTIFICATION_MUTE", true);
                editor.apply();
            }
            else {
                notificationOnIcon.setVisibility(View.VISIBLE);
                notificationOffIcon.setVisibility(View.INVISIBLE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("NOTIFICATION_MUTE", false);
                editor.apply();
            }
        }
        //SettingButton 을 클릭할 경우, SettingActivity 로 넘어감
        else if (view == settingButton) {
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            startActivity(intent);
        }
    }
}