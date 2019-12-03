package com.vaibhavdhunde.app.hackenglish.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vaibhavdhunde.app.hackenglish.R
import com.vaibhavdhunde.app.hackenglish.databinding.FragmentLoginBinding
import com.vaibhavdhunde.app.hackenglish.util.EventObserver
import com.vaibhavdhunde.app.hackenglish.util.ViewModelFactory
import com.vaibhavdhunde.app.hackenglish.util.obtainViewModel
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
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.viewmodel?.registerUserEvent?.observe(viewLifecycleOwner, EventObserver {
            navigateToFragmentRegister()
        })
    }

    private fun navigateToFragmentRegister() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(action)
    }

}
