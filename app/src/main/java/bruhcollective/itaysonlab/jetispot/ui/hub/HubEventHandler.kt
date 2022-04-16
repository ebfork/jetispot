package bruhcollective.itaysonlab.jetispot.ui.hub

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import bruhcollective.itaysonlab.jetispot.core.objs.hub.HubEvent
import bruhcollective.itaysonlab.jetispot.core.objs.hub.HubItem

object HubEventHandler {
  fun handle (navController: NavController, delegate: HubScreenDelegate, event: HubEvent) {
    when (event) {
      is HubEvent.NavigateToUri -> {
        if (event.data.uri.startsWith("http")) {
          navController.context.startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(event.data.uri)))
        } else {
          navController.navigate(event.data.uri)
        }
      }
      is HubEvent.PlayFromContext -> delegate.play(event.data)
      HubEvent.Unknown -> {}
    }
  }
}

@Stable
fun Modifier.clickableHub(navController: NavController, delegate: HubScreenDelegate, item: HubItem) = this.clickable(enabled = item.events?.click != null) {
  HubEventHandler.handle(navController, delegate, item.events!!.click!!)
}