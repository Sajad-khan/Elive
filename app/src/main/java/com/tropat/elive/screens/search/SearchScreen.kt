package com.tropat.elive.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tropat.elive.R
import com.tropat.elive.model.books.Item
import com.tropat.elive.utils.EliveAppBar
import com.tropat.elive.utils.brush
import com.tropat.elive.utils.myTextFieldColors
import kotlinx.coroutines.launch

@Composable
fun SearchScreenContent(navController: NavController, searchViewModel: BookSearchViewModel = hiltViewModel()){
    Scaffold(modifier = Modifier.background(brush = brush),
        containerColor = Color.Transparent,
        topBar = { EliveAppBar(title = "Search Book"){navController.popBackStack()} }) {
        SearchScreenMainContent(navController, it, searchViewModel)
    }
}

@Composable
fun SearchScreenMainContent(navController: NavController,
                            paddingValues: PaddingValues,
                            searchViewModel: BookSearchViewModel = hiltViewModel()) {
    var showBooks by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier
        .padding(start = 10.dp, end = 10.dp)
        .padding(paddingValues)) {
        SearchTextField {searchQuery ->
            searchViewModel.getBooks(searchQuery)
        }
        Spacer(modifier = Modifier.height(20.dp))
        BooksList(searchViewModel)
    }
}

@Composable
fun BooksList(searchViewModel: BookSearchViewModel = hiltViewModel()) {
    val bookList = searchViewModel.list
    var showContent by remember { mutableStateOf(false) }
    if (showContent){
        if(searchViewModel.isLoading){
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(color = Color.White)
            }
        }
        else{
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                content = {
                    items(bookList){book ->
                        SearchBookItem(book = book)
                        showContent = true
                    }
                })
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTextField(onSearch: (String) -> Unit) {
    var searchQuery by remember{ mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(40.dp),
        value = searchQuery,
        trailingIcon = {
                       if(searchQuery.isNotEmpty()){
                           Icon(
                               modifier = Modifier.clickable { searchQuery = "" },
                               imageVector = Icons.Default.Clear,
                               contentDescription = "clear",
                               tint = Color.White)
                       }
        },
        onValueChange = {searchQuery = it},
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "")},
        placeholder = { Text(text = "Search for books")},
        label = { Text(text = "Search")},
        colors = myTextFieldColors(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onSearch(searchQuery.trim())
                scope.launch {
                    keyboardController?.hide()
                }

            }
        ),
        maxLines = 1
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBookItem(book: Item) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(10.dp),
        modifier = Modifier.fillMaxWidth(),
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(modifier = Modifier.height(80.dp),
            verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(book.volumeInfo.imageLinks.thumbnail)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.book_placeholder),
                contentDescription = "",
                alignment = Alignment.TopStart,
                modifier = Modifier
                    .fillMaxHeight()
            )
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = book.volumeInfo.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "Authors: ${book.volumeInfo.authors}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "Published on: ${book.volumeInfo.publishedDate}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
            }
        }

    }
}