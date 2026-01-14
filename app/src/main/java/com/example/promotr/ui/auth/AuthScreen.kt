package com.example.promotr.ui.auth

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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.promotr.R
import com.example.promotr.ui.components.BrandTextField
import com.example.promotr.ui.components.OrangeButton
import com.example.promotr.ui.components.ToggleButton
import com.example.promotr.ui.theme.White

@Composable
fun AuthScreen(
    onNavigateToMain: () -> Unit
) {
    var isLoginSelected by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            // Logo
            Image(
                painter = painterResource(id = R.drawable.startlogo),
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Fit
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Toggle Container
            Card(
                modifier = Modifier.fillMaxWidth(0.9f),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    ToggleButton(
                        text = "Login",
                        isSelected = isLoginSelected,
                        onClick = { isLoginSelected = true },
                        modifier = Modifier.weight(1f)
                    )
                    
                    ToggleButton(
                        text = "Sign Up",
                        isSelected = !isLoginSelected,
                        onClick = { isLoginSelected = false },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Input Container
            Column(
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                // Email Input
                BrandTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Email,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                
                // Password Input
                BrandTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    isPassword = true,
                    isPasswordVisible = isPasswordVisible,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                
                // Forgot Password
                Text(
                    text = "Forgot Password?",
                    fontSize = 14.sp,
                    color = Color(0xFF757575),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(bottom = 16.dp)
                        .clickable { /* Handle forgot password */ }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Action Button
                OrangeButton(
                    text = if (isLoginSelected) "Login" else "Sign Up",
                    onClick = {
                        // Handle login/signup logic here
                        onNavigateToMain()
                    }
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Bottom Text Container
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                ) {
                    Text(
                        text = "Don't have an account? ",
                        fontSize = 14.sp,
                        color = Color(0xFF757575)
                    )
                    Text(
                        text = "Sign Up",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = com.example.promotr.ui.theme.BrandOrange,
                        modifier = Modifier.clickable { isLoginSelected = false }
                    )
                }
            }
        }
    }
}
