package com.homeassignment.cocktails.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.homeassignment.cocktails.R
import com.homeassignment.cocktails.data.model.Drink
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CocktailsList(cocktails: List<Drink>) {
    LazyColumn {
        items(items = cocktails, key = { item: Drink -> item.id }) { item ->
            CocktailItem(item = item)
        }

    }
}

@Composable
fun CocktailItem(item: Drink) {
    val padding = dimensionResource(id = R.dimen.drink_holder_surface_padding)
    val imageSize = dimensionResource(id = R.dimen.li_cocktail_image_size)
    Card(
        modifier = Modifier
            .padding(all = padding)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier.background(color = MaterialTheme.colors.secondaryVariant)
        ) {
            GlideImage(
                imageModel = item.imageUrl,
                modifier = Modifier
                    .padding(top = padding, start = padding, bottom = padding)
                    .size(imageSize)
                    .clip(RoundedCornerShape(10.dp))
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = item.name, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.size(24.dp))
                Text(
                    text = item.ingredients.filter { it.isNotEmpty() }.joinToString(", "),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}