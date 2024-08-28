package com.example.raceconditiontest

import com.example.raceconditiontest.stock.domain.StockRepository
import com.example.raceconditiontest.stock.domain.Stock
import com.example.raceconditiontest.stock.service.StockService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@SpringBootTest
class StockServiceTest @Autowired constructor(
    private  var stockService: StockService,
    private  var stockRepository: StockRepository
    )
{

    private var stockId: Long? = null


    @BeforeEach
    fun setUp() {
        stockId = stockRepository.saveAndFlush(Stock(1L, 100)).id
    }

    @AfterEach
    fun tearDown() {
        stockRepository.deleteAll()
    }

    @Test
    fun decreaseStock() {

        // given
        stockService.decrease(stockId!!)

        // when
        val stock = stockRepository.getById(stockId!!)

        // then
        assertEquals(99, stock.quantity)
    }

    @Test
    fun decrease_with_100_request() {

        // given
        val threadCnt: Int = 100
        val restTemplate: RestTemplate = RestTemplate()
        val executorService: ExecutorService = Executors.newFixedThreadPool(32);
        val latch: CountDownLatch = CountDownLatch(threadCnt)

        // when
        for (i in 0 until threadCnt) {
            executorService.submit {
                try{
                    val port = 8080
                    val forEntity: ResponseEntity<Void> = restTemplate.getForEntity(
                        "http://localhost:$port/stocks/$stockId/decrease",
                            Void::class.java
                    )
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await();

        //then
        val stock: Stock = stockRepository.getById(stockId!!)
        assertEquals(0, stock.quantity)
    }


}
