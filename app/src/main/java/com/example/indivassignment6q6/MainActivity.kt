package com.example.indivassignment6q6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.indivassignment6q6.ui.theme.IndivAssignment6Q6Theme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IndivAssignment6Q6Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MapScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    val startPoint = LatLng(40.7688, -73.9712) // Near Central Park Zoo
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startPoint, 14f)
    }

    // State for customizations
    var polylineColor by remember { mutableStateOf(Color.Red) }
    var polylineWidth by remember { mutableFloatStateOf(15f) }
    var polygonColor by remember { mutableStateOf(Color.Green) }
    var polygonWidth by remember { mutableFloatStateOf(5f) }

    // Sample coordinates for a hiking trail
    val hikingTrail = listOf(
        LatLng(40.7688, -73.9712),
        LatLng(40.7710, -73.9700),
        LatLng(40.7730, -73.9680),
        LatLng(40.7750, -73.9660),
        LatLng(40.7770, -73.9640)
    )

    // Sample coordinates for a park area (Polygon)
    val parkArea = listOf(
        LatLng(40.7680, -73.9720),
        LatLng(40.7780, -73.9720),
        LatLng(40.7780, -73.9630),
        LatLng(40.7680, -73.9630)
    )

    Column(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                Polyline(
                    points = hikingTrail,
                    color = polylineColor,
                    width = polylineWidth
                )

                Polygon(
                    points = parkArea,
                    fillColor = polygonColor.copy(alpha = 0.3f),
                    strokeColor = polygonColor,
                    strokeWidth = polygonWidth
                )
            }
        }

        // Controls
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Text("Trail Customization", style = MaterialTheme.typography.titleMedium)
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Width", modifier = Modifier.width(60.dp))
                Slider(
                    value = polylineWidth,
                    onValueChange = { polylineWidth = it },
                    valueRange = 5f..50f,
                    modifier = Modifier.weight(1f)
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ColorButton(Color.Red, polylineColor) { polylineColor = it }
                ColorButton(Color.Blue, polylineColor) { polylineColor = it }
                ColorButton(Color.Magenta, polylineColor) { polylineColor = it }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("Park Customization", style = MaterialTheme.typography.titleMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Width", modifier = Modifier.width(60.dp))
                Slider(
                    value = polygonWidth,
                    onValueChange = { polygonWidth = it },
                    valueRange = 1f..20f,
                    modifier = Modifier.weight(1f)
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ColorButton(Color.Green, polygonColor) { polygonColor = it }
                ColorButton(Color.Yellow, polygonColor) { polygonColor = it }
                ColorButton(Color.Cyan, polygonColor) { polygonColor = it }
            }
        }
    }
}

@Composable
fun ColorButton(color: Color, selectedColor: Color, onClick: (Color) -> Unit) {
    val isSelected = color == selectedColor
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color)
            .clickable { onClick(color) }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
        }
    }
}
