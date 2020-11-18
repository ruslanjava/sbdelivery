package ru.skillbranch.sbdelivery.http

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.skillbranch.sbdelivery.http.data.dishes.CategoryRes
import ru.skillbranch.sbdelivery.http.data.dishes.DishRes
import ru.skillbranch.sbdelivery.http.data.review.ReviewRes

object HttpClient {

    private val LAST_CATEGORY = listOf<CategoryRes>()
    private val LAST_DISH = listOf<DishRes>()
    private val LAST_REVIEW = listOf<ReviewRes>()

    private val service = NetworkManager.api
    private val scope = CoroutineScope(Dispatchers.IO)

    private const val THREADS = 8
    private const val LIMIT = 25

    @ExperimentalCoroutinesApi
    suspend fun getAllCategories(): List<CategoryRes> {
        val result = mutableListOf<CategoryRes>()

        // запускаем параллельные "сборщики" категорий
        val channels = mutableListOf<Channel<List<CategoryRes>>>()
        for (i in 0 until THREADS) {
            val channel = startCategoryChannel(i)
            channels.add(channel)
        }

        // собираем информацию от сборщиков через каналы, пока "жив" хотя бы один из них
        var alive = THREADS
        while (alive > 0) {
            for (i in 0 until THREADS) {
                val channel = channels[i]
                if (channel.isClosedForReceive) {
                    continue
                }

                val categories = channel.receive()
                // паттерн "последний чемодан"
                if (categories == LAST_CATEGORY) {
                    channel.close()
                    alive--
                    continue
                }

                result.addAll(categories)
            }
        }

        return result
    }

    @ExperimentalCoroutinesApi
    suspend fun getRecommendedIds(): List<String> {
        return service.recommendedIds()
    }

    @ExperimentalCoroutinesApi
    suspend fun getAllDishes(): List<DishRes> {
        val result = mutableListOf<DishRes>()

        // запускаем параллельные "сборщики" категорий
        val channels = mutableListOf<Channel<List<DishRes>>>()
        for (i in 0 until THREADS) {
            val channel = startDishChannel(i)
            channels.add(channel)
        }

        // собираем информацию от "сборщиков" через каналы, пока "жив" хотя бы один из них
        var alive = THREADS
        while (alive > 0) {
            for (i in 0 until THREADS) {
                val channel = channels[i]
                if (channel.isClosedForReceive) {
                    continue
                }

                val dishes = channel.receive()
                // паттерн "последний чемодан"
                if (dishes == LAST_DISH) {
                    channel.close()
                    alive--
                    continue
                }

                result.addAll(dishes)
            }
        }

        return result
    }

    @ExperimentalCoroutinesApi
    suspend fun getAllReviews(dishId: String): List<ReviewRes> {
        val result = mutableListOf<ReviewRes>()

        // запускаем параллельные "сборщики" комментариев
        val channels = mutableListOf<Channel<List<ReviewRes>>>()
        for (i in 0 until THREADS) {
            val channel = startCommentChannel(dishId, i)
            channels.add(channel)
        }

        // собираем информацию от "сборщиков" через каналы, пока "жив" хотя бы один из них
        var alive = THREADS
        while (alive > 0) {
            for (i in 0 until THREADS) {
                val channel = channels[i]
                if (channel.isClosedForReceive) {
                    continue
                }

                val reviews = channel.receive()
                // паттерн "последний чемодан"
                if (reviews == LAST_REVIEW) {
                    channel.close()
                    alive--
                    continue
                }

                result.addAll(reviews)
            }
        }

        return result
    }

    @ExperimentalCoroutinesApi
    private suspend fun startCategoryChannel(threadNumber: Int): Channel<List<CategoryRes>> {
        val channel = Channel<List<CategoryRes>>()
        scope.launch {
            var offset = threadNumber * LIMIT
            while (!channel.isClosedForSend) {
                var categories: List<CategoryRes>
                try {
                    categories = service.categories(offset, LIMIT)
                } catch (e: HttpException) {
                    if (e.code() == 304) {
                        categories = listOf<CategoryRes>()
                    } else {
                        throw e
                    }
                }

                if (categories.isEmpty()) {
                    // отправляем пустой список (паттерн "последний чемодан")
                    channel.send(LAST_CATEGORY)
                    break
                }
                channel.send(categories)
                offset += THREADS * LIMIT
            }
        }
        return channel
    }

    @ExperimentalCoroutinesApi
    private suspend fun startDishChannel(threadNumber: Int): Channel<List<DishRes>> {
        val channel = Channel<List<DishRes>>()
        scope.launch {
            var offset = threadNumber * LIMIT
            while (!channel.isClosedForSend) {
                var dishes: List<DishRes>
                try {
                    dishes = service.dishes(offset, LIMIT)
                } catch (e: HttpException) {
                    if (e.code() == 304) {
                        dishes = listOf()
                    } else {
                        throw e
                    }
                }

                if (dishes.isEmpty()) {
                    // отправляем пустой список (паттерн "последний чемодан")
                    channel.send(LAST_DISH)
                    break
                }

                channel.send(dishes)
                offset += THREADS * LIMIT
            }
        }
        return channel
    }

    @ExperimentalCoroutinesApi
    private suspend fun startCommentChannel(dishId: String, threadNumber: Int): Channel<List<ReviewRes>> {
        val channel = Channel<List<ReviewRes>>()
        scope.launch {
            var offset = threadNumber * LIMIT
            while (!channel.isClosedForSend) {
                var reviews: List<ReviewRes>
                try {
                    reviews = service.reviews(dishId, offset, LIMIT)
                } catch (e: HttpException) {
                    if (e.code() == 304) {
                        reviews = listOf()
                    } else {
                        throw e
                    }
                }

                if (reviews.isEmpty()) {
                    // отправляем пустой список (паттерн "последний чемодан")
                    channel.send(LAST_REVIEW)
                    break
                }

                channel.send(reviews)
                offset += THREADS * LIMIT
            }
        }
        return channel
    }

}