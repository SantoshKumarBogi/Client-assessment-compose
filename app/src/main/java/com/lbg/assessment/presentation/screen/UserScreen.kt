@file:OptIn(ExperimentalMaterial3Api::class)

package com.lbg.assessment.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lbg.assessment.presentation.intent.UserIntent
import com.lbg.assessment.presentation.state.UserState
import com.lbg.assessment.presentation.viewmodel.UserViewModel
import com.lbg.domain.model.User

@Composable
fun UserScreen(viewModel: UserViewModel = hiltViewModel()) {

    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.userIntent.send(UserIntent.FetchUsers)
    }

    Scaffold(topBar = {
        AppBar(title = "Users List")
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (state.value) {
                UserState.Loading -> CircularProgressIndicator()
                is UserState.Error -> {
                    @Suppress("CAST_NEVER_SUCCEEDS")
                    Text(text = "Error: ${(state.value as UserState.Error).error}")
                }

                is UserState.Success -> {
                    @Suppress("CAST_NEVER_SUCCEEDS")
                    val users = (state.value as UserState.Success).users
                    if (users.isNotEmpty()) {
                        LazyColumn {
                            items(users.size) { index ->
                                UserItem(users[index])
                            }
                        }
                    } else {
                        Text(
                            text = "No users found",
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.Center)
                        )
                    }
                }

                else -> {}
            }
        }
    }
}


@Composable
fun UserItem(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = user.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = user.email, fontSize = 16.sp, color = Color.Gray)
            Text(text = user.phone, fontSize = 16.sp, color = Color.Gray)
        }
    }
}

@Composable
fun AppBar(title: String) {
    TopAppBar(
        title = { Text(title) }
    )
}