package com.example.raceconditiontest.stock.controller

import com.example.raceconditiontest.stock.domain.Stock
import com.example.raceconditiontest.stock.domain.StockRepository
import com.example.raceconditiontest.stock.service.StockService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
class StockController(
    var stockService: StockService,
    var stockRepository: StockRepository
) {

    @GetMapping("/stocks")
    fun createStock() {
        stockRepository.save(Stock(1L, 100))
    }

    @GetMapping("stocks/{id}/decrease")
    fun decreaseStock(@PathVariable id:Long) {
        stockService.decrease(id)
    }

}
