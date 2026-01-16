package com.example.promotr.ui.crew.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.promotr.ui.theme.HomePrimary
import com.example.promotr.ui.theme.White

enum class BottomNavItem {
    HOME, EARNINGS, PROFILE
}

@Composable
fun BottomNavigation(
    currentItem: BottomNavItem,
    onItemClick: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(0.dp),
                spotColor = Color.Black.copy(alpha = 0.05f)
            )
            .background(
                color = White,
                shape = RoundedCornerShape(0.dp)
            )
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .padding(bottom = 8.dp) // Add bottom padding for safe area
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BottomNavButton(
                icon = Icons.Default.Home,
                label = "Home",
                isSelected = currentItem == BottomNavItem.HOME,
                onClick = { onItemClick(BottomNavItem.HOME) }
            )
            BottomNavButton(
                icon = Icons.Default.Paid,
                label = "Earnings",
                isSelected = currentItem == BottomNavItem.EARNINGS,
                onClick = { onItemClick(BottomNavItem.EARNINGS) }
            )
            BottomNavButton(
                icon = Icons.Default.Person,
                label = "Profile",
                isSelected = currentItem == BottomNavItem.PROFILE,
                onClick = { onItemClick(BottomNavItem.PROFILE) }
            )
        }
    }
}

@Composable
private fun BottomNavButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) HomePrimary else Color(0xFF9CA3AF),
        animationSpec = tween(200),
        label = "nav_icon_color"
    )

    val textColor by animateColorAsState(
        targetValue = if (isSelected) HomePrimary else Color(0xFF9CA3AF),
        animationSpec = tween(200),
        label = "nav_text_color"
    )

    Column(
        modifier = Modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = iconColor,
            modifier = Modifier.size(26.dp)
        )
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = textColor
        )
    }
}
