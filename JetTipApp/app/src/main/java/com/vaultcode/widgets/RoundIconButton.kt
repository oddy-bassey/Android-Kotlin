package com.vaultcode.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/* Density Independent(dp) example 4.dp
 * This means it's independent of a particular device pixels
 * Whatever device runs the application, the android system will be able to pick the value
 * and adapt it to the pixel of that device
 */

val IconButtonSizeModifier = Modifier.size(40.dp)

@Composable
fun RoundIconButton(
    modifier: Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    tint: Color = Color.Black.copy(alpha = 0.8f),
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    elevation: CardElevation = CardDefaults.cardElevation(4.dp)) {

    Card(
        modifier = Modifier
            .padding(all = 4.dp)
            .clickable { onClick.invoke() }.then(IconButtonSizeModifier), // or .clickable(onClick = onClick).then(IconButtonSizeModifier))
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = elevation) {

        Icon(modifier = Modifier.size(40.dp),
            imageVector = imageVector,
            contentDescription = "Plus or minus icon",
            tint = tint)
    }
}