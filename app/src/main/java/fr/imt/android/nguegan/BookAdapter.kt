package fr.imt.android.nguegan

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class BookAdapter(private val books: List<Book>,
                  private val onClickListener: BookAdapter.OnClickListener)
    : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    class ViewHolder(val bookItemView: BookItemView) : RecyclerView.ViewHolder(bookItemView) {
        fun bind(book: Book, listener: BookAdapter.OnClickListener) {
            bookItemView.bindView(book)
            bookItemView.setOnClickListener {
                listener.onClick(book)
            }
        }
    }

    interface OnClickListener {
        fun onClick(book: Book)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_book, parent, false) as BookItemView
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = books.count()

    override fun onBindViewHolder(holder: BookAdapter.ViewHolder, position: Int) {
        holder.bind(books[position], onClickListener)
    }

}