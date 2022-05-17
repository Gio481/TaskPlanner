package com.example.taskplanner.presentation.ui.auth.signup

import android.Manifest
import android.app.AlertDialog
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.taskplanner.R
import com.example.taskplanner.databinding.FragmentSignUpBinding
import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.presentation.base.BaseFragment
import com.example.taskplanner.presentation.ui.auth.signup.viewmodel.SignUpViewModel
import com.example.taskplanner.util.BindingInflater
import com.example.taskplanner.util.PermissionManager
import com.example.taskplanner.util.extensions.checkGalleryPermission
import com.example.taskplanner.util.extensions.isVisible
import com.example.taskplanner.util.extensions.observer
import com.example.taskplanner.util.extensions.showToast
import kotlin.reflect.KClass

class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel>() {

    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>
    private var imageUri: String? = null

    override val inflater: BindingInflater<FragmentSignUpBinding>
        get() = FragmentSignUpBinding::inflate

    override fun getViewModelClass(): KClass<SignUpViewModel> = SignUpViewModel::class

    override fun onBindViewModel(viewModel: SignUpViewModel) {
        with(binding) {
            passwordEditTextLayout.setPasswordToggleButton()
            repeatPasswordEditTextLayout.setPasswordToggleButton()
        }
        observeAuthResultLiveData(viewModel)
        observeErrorLiveData(viewModel)
        setUserImage()
        setListeners(viewModel)
    }

    private fun observeAuthResultLiveData(viewModel: SignUpViewModel) {
        observer(viewModel.signUpLiveData) {
            if (it != null) {
                binding.progressBarView.isVisible(false)
                findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
            }
        }
    }

    private fun observeErrorLiveData(viewModel: SignUpViewModel) {
        observer(viewModel.errorLiveData) {
            binding.progressBarView.isVisible(false)
            showToast(it)
        }
    }

    private fun setUserImage() {
        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                imageUri = it.toString()
                binding.userProfileImageView.setImageURI(it)
            }
        }
    }

    private fun setListeners(viewModel: SignUpViewModel) {
        with(binding) {
            signInButton.setOnClickListener {
                progressBarView.isVisible(true)
                signUp(viewModel)
            }
            userProfileImageView.setOnClickListener {
                PermissionManager(requireContext(), requireActivity()).mediaPermissionRequest(
                    { openStorage() },
                    { makeDialog() },
                    { requestMediaPermission(mediaPermissionChecker) },
                )
            }
        }
    }

    private fun signUp(viewModel: SignUpViewModel) {
        with(binding) {
            val user = UserDomain(
                name = userFullNameEditTextLayout.getText(),
                job = jobEditTextLayout.getText(),
                email = emailEditTextLayout.getText(),
                password = passwordEditTextLayout.getText(),
                profileImage = imageUri
            )
            viewModel.signUp(user, repeatPasswordEditTextLayout.getText())
        }
    }

    private fun openStorage() = imagePickerLauncher.launch("image/*")

    private fun makeDialog() {
        alertDialog {
            setTitle(getString(R.string.permission_dialog_title))
            setMessage(getString(R.string.permission_dialog_message))
            setNegativeButton(getString(R.string.permission_dialog_negative_button_text)) { _, _ -> }
            setPositiveButton(getString(R.string.permission_dialog_positive_button_text)) { _, _ ->
                requestMediaPermission(mediaPermissionChecker)
            }
        }
    }

    private val mediaPermissionChecker = checkGalleryPermission { openStorage() }

    private fun requestMediaPermission(request: ActivityResultLauncher<Array<String>>) {
        request.launch(
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )
    }

    private fun alertDialog(block: AlertDialog.Builder.() -> Unit): AlertDialog? {
        return AlertDialog.Builder(requireContext()).apply { block() }.show()
    }
}