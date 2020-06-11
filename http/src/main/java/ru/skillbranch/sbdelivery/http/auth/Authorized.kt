package ru.skillbranch.sbdelivery.http.auth

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Target(AnnotationTarget.FUNCTION)
@Retention(RetentionPolicy.RUNTIME)
annotation class Authorized