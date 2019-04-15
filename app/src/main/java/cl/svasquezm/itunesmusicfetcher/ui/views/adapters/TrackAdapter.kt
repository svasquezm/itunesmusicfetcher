package cl.svasquezm.itunesmusicfetcher.ui.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cl.svasquezm.itunesmusicfetcher.R
import cl.svasquezm.itunesmusicfetcher.domain.models.TrackModel
import cl.svasquezm.itunesmusicfetcher.framework.utils.ItemAdapterPayload

class TrackAdapter(private var tracks: ArrayList<TrackModel> = ArrayList()):
    RecyclerView.Adapter<TrackViewHolder>() {

    var onPlayStopImageClickListener: (TrackModel, Int) -> Unit = { _, _ -> }
    var onFavoriteIconClickListener: (TrackModel, Int) -> Unit = { _, _ -> }

    override fun getItemCount() = tracks.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int, payloads: MutableList<Any>) {
        if(payloads.isNotEmpty()) {
            when (payloads[0] as ItemAdapterPayload) {
                ItemAdapterPayload.SHOW_STOP_ICON -> {
                    val drawable = ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_stop_black_24dp)
                    holder.progressBar.visibility = View.INVISIBLE
                    holder.playStopImage.visibility = View.VISIBLE
                    holder.playStopImage.setImageDrawable(drawable)
                }

                ItemAdapterPayload.SHOW_PLAY_ICON -> {
                    val drawable =
                        ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_play_arrow_black_24dp)
                    holder.progressBar.visibility = View.INVISIBLE
                    holder.playStopImage.visibility = View.VISIBLE
                    holder.playStopImage.setImageDrawable(drawable)
                }

                ItemAdapterPayload.SHOW_LOADING -> {
                    holder.progressBar.visibility = View.VISIBLE
                    holder.playStopImage.visibility = View.INVISIBLE
                }
            }
        } else {
            val drawable =
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_play_arrow_black_24dp)
            holder.progressBar.visibility = View.INVISIBLE
            holder.playStopImage.visibility = View.VISIBLE
            holder.playStopImage.setImageDrawable(drawable)
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = tracks[position]
        holder.bandName.text = track.artistName
        holder.title.text = track.trackName

        holder.playStopImage.setOnClickListener { onPlayStopImageClickListener(track, position) }
        holder.startImage.setOnClickListener { onFavoriteIconClickListener(track, position) }
        holder.startImage.alpha = if(track.isFavorite){
            1f
        } else {
            0.2f
        }
    }

    var previousClickedElement = -1
    fun notifyItemChangedRerstoringPrevious(position: Int, payload: ItemAdapterPayload){
        if(previousClickedElement != position) {
            // Notify previous element
            if(previousClickedElement >= 0) {
                super.notifyItemChanged(previousClickedElement, ItemAdapterPayload.SHOW_PLAY_ICON)
            }

            // Notify current element
            super.notifyItemChanged(position, payload)

            previousClickedElement = position
        }
    }

    fun clearTracks() = tracks.clear()
    fun setTracks(tracks: List<TrackModel>) {
        this.tracks.addAll(tracks)
    }
}

class TrackViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val progressBar: View = view.findViewById(R.id.progressBar)
    val playStopImage: ImageView = view.findViewById(R.id.playStopImage)
    val bandName: TextView = view.findViewById(R.id.bandName)
    val title: TextView = view.findViewById(R.id.title)
    val startImage: ImageView = view.findViewById(R.id.startImage)
}