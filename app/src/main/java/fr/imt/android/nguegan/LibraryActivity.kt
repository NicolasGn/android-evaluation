package fr.imt.android.nguegan

import android.app.Activity
import android.content.res.Configuration
import android.net.LinkAddress
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import android.widget.LinearLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class LibraryActivity : AppCompatActivity(), BookList.OnBookSelectedListener {

    companion object {
        const val SELECTED_BOOK_KEY = "selectedBook"
        const val BOOKS_KEY = "books"
    }

    private var isLandscape: Boolean = false
    private var books: Array<Book>? = null
    private var selectedBook: Book? = null

    override fun onBookSelected(book: Book) {
        Timber.i("Selected ${book.title}")

        val bookDetails = BookDetails()
        val bundle = Bundle()
        bundle.putString("title", book.title)
        bookDetails.arguments = bundle

        selectedBook = book

        if (isLandscape) {
            supportFragmentManager.beginTransaction()
                .replace(if (isLandscape) R.id.sideContainer else R.id.mainContainer , bookDetails)
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(if (isLandscape) R.id.sideContainer else R.id.mainContainer , bookDetails)
                .addToBackStack(BookDetails::class.java.name)
                .commit()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)
        Timber.plant(Timber.DebugTree())

        isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        val mainLayout = findViewById<LinearLayout>(R.id.mainLayout)
        mainLayout.orientation = if (isLandscape) LinearLayout.HORIZONTAL else LinearLayout.VERTICAL

        val sideContainer = findViewById<FrameLayout>(R.id.sideContainer)
        sideContainer.visibility = if (isLandscape) FrameLayout.VISIBLE else FrameLayout.GONE

        if (books == null) {
            val retrofit = Retrofit.Builder()
                    .baseUrl("http://henri-potier.xebia.fr/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            val api = retrofit.create(HenriPotierService::class.java)

            api.listBooks().enqueue(FetchBooksCallback(this))
        } else {
            updateList()
        }
    }

    private class FetchBooksCallback(private val activity: LibraryActivity) : Callback<Array<Book>> {
        override fun onFailure(call: Call<Array<Book>>, t: Throwable) {
            Timber.e("Error fetching books !")
        }

        override fun onResponse(call: Call<Array<Book>>, response: Response<Array<Book>>) {
            activity.books = response.body()
            activity.updateList()
        }
    }

    private fun updateList() {
        val bundle = Bundle()
        bundle.putParcelableArray("books", books)

        val bookList = BookList()
        bookList.arguments = bundle

        supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, bookList)
                .commit()

        if (selectedBook != null) {
            onBookSelected(selectedBook as Book)
        }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArray(BOOKS_KEY, books)
        outState.putParcelable(SELECTED_BOOK_KEY, selectedBook)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        books = savedInstanceState.getParcelableArray(BOOKS_KEY) as Array<Book>
        selectedBook = savedInstanceState.getParcelable(SELECTED_BOOK_KEY)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (!isLandscape) selectedBook = null
    }
}
