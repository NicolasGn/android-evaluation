package fr.imt.android.nguegan

import retrofit2.Call
import retrofit2.http.GET

interface HenriPotierService {
    @GET("books")
    fun listBooks(): Call<Array<Book>>
}
