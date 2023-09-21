package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

/// Reference's
//
//

class MainActivity : AppCompatActivity() ,Animation.AnimationListener{

    private var count = 0
    private var flag = false

    private var powerButton: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var button = findViewById<Button>(R.id.spin)
        powerButton = findViewById(R.id.imageView)
        button!!.setOnTouchListener(PowerTouchListener())
        intSpinner()


    }


    val prizes = arrayOf<String>("Jarrud","Shai","Gumedi","Sheldon","Adarsh","Jayden","Sydney","Sibu","Ziyaad","Sabein")
    private var mSpinDuration :Long = 0
    private var mSpinRevolution = 0f
    var pointerImageView:ImageView? = null
    var infoText: TextView? = null


    private fun intSpinner() {
        pointerImageView = findViewById(R.id.imageView)

    }

    fun startSpinner() {
        mSpinRevolution = 3600f
        mSpinDuration = 5000

        if (count >= 30){
            mSpinDuration = 1000
            mSpinRevolution = (3600 * 2).toFloat()
        }
        if (count >= 60){
            mSpinDuration = 15000
            mSpinRevolution = (3600 * 3).toFloat()

        }



        val end = Math.floor(Math.random() * 3600).toInt()
        val numOfPrizes = prizes.size
        val degreesPerPrize = 360/numOfPrizes
        val shift = 0
        val prizeIndex = (shift + end) % numOfPrizes



        val rotateAnim = RotateAnimation(
            0f,mSpinRevolution + end,
            Animation.RELATIVE_TO_SELF,
            0.5f,Animation.RELATIVE_TO_SELF,0.5f
        )
        rotateAnim.interpolator = DecelerateInterpolator()
        rotateAnim.repeatCount = 0
        rotateAnim.duration = mSpinDuration
        rotateAnim.setAnimationListener(this)
        rotateAnim.fillAfter = true
        pointerImageView!!.startAnimation(rotateAnim)

    }

    override fun onAnimationStart(p0: Animation?) {

    }

    override fun onAnimationEnd(p0: Animation?) {

    }

    override fun onAnimationRepeat(p0: Animation?) {}

    private inner class PowerTouchListener: View.OnTouchListener {
        override fun onTouch(p0: View?, motionEvent: MotionEvent?): Boolean {

            when(motionEvent!!.action){
                MotionEvent.ACTION_DOWN ->{
                    flag = true
                    count = 0
                    Thread{
                        while (flag){
                            count++
                            if (count == 100){
                                try {
                                    Thread.sleep(100)
                                }catch (e: InterruptedException) {
                                    e.printStackTrace()
                                }
                                count = 0
                            }
                            try {
                                Thread.sleep(10)
                            }
                            catch (e: InterruptedException){
                                e.printStackTrace()
                            }
                        }
                    }.start()
                    return true
                }
                MotionEvent.ACTION_UP ->{
                    flag = false
                    startSpinner()
                    return false
                }

            }


            return false
        }

    }
}