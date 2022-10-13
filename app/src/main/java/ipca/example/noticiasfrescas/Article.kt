package ipca.example.noticiasfrescas

import java.util.*

class Article {

    var title : String? = null
    var body : String? = null
    var pubDate : Date? = null
    var imageUrl : String? = null

    constructor(title: String?, body: String?, pubDate: Date?, imageUrl: String?) {
        this.title = title
        this.body = body
        this.pubDate = pubDate
        this.imageUrl = imageUrl
    }

}