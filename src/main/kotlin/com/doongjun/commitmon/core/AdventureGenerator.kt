package com.doongjun.commitmon.core

import java.util.concurrent.*

class AdventureGenerator {
    companion object {
        private val random = ThreadLocalRandom.current()

        fun generateY(isFlying: Boolean): Int = if (isFlying) random.nextInt(5, 15) else random.nextInt(30, 80)

        fun generateMotion(): Motion {
            val duration = random.nextInt(30, 180)
            val toRight = random.nextBoolean()
            val d = if (toRight) -1 else 1

            val x1 = random.nextFloat(20f, 80f)
            val x2 = if (toRight) x1 + random.nextFloat() * (80 - x1) else x1 - random.nextFloat() * x1
            val x3 = if (toRight) x2 + random.nextFloat() * (80 - x2) else x2 - random.nextFloat() * x2
            val positions =
                listOf(
                    Position(x1, d),
                    Position(x2, d),
                    Position(x3, d),
                    Position(x3, d * -1),
                    Position(if (toRight) random.nextFloat(x1, x3) else random.nextFloat(x3, x1), d * -1),
                    Position(x1, d * -1),
                )

            return Motion(
                duration,
                positions,
            )
        }
    }

    data class Motion(
        val duration: Int,
        val positions: List<Position>,
        val namePositions: List<NamePosition> =
            positions.map {
                NamePosition(
                    x = if (it.direction == -1) it.x - 3.5f else it.x - 0.3f,
                )
            },
    )

    data class Position(
        val x: Float,
        val direction: Int,
    )

    data class NamePosition(
        val x: Float,
    )
}
