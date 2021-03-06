import sbt._
import sbt.Keys._
import com.typesafe.sbt.SbtAspectj.{ Aspectj, aspectjSettings, compiledClasses }
import com.typesafe.sbt.SbtAspectj.AspectjKeys.{ binaries, compileOnly, inputs, lintProperties, weaverOptions }


object AspectJ {

  lazy val aspectJSettings = aspectjSettings ++ Seq(
     compileOnly in Aspectj    :=  true,
               fork in Test    :=  true,
        javaOptions in Test  <++=  weaverOptions in Aspectj,
  lintProperties in Aspectj    +=  "invalidAbsoluteTypeName = ignore"

    //  Add this line if we need to include some .aj aspects again in the project.
    //    products in Compile   <<=   products in Aspectj
  )
}