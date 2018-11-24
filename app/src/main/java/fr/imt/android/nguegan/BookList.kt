package fr.imt.android.nguegan

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.lang.ClassCastException

class BookList : Fragment() {

    interface OnBookSelectedListener {
        fun onBookSelected(book: Book)
    }

    private var viewAdapter: RecyclerView.Adapter<*>? = null
    private var viewManager: RecyclerView.LayoutManager? = null

    private var listener: OnBookSelectedListener? = null

    private class OnBookClickListener(private val onSelectedListener: OnBookSelectedListener?)
            : BookAdapter.OnClickListener {
        override fun onClick(book: Book) {
            onSelectedListener?.onBookSelected(book)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.book_list, container, false) as RecyclerView

        viewManager = LinearLayoutManager(view.context)

        val retrofit = Retrofit.Builder()
                .baseUrl("http://henri-potier.xebia.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val api = retrofit.create(HenriPotierService::class.java)

        api.listBooks().enqueue(object : Callback<Array<Book>> {
            override fun onFailure(call: Call<Array<Book>>, t: Throwable) {
                Timber.e("Error fetching books !")
            }

            override fun onResponse(call: Call<Array<Book>>, response: Response<Array<Book>>) {
                var books = ArrayList<Book>()
                response.body()?.forEach {
                    Timber.i(it.title)
                    books.add(it)
                }

                viewAdapter = BookAdapter(books, OnBookClickListener(listener))

                view.apply {
                    setHasFixedSize(true)
                    layoutManager = viewManager
                    adapter = viewAdapter
                }
            }
        })

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as OnBookSelectedListener
        if (listener == null) {
            throw ClassCastException("$context must implement OnBookSelectedListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}