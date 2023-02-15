package app.te.alo_chef.appTutorial

import android.app.Activity
import android.content.res.ColorStateList
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import app.te.alo_chef.domain.intro.entity.AppTutorialModel
import com.google.android.material.button.MaterialButton
import app.te.alo_chef.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.delay

class AppTutorialHelper private constructor(builder: Builder) : LifecycleObserver {

    companion object {
        const val SLIDER_DELAY: Long = 2500
    }

    private var activity: Activity = builder.activity
    private var lifecycle: Lifecycle = builder.lifecycle

    private var tutorialModelData: List<AppTutorialModel>
    private var sliderContainer: FrameLayout? = null

    @IdRes
    private var sliderContainerResourceID: Int = 0

    @ColorRes
    private var titleColor: Int = R.color.colorDarkGray

    @ColorRes
    private var contentColor: Int = R.color.colorGray

    @ColorRes
    private var activeIndicatorColor: Int = R.color.colorBlack

    @ColorRes
    private var inActiveIndicatorColor: Int = R.color.colorWhite
    private var autoScrolling: Boolean = false

    @ColorRes
    private var btnNextTextColor: Int = R.color.colorDarkGray

    @ColorRes
    private var btnPreviousTextColor: Int = R.color.white

    @ColorRes
    private var btnOpenAppTextColor: Int = R.color.colorBlack

    private var btnNextTextBackground: Int? = null
    private var btnPreviousTextBackground: Int? = null
    private var btnNextIcon: Int? = null

    private var currentItem = 0
    private lateinit var tutorialViewsContainer: RelativeLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var indicatorsContainer: LinearLayout
    private var viewPagerIndicators: ArrayList<TextView>
    private lateinit var btnNext: FloatingActionButton
    private lateinit var btnPrevious: TextView
    private lateinit var btnOpenApp: MaterialButton

    //    MaterialButton(ContextThemeWrapper(activity, R.style.nextButtonTheme))
    private val constraintLayout = ConstraintLayout(activity)
    private val constraintSet = ConstraintSet()
    private lateinit var tutorialAdapter: TutorialAdapter
    private lateinit var pageChangedCallback: ViewPager2.OnPageChangeCallback
    private var firstTimeCallListener: Boolean = true

    private var skipTutorialClick: (() -> Unit)? = null

    init {
        tutorialModelData = builder.tutorialModelData
        viewPagerIndicators = ArrayList(tutorialModelData.size)
        sliderContainer = builder.sliderContainer
        sliderContainerResourceID = builder.sliderContainerResourceID
        titleColor = builder.titleColor
        contentColor = builder.contentColor
        activeIndicatorColor = builder.activeIndicatorColor
        inActiveIndicatorColor = builder.inActiveIndicatorColor
        autoScrolling = builder.autoScrolling
        btnNextTextColor = builder.btnNextTextColor
        btnPreviousTextColor = builder.btnPreviousTextColor
        btnOpenAppTextColor = builder.btnOpenAppTextColor
        btnNextTextBackground = builder.btnNextTextBackground
        btnPreviousTextBackground = builder.btnPreviousTextBackground
        btnNextIcon = builder.btnNextIcon
        skipTutorialClick = builder.skipTutorialClick
        setUpSliderContainer()

        setUpTutorialViewsContainer()
        setUpConstraintLayout()
        setUpOpenAppButton()
        setViewPagerIndicator()

        addViewPagerIndicator()

        addViewPager()

        setUpViewPagerAdapter()

        setUpViewPagerListener()

        setUpAutoScrolling()

        handleNextButtonClickListener()
        handlePreviousButtonClickListener()
        handleOpenAppButtonClickListener()
        lifecycle.addObserver(this)
    }

    private fun setUpSliderContainer() {
        if (sliderContainerResourceID != 0 && sliderContainer == null) {
            sliderContainer = activity.findViewById(sliderContainerResourceID)
        }
        sliderContainer?.removeAllViewsInLayout()
    }

    private fun setUpTutorialViewsContainer() {
        tutorialViewsContainer = RelativeLayout(activity)

        tutorialViewsContainer.apply {
            val lp = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            layoutParams = lp

        }

        sliderContainer?.addView(tutorialViewsContainer)
    }

    private fun setUpConstraintLayout() {
        constraintLayout.apply {
            val lp = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
            lp.setMargins(80, 20, 80, 0)
            layoutParams = lp
            id = View.generateViewId()
            constraintSet.clone(constraintLayout)
            setUpNextButton()
            setUpPreviousButton()
        }
        setUpConstraintSet()
        constraintSet.applyTo(constraintLayout)
        tutorialViewsContainer.addView(constraintLayout)
    }

    private fun setUpNextButton() {
        btnNext = FloatingActionButton(activity)
        btnNext.apply {
            id = View.generateViewId()
            btnNextTextBackground?.let {
                backgroundTintList =
                    ColorStateList.valueOf(activity.resources.getColor(it, null))
            }
            setImageResource(R.drawable.ic_arrow_forward)
            imageTintList = ColorStateList.valueOf(activity.resources.getColor(R.color.white, null))
            size = FloatingActionButton.SIZE_MINI
        }
        constraintLayout.addView(btnNext)
    }

