package fr.imt.android.nguegan

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class LibraryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        viewManager = LinearLayoutManager(this)

        Timber.plant(Timber.DebugTree())

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

                viewAdapter = BookAdapter(books)

                findViewById<RecyclerView>(R.id.bookRecyclerView).apply {
                    setHasFixedSize(true)
                    layoutManager = viewManager
                    adapter = viewAdapter
                }
            }
        })
    }

}
