# CD-ImageCropper

### An Image cropper like CD form, All attributes could changed.

![Screenshot](Disc.png)



## How to use 

#### In your layout use [ImageWithCdLayout](https://github.com/javadVatan/CdImageCropper/blob/main/src/ImageWithCdLayout.kt)
```
<your.package.name.ImageWithCdLayout
        android:id="@+id/iwlCover"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintDimensionRatio="1:1"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
 ```
 
 #### Set your link with method `setImageLink`
 ```
 iwlCover.setImageLink("your_link")
 ```
 
#### Properties you could cahnged inside [ImageWithCdLayout](https://github.com/javadVatan/CdImageCropper/blob/main/src/ImageWithCdLayout.kt) class

```
   val mUtil: BitmapCropperUtil by lazy { BitmapCropperUtil().apply {
        mInnerCircleSize = context.resources.getDimension(R.dimen._20sdp)
        mStrokeWidthOuter = context.resources.getDimension(R.dimen._8sdp)
        mStrokeWidthInner = context.resources.getDimension(R.dimen._6sdp)
        mStrokeColorInner = context.color(R.color.cd_stroke_inner)
        mStrokeColorOuter = context.color(R.color.cd_stroke_outer)
    } }
```