    private fun setUpPreviousButton() {
        btnPrevious = TextView(activity.applicationContext)
        btnPrevious.apply {
            id = View.generateViewId()
            text = activity.resources.getString(R.string.skip)
            textSize = activity.resources.getDimension(R.dimen._6ssp)
            setTextColor(activity.resources.getColor(R.color.colorBlack, null))
        }
        constraintLayout.addView(btnPrevious)
    }

    private fun setUpOpenAppButton() {
        btnOpenApp = MaterialButton(activity)

        btnOpenApp.apply {
            val lp = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
            lp.setMargins(50, 16, 50, 16)
            layoutParams = lp
            id = View.generateViewId()
            text = activity.resources.getString(R.string.open_app)
            btnNextTextBackground?.let {
                backgroundTintList = ColorStateList.valueOf(activity.resources.getColor(it, null))
            }
            cornerRadius = 50
        }
        tutorialViewsContainer.addView(btnOpenApp)
        btnOpenApp.visibility = View.INVISIBLE
    }

    private fun setUpConstraintSet() {
        constraintSet.constrainWidth(btnNext.id, ConstraintSet.WRAP_CONTENT)
        constraintSet.constrainHeight(btnNext.id, ConstraintSet.WRAP_CONTENT)
        constraintSet.connect(
            btnNext.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            btnNext.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
        )
        constraintSet.connect(
            btnNext.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END,
        )

        //btnPrevious
        constraintSet.constrainWidth(btnPrevious.id, ConstraintSet.WRAP_CONTENT)
        constraintSet.constrainHeight(btnPrevious.id, ConstraintSet.WRAP_CONTENT)

        constraintSet.connect(
            btnPrevious.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP,
        )
        constraintSet.connect(
            btnPrevious.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
        )
        constraintSet.connect(
            btnPrevious.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )

    }


    private fun setViewPagerIndicator() {
        if (!::indicatorsContainer.isInitialized) {
            indicatorsContainer = LinearLayout(activity)
        }
        indicatorsContainer.removeAllViews()

        for (i in tutorialModelData.indices) {
            val tv = TextView(activity)
            tv.apply {
                val lp = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                lp.setMargins(5, 0, 5, 7)
                layoutParams = lp
                setText(R.string.dot)
                textSize = if (i == currentItem) 45f else 28f
                includeFontPadding = false
                setTextColor(
                    if (i == currentItem)
                        ContextCompat.getColor(context, activeIndicatorColor)
                    else
                        ContextCompat.getColor(context, inActiveIndicatorColor)
                )
            }

            viewPagerIndicators.add(tv)
            indicatorsContainer.addView(tv)
            indicatorsContainer.bringToFront()
        }
    }

    private fun addViewPagerIndicator() {
        indicatorsContainer.apply {
            val lp = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            lp.addRule(RelativeLayout.ABOVE, btnOpenApp.id)

            layoutParams = lp
            orientation = LinearLayout.HORIZONTAL
            id = View.generateViewId()
            gravity = Gravity.CENTER
        }

        tutorialViewsContainer.addView(indicatorsContainer)
    }

