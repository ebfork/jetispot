package bruhcollective.itaysonlab.jetispot.ui.hub.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bruhcollective.itaysonlab.jetispot.core.objs.hub.HubItem
import bruhcollective.itaysonlab.jetispot.ui.hub.HubBinder
import bruhcollective.itaysonlab.jetispot.ui.hub.HubScreenDelegate

@Composable
fun Carousel(
  navController: NavController,
  delegate: HubScreenDelegate,
  item: HubItem,
) {
  Column(Modifier.padding(vertical = if (delegate.isSurroundedWithPadding()) 0.dp else 8.dp)) {
    if (item.text != null) {
      Text(text = item.text.title!!, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold, fontSize = 16.sp,
        modifier = Modifier.padding(horizontal = if (delegate.isSurroundedWithPadding()) 0.dp else 16.dp).padding(bottom = 12.dp))
    }

    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.padding(horizontal = if (delegate.isSurroundedWithPadding()) 0.dp else 16.dp)) {
      items(item.children ?: listOf()) { cItem ->
        HubBinder(navController, delegate, cItem)
      }
    }
  }
}