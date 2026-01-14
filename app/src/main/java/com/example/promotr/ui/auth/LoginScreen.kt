package com.example.promotr.ui.auth

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.KeyboardType
import com.example.promotr.ui.components.LoginTextField
import com.example.promotr.ui.theme.LoginBackgroundLight
import com.example.promotr.ui.theme.LoginIconGray
import com.example.promotr.ui.theme.LoginInputBg
import com.example.promotr.ui.theme.LoginPrimary
import com.example.promotr.ui.theme.LoginPrimaryHover
import com.example.promotr.ui.theme.LoginTextPrimary
import com.example.promotr.ui.theme.LoginTextSecondary
import com.example.promotr.ui.theme.LoginTextTertiary
import com.example.promotr.ui.theme.White

@Composable
fun LoginScreen(
    onNavigateToMain: () -> Unit,
    defaultToPhone: Boolean = false
) {
    var isEmailSelected by remember { mutableStateOf(!defaultToPhone) }
    var emailOrPhone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isButtonPressed by remember { mutableStateOf(false) }
    
    val buttonScale by animateFloatAsState(
        targetValue = if (isButtonPressed) 0.98f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "button_scale"
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LoginBackgroundLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            
            // Event Icon
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color = LoginPrimary.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Event,
                    contentDescription = "Event",
                    tint = LoginPrimary,
                    modifier = Modifier.size(32.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Title
            Text(
                text = "Log In",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = LoginTextPrimary,
                textAlign = TextAlign.Center,
                letterSpacing = (-0.5).sp,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Subtitle
            Text(
                text = "Enter your credentials to access your event crew dashboard.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = LoginTextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Email/Phone Toggle
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        color = LoginInputBg,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Email Button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .background(
                                color = if (isEmailSelected) White else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { isEmailSelected = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Email",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isEmailSelected) LoginPrimary else LoginTextTertiary
                        )
                    }
                    
                    // Phone Button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .background(
                                color = if (!isEmailSelected) White else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { isEmailSelected = false },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Phone Number",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (!isEmailSelected) LoginPrimary else LoginTextTertiary
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Input Fields
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(20.dp)
            ) {
                // Email or Phone Input
                Column {
                    Text(
                        text = if (isEmailSelected) "Work Email" else "Phone Number",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = LoginTextPrimary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LoginTextField(
                        value = emailOrPhone,
                        onValueChange = { emailOrPhone = it },
                        label = "",
                        placeholder = if (isEmailSelected) "name@eventcompany.com" else "(555) 000-0000",
                        leadingIcon = if (isEmailSelected) Icons.Default.Email else Icons.Default.Call,
                        keyboardType = if (isEmailSelected) KeyboardType.Email else KeyboardType.Phone,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                
                // Password Input
                Column {
                    Text(
                        text = "Password",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = LoginTextPrimary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LoginTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "",
                        placeholder = "••••••••",
                        leadingIcon = Icons.Default.Lock,
                        trailingIcon = {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                                tint = LoginIconGray,
                                modifier = Modifier
                                    .clickable { isPasswordVisible = !isPasswordVisible }
                                    .padding(8.dp)
                            )
                        },
                        isPassword = true,
                        isPasswordVisible = isPasswordVisible,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                
                // Forgot Password
                Text(
                    text = "Forgot Password?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = LoginPrimary,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { /* Handle forgot password */ }
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Log In Button
            Button(
                onClick = {
                    isButtonPressed = true
                    // Handle login logic
                    onNavigateToMain()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .scale(buttonScale),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LoginPrimary,
                    contentColor = White
                )
            ) {
                Text(
                    text = "Log In",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Face ID Button
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clickable { /* Handle Face ID */ },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Face,
                            contentDescription = "Face ID",
                            tint = LoginTextTertiary,
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            text = "Face ID",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = LoginTextTertiary
                        )
                    }
                }
                
                // Contact Admin Text
                Row(
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Don't have an account? ",
                        fontSize = 14.sp,
                        color = LoginTextTertiary
                    )
                    Text(
                        text = "Contact Admin",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = LoginTextPrimary,
                        modifier = Modifier.clickable { /* Handle contact admin */ }
                    )
                }
            }
        }
    }
}
