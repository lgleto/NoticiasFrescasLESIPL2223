package ipca.example.noticiasfrescas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_article,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_share -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, article?.url)
                intent.type = "text/plain"
                val intentChooser = Intent.createChooser(intent, article?.title)
                startActivity(intentChooser)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}