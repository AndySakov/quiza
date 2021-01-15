package com.quiza.api.types

class Answer {

}
object Answer {
	class DoubleAnswer(ans: Double) extends Answer {
		def value: Double = ans.toFloat.toDouble
	}
	class QuadraticAnswer(vars: List[(String, Option[Double])]) extends Answer {
		def value: List[(String, Option[Double])] = vars
	}
}