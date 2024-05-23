package com.example.pf_utils.model

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.pf_utils.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AnimalUIElement(imgPath: String, desc: String) {
    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        GlideImage(model = imgPath, contentDescription = "", modifier=Modifier.size(150.dp)){
            it.error(R.drawable.dog_placeholder)
        }

        Text(text= desc, style = TextStyle(textAlign = TextAlign.Center))

    }
}