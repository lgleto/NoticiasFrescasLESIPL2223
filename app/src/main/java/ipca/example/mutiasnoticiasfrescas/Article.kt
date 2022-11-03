package ipca.example.mutiasnoticiasfrescas

import androidx.lifecycle.LiveData
import androidx.room.*
import org.json.JSONObject
import java.util.*

@Entity
class Article {

    var title       : String? = null
    var content     : String? = null
    var publishedAt : Date? = null
    var urlToImage  : String? = null
    @PrimaryKey
    var url         : String

    constructor(title: String?,
                content: String?,
                publishedAt: Date?,
                urlToImage: String?,
                url: String) {
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

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article")
    fun getAll() : LiveData<List<Article>>

    @Query("SELECT * FROM article WHERE url = :url")
    fun getByUrl(url: String): LiveData<Article?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article)

    @Delete
    fun delete(article: Article)

}