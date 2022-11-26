package com.example.user.plalarm;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.user.plalarm.config.FirebaseConfig;
import com.example.user.plalarm.model.Event;

public class IntentActivity extends AppCompatActivity {

    Activity act = this;
    ListView listView;
    Button cancelButton;
    private List<ResolveInfo> apps;
    private PackageManager pm;

    @SuppressLint("QueryPermissionsNeeded")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_intent);

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        pm = getPackageManager();
        apps = pm.queryIntentActivities(intent, 0);

        setContentView(R.layout.activity_intent);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new gridAdapter());

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Event event = (Event) intent.getSerializableExtra("user");

                FirebaseConfig.putEventData("user", event);
                Toast.makeText(act, "일정을 등록하였습니다", Toast.LENGTH_SHORT).show();

                Intent mainIntent = new Intent(IntentActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });
    }

    public class gridAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public gridAdapter(){
            inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return apps.size();
        }

        @Override
        public Object getItem(int position) {
            return apps.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = inflater.inflate(R.layout.app_icons, parent, false);
            }
            final ResolveInfo info = apps.get(position);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.iconView);
            imageView.setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));

            TextView textView = (TextView) convertView.findViewById(R.id.iconName);
            textView.setText(info.activityInfo.loadLabel(pm).toString());

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pkgName = info.activityInfo.packageName;
                    Toast.makeText(act, pkgName, Toast.LENGTH_SHORT).show();

                    Intent intent = getIntent();
                    Event event = (Event) intent.getSerializableExtra("user");
                    event.setIntentApp(pkgName);

                    FirebaseConfig.putEventData("user", event);
                    Toast.makeText(act, "일정을 등록하였습니다", Toast.LENGTH_SHORT).show();

                    Intent mainIntent = new Intent(IntentActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }
            });
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String msg = "Icon을 눌러주세요";
                    Toast.makeText(act, msg, Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }
    }
}