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
import androidx.lifecycle.lifecycleScope


class MainActivity : AppCompatActivity() {

    //https://newsapi.org/v2/top-headlines?country=pt&apiKey=1765f87e4ebc40229e80fd0f75b6416c

    // model
    var articles = arrayListOf<Article>()
    val adapter = ArticlesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Backend.fetchTopHeadlines(lifecycleScope, "pt","sports"){
            articles = it
            adapter.notifyDataSetChanged()
        }

        val listViewArticle = findViewById<ListView>(R.id.listViewArticles)
        listViewArticle.adapter = adapter
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
            textViewArticleBody.text = article.content
            textViewArticleDate.text = article.publishedAt?.toShort()

            article.urlToImage?.let {
                Backend.fetchImage(lifecycleScope, it){ bitmap ->
                    imageViewArticle.setImageBitmap(bitmap)
                }
            }


            rowView.setOnClickListener {
                Log.d(TAG, "article:${article.title}")
                //val intent = Intent(this@MainActivity, ArticleDetailActivity::class.java)
                //intent.putExtra("title", article.title)
                //intent.putExtra("body",article.content)
                //startActivity(intent)

                val intent = Intent(this@MainActivity, ArticleWebDetailActivity::class.java)
                intent.putExtra(EXTRA_ARTICLE, article.toJSON().toString())
                startActivity(intent)
            }

            return rowView
        }

    }

    companion object {
        const val TAG = "MainActivity"
        const val EXTRA_ARTICLE = "extra_article"
    }

}