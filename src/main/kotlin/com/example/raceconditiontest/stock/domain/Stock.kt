package com.example.raceconditiontest.stock.domain

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.RequiredArgsConstructor

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
open class Stock(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    var quantity: Int

) {
    fun decrease() {
        if (this.quantity == 0) {
            throw RuntimeException("재고는 0개 미만이 될 수 없습니다.")
        }
        this.quantity--
    }
}
