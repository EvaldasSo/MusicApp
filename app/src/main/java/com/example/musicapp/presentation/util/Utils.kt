package com.example.musicapp.presentation.util


fun ConvertSecondsToMMSS(timeInSec: Int): String {
    return String.format(
        "%2dm %2ds",
        (timeInSec / 3600 * 60 + ((timeInSec % 3600) / 60)),
        (timeInSec % 60)
    )
}
