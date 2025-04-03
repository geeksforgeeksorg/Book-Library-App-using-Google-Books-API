package org.geeksforgeeks.demo

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private lateinit var mRequestQueue: RequestQueue
    private lateinit var bookInfoArrayList: ArrayList<BookInfo>
    private lateinit var progressBar: ProgressBar
    private lateinit var searchEdt: EditText
    private lateinit var searchBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        searchEdt = findViewById(R.id.searchEditText)
        searchBtn = findViewById(R.id.searchButton)

        searchBtn.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val query = searchEdt.text.toString()
            if (query.isEmpty()) {
                searchEdt.error = "Please enter search query"
                return@setOnClickListener
            }
            getBooksInfo(query)
        }
    }

    private fun getBooksInfo(query: String) {
        bookInfoArrayList = ArrayList()
        mRequestQueue = Volley.newRequestQueue(this)
        mRequestQueue.cache.clear()

        val url = "https://www.googleapis.com/books/v1/volumes?q=$query"
        val queue = Volley.newRequestQueue(this)

        val booksObjRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                progressBar.visibility = View.GONE
                try {
                    val itemsArray = response.getJSONArray("items")
                    for (i in 0 until itemsArray.length()) {
                        val itemsObj = itemsArray.getJSONObject(i)
                        val volumeObj = itemsObj.getJSONObject("volumeInfo")
                        val title = volumeObj.optString("title", "N/A")
                        val subtitle = volumeObj.optString("subtitle", "N/A")
                        val authorsArray = volumeObj.optJSONArray("authors") ?: JSONArray()
                        val publisher = volumeObj.optString("publisher", "N/A")
                        val publishedDate = volumeObj.optString("publishedDate", "N/A")
                        val description = volumeObj.optString("description", "N/A")
                        val pageCount = volumeObj.optInt("pageCount", 0)
                        val imageLinks = volumeObj.optJSONObject("imageLinks")
                        val thumbnail = imageLinks?.optString("thumbnail", "") ?: ""
                        val previewLink = volumeObj.optString("previewLink", "")
                        val infoLink = volumeObj.optString("infoLink", "")
                        val saleInfoObj = itemsObj.optJSONObject("saleInfo")
                        val buyLink = saleInfoObj?.optString("buyLink", "") ?: ""

                        val authorsArrayList = ArrayList<String>()
                        for (j in 0 until authorsArray.length()) {
                            authorsArrayList.add(authorsArray.optString(j, "Unknown"))
                        }

                        val bookInfo = BookInfo(
                            title, subtitle, authorsArrayList, publisher, publishedDate,
                            description, pageCount, thumbnail, previewLink, infoLink, buyLink
                        )

                        bookInfoArrayList.add(bookInfo)
                    }

                    val adapter = BookAdapter(bookInfoArrayList, this)
                    val recyclerView = findViewById<RecyclerView>(R.id.rv)
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    recyclerView.adapter = adapter

                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, "No Data Found: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            { error: VolleyError ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        queue.add(booksObjRequest)
    }
}
