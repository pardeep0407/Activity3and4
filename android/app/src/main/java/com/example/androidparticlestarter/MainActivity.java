package com.example.androidparticlestarter;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import io.particle.android.sdk.cloud.ParticleCloud;
import io.particle.android.sdk.cloud.ParticleCloudSDK;
import io.particle.android.sdk.cloud.ParticleDevice;
import io.particle.android.sdk.cloud.exceptions.ParticleCloudException;
import io.particle.android.sdk.utils.Async;

public class MainActivity extends AppCompatActivity {
    private final String TAG="PARDEEP";
    private final int REQ_CODE = 100;
    ImageButton micro_Phone;
    private final String PARTICLE_USERNAME = "akshdeepkaur235@gmail.com";
    private final String PARTICLE_PASSWORD = "9463931734";
    private final String DEVICE_ID = "1f0036000e47363333343437";
    private ParticleDevice mDevice;
    String command1 = "Particle turn on all lights";
    String command2 ="Particle create a rainbow";
    String voice_Result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        micro_Phone = findViewById(R.id.micro_phone);
        ParticleCloudSDK.init(this.getApplicationContext());
        getDeviceFromCloud();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to give command to particle");
        try {
            startActivityForResult(intent, REQ_CODE);
        } catch (ActivityNotFoundException a) {
        }
    }

public void speak(View v)
    {
        getDeviceFromCloud();

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to give command to particle");
        try {
            startActivityForResult(intent, REQ_CODE);
        } catch (ActivityNotFoundException a) {
           }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    voice_Result = result.get(0).toString();
                    Async.executeAsync(ParticleCloudSDK.getCloud(), new Async.ApiWork<ParticleCloud, Object>() {
                        @Override
                        public Object callApi(@NonNull ParticleCloud particleCloud) throws ParticleCloudException, IOException {

                            List<String> param = new ArrayList<String>();

                            if (voice_Result.equalsIgnoreCase(command1))
                            {
                                   param.add("command1");
                            }

                            else if (voice_Result.equalsIgnoreCase(command2))
                            {
                                param.add("command2");
                            }

                            try {

                                mDevice.callFunction("speak",param);
                            }
                            catch(Exception e)
                            {
                            }
                            return -1;
                        }
                        @Override
                        public void onSuccess(Object o) {
                            Log.d(TAG, "Successfully got device from Cloud");
                        }

                        @Override
                        public void onFailure(ParticleCloudException exception) {
                            Log.d(TAG, exception.getBestMessage());
                        }
                    });
                }
            }
        }
    }

    public void getDeviceFromCloud() {
        Async.executeAsync(ParticleCloudSDK.getCloud(), new Async.ApiWork<ParticleCloud, Object>() {

            @Override
            public Object callApi(@NonNull ParticleCloud particleCloud) throws ParticleCloudException, IOException {
                particleCloud.logIn(PARTICLE_USERNAME, PARTICLE_PASSWORD);
                mDevice = particleCloud.getDevice(DEVICE_ID);
                return -1;
            }
            @Override
            public void onSuccess(Object o) {
                Log.d(TAG, "Successfully got device from Cloud");
            }

            @Override
            public void onFailure(ParticleCloudException exception) {
                Log.d(TAG, exception.getBestMessage());
            }
        });
    }

}
