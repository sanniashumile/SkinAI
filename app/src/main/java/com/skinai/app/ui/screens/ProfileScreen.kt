package com.skinai.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Badge
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.skinai.app.AppViewModel
import com.skinai.app.R
import java.util.Calendar

/**
 * Profile screen — deliberately avoids ANY native/platform dialog (DatePickerDialog,
 * Material3 DatePicker, etc.) since those have known instability on some OEM Android
 * builds. Date of birth is entered via plain Compose dropdown menus instead, which
 * have zero platform-dialog dependency and cannot fail the same way.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: AppViewModel,
    currentName: String?,
    currentDob: String?,
    currentCnic: String?,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf(currentName ?: "") }
    var cnic by remember { mutableStateOf(currentCnic ?: "") }
    var saved by remember { mutableStateOf(false) }

    val (initialDay, initialMonth, initialYear) = remember(currentDob) { parseDobOrDefault(currentDob) }
    var selectedDay by remember { mutableStateOf(initialDay) }
    var selectedMonth by remember { mutableStateOf(initialMonth) }
    var selectedYear by remember { mutableStateOf(initialYear) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.profile_title)) },
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
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    if (name.isNotBlank()) name.first().uppercase() else "?",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it; saved = false },
                label = { Text(stringResource(R.string.profile_name_label)) },
                leadingIcon = { Icon(Icons.Rounded.Person, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Rounded.CalendarToday, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(R.string.profile_dob_label),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SimpleDropdown(
                    label = "Day",
                    options = (1..31).map { it.toString() },
                    selected = selectedDay.toString(),
                    onSelect = { selectedDay = it.toInt(); saved = false },
                    modifier = Modifier.weight(1f)
                )
                SimpleDropdown(
                    label = "Month",
                    options = monthNames,
                    selected = monthNames[selectedMonth - 1],
                    onSelect = { selectedMonth = monthNames.indexOf(it) + 1; saved = false },
                    modifier = Modifier.weight(1.3f)
                )
                SimpleDropdown(
                    label = "Year",
                    options = (Calendar.getInstance().get(Calendar.YEAR) downTo 1930).map { it.toString() },
                    selected = selectedYear.toString(),
                    onSelect = { selectedYear = it.toInt(); saved = false },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            OutlinedTextField(
                value = cnic,
                onValueChange = { input ->
                    // Simple, crash-proof formatting: keep only digits, cap at 13,
                    // insert dashes at fixed positions. No cursor manipulation —
                    // Compose handles cursor placement safely on its own this way.
                    val digitsOnly = input.filter { it.isDigit() }.take(13)
                    cnic = buildString {
                        digitsOnly.forEachIndexed { i, c ->
                            append(c)
                            if (i == 4 || i == 11) append('-')
                        }
                    }
                    saved = false
                },
                label = { Text(stringResource(R.string.profile_cnic_label)) },
                leadingIcon = { Icon(Icons.Rounded.Badge, contentDescription = null) },
                placeholder = { Text(stringResource(R.string.profile_cnic_hint)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val dobFormatted = "${selectedDay.toString().padStart(2, '0')} " +
                        "${monthNames[selectedMonth - 1]} $selectedYear"
                    viewModel.saveUserProfile(name.trim(), dobFormatted, cnic.trim())
                    saved = true
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(if (saved) stringResource(R.string.profile_saved) else stringResource(R.string.profile_save))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                stringResource(R.string.profile_privacy_note),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

private val monthNames = listOf(
    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
)

/** Parses a previously saved "DD Mon YYYY" string back into (day, month, year), or defaults to today. */
private fun parseDobOrDefault(dob: String?): Triple<Int, Int, Int> {
    val today = Calendar.getInstance()
    val defaultDay = today.get(Calendar.DAY_OF_MONTH)
    val defaultMonth = today.get(Calendar.MONTH) + 1
    val defaultYear = today.get(Calendar.YEAR) - 20 // reasonable default for an adult user

    if (dob.isNullOrBlank()) return Triple(defaultDay, defaultMonth, defaultYear)

    return try {
        val parts = dob.trim().split(" ")
        if (parts.size != 3) return Triple(defaultDay, defaultMonth, defaultYear)
        val day = parts[0].toIntOrNull() ?: defaultDay
        val monthIndex = monthNames.indexOfFirst { it.equals(parts[1].take(3), ignoreCase = true) }
        val month = if (monthIndex >= 0) monthIndex + 1 else defaultMonth
        val year = parts[2].toIntOrNull() ?: defaultYear
        Triple(day, month, year)
    } catch (e: Exception) {
        Triple(defaultDay, defaultMonth, defaultYear)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SimpleDropdown(
    label: String,
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
