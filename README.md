fp-commons
==========

[![Build Status](https://travis-ci.org/dragisak/fp-commons.png?branch=master)](https://travis-ci.org/dragisak/fp-commons)

Functional Programming Utilities
==============================


State
-----

```scala
 val st = for {
    in1   <- State[String, Int](s => (s.length, s + "*"))
    in2   <- State[String, Int](s => (in1 * 2,  s + "+"))
    in3   <- State[String, String](s => ((in2+1).toString + "X",  s + "#"))
} yield in3

 st.run("--")

 res0: (String, String) = (5X,--*+#)
```