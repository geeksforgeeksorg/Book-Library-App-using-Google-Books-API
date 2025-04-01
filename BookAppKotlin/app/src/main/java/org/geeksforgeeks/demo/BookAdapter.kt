package org.geeksforgeeks.demo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BookAdapter(
    private val bookInfoArrayList: ArrayList<BookInfo>,
    private val context: Context
) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        // inflating layout for item of recycler view
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.book_rv_item, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        // setup data to each UI component
        val bookInfo = bookInfoArrayList[position]
        holder.nameTV.text = bookInfo.title
        holder.publisherTV.text = bookInfo.publisher
        holder.pageCountTV.text = "Pages : " + bookInfo.pageCount
        holder.dateTV.text = "Published On : " + bookInfo.publishedDate
        // set image from URL in our image view.
        Glide.with(context).load(bookInfo.thumbnail).into(holder.bookIV)

        // add on click listener on the card
        holder.itemView.setOnClickListener {
            // call a new activity and passing all the data of that item in next intent
            val intent = Intent(context, BookDetails::class.java)
            intent.putExtra("title", bookInfo.title)
            intent.putExtra("subtitle", bookInfo.subtitle)
            intent.putExtra("authors", bookInfo.authors)
            intent.putExtra("publisher", bookInfo.publisher)
            intent.putExtra("publishedDate", bookInfo.publishedDate)
            intent.putExtra("description", bookInfo.description)
            intent.putExtra("pageCount", bookInfo.pageCount)
            intent.putExtra("thumbnail", bookInfo.thumbnail)
            intent.putExtra("previewLink", bookInfo.previewLink)
            intent.putExtra("infoLink", bookInfo.infoLink)
            intent.putExtra("buyLink", bookInfo.buyLink)

            // after passing that data we are start new  intent
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = bookInfoArrayList.size

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // initialize text views and image view
        var nameTV: TextView = itemView.findViewById(R.id.bookTitle)
        var publisherTV: TextView = itemView.findViewById(R.id.publisher)
        var pageCountTV: TextView = itemView.findViewById(R.id.pageCount)
        var dateTV: TextView = itemView.findViewById(R.id.date)
        var bookIV: ImageView = itemView.findViewById(R.id.bookImage)
    }
}