    private fun addViewPager() {
        viewPager = ViewPager2(activity)

        viewPager.apply {
            val lp = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            lp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
            lp.addRule(RelativeLayout.ABOVE, indicatorsContainer.id)
            layoutParams = lp
            clipChildren = false
            clipToPadding = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        tutorialViewsContainer.addView(viewPager)
    }

    private fun setUpViewPagerAdapter() {
        tutorialAdapter = TutorialAdapter(
            ContextCompat.getColor(activity, titleColor),
            ContextCompat.getColor(activity, contentColor)
        )

        viewPager.adapter = tutorialAdapter.apply { submitList(tutorialModelData) }
    }

    private fun setUpViewPagerListener() {
        pageChangedCallback = object : ViewPager2.OnPageChangeCallback() {
            override
            fun onPageSelected(position: Int) {
                currentItem = position

                if (firstTimeCallListener) {
                    firstTimeCallListener = false
                    return
                }
                setViewPagerIndicator()

                if (position == tutorialModelData.size - 1) {
                    btnPrevious.visibility = View.GONE
                    btnNext.visibility = View.GONE
                    btnOpenApp.visibility = View.VISIBLE
                } else {
                    btnPrevious.visibility = View.VISIBLE
                    btnNext.visibility = View.VISIBLE
                    btnOpenApp.visibility = View.INVISIBLE
                }

            }
        }
    }

    private fun setUpAutoScrolling() {
        if (autoScrolling) {
            lifecycle.coroutineScope.launchWhenResumed {
                while (true) {
                    delay(SLIDER_DELAY)

                    if (tutorialModelData.size - 1 == currentItem) {
                        currentItem = 0
                    } else {
                        currentItem++
                    }

                    if (currentItem == 0) {
                        viewPager.setCurrentItem(currentItem, false)
                    } else {
                        viewPager.setCurrentItem(currentItem, true)
                    }
                }
            }
        }
    }

    private fun handleNextButtonClickListener() {
        btnNext.setOnClickListener {
            if (tutorialModelData.size - 1 == currentItem) {
                skipTutorialClick?.invoke()
            } else {
                currentItem++
            }

            viewPager.setCurrentItem(currentItem, true)
        }
    }

    private fun handlePreviousButtonClickListener() {
        btnPrevious.setOnClickListener {
            skipTutorialClick?.invoke()
        }
    }

    private fun handleOpenAppButtonClickListener() {
        btnOpenApp.setOnClickListener {
            skipTutorialClick?.invoke()
        }
    }

    class Builder(internal var activity: Activity, internal var lifecycle: Lifecycle) {

        internal lateinit var tutorialModelData: List<AppTutorialModel>
        internal var sliderContainer: FrameLayout? = null

        @IdRes
        internal var sliderContainerResourceID: Int = 0

        @ColorRes
        internal var titleColor: Int = R.color.colorDarkGray

        @ColorRes
        internal var contentColor: Int = R.color.colorGray

        @ColorRes
        internal var activeIndicatorColor: Int = R.color.colorBlack

        @ColorRes
        internal var inActiveIndicatorColor: Int = R.color.colorWhite

        internal var autoScrolling: Boolean = false

        @ColorRes
        internal var btnNextTextColor: Int = R.color.colorDarkGray

        @ColorRes
        internal var btnPreviousTextColor: Int = R.color.colorBlack

        @ColorRes
        internal var btnOpenAppTextColor: Int = R.color.colorBlack

        internal var btnNextTextBackground: Int? = null
        internal var btnPreviousTextBackground: Int? = null
        internal var btnNextIcon: Int? = null

        internal var skipTutorialClick: (() -> Unit)? = null

        fun setTutorialData(tutorialModelData: List<AppTutorialModel>): Builder {
            this.tutorialModelData = tutorialModelData
            return this
        }

        fun setSliderContainer(sliderContainer: FrameLayout): Builder {
            this.sliderContainer = sliderContainer
            return this
        }

        fun setSliderContainerResourceID(@IdRes sliderContainerResourceID: Int): Builder {
            this.sliderContainerResourceID = sliderContainerResourceID
            return this
        }

        fun setTitleColor(@ColorRes titleColor: Int): Builder {
            this.titleColor = titleColor
            return this
        }

        fun setContentColor(@ColorRes contentColor: Int): Builder {
            this.contentColor = contentColor
            return this
        }

        fun setActiveIndicatorColor(@ColorRes activeIndicatorColor: Int): Builder {
            this.activeIndicatorColor = activeIndicatorColor
            return this
        }

        fun setInActiveIndicatorColor(@ColorRes inActiveIndicatorColor: Int): Builder {
            this.inActiveIndicatorColor = inActiveIndicatorColor
            return this
        }

        fun setAutoScrolling(autoScrolling: Boolean): Builder {
            this.autoScrolling = autoScrolling
            return this
        }

        fun setNextButtonTextColor(@ColorRes btnNextTextColor: Int): Builder {
            this.btnNextTextColor = btnNextTextColor
            return this
        }

        fun setPreviousTextColor(@ColorRes btnPreviousTextColor: Int): Builder {
            this.btnPreviousTextColor = btnPreviousTextColor
            return this
        }

        fun setOpenAppTextColor(@ColorRes btnOpenAppTextColor: Int): Builder {
            this.btnOpenAppTextColor = btnOpenAppTextColor
            return this
        }

        fun setNextButtonBackground(btnNextTextBackground: Int): Builder {
            this.btnNextTextBackground = btnNextTextBackground
            return this
        }

        fun setBtnPreviousTextBackground(btnPreviousTextBackground: Int): Builder {
            this.btnPreviousTextBackground = btnPreviousTextBackground
            return this
        }

        fun setNextButtonIcon(btnNextIcon: Int): Builder {
            this.btnNextIcon = btnNextIcon
            return this
        }

        fun setSkipTutorialClick(skipTutorialClick: (() -> Unit)): Builder {
            this.skipTutorialClick = skipTutorialClick
            return this
        }

        fun setSkipVisible(visible: (() -> Unit)): Builder {
            this.skipTutorialClick = skipTutorialClick
            return this
        }

        fun create(): AppTutorialHelper {
            return AppTutorialHelper(this)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {
        unregisterViewPagerCallback()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        registerViewPagerCallback()
    }

    private fun registerViewPagerCallback() {
        viewPager.registerOnPageChangeCallback(pageChangedCallback)
    }

    private fun unregisterViewPagerCallback() {
        viewPager.unregisterOnPageChangeCallback(pageChangedCallback)
    }
}