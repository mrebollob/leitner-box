package com.mrebollob.leitnerbox.domain.exception

sealed class Failure {

    object EmptyData : Failure()

    abstract class FeatureFailure : Failure()
}
