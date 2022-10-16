package ir.app.rooritm.view.image

import android.graphics.*
import android.widget.ImageView


class BitmapCropperUtil {
    private val pathCircle =
        "M56.5 102C84.1142 102 106.5 79.6142 106.5 52C106.5 24.3858 84.1142 2 56.5 2C28.8858 2 6.5 24.3858 6.5 52C6.5 79.6142 28.8858 102 56.5 102ZM56.5 60C60.9183 60 64.5 56.4183 64.5 52C64.5 47.5817 60.9183 44 56.5 44C52.0817 44 48.5 47.5817 48.5 52C48.5 56.4183 52.0817 60 56.5 60Z"
    var mInnerCircleSize = 80f
    var mStrokeWidthOuter: Float = 10f
    var mStrokeWidthInner: Float = 15f
    var mStrokeColorOuter: Int = Color.GRAY
    var mStrokeColorInner: Int = Color.GRAY

    fun convertToCd(src: Bitmap, ivDes: ImageView) {
        getCroppedBitmap(src)?.let {
            ivDes.setImageBitmap(it)
        }
    }

    private fun getPath(width: Int, height: Int, pathData: String): Path {
        return resizePath(PathParser.createPathFromPathData(pathData), width, height)
    }

    private fun resizePath(path: Path?, width: Int, height: Int): Path {
        val bounds = RectF(0f, 0f, width.toFloat(), height.toFloat())
        val resizedPath = Path(path)
        val src = RectF()
        resizedPath.computeBounds(src, true)
        val resizeMatrix = Matrix()
        resizeMatrix.setRectToRect(src, bounds, Matrix.ScaleToFit.CENTER)
        resizedPath.transform(resizeMatrix)
        return resizedPath
    }

    private fun getCroppedBitmap(src: Bitmap): Bitmap? {
        val output = Bitmap.createBitmap(src.width, src.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.setColor(-0x1000000)

        val pathOuterCircle = getPath(src.width, src.height, pathCircle)
        canvas.drawPath(pathOuterCircle, paint)


        // Keeps the source pixels that cover the destination pixels,
        // discards the remaining source and destination pixels.
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(src, 0f, 0f, paint)

        canvas.punchHole(paint, src.width / 2f, src.height / 2f)

        return output.addBorderToCircularBitmap()?.doHighlightImage()
    }

    private fun Bitmap.addBorderToCircularBitmap(): Bitmap? { // Calculate the circular bitmap width with border
        val dstBitmapWidth = width + mStrokeWidthOuter.toInt() * 2

        // Initialize a new Bitmap to make it bordered circular bitmap
        val dstBitmap = Bitmap.createBitmap(dstBitmapWidth, dstBitmapWidth, Bitmap.Config.ARGB_8888)

        // Initialize a new Canvas instance
        val canvas = Canvas(dstBitmap) // Draw source bitmap to canvas
        canvas.drawBitmap(this, mStrokeWidthOuter, mStrokeWidthOuter, null)

        // Initialize a new Paint instance to draw border
        val paint = Paint()
        paint.color = mStrokeColorOuter
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = mStrokeWidthOuter
        paint.isAntiAlias = true


        canvas.drawCircle(canvas.width / 2f, canvas.width / 2f,
                canvas.width / 2 - mStrokeWidthOuter / 2f, paint)

        // Draw Inner circle stroke
        paint.strokeWidth = mStrokeWidthInner
        paint.color = mStrokeColorInner
        canvas.drawCircle(canvas.width / 2f, canvas.width / 2f, mInnerCircleSize, paint)

        // Free the native object associated with this bitmap.
        this.recycle()

        // Return the bordered circular bitmap
        return dstBitmap
    }

    private fun Canvas.punchHole(paint: Paint, cx: Float, cy: Float) {
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        drawCircle(cx, cy, mInnerCircleSize, paint)
    }

    private fun Bitmap.doHighlightImage(): Bitmap? {
        val bmOut = Bitmap.createBitmap(width + mStrokeWidthOuter.toInt(),
                height + mStrokeWidthOuter.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmOut)
        canvas.drawColor(0, PorterDuff.Mode.CLEAR)
        val ptBlur = Paint()
        ptBlur.maskFilter = BlurMaskFilter(15f, BlurMaskFilter.Blur.NORMAL)
        val offsetXY = IntArray(2)
        val bmAlpha = extractAlpha(ptBlur, offsetXY)
        val ptAlphaColor = Paint()
        ptAlphaColor.color = Color.BLACK
        canvas.drawBitmap(bmAlpha, offsetXY[0].toFloat(), offsetXY[1].toFloat(), ptAlphaColor)
        bmAlpha.recycle()
        canvas.drawBitmap(this, 0f, 0f, null)
        return bmOut
    }

}