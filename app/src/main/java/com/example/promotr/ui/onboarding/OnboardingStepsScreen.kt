package com.example.promotr.ui.onboarding

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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.promotr.R
import com.example.promotr.ui.components.ProgressDots
import com.example.promotr.ui.components.OrangeButton
import com.example.promotr.ui.theme.BrandOrange
import com.example.promotr.ui.theme.White
import kotlinx.coroutines.launch

data class OnboardingStep(
    val imageRes: Int,
    val title: String,
    val subtitle: String
)

@Composable
fun OnboardingStepsScreen(
    onNavigateToAuth: () -> Unit
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(initialPage = 0) { 3 }
    val coroutineScope = rememberCoroutineScope()
    
    val steps = listOf(
        OnboardingStep(
            imageRes = R.drawable.hireskilledworker,
            title = "Hire Skilled Professionals Anytime",
            subtitle = "Connect with trusted experts for any project, big or small, right at your fingertips."
        ),
        OnboardingStep(
            imageRes = R.drawable.trustedmanpowerservices,
            title = "Trusted Manpower Services Near You",
            subtitle = "Connect with skilled professionals in your local area for reliable and efficient services, whenever you need them."
        ),
        OnboardingStep(
            imageRes = R.drawable.quickbooking,
            title = "Quick Booking & Real-Time Updates",
            subtitle = "Effortlessly book skilled professionals and track your service status with real-time notifications, ensuring a seamless and efficient experience from start to finish."
        )
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            // Logo at top
            Text(
                text = "aquila.",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = BrandOrange,
                modifier = Modifier.padding(start = 24.dp, top = 48.dp)
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Pager for onboarding steps
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { page ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Image
                    Image(
                        painter = painterResource(id = steps[page].imageRes),
                        contentDescription = steps[page].title,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(300.dp),
                        contentScale = ContentScale.Fit
                    )
                    
                    Spacer(modifier = Modifier.height(40.dp))
                    
                    // Title
                    Text(
                        text = steps[page].title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.ui.graphics.Color.Black,
                        textAlign = TextAlign.Center,
                        lineHeight = 28.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Subtitle
                    Text(
                        text = steps[page].subtitle,
                        fontSize = 16.sp,
                        color = androidx.compose.ui.graphics.Color(0xFF666666),
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Progress Dots
            ProgressDots(
                currentStep = pagerState.currentPage,
                totalSteps = steps.size,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 32.dp)
            )
            
            // Next/Get Started Button
            OrangeButton(
                text = if (pagerState.currentPage == steps.size - 1) "Get Started" else "Next",
                onClick = {
                    if (pagerState.currentPage < steps.size - 1) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        // Save onboarding completed status
                        val sharedPref = context.getSharedPreferences("onboarding", android.content.Context.MODE_PRIVATE)
                        sharedPref.edit().putBoolean("completed", true).apply()
                        onNavigateToAuth()
                    }
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Skip Button
            Button(
                onClick = {
                    // Save onboarding completed status
                    val sharedPref = context.getSharedPreferences("onboarding", android.content.Context.MODE_PRIVATE)
                    sharedPref.edit().putBoolean("completed", true).apply()
                    onNavigateToAuth()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.ui.graphics.Color.Transparent,
                    contentColor = androidx.compose.ui.graphics.Color(0xFF4A4A4A)
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text(
                    text = "Skip",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
