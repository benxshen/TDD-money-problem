package money_problem.domain

import arrow.core.Either

fun <A, B> Either<A, B>.leftUnsafe(): A = when (this) {
    is Either.Left -> this.value
    else -> throw IllegalArgumentException("Not a Left")
}

fun <A, B> Either<A, B>.rightUnsafe(): B = when (this) {
    is Either.Right -> this.value
    else -> throw IllegalArgumentException("Not a Right")
}