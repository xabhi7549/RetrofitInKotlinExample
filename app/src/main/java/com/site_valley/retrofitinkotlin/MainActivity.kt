package com.site_valley.retrofitinkotlin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val responseText = findViewById<View>(R.id.text) as TextView
        val apiInterface = APIClient().getClient()?.create<APIInterface>(APIInterface::class.java)

        /**
         * GET List Resources
         */
        val call: Call<MultipleResource?>? = apiInterface!!.doGetListResources()
        call?.enqueue(object : Callback<MultipleResource?> {
            override fun onResponse(
                call: Call<MultipleResource?>?,
                response: Response<MultipleResource?>
            ) {
                Log.d("TAG", response.code().toString() + "")
                var displayResponse = ""
                val resource: MultipleResource? = response.body()
                val text = resource?.page
                val total = resource?.total
                val totalPages = resource?.totalPages
                val datumList = resource?.data
                displayResponse += """${text.toString()} Page
$total Total
$totalPages Total Pages
"""
                for (datum in datumList!!) {
                    displayResponse += """${datum.id.toString()} ${datum.name} ${datum.pantoneValue} ${datum.year}
"""
                }
                responseText.text = displayResponse
            }

            override fun onFailure(call: Call<MultipleResource?>, t: Throwable?) {
                call.cancel()
            }
        })

    }
}