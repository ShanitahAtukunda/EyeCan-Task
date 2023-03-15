package com.example.testingtask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.testingtask.client.ApiClient
import com.example.testingtask.databinding.ActivityRegisterBinding
import com.eyecan.app.models.SignupRequestModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern

private const val TAG = "RegisterActivity"
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var occupation: String? = null
    //back button for activity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        binding.spinnerOccupation.setOnSpinnerItemSelectedListener<String> { _, _, _, newText ->
            occupation = newText
        }
        binding.btnRegister.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            Log.e(TAG,"################# Signup clicked ####################### ")
            val registerInfo = SignupRequestModel(
                fullName = binding.fullNameField.text.toString(),
                emailId = binding.edEmail.text.toString(),
                mobile = binding.mobileField.text.toString(),
                address = binding.addressField.text.toString(),
                role = "donor",
                occupation = occupation.toString(),
                country = "Ind",
                postcode ="478569",
                age = binding.editTextAge.text.toString(),
                password = binding.passwordField.text.toString()
            )
            signUp(signUpRequest = registerInfo)

        }



    }
    private fun signUp(signUpRequest: SignupRequestModel){
        val apiClient = ApiClient.getClient();

        val isAllFieldsChecked = checkAllFields()


        if (isAllFieldsChecked) {
            apiClient.registerUser(signUpRequest).enqueue(object :
                Callback<ResponseBody> {

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    binding.loading.visibility = View.INVISIBLE
                    Toast.makeText(
                        this@RegisterActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    binding.loading.visibility = View.INVISIBLE
                    Log.i(TAG, "register response $response");
                    if (response.code() == 201) {
                        Toast.makeText(this@RegisterActivity, "Registration success!", Toast.LENGTH_LONG)
                            .show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))

                    }
                    else{
                        Toast.makeText(this@RegisterActivity, "Registration failed!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
        }else{
            binding.loading.visibility = View.INVISIBLE
        }

    }

    private fun checkAllFields(): Boolean {
        // ReGex to check if a string
        // contains uppercase, lowercase
        // special character & numeric value
        val regex = ("^(?=.*[a-z])(?=."
                + "*[A-Z])(?=.*\\d)"
                + "(?=.*[-+_!@#$%^&*., ?]).+$")


        val p: Pattern = Pattern.compile(regex)
        val m: Matcher = p.matcher(binding.passwordField.text)

        if (binding.edEmail.length() == 0) {
            binding.edEmail.error = "Email field is required"
            return false
        }
//        else if(binding.fullNameField.length() == 0){
//            binding.fullNameField.error = "Full name field is required"
//            return false
//        }
        else if(binding.editTextAge.length() == 0){
            binding.editTextAge.error = "Age field is required"
            return false
        }else if(binding.mobileField.length() == 0){
            binding.mobileField.error = "Mobile Number field is required"
            return false
        }else if(binding.addressField.length() == 0){
            binding.addressField.error = "Address field is required"
            return false
        }
        else if(occupation.isNullOrEmpty()){
            binding.spinnerOccupation.error = "Occupation field is required"
            return false
        }
        if (binding.passwordField.length() == 0) {
            binding.passwordField.error = "Password is required"
            return false
        } else if (binding.passwordField.length() < 8) {
            binding.passwordField.error = "Password must be minimum 8 characters"
            return false
        }else if (!(m.matches())) {
            Log.e(TAG,"PASSWORD IS " + binding.passwordField.text)
            binding.passwordField.error = "Password must contain upper case and special character"
            return false
        }

        return true
    }
}