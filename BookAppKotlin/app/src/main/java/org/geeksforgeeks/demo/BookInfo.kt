package org.geeksforgeeks.demo

data class BookInfo (
    var title: String,
    var subtitle: String,
    var authors: ArrayList<String>,
    var publisher: String,
    var publishedDate: String,
    var description: String,
    var pageCount: Int,
    var thumbnail: String,
    var previewLink: String,
    var infoLink: String,
    var buyLink: String
)