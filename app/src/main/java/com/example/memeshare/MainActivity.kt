package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    var currImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }

    private fun loadMeme() {
        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        val memeImageView: ImageView = findViewById(R.id.memeImageView)
        // Instantiate the RequestQueue.
       // val queue = Volley.newRequestQueue(this) -> we will use mysingleton class instead of this
        val url =   " https://meme-api.com/gimme"


        //json ->javascript object notation
        //This type of request specifically targets APIs that return data in JSON format.

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                 currImageUrl = response.getString("url")

                Glide.with(this).load(currImageUrl).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                }).into(memeImageView)

            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()

            }
        )

        // Add the request to the RequestQueue.
       MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }




    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND) //this is used to send we can also use others
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey checkout this new meme i got from Reddit $currImageUrl" )
        val chooser = Intent.createChooser(intent,"Share this meme using")
        startActivity(chooser)



    }
    fun nextMeme(view: View) {
        loadMeme()
    }
}