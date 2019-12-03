package com.vaibhavdhunde.app.hackenglish.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vaibhavdhunde.app.hackenglish.R
import com.vaibhavdhunde.app.hackenglish.databinding.FragmentLoginBinding
import com.vaibhavdhunde.app.hackenglish.ui.MainActivity
import com.vaibhavdhunde.app.hackenglish.util.EventObserver
import com.vaibhavdhunde.app.hackenglish.util.ViewModelFactory
import com.vaibhavdhunde.app.hackenglish.util.closeSoftKeyboard
import com.vaibhavdhunde.app.hackenglish.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.newTask
import org.jetbrains.anko.support.v4.intentFor
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class LoginFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: ViewModelFactory by instance()
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)

        viewModel = obtainViewModel(LoginViewModel::class.java, factory)

        binding = FragmentLoginBinding.bind(rootView).apply {
            viewmodel = this@LoginFragment.viewModel
            lifecycleOwner = this@LoginFragment.viewLifecycleOwner
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
                fragment_login?.snackbar(message)
            } else if (message is String) {
                fragment_login?.snackbar(message)
            }
        })

        binding.viewmodel?.closeKeyboardEvent?.observe(viewLifecycleOwner, EventObserver {
            closeSoftKeyboard()
        })

        input_password?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.viewmodel?.loginUser()
                true
            } else {
                false
            }
        }
    }

    private fun setupNavigation() {
        binding.viewmodel?.registerUserEvent?.observe(viewLifecycleOwner, EventObserver {
            navigateToFragmentRegister()
        })

        binding.viewmodel?.userLoginEvent?.observe(viewLifecycleOwner, EventObserver {
            navigateToHome()
        })
    }

    private fun navigateToFragmentRegister() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(action)
    }

    private fun navigateToHome() {
        startActivity(intentFor<MainActivity>().newTask().clearTask())
    }

}
