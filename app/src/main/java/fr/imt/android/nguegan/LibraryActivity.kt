package fr.imt.android.nguegan

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import timber.log.Timber

class LibraryActivity : AppCompatActivity(), BookList.OnBookSelectedListener {
    override fun onBookSelected(book: Book) {
        Timber.i("Selected ${book.title}")

        val bookDetails = BookDetails()
        val bundle = Bundle()
        bundle.putString("title", book.title)
        bookDetails.arguments = bundle

        supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, bookDetails)
                .addToBackStack(BookDetails::class.java.name)
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)
        Timber.plant(Timber.DebugTree())

        supportFragmentManager.beginTransaction()
                .add(R.id.mainContainer, BookList())
                .commit()
    }
}
