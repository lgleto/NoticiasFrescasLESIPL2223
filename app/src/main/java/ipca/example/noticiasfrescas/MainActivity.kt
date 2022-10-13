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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    //https://newsapi.org/v2/top-headlines?country=pt&apiKey=1765f87e4ebc40229e80fd0f75b6416c

    // model
    var articles = arrayListOf<Article>()
    val adapter = ArticlesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchData()


        val listViewArticle = findViewById<ListView>(R.id.listViewArticles)
        listViewArticle.adapter = adapter


    }

    fun fetchData(){
        GlobalScope.launch (Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://newsapi.org/v2/top-headlines?country=pt&apiKey=1765f87e4ebc40229e80fd0f75b6416c")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val result =  response.body!!.string()
                Log.d(TAG, result)

                val jsonObject = JSONObject(result)
                if (jsonObject.getString("status") == "ok"){
                    val articlesJSONArray = jsonObject.getJSONArray("articles")
                    for( index in 0 until articlesJSONArray.length()){
                        val articleJSONObject = articlesJSONArray.getJSONObject(index)
                        val article = Article.fromJSON(articleJSONObject)
                        articles.add(article)
                    }
                    GlobalScope.launch (Dispatchers.Main){
                        adapter.notifyDataSetChanged()
                    }

                }
            }
        }

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
            textViewArticleDate.text = article.publishedAt.toString()

            rowView.setOnClickListener {
                Log.d(TAG, "article:${article.title}")
                val intent = Intent(this@MainActivity, ArticleDetailActivity::class.java)
                intent.putExtra("title", article.title)
                intent.putExtra("body",article.content)
                startActivity(intent)
            }

            return rowView
        }

    }

    companion object {
        const val TAG = "MainActivity"
    }

}