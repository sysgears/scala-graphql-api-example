package errors

case class AmbigousResult(msg: String = "") extends Error(msg)
