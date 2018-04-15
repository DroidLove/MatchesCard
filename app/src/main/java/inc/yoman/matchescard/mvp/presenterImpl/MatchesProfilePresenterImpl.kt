package inc.yoman.matchescard.mvp.presenterImpl

import com.google.gson.Gson
import inc.yoman.matchescard.helper.MatchesProfileResponseModel
import inc.yoman.matchescard.mvp.model.MatchesProfileModel
import inc.yoman.matchescard.mvp.presenter.MatchesProfilePresenter
import inc.yoman.matchescard.mvp.view.BaseView
import inc.yoman.matchescard.utils.AppUtils
import io.reactivex.Flowable
import org.json.JSONArray
import org.json.JSONObject

/**
 * Getting the response using the Flowable(Observable) by using
 * appropriate operators
 */

class MatchesProfilePresenterImpl(private val baseView: BaseView) : MatchesProfilePresenter {

    private val matchesProfileModel: MatchesProfileModel

    init {
        matchesProfileModel = MatchesProfileModel(this)
    }

    override fun onResponse(responseJson: Flowable<MutableList<MatchesProfileResponseModel>>) {
         baseView.hideProgress()
         baseView.onResponseSuccess(responseJson)
    }

    override fun mappingResult(result: Any): MutableList<MatchesProfileResponseModel> {
        val listMatchesProfile: MutableList<MatchesProfileResponseModel> = ArrayList()
        val jsonObj = JSONObject(result.toString())
        val jsonArray = JSONArray(jsonObj.get("results").toString())

        Flowable
                .range(0, jsonArray.length())
                .flatMap { idx -> getMatchesProfileObservable(idx, jsonArray) }
                .subscribe({ result ->
                    listMatchesProfile.add(result)
                }
                        , { e -> AppUtils.logMe("Response Mapping error", e.toString())
                    baseView.onResponseFailure(e.toString())
                })

        return listMatchesProfile
    }

    override fun getMatchesProfileListing(listCount: String) {
        matchesProfileModel.getMatchesProfileListing(listCount)
    }

    private fun getMatchesProfileObservable(idx: Int, jsonArray: JSONArray?): Flowable<MatchesProfileResponseModel> {
        val gsonObj: MatchesProfileResponseModel = Gson().fromJson<MatchesProfileResponseModel>(jsonArray?.get(idx)?.toString(), MatchesProfileResponseModel::class.java)
        return Flowable.just(gsonObj)
    }
}
