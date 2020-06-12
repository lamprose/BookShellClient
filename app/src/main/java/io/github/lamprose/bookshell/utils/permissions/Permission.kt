package io.github.lamprose.bookshell.utils.permissions

data class Permission(
    var name: String,
    var granted: Boolean,
    var shouldShowRequestPermission: Boolean
)