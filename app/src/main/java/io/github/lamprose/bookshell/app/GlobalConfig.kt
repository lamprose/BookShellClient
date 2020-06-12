package io.github.lamprose.bookshell.app

import androidx.lifecycle.ViewModelProvider
import io.github.lamprose.bookshell.app.base.ViewModelFactory

class GlobalConfig {
    var viewModelFactory: ViewModelProvider.NewInstanceFactory = ViewModelFactory()
}