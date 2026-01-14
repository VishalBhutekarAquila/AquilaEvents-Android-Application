package com.example.promotr.ui.splash

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.promotr.R
import com.example.promotr.ui.theme.BrandOrange
import com.example.promotr.ui.theme.White
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToOnboardingSteps: () -> Unit,
    viewModel: SplashViewModel = viewModel()
) {
    val context = LocalContext.current
    var targetProgress by remember { mutableFloatStateOf(0f) }
    var currentTextIndex by remember { mutableIntStateOf(0) }
    var hasNavigated by remember { mutableStateOf(false) }
    var showPermissionDialog by remember { mutableStateOf(false) }
    var showNoInternetDialog by remember { mutableStateOf(false) }
    var animationStarted by remember { mutableStateOf(false) }
    
    val texts = listOf(
        "Work Smarter. Shine Brighter.",
        "Your crew. Your strength, your Aquila.",
        "Find work that moves you"
    )
    
    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 2000),
        label = "progress"
    )
    
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
    
    // Animate progress in segments
    LaunchedEffect(animationStarted) {
        if (animationStarted) {
            // First segment: 0-33%
            targetProgress = 0.33f
            currentTextIndex = 0
            delay(2000)
            
            // Second segment: 33-66%
            targetProgress = 0.66f
            currentTextIndex = 1
            delay(2000)
            
            // Third segment: 66-100%
            targetProgress = 1f
            currentTextIndex = 2
            delay(2000)
            
            // Check internet and proceed after animation completes
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
            .background(White)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.startlogo),
                contentDescription = "Logo",
                modifier = Modifier.size(300.dp, 244.dp),
                contentScale = ContentScale.Fit
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // App Name
            Text(
                text = "aquila.",
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                color = BrandOrange,
                letterSpacing = 0.05.sp
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Progress Bar
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                color = BrandOrange,
                trackColor = androidx.compose.ui.graphics.Color(0xFFE0E0E0)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Progress Status Text
            Text(
                text = texts.getOrElse(currentTextIndex) { texts[0] },
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = BrandOrange,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    
    // Permission Dialog
    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { 
                showPermissionDialog = false
                animationStarted = true
            },
            title = { Text("Storage Permission") },
            text = { Text("Aquila needs storage access to cache data for a better experience.") },
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
