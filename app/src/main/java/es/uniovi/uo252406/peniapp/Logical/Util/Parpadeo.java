package es.uniovi.uo252406.peniapp.Logical.Util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import es.uniovi.uo252406.simplefer.R;

public class Parpadeo {

    Context context;
    private ImageView img = null;
    private Animation fadeIn = null;
    private Animation fadeOut = null;

    int limit=2;
    int  animationCont=0;
    // Listeners to detect the end of an animation
    private LocalFadeInAnimationListener myFadeInAnimationListener = new LocalFadeInAnimationListener();
    private LocalFadeOutAnimationListener myFadeOutAnimationListener = new LocalFadeOutAnimationListener();


    public Parpadeo(Context context, ImageView img){
        this.context = context;
        this.img = (ImageView)img;
        runAnimations();

    }

    /**
     * Performs the actual fade-out
     */
    private void launchOutAnimation() {
        img.startAnimation(fadeOut);
    }

    /**
     * Performs the actual fade-in
     */
    private void launchInAnimation() {
        img.startAnimation(fadeIn);
    }


    public void forceFinish(){
        img.setAnimation(null);
        img.setVisibility(View.INVISIBLE);
    }

    /**
     * Starts the animation
     */
    private void runAnimations() {
        //uso de las animaciones
        fadeIn = AnimationUtils.loadAnimation(this.context, R.anim.fadein);
        fadeIn.setAnimationListener( myFadeInAnimationListener );
        fadeOut = AnimationUtils.loadAnimation(this.context, R.anim.fadeout);
        fadeOut.setAnimationListener( myFadeOutAnimationListener );
        // And start
        launchInAnimation();
    }

    // Runnables to start the actual animation
    private Runnable mLaunchFadeOutAnimation = new Runnable() {
        public void run() {
            launchOutAnimation();
        }
    };

    private Runnable mLaunchFadeInAnimation = new Runnable() {
        public void run() {
            launchInAnimation();
        }
    };


    /**
     * Animation listener for fade-out
     *
     * @author moi
     *
     */
    private class LocalFadeInAnimationListener implements Animation.AnimationListener {
        public void onAnimationEnd(Animation animation) {
            if(animationCont< limit) {
                img.post(mLaunchFadeOutAnimation);
                animationCont++;
            }
            else{
                img.post(mLaunchFadeOutAnimation);
                img.setVisibility(View.INVISIBLE);
            }

        }
        public void onAnimationRepeat(Animation animation){
        }
        public void onAnimationStart(Animation animation) {
        }
    };

    /**
     * Listener de animación para el Fadein
     */
    private class LocalFadeOutAnimationListener implements Animation.AnimationListener {
        public void onAnimationEnd(Animation animation) {
            if(animationCont< limit) {
                img.post(mLaunchFadeInAnimation);
                animationCont++;
            }

        }
        public void onAnimationRepeat(Animation animation) {
        }
        public void onAnimationStart(Animation animation) {
        }
    };


}
