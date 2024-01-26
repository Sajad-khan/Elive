package com.tropat.elive.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.auth.FirebaseAuth
import com.tropat.elive.R
import com.tropat.elive.model.Book
import com.tropat.elive.model.EliveUser
import com.tropat.elive.navigation.EliveScreens
import com.tropat.elive.utils.brush
import com.tropat.elive.utils.myNavigationDrawerItemColors
import kotlinx.coroutines.launch

@Composable
fun HomeScreenContent(navController: NavController){
    val email = FirebaseAuth.getInstance().currentUser?.email
    val viewModel: HomeScreenViewModel = viewModel()
    val data by viewModel.data.collectAsState()
    Log.d("FB_U", data.toString())
    val scope = rememberCoroutineScope()
    var showDialog = remember{mutableStateOf(false)}
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationDrawer(drawerContent = {
        NavigationDrawerContent(showDialog, email, data)
    },
        drawerState = drawerState) {
        Scaffold(modifier = Modifier.background(brush = brush),
            containerColor = Color.Transparent,
            topBar = { HomeScreenTopBar(isHomeScreen = true){ scope.launch { drawerState.open() } } },
            floatingActionButton = {FloatingActionButtonContent{navController.navigate(EliveScreens.SearchScreen.name)}}) {
            if(showDialog.value){
                ShowLogoutDialog(showDialog = showDialog, it, navController)
            }
            HomeScreenMainContent(paddingValues = it, navController = navController)
        }
    }
}

@Composable
fun NavigationDrawerContent(showDialog: MutableState<Boolean>, email: String?, data: EliveUser?) {
    ModalDrawerSheet(
        drawerContainerColor = Color(0xFF397796)
    ) {
        Column{
            Column(modifier = Modifier.padding(start = 15.dp, top = 10.dp, end = 10.dp)) {
                Image(modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = "")
                Spacer(Modifier.height(5.dp))
                if (data != null) {
                    Text(text = "${data.firstName} ${data.lastName}", style = MaterialTheme.typography.titleMedium, color = Color.White)
                }
                Text(text = email.toString(), color = Color.White)
                Spacer(modifier = Modifier.height(5.dp))
            }
            Divider(color = Color.White)
            NavigationDrawerItem(label = { Text(text = "Home")},
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "")},
                selected = false, onClick = { /*TODO*/ },
                colors = myNavigationDrawerItemColors())
            NavigationDrawerItem(label = { Text(text = "Profile") },
                icon = { Icon(imageVector = Icons.Default.Person, contentDescription = "")},
                selected = false, onClick = { /*TODO*/ },
                colors = myNavigationDrawerItemColors())
            NavigationDrawerItem(label = { Text(text = "Logout") },
                icon = { Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "")},
                selected = false,
                onClick = {showDialog.value = true},
                colors = myNavigationDrawerItemColors())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar(isHomeScreen: Boolean, leadingIconClicked: () -> Unit) {
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent),
        navigationIcon = { IconButton(onClick = leadingIconClicked) {
            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
        }},
        title = { Image(painter = painterResource(id = R.drawable.elive_white),
            contentDescription = "Logo",
            modifier = Modifier.size(width = 80.dp, height = 40.dp))})
}

@Composable
fun FloatingActionButtonContent(buttonClicked: () -> Unit) {
    FloatingActionButton(modifier = Modifier
        .padding(5.dp),
        containerColor = Color(0xFF397796),
        onClick = buttonClicked,
        shape = CircleShape
    ) {
        Icon(modifier = Modifier.size(30.dp),
            imageVector = Icons.Default.Add,
            contentDescription = "Add a book",
            tint = Color.White)
    }
}

@Composable
fun HomeScreenMainContent(paddingValues: PaddingValues,
                          navController: NavController) {
    Column(modifier = Modifier
        .padding(paddingValues)
        .padding(16.dp)) {
        ReadingRightNowArea(navController = navController)
        Spacer(modifier = Modifier.padding(20.dp))
        ReadingListArea(navController = navController)
    }
}

@Composable
fun ReadingListArea(navController: NavController) {
    Column {
        Text(text = "Reading list", style = MaterialTheme.typography.titleMedium, color = Color.White)
        BookCard(readingStatus = "Not started")
    }
}

@Composable
fun ShowLogoutDialog(showDialog: MutableState<Boolean>, paddingValues: PaddingValues, navController: NavController){
    val user = FirebaseAuth.getInstance()
   Column(modifier = Modifier
       .padding(paddingValues)
       .background(Color.Transparent)){
       if(showDialog.value){
           AlertDialog(
               containerColor = Color(0xFF3A3A3A),
               title = { Image(painter = painterResource(id = R.drawable.elive_white),
                   contentDescription = "Logo",
                   modifier = Modifier.size(width = 80.dp, height = 40.dp))},
               text = { Text(text = "Do you want to logout?", color = Color.White)},
               onDismissRequest = {showDialog.value = false},
               confirmButton = { Button(onClick = {
                   user.signOut().run {
                       showDialog.value = false
                       navController.popBackStack()
                       navController.navigate(EliveScreens.LoginScreen.name)
                   }
               }) {
                   Text(text = "Yes")
               }
               },
               dismissButton = { Button(onClick = { showDialog.value = false }) {
                   Text(text = "No")
               } })
       }
   }
}


@Composable
fun ReadingRightNowArea(book: List<Book> = listOf(), navController: NavController){
    Column {
        Text(text = "Reading activity", style = MaterialTheme.typography.titleMedium, color = Color.White)
        BookCard(readingStatus = "Reading")
    }
}

@Composable
fun BookCard(book: Book = Book("xy", "hello", listOf(""), ""), readingStatus: String){
    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    Card(
        shape = RoundedCornerShape(29.dp),
        elevation = CardDefaults.elevatedCardElevation(5.dp),
        modifier = Modifier
            .padding(top = 16.dp)
            .width(202.dp)
            .clickable { }
    ) {
        Column {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://m.media-amazon.com/images/I/61O6K0yPmzL._AC_UF350,350_QL80_.jpg")
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.book_placeholder),
                        contentDescription = "",
                        alignment = Alignment.TopStart,
                        modifier = Modifier
                            .height(140.dp)
                            .width(100.dp)
                            .weight(1f)
                            .clip(shape = RoundedCornerShape(topStart = 29.dp))
                    )
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .height(100.dp)) {
                        Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "")
                        Spacer(modifier = Modifier.height(5.dp))
                        BookRating()
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "Book Title", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "Author", style = MaterialTheme.typography.bodySmall)
            }
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End) {
            ReadingStatus(status = readingStatus)
        }
    }
}

@Composable
fun BookRating(score: Double = 4.5){
    Surface(modifier = Modifier
        .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            Icon(imageVector = Icons.Default.Star, contentDescription = "star")
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = score.toString())
        }
    }
}

@Composable
fun ReadingStatus(status: String = "Reading"){
    Surface(shape = RoundedCornerShape(topStart = 29.dp, bottomEnd = 29.dp),
        color = Color(0xFF397796),
        shadowElevation = 10.dp) {
        Row(modifier = Modifier.padding(start = 30.dp, end = 30.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Text(text = status, style = MaterialTheme.typography.titleSmall, color = Color.White)
        }
    }
}

