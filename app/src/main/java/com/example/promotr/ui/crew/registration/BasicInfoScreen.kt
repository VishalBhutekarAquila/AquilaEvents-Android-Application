package com.example.promotr.ui.crew.registration

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.promotr.ui.components.LoginTextField
import com.example.promotr.ui.components.ProgressBar
import com.example.promotr.ui.theme.RegistrationBackground
import com.example.promotr.ui.theme.RegistrationPrimary
import com.example.promotr.ui.theme.White

enum class Gender {
    MALE, FEMALE, OTHER
}

@Composable
fun BasicInfoScreen(
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf<Gender?>(null) }
    var termsAccepted by remember { mutableStateOf(false) }
    var isButtonPressed by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val buttonScale by animateFloatAsState(
        targetValue = if (isButtonPressed) 0.98f else 1f,
        animationSpec = tween(100),
        label = "button_scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(RegistrationBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Sticky Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = RegistrationBackground.copy(alpha = 0.95f)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "Back",
                    tint = RegistrationPrimary,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable(onClick = onBack)
                )
                Text(
                    text = "Promoter",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 40.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(32.dp)) // Increased top margin

                // Step Title & Progress
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Step 1 – Basic Info",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827)
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = "Profile setup is 50% complete",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF6B7280)
                            )
                            Text(
                                text = "50%",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = RegistrationPrimary
                            )
                        }
                        ProgressBar(
                            progress = 0.5f,
                            progressColor = RegistrationPrimary,
                            height = 8.dp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Form Fields
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Name
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Name",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF374151)
                        )
                        LoginTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = "",
                            placeholder = "Enter your full name",
                            leadingIcon = null,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Email
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Email ID",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF374151)
                        )
                        LoginTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = "",
                            placeholder = "example@mail.com",
                            leadingIcon = null,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Date of Birth
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Date of Birth",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF374151)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showDatePicker = true }
                        ) {
                            LoginTextField(
                                value = dob,
                                onValueChange = { },
                                label = "",
                                placeholder = "DD/MM/YYYY",
                                leadingIcon = null,
                                modifier = Modifier.fillMaxWidth(),
                                readOnly = true
                            )
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = "Calendar",
                                tint = RegistrationPrimary,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 16.dp)
                            )
                        }
                    }

                    // Address
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Address & City",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF374151)
                        )
                        androidx.compose.foundation.layout.Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(112.dp)
                                .background(
                                    color = White,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFE5E7EB),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(16.dp)
                        ) {
                            androidx.compose.foundation.text.BasicTextField(
                                value = address,
                                onValueChange = { address = it },
                                modifier = Modifier.fillMaxSize(),
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontSize = 16.sp,
                                    color = Color(0xFF111827)
                                )
                            )
                            if (address.isEmpty()) {
                                Text(
                                    text = "Street, City",
                                    fontSize = 16.sp,
                                    color = Color(0xFF9CA3AF)
                                )
                            }
                        }
                    }

                    // Gender Selection
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Gender",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF374151)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            GenderButton(
                                text = "Male",
                                isSelected = selectedGender == Gender.MALE,
                                onClick = { selectedGender = Gender.MALE },
                                modifier = Modifier.weight(1f)
                            )
                            GenderButton(
                                text = "Female",
                                isSelected = selectedGender == Gender.FEMALE,
                                onClick = { selectedGender = Gender.FEMALE },
                                modifier = Modifier.weight(1f)
                            )
                            GenderButton(
                                text = "Other",
                                isSelected = selectedGender == Gender.OTHER,
                                onClick = { selectedGender = Gender.OTHER },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Terms Checkbox
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Checkbox(
                            checked = termsAccepted,
                            onCheckedChange = { termsAccepted = it },
                            colors = androidx.compose.material3.CheckboxDefaults.colors(
                                checkedColor = RegistrationPrimary
                            )
                        )
                        Text(
                            text = "I agree to the ",
                            fontSize = 14.sp,
                            color = Color(0xFF4B5563)
                        )
                        Text(
                            text = "Terms of Service",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = RegistrationPrimary,
                            modifier = Modifier.clickable { /* Open terms */ }
                        )
                        Text(
                            text = " & ",
                            fontSize = 14.sp,
                            color = Color(0xFF4B5563)
                        )
                        Text(
                            text = "Privacy Policy",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = RegistrationPrimary,
                            modifier = Modifier.clickable { /* Open privacy */ }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(120.dp)) // Increased bottom spacing
            }
        }

        // Date Picker Dialog
        if (showDatePicker) {
            AlertDialog(
                onDismissRequest = { showDatePicker = false },
                title = { Text("Select Date of Birth") },
                text = {
                    Text("Please enter your date of birth in DD/MM/YYYY format.")
                },
                confirmButton = {
                    TextButton(
                        onClick = { showDatePicker = false }
                    ) {
                        Text("OK")
                    }
                }
            )
        }

        // Fixed Bottom Button
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    color = RegistrationBackground.copy(alpha = 0.8f)
                )
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    isButtonPressed = true
                    onNext()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .scale(buttonScale),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RegistrationPrimary,
                    contentColor = White
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 4.dp
                )
            ) {
                Text(
                    text = "Next",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun GenderButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) RegistrationPrimary.copy(alpha = 0.1f) else White,
        animationSpec = tween(200),
        label = "gender_bg"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) RegistrationPrimary else Color(0xFFE5E7EB),
        animationSpec = tween(200),
        label = "gender_border"
    )

    val textColor by animateColorAsState(
        targetValue = if (isSelected) RegistrationPrimary else Color(0xFF374151),
        animationSpec = tween(200),
        label = "gender_text"
    )

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = spring(),
        label = "gender_scale"
    )

    Box(
        modifier = modifier
            .height(48.dp)
            .scale(scale)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}
