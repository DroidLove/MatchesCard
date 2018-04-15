package inc.yoman.matchescard.mvp.presenter

import inc.yoman.matchescard.helper.MatchesProfileResponseModel
import io.reactivex.Flowable

interface MatchesProfilePresenter {
    fun getMatchesProfileListing(listCount: String)

    fun mappingResult(result: Any): MutableList<MatchesProfileResponseModel>

    fun onResponse(responseJson: Flowable<MutableList<MatchesProfileResponseModel>>)
}