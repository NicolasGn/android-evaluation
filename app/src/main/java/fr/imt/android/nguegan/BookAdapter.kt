package fr.imt.android.nguegan

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class BookAdapter(private val books: List<Book>) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    class ViewHolder(val bookItemView: BookItemView) : RecyclerView.ViewHolder(bookItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.ViewHolder {
        val bookItemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_book, parent, false) as BookItemView
        return ViewHolder(bookItemView)
    }

    override fun getItemCount(): Int = books.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bookItemView.bindView(books[position])
    }

    /*override fun getItem(position: Int): Book = books[position]

    override fun getItemId(position: Int): Long = books[position].hashCode().toLong()

    override fun getCount(): Int = books.count()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        if (view == null) {
            view = inflater.inflate(R.layout.item_view_book, parent, false)
        }
        val book = getItem(position)
        val bookItemView = view as BookItemView
        bookItemView.bindView(book)
        return bookItemView
    }*/

}