package com.example.raceconditiontest.stock.domain

import lombok.RequiredArgsConstructor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@RequiredArgsConstructor
@Component
interface StockRepository : JpaRepository<Stock, Long> {

    override fun getById(id: Long): Stock {
        return findById(id).orElseThrow {NoSuchElementException()}
    }


}
