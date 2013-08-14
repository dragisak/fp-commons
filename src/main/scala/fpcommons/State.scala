package fpcommons

/**
 * State monad.
 *
 * @param run state function
 * @tparam S state type
 * @tparam A result type
 */
case class State[S,+A](run: S => (A,S)) {

  /**
   * Applies function on the state result.
   *
   * @param f function that maps result from type A to type B
   * @tparam B new result type
   * @return state with new result type
   */
  def map[B](f :A => B) :State[S, B] = State(state => {
    val (a, state2) = run(state)
    (f(a), state2)
  })

  /**
   * Applies a state function on the state result.
   *
   * @param f function from result of type A to result of type State [_,A]
   * @tparam B New result type
   * @return state with new result type
   */
  def flatMap[B](f :A => State[S, B]) :State[S, B] = State(state => {
    val (a, state2) = run(state)
    f(a).run(state2)
  })

}


object State {

  /**
   * Returns a state without modification.
   *
   * @param a value
   * @tparam S state type
   * @tparam A result type
   * @return state that returns unmodified value and state
   */
  def unit[S, A](a :A) :State[S, A] = State(state => (a, state))

  /**
   * Transforms a collection of states into a state of collections.
   *
   * @param fs collection
   * @tparam S state type
   * @tparam A result type
   * @return state of collection
   */
  def sequence[S, A](fs :Traversable[State[S, A]]) :State[S, Traversable[A]] = State(state =>
    fs.foldLeft((Seq[A](), state)) { case ((seq, st1), s) =>
        val (a, st2) = s.run(st1)
        (seq :+ a, st2)
    })

}