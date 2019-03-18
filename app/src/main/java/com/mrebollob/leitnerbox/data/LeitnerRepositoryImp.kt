package com.mrebollob.leitnerbox.data

import com.mrebollob.leitnerbox.data.api.LeitnerBoxApiService
import com.mrebollob.leitnerbox.data.entity.LevelEntity
import com.mrebollob.leitnerbox.data.utils.NetworkHandler
import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.functional.Either
import com.mrebollob.leitnerbox.domain.model.Level
import com.mrebollob.leitnerbox.domain.model.Question
import com.mrebollob.leitnerbox.domain.repository.LeitnerRepository
import retrofit2.Call

class LeitnerRepositoryImp(
    private val wibleApiService: LeitnerBoxApiService,
    private val networkHandler: NetworkHandler
) : LeitnerRepository {


    override suspend fun levels(day: Int): Either<Failure, Level> {
        return when (networkHandler.isConnected) {
            true -> request(
                wibleApiService.levels(day),
                { it.toLevel() },
                LevelEntity.empty()
            )
            false -> Either.Left(Failure.NetworkConnection)
        }
    }

    override suspend fun questions(level: Int): Either<Failure, List<Question>> {
        TODO("not implemented")
    }

    private fun <T, R> request(
        call: Call<T>,
        transform: (T) -> R,
        default: T
    ): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> Either.Left(Failure.ServerError)
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerError)
        }
    }
}