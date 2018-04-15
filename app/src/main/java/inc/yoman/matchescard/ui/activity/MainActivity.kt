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

/**
 * Using the MVP pattern to organize the code
 * For API calling, we used OkHttp library with RxJava2 for asynchronous calls and
 * later updating the UI with Help of RxAndroid2
 */
class MainActivity : AppCompatActivity(), BaseView, MatchesProfileAdapter.MatchesProfileRefreshInterface {

    lateinit var matchesPresenter: MatchesProfilePresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        clickListeners()
    }

    private fun init() {
        val layout = LinearLayoutManager(this)
        recyclerView_matches_card.layoutManager = layout

        val spacing = resources.getDimensionPixelOffset(R.dimen.default_spacing_small)
        recyclerView_matches_card.addItemDecoration(ItemOffsetDecoration(spacing))

        matchesPresenter = MatchesProfilePresenterImpl(this)
        apiCallFromPresenter()
    }

    private fun clickListeners() {
        imageVIew_refresh_listing.setOnClickListener { apiCallFromPresenter() }
    }

    private fun apiCallFromPresenter() {
        if(AppUtils.isOnline(this)) {
            progress_bar.visibility = View.VISIBLE
            matchesPresenter.getMatchesProfileListing("10")
        }
        else {
            AppUtils.toastMessage(this, getString(R.string.no_internet_connection))
            progress_bar.visibility = View.GONE
            linearLayout_content.visibility = View.VISIBLE
            textView_info.text = getString(R.string.please_try_again)
        }
    }

    private fun populateRecyclerView(result: MutableList<MatchesProfileResponseModel>) {
        val adapterMatchesProfile = MatchesProfileAdapter(this, this, result)
        recyclerView_matches_card.adapter = adapterMatchesProfile

        val context = recyclerView_matches_card.context

        val controller: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(context,  R.anim.layout_animation_from_bottom)

        recyclerView_matches_card.layoutAnimation = controller
        recyclerView_matches_card.adapter.notifyDataSetChanged()
        recyclerView_matches_card.scheduleLayoutAnimation()
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
        linearLayout_content.visibility = View.GONE
    }

    override fun onResponseSuccess(mutableListFlowable: Flowable<MutableList<MatchesProfileResponseModel>>) {
        mutableListFlowable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                {it ->  it?.let {
                    progress_bar.visibility = View.GONE
                    populateRecyclerView(it)
                }}
        )
    }

    override fun onListingEmpty() {
        linearLayout_content.visibility = View.VISIBLE
        textView_info.text = getString(R.string.refresh_to_see_more)
    }

    override fun onResponseFailure(msg: String) {
        linearLayout_content.visibility = View.VISIBLE
        textView_info.text = getString(R.string.general_error_message)
    }
}