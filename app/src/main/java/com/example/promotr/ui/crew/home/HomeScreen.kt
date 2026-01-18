package com.example.promotr.ui.crew.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.promotr.ui.components.CategoryFilterChip
import com.example.promotr.ui.models.JobListing
import com.example.promotr.ui.models.JobType
import com.example.promotr.ui.theme.HomeBackgroundLight
import com.example.promotr.ui.theme.HomePrimary
import com.example.promotr.ui.theme.White
@Preview

@Composable
fun HomeScreen(
    onNavigateToEarnings: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToJobApplication: (com.example.promotr.ui.models.JobListing) -> Unit = {}
) {
    var selectedCategory by remember { mutableStateOf("All Jobs") }
    var currentNavItem by remember { mutableStateOf(BottomNavItem.HOME) }

    val categories = listOf("All Jobs", "Promotional", "Technical", "Hospitality")

    // Mock job data
    val jobs = remember {
        listOf(
            JobListing(
                id = "1",
                title = "Event Promoter",
                type = JobType.ONE_OFF_EVENT,
                pay = "$25",
                payType = "hr",
                location = "Convention Center, Downtown",
                date = "Sat, Oct 24",
                time = "9:00 AM - 5:00 PM",
                description = "Looking for energetic brand ambassadors to distribute flyers and engage with attendees at the annual Tech Summit."
            ),
            JobListing(
                id = "2",
                title = "Stage Technician Asst.",
                type = JobType.URGENT_FILL,
                pay = "$200",
                payType = "flat",
                location = "Grand Music Hall",
                date = "Fri, Oct 23",
                time = "4:00 PM - 11:00 PM",
                description = "Assist the lead technician with lighting setup and cable management for a live concert. Must have basic AV knowledge."
            ),
            JobListing(
                id = "3",
                title = "Registration Staff",
                type = JobType.MULTI_DAY,
                pay = "$22",
                payType = "hr",
                location = "City Expo Center",
                date = "Oct 26 - Oct 28",
                time = "Various Shifts",
                description = "Check-in attendees, hand out badges, and provide general information. Customer service experience preferred."
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBackgroundLight)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Sticky Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = HomeBackgroundLight.copy(alpha = 0.95f),
                        shape = RoundedCornerShape(0.dp)
                    )
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(0.dp),
                        spotColor = Color.Black.copy(alpha = 0.05f)
                    )
            ) {
                Column {
                    // Top bar with logo and icons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Logo
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(
                                        color = HomePrimary,
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "⚡",
                                    fontSize = 20.sp
                                )
                            }
                            Text(
                                text = "Promotr",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF181511)
                            )
                        }

                        // Notifications and Filter with iOS-style backgrounds
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(
                                            color = White,
                                            shape = CircleShape
                                        )
                                        .shadow(
                                            elevation = 1.dp,
                                            shape = CircleShape,
                                            spotColor = Color.Black.copy(alpha = 0.05f)
                                        )
                                        .clickable { },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Notifications,
                                        contentDescription = "Notifications",
                                        tint = Color(0xFF374151),
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .size(8.dp)
                                        .background(
                                            color = Color(0xFFEF4444),
                                            shape = CircleShape
                                        )
                                        .border(
                                            width = 1.5.dp,
                                            color = White,
                                            shape = CircleShape
                                        )
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        color = White,
                                        shape = CircleShape
                                    )
                                    .shadow(
                                        elevation = 1.dp,
                                        shape = CircleShape,
                                        spotColor = Color.Black.copy(alpha = 0.05f)
                                    )
                                    .clickable { },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Tune,
                                    contentDescription = "Filter",
                                    tint = Color(0xFF374151),
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }

                    // Greeting
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Hello, Sarah 👋",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )
                        Text(
                            text = "Ready for your next gig?",
                            fontSize = 14.sp,
                            color = Color(0xFF6B7280),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    // Search Bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .background(
                                    color = White,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .shadow(
                                    elevation = 1.dp,
                                    shape = RoundedCornerShape(12.dp),
                                    spotColor = Color.Black.copy(alpha = 0.05f)
                                )
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = Color(0xFF9CA3AF),
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "Search jobs, roles, or venues...",
                                    fontSize = 14.sp,
                                    color = Color(0xFF9CA3AF)
                                )
                            }
                        }
                    }

                    // Category Filters
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        categories.forEach { category ->
                            CategoryFilterChip(
                                text = category,
                                isSelected = selectedCategory == category,
                                onClick = { selectedCategory = category }
                            )
                        }
                    }
                }
            }

            // Job Listings
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 100.dp // Add bottom padding to avoid bottom nav overlap
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(jobs) { job ->
                    JobCard(
                        job = job,
                        onApplyClick = { onNavigateToJobApplication(job) },
                        onBookmarkClick = { /* Handle bookmark */ }
                    )
                }
            }
        }

        // Bottom Navigation
        BottomNavigation(
            currentItem = currentNavItem,
                onItemClick = { item ->
                currentNavItem = item
                when (item) {
                    BottomNavItem.EARNINGS -> onNavigateToEarnings()
                    BottomNavItem.PROFILE -> onNavigateToProfile()
                    BottomNavItem.HOME -> { /* Already on home */ }
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
