package com.cchat.ldbuttonsandloadinganimation.LoadingAnimation

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.os.Build
import android.os.Handler
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.MotionEvent
import android.view.PixelCopy
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread
import androidx.core.content.ContextCompat


class SimpleLoadingAnimationWithBlur(context: Context) : View(context) {

    private val buttonDefaultPaint: Paint = Paint()
    private val iconPaint: Paint = Paint()
    private val loadingPathBackground: Paint = Paint()
    private val loadingPathBackgroundInner: Paint = Paint()
    private val buttonTouchPaint: Paint = Paint()

    private var textPaint: Paint = Paint()

    private var finish: Boolean = false
    private val topSemiRect: RectF = RectF()

    var backgroundBitmap: Bitmap? = null
    var textBitmap: Bitmap? = null

    private var isTouchBottom = false
    private var initialRadius = 0f
    val waveGap: Float = 360f
    private var loadingDotCounter: Int = 1
    private var loadingText = "Loading"
    var viewGroup: ViewGroup? = null
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        setPaintProperties()


        topSemiRect.set(
            (width / 3).toFloat(),
            (height / 3).toFloat(),
            ((width / 3).toFloat() * 2),
            (height / 3).toFloat() + ((width / 3).toFloat() * 2) - ((width / 3).toFloat())
        )
        backgroundBitmap?.apply {
            canvas.drawBitmap(this, 0f, 0f, iconPaint)
        }
        var currentRadius = initialRadius + waveRadiusOffset
        canvas.drawCircle(
            ((width / 3) + ((width / 3).toFloat() * 2)) / 2,
            ((height / 3).toFloat() + (height / 3).toFloat() + ((width / 3).toFloat() * 2) - ((width / 3).toFloat())) / 2,
            ((width / 3) + ((width / 3).toFloat() * 2)) / 6,
            loadingPathBackground
        )


        while (currentRadius < 360) {

            canvas.drawArc(topSemiRect, currentRadius, 10f, false, iconPaint)
            currentRadius += waveGap

            if (loadingDotCounter < 4)
                loadingDotCounter++;
            else
                loadingDotCounter = 1;

        }
        if (finish) {
            visibility = View.GONE
        }
        var loadingTextTemp = loadingText + ".".repeat(loadingDotCounter);
        canvas.drawText(
            loadingTextTemp,
            (((width / 3) + ((width / 3).toFloat() * 2)) / 2) - 100,
            (height / 3).toFloat() + ((width / 3).toFloat() * 2) - ((width / 3).toFloat()) + 100.0f,
            textPaint
        );


    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchY = event.y.toDouble()

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isTouchBottom = true
                // start the animation:

