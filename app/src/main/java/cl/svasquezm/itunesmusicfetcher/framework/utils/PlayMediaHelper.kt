package cl.svasquezm.itunesmusicfetcher.framework.utils

import android.media.AudioAttributes
import android.media.MediaPlayer
import org.jetbrains.anko.doAsync

class PlayMediaHelper {
    private var currentMediaUrl: String? = null
    private var mediaPlayer: MediaPlayer? = null

    fun playOrStopMedia(mediaUrl: String?, onCompleted: (ItemAdapterPayload) -> Unit) {
        mediaUrl?.let {
            if (currentMediaUrl == mediaUrl) {

                // Remove previous url
                currentMediaUrl = ""

                onCompleted(ItemAdapterPayload.SHOW_PLAY_ICON)
                stopCurrentMedia()
            } else {
                currentMediaUrl = mediaUrl
                playCurrentMedia(onCompleted)
            }
        }
    }

    /**
     * Plays current media sopping last process
     */
    fun playCurrentMedia(onStreamCompleted: (ItemAdapterPayload) -> Unit) =
        doAsync {
            if (mediaPlayer != null) {
                stopCurrentMedia()
            }

            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setFlags(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                setOnPreparedListener { onStreamCompleted(ItemAdapterPayload.SHOW_STOP_ICON) }
                setOnCompletionListener { onStreamCompleted(ItemAdapterPayload.SHOW_PLAY_ICON) }
                setDataSource(currentMediaUrl)
                prepare()
                start()
            }

    }

    private fun stopCurrentMedia() {
        mediaPlayer?.stop()
    }
}

enum class ItemAdapterPayload {
    SHOW_PLAY_ICON,
    SHOW_STOP_ICON,
    SHOW_LOADING
}