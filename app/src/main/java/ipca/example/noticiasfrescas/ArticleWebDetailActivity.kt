package ipca.example.noticiasfrescas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import org.json.JSONObject

class ArticleWebDetailActivity : AppCompatActivity() {

    var article : Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_web_detail)

        article = Article.fromJSON(JSONObject(intent.getStringExtra(MainActivity.EXTRA_ARTICLE)))

        title = article?.title

        article?.url?.let {
            findViewById<WebView>(R.id.webView).loadUrl(it)
        }

    }
}