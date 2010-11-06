import sbt._

class Project(info: ProjectInfo) extends DefaultProject(info) {
  val uf_version = "$unfiltered_version$"
  val ufws = "net.databinder" %% "unfiltered-websockets" % uf_version
}