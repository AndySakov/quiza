package com.quiza.api.types

class Answer {

}
object Answer {
	type SingleAnswer = (String, Option[Double])
	class DoubleAnswer(ans: Double) extends Answer {
		def value: Double = ans.toFloat.toDouble
	}
	class QuadraticAnswer(vars: List[SingleAnswer]) extends Answer {
		def value: List[(String, Option[Double])] = vars
	}
}
