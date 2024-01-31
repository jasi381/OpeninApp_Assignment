package com.jasmeet.openinapp.appComponents

import android.graphics.Typeface
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.jasmeet.openinapp.R
import com.jasmeet.openinapp.data.DateValue
import com.jasmeet.openinapp.tokenManager.TokenManager
import com.jasmeet.openinapp.ui.theme.figtree
import com.jasmeet.openinapp.utils.convertDateFormat
import com.jasmeet.openinapp.utils.getLineChartData
import com.jasmeet.openinapp.viewModel.MainViewModel
import com.patrykandpatrick.vico.core.entry.entryOf
import java.time.LocalDate
import java.time.Month

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
    val overallUrlChartList by viewModel.overallUrlChartList.observeAsState(listOf())

    val overAllList = overallUrlChartList
        .filter { it.value != 0 }
        .mapIndexed { index, dateValue -> entryOf(index, dateValue.value.toFloat()) }

    val todayClicks = apiResponse?.today_clicks
    val topLocation = apiResponse?.top_location
    val topSource = apiResponse?.top_source

    val recentLinksVm by viewModel.recentLinks.observeAsState(listOf())
    val topLinksVm by viewModel.topLinks.observeAsState(listOf())

    val isAllClicked = rememberSaveable { mutableStateOf(false) }

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

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

    val stroke = Stroke(
        width = 7f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )

    val recentLinksList = if (selectedTabIndex == 0 && isAllClicked.value) recentLinksVm.take(3) else recentLinksVm
    val topLinksList = if (selectedTabIndex != 0 && isAllClicked.value) topLinksVm.take(3) else topLinksVm


    ElevatedCard(
        Modifier
            .navigationBarsPadding()
            .padding(bottom = 25.dp)
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

        Graph(
            pointsData = if (overallUrlChartList.isEmpty()) getLineChartData(
                12,
                start = -50,
                maxRange = 50
            )
            else
                getLineChartData(dateValues = overallUrlChartList),
            overallUrlChartList = overallUrlChartList
        )

        Spacer(modifier = Modifier.height(20.dp))

        Cards(todaysClicks = todayClicks, topLocation = topLocation, topSource = topSource)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            title = "View Analytics",
            icon = R.drawable.ic_analytics,
            onClick = {
                Log.d("demo", "${overallUrlChartList}}")
            }
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

        Box(
            Modifier.height(LocalConfiguration.current.screenHeightDp.dp /1.64f)
        ) {
            LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                if (selectedTabIndex == 0) {
                    items(recentLinksList) { link ->

                        Column(Modifier.background(Color.Transparent)) {
                            Surface(
                                Modifier
                                    .padding(horizontal = 16.dp)
                                    .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp)),
                                color = Color.White,
                                shape = RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp)
                            ) {
                                Row(
                                    Modifier
                                        .padding(bottom = 10.dp)
                                        .fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {

                                    NetworkImage(
                                        imageModifier = Modifier
                                            .padding(2.dp)
                                            .size(48.dp),
                                        imgUrl = link.original_image,
                                        containerModifier = Modifier.padding(10.dp)
                                    )

                                    Column(
                                        Modifier
                                            .padding(start = 10.dp)
                                            .weight(1f),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.Start,
                                    ) {
                                        Text(
                                            text = link.title,
                                            style = TextStyle(
                                                fontSize = 14.sp,
                                                lineHeight = 24.sp,
                                                fontFamily = figtree,
                                                fontWeight = FontWeight(400),
                                                color = Color(0xFF000000),
                                            ),
                                            modifier = Modifier,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                        )
                                        Text(
                                            text = convertDateFormat(link.created_at),
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                lineHeight = 18.sp,
                                                fontFamily = figtree,
                                                fontWeight = FontWeight(400),
                                                color = Color(0xFF999CA0),
                                            )
                                        )
                                    }
                                    Spacer(modifier = Modifier.weight(1f))

                                    Column(Modifier.padding(end = 10.dp)) {
                                        Text(
                                            text = "${link.total_clicks.toInt()}",
                                            style = TextStyle(
                                                fontSize = 14.sp,
                                                lineHeight = 24.sp,
                                                fontFamily = figtree,
                                                fontWeight = FontWeight(600),
                                                color = Color(0xFF000000),
                                                textAlign = TextAlign.Center,
                                            ),
                                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                        )
                                        Text(
                                            text = "Clicks",
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                lineHeight = 18.sp,
                                                fontFamily = figtree,
                                                fontWeight = FontWeight(400),
                                                color = Color(0xFF999CA0),
                                            )
                                        )
                                    }


                                }
                            }
                            Surface(
                                Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .drawBehind {
                                        drawRoundRect(
                                            color = Color(0xffa6c7ff),
                                            style = stroke,
                                            cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                                        )
                                    },
                                color = Color(0xffe8f1ff),
                                shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp)
                            ) {
                                Row (
                                    Modifier
                                        .padding(
                                            vertical = 14.5.dp,
                                            horizontal = 12.dp
                                        )
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Text(
                                        text = link.web_link,
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            lineHeight = 16.sp,
                                            fontFamily = figtree,
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF0E6FFF),
                                        ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.ic_copy),
                                        contentDescription = null,
                                        tint = Color(0xff0E6FFF),
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable {
                                                Toast
                                                    .makeText(context, "Copied", Toast.LENGTH_SHORT)
                                                    .show()
                                            }
                                    )

                                }
                            }
                        }

                    }
                } else {
                    items(topLinksList) { top ->
                        Column(Modifier.background(Color.Transparent)) {
                            Surface(
                                Modifier
                                    .padding(horizontal = 16.dp)
                                    .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp)),
                                color = Color.White,
                                shape = RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp)
                            ) {
                                Row(
                                    Modifier
                                        .padding(bottom = 10.dp)
                                        .fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {

                                    NetworkImage(
                                        imageModifier = Modifier
                                            .padding(2.dp)
                                            .size(48.dp),
                                        imgUrl = top.original_image,
                                        containerModifier = Modifier.padding(10.dp)
                                    )

                                    Column(
                                        Modifier
                                            .padding(start = 10.dp)
                                            .weight(1f),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.Start,
                                    ) {
                                        Text(
                                            text = top.title,
                                            style = TextStyle(
                                                fontSize = 14.sp,
                                                lineHeight = 24.sp,
                                                fontFamily = figtree,
                                                fontWeight = FontWeight(400),
                                                color = Color(0xFF000000),
                                            ),
                                            modifier = Modifier,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                        )
                                        Text(
                                            text = convertDateFormat(top.created_at),
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                lineHeight = 18.sp,
                                                fontFamily = figtree,
                                                fontWeight = FontWeight(400),
                                                color = Color(0xFF999CA0),
                                            )
                                        )
                                    }
                                    Spacer(modifier = Modifier.weight(1f))

                                    Column(Modifier.padding(end = 10.dp)) {
                                        Text(
                                            text = "${top.total_clicks}",
                                            style = TextStyle(
                                                fontSize = 14.sp,
                                                lineHeight = 24.sp,
                                                fontFamily = figtree,
                                                fontWeight = FontWeight(600),
                                                color = Color(0xFF000000),
                                                textAlign = TextAlign.Center,
                                            ),
                                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                        )
                                        Text(
                                            text = "Clicks",
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                lineHeight = 18.sp,
                                                fontFamily = figtree,
                                                fontWeight = FontWeight(400),
                                                color = Color(0xFF999CA0),
                                            )
                                        )
                                    }


                                }
                            }
                            Surface(
                                Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .drawBehind {
                                        drawRoundRect(
                                            color = Color(0xffa6c7ff),
                                            style = stroke,
                                            cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                                        )
                                    },
                                color = Color(0xffe8f1ff),
                                shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp)
                            ) {
                                Row (
                                    Modifier
                                        .padding(
                                            vertical = 14.5.dp,
                                            horizontal = 12.dp
                                        )
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Text(
                                        text = top.web_link,
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            lineHeight = 16.sp,
                                            fontFamily = figtree,
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF0E6FFF),
                                        ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.ic_copy),
                                        contentDescription = null,
                                        tint = Color(0xff0E6FFF),
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable {
                                                Toast
                                                    .makeText(context, "Copied", Toast.LENGTH_SHORT)
                                                    .show()
                                            }
                                    )

                                }
                            }
                        }

                    }
                }

            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            icon = R.drawable.ic_links,
            title = "View all Links",
            onClick = {
                isAllClicked.value = !isAllClicked.value
            }
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


