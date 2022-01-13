package com.kosztolanyi.composeyurdu.screens.campaign

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.kosztolanyi.composeyurdu.models.Banner
import com.kosztolanyi.composeyurdu.models.Campaign
import com.kosztolanyi.composeyurdu.ui.theme.LightGray
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduGray
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduOrange

@Composable
fun CampaignContent(campaignList: List<Campaign>) {

    LazyColumn(
        modifier = Modifier
            .background(kitapyurduGray)
            .padding(bottom = 57.dp),
        contentPadding = PaddingValues(vertical = 4.dp)
    ) {
        items(campaignList) { campaign ->
            CampaignItem(campaign = campaign)
        }
    }
}

@Composable
fun CampaignItem(campaign: Campaign) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.White)
            .border(1.dp, LightGray)
            .padding(horizontal = 6.dp)
            .padding(vertical = 6.dp)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp),
            painter = rememberImagePainter(data = campaign.campaignImageUrl),
            contentDescription = "campaign"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Kapra Yayıncılık'tan 10 Kitap Alana 1984 Sepette 1 TL.",
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(10))
                    .border(1.dp, kitapyurduOrange), contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "",
                    tint = kitapyurduOrange
                )
            }
        }
    }
}