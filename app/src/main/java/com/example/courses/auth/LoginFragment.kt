package com.example.courses.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.courses.R
import com.example.courses.databinding.FragmentLoginBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        setupFormValidation()
        setupInputs()
        setupClicks()
        observeState()

    }

    private fun setupInputs() {

        val noCyrillicFilter = InputFilter { source, _, _, _, _, _ ->
            if (source.any { it in 'А'..'я' || it == 'Ё' || it == 'ё' }) {
                ""
            } else null
        }
        binding.etEmail.filters = arrayOf(noCyrillicFilter)

        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChanged(text?.toString().orEmpty())
        }

        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordChanged(text?.toString().orEmpty())
        }
    }
    private fun setupFormValidation() {
        val emailField = binding.etEmail
        val passwordField = binding.etPassword
        val button = binding.btnLogin

        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

        fun validate() {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            val isValid = emailRegex.matches(email) && password.isNotEmpty()

            button.isEnabled = isValid

            if (isValid) {
                button.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_button_primary)
            } else {
                button.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_button_disabled)
            }
        }

        emailField.addTextChangedListener { validate() }
        passwordField.addTextChangedListener { validate() }
    }

    private fun setupClicks() {
        binding.btnLogin.setOnClickListener {
            // К этому моменту кнопка активна только при валидных данных
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        binding.btnVk.setOnClickListener {
            openUrl("https://vk.com/")
        }

        binding.btnOk.setOnClickListener {
            openUrl("https://ok.ru/")
        }
        // tvRegister и tvForgot действий не имеют по ТЗ
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                binding.btnLogin.isEnabled = state.isLoginEnabled
                binding.btnLogin.alpha = if (state.isLoginEnabled) 1f else 0.5f
            }
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
