package fr.imt.android.nguegan

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class BookDetails : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.book_details, container, false)
        val titleView = view?.findViewById<TextView>(R.id.bookDetailsTitle)
        val title = arguments?.getString("title")
        if (title != null) {
            titleView?.text = title
        }
        return view
    }

}