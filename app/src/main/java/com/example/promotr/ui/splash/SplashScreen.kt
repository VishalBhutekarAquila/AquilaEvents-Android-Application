package com.example.promotr.ui.splash

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.compose.foundation.layout.size
import androidx.core.content.ContextCompat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.promotr.ui.theme.GradientStartOrange
import com.example.promotr.ui.theme.PrimaryOrange
import com.example.promotr.ui.theme.White
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToOnboardingSteps: () -> Unit,
    viewModel: SplashViewModel = viewModel()
) {
    val context = LocalContext.current
    var hasNavigated by remember { mutableStateOf(false) }
    var showPermissionDialog by remember { mutableStateOf(false) }
    var showNoInternetDialog by remember { mutableStateOf(false) }
    var animationStarted by remember { mutableStateOf(false) }
    
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        animationStarted = true
    }
    
    // Check permission on first launch
    LaunchedEffect(Unit) {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        
        val prefs = context.getSharedPreferences("permission_prefs", android.content.Context.MODE_PRIVATE)
        val wasAsked = prefs.getBoolean("permission_asked", false)
        
        if (hasPermission) {
            animationStarted = true
        } else if (!wasAsked) {
            showPermissionDialog = true
            prefs.edit().putBoolean("permission_asked", true).apply()
        } else {
            animationStarted = true
        }
    }
    
    // Auto-navigate after a delay (splash screen duration)
    LaunchedEffect(animationStarted) {
        if (animationStarted) {
            delay(2000) // 2 seconds splash screen
            viewModel.checkInternetAndProceed()
        }
    }
    
    // Observe navigation trigger
    val navigateToNext by viewModel.navigateToNext.observeAsState()
    LaunchedEffect(navigateToNext) {
        if (navigateToNext == true && !hasNavigated) {
            hasNavigated = true
            val onboardingPrefs = context.getSharedPreferences("onboarding", android.content.Context.MODE_PRIVATE)
            val isOnboardingCompleted = onboardingPrefs.getBoolean("completed", false)
            
            if (isOnboardingCompleted) {
                onNavigateToOnboardingSteps()
            } else {
                onNavigateToOnboarding()
            }
        }
    }
    
    // Observe internet availability
    val isInternetAvailable by viewModel.isInternetAvailable.observeAsState()
    LaunchedEffect(isInternetAvailable) {
        if (isInternetAvailable == false) {
            showNoInternetDialog = true
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(GradientStartOrange, PrimaryOrange),
                    center = androidx.compose.ui.geometry.Offset(0.5f, 0.5f),
                    radius = 1000f
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Glow effect - white blurred circle in center
        // Note: Blur effect in Compose requires RenderEffect (API 31+) or custom implementation
        // For now, using a semi-transparent circle to simulate the glow
        Box(
            modifier = Modifier
                .size(256.dp)
                .align(Alignment.Center)
                .background(
                    color = White.copy(alpha = 0.1f),
                    shape = androidx.compose.foundation.shape.CircleShape
                )
                .zIndex(0f)
        )
        
        // Promotr text with shadow effect
        // Using scale to match HTML's transform scale-105 effect
        Text(
            text = "Promotr",
            fontSize = 56.sp, // Base size, responsive: 5xl (48px) to 7xl (72px) equivalent
            fontWeight = FontWeight.ExtraBold, // font-extrabold
            color = White,
            textAlign = TextAlign.Center,
            letterSpacing = (-0.5).sp, // tracking-tighter equivalent
            modifier = Modifier
                .align(Alignment.Center)
                .zIndex(1f)
                .graphicsLayer {
                    scaleX = 1.05f // transform scale-105
                    scaleY = 1.05f
                }
                .shadow(
                    elevation = 20.dp,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp),
                    ambientColor = Color.Black.copy(alpha = 0.1f),
                    spotColor = White.copy(alpha = 0.2f)
                )
        )
    }
    
    // Permission Dialog
    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { 
                showPermissionDialog = false
                animationStarted = true
            },
            title = { Text("Storage Permission") },
            text = { Text("Promotr needs storage access to cache data for a better experience.") },
            confirmButton = {
                TextButton(onClick = {
                    showPermissionDialog = false
                    permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }) {
                    Text("Allow")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showPermissionDialog = false
                    animationStarted = true
                }) {
                    Text("Skip")
                }
            }
        )
    }
    
    // No Internet Dialog
    if (showNoInternetDialog) {
        AlertDialog(
            onDismissRequest = { showNoInternetDialog = false },
            title = { Text("No Internet Connection") },
            text = { Text("Please check your internet connection and try again.") },
            confirmButton = {
                TextButton(onClick = {
                    showNoInternetDialog = false
                    viewModel.checkInternetAndProceed()
                }) {
                    Text("Retry")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showNoInternetDialog = false
                }) {
                    Text("Exit")
                }
            }
        )
    }
}
