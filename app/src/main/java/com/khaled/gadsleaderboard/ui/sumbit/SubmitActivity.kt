package com.khaled.gadsleaderboard.ui.sumbit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.URLUtil
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.khaled.gadsleaderboard.R
import com.khaled.gadsleaderboard.utils.Constants.Companion.SUBMISSION_BASE_URL
import com.khaled.gadsleaderboard.utils.Resource
import com.khaled.gadsleaderboard.utils.Validation
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_submit.*
import kotlinx.android.synthetic.main.progressbar.*

class SubmitActivity : AppCompatActivity() {
    // views
    private lateinit var firstNameTextInputLayout: TextInputLayout
    private lateinit var lastNameTextInputLayout: TextInputLayout
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var linkTextInputLayout: TextInputLayout
    private lateinit var submitButton: Button
    private lateinit var progresbarLayout: FrameLayout
    // vars
    private lateinit var viewModel: SubmissionViewModel
    private var firstName: String = ""
    private var lastName: String = ""
    private var email: String = ""
    private var projectLink: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit)
        initViews()
        viewModel = ViewModelProvider(this).get(SubmissionViewModel::class.java)
    }

    private fun initViews() {
        // views
        firstNameTextInputLayout = activity_submit_first_name_text_layout
        lastNameTextInputLayout = activity_submit_last_name_text_layout
        emailTextInputLayout = activity_submit_email_text_layout
        linkTextInputLayout = activity_submit_link_text_layout
        submitButton = activity_submit_button
        progresbarLayout = progress_layout

        // OnClickListener
        submitButton.setOnClickListener { confirmInput() }
    }

    private fun confirmInput() {
        if (validateInput())
            showConfirmationAlertDialog()
    }

    private fun submitProject() {
        viewModel.submitProject(SUBMISSION_BASE_URL, email, firstName, lastName, projectLink)
        viewModel.submissionMutableLiveData.observe(this, { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgress(true)
                }
                is Resource.Success -> {
                    showSuccessAlertDialog()
                    showProgress(false)
                }
                is Resource.Error -> {
                    showErrorAlertDialog()
                    showProgress(false)
                }
            }
        })
    }

    private fun showConfirmationAlertDialog() {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val dialogView: View = layoutInflater.inflate(R.layout.dialog_confirmation, null)
        dialogBuilder.setView(dialogView)

        val cancelButton: ImageButton = dialogView.findViewById(R.id.dialog_confirmation_close)
        val yesButton: Button = dialogView.findViewById(R.id.dialog_confirmation_yes)

        val confirmationAlertDialog = dialogBuilder.create()
        confirmationAlertDialog.setCanceledOnTouchOutside(false)
        confirmationAlertDialog.show()

        cancelButton.setOnClickListener { confirmationAlertDialog.dismiss() }
        yesButton.setOnClickListener {
            confirmationAlertDialog.dismiss()
            submitProject()
        }
    }

    private fun showErrorAlertDialog() {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val dialogView: View = layoutInflater.inflate(R.layout.dialog_error, null)
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()

        Handler(Looper.getMainLooper()).postDelayed({
            alertDialog.dismiss()
        }, 2000)
    }

    private fun showSuccessAlertDialog() {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val dialogView: View = layoutInflater.inflate(R.layout.dialog_success, null)
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()

        Handler(Looper.getMainLooper()).postDelayed({
            alertDialog.dismiss()
            finish()
        }, 2000)
    }

    private fun validateInput(): Boolean {
        firstName = firstNameTextInputLayout.editText!!.text.toString()
        lastName = lastNameTextInputLayout.editText!!.text.toString()
        email = emailTextInputLayout.editText!!.text.toString()
        projectLink = linkTextInputLayout.editText!!.text.toString()

        val isValidFirstName =
            Validation.validateInput(
                firstNameTextInputLayout,
                getString(R.string.please_add_first_name),
                getString(R.string.invalid_first_name),
                Validation.isValidUserName(firstName)
            )

        val isValidLastName =
            Validation.validateInput(
                lastNameTextInputLayout,
                getString(R.string.please_add_last_name),
                getString(R.string.invalid_last_name),
                Validation.isValidUserName(lastName)
            )

        val isValidEmail =
            Validation.validateInput(
                emailTextInputLayout,
                getString(R.string.please_add_email),
                getString(R.string.invalid_email),
                Validation.isValidEmail(email)
            )

        val isValidLink =
            Validation.validateInput(
                linkTextInputLayout,
                getString(R.string.please_add_link),
                getString(R.string.invalid_link),
                URLUtil.isValidUrl(projectLink)
            )

        return isValidFirstName && isValidLastName && isValidEmail && isValidLink
    }

    private fun showProgress(flag: Boolean) {
        if (flag) {
            progresbarLayout.visibility = View.VISIBLE
        } else {
            progresbarLayout.visibility = View.GONE
        }
    }
}