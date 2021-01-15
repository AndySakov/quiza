package com.quiza.api

import com.quiza.api.types.Answer.{DoubleAnswer, QuadraticAnswer}
import com.quiza.api.types.Question.{Difficulty, Operator, SignedOp, Variable, randNumber, randOp, randType, randVariable}

import scala.io.StdIn.readDouble
import scala.math.{pow, random, sqrt}

package object types {
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
        println("Wrong answer!!!\nThe correct answer is "+ans.value)
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
            println("Wrong answer!!!\nThe correct answer is "+ans.value)
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
        println("Wrong answer!!!\nThe correct answer is "+ans.value)
        0
      }
    }
  }
}
