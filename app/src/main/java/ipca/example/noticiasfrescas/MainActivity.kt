package ipca.example.noticiasfrescas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {

    // model
    var articles = arrayListOf<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        articles.add(Article("safasf","afwaefaf", Date(),null))
        articles.add(Article("sdfwqwdfs","afwaefaf", Date(),null))

        val listViewArticle = findViewById<ListView>(R.id.listViewArticles)
        listViewArticle.adapter = ArticlesAdapter()
    }

    inner class ArticlesAdapter : BaseAdapter() {
        override fun getCount(): Int {
           return articles.size
        }

        override fun getItem(positon: Int): Any {
            return articles[positon]
        }

        override fun getItemId(positon: Int): Long {
            return 0L
        }

        override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_article,parent, false)
            val textViewArticleTitle = rowView.findViewById<TextView>(R.id.textViewArticleTitle)
            val textViewArticleBody = rowView.findViewById<TextView>(R.id.textViewArticleBody)
            val textViewArticleDate = rowView.findViewById<TextView>(R.id.textViewArticleDate)
            val imageViewArticle = rowView.findViewById<ImageView>(R.id.imageViewArticle)

            val article = articles[position]
            textViewArticleTitle.text = article.title
            textViewArticleBody.text = article.body
            textViewArticleDate.text = article.pubDate.toString()

            rowView.setOnClickListener {
                Log.d("MainActivity", "article:${article.title}")
                val intent = Intent(this@MainActivity, ArticleDetailActivity::class.java)
                intent.putExtra("title", article.title)
                intent.putExtra("body",article.body)
                startActivity(intent)
            }

            return rowView
        }

    }

}