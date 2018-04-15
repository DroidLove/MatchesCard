package inc.yoman.matchescard.mvp.model

import inc.yoman.matchescard.mvp.presenter.MatchesProfilePresenter
import inc.yoman.matchescard.utils.Constants
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Using OkHttp for the Network calls
 */

class MatchesProfileModel(private val presenter: MatchesProfilePresenter) {

    fun getMatchesProfileListing(listCount: String) {

        presenter.onResponse(
        Flowable.fromCallable {
            val request = Request.Builder()
                    .url(Constants.url + "?results=" + listCount)
                    .build()

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                    .readTimeout(5, TimeUnit.MINUTES)
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .addInterceptor(logging)
                    .build()

            val response = client.newCall(request).execute()
            return@fromCallable response?.body()?.string()
        }
                .subscribeOn(Schedulers.io())
                .map { resultMap -> presenter.mappingResult(resultMap) }
        )
    }
}
