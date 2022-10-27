package ipca.example.noticiasfrescas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.TextView

class ArticleDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        val articleTitle = intent.getStringExtra("title")
        val body = intent.getStringExtra("body")

        findViewById<TextView>(R.id.textViewTitle).text = articleTitle
        findViewById<TextView>(R.id.textViewDetail).text = body

    }
}