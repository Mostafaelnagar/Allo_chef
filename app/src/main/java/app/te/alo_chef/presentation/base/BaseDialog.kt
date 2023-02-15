package app.te.alo_chef.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseDialog<VB : ViewDataBinding> : DialogFragment() {

    private var _binding: VB? = null
    open val binding get() = _binding!!
    private var mRootView: View? = null
    private var hasInitializedRootView = false

//  override
//  fun getTheme(): Int {
//    return style.CustomDialogAnimation
//  }

    override
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView == null) {
            initViewBinding(inflater, container)
        }
        updateLayoutParams()
        return mRootView
    }

    private fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        mRootView = binding.root
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    override
    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!hasInitializedRootView) {
            setUpViews()

            hasInitializedRootView = true
        }
    }

    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun updateLayoutParams()
    open fun setUpViews() {}
}