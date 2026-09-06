package com.skinai.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.skinai.app.R
import com.skinai.app.data.model.ScanCategory
import com.skinai.app.ui.theme.*

@Composable
fun HomeScreen(
    onCategorySelected: (ScanCategory) -> Unit,
    onHistoryClick: () -> Unit,
    onGlossaryClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onContactDermatologistClick: () -> Unit,
    onHowItWorksClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                HeroHeader(
                    onProfileClick = onProfileClick,
                    onSettingsClick = onSettingsClick,
                    onHowItWorksClick = onHowItWorksClick
                )
            }

            item {
                Text(
                    stringResource(R.string.home_choose_scan_type),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp).padding(top = 16.dp)
                )
            }

            items(ScanCategory.entries.toList()) { category ->
                Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                    CategoryCard(category = category, onClick = { onCategorySelected(category) })
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionCard(
                        title = stringResource(R.string.home_scan_history),
                        icon = Icons.Rounded.History,
                        tint = AccentForest,
                        modifier = Modifier.weight(1f),
                        onClick = onHistoryClick
                    )
                    QuickActionCard(
                        title = stringResource(R.string.home_glossary),
                        icon = Icons.Rounded.MenuBook,
                        tint = GradientMutedTeal,
                        modifier = Modifier.weight(1f),
                        onClick = onGlossaryClick
                    )
                    QuickActionCard(
                        title = stringResource(R.string.contact_dermatologist),
                        icon = Icons.Rounded.LocalHospital,
                        tint = AccentSage,
                        modifier = Modifier.weight(1f),
                        onClick = onContactDermatologistClick
                    )
                }
            }

            item {
                Text(
                    stringResource(R.string.home_disclaimer),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun HeroHeader(
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onHowItWorksClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(AccentForest)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = (-40).dp)
                .size(160.dp)
                .clip(CircleShape)
                .background(Brush.radialGradient(listOf(Color.White.copy(alpha = 0.18f), Color.Transparent)))
        )

        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        stringResource(R.string.app_tagline),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
                Row {
                    GlassIconButton(icon = Icons.Rounded.Info, onClick = onHowItWorksClick)
                    Spacer(modifier = Modifier.width(8.dp))
                    GlassIconButton(icon = Icons.Rounded.AccountCircle, onClick = onProfileClick)
                    Spacer(modifier = Modifier.width(8.dp))
                    GlassIconButton(icon = Icons.Rounded.Settings, onClick = onSettingsClick)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun GlassIconButton(icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.18f)),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick, modifier = Modifier.size(38.dp)) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
        }
    }
}

@Composable
fun ScanCategory.localizedName(): String = when (this) {
    ScanCategory.MOLE_CHECK -> stringResource(R.string.category_mole_title)
    ScanCategory.GENERAL_CHECK -> stringResource(R.string.category_general_title)
    ScanCategory.COSMETIC_CHECK -> stringResource(R.string.category_cosmetic_title)
}

@Composable
fun ScanCategory.localizedSubtitle(): String = when (this) {
    ScanCategory.MOLE_CHECK -> stringResource(R.string.category_mole_subtitle)
    ScanCategory.GENERAL_CHECK -> stringResource(R.string.category_general_subtitle)
    ScanCategory.COSMETIC_CHECK -> stringResource(R.string.category_cosmetic_subtitle)
}

@Composable
private fun CategoryCard(category: ScanCategory, onClick: () -> Unit) {
    val (icon, cardColor) = when (category) {
        ScanCategory.MOLE_CHECK -> Icons.Rounded.TrackChanges to AccentForest
        ScanCategory.GENERAL_CHECK -> Icons.Rounded.HealthAndSafety to GradientSageDeep
        ScanCategory.COSMETIC_CHECK -> Icons.Rounded.AutoAwesome to AccentAmber
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = androidx.compose.animation.core.tween(120),
        label = "cardScale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .clip(RoundedCornerShape(22.dp))
            .background(cardColor)
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 20.dp, y = (-20).dp)
                .size(100.dp)
                .clip(CircleShape)
                .background(Brush.radialGradient(listOf(Color.White.copy(alpha = 0.18f), Color.Transparent)))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.22f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    category.localizedName(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    category.localizedSubtitle(),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
            Icon(Icons.Rounded.ChevronRight, contentDescription = null, tint = Color.White.copy(alpha = 0.8f))
        }
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(tint.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                title,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
