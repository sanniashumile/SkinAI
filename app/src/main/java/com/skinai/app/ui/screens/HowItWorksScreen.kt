package com.skinai.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.skinai.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HowItWorksScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("How This Works") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoRow(
                icon = Icons.Rounded.Psychology,
                tint = MaterialTheme.colorScheme.secondary,
                title = "How Scans Are Analyzed",
                body = "Your photo is analyzed using trained machine learning models specific to each scan category. Everything runs securely, and your images are never stored or shared without your action."
            )
            InfoRow(
                icon = Icons.Rounded.WarningAmber,
                tint = MaterialTheme.colorScheme.error,
                title = "Not a Medical Diagnosis",
                body = "This app provides general educational information based on pattern recognition. It cannot examine skin the way a dermatologist can — with dermoscopy, patient history, and clinical judgment. Always treat results as a starting point, not a conclusion."
            )
            InfoRow(
                icon = Icons.Rounded.Percent,
                tint = MaterialTheme.colorScheme.primary,
                title = "Understanding Confidence Scores",
                body = "The confidence percentage reflects how strongly the model associates your photo with a given condition — it is not a probability that you actually have that condition. Low confidence or close scores between conditions mean the result is less certain."
            )
            InfoRow(
                icon = Icons.Rounded.WbSunny,
                tint = MaterialTheme.colorScheme.tertiary,
                title = "Skin Tone Estimate",
                body = "The Fitzpatrick skin type shown is calculated from your photo's colors using a dermatology-standard formula (ITA°). Lighting, camera settings, and filters can all affect this estimate — it's a helpful guide, not a lab measurement."
            )
            InfoRow(
                icon = Icons.Rounded.LocalHospital,
                tint = MaterialTheme.colorScheme.secondary,
                title = "When in Doubt, See a Professional",
                body = "If a result is flagged 'See a Doctor Soon' or 'Urgent', or if something on your skin is changing, bleeding, or concerning you regardless of what the app says — please see a dermatologist."
            )
        }
    }
}

@Composable
private fun InfoRow(icon: ImageVector, tint: Color, title: String, body: String) {
    Card(shape = RoundedCornerShape(16.dp)) {
        Row(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(tint.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(6.dp))
                Text(body, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
