package com.skillbox.skillbox.flow.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.skillbox.skillbox.flow.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

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

fun <T : Fragment> T.toast(@StringRes message: Int) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun <T : Fragment> T.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

// расширение для проверки доступа к сети
val Context.isConnected: Boolean
    get() {
        return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .activeNetworkInfo?.isConnected == true
    }

//расширение для создания flow из editText поля
@ExperimentalCoroutinesApi
fun EditText.textChangesFlow(): Flow<String> {
    return callbackFlow {
//        создаем textChangeListener
        val textChangeListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                при изменении текста отправляем текст
                trySendBlocking(s?.toString().orEmpty())
            }

            override fun afterTextChanged(s: Editable?) {}
        }
//        добавляем лисенер
        this@textChangesFlow.addTextChangedListener(textChangeListener)
//        при закрытии поля удаляем лисенер
        awaitClose {
            this@textChangesFlow.removeTextChangedListener(textChangeListener)
        }
    }
}

//расширение для создания flow из RadioGroup
@ExperimentalCoroutinesApi
fun RadioGroup.elementChangeFlow(): Flow<Int> {
    return callbackFlow {
//        создаем checkedChangeListener
        val checkedChangeListener = RadioGroup.OnCheckedChangeListener { _, checkedId ->
//            находим нужный RadioButton по id
            findViewById<RadioButton>(checkedId).apply {
//                в зависимости от RadioButton отсылаем нужный результат
                when (this.text.toString()) {
                    "Movie" -> trySendBlocking(0)
                    "Series" -> trySendBlocking(1)
                    "Episode" -> trySendBlocking(2)
                }
            }
        }
//        добавляем лисенер
        setOnCheckedChangeListener(checkedChangeListener)
//        при закрытии поля удаляем лисенер
        awaitClose {
            setOnCheckedChangeListener(null)
        }
    }

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

