package com.note.notes

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox

class ToggleBookmark @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatCheckBox(context, attrs, defStyleAttr) {
    override fun setChecked(checked: Boolean) {
        super.setChecked(checked)
        this.setBackgroundResource(R.drawable.checkbox_state)
    }

}