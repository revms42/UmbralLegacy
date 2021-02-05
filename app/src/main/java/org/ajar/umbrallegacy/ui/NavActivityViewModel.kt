package org.ajar.umbrallegacy.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.ajar.umbrallegacy.model.Image

class NavActivityViewModel : ViewModel() {

    var fabAction: (() -> Unit)? = null

    private val _fabIcon = MutableLiveData<Image?>()
    val fabIconLD: LiveData<Image?> = _fabIcon
    var fabIcon: Image?
        get() = fabIconLD.value
        set(value) {
            _fabIcon.postValue(value)
        }
}