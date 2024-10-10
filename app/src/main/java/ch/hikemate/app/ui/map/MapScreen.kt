package ch.hikemate.app.ui.map

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@Composable
fun MapScreen() {
  val context = LocalContext.current
  // Avoid re-creating the MapView on every recomposition
  val mapView = remember { MapView(context) }

  Configuration.getInstance().apply {
    // Set user-agent to avoid rejected requests
    userAgentValue = context.packageName
    // TODO : Are there more configuration options that need to be set?
    // TODO : How does OSMDroid manage the cache?
  }

  mapView.apply {
    // TODO : Define a level of zoom to start at. User settings is too much for this sprint but could be a good idea in the long run.
    controller.setZoom(15.0)
    // TODO : Define where the map should be centered. User location might be a bit too much for this sprint though.
    controller.setCenter(GeoPoint(46.5, 6.6))
    // Enable touch-controls such as pinch to zoom
    setMultiTouchControls(true)
  }

  Box(modifier = Modifier.fillMaxSize()) {
    AndroidView(
      factory = { mapView },
      modifier = Modifier.fillMaxSize()
    )

    IconButton(
      onClick = {
        // TODO : Adapt the map screen to navigation
        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
      },
      modifier = Modifier
        .padding(16.dp)
        .align(Alignment.TopStart)
        // Clip needs to be before background
        .clip(RoundedCornerShape(8.dp))
        // TODO : Replace this hardcoded color
        .background(Color.White)
    ) {
      // TODO : Replace this hardcoded color
      Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.Black)
    }

    CollapsibleHikesList()
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsibleHikesList() {
  val scaffoldState = rememberBottomSheetScaffoldState()

  BottomSheetScaffold(
    scaffoldState = scaffoldState,
    sheetContent = {
      // TODO : Maybe use a LazyColumn here instead?
      Column (
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp)
      ) {
        // TODO : Import hikes from viewmodel
        Text("Hikes")
        Spacer(modifier = Modifier.height(10.dp))
        Text("Dents du Midi - 834m")
        Text("Dents du quatre heures - 412m")
        Text("Dents du Minuit - 1356m")
      }
    },
    // TODO : Adjust this value and the behavior of the sheet
    sheetPeekHeight = 100.dp
  ) { }
}