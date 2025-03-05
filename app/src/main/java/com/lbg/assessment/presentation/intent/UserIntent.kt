package com.lbg.assessment.presentation.intent

sealed class UserIntent {
    object FetchUsers : UserIntent()
}