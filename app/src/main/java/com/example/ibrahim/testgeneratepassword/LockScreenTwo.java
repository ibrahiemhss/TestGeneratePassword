package com.example.ibrahim.testgeneratepassword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.patternlockview.utils.ResourceUtils;
import com.andrognito.rxpatternlockview.RxPatternLockView;
import com.andrognito.rxpatternlockview.events.PatternLockCompleteEvent;
import com.andrognito.rxpatternlockview.events.PatternLockCompoundEvent;
import com.example.ibrahim.testgeneratepassword.data.SharedPrefManager;

import java.util.List;

import io.reactivex.functions.Consumer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LockScreenTwo extends AppCompatActivity {

    private PatternLockView mPatternLockView;
    TextView textView;
    boolean isLoocked;
    String LockVlue2;
    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            Log.d(getClass().getName(), "Pattern drawing started");
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            Log.d(getClass().getName(), "Pattern progress: " +
                    PatternLockUtils.patternToString(mPatternLockView, progressPattern));
        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            Log.d(getClass().getName(), "Pattern complete: " +
                    PatternLockUtils.patternToString(mPatternLockView, pattern));
        }

        @Override
        public void onCleared() {
            Log.d(getClass().getName(), "Pattern has been cleared");
        }
    };

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lock_screen_two);
        Log.d (getClass ().getName (), "Compare Oncreate\n LockVlue2:" + LockVlue2+
                "\nLockVlue1: "+SharedPrefManager.getInstance (this).getFirstLeck ());
        textView = findViewById (R.id.welcomScreen2);
        if(!SharedPrefManager.getInstance (this).getSeconLock ().equals ("")){
            textView.setText ("type your password");

        }
        else if(SharedPrefManager.getInstance (this).getFirstLeck ().contains (SharedPrefManager.getInstance (this).getSeconLock ())){
            Intent intent=new Intent(LockScreenTwo.this, MainActivity.class);
            startActivity(intent);
    }
        mPatternLockView = (PatternLockView) findViewById(R.id.patter_lock_view2);
        mPatternLockView.setDotCount(3);
        mPatternLockView.setDotNormalSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_size));
        mPatternLockView.setDotSelectedSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_selected_size));
        mPatternLockView.setPathWidth((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_path_width));
        mPatternLockView.setAspectRatioEnabled(true);
        mPatternLockView.setAspectRatio(PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS);
        mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
        mPatternLockView.setDotAnimationDuration(150);
        mPatternLockView.setPathEndAnimationDuration(100);
        mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(this, R.color.white));
        mPatternLockView.setInStealthMode(false);
        mPatternLockView.setTactileFeedbackEnabled(true);
        mPatternLockView.setInputEnabled(true);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);

        RxPatternLockView.patternComplete(mPatternLockView)
                .subscribe(new Consumer<PatternLockCompleteEvent> () {
                    @Override
                    public void accept(PatternLockCompleteEvent patternLockCompleteEvent) throws Exception {
                        Log.d(getClass().getName(), "Complete: " + patternLockCompleteEvent.getPattern().toString());
                    }
                });

        RxPatternLockView.patternChanges(mPatternLockView)
                .subscribe(new Consumer<PatternLockCompoundEvent>() {
                    @Override
                    public void accept(PatternLockCompoundEvent event) throws Exception {
                        if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_STARTED) {
                            Log.d(getClass().getName(), "Pattern drawing started");
                        } else if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_PROGRESS) {
                            Log.d(getClass().getName(), "Pattern progress: " +
                                    PatternLockUtils.patternToString(mPatternLockView, event.getPattern()));
                        } else if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_COMPLETE) {

                            textView = findViewById (R.id.welcomScreen2);


                            if (LockVlue2.equals ("")) {

                                LockVlue2 = PatternLockUtils.patternToString (mPatternLockView, event.getPattern ());
                                Log.d (getClass ().getName (), "Pattern complete LockVlue2: " + LockVlue2);
                                SharedPrefManager.getInstance (LockScreenTwo.this).saveSeconLock (LockVlue2);
                                Log.d (getClass ().getName (), "Compare On No value\n LockVlue1:" + LockVlue2+"\nLockVlue2: "+SharedPrefManager.getInstance (LockScreenTwo.this).getFirstLeck ());

                                textView.setText (LockVlue2);

                            }

                        else    if(SharedPrefManager.getInstance (LockScreenTwo.this).getFirstLeck ()
                                    .equals (SharedPrefManager.getInstance (LockScreenTwo.this).getSeconLock ())){
                                Intent intent=new Intent(LockScreenTwo.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                             textView.setText ("error retype");

                            }

                        } else if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_CLEARED) {
                            Log.d(getClass().getName(), "Pattern has been cleared");
                        }
                    }
                });
    }

    @Override
    protected void onPause () {
        super.onPause ();
        LockVlue2="";
        Log.d (getClass ().getName (), "LockVlue2: onPause " + LockVlue2);

    }

    @Override
    protected void onResume () {
        super.onResume ();
        LockVlue2="";
        Log.d (getClass ().getName (), "LockVlue2: onResume " + LockVlue2);

    }
}
