package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Scaffold(
        bottomBar = { if (!isLandscape) BottomNavigationBar() }
    ) { padding ->
        Row(modifier = Modifier.padding(padding)) {
            if (isLandscape) NavigationRailBar()
            ContentScreen()
        }
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier) {
    Column(modifier.verticalScroll(rememberScrollState())) {
        Spacer(Modifier.height(16.dp))
        SearchBar()
        HomeSection(title = R.string.top_title) {
            AlignBodyRow(
                items = listOf(
                    R.drawable.bali to R.string.title_bali,
                    R.drawable.lombok to R.string.title_lombok,
                    R.drawable.jakarta to R.string.title_jakarta,
                    R.drawable.rajaampat to R.string.title_rajaampat,
                    R.drawable.yogyakarta to R.string.title_yogyakarta,
                    R.drawable.komodoisland to R.string.title_komodoisland
                )
            )
        }
        HomeSection(title = R.string.middle_title) {
            CollectionCardGrid(
                items = listOf(
                    R.drawable.bali to R.string.title_bali,
                    R.drawable.lombok to R.string.title_lombok,
                    R.drawable.jakarta to R.string.title_jakarta,
                    R.drawable.rajaampat to R.string.title_rajaampat,
                    R.drawable.yogyakarta to R.string.title_yogyakarta,
                    R.drawable.komodoisland to R.string.title_komodoisland
                )
            )
        }
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun NavigationRailBar() {
    NavigationRail {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavigationRailItem(
                icon = { Icon(Icons.Default.Home, contentDescription = null) },
                label = { Text(stringResource(R.string.bottom_nav_home)) },
                selected = true,
                onClick = {}
            )
            NavigationRailItem(
                icon = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
                label = { Text(stringResource(R.string.bottom_nav_profile)) },
                selected = false,
                onClick = {}
            )
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text(stringResource(R.string.bottom_nav_home)) },
            selected = true,
            onClick = {}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
            label = { Text(stringResource(R.string.bottom_nav_profile)) },
            selected = false,
            onClick = {}
        )
    }
}

@Composable
fun SearchBar() {
    var searchQuery by remember { mutableStateOf("") }
    TextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        placeholder = { Text(text = "Search") },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .padding(horizontal = 16.dp)
    )
}

@Composable
fun HomeSection(@StringRes title: Int, content: @Composable () -> Unit) {
    Column {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .paddingFromBaseline(top = 40.dp, bottom = 16.dp)
                .padding(horizontal = 16.dp)
        )
        content()
    }
}

@Composable
fun AlignBodyRow(items: List<Pair<Int, Int>>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(items.size) { index ->
            AlignBody(items[index].first, items[index].second)
        }
    }
}

@Composable
fun AlignBody(@DrawableRes drawable: Int, @StringRes text: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(drawable),
            contentDescription = stringResource(text),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
        )
        Text(
            text = stringResource(text),
            modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun CollectionCardGrid(items: List<Pair<Int, Int>>) {
    LazyHorizontalGrid (
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.height(168.dp)
    ) {
        items(items.size) { index ->
            CollectionCard(items[index].first, items[index].second)
        }
    }
}

@Composable
fun CollectionCard(
    @DrawableRes drawable: Int,
    @StringRes text: Int
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(255.dp).height(80.dp)
        ) {
            Image(
                painter = painterResource(drawable),
                contentDescription = stringResource(text),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = stringResource(text),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun PreviewScreen() {
    MyApplicationTheme { MainScreen() }
}
