package com.vaibhavdhunde.app.hackenglish.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vaibhavdhunde.app.hackenglish.R
import com.vaibhavdhunde.app.hackenglish.databinding.FragmentRegisterBinding
import com.vaibhavdhunde.app.hackenglish.ui.MainActivity
import com.vaibhavdhunde.app.hackenglish.util.EventObserver
import com.vaibhavdhunde.app.hackenglish.util.ViewModelFactory
import com.vaibhavdhunde.app.hackenglish.util.closeSoftKeyboard
import com.vaibhavdhunde.app.hackenglish.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_register.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.newTask
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class RegisterFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: ViewModelFactory by instance()
    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_register, container, false)

        viewModel = obtainViewModel(RegisterViewModel::class.java, factory)

        binding = FragmentRegisterBinding.bind(rootView).apply {
            viewmodel = this@RegisterFragment.viewModel
            lifecycleOwner = this@RegisterFragment.viewLifecycleOwner
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setupNavigation()
    }

    private fun setupEvents() {
        binding.viewmodel?.showMessage?.observe(viewLifecycleOwner, EventObserver { message ->
            if (message is Int) {
                fragment_register?.snackbar(message)
            } else if (message is String) {
                fragment_register?.snackbar(message)
            }
        })

        binding.viewmodel?.closeKeyboardEvent?.observe(viewLifecycleOwner, EventObserver {
            closeSoftKeyboard()
        })

        input_password?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.viewmodel?.registerUser()
                true
            } else {
                false
            }
        }
    }

    private fun setupNavigation() {
        binding.viewmodel?.loginUserEvent?.observe(viewLifecycleOwner, EventObserver {
            navigateToFragmentLogin()
        })

        binding.viewmodel?.userRegisteredEvent?.observe(viewLifecycleOwner, EventObserver {
            navigateToHome()
        })
    }

    private fun navigateToFragmentLogin() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    private fun navigateToHome() {
        toast(getString(R.string.msg_welcome))
        startActivity(intentFor<MainActivity>().newTask().clearTask())
    }

}
