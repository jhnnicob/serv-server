package com.wktsoft.serv.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.wktsoft.serv.R

class UIUtil {

    companion object {

        fun convertDPStoPixel(context: Context, dps: Int): Int {
            val scale = context.resources.displayMetrics.density
            return (dps * scale + 0.5f).toInt()
        }

        fun createTextImage(context: Context, name: String): Bitmap? {
            val size: Int = convertDPStoPixel(context, 80)
            val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)

            val fillPaint = Paint()
            fillPaint.style = Paint.Style.FILL
            ResourceUtil.getInstance()?.getColor(R.color.colorSilver)?.let { fillPaint.setColor(it) }
            canvas.drawRect(0f, 0f, size.toFloat(), size.toFloat(), fillPaint)

            val paint = Paint()
            ResourceUtil.getInstance()?.getColor(R.color.colorText)?.let { paint.setColor(it)}
            paint.textSize = convertDPStoPixel(context, 14).toFloat()
            paint.textScaleX = 1f
            paint.textAlign = Paint.Align.CENTER

            val xPos = canvas.width / 2
            val yPos = (canvas.height / 2 - (paint.descent() + paint.ascent()) / 2).toInt()

            canvas.drawText(name, xPos.toFloat(), yPos.toFloat(), paint)
            return bitmap
        }
    }

}