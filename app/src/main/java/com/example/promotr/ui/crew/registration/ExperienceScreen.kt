package com.example.promotr.ui.crew.registration

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.promotr.ui.components.FileUploadCard
import com.example.promotr.ui.components.ProgressBar
import com.example.promotr.ui.components.SkillChip
import com.example.promotr.ui.theme.RegistrationBackgroundAlt
import com.example.promotr.ui.theme.RegistrationBorder
import com.example.promotr.ui.theme.RegistrationPrimaryExp
import com.example.promotr.ui.theme.RegistrationSurfaceLight
import com.example.promotr.ui.theme.White

@Composable
fun ExperienceScreen(
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    var selectedRoles by remember { mutableStateOf<List<String>>(emptyList()) }
    var selectedExperience by remember { mutableStateOf<String?>(null) }
    var selectedSkills by remember { mutableStateOf(setOf("Brand Ambassador", "Sales")) }
    var customSkill by remember { mutableStateOf("") }
    var isButtonPressed by remember { mutableStateOf(false) }

    val availableSkills = listOf("Event Logistics", "Ticket Scanning", "VIP Host")
    val allSkills = (selectedSkills + availableSkills).toList()

    val buttonScale by animateFloatAsState(
        targetValue = if (isButtonPressed) 0.98f else 1f,
        animationSpec = tween(100),
        label = "button_scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(RegistrationBackgroundAlt)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = RegistrationBackgroundAlt.copy(alpha = 0.95f)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "Back",
                    tint = RegistrationPrimaryExp,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable(onClick = onBack)
                )
                Text(
                    text = "Promoter",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1C140D),
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
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp)) // Increased top margin

                // Progress
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "STEP 2 OF 3",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF9C7349),
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Almost done!",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = RegistrationPrimaryExp
                        )
                    }
                    ProgressBar(
                        progress = 0.85f,
                        progressColor = RegistrationPrimaryExp,
                        height = 8.dp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Title
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Experience & Skills",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1C140D)
                    )
                    Text(
                        text = "Tell us about your background so we can match you with the right events.",
                        fontSize = 16.sp,
                        color = Color(0xFF9C7349)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Preferred Job Roles
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Preferred Job Roles",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1C140D)
                    )
                    JobRolesDropdown(
                        selectedRoles = selectedRoles,
                        onRolesSelected = { selectedRoles = it }
                    )
                    Text(
                        text = "Hold Cmd/Ctrl to select multiple roles",
                        fontSize = 11.sp,
                        color = Color(0xFF9C7349)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Years of Experience
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Years of Experience",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1C140D)
                    )
                    ExperienceDropdown(
                        selectedExperience = selectedExperience,
                        onExperienceSelected = { selectedExperience = it }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Top Skills
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Top Skills",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1C140D)
                        )
                        Text(
                            text = "See all",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = RegistrationPrimaryExp,
                            modifier = Modifier.clickable { }
                        )
                    }
                    // Skills chips with proper wrapping
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // First row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            allSkills.take(3).forEach { skill ->
                                SkillChip(
                                    text = skill,
                                    isSelected = selectedSkills.contains(skill),
                                    onClick = {
                                        selectedSkills = if (selectedSkills.contains(skill)) {
                                            selectedSkills - skill
                                        } else {
                                            selectedSkills + skill
                                        }
                                    }
                                )
                            }
                        }
                        // Second row if more than 3 skills
                        if (allSkills.size > 3) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                allSkills.drop(3).forEach { skill ->
                                    SkillChip(
                                        text = skill,
                                        isSelected = selectedSkills.contains(skill),
                                        onClick = {
                                            selectedSkills = if (selectedSkills.contains(skill)) {
                                                selectedSkills - skill
                                            } else {
                                                selectedSkills + skill
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                    // Custom skill input
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .background(
                                    color = Color(0xFFFCFAF8),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            androidx.compose.foundation.text.BasicTextField(
                                value = customSkill,
                                onValueChange = { customSkill = it },
                                modifier = Modifier.fillMaxSize(),
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontSize = 16.sp,
                                    color = Color(0xFF1C140D)
                                )
                            )
                            if (customSkill.isEmpty()) {
                                Text(
                                    text = "Add a custom skill...",
                                    fontSize = 16.sp,
                                    color = Color(0xFF9C7349).copy(alpha = 0.7f)
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = RegistrationPrimaryExp.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    if (customSkill.isNotEmpty()) {
                                        selectedSkills = selectedSkills + customSkill
                                        customSkill = ""
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                tint = RegistrationPrimaryExp,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Resume Upload
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Resume / Portfolio",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1C140D)
                    )
                    FileUploadCard(
                        title = "Tap to upload file",
                        subtitle = "PDF, DOCX or JPG (Max 5MB)",
                        icon = Icons.Default.UploadFile,
                        onClick = { /* Handle upload */ },
                        aspectRatio = 1f
                    )
                }

                Spacer(modifier = Modifier.height(120.dp)) // Increased bottom spacing
            }
        }

        // Fixed Bottom Button
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    color = White.copy(alpha = 0.8f)
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
                    containerColor = RegistrationPrimaryExp,
                    contentColor = White
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 4.dp
                )
            ) {
                Text(
                    text = "Next Step",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Next",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun JobRolesDropdown(
    selectedRoles: List<String>,
    onRolesSelected: (List<String>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val roles = listOf("DJ", "Promoter", "Volunteer", "Stagecoach", "Hospitality", "Host & Hostess", "Event Management")

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    color = RegistrationSurfaceLight,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = RegistrationBorder,
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable { expanded = true }
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (selectedRoles.isEmpty()) "Select roles..." else selectedRoles.joinToString(", "),
                    fontSize = 16.sp,
                    color = if (selectedRoles.isEmpty()) Color(0xFF9C7349) else Color(0xFF1C140D)
                )
                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = "Expand",
                    tint = Color(0xFF9C7349)
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(200.dp)
        ) {
            roles.forEach { role ->
                DropdownMenuItem(
                    text = { Text(role) },
                    onClick = {
                        onRolesSelected(
                            if (selectedRoles.contains(role)) {
                                selectedRoles - role
                            } else {
                                selectedRoles + role
                            }
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun ExperienceDropdown(
    selectedExperience: String?,
    onExperienceSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val experiences = listOf("0 - 1 years", "1 - 3 years", "3 - 5 years", "5+ years")

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    color = RegistrationSurfaceLight,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = RegistrationBorder,
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable { expanded = true }
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedExperience ?: "Select experience level",
                    fontSize = 16.sp,
                    color = if (selectedExperience == null) Color(0xFF9C7349) else Color(0xFF1C140D)
                )
                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = "Expand",
                    tint = Color(0xFF9C7349)
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            experiences.forEach { exp ->
                DropdownMenuItem(
                    text = { Text(exp) },
                    onClick = {
                        onExperienceSelected(exp)
                        expanded = false
                    }
                )
            }
        }
    }
}
