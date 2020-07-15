package com.cchat.ldbuttonsandloadinganimation

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat


class ButtonBubbleSplitAnimation(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val buttonDefaultPaint: Paint = Paint()
    private val iconPaint: Paint = Paint()
    private val buttonTouchPaint: Paint = Paint()


    private val topSemiRect: RectF = RectF()

    private val path: Path = Path()
    private val textPaint: Paint = Paint()


    private var isTouchBottom = false
    private var initialRadius = 0f
    val waveGap:Float=360f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        val length: Float = (width / 2).toFloat()
        val centerTextY = (height / 2).toFloat()

        setPaintProperties()
        if(isTouchBottom) {
            topSemiRect.set(0f, 0f, (width.toFloat() / 2)-10f, height.toFloat()-10f)
//            canvas.drawRect(topSemiRect, iconPaint)
            canvas.concat(matrix);
            canvas.drawArc(topSemiRect, 180f, 180f, false, iconPaint)
            canvas.drawArc(topSemiRect, 180f, -180f, false, iconPaint)
            topSemiRect.set((width.toFloat() / 2)+10f, 0f, width.toFloat(), height.toFloat())
            canvas.drawArc(topSemiRect, 180f, 180f, false, iconPaint)
            canvas.drawArc(topSemiRect, 180f, -180f, false, iconPaint)
        }
        else{
            topSemiRect.set(width.toFloat()/4, 0f, (width.toFloat() / 4)*3, height.toFloat())
//            canvas.drawRect(topSemiRect, iconPaint)
            var currentRadius = initialRadius + waveRadiusOffset
            while (currentRadius < 360) {
                canvas.drawArc(topSemiRect, currentRadius, 10f, false, iconPaint)
                currentRadius += waveGap
            }

//            canvas.drawArc(topSemiRect, 180f, 180f, false, iconPaint)
//            canvas.drawArc(topSemiRect, 180f, -180f, false, iconPaint)
        }

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
        return super.onTouchEvent(event)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = widthSize / 2
        val height = widthSize / 4

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
        iconPaint.strokeWidth = 5f

        textPaint.color = Color.WHITE
        textPaint.textSize = fontSize.toFloat()
        textPaint.style = Paint.Style.FILL

        buttonDefaultPaint.color = defaultButtonColor
    }

    private var waveAnimator: ValueAnimator? = null
    private var waveRadiusOffset = 0f
        set(value) {
            field = value
            postInvalidateOnAnimation()
        }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        waveAnimator = ValueAnimator.ofFloat(0f, waveGap).apply {
            addUpdateListener {
                waveRadiusOffset = it.animatedValue as Float
            }
            duration = 1500L
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            start()
        }
    }

    override fun onDetachedFromWindow() {
        waveAnimator?.cancel()
        super.onDetachedFromWindow()
    }

}