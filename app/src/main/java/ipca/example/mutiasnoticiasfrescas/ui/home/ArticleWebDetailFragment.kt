package ipca.example.mutiasnoticiasfrescas.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ipca.example.mutiasnoticiasfrescas.AppDatabase
import ipca.example.mutiasnoticiasfrescas.Article
import ipca.example.mutiasnoticiasfrescas.R
import ipca.example.mutiasnoticiasfrescas.databinding.FragmentArticleWebDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject


class ArticleWebDetailFragment : Fragment() {

    var article : Article? = null
    var articleOnDb : Article? = null

    private var _binding: FragmentArticleWebDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            article = Article.fromJSON(JSONObject (it.getString(ARTICLE_JSON_STRING)))
        }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleWebDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //title = article?.title

        article?.url?.let {
            binding.webView.loadUrl(it)


            AppDatabase.getDatabase(requireContext())
                ?.articleDao()
                ?.getByUrl(it)
                ?.observe(viewLifecycleOwner, Observer { article ->
                    articleOnDb = article
                    if (article == null){
                        menuItem?.icon = resources.getDrawable(R.drawable.ic_baseline_bookmark_border_24)
                    }else{
                        menuItem?.icon = resources.getDrawable(R.drawable.ic_baseline_bookmark_24)
                    }
                })

        }
        setHasOptionsMenu(true)

    }

    var menuItem : MenuItem? = null

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_article, menu)
        menuItem = menu.getItem(1)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, article?.url)
                intent.type = "text/plain"
                val intentChooser = Intent.createChooser(intent, article?.title)
                startActivity(intentChooser)
                return true
            }
            R.id.action_save -> {
                article?.let {
                    lifecycleScope.launch(Dispatchers.IO) {
                        if (articleOnDb == null) {
                            AppDatabase.getDatabase(requireContext())?.articleDao()?.insert(it)
                        }else{
                            AppDatabase.getDatabase(requireContext())?.articleDao()?.delete(it)
                        }
                    }
                }
            }
            android.R.id.home -> {
                findNavController().popBackStack()
            }
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        const val ARTICLE_JSON_STRING = "article_json_string"

        @JvmStatic
        fun newInstance(articleJSONString: String) =
            ArticleWebDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARTICLE_JSON_STRING, articleJSONString)
                }
            }
    }
}