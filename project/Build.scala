import sbt._
import sbt.Keys._
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object ApplicationBuild extends Build {

  lazy val botenjohanna = (
    Project("botenjohanna", file("."))
    enablePlugins(ScalaJSPlugin)
    settings(
      scalaVersion := "2.11.5",
      scalacOptions ++= Seq("-feature", "-deprecation"),
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "0.8.0"
      )
    )
  )

}