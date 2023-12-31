/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.brocoli.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import com.example.brocoli.data.RegistrationRepository
import com.example.brocoli.data.DefaultRegistrationRepository
import com.example.brocoli.data.local.database.Registration
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsRegistrationRepository(
        registrationRepository: DefaultRegistrationRepository
    ): RegistrationRepository
}

class FakeRegistrationRepository @Inject constructor() : RegistrationRepository {
    override val registrations: Flow<List<Registration>> = flowOf(fakeRegistrations)

    override suspend fun add(name: String, email: String) {
        throw NotImplementedError()
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }
}

val fakeRegistrations = listOf(
    Registration(name = "Android", email = "android@gmail.com"),
    Registration(name = "Kotlin", email = "kotlin@gmail.com")
)
