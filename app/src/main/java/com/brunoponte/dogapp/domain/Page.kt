package com.brunoponte.dogapp.domain

import com.brunoponte.dogapp.presentation.breedList.SortMode

data class Page(val size: Int, val page: Int, val order: SortMode)