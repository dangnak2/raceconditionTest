package com.example.raceconditiontest.stock.service

import com.example.raceconditiontest.stock.domain.StockRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class StockService(private var stockRepository: StockRepository) {

    //    @Transactional
    fun decrease(id: Long) {
        synchronized(this) {
            val stock = stockRepository.findById(id).orElseThrow()
            stock.decrease()
            stockRepository.saveAndFlush(stock)
        }
    }
}
