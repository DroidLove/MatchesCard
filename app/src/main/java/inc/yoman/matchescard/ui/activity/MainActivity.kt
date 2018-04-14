package inc.yoman.matchescard.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.gson.Gson
import inc.yoman.matchescard.R
import inc.yoman.matchescard.model.MatchesProfileModel
import inc.yoman.matchescard.ui.adapter.MatchesProfileAdapter
import inc.yoman.matchescard.utils.AppUtils
import inc.yoman.matchescard.utils.Constants
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var listMatchesProfile: ArrayList<MatchesProfileModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        apiCall("10")
    }

    private fun init() {
        listMatchesProfile = ArrayList()

        val layout = LinearLayoutManager(this)
        recyclerView_matches_card.layoutManager = layout
    }

    private fun apiCall(result:String) {
        Flowable.fromCallable {
            val request = Request.Builder()
                    .url(Constants.url + "?results=" + result)
                    .build()

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                    .addInterceptor(logging).build()

            val response = client.newCall(request).execute()
            return@fromCallable response?.body()?.string()
        }
                .subscribeOn(Schedulers.io())
                .map { result -> mappingResult(result) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    progress_bar.visibility = View.GONE
                        val adapterMatchesProfile = MatchesProfileAdapter(result)
                        recyclerView_matches_card.adapter = adapterMatchesProfile
                }, { e -> AppUtils.logMe("Api Call error", e.toString())
                })
    }

    private fun mappingResult(result: Any): ArrayList<MatchesProfileModel> {
        val listMatchesProfile: ArrayList<MatchesProfileModel> = ArrayList()
        val jsonObj = JSONObject(result.toString())
        val jsonArray = JSONArray(jsonObj.get("results").toString())

        Flowable
                .range(0, jsonArray.length())
                .flatMap { idx -> getMatchesProfileObservable(idx, jsonArray) }
                .subscribe({ result ->
                    listMatchesProfile.add(result)
                }
                        , { e -> AppUtils.logMe("Response Mapping error", e.toString()) })

        return listMatchesProfile
    }

    private fun getMatchesProfileObservable(idx: Int, jsonArray: JSONArray?): Flowable<MatchesProfileModel> {
        val gsonObj: MatchesProfileModel = Gson().fromJson<MatchesProfileModel>(jsonArray?.get(idx)?.toString(), MatchesProfileModel::class.java)
        return Flowable.just(gsonObj)
    }
}