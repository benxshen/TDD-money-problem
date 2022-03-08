package money_problem.domain

import arrow.core.Either
import arrow.core.getOrElse

fun <A, B> List<Either<A, B>>.lefts(): List<A> =
    this.map { it.swap() }
        .mapNotNull { it.getOrElse { null } }

fun <A, B> List<Either<A, B>>.rights(): List<B> =
    this.mapNotNull { it.getOrElse { null } }