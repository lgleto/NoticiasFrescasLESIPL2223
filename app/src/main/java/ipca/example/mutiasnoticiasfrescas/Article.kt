package ipca.example.mutiasnoticiasfrescas

import org.json.JSONObject
import java.util.*

class Article {

    var title       : String? = null
    var content     : String? = null
    var publishedAt : Date? = null
    var urlToImage  : String? = null
    var url         : String? = null

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

    fun toJSON() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("title"      , title      )
        jsonObject.put("content"    , content    )
        jsonObject.put("publishedAt", publishedAt?.toServerFormat())
        jsonObject.put("urlToImage" , urlToImage )
        jsonObject.put("url"        , url        )
        return jsonObject
    }

    companion object{
        fun fromJSON(jsonObject: JSONObject) : Article {
            return Article(
                jsonObject.getString("title"      ),
                jsonObject.getString("content"    ),
                jsonObject.getString("publishedAt").parsePubDate(),
                jsonObject.getString("urlToImage" ),
                jsonObject.getString("url"        )
            )
        }
    }

}