package com.quiza.api.types
import scala.math._
import scala.util.Random

object Question {
	type Operator = ((Double, Double) => Double, String)
	type SignedOp = (Operator, Difficulty)
	type Difficulty = Int
	type Variable = (String, Option[Double])
	def randNumber(digits: Int): Double = {Random.nextInt(10*digits)}
	
	def randType(x: Double): Double = {
		if (Random.nextBoolean) {
			x
		} else {
			x * -1
		}
	}
	
	def randVariable(): String = {
		val set = ('a' to 'z' map(_.toString)).toList
		val f = Random.shuffle(set).head
		if(Random.nextBoolean()){
			f.toUpperCase()
		}else{
			f
		}
	}
	
	def randOp(diff: Difficulty): SignedOp = {
		Random.shuffle(ops).filter(x => x._2 <= diff).head
	}
	
	def questions: Int = 3
	def questionNames: List[(String, Int)] = List("Sum" -> 1, "QuadraticEquation" -> 6, "LinearEquation" -> 4)
	
	def randQuestion(diff: Difficulty): Option[Question] = {
		val _type = Random.shuffle(questionNames).filter(_._2 <= diff).head._1
		_type match {
			case "Sum" => Some(Sum(diff))
			case "QuadraticEquation" => Some(QuadraticEquation(diff))
			case "LinearEquation" => Some(Algebra(diff))
			case _ => None
		}
	}
	
	private val ops: List[SignedOp] = List(((_ + _, "+"), 1), ((_ * _, "x"), 2), ((_ - _, "-"), 1), ((_ / _, "÷"), 2), ((_ % _, "%"), 3), ((pow, "^"), 4))
}
trait Question{
	def expr: String
	def points: Int
	def ans: Answer
	def run(): Double
}
