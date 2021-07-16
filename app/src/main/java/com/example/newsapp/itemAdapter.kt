package com.example.recyclerview


import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Picture
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_custom_row.view.*
import java.net.URL

class ItemAdapter(val context: Context, var title: ArrayList<String> , var publishedDate: ArrayList<String>,
                  var cat: ArrayList<String>,  var picture: ArrayList<String>, var url: ArrayList<String>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    var mContext: Context

    init {

         mContext = context

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(

            LayoutInflater.from(context).inflate(
                R.layout.item_custom_row,
                parent,
                false
            )
        )
    }
    override fun getItemCount(): Int {

        return title.size
    }

//    fun clearItem(){
//            items.clear()
//    }
fun deleteItems() {
    title.removeAll(title)
    publishedDate.removeAll(publishedDate)
    cat.removeAll(cat)
    url.removeAll(url)
    picture.removeAll(url)
    notifyDataSetChanged()
}

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val picasso = Picasso.get()

        val item = title.get(position)
        val PublishedDate = publishedDate.get(position)
        val Cat = cat.get(position)
        val Url = url.get(position)
        val Picture = picture.get(position)
        holder.tvItem.text = item
        holder.tvDate.text = PublishedDate
        holder.tv_cat.text = Cat
        holder.tv_url.text = Picture


        picasso.load(Picture).into(holder.iv_picture)


        holder.cardViewItem.setOnClickListener{

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Url))
            mContext.startActivity(browserIntent)

        }



        //   notifyDataSetChanged()

    }




    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each item to

        val tvItem = view.tv_item_name
        val tvDate = view.tv_date
        val tv_cat = view.tv_cat
        val tv_url = view.tv_url
        val iv_picture = view.iv_thumbnail
        val cardViewItem = view.card_view_item

    }


}