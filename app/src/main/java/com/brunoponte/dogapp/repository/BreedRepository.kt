package com.brunoponte.dogapp.repository

import com.brunoponte.dogapp.cache.daos.BreedDao
import com.brunoponte.dogapp.network.IRequestService

class BreedRepository(
    private val requestService: IRequestService,
    private val breedDao: BreedDao
) : IBreedRepository {
}