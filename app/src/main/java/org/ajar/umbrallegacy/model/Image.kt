package org.ajar.umbrallegacy.model

import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import org.ajar.umbrallegacy.R
import java.io.File

@Suppress("DataClassPrivateConstructor")
data class Image private constructor(private var _resource: Int? = null, private var _path: String? = null) {

    constructor(@DrawableRes resource: Int) : this(resource, null)
    constructor(path: String) : this(null, path)

    private var drawable: Drawable? = null

    var resource: Int?
        get() = _resource
        set(value) {
            _resource = value
            path = null
        }

    var path: String?
        get() = _path
        set(value) {
            _resource = null
            if(value?.let {File(value).exists()} == true) {
                _path = value
            }
        }

    val invalid: Boolean
        get() = path == null && (resource == null || resource == R.drawable.ic_invalid)

    fun getDrawable(resources: Resources): Drawable {
        if(drawable == null) {
            drawable = when {
                path != null -> BitmapDrawable(resources, path!!)
                resource != null -> ResourcesCompat.getDrawable(resources, resource!!, null)
                else -> ResourcesCompat.getDrawable(resources, R.drawable.ic_invalid, null)
            }
        }
        return drawable!!
    }

    override fun toString(): String {
        return if(path.isNullOrBlank()) "res:$resource" else path?: ""
    }

    companion object {
        fun fromString(str: String): Image {
            return when {
                str.startsWith("res:") -> Image(str.split(":")[1].toInt())
                File(str).exists() -> Image(str)
                else -> Image()
            }
        }
    }
}