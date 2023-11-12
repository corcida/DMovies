package com.corcida.dmovie.ui.profile

import com.corcida.domain.User

sealed class ProfileUiModel {
    data object Loading : ProfileUiModel()
    class Error(val error: String) : ProfileUiModel()
    class Content(val user: User) : ProfileUiModel()
}
