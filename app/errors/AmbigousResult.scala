package errors

import sangria.execution.UserFacingError

/**
  * Represents an exception object indicating that we received ambiguous result.
  *
  * @param msg an exception message to show
  */
case class AmbigousResult(msg: String) extends Exception with UserFacingError {
  override def getMessage: String = msg
}