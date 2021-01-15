package com.quiza.api.types

trait Equation extends Question {
  override def points: Int = 10
}
