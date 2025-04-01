package org.geeksforgeeks.demo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class BookDetails : AppCompatActivity() {
    //creating variables for strings,text view, image views and button.
    private var title: String? = null
    private var subtitle: String? = null
    private var publisher: String? = null
    private var publishedDate: String? = null
    private var description: String? = null
    private var thumbnail: String? = null
    private var previewLink: String? = null
    private var infoLink: String? = null
    private var buyLink: String? = null
    private var pageCount: Int = 0

    private lateinit var titleTV: TextView
    private lateinit var subtitleTV: TextView
    private lateinit var publisherTV: TextView
    private lateinit var descTV: TextView
    private lateinit var pageTV: TextView
    private lateinit var publishDateTV: TextView
    private lateinit var previewBtn: Button
    private lateinit var buyBtn: Button
    private lateinit var bookIV: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_book_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //initializing our views..
        titleTV = findViewById(R.id.bookTitle)
        subtitleTV = findViewById(R.id.bookSubTitle)
        publisherTV = findViewById(R.id.publisher)
        descTV = findViewById(R.id.bookDescription)
        pageTV = findViewById(R.id.pageCount)
        publishDateTV = findViewById(R.id.publishedDate)
        previewBtn = findViewById(R.id.idBtnPreview)
        buyBtn = findViewById(R.id.idBtnBuy)
        bookIV = findViewById(R.id.bookImage)

        //getting the data which we have passed from our adapter class.
        title = intent.getStringExtra("title")
        subtitle = intent.getStringExtra("subtitle")
        publisher = intent.getStringExtra("publisher")
        publishedDate = intent.getStringExtra("publishedDate")
        description = intent.getStringExtra("description")
        pageCount = intent.getIntExtra("pageCount", 0)
        thumbnail = intent.getStringExtra("thumbnail")
        previewLink = intent.getStringExtra("previewLink")
        infoLink = intent.getStringExtra("infoLink")
        buyLink = intent.getStringExtra("buyLink")

        //after getting the data we are setting that data to our text views and image view.
        titleTV.text = title
        subtitleTV.text = subtitle
        publisherTV.text = publisher
        publishDateTV.text = "Published On : $publishedDate"
        descTV.text = description
        pageTV.text = "Pages : $pageCount"
        Glide.with(this).load(thumbnail).into(bookIV)

        //adding on click listener for our preview button.
        previewBtn.setOnClickListener {
            if (previewLink!!.isEmpty()) {
                //below toast message is displayed when preview link is not present.
                Toast.makeText(this@BookDetails, "No preview Link present", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //if the link is present we are opening that link via an intent.
            val uri = Uri.parse(previewLink)
            val i = Intent(Intent.ACTION_VIEW, uri)
            startActivity(i)
        }

        //initializing on click listener for buy button.
        buyBtn.setOnClickListener {
            if (buyLink!!.isEmpty()) {
                //below toast message is displaying when buy link is empty.
                Toast.makeText(this@BookDetails, "No buy page present for this book", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //if the link is present we are opening the link via an intent.
            val uri = Uri.parse(buyLink)
            val i = Intent(Intent.ACTION_VIEW, uri)
            startActivity(i)
        }
    }
}