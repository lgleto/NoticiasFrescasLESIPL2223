package ipca.example.noticiasfrescas

import org.json.JSONObject
import java.util.*

class Article {

    var title : String? = null
    var content : String? = null
    var publishedAt : Date? = null
    var urlToImage : String? = null
    var url : String? = null

    constructor(title: String?,
                content: String?,
                publishedAt: Date?,
                urlToImage: String?,
                url: String?) {
        this.title = title
        this.content = content
        this.publishedAt = publishedAt
        this.urlToImage = urlToImage
        this.url = url
    }

    companion object{
        fun fromJSON(jsonObject: JSONObject) : Article {
            return Article(
                jsonObject.getString("title"),
                jsonObject.getString("content"),
                 Date(),//jsonObject.getString("publishedAt"),
                jsonObject.getString("urlToImage"),
                jsonObject.getString("url")
            )
        }
    }

}