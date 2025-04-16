package com.lbg.assessment.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lbg.assessment.R
import com.lbg.assessment.presentation.viewmodel.UserViewModel
import com.lbg.core.AppConstants.ADDRESS
import com.lbg.core.AppConstants.COMPANY
import com.lbg.core.AppConstants.EMAIL
import com.lbg.core.AppConstants.LOCATION
import com.lbg.core.AppConstants.PHONE
import com.lbg.core.AppConstants.USER_NAME
import com.lbg.core.AppConstants.WEB_SITE
import com.lbg.domain.model.User

@Composable
fun UserDetailsScreen(userJson: String?, viewModel: UserViewModel = hiltViewModel()) {
    val user = remember { mutableStateOf<User?>(null) }
    LaunchedEffect(user) {
        userJson?.let {
            user.value = viewModel.decodeFromJson(it)
        }
    }

    user.value?.let {
        Scaffold(topBar = { AppBar(stringResource(R.string.users_details)) }) { paddingValue ->
            Column(
                modifier = Modifier
                    .padding(paddingValue)
                    .fillMaxSize()
            ) {
                Title(it.name)
                UserProperty(USER_NAME, it.username)
                UserProperty(EMAIL, it.email)
                UserProperty(PHONE, it.phone)
                UserProperty(WEB_SITE, it.website)
                UserProperty(
                    ADDRESS,
                    "${it.address.city}, " +
                            "${it.address.street}, " +
                            "${it.address.suite}, " +
                            it.address.zipcode
                )
                UserProperty(LOCATION, it.address.geo.lat + it.address.geo.lng)
                UserProperty(COMPANY, it.company.name)
            }

        }
    } ?: run {
        Scaffold(topBar = { AppBar(stringResource(R.string.users_details)) }) { paddingValue ->
            Column(
                modifier = Modifier
                    .padding(paddingValue)
                    .fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun Title(name: String) {
    Text(
        modifier = Modifier.padding(start = 16.dp),
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        text = name
    )
}

@Composable
fun UserProperty(label: String, name: String) {
    Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)) {
        Divider(modifier = Modifier.padding(bottom = 1.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .height(24.dp)
                .padding(top = 4.dp)
        )
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .height(24.dp)
                .padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserDetailsScreenPreview(viewModel: UserViewModel = hiltViewModel()) {
    val userJson = remember { mutableStateOf<String?>(null) }
    val user = User(
        1,
        "Leanne Graham",
        "Sincere@april.biz",
        "1-770-736-8031 x56442",
        "Bret",
        User.Address(
            "Kulas Light",
            "Apt. 556",
            "Gwenborough",
            "92998-3874",
            User.Location(
                "-37.3159",
                "81.1496"
            )
        ),
        "hildegard.org",
        User.Company(
            "Romaguera-Crona",
            "Multi-layered client-server neural-net",
            "harness real-time e-markets"
        )
    )
    LaunchedEffect(user) {
        userJson.value = viewModel.encodeUserObjectToJson(user)
    }
    userJson.value?.let {
        UserDetailsScreen(it)
    }
}