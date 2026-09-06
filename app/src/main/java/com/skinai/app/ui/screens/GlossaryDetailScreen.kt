package com.skinai.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skinai.app.data.model.ConditionInfo
import com.skinai.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlossaryDetailScreen(
    info: ConditionInfo,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(info.displayName) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 20.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { UrgencyPill(info) }
            item {
                Card(shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        SectionHeaderG(stringResource(R.string.result_overview), Icons.Rounded.Info)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(info.overview, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
            if (info.symptoms.isNotEmpty()) item { BulletCardG(stringResource(R.string.result_symptoms), Icons.Rounded.Checklist, info.symptoms) }
            if (info.warningSigns.isNotEmpty()) item { BulletCardG(stringResource(R.string.result_warning_signs), Icons.Rounded.Warning, info.warningSigns, isWarning = true) }
            if (info.precautions.isNotEmpty()) item { BulletCardG(stringResource(R.string.result_precautions), Icons.Rounded.Block, info.precautions) }
            if (info.careTips.isNotEmpty()) item { BulletCardG(stringResource(R.string.result_care_tips), Icons.Rounded.CheckCircle, info.careTips) }
            if (info.lifestyleAdvice.isNotEmpty()) item { BulletCardG(stringResource(R.string.result_lifestyle), Icons.Rounded.Spa, info.lifestyleAdvice) }
            item {
                Card(shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        SectionHeaderG(stringResource(R.string.result_when_to_see_doctor), Icons.Rounded.LocalHospital)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(info.whenToSeeDoctor, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Composable
private fun UrgencyPill(info: ConditionInfo) {
    val color = Color(info.urgency.colorHex)
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(color))
        Spacer(modifier = Modifier.width(8.dp))
        Text(info.urgency.label, color = color, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
private fun BulletCardG(title: String, icon: ImageVector, items: List<String>, isWarning: Boolean = false) {
    Card(shape = RoundedCornerShape(16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            SectionHeaderG(title, icon, tint = if (isWarning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(8.dp))
            items.forEach {
                Row(modifier = Modifier.padding(vertical = 3.dp)) {
                    Text("•  ", color = if (isWarning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(it, style = MaterialTheme.typography.bodyMedium, color = if (isWarning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}

@Composable
private fun SectionHeaderG(title: String, icon: ImageVector, tint: Color = MaterialTheme.colorScheme.secondary) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
    }
}
