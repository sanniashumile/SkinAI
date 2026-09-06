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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.skinai.app.data.model.AnalysisSource
import com.skinai.app.data.model.ConditionInfo
import com.skinai.app.data.model.FitzpatrickType
import com.skinai.app.data.model.ScanResult
import com.skinai.app.data.model.UrgencyLevel
import com.skinai.app.R
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    result: ScanResult,
    onBack: () -> Unit,
    onScanAgain: () -> Unit,
    onContactDermatologist: () -> Unit
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.result_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { SkinToneCard(result.skinTone) }
            item { PredictionHeader(result) }
            item { TopPredictionsBar(result.allScores, result.predictedLabel) }
            item { UrgencyBanner(result.conditionInfo.urgency) }
            item { InfoSection(title = stringResource(R.string.result_overview), icon = Icons.Rounded.Info) {
                Text(result.conditionInfo.overview, style = MaterialTheme.typography.bodyMedium)
            } }
            if (result.conditionInfo.symptoms.isNotEmpty()) {
                item { BulletSection(stringResource(R.string.result_symptoms), Icons.Rounded.Checklist, result.conditionInfo.symptoms) }
            }
            if (result.conditionInfo.warningSigns.isNotEmpty()) {
                item { BulletSection(stringResource(R.string.result_warning_signs), Icons.Rounded.Warning, result.conditionInfo.warningSigns, isWarning = true) }
            }
            if (result.conditionInfo.precautions.isNotEmpty()) {
                item { BulletSection(stringResource(R.string.result_precautions), Icons.Rounded.Block, result.conditionInfo.precautions) }
            }
            if (result.conditionInfo.careTips.isNotEmpty()) {
                item { BulletSection(stringResource(R.string.result_care_tips), Icons.Rounded.CheckCircle, result.conditionInfo.careTips) }
            }
            if (result.conditionInfo.lifestyleAdvice.isNotEmpty()) {
                item { BulletSection(stringResource(R.string.result_lifestyle), Icons.Rounded.Spa, result.conditionInfo.lifestyleAdvice) }
            }
            item {
                InfoSection(title = stringResource(R.string.result_when_to_see_doctor), icon = Icons.Rounded.LocalHospital) {
                    Text(result.conditionInfo.whenToSeeDoctor, style = MaterialTheme.typography.bodyMedium)
                }
            }
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.12f)),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
                        Icon(Icons.Rounded.Info, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            stringResource(R.string.result_disclaimer),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
            item {
                if (result.conditionInfo.urgency == UrgencyLevel.SEE_SOON || result.conditionInfo.urgency == UrgencyLevel.URGENT) {
                    Button(
                        onClick = onContactDermatologist,
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(UrgencyLevel.URGENT.colorHex))
                    ) {
                        Icon(Icons.Rounded.LocalHospital, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(stringResource(R.string.contact_dermatologist), fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                Button(
                    onClick = onScanAgain,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(stringResource(R.string.result_scan_again), fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    onClick = {
                        com.skinai.app.util.PdfReportGenerator.generateAndShare(context, result)
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Rounded.PictureAsPdf, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Share Report with Doctor")
                }
            }
        }
    }
}

@Composable
private fun SkinToneCard(skinTone: FitzpatrickType) {
    Card(shape = RoundedCornerShape(16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Rounded.WbSunny, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "${stringResource(R.string.result_skin_tone_prefix)} ${skinTone.romanNumeral} — ${skinTone.label}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(skinTone.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                "${stringResource(R.string.result_sun_reaction)} ${skinTone.sunReaction}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "${stringResource(R.string.result_recommended_spf)} ${skinTone.recommendedSpf}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Rounded.WaterDrop, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(stringResource(R.string.result_sun_care_tips), style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(6.dp))
            skinTone.sunCareTips.forEach {
                Row(modifier = Modifier.padding(vertical = 2.dp)) {
                    Text("•  ", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(it, style = MaterialTheme.typography.bodyMedium)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                stringResource(R.string.result_skin_tone_disclaimer),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun TopPredictionsBar(allScores: Map<String, Float>, predictedLabel: String) {
    if (allScores.size <= 1) return // online mode may only have one score; skip if nothing to compare

    val top3 = allScores.entries.sortedByDescending { it.value }.take(3)

    Card(shape = RoundedCornerShape(16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Top Predictions", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(12.dp))
            top3.forEach { (label, score) ->
                val isTop = label == predictedLabel
                val percent = (score * 100).roundToInt()
                Column(modifier = Modifier.padding(vertical = 5.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            label,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = if (isTop) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isTop) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            "$percent%",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = if (isTop) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isTop) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    val animatedProgress by androidx.compose.animation.core.animateFloatAsState(
                        targetValue = score,
                        animationSpec = androidx.compose.animation.core.tween(700),
                        label = "predictionBar"
                    )
                    LinearProgressIndicator(
                        progress = { animatedProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(50)),
                        color = if (isTop) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun PredictionHeader(result: ScanResult) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 20.dp, y = (-20).dp)
                .size(110.dp)
                .clip(CircleShape)
                .background(Brush.radialGradient(listOf(Color.White.copy(alpha = 0.18f), Color.Transparent)))
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    result.conditionInfo.displayName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    result.category.localizedName(),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
            ConfidenceRing(confidence = result.confidence)
        }
    }
}

@Composable
private fun ConfidenceRing(confidence: Float) {
    val animatedConfidence by androidx.compose.animation.core.animateFloatAsState(
        targetValue = confidence,
        animationSpec = androidx.compose.animation.core.tween(1000),
        label = "confidenceRing"
    )
    val percent = (animatedConfidence * 100).roundToInt()

    Box(
        modifier = Modifier.size(64.dp),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 6.dp.toPx()
            drawArc(
                color = Color.White.copy(alpha = 0.25f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth, cap = androidx.compose.ui.graphics.StrokeCap.Round),
                size = androidx.compose.ui.geometry.Size(size.width - strokeWidth, size.height - strokeWidth),
                topLeft = androidx.compose.ui.geometry.Offset(strokeWidth / 2, strokeWidth / 2)
            )
            drawArc(
                color = Color.White,
                startAngle = -90f,
                sweepAngle = 360f * animatedConfidence,
                useCenter = false,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth, cap = androidx.compose.ui.graphics.StrokeCap.Round),
                size = androidx.compose.ui.geometry.Size(size.width - strokeWidth, size.height - strokeWidth),
                topLeft = androidx.compose.ui.geometry.Offset(strokeWidth / 2, strokeWidth / 2)
            )
        }
        Text("$percent%", fontWeight = FontWeight.Bold, color = Color.White, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
private fun UrgencyBanner(urgency: UrgencyLevel) {
    val color = Color(urgency.colorHex)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(color))
        Spacer(modifier = Modifier.width(10.dp))
        Text(urgency.label, fontWeight = FontWeight.SemiBold, color = color)
    }
}

@Composable
private fun InfoSection(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable () -> Unit
) {
    Card(shape = RoundedCornerShape(16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            SectionHeader(title, icon)
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
private fun BulletSection(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    items: List<String>,
    isWarning: Boolean = false
) {
    Card(shape = RoundedCornerShape(16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            SectionHeader(title, icon, tint = if (isWarning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(8.dp))
            items.forEach {
                Row(modifier = Modifier.padding(vertical = 3.dp)) {
                    Text("•  ", color = if (isWarning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(
                        it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isWarning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, tint: Color = MaterialTheme.colorScheme.secondary) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
    }
}
