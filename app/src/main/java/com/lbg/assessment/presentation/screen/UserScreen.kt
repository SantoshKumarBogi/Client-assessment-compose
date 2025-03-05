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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lbg.assessment.R
import com.lbg.assessment.presentation.viewmodel.UserViewModel
import com.lbg.domain.model.User

@Composable
fun UserScreen(viewModel: UserViewModel = hiltViewModel()) {

    val users = viewModel.users.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val error = viewModel.errorMessage.collectAsState()

    Scaffold(topBar = {
        AppBar(title = stringResource(R.string.users_list))
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading.value -> CircularProgressIndicator()
                error.value != null -> Text(text = error.value.let { error ->
                    error + stringResource(R.string.error)
                })

                users.value.isNotEmpty() ->
                    LazyColumn {
                        items(users.value.size) { index ->
                            UserItem(users.value[index])
                        }
                    }

                else -> Text(
                    text = stringResource(R.string.no_users_found),
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                )
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