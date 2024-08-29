package com.example.raceconditiontest.stock.domain

import com.example.raceconditiontest.stock.service.StockService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.lang.String.valueOf
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

@Service
@RequiredArgsConstructor
class LockStockFacade(
    private var stockService: StockService,
) {
    private var locks: ConcurrentHashMap<String, Lock> = ConcurrentHashMap()

    fun decrease(id: Long) {
        val lock: Lock = locks.computeIfAbsent(valueOf(id)){ ReentrantLock()}
        val acquiredLock: Boolean = lock.tryLock(3, TimeUnit.SECONDS)

        if (!acquiredLock) {
            throw RuntimeException("Lock 획득 실패!")
        }
        try {
            stockService.decrease(id)
        } finally {
            lock.unlock()
        }
    }


}
