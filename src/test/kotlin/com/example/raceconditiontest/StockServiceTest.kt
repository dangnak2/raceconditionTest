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



}
