package github.idmeetrious.githubclient.presentation.ui.splash

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.fragment.app.DialogFragment
import github.idmeetrious.githubclient.R
import github.idmeetrious.githubclient.databinding.DialogFragmentSplashBinding

private const val TAG = "SplashDialogFragment"

class SplashDialogFragment : DialogFragment() {
    private var _binding: DialogFragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun getTheme(): Int {
        return R.style.Theme_DialogFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogFragmentSplashBinding.inflate(inflater, container, false)
        val rootView = binding.root
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startAnimation()
    }

    private fun startAnimation() {
        val fadeOutAnimator = ValueAnimator.ofInt(200, 0)
            .apply {
                addUpdateListener {
                    val value = it.animatedValue as Int
                    binding.splashLogoIv.imageAlpha = value
                }
                duration = 1000
                doOnEnd {
                    dismiss()
                }
            }

        val fadeInAnimator = ValueAnimator.ofInt(0, 200)
            .apply {
                addUpdateListener {
                    val value = it.animatedValue as Int
                    binding.splashLogoIv.imageAlpha = value
                }
                duration = 2000
                doOnEnd {
                    fadeOutAnimator.start()
                }
            }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}