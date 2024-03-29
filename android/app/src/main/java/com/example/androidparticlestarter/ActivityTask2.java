package com.example.androidparticlestarter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.particle.android.sdk.cloud.ParticleCloud;
import io.particle.android.sdk.cloud.ParticleCloudSDK;
import io.particle.android.sdk.cloud.ParticleDevice;
import io.particle.android.sdk.cloud.ParticleEvent;
import io.particle.android.sdk.cloud.ParticleEventHandler;
import io.particle.android.sdk.cloud.exceptions.ParticleCloudException;
import io.particle.android.sdk.utils.Async;

public class ActivityTask2 extends AppCompatActivity {
    private final String TAG="Pardeep";

    Random random_genrator = new Random();
    String particleId;
    String answer = "";
    private final String PARTICLE_USERNAME = "pardeepvirk18@gmail.com";
    private final String PARTICLE_PASSWORD = "Lager@5678";
    private final String DEVICE_ID = "1c002d001247363333343437";
    private long subscriptionId;
    private ParticleDevice mDevice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);
        ParticleCloudSDK.init(this.getApplicationContext());
        setImage();
        getDeviceFromCloud();
        getEvents();
    }

    public void getEvents()
    {
        getDeviceFromCloud();
        Async.executeAsync(ParticleCloudSDK.getCloud(), new Async.ApiWork<ParticleCloud, Object>() {
            @Override
            public Object callApi(@NonNull ParticleCloud particleCloud) throws ParticleCloudException, IOException {
                try {
                    subscriptionId = ParticleCloudSDK.getCloud().subscribeToAllEvents("answer2", new ParticleEventHandler() {
                        @Override
                        public void onEventError(Exception e) {
                        }
                        @Override
                        public void onEvent(String eventName, ParticleEvent particleEvent) {
                            particleId = particleEvent.deviceId;
                            answer = particleEvent.dataPayload;
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return -1;
            }

            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onFailure(ParticleCloudException exception) {
            }
        });
    }
    public void start(View v)
    {
        setImage();
        getDeviceFromCloud();
    }

    public void setImage() {

        int number = random_genrator.nextInt(2);
        Async.executeAsync(ParticleCloudSDK.getCloud(), new Async.ApiWork<ParticleCloud, Object>() {
            @Override
            public Object callApi(@NonNull ParticleCloud particleCloud) throws ParticleCloudException, IOException {
                List<String> functionParameters = new ArrayList<String>();
                functionParameters.add(String.valueOf(number));
                try {
                    mDevice.callFunction("myAnswer2", functionParameters);
                } catch (ParticleDevice.FunctionDoesNotExistException e) {
                    e.printStackTrace();
                }
                return -1;
            }
            @Override
            public void onSuccess(Object o) {

            }
            @Override
            public void onFailure(ParticleCloudException exception) {
                Log.d(TAG, exception.getBestMessage());
            }
        });

    }

    public void getDeviceFromCloud() {
        Async.executeAsync(ParticleCloudSDK.getCloud(), new Async.ApiWork<ParticleCloud, Object>() {
            @Override
            public Object callApi(@NonNull ParticleCloud particleCloud) throws ParticleCloudException, IOException {
                particleCloud.logIn(PARTICLE_USERNAME, PARTICLE_PASSWORD);
                mDevice = particleCloud.getDevice(DEVICE_ID);
                try {
                }
                catch(Exception e)
                {
                }

                return -1;
            }
            @Override
            public void onSuccess(Object o) {
            }

            @Override public void onFailure(ParticleCloudException exception) {
            }
        });
    }


}
