package com.quiza.api.types
import scala.io.StdIn._
import scala.math._
import scala.util.Random
import Answer._

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
	
	case class Sum(diff: Difficulty) extends Question {
		val lhs: Long = ((1 / random) * pow(10, diff)).round
		val rhs: Long = ((1 / random) * 10).round
		val op: SignedOp = randOp(diff)
		def ans: DoubleAnswer = {
			new DoubleAnswer(op._1._1(lhs, rhs))
		}
		override def expr: String = s"$lhs ${op._1._2} $rhs = "
		
		override def points: Difficulty = 5
		
		override def run(): Double = {
			print(expr)
			val gh = readDouble
			if (gh == ans.value) {
				println(s"That's correct!!!\nYou get $points points")
				points
			}
			else{
				println("Olodo oshi!!! Wrong answer jor.\nThe correct answer is "+ans.value)
				0
			}
		}
	}
	
	//noinspection DuplicatedCode
	case class QuadraticEquation(diff: Difficulty) extends Equation {
		val a: Double = randType(randNumber(diff))
		val b: Double = randType(randNumber(diff))
		val c: Double = randType(randNumber(diff))
		
		override def expr: String = {
//			Just useless operator ops
//			val ab = Random.shuffle(ops.filter(p => p._2 == 1)).head._1
//			val bc = Random.shuffle(ops.filter(p => p._2 == 1)).head._1
			s"${a}x^2 ${randOp(diff)._1._2} ${b}x $c = 0"
		}
		override def ans: QuadraticAnswer = {
			val x1 = (b * -1) + sqrt(-4*a*c)/2*a
			val x2 = (b * -1) - sqrt(-4*a*c)/2*a
			new QuadraticAnswer(List(("x1",Some(x1)),("x2",Some(x2))))
		}
		
		override def run(): Double = {
			print(expr)
			ans.value.map{
				kl => {
					print(s"${kl._1} = ")
					val jk: Double = readDouble
					if(jk == kl._2.get){
						println(s"That's correct!!!\nYou get $points points")
						points
					}
					else{
						println("Olodo oshi!!! Wrong answer jor.\nThe correct answer is "+ans.value)
						0
					}
				}
			}.sum
		}
	}
	
	case class Algebra(diff: Difficulty) extends Equation {
		val x = new Variable(randVariable(), Some(randType(randNumber(diff))))
		val o: Double = randType(randNumber(diff))
		val op: Operator = randOp(diff)._1
		override def ans: QuadraticAnswer = {
			new QuadraticAnswer(List(x))
		}
		
		override def expr: String = s"if ${x._1} ${op._2} $o = ${op._1(x._2.get, o)} then ${x._1} = "
		
		override def run(): Double = {
			print(expr)
			val v = readDouble
			if(v == x._2.get) {
				println(s"That's correct!!!\nYou get $points points")
				points
			}
			else{
				println("Olodo oshi!!! Wrong answer jor.\nThe correct answer is "+ans.value)
				0
			}
		}
	}
}
trait Question{
	def expr: String
	def points: Int
	def ans: Answer
	def run(): Double
}
trait Equation extends Question {
	override def points: Int = 10
}