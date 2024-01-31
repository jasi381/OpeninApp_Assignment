package com.jasmeet.openinapp.appComponents

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jasmeet.openinapp.GreetingsComponent
import com.jasmeet.openinapp.R
import com.jasmeet.openinapp.tokenManager.TokenManager
import com.jasmeet.openinapp.ui.theme.figtree
import com.jasmeet.openinapp.viewModel.MainViewModel

/**
 * @author: Jasmeet Singh
 */
@Composable
fun GraphComponent() {

    val context = LocalContext.current
    val tokenManager = TokenManager(context)
    val viewModel = MainViewModel(tokenManager)

    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    val apiResponse by viewModel.apiResponse.observeAsState()

    val todayClicks = apiResponse?.today_clicks
    val topLocation = apiResponse?.top_location
    val topSource = apiResponse?.top_source

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    val currentList = if (selectedTabIndex == 0) topLinks else recentLinks

    var showAllItems by rememberSaveable { mutableStateOf(false) }

    val displayedList =
        if (showAllItems) currentList else currentList.take(4)


    val topLinksModifier = Modifier
        .clickable { selectedTabIndex = 0 }
        .background(
            color = if (selectedTabIndex == 0) Color(0xff0e6fff) else Color.Transparent,
            shape = RoundedCornerShape(18.dp)
        )
        .padding(vertical = 6.dp, horizontal = 10.dp)

    val recentLinksModifier = Modifier
        .clickable { selectedTabIndex = 1 }
        .background(
            color = if (selectedTabIndex == 1) Color(0xff0e6fff) else Color.Transparent,
            shape = RoundedCornerShape(18.dp)
        )
        .padding(vertical = 8.dp, horizontal = 10.dp)



    ElevatedCard(
        Modifier
            .fillMaxSize()
            .offset(y = (-12).dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color(0xFFF5F5F5)
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 0.dp
        ),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {

        GreetingsComponent(
            greetingMessage = "Good morning",
            name = "Ajay Manva"
        )
        Spacer(modifier = Modifier.height(24.dp))

        Graph()

        Spacer(modifier = Modifier.height(20.dp))

        Cards(todaysClicks= todayClicks, topLocation = topLocation, topSource = topSource)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            title = "View Analytics",
            icon = R.drawable.ic_analytics,
            onClick = { }
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Text(
                text = "Top Links",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = figtree,
                    fontWeight = FontWeight(600),
                    color = if (selectedTabIndex == 0) Color(0xFFFFFFFF) else Color(0xff999ca0),
                ),
                modifier = topLinksModifier
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Recent Links",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = figtree,
                    fontWeight = FontWeight(600),
                    color = if (selectedTabIndex == 1) Color(0xFFFFFFFF) else Color(0xff999ca0),
                ),
                modifier = recentLinksModifier
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                Modifier
                    .background(Color(0xfff2f2f2))
                    .clickable { }
                    .border(
                        1.dp,
                        color = Color(0xffd8d8d8),
                        RoundedCornerShape(8.dp)
                    )
                    .size(36.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color(0xff999ca0)
                )
            }


        }
        Spacer(modifier = Modifier.height(28.dp))

        Box(Modifier.height(LocalConfiguration.current.screenHeightDp.dp / 3f)) {
            LazyColumn {
                items(displayedList) { link ->
                    Text(text = link.title, modifier = Modifier.padding(16.dp))
                    Divider(color = Color.Gray, thickness = 1.dp)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            icon = R.drawable.ic_links,
            title = if (showAllItems) "View less Links" else "View all Links",
            onClick = { showAllItems = !showAllItems }
        )
        Spacer(modifier = Modifier.height(40.dp))

        OutlinedButtonMiscellaneous(
            title = "Talk with us",
            img = R.drawable.ic_whatsapp,
            backgroundColor = Color(0x524AD15F),
            borderColor = Color(0x524AD15F),
            onClick = {}
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButtonMiscellaneous(
            title = "Frequently Asked Questions",
            img = R.drawable.ic_quest,
            backgroundColor = Color(0xFFE8F1FF),
            borderColor = Color(0x520E6FFF),
            onClick = {}
        )


    }


}


/**
 * You can implement your logic for click on card item
 */

@Composable
private fun Cards(
    todaysClicks: Int? = 25,
    topLocation: String? = "India",
    topSource: String? = "Instagram"
) {
    val context = LocalContext.current
    Row(
        Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        CardItem(
            icon = R.drawable.ic_total_clicks,
            title = "${todaysClicks ?: 0}",
            description = "Today's Clicks",
            onItemClick = {
                Toast.makeText(context, "Today's Clicks", Toast.LENGTH_SHORT).show()
            }
        )

        Spacer(modifier = Modifier.width(16.dp))

        CardItem(
            icon = R.drawable.ic_location,
            title = "$topLocation",
            description = "Top Location",
            onItemClick = {
                Toast.makeText(context, "Top Location", Toast.LENGTH_SHORT).show()
            }
        )

        Spacer(modifier = Modifier.width(16.dp))

        CardItem(
            icon = R.drawable.ic_source,
            title = "$topSource",
            description = "Top source",
            imgTint = Color(0xffff4e64),
            imgBackground = Color(0xffffe9ec),
            onItemClick = {
                Toast.makeText(context, "Top source", Toast.LENGTH_SHORT).show()
            }
        )
    }
}



/**
 * Todo: Add graph
 */
@Composable
fun Graph(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White, RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .height(200.dp)
    ) {}

}

data class Link(val title: String, val url: String)

val topLinks = listOf(
    Link("Top Link 1", "https://example.com/top1"),
    Link("Top Link 2", "https://example.com/top2"),
    Link("Top Link 2", "https://example.com/top2"),
    Link("Top Link 2", "https://example.com/top2"),
    Link("Top Link 2", "https://example.com/top2"),
    Link("Top Link 2", "https://example.com/top2"),
    Link("Top Link 2", "https://example.com/top2"),
    Link("Top Link 2", "https://example.com/top2"),

    )
val recentLinks = listOf(
    Link("Recent Link 1", "https://example.com/recent1"),
    Link("Recent Link 2", "https://example.com/recent2"),
    Link("Recent Link 2", "https://example.com/recent2"),
    Link("Recent Link 2", "https://example.com/recent2"),
    Link("Recent Link 2", "https://example.com/recent2"),
    Link("Recent Link 2", "https://example.com/recent2"),
    Link("Recent Link 2", "https://example.com/recent2"),
    Link("Recent Link 2", "https://example.com/recent2"),
    Link("Recent Link 2", "https://example.com/recent2"),
    // Add more recent links as needed
)