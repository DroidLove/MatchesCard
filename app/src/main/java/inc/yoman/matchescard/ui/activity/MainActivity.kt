package inc.yoman.matchescard.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import inc.yoman.matchescard.R
import inc.yoman.matchescard.helper.MatchesProfileResponseModel
import inc.yoman.matchescard.mvp.presenterImpl.MatchesProfilePresenterImpl
import inc.yoman.matchescard.mvp.view.BaseView
import inc.yoman.matchescard.ui.adapter.MatchesProfileAdapter
import inc.yoman.matchescard.utils.AppUtils
import inc.yoman.matchescard.utils.customview.ItemOffsetDecoration
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BaseView {

    lateinit var matchesPresenter: MatchesProfilePresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        val layout = LinearLayoutManager(this)
        recyclerView_matches_card.layoutManager = layout

        matchesPresenter = MatchesProfilePresenterImpl(this)
        matchesPresenter.getMatchesProfileListing("10")
    }

    private fun populateRecyclerView(result: MutableList<MatchesProfileResponseModel>) {
        val adapterMatchesProfile = MatchesProfileAdapter(this,result)
        recyclerView_matches_card.adapter = adapterMatchesProfile

        val spacing = resources.getDimensionPixelOffset(R.dimen.default_spacing_small)
        recyclerView_matches_card.addItemDecoration(ItemOffsetDecoration(spacing))

        val context = recyclerView_matches_card.context

        val controller: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(context,  R.anim.layout_animation_from_bottom)

        recyclerView_matches_card.layoutAnimation = controller
        recyclerView_matches_card.adapter.notifyDataSetChanged()
        recyclerView_matches_card.scheduleLayoutAnimation()
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun onResponseSuccess(mutableListFlowable: Flowable<MutableList<MatchesProfileResponseModel>>) {
        mutableListFlowable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                {it ->  it?.let {
                    progress_bar.visibility = View.GONE
                    populateRecyclerView(it)
                    AppUtils.toastMessage(this,"Success")
                }}
        )
    }

    override fun onResponseFailure(msg: String) {
    }

    override fun onErrorResponse(data: Any, apiName: String) {
    }

    override fun switchVisibility(type: Int) {
    }

    override fun switchVisibility(type: Int, msg: String) {
    }

    override fun onConnectionLost(e: Throwable) {
    }
}