package com.littlezombie.shurover.view.compose.home

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.littlezombie.shurover.view.compose.group.GroupSongScreen
import com.littlezombie.shurover.view.compose.scout.ScoutSongScreen
import com.littlezombie.shurover.view.ui.theme.Dark_Green
import com.littlezombie.shurover.view.ui.theme.Gray
import com.littlezombie.shurover.view.ui.theme.Green
import com.littlezombie.shurover.view.ui.theme.White
import com.littlezombie.shurover.viewmodel.SongViewModel
import kotlinx.coroutines.launch
import scoutsongs.littlezombie.com.scoutsongs.R

enum class SongPage(@StringRes val titleResId: Int) {
    SCOUT_SONG(R.string.scout_song),
    GROUP_SONG(R.string.group_song)
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    pages: Array<SongPage> = SongPage.values(),
    navController: NavController,
    viewModel: SongViewModel
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })

    Scaffold(
        modifier = modifier,
        topBar = { HomeTopAppBar(modifier = modifier) }
    ) { contentPadding ->
        HomePagerScreen(
            modifier = Modifier.padding(top = contentPadding.calculateTopPadding()),
            pagerState = pagerState,
            pages = pages,
            navController = navController,
            viewModel = viewModel
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePagerScreen(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    pages: Array<SongPage>,
    navController: NavController,
    viewModel: SongViewModel
) {
    Column(modifier) {
        val coroutineScope = rememberCoroutineScope()
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Green,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = Dark_Green,
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                )
            }
        ) {
            pages.forEachIndexed { index, songPage ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = stringResource(id = songPage.titleResId),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    selectedContentColor = White,
                    unselectedContentColor = Gray
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { index ->
            when (pages[index]) {
                SongPage.SCOUT_SONG -> {
                    ScoutSongScreen(modifier = modifier, navController, viewModel)
                }

                SongPage.GROUP_SONG -> {
                    GroupSongScreen(modifier = modifier, navController, viewModel)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        colors = centerAlignedTopAppBarColors(
            containerColor = Green,
            titleContentColor = White
        ),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.logo), contentDescription = "")
                Text(
                    text = stringResource(id = R.string.app_name_chinese_name),
                    Modifier.padding(start = 8.dp)
                )
            }
        }
    )
}