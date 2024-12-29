package com.example.anshsleetcodetracker.Components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AuthTextField(modifier: Modifier, label:String, value:String, placeHolder:String, onChange:(String)->Unit) {
    Card(colors = CardDefaults.cardColors(
        containerColor = Color(0xFF1D2022)
    ),
        modifier=modifier) {
        Text(text = label,
            modifier=Modifier.padding(start = 16.dp, top = 16.dp),
            fontSize = 12.sp,
            color = Color.White)
        TextField(value = value,
            placeholder = { Text(text = placeHolder,
                color = Color(0xFF393F42))},
            onValueChange = { newValue -> onChange(newValue) },
            modifier= Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ))
    }
}