package inc.yoman.matchescard.mvp.view

import inc.yoman.matchescard.helper.MatchesProfileResponseModel
import io.reactivex.Flowable

/**
 */
interface BaseView {
    fun showProgress()

    fun hideProgress()

    fun onResponseSuccess(mutableListFlowable:  Flowable<MutableList<MatchesProfileResponseModel>>)

    fun onResponseFailure(msg: String)

    fun onErrorResponse(data: Any, apiName: String)

    fun switchVisibility(type: Int)

    fun switchVisibility(type: Int, msg: String)

    fun onConnectionLost(e: Throwable)
}
