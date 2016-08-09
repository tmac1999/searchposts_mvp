package com.mrz.searchenginefortieba.view.catloadingview;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.mrz.searchenginefortieba.R;
import com.mrz.searchenginefortieba.component.main.MainActivity;
import com.mrz.searchenginefortieba.utils.ToastUtils;

/**
 * Created by Administrator on 2016/3/30.
 */
public class CatLoadingView extends DialogFragment {

    public CatLoadingView() {
    }


    Animation operatingAnim, eye_left_Anim, eye_right_Anim;

    Dialog mDialog;

    View mouse, eye_left, eye_right;

    EyelidView eyelid_left, eyelid_right;

    GraduallyTextView mGraduallyTextView;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (mDialog == null) {
            mDialog = new Dialog(getActivity(), R.style.cart_dialog);
            mDialog.setContentView(R.layout.catloading_main);
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.getWindow().setGravity(Gravity.CENTER);

            operatingAnim = new RotateAnimation(360f, 0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            operatingAnim.setRepeatCount(Animation.INFINITE);
            operatingAnim.setDuration(2000);

            eye_left_Anim = new RotateAnimation(360f, 0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            eye_left_Anim.setRepeatCount(Animation.INFINITE);
            eye_left_Anim.setDuration(2000);

            eye_right_Anim = new RotateAnimation(360f, 0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            eye_right_Anim.setRepeatCount(Animation.INFINITE);
            eye_right_Anim.setDuration(2000);

            LinearInterpolator lin = new LinearInterpolator();
            operatingAnim.setInterpolator(lin);
            eye_left_Anim.setInterpolator(lin);
            eye_right_Anim.setInterpolator(lin);

            View view = mDialog.getWindow().getDecorView();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.longToast("请勿做任何动作");
                    Log.d("TAG","请勿做任何动作");
                }
            });
            View viewById = view.findViewById(R.id.catloading);
            viewById.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 MainActivity activity = (MainActivity) getDialog().getOwnerActivity();
                    activity.showCancelDialog();
                }
            });
            mouse = view.findViewById(R.id.mouse);

            eye_left = view.findViewById(R.id.eye_left);

            eye_right = view.findViewById(R.id.eye_right);

            eyelid_left = (EyelidView) view.findViewById(R.id.eyelid_left);

            eyelid_left.setColor(Color.parseColor("#d0ced1"));

            eyelid_left.setFromFull(true);

            eyelid_right = (EyelidView) view.findViewById(R.id.eyelid_right);

            eyelid_right.setColor(Color.parseColor("#d0ced1"));

            eyelid_right.setFromFull(true);

            mGraduallyTextView = (GraduallyTextView) view.findViewById(
                    R.id.graduallyTextView);

            operatingAnim.setAnimationListener(
                    new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }


                        @Override
                        public void onAnimationEnd(Animation animation) {
                        }


                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            eyelid_left.resetAnimator();
                            eyelid_right.resetAnimator();
                        }
                    });
        }
        return mDialog;
    }


    @Override
    public void onResume() {
        super.onResume();
        mouse.setAnimation(operatingAnim);
        eye_left.setAnimation(eye_left_Anim);
        eye_right.setAnimation(eye_right_Anim);
        eyelid_left.startLoading();
        eyelid_right.startLoading();
        mGraduallyTextView.startLoading();
    }


    @Override
    public void onPause() {
        super.onPause();

        operatingAnim.reset();
        eye_left_Anim.reset();
        eye_right_Anim.reset();

        mouse.clearAnimation();
        eye_left.clearAnimation();
        eye_right.clearAnimation();

        eyelid_left.stopLoading();
        eyelid_right.stopLoading();
        mGraduallyTextView.stopLoading();
    }

    @Override
    public void onAttach(Activity activity) {
        activity.getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ToastUtils.longToast("请勿做任何动作mainactivity");
                Log.d("TAG","请勿做任何动作mainactivity");
                return true;
            }
        });
        getActivity().getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ToastUtils.longToast("请勿做任何动作11111111mainactivity");
                Log.d("TAG","请勿做任何动作1111111111mainactivity");
                return true;
            }
        });

        super.onAttach(activity);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ToastUtils.longToast("dialoggggggggg");
                Log.d("TAG","dialoggggggggg");
                return true;
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().getDecorView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.longToast("请勿做任何动作");
                Log.d("TAG","请勿做任何动作");
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mDialog = null;
        System.gc();
    }
}
