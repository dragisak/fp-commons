package fpcommons

sealed trait Tree[A]

object Tree {

  trait Node[A] extends Tree {

    def value :A

    def left: Tree[A]

    def right: Tree[A]
  }

  case object Empty extends Tree


  def apply[A](a : => A) :Node[A] = new Node[A] {
    override lazy val value :A = a

    override val left = Empty

    override val right = Empty
  }

  def apply[A](a : => A, l : => Tree[A], r : => Tree[A]) :Node[A] = new Node[A] {
    override lazy val value :A = a
    override lazy val left :Tree[A] = l
    override lazy val right :Tree[A] = r
  }




}