@Composable
fun Graph(pointsData: List<Point>, overallUrlChartList: List<DateValue>) {

    val listSize = overallUrlChartList.filter { it.value != 0 }.size
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .height(200.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .fillMaxWidth()

    ) {
        val xAxisData = AxisData.Builder()
            .steps(listSize)
            .labelData { i ->
                val monthLabel = getMonthLabelForIndex(i)
                monthLabel
            }
            .axisLineColor(Color(0xfff1f2f2))
            .build()

        val yAxisData = AxisData.Builder()
            .steps(4)
            .labelData { i ->
                (i * 25).toString()
            }
            .axisLineColor(Color.Black)
            .build()

        val data = LineChartData(
            linePlotData = LinePlotData(
                lines = listOf(
                    Line(
                        dataPoints = pointsData,
                        lineStyle = LineStyle(
                            lineType = LineType.SmoothCurve(isDotted = true),
                            color = Color(0xff3e8bff)
                        ),
                        shadowUnderLine = ShadowUnderLine(
                            brush = Brush.verticalGradient(
                                listOf(
                                    Color(0xff3e8bff),
                                    Color.Transparent
                                ),
                            ), alpha = 0.3f
                        ),
                        selectionHighlightPoint = SelectionHighlightPoint(
                            color = Color(0xff3e8bff)
                        ),
                        selectionHighlightPopUp = SelectionHighlightPopUp(
                            backgroundColor = Color.Black,
                            backgroundStyle = Stroke(2f),
                            labelColor = Color.Red,
                            labelTypeface = Typeface.DEFAULT_BOLD
                        )
                    )
                )
            ),
            xAxisData = xAxisData,
            yAxisData = yAxisData
        )

        LineChart(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            lineChartData = data
        )

    }

}

private fun getMonthLabelForIndex(index: Int): String {
    val currentMonth = LocalDate.now().monthValue
    val targetMonth = (currentMonth + index - 1) % 12 + 1
    return Month.of(targetMonth).toString().substring(0, 3)
}