                this.postInvalidate();
                return true
            }
            MotionEvent.ACTION_UP -> {
                return true
            }
        }
        return false
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = widthSize
        val height = heightSize

        setMeasuredDimension(width, height)
    }

    private fun setCommonPaintProperties(paint: Paint) {
        paint.isDither = true
        paint.style = Paint.Style.FILL
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.isAntiAlias = true
    }

    private fun setPaintProperties() {
        val fontSize = 50
        val defaultButtonColor = ContextCompat.getColor(context, android.R.color.darker_gray)
        val gradient = LinearGradient(
            0f, 0f, 0f, (height / 2).toFloat(),
            intArrayOf(Color.YELLOW, defaultButtonColor, defaultButtonColor),
            floatArrayOf(0f, 0.85.toFloat(), 1f), Shader.TileMode.MIRROR
        )

        setCommonPaintProperties(buttonTouchPaint)
        buttonTouchPaint.shader = gradient

        setCommonPaintProperties(iconPaint)
        iconPaint.style = Paint.Style.STROKE
        iconPaint.color = Color.BLACK
        iconPaint.strokeWidth = 10f
        iconPaint.isAntiAlias = true


        loadingPathBackground.color = Color.GRAY
        loadingPathBackground.alpha = 100
        loadingPathBackground.style = Paint.Style.STROKE
        loadingPathBackground.strokeWidth = 15f
        loadingPathBackground.textSize = fontSize.toFloat()
        loadingPathBackground.isAntiAlias = true


        loadingPathBackgroundInner.color = Color.WHITE
        loadingPathBackgroundInner.alpha = 100
        loadingPathBackgroundInner.style = Paint.Style.FILL
        loadingPathBackgroundInner.strokeWidth = 5f
        loadingPathBackgroundInner.textSize = fontSize.toFloat()
        loadingPathBackgroundInner.isAntiAlias = true

        buttonDefaultPaint.color = defaultButtonColor


        textPaint.setColor(Color.BLACK)
        textPaint.setTextSize(50.0f)
        textPaint.setAntiAlias(true)
        textPaint.setTypeface(Typeface.create("HELVETICA", Typeface.NORMAL))
        textPaint.setShadowLayer(30.0f, 0.0f, 0.0f, Color.BLACK)

        textBitmap = Bitmap.createBitmap(500, 300, Bitmap.Config.ARGB_8888)
        textBitmap?.apply {
            val canvas2 = Canvas(this)
            canvas2.drawText("Dec Use", 100.0f, 100.0f, textPaint)
        }

    }

    private var waveAnimator: ValueAnimator? = null
    private var waveRadiusOffset = 0f
        set(value) {
            field = value
            postInvalidateOnAnimation()
        }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

    }

    override fun onDetachedFromWindow() {
        waveAnimator?.cancel()
        super.onDetachedFromWindow()
    }

    fun finish() {
        finish = true
        waveAnimator?.cancel()
    }

    /*
        Pass @activity veriable
        This function will start animation on root level
        i.e Including Top Navigation Bar
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun startWithRespectToApplication(activity: Activity) {

        if(viewGroup!=null){
            viewGroup?.removeView(this)
        }
        viewGroup = activity.window.decorView as ViewGroup
        viewGroup?.addView(this)
        finish = false
        waveAnimator = ValueAnimator.ofFloat(0f, waveGap).apply {
            addUpdateListener {
                waveRadiusOffset = it.animatedValue as Float
            }
            duration = 2000L
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }

        visibility = View.VISIBLE
        var uiview = activity?.findViewById<View>(android.R.id.content)?.getRootView()!!
        backgroundBitmap = getBitmapFromView(uiview, Color.WHITE)

    }

    /*
    Pass @activity veriable
    This function will start animation on the provided UI
    @view : Will be root view object
    */

    fun startWithRespectToUI(view: View, activity: Activity) {

        if(viewGroup!=null){
            viewGroup?.removeView(this)
        }
        viewGroup = activity.window.decorView as ViewGroup
        viewGroup?.addView(this)

        finish = false
        waveAnimator = ValueAnimator.ofFloat(0f, waveGap).apply {
            addUpdateListener {
                waveRadiusOffset = it.animatedValue as Float
            }
            duration = 1000L
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }

        visibility = View.VISIBLE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            backgroundBitmap = getBitmapFromView(view, Color.WHITE)
        }

    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @WorkerThread
    open fun getBitmapFromView(view: View, defaultColor: Int): Bitmap? {
        var bitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        var canvas = Canvas(bitmap)
        canvas.drawColor(defaultColor)
        view.draw(canvas)

        return blurBitmap(bitmap, context)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @WorkerThread
    fun blurBitmap(bitmap: Bitmap, applicationContext: Context): Bitmap {
        lateinit var rsContext: RenderScript
        try {

            // Create the output bitmap
            val output = Bitmap.createBitmap(
                bitmap.width, bitmap.height, bitmap.config
            )

            // Blur the image
            rsContext = RenderScript.create(applicationContext, RenderScript.ContextType.DEBUG)
            val inAlloc = Allocation.createFromBitmap(rsContext, bitmap)
            val outAlloc = Allocation.createTyped(rsContext, inAlloc.type)
            val theIntrinsic = ScriptIntrinsicBlur.create(rsContext, Element.U8_4(rsContext))
            theIntrinsic.apply {
                setRadius(25f)
                theIntrinsic.setInput(inAlloc)
                theIntrinsic.forEach(outAlloc)
            }
            outAlloc.copyTo(output)

            return output
        } finally {
            rsContext.finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getBitmapFromView(view: View, activity: Activity, callback: (Bitmap) -> Unit) {
        activity.window?.let { window ->
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val locationOfViewInWindow = IntArray(2)
            view.getLocationInWindow(locationOfViewInWindow)
            try {
                PixelCopy.request(
                    window,
                    Rect(
                        locationOfViewInWindow[0],
                        locationOfViewInWindow[1],
                        locationOfViewInWindow[0] + view.width,
                        locationOfViewInWindow[1] + view.height
                    ),
                    bitmap,
                    { copyResult ->
                        if (copyResult == PixelCopy.SUCCESS) {
                            callback(bitmap)
                        }
                        // possible to handle other result codes ...
                    },
                    Handler()
                )
            } catch (e: IllegalArgumentException) {
                // PixelCopy may throw IllegalArgumentException, make sure to handle it
                e.printStackTrace()
            }
        }
    }

}