package com.example.anshsleetcodetracker.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.anshsleetcodetracker.R

@Composable
fun CustomDropdownMenu(
    modifier: Modifier,
    options: List<String>, // List of options to show in the dropdown
    onOptionSelected: (String) -> Unit // Callback to handle selection
) {
    var expanded by remember { mutableStateOf(false) } // Tracks if the dropdown is expanded
    var selectedOption by remember { mutableStateOf("") } // Tracks the selected option

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF1D2022))
            .border(1.dp, Color.White),
    ) {
        // Display the currently selected option
        Row {
            Text(
                text = selectedOption,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true } // Toggle dropdown visibility
                    .background(
                        Color(0xFF2A2E31),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .padding(12.dp),
                color = Color.White
            )
            Icon(painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                contentDescription = "")
        }


        // Dropdown menu
        DropdownMenu(
            modifier = modifier
//                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFF1D2022)),
            expanded = expanded,
            onDismissRequest = { expanded = false } // Close dropdown when clicked outside
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    modifier= Modifier
                        .background(Color(0xFF1D2022)),
                    onClick =
                    {
                        selectedOption = option // Update the selected option
                        onOptionSelected(option) // Notify parent of the selection
                        expanded = false // Close dropdown
                    },
                    text = { Text(text = option,
                        color = Color.White) },
                    colors = MenuDefaults.itemColors(
                        textColor = Color.Black
                    )
                )
            }
        }
    }
}
