package com.quiza.api

import com.quiza.api.types.Question
import com.quiza.api.types.Question._
//import shapeless.ops.nat.Sum

import scala.io.StdIn.readInt

object Api {
	/*
	* @author Obafemi Teminife
	 * TODO:
	 *  1. Add error handling for stupid input
	 *  2. Create a GUI version
	 *  3. Remove main method when tests are complete
	 *  4. Change object to a class
	*/
	
	def main(args: Array[String]): Unit = {
		try {
			print("Pick a difficulty level from 1-10: ")
			val difficulty = readInt
			if (difficulty > 10) {
				println(s"Over sabi, is $difficulty between 1-10, and you are taking a maths test, you better go back to primary school")
				System.exit(0)
			}
			print("How many questions: ")
			val h = genRandTest(difficulty, readInt()).map(_.run()).sum
			println(s"Total Score: $h")
		} catch {
			case _: java.util.InputMismatchException => println("Ode is that what I asked you for?")
		}
	}

	
	def genRandTest(diff: Int, no_of_ques: Int): List[Question] = {
		List.fill(no_of_ques)(randQuestion(diff).get)
	}
	
	
}
