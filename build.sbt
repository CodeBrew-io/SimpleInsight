organization := "io.codebrew"

name := "simple-insight"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.0-M8"

libraryDependencies ++= Seq(
	"org.scala-lang" % "scala-compiler" % scalaVersion.value,
	"org.specs2" %% "specs2" % "2.3.7" % "test"
)

initialCommands in console := """
import scala.reflect.runtime.{currentMirror => cm}
import scala.reflect.runtime.universe._
import scala.tools.reflect.ToolBox
val tb = cm.mkToolBox()
"""