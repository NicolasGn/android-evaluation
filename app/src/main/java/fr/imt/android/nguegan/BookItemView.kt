package fr.imt.android.nguegan

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView

class BookItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var titleView: TextView? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        titleView = findViewById(R.id.titleTextView)
    }

    fun bindView(book: Book) {
        titleView?.text = book.title
    }

}