package ir.app.rooritm.view.image

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import ir.app.rooritm.R
import ir.app.rooritm.global.extension.loadBitmap
import ir.app.rooritm.global.extension.loadCircleImage
import ir.app.rooritm.global.extension.loadImage
import ir.app.rooritm.global.extension.loadRoundedImage
import it.ctatuscolana.ctaapp.global.extension.color
import kotlinx.android.synthetic.main.view_image_with_cd_layout.view.*

/**
 * Create by Mohammadreza Allahgholi &&  Javad vatandoost
 *  Site: https://seniorandroid.ir
 */
class ImageWithCdLayout(context: Context, attrs: AttributeSet?) :
    FrameLayout(context, attrs) {
    val mUtil: BitmapCropperUtil by lazy { BitmapCropperUtil().apply {
        mInnerCircleSize = context.resources.getDimension(R.dimen._20sdp)
        mStrokeWidthOuter = context.resources.getDimension(R.dimen._8sdp)
        mStrokeWidthInner = context.resources.getDimension(R.dimen._6sdp)
        mStrokeColorInner = context.color(R.color.cd_stroke_inner)
        mStrokeColorOuter = context.color(R.color.cd_stroke_outer)
    } }
    val radius = context.resources.getDimension(R.dimen.bigCornerRadius).toInt()
    private var view: View = inflate(context, R.layout.view_image_with_cd_layout, this)

    fun setImageLink(url: String?) {
        view.context.loadBitmap(url) {
            it?.let {
                mUtil.convertToCd(it,view.ivLogoCd)
            }
        }
    }

}