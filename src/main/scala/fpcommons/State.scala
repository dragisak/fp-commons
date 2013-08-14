package fpcommons

/**
 * State monad.
 *
 * @param run state function
 */
case class State[S,+A](run: S => (A,S)) {

  def map[B](f :A => B) :State[S, B] = State(state => {
    val (a, state2) = run(state)
    (f(a), state2)
  })

  def flatMap[B](f :A => State[S, B]) :State[S, B] = State(state => {
    val (a, state2) = run(state)
    f(a).run(state2)
  })

}


object State {

  def unit[S, A](a :A) :State[S, A] = State(state => (a, state))

  def sequence[S, A](fs :Traversable[State[S, A]]) :State[S, Traversable[A]] = State(state =>
    fs.foldLeft((Seq[A](), state)) { case ((seq, st1), s) =>
        val (a, st2) = s.run(st1)
        (seq :+ a, st2)
    })

}