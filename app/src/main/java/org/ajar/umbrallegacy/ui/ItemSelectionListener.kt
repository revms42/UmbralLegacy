package org.ajar.umbrallegacy.ui

import android.view.View

interface ItemSelectionListener<A> {

    fun onSelect(item: A, view: View)
}