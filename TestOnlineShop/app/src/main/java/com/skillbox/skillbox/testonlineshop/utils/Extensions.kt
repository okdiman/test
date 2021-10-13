package com.skillbox.skillbox.testonlineshop.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.skillbox.skillbox.testonlineshop.R

//    инфлейт баиндинга
fun <T : ViewBinding> ViewGroup.inflate(
    inflateBinding: (
        inflater: LayoutInflater,
        root: ViewGroup?,
        attachToRoot: Boolean
    ) -> T, attachToRoot: Boolean = false
): T {
    val inflater = LayoutInflater.from(context)
    return inflateBinding(inflater, this, attachToRoot)
}

//    расширение для тостов с строковыми ресурсами и объектами String
fun <T : Fragment> T.toast(@StringRes message: Int) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun <T : Fragment> T.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

//    расширение для работы с Glide
fun ImageView.glideLoadImage(
    imageUri: Uri,
    @DrawableRes
    imagePlaceHolder: Int = R.drawable.ic_cloud_download,
    @DrawableRes
    imageError: Int = R.drawable.ic_sync_problem
) {
    Glide
        .with(this)
        .load(imageUri)
        .transform(CircleCrop())
        .dontAnimate()
        // картинка пока грузится изображение
        .placeholder(imagePlaceHolder)
        // картинка при ошибке загрузки
        .error(imageError)
        // масштабирует картинку, сохраняя пропорции. Можно увидеть задний фон контейнера, если его размеры отличаются от размера картинки.
        // .centerInside()
        // также размещает картинку в центре, но учитывает ширину или высоту контейнера.
        .centerCrop()
        .into(this)
}

//   расширение для проверки версии android у пользователя
fun haveQ(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
}

// расширение для проверки доступа к сети
val Context.isConnected: Boolean
    get() {
        return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .activeNetworkInfo?.isConnected == true
    }