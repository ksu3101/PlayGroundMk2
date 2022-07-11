package com.swkang.playground2.domain.billing

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swkang.playground2.R
import com.swkang.playground2.theme.Blue40
import com.swkang.playground2.theme.BlueGrey30

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GemItemCard(
    title: String,
    description: AnnotatedString,
    price: String,
    @DrawableRes bgDrawableRes: Int,
    onItemClicked: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .clickable { onItemClicked() },
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Image(
            painter = painterResource(id = bgDrawableRes),
            contentDescription = "card background image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
                .height(200.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp)
        ) {
            Column {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W900,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(0.6f)
                        .padding(bottom = 6.dp)

                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = BlueGrey30
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right,
                text = price,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun PreviewGemItemCard() {
    GemItemCard(
        title = "보석 100개",
        description = buildAnnotatedString {
            append("테스트용 ")
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = Blue40
                )
            ) {
                append("보석 아이템 100개")
            }
            append("를 구매 합니다.")
        },
        price = "₩ 100,000",
        bgDrawableRes = R.drawable.tscene_18
    ) {
    }
}
