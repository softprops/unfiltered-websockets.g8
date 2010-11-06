import sbt._

class UnfilteredWebSocketsTemplate(info: ProjectInfo)
    extends DefaultProject(info) with giter8.Template {
  override def disableCrossPaths = true
}
