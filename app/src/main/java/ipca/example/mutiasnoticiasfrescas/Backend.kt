package ipca.example.mutiasnoticiasfrescas

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

object Backend {

    private const val API_KEY = "1765f87e4ebc40229e80fd0f75b6416c"

    private val client = OkHttpClient()

    fun fetchTopHeadlines(scope: CoroutineScope,
                          country: String,
                          category: String,
                          callback: (ArrayList<Article>)->Unit )   {
        scope.launch (Dispatchers.IO) {

            val request = Request.Builder()
                .url("https://newsapi.org/v2/top-headlines?country=$country&category=$category&apiKey=$API_KEY")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val result =  response.body!!.string()
                Log.d(MainActivity.TAG, result)

                val jsonObject = JSONObject(result)
                if (jsonObject.getString("status") == "ok"){
                    val articles = arrayListOf<Article>()
                    val articlesJSONArray = jsonObject.getJSONArray("articles")
                    for( index in 0 until articlesJSONArray.length()){
                        val articleJSONObject = articlesJSONArray.getJSONObject(index)
                        val article = Article.fromJSON(articleJSONObject)
                        articles.add(article)
                    }
                    scope.launch (Dispatchers.Main){
                        callback(articles)
                    }
                }
            }
        }
    }

    fun fetchImage(scope: CoroutineScope,
                   url: String,
                   callback: (Bitmap?)->Unit) {
        scope.launch{
            withContext(Dispatchers.IO){

                if (url.contains("http")) {
                    val request = Request.Builder()
                        .url(url)
                        .build()
                    client.newCall(request).execute().use { response ->
                        val input = response.body!!.byteStream()
                        val bitmap = BitmapFactory.decodeStream(input)
                        withContext(Dispatchers.Main) {
                            callback(bitmap)
                        }
                    }
                }
            }
        }
    }

}