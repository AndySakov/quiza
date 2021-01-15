package com.quiza.ui

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.{Menu, MenuBar, MenuItem}
import scalafx.scene.layout.{AnchorPane, VBox}

object UI extends JFXApp {
	stage = new PrimaryStage {
		scene = new Scene(1350d, 629d) {
			root = new AnchorPane() {
				val jk: VBox = new VBox{
//					val splash: ImageView = new ImageView(new Image("file:///c/Users/lenovo/Documents/code/scala/projectz/Mathapp/src/main/scala/com/mathapp/ui/res/splash.png")){
//						layoutX = 0d
//						layoutY = 0d
//					}
//					style = "fx-backgroud-image: src(file:///C:/Users/lenovo/Documents/code/scala/projectz/Mathapp/src/main/scala/com/mathapp/ui/res/splash.png)"
					children = List(
//						splash
					)
//					background = new Background(new BackgroundImage())
					
				}
				val menuBar: MenuBar = new MenuBar {
					useSystemMenuBar =true
					menus = List(
						new Menu("File") {
							items = List(
								new MenuItem("Preferences..."),
								new MenuItem("Exit")
							)
						},
						new Menu("Help") {
							items = List(
								new MenuItem("About Math App")
							)
						}
					)
					hide
				}
				children = List(
//					menuBar
					jk
				)
			}
			content = List(
			)
			
		}
	}
	
}
