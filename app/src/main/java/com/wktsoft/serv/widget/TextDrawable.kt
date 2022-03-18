package com.wktsoft.serv.widget

import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.graphics.drawable.shapes.RoundRectShape
import java.util.*


class TextDrawable(builder: Builder) : ShapeDrawable(builder.shape) {

    private var textPaint: Paint? = null
    private var borderPaint: Paint? = null
    private val SHADE_FACTOR = 0.9f
    private var text: String? = null
    private var color = 0
    private var shape: RectShape? = null
    private var height = 0
    private var width = 0
    private var fontSize = 0
    private var radius = 0f
    private var borderThickness = 0

    init {
        shape = builder.shape
        height = builder.height
        width = builder.width
        radius = builder.radius
        text =
            if (builder.toUpperCase) builder.text.toUpperCase(Locale.getDefault()) else builder.text
        color = builder.color
        fontSize = builder.fontSize
        textPaint = Paint()
        textPaint!!.setColor(builder.textColor)
        textPaint!!.setAntiAlias(true)
        textPaint!!.setFakeBoldText(builder.isBold)
        textPaint!!.setStyle(Paint.Style.FILL)
        textPaint!!.setTypeface(builder.font)
        textPaint!!.setTextAlign(Paint.Align.CENTER)
        textPaint!!.setStrokeWidth(builder.borderThickness.toFloat())
        borderThickness = builder.borderThickness
        borderPaint = Paint()
        borderPaint!!.setColor(getDarkerShade(color))
        borderPaint!!.setStyle(Paint.Style.STROKE)
        borderPaint!!.setStrokeWidth(borderThickness.toFloat())
        val paint: Paint = paint
        paint.setColor(color)
    }

    private fun getDarkerShade(color: Int): Int {
        return Color.rgb(
            (SHADE_FACTOR * Color.red(color)).toInt(),
            (SHADE_FACTOR * Color.green(color)).toInt(),
            (SHADE_FACTOR * Color.blue(color)).toInt()
        )
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val r: Rect = bounds


        // draw border
        if (borderThickness > 0) {
            drawBorder(canvas)
        }
        val count: Int = canvas.save()
        canvas.translate(r.left.toFloat(), r.top.toFloat())

        // draw text
        val width = if (width < 0) r.width() else width
        val height = if (height < 0) r.height() else height
        val fontSize = if (fontSize < 0) Math.min(width, height) / 2 else fontSize
        textPaint?.setTextSize(fontSize.toFloat())
        text?.let {
            textPaint?.let { it1 ->
                canvas.drawText(
                    it,
                    (width / 2).toFloat(),
                    height / 2 - (textPaint!!.descent() + textPaint!!.ascent()) / 2,
                    it1
                )
            }
        }
        canvas.restoreToCount(count)
    }

    private fun drawBorder(canvas: Canvas) {
        val rect = RectF(bounds)
        rect.inset((borderThickness / 2).toFloat(), (borderThickness / 2).toFloat())
        if (shape is OvalShape) {
            borderPaint?.let { canvas.drawOval(rect, it) }
        } else if (shape is RoundRectShape) {
            borderPaint?.let { canvas.drawRoundRect(rect, radius, radius, it) }
        } else {
            borderPaint?.let { canvas.drawRect(rect, it) }
        }
    }

    override fun setAlpha(alpha: Int) {
        textPaint?.setAlpha(alpha)
    }

    override fun setColorFilter(cf: ColorFilter?) {
        textPaint?.setColorFilter(cf)
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun getIntrinsicWidth(): Int {
        return width
    }

    override fun getIntrinsicHeight(): Int {
        return height
    }

    fun builder(): IShapeBuilder {
        return Builder()
    }

    class Builder : IConfigBuilder, IShapeBuilder, IBuilder {
        var text = ""
        var color: Int
        var borderThickness: Int
        var width: Int
        var height: Int
        var font: Typeface
        var shape: RectShape
        var textColor: Int
        var fontSize: Int
        var isBold: Boolean
        var toUpperCase: Boolean
        var radius = 0f
        override fun width(width: Int): IConfigBuilder {
            this.width = width
            return this
        }

        override fun height(height: Int): IConfigBuilder {
            this.height = height
            return this
        }

        override fun textColor(color: Int): IConfigBuilder {
            textColor = color
            return this
        }

        override fun withBorder(thickness: Int): IConfigBuilder {
            borderThickness = thickness
            return this
        }

        override fun useFont(font: Typeface?): IConfigBuilder {
            if (font != null) {
                this.font = font
            }
            return this
        }

        override fun fontSize(size: Int): IConfigBuilder {
            fontSize = size
            return this
        }

        override fun bold(): IConfigBuilder {
            isBold = true
            return this
        }

        override fun toUpperCase(): IConfigBuilder {
            toUpperCase = true
            return this
        }

        override fun beginConfig(): IConfigBuilder {
            return this
        }

        override fun endConfig(): IShapeBuilder {
            return this
        }

        override fun rect(): IBuilder {
            shape = RectShape()
            return this
        }

        override fun round(): IBuilder {
            shape = OvalShape()
            return this
        }

        override fun roundRect(radius: Int): IBuilder {
            this.radius = radius.toFloat()
            val radii = floatArrayOf(
                radius.toFloat(),
                radius.toFloat(),
                radius.toFloat(),
                radius.toFloat(),
                radius.toFloat(),
                radius.toFloat(),
                radius.toFloat(),
                radius.toFloat()
            )
            shape = RoundRectShape(radii, null, null)
            return this
        }

        override fun buildRect(text: String?, color: Int): TextDrawable {
            rect()
            return build(text, color)
        }

        override fun buildRoundRect(text: String?, color: Int, radius: Int): TextDrawable {
            roundRect(radius)
            return build(text, color)
        }

        override fun buildRound(text: String?, color: Int): TextDrawable {
            round()
            return build(text, color)
        }

        override fun build(text: String?, color: Int): TextDrawable {
            this.color = color
            if (text != null) {
                this.text = text
            }
            return TextDrawable(this)
        }

        init {
            color = Color.GRAY
            textColor = Color.WHITE
            borderThickness = 0
            width = -1
            height = -1
            shape = RectShape()
            font = Typeface.create("sans-serif-light", Typeface.NORMAL)
            fontSize = -1
            isBold = false
            toUpperCase = false
        }
    }

    interface IConfigBuilder {
        fun width(width: Int): IConfigBuilder?
        fun height(height: Int): IConfigBuilder?
        fun textColor(color: Int): IConfigBuilder?
        fun withBorder(thickness: Int): IConfigBuilder?
        fun useFont(font: Typeface?): IConfigBuilder?
        fun fontSize(size: Int): IConfigBuilder?
        fun bold(): IConfigBuilder?
        fun toUpperCase(): IConfigBuilder?
        fun endConfig(): IShapeBuilder?
    }

    interface IBuilder {
        fun build(text: String?, color: Int): TextDrawable?
    }

    interface IShapeBuilder {
        fun beginConfig(): IConfigBuilder?
        fun rect(): IBuilder?
        fun round(): IBuilder?
        fun roundRect(radius: Int): IBuilder?
        fun buildRect(text: String?, color: Int): TextDrawable?
        fun buildRoundRect(text: String?, color: Int, radius: Int): TextDrawable?
        fun buildRound(text: String?, color: Int): TextDrawable?
    }

}