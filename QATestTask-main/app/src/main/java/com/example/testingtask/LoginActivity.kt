package com.example.testingtask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.testingtask.client.ApiClient
import com.example.testingtask.databinding.ActivityLoginBinding
import com.eyecan.app.models.SignInRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern

private const val TAG = "LoginActivity"
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createAccount.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            Log.e(TAG,"################# Login clicked #######################")
            //emailId = "alexis@gmail.com", password = "User.password1"
            val signInInfo = SignInRequest(emailId = binding.edEmail.text.toString(), password = binding.edPassword.text.toString())
            signIn(signInRequest = signInInfo)

        }
    }

    private fun signIn(signInRequest: SignInRequest){
        val apiClient = ApiClient.getClient();
        val isAllFieldsChecked = checkAllFields()

        if (isAllFieldsChecked) {
            apiClient.signIn(signInRequest).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    binding.loading.visibility = View.INVISIBLE
                    Toast.makeText(
                        this@LoginActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    binding.loading.visibility = View.INVISIBLE
                    if (response.code() == 200) {
                        Toast.makeText(this@LoginActivity, "Login success!", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    } else {
                        Toast.makeText(this@LoginActivity, "Login failed!", Toast.LENGTH_LONG).show()
                    }
                }
            })
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
        val m: Matcher = p.matcher(binding.edPassword.text)

        if (binding.edEmail.length() == 0) {
            binding.loading.visibility = View.INVISIBLE
            binding.edEmail.error = "This field is required"
            return false
        }
        if (binding.edPassword.length() == 0) {
            binding.loading.visibility = View.INVISIBLE
            binding.edPassword.error = "Password is required"
            return false
        } else if (binding.edPassword.length() < 8) {
            binding.loading.visibility = View.INVISIBLE
            binding.edPassword.error = "Password must be minimum 8 characters"
            return false
        }else if (!(m.matches())) {
            binding.loading.visibility = View.INVISIBLE
            Log.e(TAG,"PASSWORD IS " + binding.edPassword.text)
            binding.edPassword.error = "Password must contain upper case and special character"
            return false
        }

        return true
    }

}