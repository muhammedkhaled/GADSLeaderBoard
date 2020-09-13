package com.khaled.gadsleaderboard.utils

import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.khaled.gadsleaderboard.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

class ImageViewUtils {
    companion object {
        fun circularImage(
            view: View,
            imageView: ImageView,
            image: Any,
            placeHolder: Int
        ) {
            Glide
                .with(view)
                .load(image)
                .transition(DrawableTransitionOptions().crossFade())
                .apply(RequestOptions().placeholder(placeHolder))
                .apply(RequestOptions().circleCrop())
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView)
        }

        fun fitImage(
            context: Context,
            imageView: ImageView,
            image: Any,
//            placeHolder: Int
        ) {
            Glide.with(context)
                .load(image)
                .transition(DrawableTransitionOptions().crossFade())
//                .apply(RequestOptions().placeholder(placeHolder))
                .apply(RequestOptions().fitCenter())
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView)
        }

        fun centerImage(
            context: Context,
            imageView: ImageView,
            image: Any,
//            placeHolder: Int
        ) {
            Glide.with(context)
                .load(image)
                .transition(DrawableTransitionOptions().crossFade())
//                .apply(RequestOptions().placeholder(placeHolder))
                .apply(RequestOptions().centerCrop())
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView)
        }

        fun getURLForResource(resourceId: Int): String {
            return Uri.parse("android.resource://" + R::class.java.getPackage()!!.name + "/" + resourceId)
                .toString()
        }
    }
}
