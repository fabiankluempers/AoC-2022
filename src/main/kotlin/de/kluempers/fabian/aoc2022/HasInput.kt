interface HasInput {
  val input : List<String>
  val day: Int
}

fun inputReaderFor(day: Int) = object : HasInput {
  override val input: List<String> by lazy { readInputFor(day) }
  override val day: Int = day
}