package com.example.promotr.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.promotr.ui.models.JobType
import com.example.promotr.ui.theme.JobTypeMultiDay
import com.example.promotr.ui.theme.JobTypeMultiDayBg
import com.example.promotr.ui.theme.JobTypeOneOff
import com.example.promotr.ui.theme.JobTypeOneOffBg
import com.example.promotr.ui.theme.JobTypeUrgent
import com.example.promotr.ui.theme.JobTypeUrgentBg

@Composable
fun JobTypeBadge(
    type: JobType,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor, label) = when (type) {
        JobType.ONE_OFF_EVENT -> Triple(
            JobTypeOneOffBg,
            JobTypeOneOff,
            "ONE-OFF EVENT"
        )
        JobType.URGENT_FILL -> Triple(
            JobTypeUrgentBg,
            JobTypeUrgent,
            "URGENT FILL"
        )
        JobType.MULTI_DAY -> Triple(
            JobTypeMultiDayBg,
            JobTypeMultiDay,
            "MULTI-DAY"
        )
    }

    Text(
        text = label,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = textColor,
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}
