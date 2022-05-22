package bruhcollective.itaysonlab.jetispot.playback.service

import android.annotation.SuppressLint
import bruhcollective.itaysonlab.jetispot.core.util.Log
import androidx.media2.common.MediaItem
import androidx.media2.common.MediaMetadata
import androidx.media2.common.SessionPlayer
import bruhcollective.itaysonlab.jetispot.playback.helpers.toMediaMetadata
import xyz.gianlu.librespot.audio.MetadataWrapper
import xyz.gianlu.librespot.metadata.PlayableId
import xyz.gianlu.librespot.player.Player
import java.lang.Exception

@SuppressLint("UnsafeOptInUsageError")
class SpServiceEventsListener(
  val player: SpPlayerWrapper
) : Player.EventsListener {
  override fun onContextChanged(p0: Player, p1: String) {
    player.state.currentContextMetadata = MediaMetadata.Builder().putString(MediaMetadata.METADATA_KEY_TITLE, p1).build()
    player.runOnListeners { it.onPlaylistMetadataChanged(player, player.state.currentContextMetadata) }
  }

  override fun onTrackChanged(p0: Player, p1: PlayableId, p2: MetadataWrapper?, userInitiated: Boolean) {
    // no ID yet
  }

  override fun onPlaybackEnded(p0: Player) {
    player.state.playbackState = SessionPlayer.PLAYER_STATE_IDLE
    player.runOnListeners { it.onPlaybackCompleted(player) }
  }

  override fun onPlaybackPaused(p0: Player, trackTime: Long) {
    player.audioFocus.abandonFocus()
    player.state.playbackState = SessionPlayer.PLAYER_STATE_PAUSED
    player.runOnListeners { it.onPlayerStateChanged(player, player.state.playbackState) }
  }

  override fun onPlaybackResumed(p0: Player, trackTime: Long) {
    player.audioFocus.requestFocus()
    player.state.playbackState = SessionPlayer.PLAYER_STATE_PLAYING
    player.runOnListeners { it.onPlayerStateChanged(player, player.state.playbackState) }
  }

  override fun onPlaybackFailed(p0: Player, p1: Exception) {
    Log.e("SpService", "onPlaybackFailed => ${p1.message}")
  }

  override fun onTrackSeeked(p0: Player, trackTime: Long) {
    // player.runOnListeners { it.onSeekCompleted(player, trackTime) }
  }

  override fun onMetadataAvailable(p0: Player, p1: MetadataWrapper) {
    player.state.currentTrack = p1
    player.state.currentMediaItem = MediaItem.Builder().apply {
      setStartPosition(0L)
      setEndPosition(p1.duration().toLong())
      setMetadata(p1.toMediaMetadata(p0))
    }.build()

    player.runOnListeners { it.onCurrentMediaItemChanged(player, player.state.currentMediaItem) }
  }

  override fun onPlaybackHaltStateChanged(p0: Player, p1: Boolean, p2: Long) {

  }

  override fun onInactiveSession(p0: Player, p1: Boolean) {

  }

  override fun onVolumeChanged(p0: Player, p1: Float) {

  }

  override fun onPanicState(p0: Player) {

  }

  override fun onStartedLoading(p0: Player) {

  }

  override fun onFinishedLoading(p0: Player) {
    player.state.playbackState = SessionPlayer.PLAYER_STATE_PLAYING
  }
}