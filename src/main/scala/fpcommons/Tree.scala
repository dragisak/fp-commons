package fpcommons

sealed trait Tree[+A]

object Tree {

  trait Node[A] extends Tree[A] {

    def value :A

    def left: Tree[A]

    def right: Tree[A]
  }

  trait Empty[A] extends Tree[A] {}

  def empty[A] :Empty[A] = new Empty[A] {}

  def apply[A](a : => A) :Node[A] = new Node[A] {
    override lazy val value :A = a

    override val left = empty

    override val right = empty
  }

  def apply[A](a : => A, l : => Tree[A], r : => Tree[A]) :Node[A] = new Node[A] {
    override lazy val value :A = a
    override lazy val left :Tree[A] = l
    override lazy val right :Tree[A] = r
  }




}